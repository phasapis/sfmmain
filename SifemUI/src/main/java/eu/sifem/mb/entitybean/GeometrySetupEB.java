package eu.sifem.mb.entitybean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.GeometrySetupTO;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "geometrySetupEB") 
@SessionScoped
public class GeometrySetupEB implements Serializable{

	private static final long serialVersionUID = 3273889646700950247L;
	
	private GeometrySetupTO geometrySetupTO = new GeometrySetupTO();

	public GeometrySetupTO getGeometrySetupTO() {
		return geometrySetupTO;
	}

	public void setGeometrySetupTO(GeometrySetupTO geometrySetupTO) {
		this.geometrySetupTO = geometrySetupTO;
	}
	
	

}
