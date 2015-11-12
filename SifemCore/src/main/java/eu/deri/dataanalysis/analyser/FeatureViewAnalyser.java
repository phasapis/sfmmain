package eu.deri.dataanalysis.analyser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eu.deri.dataanalysis.ruleset.RuleEngine;
import eu.deri.dataanalysis.ruleset.ViewSelectionRule;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

/**
 * @author swapnil
 * It helps to create view and assign axis to each feature
 */
public class FeatureViewAnalyser implements Analyser{

	public Object analyse() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object analyse(Object object) {
		 
		RuleEngine ruleEngine=new RuleEngine();
		List<Feature> lstOfFeatures=(List<Feature>)object;
		List<PairFeature> lstOfExtractedFeature=new ArrayList<PairFeature>();
		try{
			for(Integer i=0;i<lstOfFeatures.size();i++) {
				Feature feature=lstOfFeatures.get(i);
				for(Feature featureTemp:lstOfFeatures) {
					if (!featureTemp.getAttrName().equalsIgnoreCase(feature.getAttrName())) {
						List<Feature> lstOfFeature=new ArrayList<Feature>();
						lstOfFeature.add(feature);
						lstOfFeature.add(featureTemp);
						PairFeature pair=(PairFeature)ruleEngine.fire(ViewSelectionRule.class, lstOfFeature);
						if(pair!=null && (!lstOfExtractedFeature.contains(pair))) {
							lstOfExtractedFeature.add(pair);
						}
					}
				}
			}
			//lstOfExtractedFeature.addAll(mapOfFeature.keySet());
		}catch(Exception e){
			e.printStackTrace();
		}
//		System.out.println("Extracted Features:" + lstOfExtractedFeature.size());
		return lstOfExtractedFeature;
	}

}
