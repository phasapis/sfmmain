/**
 * 
 */
package eu.deri.dataanalysis.analyser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.deri.dataanalysis.composer.OperationComposer;
import eu.deri.dataanalysis.util.OperationName;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

/**
 * @author swapnil
 * Combine the operations to be performed on dimensions or features
 */
public class InterpretationAnalyser implements Analyser {

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
		List<PairFeature> lstOfPairOfFeature = (List<PairFeature>) object;
		List<PairFeature> results=new ArrayList<PairFeature>();
		for(PairFeature pairFeature:lstOfPairOfFeature){
			OperationComposer composer=new OperationComposer();
			Feature featureX = pairFeature.getFeatureX();
			Feature featureY = pairFeature.getFeatureY();
			Map<OperationName,Integer> mapOfOpeartionX = featureX.getLstOfOperation();
			Map<OperationName,Integer> mapOfOpeartionY = featureY.getLstOfOperation();
			for(OperationName opernaName:mapOfOpeartionX.keySet()){
				composer.add(opernaName);
			}
			for(OperationName opernaName:mapOfOpeartionY.keySet()){
				composer.add(opernaName);
			}
			pairFeature.setComposer(composer);
			composer.executeRule(pairFeature);
			/*Execute rules on operations*/
			List<PairFeature> lstOfPF = new ArrayList<PairFeature>();
			lstOfPF.add(pairFeature);
			List<PairFeature> obj = (List<PairFeature>) composer.execute(lstOfPF);
			results.addAll(obj);
		}
		 
		return results;
	}

}
