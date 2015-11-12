package eu.sifem.model.to;

/**
 * 
 * @author yaskha
 *
 */
public class RulesBasicTO implements AbstractTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8048044003948223548L;
	
	private String ruleLabel;
	
	private String ruleType;

	public String getRuleLabel() {
		return ruleLabel;
	}

	public void setRuleLabel(String ruleLabel) {
		this.ruleLabel = ruleLabel;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

}
