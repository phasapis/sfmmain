package eu.deri.dataanalysis.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

public class DerivationOperation implements Operation {

	 
	private List<PairFeature>  buildObject(Map<String,Double> uniqueRec,String xName,String yName){
		PairFeature pairFeat=new PairFeature();
		List<PairFeature> lstOfFeature=new ArrayList<PairFeature>();
		//Set<String> setOfKey=uniqueRec.keySet();
		Feature featureX=new Feature();
		Feature featureY=new Feature();
		featureX.setAttrName(xName);
		featureY.setAttrName(yName);
		Set<String> keys=uniqueRec.keySet();
		for(String key:keys){
			featureX.getLstOfData().add(key);
			featureY.getLstOfData().add(uniqueRec.get(key));
		}
		pairFeat.setFeatureX(featureX);
		pairFeat.setFeatureY(featureY);
		lstOfFeature.add(pairFeat);
		return lstOfFeature;
	}

	public Object perform(List<PairFeature> object) {
		Map<String,Double> uniqueRec=new TreeMap<String,Double>();
		String xName=object.get(0).getFeatureX().getAttrName();
		String yName=object.get(0).getFeatureY().getAttrName();
		if(object.get(0).getFeatureX().getAxis().equalsIgnoreCase("X")){
			Feature featureY=object.get(0).getFeatureY();
			Feature featureX=object.get(0).getFeatureX();
			for(Integer i=0;i<featureX.getLstOfData().size()-1;i++){
				double xh=(double)((Double.valueOf(featureX.getLstOfData().get(i).toString())) + (Double.valueOf(featureX.getLstOfData().get(i+1).toString())));
				double fxhx=(double)((Double.valueOf(featureY.getLstOfData().get(i+1).toString()))-(Double.valueOf(featureY.getLstOfData().get(i).toString())));
				double deriv=fxhx/xh;
				String key=featureX.getLstOfData().get(i)+"-"+featureX.getLstOfData().get(i+1);
				uniqueRec.put(key, deriv); 
			}
		} else if(object.get(0).getFeatureY().getAxis().equalsIgnoreCase("X")){
			Feature featureY=object.get(0).getFeatureX();
			Feature featureX=object.get(0).getFeatureY();
			
			for(Integer i=0;i<featureX.getLstOfData().size()-1;i++){
				double xh=(double)((Double.valueOf(featureX.getLstOfData().get(i).toString())) + (Double.valueOf(featureX.getLstOfData().get(i+1).toString())));
				double fxhx=(double)((Double.valueOf(featureY.getLstOfData().get(i+1).toString()))-(Double.valueOf(featureY.getLstOfData().get(i).toString())));
				double deriv=fxhx/xh;
				String key=featureX.getLstOfData().get(i)+"-"+featureX.getLstOfData().get(i+1);
				uniqueRec.put(key, deriv); 
			}
		}
		
		return buildObject(uniqueRec,xName,yName);
	}
}
