package eu.sifem.mb.entitybean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.MaterialPropertyTO;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "materialPropertyEB") 
@SessionScoped
public class MaterialPropertyEB implements Serializable{
	private static final long serialVersionUID = -5033208917141944387L;
	
	private MaterialPropertyTO MaterialPropertyTO = new MaterialPropertyTO();

	public MaterialPropertyTO getMaterialPropertyTO() {
		return MaterialPropertyTO;
	}

	public void setMaterialPropertyTO(MaterialPropertyTO materialPropertyTO) {
		MaterialPropertyTO = materialPropertyTO;
	}
	
	

}
