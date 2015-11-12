package eu.sifem.service.dao;

import java.util.List;

import eu.sifem.model.to.RulesValidationTO;

/**
 * 
 * @author yaskha
 *
 */
public interface IRulesValidationDAOService {
	
	static final String GET_ALL_RULES = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX sifem: <http://www.semanticweb.org/ontologies/sifem#> "
			+ "SELECT ?rule ?constructQuery ?ruleLabel { "
			+ "?rule rdf:type sifem:Rule. "
			+ "?rule sifem:hasContent ?constructQuery. "
			+ "?rule sifem:ruleLabel ?ruleLabel. "
			+ "}";
	
	List<RulesValidationTO> loadRules();
	
	void validateRules(List<RulesValidationTO> selectedRulesValidationTOList);

}
