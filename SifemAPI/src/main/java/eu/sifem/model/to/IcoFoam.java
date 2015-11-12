/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.sifem.model.to;

/**
 *
 * @author jled
 */
@Deprecated
public class IcoFoam extends SolverTO{
 
private String parameterName;  
private double parameterValue = 0;  

    public IcoFoam(){
        this.setName("icoFOAM");
        this.parameterName="param1";

    }


    /**
     * @return the parameterName
     */
    public String getParameterName() {
        return parameterName;
    }

    /**
     * @param parameterName the parameterName to set
     */
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    /**
     * @return the parameterValue
     */
    public double getParameterValue() {
        return parameterValue;
    }

    /**
     * @param parameterValue the parameterValue to set
     */
    public void setParameterValue(double parameterValue) {
        this.parameterValue = parameterValue;
    }
}
