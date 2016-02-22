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
public class Plot2D extends OutputValue{
    private String velocityX="velocity {x}";
    private String distanceFromCavityBase="Distance from cavity base";
    private float velocityValue;
    private float distanceFromCavityBaseValue;

    /**
     * @return the velocityX
     */
    public String getVelocityX() {
        return velocityX;
    }

    /**
     * @param velocityX the velocityX to set
     */
    public void setVelocityX(String velocityX) {
        this.velocityX = velocityX;
    }

    /**
     * @return the distanceFromCavityBase
     */
    public String getDistanceFromCavityBase() {
        return distanceFromCavityBase;
    }

    /**
     * @param distanceFromCavityBase the distanceFromCavityBase to set
     */
    public void setDistanceFromCavityBase(String distanceFromCavityBase) {
        this.distanceFromCavityBase = distanceFromCavityBase;
    }

    /**
     * @return the velocityValue
     */
    public float getVelocityValue() {
        return velocityValue;
    }

    /**
     * @param velocityValue the velocityValue to set
     */
    public void setVelocityValue(float velocityValue) {
        this.velocityValue = velocityValue;
    }

    /**
     * @return the distanceFromCavityBaseValue
     */
    public float getDistanceFromCavityBaseValue() {
        return distanceFromCavityBaseValue;
    }

    /**
     * @param distanceFromCavityBaseValue the distanceFromCavityBaseValue to set
     */
    public void setDistanceFromCavityBaseValue(float distanceFromCavityBaseValue) {
        this.distanceFromCavityBaseValue = distanceFromCavityBaseValue;
    }

}

