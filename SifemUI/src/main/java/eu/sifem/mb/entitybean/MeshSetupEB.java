package eu.sifem.mb.entitybean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.AbstractTO;
import eu.sifem.model.to.MeshSetupTO;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "meshSetupEB") 
@SessionScoped
public class MeshSetupEB implements Serializable{
	private static final long serialVersionUID = -8378342434305264846L;
	
	private MeshSetupTO meshSetupTO = new MeshSetupTO();

	public MeshSetupTO getMeshSetupTO() {
		return meshSetupTO;
	}

	public void setMeshSetupTO(MeshSetupTO meshSetupTO) {
		this.meshSetupTO = meshSetupTO;
	}

	
	


}
