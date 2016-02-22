package eu.sifem.mb.entitybean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.ProjectSimulationTO;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "projectSimulationEB") 
@SessionScoped
public class ProjectSimulationEB implements Serializable{

	private static final long serialVersionUID = 7643849882106237799L;

	private ProjectSimulationTO projectSimulationTO = new ProjectSimulationTO();

	public ProjectSimulationTO getProjectSimulationTO() {
		return projectSimulationTO;
	}

	public void setProjectSimulationTO(ProjectSimulationTO projectSimulationTO) {
		this.projectSimulationTO = projectSimulationTO;
	}
	
	

}
