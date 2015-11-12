package eu.sifem.knowledgebase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.sifem.service.IKnowledgeBaseService;
import eu.sifem.service.dao.IKnowledgeBaseDAOService;

/**
 * 
 * @author yaskha/jbjares
 *
 */

@Service(value="knowledgeBaseService")
public class KnowledgeBaseService implements IKnowledgeBaseService {
	
	
	@Autowired
	private IKnowledgeBaseDAOService knowledgeBaseDAO;

	@Override
	public List<String> findSubject(String subject) {
		return knowledgeBaseDAO.findSubject(subject);
	}
	
	@Override
	public List<String> findPredicate(String predicate) {
		return knowledgeBaseDAO.findPredicate(predicate);
	}

	@Override
	public List<String> findObject(String object) {
		return knowledgeBaseDAO.findObject(object);
	}

	public IKnowledgeBaseDAOService getKnowledgeBaseDAO() {
		return knowledgeBaseDAO;
	}

	public void setKnowledgeBaseDAO(IKnowledgeBaseDAOService knowledgeBaseDAO) {
		this.knowledgeBaseDAO = knowledgeBaseDAO;
	}

	@Override
	public void insertAxiom(String subject, String predicate, String object) {
		knowledgeBaseDAO.insertAxiom(subject, predicate, object);
	}
	


}

