package eu.sifem.model.to;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jbjares
 * 
 */
public class BoundaryInternalConditionsItemTO implements AbstractTO{
	
	private static final long serialVersionUID = 941788916351101323L;
	
	public BoundaryInternalConditionsItemTO(){}

	private String conditionType = "Velocity";
	
	private Condition condition = new Condition();
	
	private String patchBoundary = "Boundary Condition";
	
	private List<String> value = new ArrayList<String>();
	
	private List<String> variationStep = new ArrayList<String>();
	
	private List<String> finalValue = new ArrayList<String>();
	
	private Velocity velocityInternal = new Velocity();
	
	private Pressure pressureInternal = new Pressure();
	
	private Velocity velocity = new Velocity();
	
	private Pressure pressure = new Pressure();

	
	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public String getPatchBoundary() {
		return patchBoundary;
	}

	public void setPatchBoundary(String patchBoundary) {
		this.patchBoundary = patchBoundary;
	}

	public List<String> getValue() {
		return value;
	}

	public void setValue(List<String> value) {
		this.value = value;
	}

	public List<String> getVariationStep() {
		return variationStep;
	}

	public void setVariationStep(List<String> variationStep) {
		this.variationStep = variationStep;
	}

	public List<String> getFinalValue() {
		return finalValue;
	}

	public void setFinalValue(List<String> finalValue) {
		this.finalValue = finalValue;
	}

	public Velocity getVelocityInternal() {
		return velocityInternal;
	}

	public void setVelocityInternal(Velocity velocityInternal) {
		this.velocityInternal = velocityInternal;
	}

	public Pressure getPressureInternal() {
		return pressureInternal;
	}

	public void setPressureInternal(Pressure pressureInternal) {
		this.pressureInternal = pressureInternal;
	}

	public Velocity getVelocity() {
		return velocity;
	}

	public void setVelocity(Velocity velocity) {
		this.velocity = velocity;
	}

	public Pressure getPressure() {
		return pressure;
	}

	public void setPressure(Pressure pressure) {
		this.pressure = pressure;
	}
	

}
