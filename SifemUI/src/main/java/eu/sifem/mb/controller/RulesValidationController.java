package eu.sifem.mb.controller;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import eu.sifem.mb.entitybean.RulesEB;
import eu.sifem.mb.entitybean.RulesValidationEB;
import eu.sifem.model.to.RulesValidationTO;
import eu.sifem.service.IRulesValidationService;

/**
 * 
 * @author yaskha
 *
 */

@ManagedBean(name = "rulesValidationController")
@ViewScoped
public class RulesValidationController extends GenericMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7063224034685247831L;
	
	@ManagedProperty(value="#{rulesValidationEB}")
	private RulesValidationEB rulesValidationEB;
	
	@ManagedProperty(value="#{rulesValidationService}")
	private IRulesValidationService rulesValidationService;

	
	public RulesValidationEB getRulesValidationEB() {
		return rulesValidationEB;
	}

	public void setRulesValidationEB(RulesValidationEB rulesValidationEB) {
		this.rulesValidationEB = rulesValidationEB;
	}

	public IRulesValidationService getRulesValidationService() {
		return rulesValidationService;
	}

	public void setRulesValidationService(
			IRulesValidationService rulesValidationService) {
		this.rulesValidationService = rulesValidationService;
	}
	
	
	public void loadRulesActionListner() {
		try {
			if(!(rulesValidationEB.getRulesValidationTOList().isEmpty())) {
				rulesValidationEB.getRulesValidationTOList().clear();
			}
			
			List<RulesValidationTO> rulesValidationTOList = rulesValidationService.loadRules();
			rulesValidationEB.addRulesValidationTOList(rulesValidationTOList);			//TODO insertcode here
		} catch (Exception e) {
			addExceptionMessage(e);
		}


//		Iterator<RulesValidationTO> rulIter = rulesValidationTOList.iterator();
//		while(rulIter.hasNext()) {
//			System.out.println("Label of Rule: " + rulIter.next().getRuleLabel());
//		}
//		rulesValidationEB.setRulesValidationTOList(rulesValidationService.validateRules());
	}
	
	public void validateRulesActionListner() {
		try {
			//TODO insertcode here
			rulesValidationService.validateRules(rulesValidationEB.getSelectedRulesValidationTOList());
		} catch (Exception e) {
			addExceptionMessage(e);
		}
		
//		Iterator<RulesValidationTO> rulesIter = rulesValidationEB.getSelectedRulesValidationTOList().iterator();
//		while(rulesIter.hasNext()) {
//			RulesValidationTO rulesTO = rulesIter.next();
//			System.out.println("RulesValidationController >> " + rulesTO.getRuleID() + " : " + 
//			rulesTO.getRuleLabel() + " : " + rulesTO.getRuleValidity());
//		}
		
	}

}
