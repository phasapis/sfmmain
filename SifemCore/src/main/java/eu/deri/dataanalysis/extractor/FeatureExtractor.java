package eu.deri.dataanalysis.extractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.deri.dataanalysis.ruleset.ViewSelectionRule;
import eu.deri.dataanalysis.ruleset.RuleEngine;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;


/**
 * 
 * @author swapnil/yaskha
 *
 */
public class FeatureExtractor implements Extractor{

	public List<PairFeature> extract(Object object) {
		
		List<PairFeature> result=new ArrayList<PairFeature>();
		List<PairFeature> lstOfPairFeature=(List<PairFeature>)object;
		
//		for (Integer i = 0; i < lstOfPairFeature.size(); i++) {
//			PairFeature pairFeature = lstOfPairFeature.get(i);
//			Feature featureX=pairFeature.getFeatureX();
//			Feature featureY=pairFeature.getFeatureY();
//			if(featureX!=null && featureY!=null){
//				if (featureX.getAttrName().equalsIgnoreCase("Age") && featureY.getAttrName().equalsIgnoreCase("Salary")) {
//					result.add(pairFeature);
//				} else if (featureY.getAttrName().equalsIgnoreCase("Age") && featureX.getAttrName().equalsIgnoreCase("Salary")) {
//					pairFeature.setFeatureX(featureY);
//					pairFeature.setFeatureY(featureX);
//					result.add(pairFeature);
//				}
//				if (featureX.getAttrName().equalsIgnoreCase("Department") && featureY.getAttrName().equalsIgnoreCase("Salary")) {
//					result.add(pairFeature);
//				} else if (featureY.getAttrName().equalsIgnoreCase("Department") && featureX.getAttrName().equalsIgnoreCase("Salary")) {
//					pairFeature.setFeatureX(featureY);
//					pairFeature.setFeatureY(featureX);
//					result.add(pairFeature);
//				}
//				if (featureX.getAttrName().equalsIgnoreCase("sequence") && featureY.getAttrName().equalsIgnoreCase("function")) {
//					result.add(pairFeature);
//				} else if (featureY.getAttrName().equalsIgnoreCase("function") && featureX.getAttrName().equalsIgnoreCase("sequence")) {
//					pairFeature.setFeatureX(featureY);
//					pairFeature.setFeatureY(featureX);
//					result.add(pairFeature);
//				} 
//				
//				if (featureX.getAttrName().equalsIgnoreCase("x") && featureY.getAttrName().equalsIgnoreCase("y")) {
//					result.add(pairFeature);
//				} else if (featureY.getAttrName().equalsIgnoreCase("y") && featureX.getAttrName().equalsIgnoreCase("x")) {
//					pairFeature.setFeatureX(featureY);
//					pairFeature.setFeatureY(featureX);
//					result.add(pairFeature);
//				} 
//				if (featureX.getAttrName().equalsIgnoreCase("position") && featureY.getAttrName().equalsIgnoreCase("frequency")) {
//					result.add(pairFeature);
//				} else if (featureY.getAttrName().equalsIgnoreCase("frequency") && featureX.getAttrName().equalsIgnoreCase("position")) {
//					pairFeature.setFeatureX(featureY);
//					pairFeature.setFeatureY(featureX);
//					result.add(pairFeature);
//				}if (featureX.getAttrName().equalsIgnoreCase("velocity") && featureY.getAttrName().equalsIgnoreCase("cavity")) {
//					result.add(pairFeature);
//				} else if (featureY.getAttrName().equalsIgnoreCase("cavity") && featureX.getAttrName().equalsIgnoreCase("velocity")) {
//					pairFeature.setFeatureX(featureY);
//					pairFeature.setFeatureY(featureX);
//					result.add(pairFeature);
//				}
//				 
//			}
//		}
		
		result = lstOfPairFeature;
		
		return result; 
	}

	public void extract() {
		

	}

}
