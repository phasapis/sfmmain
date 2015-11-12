package eu.sifem.mb.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
 
@ManagedBean
@ViewScoped
public class ProgressBarView extends GenericMB implements Serializable {
     
	private static final long serialVersionUID = -233696634134485384L;
	private Integer progress;
	

	@ManagedProperty(value = "#{projectSimulationController}")
	private ProjectSimulationController projectSimulationController;
	
    public Integer getProgress() {
    	if(projectSimulationController.getRenderPogressBar()){
    		addInfoMessage("Info!", "Loading...");
    		if(progress == null) {
    			progress = 0;
    		}
    		else {
    			progress = progress + (int)(Math.random() * 35);
    			
    			if(progress > 100){
    				progress = 100;            	
    			}
    		}    		
    	}
        return progress;
    }
 
    public void setProgress(Integer progress) {
        this.progress = progress;
    }
     
    public void onComplete() {
    	addInfoMessage("Progress completed!", "Now, simulation is running as a bacth process. Please, take a look in \"Show Batch Processes\" on main top panel menu.");
    	progress=null;
    }
     
    public void cancel() {
        progress = null;
    }

	public ProjectSimulationController getProjectSimulationController() {
		return projectSimulationController;
	}

	public void setProjectSimulationController(
			ProjectSimulationController projectSimulationController) {
		this.projectSimulationController = projectSimulationController;
	}
    

}