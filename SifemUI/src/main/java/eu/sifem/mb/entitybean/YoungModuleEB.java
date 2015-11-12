package eu.sifem.mb.entitybean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.AbstractTO;
import eu.sifem.model.to.YoungModuleTO;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "youngModuleEB") 
@SessionScoped
public class YoungModuleEB implements Serializable{
	private static final long serialVersionUID = 8112139706398636424L;
	
	private YoungModuleTO youngModuleTO = new YoungModuleTO();

	public YoungModuleTO getYoungModuleTO() {
		return youngModuleTO;
	}

	public void setYoungModuleTO(YoungModuleTO youngModuleTO) {
		this.youngModuleTO = youngModuleTO;
	}
	
	
}
