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
public class KinematicViscosityTO implements AbstractTO{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 6994825742389505683L;
	private String name;
    private MeasurementUnit measurementUnit = new MeasurementUnit("m*m/s");
    private double value = 0;

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
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
	
}
