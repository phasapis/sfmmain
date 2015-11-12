package eu.deri.dataanalysis.analyser;

import java.util.List;

import eu.deri.dataanalysis.vo.PairFeature;

public class FunctionAnalyser implements Analyser{

	public Object analyse() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object analyse(Object object) {
		List<PairFeature> lstOfPairOfFeature=(List<PairFeature>)object;
		if (lstOfPairOfFeature.get(0).getFeatureY().getAxis().equalsIgnoreCase("Y")) {
			System.out.println("Distance is:"+Double.valueOf(lstOfPairOfFeature.get(0).getFeatureY().getLstOfData().get(0).toString()));
		}
		return null;
	}

}
