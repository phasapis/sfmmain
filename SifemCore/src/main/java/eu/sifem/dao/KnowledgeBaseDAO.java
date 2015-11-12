package eu.sifem.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;

import eu.sifem.service.dao.IKnowledgeBaseDAOService;

/**
 * 
 * @author yaskha/jbjares
 *
 */
//FIXME Remove sysos and change location.
@Service(value="knowledgeBaseDAO")
public class KnowledgeBaseDAO extends GenericDAO implements IKnowledgeBaseDAOService{


	@Override
	public List<String> findSubject(String subject) {
		List<String> result = new ArrayList<String>();
		Model model = getModel();
		QueryExecution queryExec= QueryExecutionFactory.create(FIND_MATCHING_SUBJECT.toString(),model);
		ResultSet rs=queryExec.execSelect();
		List<QuerySolution> lstOfQuerySol=ResultSetFormatter.toList(rs);
		
		for(QuerySolution qSoluction:lstOfQuerySol){
			String subjectLocalName = qSoluction.getResource("subject").getLocalName().toString();
			
			if(subjectLocalName.toLowerCase().startsWith(subject.toLowerCase())) {
				result.add(subjectLocalName);
			}
		}
		return result;
	}

	@Override
	public List<String> findPredicate(String predicate) {
		List<String> result = new ArrayList<String>();
		Model model = getModel();
		QueryExecution queryExec= QueryExecutionFactory.create(FIND_MATCHING_PREDICATE.toString(),model);
		ResultSet rs=queryExec.execSelect();
		List<QuerySolution> lstOfQuerySol=ResultSetFormatter.toList(rs);
		
		for(QuerySolution qSoluction:lstOfQuerySol){
			String predicateLocalName = qSoluction.getResource("predicate").getLocalName().toString();
			
			if(predicateLocalName.toLowerCase().startsWith(predicate.toLowerCase()))
				result.add(predicateLocalName);
			
		}
		
		return result;
	}

	@Override
	public List<String> findObject(String object) {
		List<String> result = new ArrayList<String>();
		Model model = getModel();
		QueryExecution queryExec= QueryExecutionFactory.create(FIND_MATCHING_OBJECT.toString(),model);
		ResultSet rs=queryExec.execSelect();
		List<QuerySolution> lstOfQuerySol=ResultSetFormatter.toList(rs);
		
		for(QuerySolution qSoluction:lstOfQuerySol){
			RDFNode objNode = qSoluction.get("object");
			
			String objectLocalName = "";
			
			if(objNode.isResource())
				objectLocalName = objNode.asResource().getLocalName().toString().trim();
			else if(objNode.isLiteral())
				objectLocalName = objNode.asLiteral().toString().trim();
			
			if(objectLocalName.toLowerCase().startsWith(object.toLowerCase()))
				result.add(objectLocalName);
			
		}
		
		return result;
	}

	@Override
	public void insertAxiom(String subject, String predicate, String object) {
		
		String query = INSERT_AXIOM;
		SimplePreparedStatementForSPARQL sps = new SimplePreparedStatementForSPARQL(query);
		try {
			sps.setString(0,"sifem:" + subject + "");
			sps.setString(1,"sifem:" + predicate + "");
			sps.setString(2, "sifem:" + object + "");
			System.out.println(sps.toString());
			execute(sps.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
