package eu.sifem.model.to;

import java.util.HashMap;
import java.util.Map;

import eu.sifem.service.IRulesService;

public class RulesTO implements AbstractTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1071024044196909671L;
	
	private String feature;
	
	private String method;
	
	private String condition;
	
	private String operator;
		
	private float value;
	
	private String thenSubject;
	
	private String thenPredicate;
	
	private String thenObject;
	
	
	private Map<String,String> validationFeatureList = new HashMap<String, String>(); 
	private Map<String,String> validationMethodList = new HashMap<String, String>(); 
	private Map<String,String> validationConditionList = new HashMap<String, String>(); 
	private Map<String,String> validationOperatorList = new HashMap<String, String>();
	private Map<String,String> validationinputOutputList = new HashMap<String, String>();
	
	public RulesTO() {
		super();
//		this.feature = new String();
//		this.method = new String();
//		this.condition = new String();
//		this.operator = new String();
//		this.value = 0;
		
		
		validationFeatureList.put("AND", "AND");
		validationFeatureList.put("IF", "IF");
		validationFeatureList.put("OR", "OR");
        
		validationConditionList.put("minima","minima");
		validationConditionList.put("maxima","maxima");
		validationConditionList.put("value","value");  

		validationMethodList.put("velocity", "velocity");
		validationMethodList.put("pressure", "pressure");
		validationMethodList.put("kinematic viscosity", "kinematic viscosity");

		validationOperatorList.put("equal", "=");
		validationOperatorList.put("greater", ">");
		validationOperatorList.put("smaller", "<");
		
		validationinputOutputList.put("Input", "input");
		validationinputOutputList.put("Output", "output");
	}
	

	/**
	 * 
	 * @return
	 */
	public String getFeature() {
		return feature;
	}

	/**
	 * 
	 * @param feature
	 */
	public void setFeature(String feature) {
		this.feature = feature;
	}

	/**
	 * 
	 * @return
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * 
	 * @param method
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 
	 * @return
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * 
	 * @param condition
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * 
	 * @return
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * 
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getValue() {
		return value;
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(float value) {
		this.value = value;
	}
	
	
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


	public Map<String, String> getValidationFeatureList() {
		return validationFeatureList;
	}

	public void setValidationFeatureList(Map<String, String> validationFeatureList) {
		this.validationFeatureList = validationFeatureList;
	}

	public Map<String, String> getValidationMethodList() {
		return validationMethodList;
	}

	public void setValidationMethodList(Map<String, String> validationMethodList) {
		this.validationMethodList = validationMethodList;
	}

	public Map<String, String> getValidationConditionList() {
		return validationConditionList;
	}

	public void setValidationConditionList(
			Map<String, String> validationConditionList) {
		this.validationConditionList = validationConditionList;
	}

	public Map<String, String> getValidationOperatorList() {
		return validationOperatorList;
	}

	public void setValidationOperatorList(Map<String, String> validationOperatorList) {
		this.validationOperatorList = validationOperatorList;
	}

	public Map<String, String> getValidationinputOutputList() {
		return validationinputOutputList;
	}


	public void setValidationinputOutputList(
			Map<String, String> validationinputOutputList) {
		this.validationinputOutputList = validationinputOutputList;
	}


	public void addFeature(){
		System.out.println("TO Feature: " + getFeature() );
	}
	
	
//	public void addRulesTO(){
//		RulesTO rulesTO = new RulesTO();
//	}

	
}
