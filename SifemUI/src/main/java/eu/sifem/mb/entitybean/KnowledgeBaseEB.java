package eu.sifem.mb.entitybean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.KnowledgeBaseTO;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "knowledgeBaseEB") 
@SessionScoped
public class KnowledgeBaseEB  implements Serializable {

	private static final long serialVersionUID = 4324740590679905691L;
	
	private  KnowledgeBaseTO knowledgeBaseTO = new KnowledgeBaseTO();

	public KnowledgeBaseTO getKnowledgeBaseTO() {
		return knowledgeBaseTO;
	}

	public void setKnowledgeBaseTO(KnowledgeBaseTO knowledgeBaseTO) {
		this.knowledgeBaseTO = knowledgeBaseTO;
	}
	

	

}
