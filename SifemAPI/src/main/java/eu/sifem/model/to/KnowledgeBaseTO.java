package eu.sifem.model.to;

/**
 * 
 * @author jbjares
 * 
 */
public class KnowledgeBaseTO implements AbstractTO{

	private static final long serialVersionUID = -3779513848506811290L;

	private String subject;
	
	private String predicate;
	
	private String object;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}
	
	
	
	
	
}
