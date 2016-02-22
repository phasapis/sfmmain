/**
 * 
 */
package eu.deri.dataanalysis.analyser;

import java.util.ArrayList;
import java.util.List;

import eu.deri.dataanalysis.ruleset.OperationsRule;
import eu.deri.dataanalysis.ruleset.RuleEngine;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

/**
 * @author swapnil
 * This class fire rules and get set of operations for each feature.
 */
public class OperationAnalyser implements Analyser {

	/* (non-Javadoc)
	 * @see eu.deri.dataanalysis.analyser.Analyser#analyse()
	 */
	public Object analyse() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.deri.dataanalysis.analyser.Analyser#analyse(java.util.List)
	 */
	public Object analyse(Object object) {
		 /* Right now logic is to perform for only Sal and Number and periodic function of year
		  * TODO: Implement  kind of operations can be performed on the basis of
		  * statistical data type*/
		List<PairFeature> lstOfpairFeature = (List<PairFeature>) object;
		try {
			RuleEngine engine = new RuleEngine();
			for (Integer i=0;i<lstOfpairFeature.size();i++) {
				PairFeature pairFeature=lstOfpairFeature.get(i);
				Feature featureX = pairFeature.getFeatureX();
				Feature featureY = pairFeature.getFeatureY();
				List<Feature> lstOfFeature=new ArrayList<Feature>();
				lstOfFeature.add(featureX);
				lstOfFeature.add(featureY);
				engine.fire(OperationsRule.class, lstOfFeature);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstOfpairFeature;
	}


}
