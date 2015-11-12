package eu.sifem.knowledgebase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.sifem.model.to.RulesValidationTO;
import eu.sifem.service.IRulesValidationService;
import eu.sifem.service.dao.IRulesDAOService;
import eu.sifem.service.dao.IRulesValidationDAOService;

/**
 * 
 * @author yaskha
 *
 */

@Service(value="rulesValidationService")
public class RulesValidationService implements IRulesValidationService {

	
	@Autowired
	private IRulesValidationDAOService rulesValidationDAO;


	public IRulesValidationDAOService getRulesValidationDAO() {
		return rulesValidationDAO;
	}


	public void setRulesValidationDAO(IRulesValidationDAOService rulesValidationDAO) {
		this.rulesValidationDAO = rulesValidationDAO;
	}


	@Override
	public List<RulesValidationTO> loadRules() {
		
		List<RulesValidationTO> rulesValidationTOList = rulesValidationDAO.loadRules();
		
		return rulesValidationTOList;
	}


	@Override
	public void validateRules(
			List<RulesValidationTO> selectedRulesValidationTOList) {
		
		rulesValidationDAO.validateRules(selectedRulesValidationTOList);
		
	}

}
