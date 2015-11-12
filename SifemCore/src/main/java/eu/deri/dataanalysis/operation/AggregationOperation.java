package eu.deri.dataanalysis.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.deri.dataanalysis.vo.Aggregate;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;
import eu.deri.dataanalysis.vo.QualitativeDT;
import eu.deri.dataanalysis.vo.QuantativeDT;

public class AggregationOperation implements Operation {

	public Object perform(List<PairFeature> object) {
		List<Map<String,Aggregate>> lstOfMap=new ArrayList<Map<String,Aggregate>>();
		List<PairFeature> lstOfFeature=new ArrayList<PairFeature>();
		for (Integer i = 0; i < object.size(); i++) {
			Feature featureX=object.get(i).getFeatureX();
			Feature featureY=object.get(i).getFeatureY();
			boolean isGroupBy=false;
			if (featureX.getStatDataType().isQualitative() && featureY.getStatDataType().isQualitative()) {
				QualitativeDT qualitativeX = (QualitativeDT) featureX.getStatDataType();
				QualitativeDT qualitativeY = (QualitativeDT) featureY.getStatDataType();
				if (qualitativeX.isCategorical() && qualitativeY.isCategorical()) {
					isGroupBy = true;
				} 
				Map<String,Aggregate> result=groupBy(object,featureX,featureY);
				lstOfMap.add(result);
			} else if (featureX.getStatDataType().isQuantative() && featureY.getStatDataType().isQuantative()) {
				QualitativeDT qualitativeX = (QualitativeDT) featureX.getStatDataType();
				QuantativeDT quantitativeY = (QuantativeDT) featureY.getStatDataType();
				if (qualitativeX.isCategorical() && quantitativeY.isContinuous()) {
					isGroupBy = true;
				}
				Map<String,Aggregate> result=groupBy(object,featureX,featureY);
				lstOfMap.add(result);
			}
			buildObject(lstOfFeature,lstOfMap,featureX.getAttrName(),featureY.getAttrName());
		}
		return lstOfFeature;
	}
	private List<PairFeature> buildObject(List<PairFeature> lstOfFeature,List<Map<String,Aggregate>> lstOfMap,String xName,String yName){
		PairFeature pairFeat=new PairFeature();
		Feature featureX=new Feature();
		Feature featureY=new Feature();
		featureX.setAttrName(xName);
		featureY.setAttrName(yName);
		for(Integer i=0;i<lstOfMap.size();i++){
			Map<String,Aggregate> mapOf=lstOfMap.get(i);
			for(String key:mapOf.keySet()){
				featureX.getLstOfData().add(key);
				Aggregate aggregate=mapOf.get(key);
				featureY.getLstOfData().add(aggregate);
			}
			pairFeat.setFeatureX(featureX);
			pairFeat.setFeatureX(featureY);
			lstOfFeature.add(pairFeat);
		}
		return lstOfFeature;
	}
	private Map<String,Aggregate> groupBy(List<PairFeature> object,Feature featureX,Feature featureY){
		Map<String,List<Double>> uniqueRec=new LinkedHashMap<String,List<Double>>();
		for (Integer i = 0; i < featureX.getLstOfData().size(); i++) {
			String dataX = (String) featureX.getLstOfData().get(i);
			if (uniqueRec.get(dataX) == null) {
				uniqueRec.put(dataX, new ArrayList<Double>());
			}
			uniqueRec.get(dataX).add(Double.valueOf((String) featureY.getLstOfData().get(i)));
		}
		return aggregate(uniqueRec);
	}
	private Map<String,Aggregate> aggregate(Map<String,List<Double>> uniqueRec){
		Map<String,Aggregate> results=new LinkedHashMap<String,Aggregate>();
		Set<String> setOfKeys = uniqueRec.keySet();
		for(String key:setOfKeys){
			double sum=0.0;
			double avg=0.0;
			double total=0.0;
			double max=0.0;
			double min=0.0;
			List<Double> lstOfValues = uniqueRec.get(key);
			for(Double val:lstOfValues){
				sum+=val;
			}
			total=lstOfValues.size();
			avg=sum/total;
			Collections.sort(lstOfValues);
			min=lstOfValues.get(0);
			max=lstOfValues.get(lstOfValues.size()-1);
			results.put(key, new Aggregate(sum, avg, max, min, total));
		}
		return results;
	}
}
