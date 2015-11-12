package eu.sifem.model.to;

/**
 * 
 * @author yaskha
 * 
 */
public class RulesThenTO implements AbstractTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4645256498802979351L;

	private String thenSubject;

	private String thenPredicate;

	private String thenObject;

	public String getThenSubject() {
		return thenSubject;
	}

	public void setThenSubject(String thenSubject) {
		this.thenSubject = thenSubject;
	}

	public String getThenPredicate() {
		return thenPredicate;
	}

	public void setThenPredicate(String thenPredicate) {
		this.thenPredicate = thenPredicate;
	}

	public String getThenObject() {
		return thenObject;
	}

	public void setThenObject(String thenObject) {
		this.thenObject = thenObject;
	}

}
