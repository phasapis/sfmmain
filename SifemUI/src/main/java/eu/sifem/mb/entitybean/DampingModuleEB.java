package eu.sifem.mb.entitybean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.AbstractTO;
import eu.sifem.model.to.DampingModuleTO;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "dampingModuleEB") 
@SessionScoped
public class DampingModuleEB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6582185590335974329L;
	private DampingModuleTO dampingModuleTO = new DampingModuleTO();
	public DampingModuleTO getDampingModuleTO() {
		return dampingModuleTO;
	}
	public void setDampingModuleTO(DampingModuleTO dampingModuleTO) {
		this.dampingModuleTO = dampingModuleTO;
	}


}
