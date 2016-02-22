package eu.sifem.service.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import eu.sifem.model.to.RulesBasicTO;
import eu.sifem.model.to.RulesTO;
import eu.sifem.model.to.RulesThenTO;


/**
 * 
 * @author yaskha
 *
 */

public interface IRulesDAOService {
	
	static final ArrayList<String> scalar = new ArrayList<String>(
		    Arrays.asList("http://codata.jp/OML-UnitDimension.owl#Pressure"));
	
	static final ArrayList<String>  vector = new ArrayList<String>(
		    Arrays.asList("http://codata.jp/OML-UnitDimension.owl#Velocity", "http://codata.jp/OML-UnitDimension.owl#Rotation"));
	
	static final ArrayList<String>  input = new ArrayList<String>(
		    Arrays.asList("http://codata.jp/OML-UnitDimension.owl#Frequency"));
 	
	static final String INSERT_RULE = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX sifem: <http://www.semanticweb.org/ontologies/sifem#> "
			+ "INSERT DATA { "
			+ "?0 rdf:type sifem:Rule. "
			+ "?0 sifem:hasContent ?1. "
			+ "?0 sifem:ruleType ?2. "
			+ "?0 sifem:ruleLabel ?3. "
			+ "}";
	
	static final String GET_RULES_METHOD_LIST = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
			+ "SELECT ?subject "
			+ "WHERE { "
			+ " ?subject rdfs:subClassOf <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#PhysicalAttribute> . "
			+ "}";
	
	
	static final String IS_SCALAR = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
			+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
			+ "ASK "
			+ "WHERE { "
			+ " ?scalarInd sim:hasScalarValue ?s . "
			+ " ?scalarInd a ?0 . "
			+ "}";
	
	static final String IS_VECTOR = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
			+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
			+ "ASK "
			+ "WHERE { "
			+ " ?vectorInd sim:hasVectorValue ?s . "
			+ " ?vectorInd a ?0 . "
			+ "}";
	
	
	/**
	 * Scalar: Nodal Pressure, Fluid Pressure etc.
	 */
	static final String SCALAR = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
			+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
			+ "PREFIX sifem: <http://www.semanticweb.org/ontologies/sifem#> "
			+ "CONSTRUCT { ?0 } "
			+ "WHERE { "
			+ "?node rdf:type ?1 . "
			+ "?node fem:holdsValueFor ?parameter . "
			+ "?parameter rdf:type ?2 . "
			+ "?parameter sim:hasScalarValue ?parameterValObj . "
			+ "?parameterValObj sim:hasScalarDataValue ?parameterVal."
			+ " ?3 "
			+ "}";
	
	
	/**
	 * Vector: Velocity, Rotation etc.
	 */
	static final String VECTOR = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
			+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
			+ "PREFIX sifem: <http://www.semanticweb.org/ontologies/sifem#> "
			+ "CONSTRUCT { ?0 } "
			+ "WHERE { "
			+ "?element rdf:type ?1 . "
			+ "?element fem:holdsValueFor ?parameter . "
			+ "?parameter rdf:type ?2 . "
			+ "?parameter sim:hasVectorValue ?parameterValObj . "
			+ "?parameterValObj sim:hasVectorXValue ?parameterVal . "
			+ " ?3 "
			+ "}";
	
	
	/**
	 * INPUT: Excitation Frequency etc.
	 */
	static final String INPUT = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
			+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
			+ "PREFIX sifem: <http://www.semanticweb.org/ontologies/sifem#> "
			+ "CONSTRUCT { ?0 } "
			+ "WHERE { "
			+ "?loadResource rdf:type fem:Load . "
			+ "?loadResource fem:holdsValueFor ?parameter . "
			+ "?parameter rdf:type ?1 . "
			+ "?parameter sim:hasScalarValue ?parameterValObj . "
			+ "?parameterValObj sim:hasScalarDataValue ?parameterVal . "
			+ " ?2 "
			+ "}";

	
	void saveRules(List<RulesTO> rulesTOList, List<RulesThenTO> rulesTOThenList, RulesBasicTO rulesBasicTO);
	
	Map<String,String> getRulesMethodList();

}
