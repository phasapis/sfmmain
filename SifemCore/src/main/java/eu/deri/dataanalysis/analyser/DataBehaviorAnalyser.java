package eu.deri.dataanalysis.analyser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.deri.dataanalysis.util.Fft;
import eu.deri.dataanalysis.util.OperationName;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

/**
 * 
 * @author swapnil/yaskha
 *
 */
public class DataBehaviorAnalyser implements Analyser {

	public Object analyse() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object analyse(Object object) {
		List<PairFeature> lstOfPairOfFeature=(List<PairFeature>)object;
		for (PairFeature pairFeature : lstOfPairOfFeature) {
			pairFeature.getComposer().getLstOfoperationName().clear();

			/* checking data set behavior(periodic) */
			Feature featureX = pairFeature.getFeatureX();
			Feature featureY = pairFeature.getFeatureY();
			List<Object> lstOfData = featureY.getLstOfData();
			float real[] = new float[lstOfData.size()];
			float img[] = new float[lstOfData.size()];
			List<Double> data = new ArrayList<Double>();
			Integer counter = 0;
			for (Object obj : lstOfData) {
				float val = Float.valueOf(obj.toString());
				real[counter] = val;
				img[counter] = 0.0f;
				counter++;
			}
			
//			for(int i=0; i<real.length; i++) {
//				System.out.println("Real: " + real[i]);
//				System.out.println("Img: " + img[i]);
//			}
			
//			System.out.println(" ******************************************* ");
			Fft fft = new Fft();
			try{
				fft.complexToComplex(1, lstOfData.size(), real, img);
			}catch(Exception e){}
			counter = 0;
//			for(int i=0; i<real.length; i++) {
//				System.out.println("Real: " + real[i]);
//				System.out.println("Img: " + img[i]);
//			}

			for (Integer i = 0; i < lstOfData.size(); i++) {
				data.add((Math.sqrt((real[i] * real[i]) + (img[i] * img[i]))));// +","+img[i]);
				counter++;
			}
			if (analyse(data)) {
				//OperationComposer composer = new OperationComposer();
//				Feature featureX = pairFeature.getFeatureX();
//				Feature featureY = pairFeature.getFeatureY();
				featureY.getLstOfOperation().clear();
				//featureY.getLstOfOperation().put(OperationName.DERVIATION, 1);
				featureY.getLstOfOperation().put(OperationName.AMPLITUDECALCULATION, 1);
				System.out.println("Data set is periodic");
				featureY.setFeature("Periodic");
			} else {
				/* Right now assuming that it is greenwood function will take input from user as reference for function*/
//				Feature featureX = pairFeature.getFeatureX();
//				Feature featureY = pairFeature.getFeatureY();
				featureY.getLstOfOperation().clear();
				featureY.getLstOfOperation().put(OperationName.AMPLITUDECALCULATION, 1);
				System.out.println("Take input from user: (165.4*(10^0.06*x)-1)");
			}
			/* end behavior(periodic) */

		}
		return null;
	}

	
	private boolean analyse(List<Double> data) {
		boolean isFFt = false;
		List<BigDecimal> bigdecimaldata = convertToBigDecimal(data);
		if(bigdecimaldata!=null) {
			
			Collections.sort(bigdecimaldata);
			
			for(int counter=1; counter<bigdecimaldata.size(); counter++) {
//				System.out.println("Counter: " + counter);
//				System.out.println(bigdecimaldata.get(bigdecimaldata.size()-counter).doubleValue());
//				System.out.println(bigdecimaldata.get(bigdecimaldata.size()-(counter+1)).doubleValue());
				if (bigdecimaldata.get(bigdecimaldata.size()-counter).equals(bigdecimaldata.get(bigdecimaldata.size()-(counter+1)))) {
					isFFt = true;
					counter++;
					break;
				}
			}
			
//			System.out.println(bigdecimaldata.get(bigdecimaldata.size()-3).doubleValue());
//			System.out.println(bigdecimaldata.get(bigdecimaldata.size()-4).doubleValue());
//			if (bigdecimaldata.get(bigdecimaldata.size()-3).equals(bigdecimaldata.get(bigdecimaldata.size()-4))) {
//				isFFt = true;
//			}
		}
		return isFFt;
	}

	
	private List<BigDecimal> convertToBigDecimal(List<Double> data) {
		List<BigDecimal> result = new ArrayList<BigDecimal>();
		for(Double d:data){
			result.add(new BigDecimal(d));
		}
		
		return result;
	}

}
