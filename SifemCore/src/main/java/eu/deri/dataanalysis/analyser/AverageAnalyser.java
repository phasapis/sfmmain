package eu.deri.dataanalysis.analyser;

import java.util.List;

import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

public class AverageAnalyser implements Analyser{

	public Object analyse() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object analyse(Object object) {
		averageAnalysis(object);
		return null;
	}
	private void averageAnalysis(Object object){
		List<PairFeature> lstOfPairOfFeature=(List<PairFeature>)object;
		if (lstOfPairOfFeature.get(0).getFeatureX().getAxis().equalsIgnoreCase("X")) {
			Feature fetureY = lstOfPairOfFeature.get(0).getFeatureY();
			Feature fetureX = lstOfPairOfFeature.get(0).getFeatureX();
			double total=0.0;
			for (Integer i = 0; i < fetureY.getLstOfData().size(); i++) {
				System.out.println("Average "+fetureY.getAttrName()+" for each "+fetureX.getAttrName()+"-"+ fetureX.getLstOfData().get(i)+" : "+fetureY.getLstOfData().get(i));
//				total=total+(Double)fetureY.getLstOfData().get(i);
				Double fYDouble = Double.parseDouble(fetureY.getLstOfData().get(i).toString());
				total = total + fYDouble;
				
			}
			System.out.println("Avererage of "+fetureY.getAttrName()+" is :"+(total/fetureY.getLstOfData().size()));
			total=0.0;
			for (Integer i = 0; i < fetureY.getLstOfData().size(); i++) {
				total+=Double.valueOf(fetureY.getLstOfData().get(i).toString());
			}
			double avg=total/fetureY.getLstOfData().size();
			for (Integer i = 0; i < fetureY.getLstOfData().size(); i++) {
				double val=Double.valueOf(fetureY.getLstOfData().get(i).toString());
				if(val<avg){
					System.out.println( fetureX.getLstOfData().get(i) +" is "+fetureX.getAttrName() +" is below average");
				} else if(val>avg){
					System.out.println(fetureX.getLstOfData().get(i) +" is "+fetureX.getAttrName() +" is above average");
				} else if(val==avg){
					System.out.println(fetureX.getLstOfData().get(i) +" is "+fetureX.getAttrName() +" is equal average");
				}
			}
			
		}
	}
}
