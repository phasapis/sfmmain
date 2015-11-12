package eu.deri.dataanalysis.analyser;

import java.util.List;

import eu.deri.dataanalysis.operation.ExtremaOperation;
import eu.deri.dataanalysis.operation.Operation;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;


/**
 * 
 * @author swapnil/yaskha/jbjares
 *
 */
public class ExtremaAnalyser implements Analyser {

	public Object analyse() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Object analyse(Object object) {
		
		List<PairFeature> lstOfPairOfFeature = (List<PairFeature>) object;
		PairFeature pairFeatureObject = lstOfPairOfFeature.get(0);
		Feature featureXObj = pairFeatureObject.getFeatureX();
		Feature featureYObj = pairFeatureObject.getFeatureY();
		Operation extremaOperation = new ExtremaOperation();
		lstOfPairOfFeature = (List<PairFeature>) extremaOperation.perform(lstOfPairOfFeature);
		
		Double minima = null;
		Double maxima = null;
		String posMin = null; 
		String posMax = null;
		
		for(PairFeature pairFeature:lstOfPairOfFeature) {
			
			Feature featureY = pairFeature.getFeatureY();
			Feature featureX = pairFeature.getFeatureX();
			
			//for(String min : (List<String>) featureY.getLstOfData().get(0)) {
			int countX = 0;
			for(Object obj : featureY.getLstOfData()) {
				countX++;
				if(obj==null){
					continue;
				}
				if(countX>=featureY.getLstOfData().size()){
					System.out.println(featureY.getLstOfData().size());
					continue;
				}
				System.out.println(featureY.getLstOfData().size());
				List<String> minList = (List<String>) featureY.getLstOfData().get(countX);
				if(minList==null || minList.isEmpty()){
					continue;
				}
				
				String min = minList.get(0);
					if(min==null || "".equals(min) || !min.contains("#")){
						continue;
					}
					double y=Double.valueOf(min.split("#")[1]);
					if(minima==null)
						minima=y;
					
					if(y<=minima) {
						minima=y;
						posMin=min;
					}

				
			}
			
			int countY = 0;
			//for(String max: (List<String>) featureY.getLstOfData().get(1)){
			for(Object obj : featureY.getLstOfData()) {
				countY++;
				if(obj==null){
					continue;
				}
				if(countY>=featureY.getLstOfData().size()){
					System.out.println(featureY.getLstOfData().size());
					continue;
				}
				List<String> maxList = (List<String>) featureY.getLstOfData().get(countY);
				if(maxList==null || maxList.isEmpty()){
					continue;
				}
				
				String max = maxList.get(1);
				if(max==null || "".equals(max) || !max.contains("#")){
					continue;
				}
				double y=Double.valueOf(max.split("#")[1]);
				if(maxima==null)
					maxima=y;
				
				if(y>=maxima) {
					maxima=y;
					posMax=max;
				}
				
			}

				
			
			
			List<String> lstOfNegSlope=(List<String>)featureY.getLstOfData().get(2);
			List<String> lstOfPosSlope=(List<String>)featureY.getLstOfData().get(3);
			
			System.out.println("Neg Slope size: " + lstOfNegSlope.size());
			System.out.println("Pos Slope size: " + lstOfPosSlope.size());
			
			for(Integer i=0;i<lstOfNegSlope.size()-1;i++) {
				System.out.println(featureX.getAttrName()+" is decreasing from :"+lstOfNegSlope.get(i)+" to "+lstOfNegSlope.get(i+1));
			}
			
			for(Integer i=0;i<lstOfPosSlope.size()-1;i++) {
				System.out.println(featureY.getAttrName()+" rising from :"+lstOfPosSlope.get(i)+" to "+lstOfPosSlope.get(i+1));
			}
		}
		
		
		if(posMin != null) {
			posMin = posMin.replace("#", ", ");
		}
		
		if(posMax != null) {
			posMax = posMax.replace("#", ", ");
		}
		
		System.out.println("Minima at :- (" + posMin + ")");
		System.out.println("Maxima at :- (" + posMax + ")");
		
		String minArr[] = posMin.split(",");
		String maxArr[] = posMax.split(",");
		
		featureXObj.setMinima(minArr[0]);
		featureXObj.setMaxima(maxArr[0]);
		featureYObj.setMinima(minArr[1]);
		featureYObj.setMaxima(maxArr[1]);
		
		pairFeatureObject.setMinimaGlobal("( " + posMin + " )");
		pairFeatureObject.setMaximaGlobal("( " + posMax + " )");
		
		return object;
	}

}
