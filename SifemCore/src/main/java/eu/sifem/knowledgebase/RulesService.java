package eu.sifem.knowledgebase;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.sifem.model.to.RulesBasicTO;
import eu.sifem.model.to.RulesTO;
import eu.sifem.model.to.RulesThenTO;
import eu.sifem.service.IRulesService;
import eu.sifem.service.dao.IKnowledgeBaseDAOService;
import eu.sifem.service.dao.IRulesDAOService;


/**
 * 
 * @author yaskha
 *
 */

@Service(value="rulesService")
public class RulesService implements IRulesService {
	
	
	@Autowired
	private IRulesDAOService rulesDAO;
	

	/**
	 * 
	 * @return
	 */
	public IRulesDAOService getRulesDAO() {
		return rulesDAO;
	}


	/**
	 * 
	 * @param rulesDAO
	 */
	public void setRulesDAO(IRulesDAOService rulesDAO) {
		this.rulesDAO = rulesDAO;
	}


	@Override
	public void saveRules(List<RulesTO> rulesTOList,
			List<RulesThenTO> rulesTOThenList, RulesBasicTO rulesBasicTO) {
		
		rulesDAO.saveRules(rulesTOList, rulesTOThenList, rulesBasicTO);
		
	}


	@Override
	public Map<String, String> getRulesMethodList() {
		return rulesDAO.getRulesMethodList();
	}
	

}
