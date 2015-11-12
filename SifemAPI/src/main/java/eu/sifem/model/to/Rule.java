package eu.sifem.model.to;

import java.io.Serializable;

@Deprecated
public class Rule implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4321959843408809388L;
	private String feature;
	private String method;
	private String condition;
	private String operator;
	private String value;
	private String ifCond;
	private String elseCond;
	private String ifVal;
	private String elseVal;
	private String simName;
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getIfCond() {
		return ifCond;
	}
	public void setIfCond(String ifCond) {
		this.ifCond = ifCond;
	}
	public String getElseCond() {
		return elseCond;
	}
	public void setElseCond(String elseCond) {
		this.elseCond = elseCond;
	}
	public String getIfVal() {
		return ifVal;
	}
	public void setIfVal(String ifVal) {
		this.ifVal = ifVal;
	}
	public String getElseVal() {
		return elseVal;
	}
	public void setElseVal(String elseVal) {
		this.elseVal = elseVal;
	}
	public String getSimName() {
		return simName;
	}
	public void setSimName(String simName) {
		this.simName = simName;
	}
	 @Override
	public String toString() {
		 
		return feature+ "-"+method+"-"+condition+"-"+operator+" ";
	}
}
