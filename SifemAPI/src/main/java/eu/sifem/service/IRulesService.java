package eu.sifem.service;

import java.util.List;
import java.util.Map;

import eu.sifem.model.to.RulesBasicTO;
import eu.sifem.model.to.RulesTO;
import eu.sifem.model.to.RulesThenTO;
import eu.sifem.model.to.RulesValidationTO;

/**
 * 
 * @author yaskha
 *
 */
public interface IRulesService {
	
	void saveRules(List<RulesTO> rulesTOList, List<RulesThenTO> rulesTOThenList, RulesBasicTO rulesBasicTO, List<RulesValidationTO> rulesValidationTOList);
	
	Map<String,String> getRulesMethodList();

}
