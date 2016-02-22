package eu.sifem.service;

import java.util.List;

import eu.sifem.model.to.RulesValidationTO;

/**
 * 
 * @author yaskha
 *
 */
public interface IRulesValidationService {
	
	List<RulesValidationTO> loadRules();
	
	void validateRules(List<RulesValidationTO> selectedRulesValidationTOList);

}
