package eu.deri.dataanalysis.composer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eu.deri.dataanalysis.analyser.AmplitudeAnalyser;
import eu.deri.dataanalysis.analyser.Analyser;
import eu.deri.dataanalysis.analyser.AverageAnalyser;
import eu.deri.dataanalysis.analyser.DataBehaviorAnalyser;
import eu.deri.dataanalysis.analyser.DerivativeAnalyser;
import eu.deri.dataanalysis.analyser.FunctionAnalyser;
import eu.deri.dataanalysis.analyser.InterpretationAnalyser;
import eu.deri.dataanalysis.analyser.MaxMinAnalyser;
import eu.deri.dataanalysis.operation.AmplitutdeOperation;
import eu.deri.dataanalysis.operation.AverageOperation;
import eu.deri.dataanalysis.operation.DerivationOperation;
import eu.deri.dataanalysis.operation.DistanceCalculation;
import eu.deri.dataanalysis.ruleset.OperationOrderRule;
import eu.deri.dataanalysis.ruleset.RuleEngine;
import eu.deri.dataanalysis.util.OperationName;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

public class OperationComposer implements Cloneable{
	private List<OperationName> lstOfoperationName;

	public OperationComposer() {
		lstOfoperationName = new ArrayList<OperationName>();
	}

	public OperationComposer add(OperationName operationName) {
		lstOfoperationName.add(operationName);
		return this;
	}
	
	public void executeRule(PairFeature pairFeatures){
		try{
			 List<OperationName> lstOfOpnName=null;
			/* Rule 1: Common operation */
			Set<OperationName> setOfOperation=new HashSet<OperationName>();
			for(OperationName operationName:lstOfoperationName){
				setOfOperation.add(operationName);
			}
			pairFeatures.getComposer().lstOfoperationName.clear();
			pairFeatures.getComposer().lstOfoperationName.addAll(setOfOperation);
			/* Rule 2: Common operation */
			//for(PairFeature pairFeature:lstOfPairFeatures) {
			
			Feature featureX=pairFeatures.getFeatureX();
			Feature featureY=pairFeatures.getFeatureY();
			if (featureX.getStatDataType().isQualitative()
					&& featureY.getStatDataType().isQuantative()) {

			} else if(featureX.getStatDataType().isQuantative()
					&& featureY.getStatDataType().isQualitative()){

			} else if(featureX.getStatDataType().isQuantative()
					&& featureY.getStatDataType().isQuantative()){
				RuleEngine ruleEngine=new RuleEngine();
				lstOfOpnName=(List<OperationName>)ruleEngine.fire(OperationOrderRule.class, pairFeatures);

			} else if(featureX.getStatDataType().isQualitative()
					&& featureY.getStatDataType().isQualitative()){

			}
			pairFeatures.getComposer().lstOfoperationName.clear();
			pairFeatures.getComposer().lstOfoperationName.addAll(lstOfOpnName);
		} catch(Exception e){
			e.printStackTrace();
		}
		/*//}lstOfOpnNmn.add(OperationName.AVERAGE);
			lstOfOpnNmn.add(OperationName.DERVIATION);
			lstOfOpnNmn.add(OperationName.AGGREGATION);*/
		//lstOfoperationName=lstOfOpnNmn;
	}
	
	
	
	public Object execute(List<PairFeature> lstOfPairFeatures){
		for(OperationName operationName:lstOfoperationName){
			if(operationName==OperationName.DERVIATION){
				lstOfPairFeatures= (List<PairFeature>)new DerivationOperation().perform(lstOfPairFeatures);
				Analyser derivAnalyser=new DerivativeAnalyser(); 
				derivAnalyser.analyse(lstOfPairFeatures);
			} else if(operationName==OperationName.AVERAGE){
				Analyser maxminAnalyser=new MaxMinAnalyser(); 
				maxminAnalyser.analyse(lstOfPairFeatures);
				lstOfPairFeatures= (List<PairFeature>)new AverageOperation().perform(lstOfPairFeatures);
				Analyser avgAnalyser=new AverageAnalyser(); 
				avgAnalyser.analyse(lstOfPairFeatures);
			} else if(operationName==OperationName.AGGREGATION){
				//lstOfPairFeatures=(List<PairFeature>)new AggregationOperation().perform(lstOfPairFeatures);
			} else if(operationName==OperationName.DATABEHAVIOUR){
				Analyser dataBehaviourAnalyser=new DataBehaviorAnalyser();
				dataBehaviourAnalyser.analyse(lstOfPairFeatures);
				new InterpretationAnalyser().analyse(lstOfPairFeatures);
				break;
			}  else if(operationName==OperationName.AMPLITUDECALCULATION){
				lstOfPairFeatures=(List<PairFeature>)new AmplitutdeOperation().perform(lstOfPairFeatures);
				new AmplitudeAnalyser().analyse(lstOfPairFeatures);
			} else if(operationName==OperationName.DISTANCECALCULATION){
				lstOfPairFeatures=(List<PairFeature>)new DistanceCalculation().perform(lstOfPairFeatures);
				new FunctionAnalyser().analyse(lstOfPairFeatures);
			} 
		}
		return lstOfPairFeatures;
	}

	public List<OperationName> getLstOfoperationName() {
		return lstOfoperationName;
	}

	public void setLstOfoperationName(List<OperationName> lstOfoperationName) {
		this.lstOfoperationName = lstOfoperationName;
	}
	public Object clone() throws CloneNotSupportedException {
		 return super.clone();
	}
}
