/**
 * 
 */
package eu.deri.dataanalysis.ruleset;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.deri.dataanalysis.util.OperationName;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.QualitativeDT;
import eu.deri.dataanalysis.vo.QuantativeDT;

/**
 * @author swapnil
 *
 */
public class OperationsRule implements Rule {

	/* (non-Javadoc)
	 * @see eu.deri.dataanalysis.ruleset.Rule#test()
	 */
	public Object test(Object object) {
		List<Feature> lstOfFeature=(List<Feature>)object;
		//Feature feature=lstOfFeature.get(0);
		for(Feature feature:lstOfFeature){
			if(feature.getStatDataType().isQuantative()){
				QuantativeDT quantativeDT=(QuantativeDT)feature.getStatDataType();
				feature.setLstOfOperation(quantativeRule(quantativeDT));

			}else if (feature.getStatDataType().isQualitative()){
				QualitativeDT qualitativeDT=(QualitativeDT)feature.getStatDataType();
				feature.setLstOfOperation(qualitativeRule(qualitativeDT));
			}
		}
		return null;
		
	}
	private Map<OperationName,Integer> quantativeRule(QuantativeDT quantativeDT){
		Map<OperationName,Integer> mapOfoperations=new HashMap<OperationName,Integer>();
		if (quantativeDT.isRatio() && quantativeDT.isContinuous() == false) {
			mapOfoperations.put(OperationName.AVERAGE, 1);
			mapOfoperations.put(OperationName.AGGREGATION, 2);
			/*
			 * mapOfoperations.put(OperationName.COEFF_VARIATION,3);
			 * mapOfoperations.put(OperationName.EQUALITY_RATIO,4);
			 * mapOfoperations.put(OperationName.LOG_TRANSFORMATION,5);
			 * mapOfoperations.put(OperationName.PRODUCT_CORRELATION,6);
			 * mapOfoperations.put(OperationName.STD_VARIATION,7);
			 */
		} else if (quantativeDT.isRatio() && quantativeDT.isContinuous()) {
			mapOfoperations.put(OperationName.AVERAGE, 1);
			mapOfoperations.put(OperationName.AGGREGATION, 2);
			mapOfoperations.put(OperationName.DERVIATION, 3);
			/*
			 * mapOfoperations.put(OperationName.COEFF_VARIATION,4);
			 * mapOfoperations.put(OperationName.EQUALITY_RATIO,5);
			 * mapOfoperations.put(OperationName.LOG_TRANSFORMATION,6);
			 * mapOfoperations.put(OperationName.PRODUCT_CORRELATION,7);
			 * mapOfoperations.put(OperationName.STD_VARIATION,8);
			 */
		} else if ((quantativeDT.isRatio() == false || quantativeDT.isRatio()) && quantativeDT.isContinuous() && quantativeDT.isCount()) {
			mapOfoperations.put(OperationName.COUNT, 1);
		} else if (quantativeDT.isContinuous()) {
			mapOfoperations.put(OperationName.DATABEHAVIOUR, 1);
		}
		return mapOfoperations;
	}
	private Map<OperationName,Integer> qualitativeRule(QualitativeDT qualitativeDT){
		Map<OperationName,Integer> mapOfoperations=new HashMap<OperationName,Integer>();
		if(qualitativeDT.isCategorical() && qualitativeDT.isNominal()==true){
			mapOfoperations.put(OperationName.AGGREGATION,1);
		}
		return mapOfoperations;
	}
}
