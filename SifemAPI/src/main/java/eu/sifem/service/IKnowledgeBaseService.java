package eu.sifem.service;

import java.util.List;

/**
 * 
 * @author yaskha/jbjares
 *
 */
public interface IKnowledgeBaseService {

	List<String> findSubject(String subject);
	
	List<String> findPredicate(String predicate);
	
	List<String> findObject(String object);
	
	void insertAxiom(String subject, String predicate, String object);

}
