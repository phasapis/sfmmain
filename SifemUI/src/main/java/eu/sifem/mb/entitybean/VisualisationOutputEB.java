package eu.sifem.mb.entitybean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.mb.controller.GenericMB;
import eu.sifem.model.to.VisualisationOutputTO;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "visualisationOutputEB") 
@SessionScoped
public class VisualisationOutputEB extends GenericMB implements Serializable{
	private static final long serialVersionUID = 6921770574437995190L;
	
	private VisualisationOutputTO visualisationOutputTO = new VisualisationOutputTO();

	public VisualisationOutputTO getVisualisationOutputTO() {
		return visualisationOutputTO;
	}

	public void setVisualisationOutputTO(VisualisationOutputTO visualisationOutputTO) {
		this.visualisationOutputTO = visualisationOutputTO;
	}
	
	
}
