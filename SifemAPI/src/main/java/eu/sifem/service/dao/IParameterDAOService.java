package eu.sifem.service.dao;

import java.util.List;

import eu.sifem.model.to.ParameterTO;


/**
 * 
 * @author jbjares
 * 
 */
public interface IParameterDAOService {

	List<ParameterTO> findAllParameters() throws Exception;
    
//	static final String NAME = "name";
//	
//    static final String INSERT_TRANSFORMATIONS_SPARUL = 
//            "PREFIX param: <http://www.semanticweb.org/ontologies/sifem/Parameters#> \n" +
//            "INSERT DATA { \n" +
//                    "<http://www.semanticweb.org/ontologies/sifem#Parameters_?0> param:name ?1 ; \n"+
//            "} \n";

//	String insert = 
//			"INSERT DATA { " +
//					"<http://www.semanticweb.org/ontologies/sifem#"+clazzSimpleName+"_"+rdfHashCode+"> "
//					+ "<http://www.semanticweb.org/ontologies/sifem/"+clazzSimpleName+"#"+method+">  "+json+" ; "+
//					"} ";
	//<http://www.semanticweb.org/ontologies/sifem/ParameterTO#json> "{'id':'12','name':'Distance', 'description':'Distance','isUniqueValue':'','value':'','simulation':''}" .
	//INSERT DATA { <http://www.semanticweb.org/ontologies/sifem/ParameterTO#json> json 
	
	
//	
//	static final String FIND_ALL = "SELECT distinct ?name WHERE { ?s ?p ?o . { \n"
//			+ "?s <http://www.semanticweb.org/ontologies/sifem/Parameters#name> ?name . \n"
//			+ "} \n"
//			+ "} \n";
//		
//	static final String FIND_BY_NAME_QUERY = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
//			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n"
//			+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
//			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
//			+ "PREFIX param: <http://www.semanticweb.org/ontologies/sifem/Parameters#> \n"
//			+ "SELECT DISTINCT ?name \n"
//			+ "WHERE { \n"
//			+ "<http://www.semanticweb.org/ontologies/sifem#Parameters_?0> ?p ?o . \n"
//			+ "<http://www.semanticweb.org/ontologies/sifem#Parameters_?0> <http://www.semanticweb.org/ontologies/sifem/Parameters#name> ?name . \n"
//			+ "} \n"
//			+ " ORDER BY ?name \n";
//
//	
//	
//
//	void insert(ParameterTO parameterTO) throws Exception;
//
//
//	List<ParameterTO> findAll() throws Exception;
//
//
//	ParameterTO findByName(String name) throws Exception;


}
