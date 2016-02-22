package eu.sifem.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import eu.sifem.model.to.RulesBasicTO;
import eu.sifem.model.to.RulesTO;
import eu.sifem.model.to.RulesThenTO;
import eu.sifem.service.dao.IRulesDAOService;


/**
 * 
 * @author yaskha
 *
 */

//FIXME Remove sysos and change location.
@Service(value="rulesDAO")
public class RulesDAO extends GenericDAO implements IRulesDAOService {

	@Override
	public void saveRules(List<RulesTO> rulesTOList, List<RulesThenTO> rulesTOThenList, RulesBasicTO rulesBasicTO) {
		
		System.out.println("Label: " + rulesBasicTO.getRuleLabel() + " ### Type: " + rulesBasicTO.getRuleType());
		
		UUID uniqueNumber = UUID.randomUUID();
		
		Iterator<RulesTO> rulesTOIter = rulesTOList.iterator();
		String constructQuery = "";
		SimplePreparedStatementForSPARQL spsConstruct = new SimplePreparedStatementForSPARQL(constructQuery);
		
		Iterator<RulesThenTO> rulesTOThenIter = rulesTOThenList.iterator();
		String thenPart = "";
		while(rulesTOThenIter.hasNext()) {
			RulesThenTO rulesTOThen = rulesTOThenIter.next();
			thenPart += " sifem:" + rulesTOThen.getThenSubject() + 
					" sifem:" + rulesTOThen.getThenPredicate() + 
					" sifem:" + rulesTOThen.getThenObject() + " . ";
		}
		
		String query = INSERT_RULE;
		SimplePreparedStatementForSPARQL sps = new SimplePreparedStatementForSPARQL(query);
		while(rulesTOIter.hasNext()) {
			
			RulesTO rulesTO = rulesTOIter.next();
			
			String rulesLabel = rulesBasicTO.getRuleLabel();
			String feature = rulesTO.getFeature();
			String method = rulesTO.getMethod();
			String condition = rulesTO.getCondition();
			String operator = rulesTO.getOperator();
			String inputOutput = rulesBasicTO.getRuleType();
			float value = rulesTO.getValue();
			
			System.out.println(feature + " ## " + method + " ## " + condition + " ## " + operator + " ## " + inputOutput + " ## " + value);
			
			try {
				SimplePreparedStatementForSPARQL spsASKScalar = new SimplePreparedStatementForSPARQL(IS_SCALAR);
				spsASKScalar.setString(0, "<" + method + ">");
				SimplePreparedStatementForSPARQL spsASKVector = new SimplePreparedStatementForSPARQL(IS_VECTOR);
				spsASKVector.setString(0, "<" + method + ">");
				
				boolean isScalar = executeASK(spsASKScalar.toString());
				boolean isVector = executeASK(spsASKVector.toString());
				boolean isInput = false;
				boolean isOutput = false;
				
				if(inputOutput.toString().equals("input"))
					isInput = true;
				else if(inputOutput.toString().equals("output"))
					isOutput = true;
								
				if (isScalar && isInput) {
					constructQuery = SCALAR;
					spsConstruct.setQuery(constructQuery);
					spsConstruct.setString(1, "fem:Load");
				} else if (isScalar && isOutput) {
					constructQuery = SCALAR;
					spsConstruct.setQuery(constructQuery);
					spsConstruct.setString(1, "fem:Element");
				} else if (isVector && isInput) {
					constructQuery = VECTOR;
					spsConstruct.setQuery(constructQuery);
					spsConstruct.setString(1, "fem:Load");
				} else if (isVector && isOutput) {
					constructQuery = VECTOR;
					spsConstruct.setQuery(constructQuery);
					spsConstruct.setString(1, "fem:Element");
				}
				
				spsConstruct.setString(0, thenPart);
				spsConstruct.setString(2, "<" + method + ">");
				spsConstruct.setString(3, "FILTER ( ?parameterVal " + operator + " " + value +  " ) ");
					
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
		}
		
		try {
			sps.setString(0, "sifem:Rule_" + uniqueNumber);
			sps.setString(1, "\"" + spsConstruct.toString() + "\"");
			sps.setString(2, "\"" + rulesBasicTO.getRuleType() + "\"");
			sps.setString(3, "\"" + rulesBasicTO.getRuleLabel() + "\"");
			System.out.println(sps.toString());
			execute(sps.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public Map<String, String> getRulesMethodList() {
		Map<String,String> rulesMethodMap = new HashMap<String, String>();
		
		try {
			Model model = getModel();
			QueryExecution queryExec= QueryExecutionFactory.create(GET_RULES_METHOD_LIST.toString(),model);
			ResultSet rs=queryExec.execSelect();
			List<QuerySolution> lstOfQuerySol=ResultSetFormatter.toList(rs);
			
			for(QuerySolution qSoluction:lstOfQuerySol){
				Resource subject = qSoluction.getResource("subject");
				
				String subjectURI = subject.toString();
				String subjectLocalName = subject.getLocalName().toString();
				
				rulesMethodMap.put(subjectLocalName, subjectURI);
				
//				System.out.println(subjectURI + " :::: " + subjectLocalName);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rulesMethodMap;
	}

}
