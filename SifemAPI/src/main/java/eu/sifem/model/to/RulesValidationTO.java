package eu.sifem.model.to;

/**
 * 
 * @author yaskha
 *
 */
public class RulesValidationTO implements AbstractTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4210250375775256029L;
	
	private int ruleID;
	
	private String ruleURI;
	
	private String ruleLabel;
	
	private String constructQuery;
	
	private String ruleValidity;

	
	public RulesValidationTO(int ruleID, String ruleURI, String ruleLabel, String constructQuery, String ruleValidity) {
		this.ruleID = ruleID;
		this.ruleURI = ruleURI;
		this.ruleLabel = ruleLabel;
		this.constructQuery = constructQuery;
		this.ruleValidity = ruleValidity;
	}
	
	
	public int getRuleID() {
		return ruleID;
	}


	public void setRuleID(int ruleID) {
		this.ruleID = ruleID;
	}


	public String getRuleURI() {
		return ruleURI;
	}


	public void setRuleURI(String ruleURI) {
		this.ruleURI = ruleURI;
	}


	public String getRuleLabel() {
		return ruleLabel;
	}

	public void setRuleLabel(String ruleLabel) {
		this.ruleLabel = ruleLabel;
	}


	public String getConstructQuery() {
		return constructQuery;
	}


	public void setConstructQuery(String constructQuery) {
		this.constructQuery = constructQuery;
	}


	public String getRuleValidity() {
		return ruleValidity;
	}


	public void setRuleValidity(String ruleValidity) {
		this.ruleValidity = ruleValidity;
	}


}
