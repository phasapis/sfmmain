package eu.sifem.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import eu.deri.dataanalysis.analyser.AnalyserPipeline;
import eu.deri.dataanalysis.util.AnalyserType;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;
import eu.sifem.service.dao.IDataAnalysisDAOService;

/**
 * 
 * @author yaskha
 * 
 */
//FIXME Remove sysos and change location.
@Service(value = "dataAnalysisDAOService")
public class DataAnalysisDAOService implements IDataAnalysisDAOService {

	
	@Override
	public String extractGraphFeaturesService(Map<String, List<String>> xyMap) {

		StringBuilder outputRDF = new StringBuilder();

		List<Feature> lstOfFeature = new ArrayList<Feature>();

		Set<String> xySet = xyMap.keySet();
		for (String s : xySet) {
			
			System.out.println("[DAO-S] Feature: " + s);
			
			Feature feature = new Feature();
			feature.setAttrName(s);

			List<Object> lstOfval = new ArrayList<>();
			for (String values : xyMap.get(s)) {
				values = values.toString().replace("[", "");
				values = values.toString().replace("]", "");
				values = values.toString().replace("'", "");
				lstOfval.add(values);
			}
			feature.setLstOfData(lstOfval);

			lstOfFeature.add(feature);
		}

		AnalyserPipeline pipeline = new AnalyserPipeline();
		pipeline.addAnalyser(AnalyserType.DATATYPE, null);
		pipeline.addAnalyser(AnalyserType.FEATUREVIEW, null);
		pipeline.addAnalyser(AnalyserType.PROJECTION, null);
		pipeline.addAnalyser(AnalyserType.OPERATION, null);
		pipeline.addAnalyser(AnalyserType.EXTREMAANALYSER, null);
		pipeline.addAnalyser(AnalyserType.INTERPRETATION, null);
//		pipeline.addAnalyser(AnalyserType.MAXMINANALYSER, null);
				
		List<PairFeature> pairFeatureList =  pipeline.execute(lstOfFeature);
				
		for(PairFeature pairFeature : pairFeatureList) {
			Feature featureX = pairFeature.getFeatureX();
			outputRDF.append(":DataViewBMMagnitude :hasDimensionX :" + featureX.getAttrName() + ". &#10; &#10; ");
//			outputRDF += "Feature Axis: " + featureX.getAxis() + "\n";
			outputRDF.append(":DataViewBMMagnitude :hasMinimumX '" + featureX.getMinima() + "' . &#10; &#10; ");
			outputRDF.append(":DataViewBMMagnitude :hasMaximumX '" + featureX.getMaxima() + "' . &#10; &#10; ");
			
			Feature featureY = pairFeature.getFeatureY();
			outputRDF.append(":DataViewBMMagnitude :hasDimensionY :" + featureY.getAttrName() + ". &#10; &#10; ");
//			outputRDF += "Feature Axis: " + featureY.getAxis() + "\n";
			outputRDF.append(":DataViewBMMagnitude :hasMinimumY '" + featureY.getMinima() + "' . &#10; &#10; ");
			outputRDF.append(":DataViewBMMagnitude :hasMaximumY '" + featureY.getMaxima() + "' . &#10; &#10; ");
			outputRDF.append(":DataViewBMMagnitude :hasGlobalMinima '" + pairFeature.getMinimaGlobal() + "' . &#10; &#10; ");
			outputRDF.append(":DataViewBMMagnitude :hasGlobalMaxima '" + pairFeature.getMaximaGlobal() + "' . &#10; &#10; ");
			outputRDF.append(":DataViewBMMagnitude :hasFeature '" + featureY.getFeature() + "' . &#10; &#10; ");
			
		}
		
//		System.out.println("[DAO-S] Output RDF: " + outputRDF);

		return outputRDF.toString();
	}
	
	
	
