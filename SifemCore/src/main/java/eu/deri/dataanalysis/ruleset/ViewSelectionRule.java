/**
 * 
 */
package eu.deri.dataanalysis.ruleset;

import java.util.List;

import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;
import eu.deri.dataanalysis.vo.QualitativeDT;
import eu.deri.dataanalysis.vo.QuantativeDT;

/**
 * @author swapnil
 * It contains all the rule for finding the a pair for features
 */
public class ViewSelectionRule implements Rule {

	/* (non-Javadoc)
	 * @see eu.deri.dataanalysis.ruleset.Rule#test(eu.deri.dataanalysis.vo.Feature)
	 */
	public Object test(Object object) {
		PairFeature pairFeature=null;
		try{
			List<Feature> lstOfFeature=(List<Feature>)object;
			Feature feature1=lstOfFeature.get(0);
			Feature feature2=lstOfFeature.get(1);
			
			if(feature1!=null && feature2!=null) {
				if(feature1.getStatDataType().isQuantative() && feature2.getStatDataType().isQuantative()){
					pairFeature=new PairFeature();
					QuantativeDT quantative1=(QuantativeDT)feature1.getStatDataType();
					QuantativeDT quantative2=(QuantativeDT)feature2.getStatDataType();
					if (quantative1.isRatio() && quantative2.isRatio()) {
						pairFeature.setFeatureX(feature1.clone());
						pairFeature.setFeatureY(feature2.clone());
					} else if (quantative1.isRatio() && (quantative2.isUnique())) {
						pairFeature.setFeatureX(feature2.clone());
						pairFeature.setFeatureY(feature1.clone());
					} else if (quantative1.isUnique() && quantative2.isRatio()) {
						pairFeature.setFeatureX(feature1.clone());
						pairFeature.setFeatureY(feature2.clone());
					} else if (quantative1.isContinuous() && quantative2.isRatio()) {
						pairFeature.setFeatureX(feature2.clone());
						pairFeature.setFeatureY(feature1.clone());
					} else if (quantative1.isRatio() && quantative2.isContinuous()) {
						pairFeature.setFeatureX(feature1.clone());
						pairFeature.setFeatureY(feature2.clone());
					} else if (quantative1.isContinuous() && quantative2.isContinuous()) {
						pairFeature.setFeatureX(feature1.clone());
						pairFeature.setFeatureY(feature2.clone());
					} else if ((quantative1.isCount() || quantative1.isUnique()) && (quantative2.isCount() || quantative2.isUnique())) {
						pairFeature.setFeatureX(feature1.clone());
						pairFeature.setFeatureY(feature2.clone());
					} else if ((quantative1.isCount() || quantative1.isUnique()) && (quantative2.isRatio() || quantative2.isContinuous())) {
						pairFeature.setFeatureX(feature1.clone());
						pairFeature.setFeatureY(feature2.clone());
					} else if ((quantative1.isRatio() || quantative1.isContinuous()) && (quantative2.isCount() || quantative2.isUnique())) {
						pairFeature.setFeatureX(feature2.clone());
						pairFeature.setFeatureY(feature1.clone());
					}
				} else if(feature1.getStatDataType().isQualitative() && feature2.getStatDataType().isQualitative()){
					//System.out.println("aa");
				} else if(feature1.getStatDataType().isQualitative() && feature2.getStatDataType().isQuantative()){
					//System.out.println("bb");
				} else if(feature1.getStatDataType().isQuantative() && feature2.getStatDataType().isQualitative()){
					//System.out.println("cc");
				}  
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return pairFeature;
	}

}
