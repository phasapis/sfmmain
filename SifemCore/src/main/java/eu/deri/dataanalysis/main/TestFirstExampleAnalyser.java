package eu.deri.dataanalysis.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.deri.dataanalysis.analyser.AnalyserFactory;
import eu.deri.dataanalysis.analyser.AnalyserPipeline;
import eu.deri.dataanalysis.analyser.CompareAnalyser;
import eu.deri.dataanalysis.analyser.DataTypeAnalyser;
import eu.deri.dataanalysis.extractor.FeatureExtractor;
import eu.deri.dataanalysis.reader.CSVReader;
import eu.deri.dataanalysis.reader.Reader;
import eu.deri.dataanalysis.util.AnalyserType;
import eu.deri.dataanalysis.visualizer.Graph;
import eu.deri.dataanalysis.visualizer.Plot2DGraph;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

public class TestFirstExampleAnalyser {
	public static void main(String[] args) {
		/*Reader r=new CSVReader();
		AnalyserPipeline pipeline=new AnalyserPipeline();
		List<Feature> lstOfFeature=(List<Feature>) r.read("/home/swapnil/swapnil/DERI/DataAnalysis_config/employee.csv");
		Analysis
		pipeline.addAnalyser(AnalyserType.DATATYPE, null);
		pipeline.addAnalyser(AnalyserType.FEATUREVIEW, null);
		pipeline.addAnalyser(AnalyserType.PROJECTION, null);
		pipeline.addAnalyser(AnalyserType.OPERATION, null);
		
		pipeline.addAnalyser(AnalyserType.INTERPRETATION, null);
	//	pipeline.addAnalyser(AnalyserType.DATAANALYSER, null);
		pipeline.execute(lstOfFeature);*/
		 
		
		//pipeline=new AnalyserPipeline();
		
		//pipeline.execute(lstOfExtractedFeature);
		
		/*second case for periodic*/
		Reader r=new CSVReader();
		List<Feature> lstOfFeature=(List<Feature>) r.read("C:/workspace/sifem/DataAnalysis/content/DisplacementGraphPoints.csv");
		AnalyserPipeline pipeline=new AnalyserPipeline();
		pipeline.addAnalyser(AnalyserType.DATATYPE, null);
		pipeline.addAnalyser(AnalyserType.FEATUREVIEW, null);
		pipeline.addAnalyser(AnalyserType.PROJECTION, null);
		pipeline.addAnalyser(AnalyserType.OPERATION, null);
		pipeline.addAnalyser(AnalyserType.INTERPRETATION, null);
		pipeline.addAnalyser(AnalyserType.MAXMINANALYSER, null);
		//pipeline.addAnalyser(AnalyserType.DATAANALYSER, null);
		pipeline.execute(lstOfFeature);
	
		/*third case for greenwood*/
//		 	Reader r=new CSVReader();
//			List<Feature> lstOfFeature=(List<Feature>) r.read("C:/workspace/sifem/DataAnalysis/content/GreenwoodGraphPoints.csv");
//			AnalyserPipeline pipeline=new AnalyserPipeline();
//			pipeline.addAnalyser(AnalyserType.DATATYPE, null);
//			pipeline.addAnalyser(AnalyserType.FEATUREVIEW, null);
//			pipeline.addAnalyser(AnalyserType.COMPAREANALYSER, new CompareAnalyser("165.4*(10^(0.06*x)-1)",35,0.03465,0.1));
//			pipeline.addAnalyser(AnalyserType.FUNCTIONANALYSER, null);
//			 pipeline.execute(lstOfFeature);
		
		/*third case for maxima and minima*/
//		Reader r=new CSVReader();
//		List<Feature> lstOfFeature=(List<Feature>) r.read("C:/workspace/sifem/DataAnalysis/content/maxmin.csv");
//		AnalyserPipeline pipeline=new AnalyserPipeline();
//		pipeline.addAnalyser(AnalyserType.DATATYPE, null);
//		pipeline.addAnalyser(AnalyserType.FEATUREVIEW, null);
//		pipeline.addAnalyser(AnalyserType.PROJECTION, null);
//		pipeline.addAnalyser(AnalyserType.EXTREMAANALYSER,null);
//		//pipeline.addAnalyser(AnalyserType.COMPAREANALYSER, new CompareAnalyser("165.4*(10^(0.06*x)-1)",35,0.3,0.1));
//		//pipeline.addAnalyser(AnalyserType.FUNCTIONANALYSER, null);
//		 pipeline.execute(lstOfFeature);
	}
}