	//TODO create testcase
//	@Override
//	public String extractGraphFeatures() {
//
//		String outputRDF = "";
//
//		Map<String, List<String>> xyMap = new LinkedHashMap<String, List<String>>();
//
//		List<String> xValues = Arrays.asList(new String[] { "3.50E-04",
//				"7.00E-04", "0.00105", "0.0014", "0.00175", "0.0021",
//				"0.00245", "0.0028", "0.00315", "0.0035", "0.00385", "0.0042",
//				"0.00455", "0.0049", "0.00525", "0.0056", "0.00595", "0.0063",
//				"0.00665", "0.007", "0.00735", "0.0077", "0.00805", "0.0084",
//				"0.00875", "0.0091", "0.00945", "0.0098", "0.01015", "0.0105",
//				"0.01085", "0.0112", "0.01155", "0.0119", "0.01225", "0.0126",
//				"0.01295", "0.0133", "0.01365", "0.014", "0.01435", "0.0147",
//				"0.01505", "0.0154", "0.01575", "0.0161", "0.01645", "0.0168",
//				"0.01715", "0.0175", "0.01785", "0.0182", "0.01855", "0.0189",
//				"0.01925", "0.0196", "0.01995", "0.0203", "0.02065", "0.021",
//				"0.02135", "0.0217", "0.02205", "0.0224", "0.02275", "0.0231",
//				"0.02345", "0.0238", "0.02415", "0.0245", "0.02485", "0.0252",
//				"0.02555", "0.0259", "0.02625", "0.0266", "0.02695", "0.0273",
//				"0.02765", "0.028", "0.02835", "0.0287", "0.02905", "0.0294",
//				"0.02975", "0.0301", "0.03045", "0.0308", "0.03115", "0.0315",
//				"0.03185", "0.0322", "0.03255", "0.0329", "0.03325", "0.0336",
//				"0.03395", "0.0343", "0.03465", "0.035" });
//		List<String> yValues = Arrays.asList(new String[] { "-1.13E-05",
//				"-2.81E-06", "-2.11E-07", "-8.02E-07", "-1.14E-06",
//				"-1.13E-06", "-1.08E-06", "-1.04E-06", "-9.84E-07",
//				"-9.00E-07", "-7.83E-07", "-6.30E-07", "-4.33E-07",
//				"-1.84E-07", "1.25E-07", "5.05E-07", "9.67E-07", "1.52E-06",
//				"2.19E-06", "2.98E-06", "3.92E-06", "5.02E-06", "6.30E-06",
//				"7.78E-06", "9.49E-06", "1.15E-05", "1.37E-05", "1.62E-05",
//				"1.90E-05", "2.22E-05", "2.56E-05", "2.94E-05", "3.34E-05",
//				"3.75E-05", "4.18E-05", "4.59E-05", "4.97E-05", "5.27E-05",
//				"5.47E-05", "5.49E-05", "5.26E-05", "4.68E-05", "3.62E-05",
//				"1.94E-05", "-5.32E-06", "-3.98E-05", "-8.59E-05", "-1.45E-04",
//				"-2.16E-04", "-2.96E-04", "-3.76E-04", "-4.36E-04",
//				"-4.41E-04", "-3.38E-04", "-7.39E-05", "3.63E-04", "8.72E-04",
//				"0.00119491", "0.00100708", "1.99E-04", "-8.23E-04",
//				"-0.00125869", "-6.43E-04", "4.42E-04", "8.01E-04", "1.06E-04",
//				"-5.49E-04", "-2.31E-04", "3.66E-04", "1.93E-04", "-2.69E-04",
//				"-1.00E-04", "2.13E-04", "4.53E-06", "-1.46E-04", "6.52E-05",
//				"6.18E-05", "-7.86E-05", "1.32E-05", "3.89E-05", "-3.74E-05",
//				"7.88E-06", "1.44E-05", "-1.69E-05", "7.97E-06", "1.04E-06",
//				"-4.81E-06", "4.21E-06", "-2.00E-06", "2.13E-07", "5.96E-07",
//				"-6.67E-07", "4.43E-07", "-2.01E-07", "5.17E-08", "1.38E-08",
//				"-2.74E-08", "2.19E-08", "-1.14E-08", "0" });
//
//		xyMap.put("sequence", xValues);
//		xyMap.put("function", yValues);
//		
//		outputRDF = extractGraphFeaturesService(xyMap);

//		List<Feature> lstOfFeature = new ArrayList<Feature>();
//
//		Set<String> xySet = xyMap.keySet();
//		for (String s : xySet) {
//			Feature feature = new Feature();
//			feature.setAttrName(s);
//
//			List<Object> lstOfval = new ArrayList<>();
//			for (String values : xyMap.get(s)) {
//				lstOfval.add(values);
//			}
//			feature.setLstOfData(lstOfval);
//
//			lstOfFeature.add(feature);
//		}
//
//		AnalyserPipeline pipeline = new AnalyserPipeline();
//		pipeline.addAnalyser(AnalyserType.DATATYPE, null);
//		pipeline.addAnalyser(AnalyserType.FEATUREVIEW, null);
//		pipeline.addAnalyser(AnalyserType.PROJECTION, null);
//		pipeline.addAnalyser(AnalyserType.OPERATION, null);
//		pipeline.addAnalyser(AnalyserType.INTERPRETATION, null);
//		
//		List<PairFeature> pairFeatureList =  pipeline.execute(lstOfFeature);
//		
//		if(pairFeatureList==null || pairFeatureList.isEmpty()){
//			return outputRDF+"1111";
//		}
//		
//		for(PairFeature pairFeature : pairFeatureList) {
//			Feature featureX = pairFeature.getFeatureX();
//			outputRDF += "Feature X \n";
//			outputRDF += "Feature Name: " + featureX.getAttrName() + "\n";
//			outputRDF += "Feature Axis: " + featureX.getAxis() + "\n";
//			
//			Feature featureY = pairFeature.getFeatureY();
//			outputRDF += "\nFeature Y \n";
//			outputRDF += "Feature Name: " + featureY.getAttrName() + "\n";
//			outputRDF += "Feature Axis: " + featureY.getAxis() + "\n";
//			
//		}

//		return outputRDF;
//	}


	
	//TODO remove this method to tests place
//	@Override
//	public String extractGraphFeaturesG(Map<String, List<String>> xyMap) {
//
//		String outputRDF = "";
//
//		List<Feature> lstOfFeature = new ArrayList<Feature>();
//
//		Set<String> xySet = xyMap.keySet();
//		for (String s : xySet) {
//			Feature feature = new Feature();
//			feature.setAttrName(s);
//
//			List<Object> lstOfval = new ArrayList<>();
//			for (String values : xyMap.get(s)) {
//				lstOfval.add(values);
//			}
//			feature.setLstOfData(lstOfval);
//
//			lstOfFeature.add(feature);
//		}
//
//		AnalyserPipeline pipeline = new AnalyserPipeline();
//		pipeline.addAnalyser(AnalyserType.DATATYPE, null);
//		pipeline.addAnalyser(AnalyserType.FEATUREVIEW, null);
//		pipeline.addAnalyser(AnalyserType.COMPAREANALYSER, new CompareAnalyser(
//				"165.4*(10^(0.06*x)-1)", 35, 0.3, 0.1));
//		pipeline.addAnalyser(AnalyserType.FUNCTIONANALYSER, null);
//		
//		List<PairFeature> pairFeatureList =  pipeline.execute(lstOfFeature);
//		
//		for(PairFeature pairFeature : pairFeatureList) {
//			Feature featureX = pairFeature.getFeatureX();
//			outputRDF += "Feature X \n";
//			outputRDF += "Feature Name: " + featureX.getAttrName() + "&lt;br/&gt; ";
//			outputRDF += "Feature Axis: " + featureX.getAxis() + "&lt;br/&gt; ";
//			
//			Feature featureY = pairFeature.getFeatureY();
//			outputRDF += "\nFeature Y \n";
//			outputRDF += "Feature Name: " + featureY.getAttrName() + "&lt;br/&gt; ";
//			outputRDF += "Feature Axis: " + featureY.getAxis() + "&lt;br/&gt;";
//			
//		}
//
//		return outputRDF;
//	}

}
