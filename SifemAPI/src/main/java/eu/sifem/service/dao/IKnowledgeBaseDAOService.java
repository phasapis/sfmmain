package eu.sifem.service.dao;

import java.util.List;

/**
 * 
 * @author yaskha/jbjares
 *
 */
public interface IKnowledgeBaseDAOService {
	
	final String NAME = null;
	final String QUERY = null;
	final String SCRIPT = null;
	final String SIMULATION_NAME = null;
	final String PARAMETERS = null;
	
	static final String FIND_MATCHING_SUBJECT = "SELECT distinct ?subject WHERE { "
			+ "?subject ?predicate ?object. "
			+ "}";
	
	static final String FIND_MATCHING_PREDICATE = "SELECT distinct ?predicate WHERE { "
			+ "?subject ?predicate ?object. "
			+ "}";
	
	static final String FIND_MATCHING_OBJECT = "SELECT distinct ?object WHERE { "
			+ "?subject ?predicate ?object. "
			+ "}";
	
	static final String INSERT_AXIOM = "PREFIX sifem: <http://www.semanticweb.org/ontologies/sifem#> "
			+ "INSERT DATA { "
			+ "?0 ?1 ?2. "
			+ "}";

	
	List<String> findSubject(String subject);
	
	List<String> findPredicate(String predicate);
	
	List<String> findObject(String object);
	
	void insertAxiom(String subject, String predicate, String object);
}
