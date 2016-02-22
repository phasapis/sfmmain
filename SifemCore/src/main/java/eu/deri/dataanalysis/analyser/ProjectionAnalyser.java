package eu.deri.dataanalysis.analyser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.deri.dataanalysis.extractor.FeatureExtractor;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

/**
 * @author swapnil
 * Finding out the dimensions
 */
public class ProjectionAnalyser implements Analyser {

	public Object analyse() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object analyse(Object object) {
		List<PairFeature> lstOfPairWise = (List<PairFeature>) object;
		FeatureExtractor extractor = new FeatureExtractor();
		List<PairFeature> lstOfFinalPair = extractor.extract(lstOfPairWise);
		return lstOfFinalPair;
	}

}
