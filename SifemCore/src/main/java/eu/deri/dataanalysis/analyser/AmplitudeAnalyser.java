package eu.deri.dataanalysis.analyser;

import java.util.ArrayList;
import java.util.List;

import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

public class AmplitudeAnalyser implements Analyser {

	public Object analyse() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object analyse(Object object) {
		List<PairFeature> lstOfPairOfFeature=(List<PairFeature>)object;
		List<Double> lstOfData=new ArrayList<Double>();
		if (lstOfPairOfFeature.get(0).getFeatureX().getAxis().equalsIgnoreCase("X")) {
			Feature fetureY = lstOfPairOfFeature.get(0).getFeatureY();
			Feature fetureX = lstOfPairOfFeature.get(0).getFeatureX();
			boolean isNotDamping=true;
			for (Integer i = 0; i < fetureY.getLstOfData().size(); i++) {
				double val=(Double.valueOf(fetureY.getLstOfData().get(i).toString()));
				for (Integer j = 0; j < lstOfData.size(); j++) {
					if(!(lstOfData.get(j).intValue()<val)){
						isNotDamping=false;
						break;
					}
					
				}
				lstOfData.add(val);
				if(isNotDamping==false)break;
			}
			if(isNotDamping)System.out.println("Data set has damping");
			System.out.println("Amplitudes are "+ fetureY.getLstOfData());
		}
		return null;
	}

}
