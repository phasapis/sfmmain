/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.sifem.model.to;

/**
 *
 * @author jled/jbjares
 */
@Deprecated
public class Velocity{
    
	private String name;
	//TODO Refactor this hardcode
    private String geometry="Patch 1";
    private MeasurementUnit measurementUnit = new MeasurementUnit("m/s");
    private Double valueX = new Double(0.0);
    private Double validationStepX = new Double(0.0);
    private Double finalValueX = new Double(0.0);
    private Double valueY = new Double(0.0);
    private Double validationStepY = new Double(0.0);
    private Double finalValueY = new Double(0.0);
    private Double valueZ = new Double(0.0);
    private Double validationStepZ = new Double(0.0);
    private Double finalValueZ = new Double(0.0);
	public String getGeometry() {
		return geometry;
	}
	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}
	public MeasurementUnit getMeasurementUnit() {
		return measurementUnit;
	}
	public void setMeasurementUnit(MeasurementUnit measurementUnit) {
		this.measurementUnit = measurementUnit;
	}
	public Double getValueX() {
		return valueX;
	}
	public void setValueX(Double valueX) {
		this.valueX = valueX;
	}
	public Double getValidationStepX() {
		return validationStepX;
	}
	public void setValidationStepX(Double validationStepX) {
		this.validationStepX = validationStepX;
	}
	public Double getFinalValueX() {
		return finalValueX;
	}
	public void setFinalValueX(Double finalValueX) {
		this.finalValueX = finalValueX;
	}
	public Double getValueY() {
		return valueY;
	}
	public void setValueY(Double valueY) {
		this.valueY = valueY;
	}
	public Double getValidationStepY() {
		return validationStepY;
	}
	public void setValidationStepY(Double validationStepY) {
		this.validationStepY = validationStepY;
	}
	public Double getFinalValueY() {
		return finalValueY;
	}
	public void setFinalValueY(Double finalValueY) {
		this.finalValueY = finalValueY;
	}
	public Double getValueZ() {
		return valueZ;
	}
	public void setValueZ(Double valueZ) {
		this.valueZ = valueZ;
	}
	public Double getValidationStepZ() {
		return validationStepZ;
	}
	public void setValidationStepZ(Double validationStepZ) {
		this.validationStepZ = validationStepZ;
	}
	public Double getFinalValueZ() {
		return finalValueZ;
	}
	public void setFinalValueZ(Double finalValueZ) {
		this.finalValueZ = finalValueZ;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

 
    
    
}