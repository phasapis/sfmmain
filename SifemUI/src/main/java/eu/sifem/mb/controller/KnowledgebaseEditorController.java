package eu.sifem.mb.controller;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import eu.sifem.mb.entitybean.KnowledgeBaseEB;
import eu.sifem.service.IKnowledgeBaseService;

/**
 * 
 * @author yaskha/jbjares
 * 
 */

@ManagedBean(name = "knowledgebaseEditorController")
@ViewScoped
public class KnowledgebaseEditorController extends GenericMB implements Serializable {

	private static final long serialVersionUID = 8822449906229514799L;
	
	
	@ManagedProperty(value="#{knowledgeBaseEB}")
	private KnowledgeBaseEB knowledgeBaseEB;
	
	
	@ManagedProperty(value="#{knowledgeBaseService}")
	private IKnowledgeBaseService knowledgeBaseService;

	private boolean renderLine = true;


	/**
	 * 
	 * @return
	 */
	public KnowledgeBaseEB getKnowledgeBaseEB() {
		return knowledgeBaseEB;
	}

	/**
	 * 
	 * @param knowledgeBaseEB
	 */
	public void setKnowledgeBaseEB(KnowledgeBaseEB knowledgeBaseEB) {
		this.knowledgeBaseEB = knowledgeBaseEB;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isRenderLine() {
		return renderLine;
	}

	/**
	 * 
	 * @param renderLine
	 */
	public void setRenderLine(boolean renderLine) {
		this.renderLine = renderLine;
	}

	/**
	 * 
	 * @param subj
	 * @return
	 */
	public List<String> completeSubjectTxt(String subj) {
		try {
			List<String> results = knowledgeBaseService.findSubject(subj);
			return results;
		} catch (Exception e) {
			addExceptionMessage(e);
		}
		return Collections.EMPTY_LIST;
				


	}

	/**
	 * 
	 * @param pred
	 * @return
	 */
	public List<String> completePredicateTxt(String pred) {
		try {
			List<String> results = knowledgeBaseService.findPredicate(pred);
			
			return results;
		} catch (Exception e) {
			addExceptionMessage(e);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public List<String> completeObjectTxt(String obj) {
		try {
			List<String> results = knowledgeBaseService.findObject(obj);
			return results;
		} catch (Exception e) {
			addExceptionMessage(e);
		}
		return Collections.EMPTY_LIST;

	}
	
	
	/**
	 * 
	 */
	public void insertAxionActionListner() {
		try {
			knowledgeBaseService.insertAxiom(knowledgeBaseEB.getKnowledgeBaseTO().getSubject(), 
					knowledgeBaseEB.getKnowledgeBaseTO().getPredicate(), 
					knowledgeBaseEB.getKnowledgeBaseTO().getObject());
		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}

	
	/**
	 * 
	 * @return
	 */
	public IKnowledgeBaseService getKnowledgeBaseService() {
		return knowledgeBaseService;
	}

	
	/**
	 * 
	 * @param knowledgeBaseService
	 */
	public void setKnowledgeBaseService(IKnowledgeBaseService knowledgeBaseService) {
		this.knowledgeBaseService = knowledgeBaseService;
	}

}
