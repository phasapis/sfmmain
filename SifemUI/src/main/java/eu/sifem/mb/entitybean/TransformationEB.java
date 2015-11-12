package eu.sifem.mb.entitybean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.TransformationTO;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "transformationEB") 
@SessionScoped
public class TransformationEB implements Serializable{

	private static final long serialVersionUID = -2187966476792451060L;
	
	
	private TransformationTO transformationTO = new TransformationTO();
	
	private List<String> transformationsSource = new ArrayList<String>();
	
	private List<String> transformationsTarget = new ArrayList<String>();
	
	private Boolean renderGreenWoodsemanticInterpretation = Boolean.FALSE;

	public TransformationTO getTransformationTO() {
		return transformationTO;
	}

	public void setTransformationTO(TransformationTO transformationTO) {
		this.transformationTO = transformationTO;
	}

	public List<String> getTransformationsSource() {
		return transformationsSource;
	}

	public void setTransformationsSource(List<String> transformationsSource) {
		this.transformationsSource = transformationsSource;
	}

	public List<String> getTransformationsTarget() {
		return transformationsTarget;
	}

	public void setTransformationsTarget(List<String> transformationsTarget) {
		this.transformationsTarget = transformationsTarget;
	}

	public Boolean getRenderGreenWoodsemanticInterpretation() {
		return renderGreenWoodsemanticInterpretation;
	}

	public void setRenderGreenWoodsemanticInterpretation(
			Boolean renderGreenWoodsemanticInterpretation) {
		this.renderGreenWoodsemanticInterpretation = renderGreenWoodsemanticInterpretation;
	}
	
	

	
}
