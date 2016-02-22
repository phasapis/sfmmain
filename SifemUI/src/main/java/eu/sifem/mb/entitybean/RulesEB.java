package eu.sifem.mb.entitybean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.RulesBasicTO;
import eu.sifem.model.to.RulesTO;
import eu.sifem.model.to.RulesThenTO;


@ManagedBean(name = "rulesEB") 
@SessionScoped
public class RulesEB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4251977966516367037L;
	
	private  List<RulesTO> rulesTOList = new ArrayList<RulesTO>();
	
	private  List<RulesThenTO> rulesThenTOList = new ArrayList<RulesThenTO>();
	
	private RulesBasicTO rulesBasicTO = new RulesBasicTO();
	
	
	public RulesEB() {
		rulesTOList.add(new RulesTO());
		rulesThenTOList.add(new RulesThenTO());
	}

	public List<RulesTO> getRulesTOList() {
		return rulesTOList;
	}

	public void setRulesTOList(List<RulesTO> rulesTOList) {
		this.rulesTOList = rulesTOList;
	}

	public List<RulesThenTO> getRulesThenTOList() {
		return rulesThenTOList;
	}

	public void setRulesThenTOList(List<RulesThenTO> rulesThenTOList) {
		this.rulesThenTOList = rulesThenTOList;
	}

	public RulesBasicTO getRulesBasicTO() {
		return rulesBasicTO;
	}

	public void setRulesBasicTO(RulesBasicTO rulesBasicTO) {
		this.rulesBasicTO = rulesBasicTO;
	}

	public void addRulesTO() {
		rulesTOList.add(new RulesTO());
	}
	
	
	public void deleteRulesTO() {
		if(rulesTOList.size() > 1)
			rulesTOList.remove(rulesTOList.size()-1);
	}
	
	public void addRulesThenTO() {
		rulesThenTOList.add(new RulesThenTO());
	}
	
	
	public void deleteRulesThenTO() {
		if(rulesThenTOList.size() > 1)
			rulesThenTOList.remove(rulesThenTOList.size()-1);
	}

}
