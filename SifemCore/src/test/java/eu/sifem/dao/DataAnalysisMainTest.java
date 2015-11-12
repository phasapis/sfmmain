package eu.sifem.dao;

import java.util.List;

import org.testng.annotations.Test;

import eu.deri.dataanalysis.analyser.AnalyserPipeline;
import eu.deri.dataanalysis.analyser.CompareAnalyser;
import eu.deri.dataanalysis.util.AnalyserType;
import eu.deri.dataanalysis.vo.Feature;
import eu.sifem.utiltest.DataAnalysisTestCases;


/**
 * 
 * @author yaskha
 *
 */

@Test(groups= { "default" })
public class DataAnalysisMainTest {
	
	/**
	 * Test case for displacement (periodic)
	 */
	@Test(groups= { "default" },enabled=true)
	public void displacementGraphPointTest() {
				
		List<Feature> lstOfFeature1 = DataAnalysisTestCases.getDisplacementRealTestCase();
		List<Feature> lstOfFeature2 = DataAnalysisTestCases.getDisplacementTestCase();
		List<Feature> lstOfFeature3 = DataAnalysisTestCases.getDisplacementTestCase3();
		
		AnalyserPipeline pipeline=new AnalyserPipeline();
		pipeline.addAnalyser(AnalyserType.DATATYPE, null);
		pipeline.addAnalyser(AnalyserType.FEATUREVIEW, null);
		pipeline.addAnalyser(AnalyserType.PROJECTION, null);
		pipeline.addAnalyser(AnalyserType.OPERATION, null);
		pipeline.addAnalyser(AnalyserType.EXTREMAANALYSER, null);
		pipeline.addAnalyser(AnalyserType.INTERPRETATION, null);
//		pipeline.addAnalyser(AnalyserType.MAXMINANALYSER, null);
//		pipeline.addAnalyser(AnalyserType.AVERAGEANALYSER, null);
		
//		pipeline.execute(lstOfFeature1);
//		pipeline.execute(lstOfFeature2);
		pipeline.execute(lstOfFeature3);
		
	}
	
	
	
	/**
	 * Test case for Greenwood
	 */
	@Test(groups= { "default" },enabled=false)
	public void greenwoodGraphPointTest() {
				
		List<Feature> lstOfFeature1 = DataAnalysisTestCases.getGreenwoodRealTestCase();
		List<Feature> lstOfFeature2 = DataAnalysisTestCases.getGreenwoodTestCase();
		
		AnalyserPipeline pipeline=new AnalyserPipeline();
		pipeline.addAnalyser(AnalyserType.DATATYPE, null);
		pipeline.addAnalyser(AnalyserType.FEATUREVIEW, null);
//		pipeline.addAnalyser(AnalyserType.EXTREMAANALYSER,null);
		pipeline.addAnalyser(AnalyserType.COMPAREANALYSER, new CompareAnalyser("165.4*(10^(0.06*x)-1)",35,0.03465,0.1));
		pipeline.addAnalyser(AnalyserType.FUNCTIONANALYSER, null);
		
//		pipeline.execute(lstOfFeature1);
		pipeline.execute(lstOfFeature2);
		
	}
	
	
	
	/**
	 * Test case for Maxima & Minima
	 */
	@Test(groups= { "default" },enabled=false)
	public void maximaMinimaTest() {
				
		List<Feature> lstOfFeature = DataAnalysisTestCases.getTestCase1();
		
		AnalyserPipeline pipeline=new AnalyserPipeline();
		pipeline.addAnalyser(AnalyserType.DATATYPE, null);
		pipeline.addAnalyser(AnalyserType.FEATUREVIEW, null);
		pipeline.addAnalyser(AnalyserType.PROJECTION, null);
		pipeline.addAnalyser(AnalyserType.EXTREMAANALYSER,null);
		pipeline.addAnalyser(AnalyserType.AVERAGEANALYSER,null);
		//pipeline.addAnalyser(AnalyserType.COMPAREANALYSER, new CompareAnalyser("165.4*(10^(0.06*x)-1)",35,0.3,0.1));
		//pipeline.addAnalyser(AnalyserType.FUNCTIONANALYSER, null);
		 
		pipeline.execute(lstOfFeature);
		
	}
	
	
	/**
	 * Test case for employee data
	 */
	@Test(groups= { "default" },enabled=false)
	public void employeeTest() {
				
		List<Feature> lstOfFeature = DataAnalysisTestCases.getTestCase2();
		
		AnalyserPipeline pipeline=new AnalyserPipeline();
		
		pipeline.addAnalyser(AnalyserType.DATATYPE, null);
		pipeline.addAnalyser(AnalyserType.FEATUREVIEW, null);
		pipeline.addAnalyser(AnalyserType.PROJECTION, null);
		pipeline.addAnalyser(AnalyserType.OPERATION, null);
		
		pipeline.addAnalyser(AnalyserType.INTERPRETATION, null);
	//	pipeline.addAnalyser(AnalyserType.DATAANALYSER, null);
		
		pipeline.execute(lstOfFeature);
		
	}
	
	
	/**
	 * Test case for periodic data
	 */
	@Test(groups= { "default" },enabled=false)
	public void periodicTest() {
				
		List<Feature> lstOfFeature = DataAnalysisTestCases.getTestCase3();
		
		AnalyserPipeline pipeline=new AnalyserPipeline();
		
		pipeline.addAnalyser(AnalyserType.DATATYPE, null);
		pipeline.addAnalyser(AnalyserType.FEATUREVIEW, null);
		pipeline.addAnalyser(AnalyserType.PROJECTION, null);
		pipeline.addAnalyser(AnalyserType.OPERATION, null);
		pipeline.addAnalyser(AnalyserType.EXTREMAANALYSER, null);
		pipeline.addAnalyser(AnalyserType.INTERPRETATION, null);
	//	pipeline.addAnalyser(AnalyserType.DATAANALYSER, null);
		
		pipeline.execute(lstOfFeature);
		
	}

}
