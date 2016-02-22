package eu.deri.dataanalysis.analyser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

public class DerivativeAnalyser implements Analyser {

	public Object analyse() {
		
		return null;
	}
	

	public Object analyse(Object object) {
		analyseDerivation(object);
		 
		return null;
	}

	private void analyseDerivation(Object object){
		List<PairFeature> lstOfPairOfFeature=(List<PairFeature>)object;
		List<Double> lstOfData=new ArrayList<Double>();
		List<String> lstOfNegPlaces=new ArrayList<String>();
		boolean isAllPositive=true;
		if (lstOfPairOfFeature.get(0).getFeatureX().getAxis().equalsIgnoreCase("X")) {
			Feature fetureY = lstOfPairOfFeature.get(0).getFeatureY();
			Feature fetureX = lstOfPairOfFeature.get(0).getFeatureX();
			
			for (Integer i = 0; i < fetureY.getLstOfData().size(); i++) {
				for (Integer j = 0; j < lstOfData.size(); j++) {
					double val=(Double.valueOf(fetureY.getLstOfData().get(i).toString()));
					if ((val <= 0.0)) {//lstOfData.get(j)
						isAllPositive = false;
						lstOfNegPlaces.add((String) lstOfPairOfFeature.get(0).getFeatureX().getLstOfData().get(j));
					}
				}
				lstOfData.add((Double) Double.valueOf(fetureY.getLstOfData().get(i).toString()));
				
			}
		} else if (lstOfPairOfFeature.get(0).getFeatureY().getAxis().equalsIgnoreCase("X")) {
			Feature fetureY = lstOfPairOfFeature.get(0).getFeatureY();
			for (Integer i = 0; i < fetureY.getLstOfData().size(); i++) {
				for (Integer j = 0; j < lstOfData.size(); j++) {
					double val=(Double.valueOf(fetureY.getLstOfData().get(i).toString()));
					if (( val  <= 0.0)) {
						isAllPositive = false;
						lstOfNegPlaces.add((String) lstOfPairOfFeature.get(0).getFeatureX().getLstOfData().get(j));
					}
				}
				lstOfData.add((Double) Double.valueOf(fetureY.getLstOfData().get(i).toString()));
			}
		}
		String yName=lstOfPairOfFeature.get(0).getFeatureY().getAttrName();
		String xName=lstOfPairOfFeature.get(0).getFeatureX().getAttrName();
		
		//analyseDerivation(lstOfNegPlaces,isAllPositive);
		if(!isAllPositive){
			System.out.println("As "+xName+" is increasing, "+yName+" is fluctating");
		}
		if(lstOfNegPlaces.size()==lstOfPairOfFeature.size()){
			System.out.println("As "+xName+" is increasing, "+yName+" is down all the time");
		}
		if(isAllPositive){
			System.out.println("As "+xName+" is increasing, "+yName+" is all positive");
		}
		
	}

	
}
