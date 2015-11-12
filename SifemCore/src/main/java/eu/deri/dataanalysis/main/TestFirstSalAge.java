package eu.deri.dataanalysis.main;

import java.util.List;
import java.util.Map;

import eu.deri.dataanalysis.extractor.Extractor;
import eu.deri.dataanalysis.extractor.FeatureExtractor;
import eu.deri.dataanalysis.reader.CSVReader;
import eu.deri.dataanalysis.reader.Reader;
import eu.deri.dataanalysis.visualizer.Graph;
import eu.deri.dataanalysis.visualizer.Plot2DGraph;

public class TestFirstSalAge {

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		 Reader r=new CSVReader();
		 String x="";
		 String y="";
		 Map<String,List<Object>> inputData=(Map<String,List<Object>>)
		 r.read("/home/swapnil/swapnil/DERI/DataAnalysis_config/employee.csv");
		
		 FeatureExtractor extractor=new FeatureExtractor();
		 /*Map<String,Double> mapOfFeature=extractor.extract(inputData.keySet());
		 for(String key:mapOfFeature.keySet()){
			 List<Object> lstOfdata= inputData.get(key);
			 if(key.equalsIgnoreCase("Age")){
				 for(Object val:lstOfdata){
					 x+=val+",";
				 }
			 } else if(key.equalsIgnoreCase("Salary")){
				 for(Object val:lstOfdata){
					 y+=val+",";
				 }
			 }
		 }*/
		 x=x.substring(0, x.length()-1);
		 y=y.substring(0, y.length()-1);
		 
		 Graph plot2D=new Plot2DGraph();
		 
		 plot2D.draw(x,y,"Age","Salary");
	}

}
