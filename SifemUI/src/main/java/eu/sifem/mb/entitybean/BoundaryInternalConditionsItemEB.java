package eu.sifem.mb.entitybean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import eu.sifem.model.to.BoundaryInternalConditionsItemTO;
import eu.sifem.model.to.Condition;
import eu.sifem.model.to.Pressure;
import eu.sifem.model.to.Velocity;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name="boundaryInternalConditionsItemEB") 
@ViewScoped
public class BoundaryInternalConditionsItemEB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7976689687586030861L;
	private BoundaryInternalConditionsItemTO boundaryInternalConditionsItemTO = new BoundaryInternalConditionsItemTO();
	public BoundaryInternalConditionsItemTO getBoundaryInternalConditionsItemTO() {
		return boundaryInternalConditionsItemTO;
	}
	public void setBoundaryInternalConditionsItemTO(
			BoundaryInternalConditionsItemTO boundaryInternalConditionsItemTO) {
		this.boundaryInternalConditionsItemTO = boundaryInternalConditionsItemTO;
	}
	

	

}
