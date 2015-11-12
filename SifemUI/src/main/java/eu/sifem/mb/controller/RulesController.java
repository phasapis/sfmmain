package eu.sifem.mb.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import eu.sifem.mb.entitybean.RulesEB;
import eu.sifem.service.IRulesService;


/**
 * 
 * @author yaskha
 *
 */

@ManagedBean(name = "rulesController")
@ViewScoped
public class RulesController extends GenericMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8409479865851406673L;
	
	@ManagedProperty(value="#{rulesEB}")
	private RulesEB rulesEB;
	
	@ManagedProperty(value="#{rulesService}")
	private IRulesService rulesService;
	
	private Map<String, String> rulesMethod = new HashMap<String, String>();
	
	/**
	 * 
	 * @return
	 */
	public RulesEB getRulesEB() {
		return rulesEB;
	}


	/**
	 * 
	 * @param rulesEB
	 */
	public void setRulesEB(RulesEB rulesEB) {
		this.rulesEB = rulesEB;
	}


	/*
	 * 
	 */
	public IRulesService getRulesService() {
		return rulesService;
	}


	/**
	 * 
	 * @param rulesService
	 */
	public void setRulesService(IRulesService rulesService) {
		this.rulesService = rulesService;
	}


	public Map<String, String> getRulesMethod() {
		return rulesService.getRulesMethodList();
	}


	public void setRulesMethod(Map<String, String> rulesMethod) {
		this.rulesMethod = rulesMethod;
	}


	/**
	 * 
	 */
	public void saveRulesActionListner() {
		try {
//		System.out.println("Feature: "+rulesEB.getRulesTOList().iterator().next().getFeature());
			rulesService.saveRules(rulesEB.getRulesTOList(), rulesEB.getRulesThenTOList(), rulesEB.getRulesBasicTO());
			//TODO insertcode here
		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}
	
	
	/**
	 * 
	 */
	public void addRulesActionListner() {
		rulesEB.addRulesTO();
		}
	
	/**
	 * 
	 */
	public void deleteRulesActionListner() {
		rulesEB.deleteRulesTO();
	}
	
	
	public void addRulesThenActionListner() {
		rulesEB.addRulesThenTO();
	}
	
	
	public void deleteRulesThenActionListner() {
		rulesEB.deleteRulesThenTO();
	}

}
