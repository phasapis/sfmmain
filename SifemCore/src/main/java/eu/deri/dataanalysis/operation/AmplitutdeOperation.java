package eu.deri.dataanalysis.operation;


import java.util.ArrayList;
import java.util.List;

import eu.deri.dataanalysis.util.Fft;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

public class AmplitutdeOperation implements Operation{

	public Object perform(List<PairFeature> object) {
		List<PairFeature> lstOfPairOfFeature=(List<PairFeature>)object;
		for(PairFeature pairFeature:lstOfPairOfFeature){
			Feature featureY=pairFeature.getFeatureY();
			List<Float> lstofVal=new ArrayList<Float>();
			List<Float> lstofAmpl=new ArrayList<Float>();
			boolean isAboveDirection=false;
			List<String> lstOfData=(List)featureY.getLstOfData();
			for(Integer i=0;i<lstOfData.size();i++){
				float val=Float.valueOf(lstOfData.get(i));
				if(i==0){
					lstofVal.add(val);
				} else {
					if(lstofVal.get(i-1)<val){
						isAboveDirection=true;
					}else{
						 float f = lstofVal.get(i-1) * Float.POSITIVE_INFINITY;
						if(lstofVal.get(i-1)>val && isAboveDirection==true && f==Double.POSITIVE_INFINITY){
							isAboveDirection=false;
							lstofAmpl.add(lstofVal.get(i-1));
						}
					}
					lstofVal.add(val);
				}
			}
			featureY.setLstOfData((List)lstofAmpl);
		}
		return lstOfPairOfFeature;
	}
	public static void main(String[] args) {
		Fft fft=new Fft();
		float real[]=new float[16];
		float img[]=new float[16];
		Integer counter=0;
		List<Double> lstOfData=new ArrayList<Double>();
		 for(double t=0.8;t<4;t=t+0.2){
			double yt=Math.exp(-t) * Math.cos((Math.PI*t*2));//Math.cos(Math.PI*t); //Math.exp(-t) * Math.cos((Math.PI*t*2));
			lstOfData.add(yt);
			img[counter]=0.0f;
			counter++;
			//System.out.println(counter);
			System.out.println( t+","+ yt);
		} 
		 
		 /*fft.complexToComplex(1, 16, real, img);
		 for(Integer i=0;i<16;i++){
			 lstOfData.add(Math.sqrt((real[i]*real[i])+(img[i]*img[i])));//+","+img[i]);
		 }*/
		 List<Double> lstofVal=new ArrayList<Double>();
			List<Double> lstofAmpl=new ArrayList<Double>();
			boolean isAboveDirection=true;
		 for(Integer i=0;i<lstOfData.size();i++){
				double val=lstOfData.get(i);
				if(i==0){
					lstofVal.add(val);
				} else {
					if(lstofVal.get(i-1)<val){
						isAboveDirection=true;
					}else{
						 double f = lstofVal.get(i-1) * Double.POSITIVE_INFINITY;
						if(lstofVal.get(i-1)>val && isAboveDirection==true && f==Double.POSITIVE_INFINITY){
							isAboveDirection=false;
							lstofAmpl.add(lstofVal.get(i-1));
						}
					}
					lstofVal.add(val);
				}
			}
		 System.out.println(lstofAmpl);
	}
}
