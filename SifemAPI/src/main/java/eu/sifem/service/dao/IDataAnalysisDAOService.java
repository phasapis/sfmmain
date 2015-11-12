package eu.sifem.service.dao;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author yaskha
 *
 */
public interface IDataAnalysisDAOService {
	
	String uriDataAnalysisOntology = "http://www.sifemontologies.com/ontologies/DataAnalysis.owl#";

	String extractGraphFeaturesService(Map<String, List<String>> xyMap);


	
}
