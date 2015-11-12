package eu.deri.dataanalysis.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

public class DistanceCalculation implements Operation {

	public Object perform(List<PairFeature> object) {
		List<PairFeature> lstOfPairOfFeature=(List<PairFeature>)object;
		List<Double> lstofVal=new ArrayList<Double>();
		String xName=object.get(0).getFeatureX().getAttrName();
		String yName=object.get(0).getFeatureY().getAttrName();
		//for(PairFeature pairFeature:lstOfPairOfFeature){
			Feature featureY=lstOfPairOfFeature.get(0).getFeatureY();
			Feature featureX=lstOfPairOfFeature.get(0).getFeatureX();
			Feature featureY1=lstOfPairOfFeature.get(1).getFeatureY();
			Feature featureX1=lstOfPairOfFeature.get(1).getFeatureX();
			Integer counter=0;
			double total=0.0;
			for(Integer i=0;i<featureX.getLstOfData().size();i++) {
				 double y=Double.valueOf(featureY1.getLstOfData().get(i).toString());//165.4*(Math.pow(10,(0.06*i))-1);
				 double x=Double.valueOf(featureX1.getLstOfData().get(i).toString());
				 double x1=Double.valueOf(featureX.getLstOfData().get(i).toString());
				 				
				 if(Math.round(Math.pow(10,2)*x)/Math.pow(10,2)==Math.round(Math.pow(10,2)*x1)/Math.pow(10,2)){
					 double yh=Double.valueOf(featureY.getLstOfData().get(i).toString());//165.4*(Math.pow(10,(0.05*i))-1);
					 double res =Math.abs(y-yh)/y;
					 total=total+res;
				 }
				// counter++;
			}
			lstofVal.add(total/(35-0.3));
			total=0.0;
		//}
		/*double total=0.0;
		 for(double i=0.3;i<=35;i=i+0.1) {
			 double y=165.4*(Math.pow(10,(0.06*i))-1);
			 double yh=165.4*(Math.pow(10,(0.05*i))-1);
			 double res =Math.abs(y-yh)/y;
			 total=total+res;
		 }*/
		return buildObject(lstofVal,xName,yName);
	}
	private List<PairFeature> buildObject(List<Double> uniqueRec,String xName,String yName){
		PairFeature pairFeat=new PairFeature();
		List<PairFeature> lstOfFeature=new ArrayList<PairFeature>();
		Feature featureX=new Feature();
		Feature featureY=new Feature();
		featureX.setAttrName(xName);
		featureY.setAttrName(yName);
		for(Double val:uniqueRec){
			featureY.getLstOfData().add(val);
		}
		pairFeat.setFeatureX(featureX);
		pairFeat.setFeatureY(featureY);
		lstOfFeature.add(pairFeat);
		return lstOfFeature;
	}
}
