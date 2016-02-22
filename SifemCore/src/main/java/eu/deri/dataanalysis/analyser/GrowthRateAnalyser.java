package eu.deri.dataanalysis.analyser;

import java.util.List;

import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

public class GrowthRateAnalyser implements Analyser {

	public Object analyse() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object analyse(Object object) {
		List<PairFeature> lstOfPairOfFeature=(List<PairFeature>)object;
		if (lstOfPairOfFeature.get(0).getFeatureX().getAxis().equalsIgnoreCase("X")) {
			Feature featureY = lstOfPairOfFeature.get(0).getFeatureY();
			Feature featureX = lstOfPairOfFeature.get(0).getFeatureX();
			System.out.println(""+featureX.getAttrName()+"");
			for (Integer i = 0; i < featureY.getLstOfData().size(); i++) {
				double xh=(double)((Double.valueOf(featureX.getLstOfData().get(i).toString())) + (Double.valueOf(featureX.getLstOfData().get(i+1).toString())));
				double fxhx=(double)((Double.valueOf(featureY.getLstOfData().get(i+1).toString()))-(Double.valueOf(featureY.getLstOfData().get(i).toString())));
				double deriv=fxhx/xh;
				double percentage=deriv/(Double.valueOf(featureX.getLstOfData().get(i).toString()))*100;
				if(featureY.getLstOfData().size()<=i+1)
					System.out.println(featureY.getLstOfData().get(i+1)+""+ percentage+" is greater than "+featureY.getLstOfData().get(i));
			}
		}
		 
		
		return null;
	}

}
