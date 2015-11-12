package eu.sifem.service.dao;

import java.util.List;

import eu.sifem.model.to.ProjectSimulationTO;


/**
 * 
 * @author jbjares
 * 
 */
public interface ISimulationDAOService {
	static final String NAME = "name";
	
    static final String WORKSPACE = "workspace";
    
    static final String TRANSFORMATIONS = "transformations";
    
	


	
    static final String DELETE_SIMULATION_SPARUL0 = 
            "DELETE WHERE { <http://www.semanticweb.org/ontologies/sifem#Simulations_?0> ?p ?o } ";
    
    
	static final String DELETE_SIMULATION_SPARUL1 = "DELETE ?name ?workspace ?transformations WHERE { \n"
			+ "<http://www.semanticweb.org/ontologies/sifem#Simulations_?0> ?p ?o . { \n"
			+ "?s <http://www.semanticweb.org/ontologies/sifem/Simulations#name> ?name . \n"
			+ "?s <http://www.semanticweb.org/ontologies/sifem/Simulations#workspace> ?workspace . \n"
			+ "?s <http://www.semanticweb.org/ontologies/sifem/Simulations#transformations> ?transformations . \n"
			+ "} "
			+ "}";

	
	static final String DELETE_SIMULATION_SPARUL2 = "DELETE ?name ?workspace ?transformations WHERE { "
			+ "<http://www.semanticweb.org/ontologies/sifem#Simulations_?0> ?p ?o . { \n"
			+ "?s ?p ?name . \n"
			+ "?s ?p ?workspace . \n"
			+ "?s ?p ?transformations . \n"
			+ "} \n"
			+ "}";
	
	
    
    static final String INSERT_SIMULATION_SPARUL = 
            "PREFIX param: <http://www.semanticweb.org/ontologies/sifem/Simulations#> \n" +
            "INSERT DATA { \n" +
                    "<http://www.semanticweb.org/ontologies/sifem#Simulations_?0> param:name ?1 . \n"+
                    "<http://www.semanticweb.org/ontologies/sifem#Simulations_?0> param:workspace ?2 . \n"+
                    "<http://www.semanticweb.org/ontologies/sifem#Simulations_?0> param:transformations ?3 . \n"+
            "} ";
    
	
	static final String FIND_ALL = "SELECT distinct ?name ?workspace ?transformations WHERE { ?s ?p ?o . { \n"
			+ "?s <http://www.semanticweb.org/ontologies/sifem/Simulations#name> ?name . \n"
			+ "?s <http://www.semanticweb.org/ontologies/sifem/Simulations#workspace> ?workspace . \n"
			+ "?s <http://www.semanticweb.org/ontologies/sifem/Simulations#transformations> ?transformations . \n"
			+ "} "
			+ "}";
		
	static final String FIND_BY_NAME_QUERY = "SELECT ?name ?workspace ?transformations WHERE { ?s ?p ?o . { "
			+ "?s <http://www.semanticweb.org/ontologies/sifem/Simulations#name> ?name .  \n"
			+ "?s <http://www.semanticweb.org/ontologies/sifem/Simulations#workspace> ?workspace . \n"
			+ "?s <http://www.semanticweb.org/ontologies/sifem/Simulations#transformations> ?transformations . \n"
			+ "FILTER(?name = \"?0\" ) . \n"
					+ "} \n"
			+ "}";


	ProjectSimulationTO findByName(String name) throws Exception;

	void insert(ProjectSimulationTO projectSimulationTO) throws Exception;

	List<ProjectSimulationTO> findAll();

	void update(ProjectSimulationTO projectSimulationTO) throws Exception;

	void delete(ProjectSimulationTO projectSimulationTO) throws Exception;

}
