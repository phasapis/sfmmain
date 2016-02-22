package eu.deri.dataanalysis.analyser;

import java.util.Collections;
import java.util.List;

import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

public class MaxMinAnalyser implements Analyser{

	public Object analyse() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object analyse(Object object) {
		
		maxMinAnalyser(object);
		return object;
	}
	
	private void maxMinAnalyser(Object object){
		
		List<PairFeature> lstOfPairOfFeature=(List<PairFeature>)object;
		Feature featureY = lstOfPairOfFeature.get(0).getFeatureY();
		Feature featureX = lstOfPairOfFeature.get(0).getFeatureX();
		String yName = featureY.getAttrName();
		String xName = featureX.getAttrName();
		List<Double> lstOfValY = (List) featureY.getLstOfData();
		 
		 Collections.sort(lstOfValY);
		 String yMinima = lstOfValY.get(0) + "";
		 String yMaxima = lstOfValY.get(lstOfValY.size()-1) + "";
		 
		 featureY.setMinima(yMinima);
		 featureY.setMaxima(yMaxima);
		 
		 
		 List<Double> lstOfValX = (List) featureX.getLstOfData();
		 
		 Collections.sort(lstOfValX);
		 String xMinima = lstOfValX.get(0) + "";
		 String xMaxima = lstOfValX.get(lstOfValX.size()-1) + "";
		 
		 featureX.setMinima(xMinima);
		 featureX.setMaxima(xMaxima);
		 
//		 System.out.println("Y Lowest "+yName+" is:-" + yMinima);
//		 System.out.println("Y Highest "+yName+" is:-" + yMaxima);
//		 System.out.println("X Lowest "+xName+" is:-" + xMinima);
//		 System.out.println("X Highest "+xName+" is:-" + xMaxima);
	}
	
}
