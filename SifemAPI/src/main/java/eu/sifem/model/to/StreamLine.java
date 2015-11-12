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

public class StreamLine extends OutputValue{
    private String velocity="velocity";
    private float velocityValue;    

    /**
     * @return the velocity
     */
    public String getVelocity() {
        return velocity;
    }

    /**
     * @param velocity the velocity to set
     */
    public void setVelocity(String velocity) {
        this.velocity = velocity;
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
}
