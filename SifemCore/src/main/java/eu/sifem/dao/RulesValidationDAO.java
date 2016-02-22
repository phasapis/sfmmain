package eu.sifem.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;

import eu.sifem.model.to.RulesValidationTO;
import eu.sifem.service.IRulesValidationService;
import eu.sifem.service.dao.IRulesValidationDAOService;

/**
 * 
 * @author yaskha
 *
 */
//FIXME Remove sysos and change location.
@Service(value="rulesValidationDAO")
public class RulesValidationDAO extends GenericDAO implements IRulesValidationDAOService {

	@Override
	public List<RulesValidationTO> loadRules() {
		
		List<RulesValidationTO> rulesValidationTOList = new ArrayList<>();
		
		Model model = getModel();
		QueryExecution queryExec= QueryExecutionFactory.create(GET_ALL_RULES.toString(),model);
		ResultSet rs=queryExec.execSelect();
		List<QuerySolution> lstOfQuerySol=ResultSetFormatter.toList(rs);
		
		int ruleID = 1;
		for(QuerySolution qSoluction:lstOfQuerySol){
			String ruleLabel = qSoluction.getLiteral("ruleLabel").toString();
			String constructQuery = qSoluction.getLiteral("constructQuery").toString();
			String ruleURI = qSoluction.getResource("rule").getURI().toString();
			
			System.out.println("Rule ID: " + ruleID + " Label: " + ruleLabel);
			System.out.println("Construct Query: " + constructQuery);
			System.out.println("Rule: " + ruleURI);
			
			rulesValidationTOList.add(new RulesValidationTO(ruleID, ruleURI, ruleLabel, constructQuery, ""));
			ruleID++;
		}
		
		
		return rulesValidationTOList;
	}

	@Override
	public void validateRules(
			List<RulesValidationTO> selectedRulesValidationTOList) {
		
		Iterator<RulesValidationTO> selectedRulesIter = selectedRulesValidationTOList.iterator();
		while(selectedRulesIter.hasNext()) {
			RulesValidationTO rulesValidationTO = selectedRulesIter.next();
			
			rulesValidationTO.setRuleValidity("Satisfied");
			
			System.out.println("RulesValidationDAOService >> " + rulesValidationTO.getRuleID() + " : " + 
			rulesValidationTO.getRuleLabel() + " : " + rulesValidationTO.getRuleValidity());
		}
		
	}

}
