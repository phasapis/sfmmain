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
public class Pressure{
	private String name;
	
	//TODO refactor this hard coded
    private MeasurementUnit measurementUnit = new MeasurementUnit("m/s");
    private Double valueX = 0.0;
    private Double validationStepX = 0.0;
    private Double finalValueX = 0.0; 
    private Double valueY = 0.0;
    private Double validationStepY = 0.0;
    private Double finalValueY = 0.0; 
    private Double valueZ = 0.0;
    private Double validationStepZ = 0.0;
    private Double finalValueZ = 0.0; 
    private String geometry="Patch 1";
 
    
    
    public String getGeometry() {
		return geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}

	/**
     * @return the measurementUnit
     */
    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    /**
     * @param measurementUnit the measurementUnit to set
     */
    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    /**
     * @return the valueX
     */
    public Double getValueX() {
        return valueX;
    }

    /**
     * @param valueX the valueX to set
     */
    public void setValueX(Double valueX) {
        this.valueX = valueX;
    }

    /**
     * @return the validationStepX
     */
    public Double getValidationStepX() {
        return validationStepX;
    }

    /**
     * @param validationStepX the validationStepX to set
     */
    public void setValidationStepX(Double validationStepX) {
        this.validationStepX = validationStepX;
    }

    /**
     * @return the finalValueX
     */
    public Double getFinalValueX() {
        return finalValueX;
    }

    /**
     * @param finalValueX the finalValueX to set
     */
    public void setFinalValueX(Double finalValueX) {
        this.finalValueX = finalValueX;
    }

    /**
     * @return the valueY
     */
    public Double getValueY() {
        return valueY;
    }

    /**
     * @param valueY the valueY to set
     */
    public void setValueY(Double valueY) {
        this.valueY = valueY;
    }

    /**
     * @return the validationStepY
     */
    public Double getValidationStepY() {
        return validationStepY;
    }

    /**
     * @param validationStepY the validationStepY to set
     */
    public void setValidationStepY(Double validationStepY) {
        this.validationStepY = validationStepY;
    }

    /**
     * @return the finalValueY
     */
    public Double getFinalValueY() {
        return finalValueY;
    }

    /**
     * @param finalValueY the finalValueY to set
     */
    public void setFinalValueY(Double finalValueY) {
        this.finalValueY = finalValueY;
    }

    /**
     * @return the valueZ
     */
    public Double getValueZ() {
        return valueZ;
    }

    /**
     * @param valueZ the valueZ to set
     */
    public void setValueZ(Double valueZ) {
        this.valueZ = valueZ;
    }

    /**
     * @return the validationStepZ
     */
    public Double getValidationStepZ() {
        return validationStepZ;
    }

    /**
     * @param validationStepZ the validationStepZ to set
     */
    public void setValidationStepZ(Double validationStepZ) {
        this.validationStepZ = validationStepZ;
    }

    /**
     * @return the finalValueZ
     */
    public Double getFinalValueZ() {
        return finalValueZ;
    }

    /**
     * @param finalValueZ the finalValueZ to set
     */
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
