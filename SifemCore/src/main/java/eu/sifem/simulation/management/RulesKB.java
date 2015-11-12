package eu.sifem.simulation.management;


public class RulesKB {
	
	//TODO create testcase
//	public List<String> getRules(JenaModel model, String femModelUri){
//		List<String> sparqlRules = new ArrayList<String>(); 
//		String prefixes = "PREFIX fem: <http://www.sifemontologies.com/ontologies/FEMOntology.owl#> " +
//				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "+
//				"PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> " +
//				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
//		String rulesQuery = prefixes + 
//				"Select ?x where {<" + femModelUri + "> fem:hasRule ?x . } ";
//		List<String> var = new ArrayList<String>(); var.add("x");
//		Map<String, List<RDFNode>> results = model.queryModel(rulesQuery, var);
//		List<RDFNode> rules = results.get("x");
//		for(RDFNode rule : rules)
//			sparqlRules.add(rule.toString());	
//		return sparqlRules;		
//	}

}
