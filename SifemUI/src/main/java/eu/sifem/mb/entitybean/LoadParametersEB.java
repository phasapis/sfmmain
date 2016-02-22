package eu.sifem.mb.entitybean;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.ParameterTO;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "loadParametersEB") 
@SessionScoped
public class LoadParametersEB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8036621300510723779L;

	private Set<ParameterTO> loadParametersTOList = new TreeSet<ParameterTO>();

	public Set<ParameterTO> getLoadParametersTOList() {
		return loadParametersTOList;
	}

	public void setLoadParametersTOList(Set<ParameterTO> loadParametersTOList) {
		this.loadParametersTOList = loadParametersTOList;
	}

	
}
