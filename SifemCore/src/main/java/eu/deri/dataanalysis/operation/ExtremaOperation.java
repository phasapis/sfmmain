package eu.deri.dataanalysis.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

/**
 * 
 * @author swapnil/yaskha
 *
 */
public class ExtremaOperation implements Operation{

	/**
	 * 
	 */
	public Object perform(List<PairFeature> object) {
		
		List<PairFeature> lstOfPairOfFeature=(List<PairFeature>)object;
		//Map<String,Double> uniqueRec=new TreeMap<String,Double>();
		
		List<String> lstOfMax=new ArrayList<String>();
		List<String> lstOfMin=new ArrayList<String>();
		List<String> lstOfNegative=new ArrayList<String>();
		List<String> lstOfPositive=new ArrayList<String>();
		
		String xName=object.get(0).getFeatureX().getAttrName();
		String yName=object.get(0).getFeatureY().getAttrName();
		
		for (Integer i=0;i<lstOfPairOfFeature.size();i++) {
			
			PairFeature pairFeature=lstOfPairOfFeature.get(i);
			
			Feature featureX = pairFeature.getFeatureX();
			Feature featureY = pairFeature.getFeatureY();
			
			List<Double> lstOfVal=new ArrayList<Double>();
			
			for(Integer j=0;j<featureX.getLstOfData().size()-1;j++) {
				
				double xNextValue = Double.valueOf(featureX.getLstOfData().get(j+1).toString());
				double xValue = Double.valueOf(featureX.getLstOfData().get(j).toString());
				
				double yNextValue = Double.valueOf(featureY.getLstOfData().get(j+1).toString());
				double yValue = Double.valueOf(featureY.getLstOfData().get(j).toString());
				
				if( (xValue != xNextValue) && (yValue != yNextValue) ) {
					
					double xh = (double) ( xNextValue - xValue );
					double fxhx = (double) ( yNextValue - yValue );
									
					double deriv = fxhx/xh;
					
//					System.out.println("=========================================");
//					System.out.println("xh = " + xNextValue + " - " + xValue + " = " + xh);
//					System.out.println("fxhx = " + yNextValue + " - " + yValue + " = " + fxhx);
//					System.out.println("Deriv = " + fxhx + " / " + xh + " = " + deriv);
//					System.out.println("========================================= \n");
					
					String key=featureX.getLstOfData().get(j)+"#"+featureY.getLstOfData().get(j);
					
					if(lstOfVal.size()==0) {
						
						lstOfVal.add(deriv);
						double sign=deriv * Double.POSITIVE_INFINITY;
						
						if(sign==Double.NEGATIVE_INFINITY) {
							lstOfNegative.add(key);
						} else if(sign==Double.POSITIVE_INFINITY) {
							lstOfPositive.add(key);
						}
						
					} else{
						
						for(Double val:lstOfVal) {
							
							double sign=val * Double.POSITIVE_INFINITY;
							
							if(sign==Double.POSITIVE_INFINITY) {
								double dervSign=deriv*Double.POSITIVE_INFINITY;
								
								if(dervSign==Double.POSITIVE_INFINITY) {
									lstOfVal.add(deriv);
									break;
								} else if(dervSign==Double.NEGATIVE_INFINITY) {
									lstOfPositive.add(key);
									lstOfVal.clear();
									lstOfVal.add(deriv);
									lstOfMax.add(key);
									lstOfNegative.add(key);
									break;
								}
								
							} else if(sign==Double.NEGATIVE_INFINITY) {
								
								double dervSign=deriv*Double.POSITIVE_INFINITY;
								
								if(dervSign==Double.NEGATIVE_INFINITY) {
									lstOfVal.add(deriv);
									break;
								} else if(dervSign==Double.POSITIVE_INFINITY) {
									lstOfNegative.add(key);
									lstOfVal.clear();
									lstOfVal.add(deriv);
									lstOfMin.add(key);
									lstOfPositive.add(key);
									break;
								}
							}
						}
					}
					
					if(j==featureX.getLstOfData().size()-2) {
						
						double sign=deriv * Double.POSITIVE_INFINITY;
						
						if(sign==Double.NEGATIVE_INFINITY){
							lstOfNegative.add(key);
						} else if(sign==Double.POSITIVE_INFINITY){
							lstOfPositive.add(key);
						} 
						
					}
					
				}
				
			}
			
		}
		
		return buildObject(lstOfMin,lstOfMax,xName,yName,lstOfNegative,lstOfPositive);
		
	}
	
	
	/**
	 * 
	 * @param lstOfMin
	 * @param lstOfMax
	 * @param xName
	 * @param yName
	 * @param lstOfNegative
	 * @param lstOfPositive
	 * @return
	 */
	private List<PairFeature> buildObject(List<String> lstOfMin,List<String> lstOfMax,String xName,String yName,List<String> lstOfNegative,List<String> lstOfPositive){
		PairFeature pairFeat=new PairFeature();
		List<PairFeature> lstOfFeature=new ArrayList<PairFeature>();
		Feature featureX=new Feature();
		Feature featureY=new Feature();
		featureX.setAttrName(xName);
		featureY.setAttrName(yName);
		featureY.getLstOfData().add(lstOfMin);
		featureY.getLstOfData().add(lstOfMax);
		featureY.getLstOfData().add(lstOfNegative);
		featureY.getLstOfData().add(lstOfPositive);
		pairFeat.setFeatureX(featureX);
		pairFeat.setFeatureY(featureY);
		lstOfFeature.add(pairFeat);
		return lstOfFeature;
	}
}
