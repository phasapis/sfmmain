package eu.deri.dataanalysis.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

public class AverageOperation implements Operation{

	 
	private List<PairFeature> buildObject(Map<String,List<Double>> uniqueRec,String xName,String yName){
		PairFeature pairFeat=new PairFeature();
		List<PairFeature> lstOfFeature=new ArrayList<PairFeature>();
		Set<String> setOfKey=uniqueRec.keySet();
		Feature featureX=new Feature();
		Feature featureY=new Feature();
		featureX.setAttrName(xName);
		featureY.setAttrName(yName);
		for(String key:setOfKey) {
			//String key=null;
			featureX.getLstOfData().add(key);
			List<Double> lstOfVal=uniqueRec.get(key);
			double sum=0;
			 for(Double dVal:lstOfVal){
				 sum+=dVal;
			 }
			 featureY.getLstOfData().add((sum/lstOfVal.size()));
		}
		pairFeat.setFeatureX(featureX);
		pairFeat.setFeatureY(featureY);
		lstOfFeature.add(pairFeat);
		return lstOfFeature;
	}

	public Object perform(List<PairFeature> object) {
		Map<String,List<Double>> uniqueRec=new LinkedHashMap<String,List<Double>>();
		String xName=object.get(0).getFeatureX().getAttrName();
		String yName=object.get(0).getFeatureY().getAttrName();
		if(object.get(0).getFeatureX().getAxis().equalsIgnoreCase("X")){
			Feature featureY=object.get(0).getFeatureY();
			Feature featureX=object.get(0).getFeatureX();
			 for(Integer i=0;i<featureX.getLstOfData().size();i++){
				 String dataX=(String)featureX.getLstOfData().get(i);
				 if(uniqueRec.get(dataX)==null){
					 uniqueRec.put(dataX, new ArrayList<Double>());
				 }
				 uniqueRec.get(dataX).add(Double.valueOf((String)featureY.getLstOfData().get(i)));
			 }
			
		} else if(object.get(0).getFeatureY().getAxis().equalsIgnoreCase("X")){
			Feature featureY=object.get(0).getFeatureX();
			Feature featureX=object.get(0).getFeatureY();
			for(Integer i=0;i<featureX.getLstOfData().size();i++){
				String dataX=(String)featureX.getLstOfData().get(i);
				 if(uniqueRec.get(dataX)==null){
					 uniqueRec.put(dataX, new ArrayList<Double>());
				 }
				 uniqueRec.get(dataX).add(Double.valueOf((String)featureY.getLstOfData().get(i)));
			 }
		}
		
	return buildObject(uniqueRec,xName,yName);
	}
	
}
