package eu.sifem.mb.entitybean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.BoundaryInternalConditionsTO;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "boundaryInternalConditionsEB") 
@SessionScoped
public class BoundaryInternalConditionsEB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1152226119221322510L;
	private BoundaryInternalConditionsTO boundaryInternalConditionsTO = new BoundaryInternalConditionsTO();

	public BoundaryInternalConditionsTO getBoundaryInternalConditionsTO() {
		return boundaryInternalConditionsTO;
	}

	public void setBoundaryInternalConditionsTO(
			BoundaryInternalConditionsTO boundaryInternalConditionsTO) {
		this.boundaryInternalConditionsTO = boundaryInternalConditionsTO;
	}


}
