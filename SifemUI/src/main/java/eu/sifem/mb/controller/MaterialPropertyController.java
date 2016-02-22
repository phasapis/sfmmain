package eu.sifem.mb.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import eu.sifem.mb.entitybean.MaterialPropertyEB;
import eu.sifem.model.to.KinematicViscosityTO;


/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "materialPropertyController") 
@ViewScoped
public class MaterialPropertyController extends GenericMB{	

	private static final long serialVersionUID = -2005923287030640775L;

	@ManagedProperty(value="#{materialPropertyEB}")
	private MaterialPropertyEB materialPropertyEB;
	
	private Map<String,String> materialPropertiesType = new HashMap<String,String>();
	
	private String materialPropertyType;
	
	private Boolean renderViscosityView = Boolean.FALSE;
	
	private Boolean renderYoungModuleView = Boolean.FALSE;
	
	private Boolean renderFluidModuleView = Boolean.FALSE;
	
	private Boolean renderDampingModuleView = Boolean.FALSE;
	
	private UploadedFile youngMaterialUploadedFile;
	
	private UploadedFile dampingUploadedFile;
	
	
    public void handlepropertyTypeChange() {
    	try {
    		if("".equalsIgnoreCase(materialPropertyType) || materialPropertyType==null){
        		return;
        	}

        	if("Young's Modulus".trim().equalsIgnoreCase(materialPropertyType.trim())){
        		renderViscosityView = Boolean.FALSE;
        		renderYoungModuleView = Boolean.TRUE;
        		renderFluidModuleView = Boolean.FALSE;
        		renderDampingModuleView = Boolean.FALSE;
        	} 
        	if("Damping".trim().equalsIgnoreCase(materialPropertyType.trim())){
        		renderViscosityView = Boolean.FALSE;
        		renderYoungModuleView = Boolean.FALSE;
        		renderFluidModuleView = Boolean.FALSE;
        		renderDampingModuleView = Boolean.TRUE;

        	}
        	if("Fluid".trim().equalsIgnoreCase(materialPropertyType.trim())){
        		renderViscosityView = Boolean.FALSE;
        		renderYoungModuleView = Boolean.FALSE;
        		renderFluidModuleView = Boolean.TRUE;
        		renderDampingModuleView = Boolean.FALSE;

        	} 
        	if("Kinematic Viscosity".trim().equalsIgnoreCase(materialPropertyType.trim())){
        		renderViscosityView = Boolean.TRUE;
        		renderYoungModuleView = Boolean.FALSE;
        		renderFluidModuleView = Boolean.FALSE;
        		renderDampingModuleView = Boolean.FALSE;

        	}
		} catch (Exception e) {
			addExceptionMessage(e);
		}
    	    	
    }
    
    public void uploadYoungModuleFile() {
        if(youngMaterialUploadedFile != null) {
            FacesMessage message = new FacesMessage("Succesful",youngMaterialUploadedFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void uploadDampingModuleFile() {
        if(dampingUploadedFile != null) {
            FacesMessage message = new FacesMessage("Succesful", dampingUploadedFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
	
	
	public void conditionCheckingViscocity() {
		if(materialPropertyEB.getMaterialPropertyTO().getKinematicViscosity().getValue() <=0.29 || materialPropertyEB.getMaterialPropertyTO().getKinematicViscosity().getValue()>=1.787){
			addErrorMessage("Ops!","Validation fail: he kinematic viscosity for water should be between 0.29 and 1.787.");
			clear();
			return;
		}else{
			removeMessage();
			addInfoMessage("Brilliant!","Let's configure the solver now?");
			addInfoMessage("Look!","Choose the solver option on the menu below.");
			return;
		}
		 
	}
	
	private void clear() {
		materialPropertyEB.getMaterialPropertyTO().setKinematicViscosity(new KinematicViscosityTO());
	}


	public MaterialPropertyEB getMaterialPropertyTO() {
		return materialPropertyEB;
	}

	public void setMaterialPropertyTO(MaterialPropertyEB materialPropertyEB) {
		this.materialPropertyEB = materialPropertyEB;
	}

	public Map<String, String> getMaterialPropertiesType() {
		try {
			if(materialPropertiesType.isEmpty()){
				//TODO Refactor this hard coded below
				materialPropertiesType.put("Young's Modulus","Young's Modulus");
				materialPropertiesType.put("Damping","Damping");
				materialPropertiesType.put("Fluid","Fluid");
				materialPropertiesType.put("Kinematic Viscosity","Kinematic Viscosity");
			}
		} catch (Exception e) {
			addExceptionMessage(e);
		}
			return materialPropertiesType;
	}

	public void setMaterialPropertiesType(Map<String, String> materialPropertiesType) {
		this.materialPropertiesType = materialPropertiesType;
	}

	public String getMaterialPropertyType() {
		return materialPropertyType;
	}

	public void setMaterialPropertyType(String materialPropertyType) {
		this.materialPropertyType = materialPropertyType;
	}

	public Boolean getRenderViscosityView() {
		return renderViscosityView;
	}

	public void setRenderViscosityView(Boolean renderViscosityView) {
		this.renderViscosityView = renderViscosityView;
	}

	public Boolean getRenderYoungModuleView() {
		return renderYoungModuleView;
	}

	public void setRenderYoungModuleView(Boolean renderYoungModuleView) {
		this.renderYoungModuleView = renderYoungModuleView;
	}

	public Boolean getRenderFluidModuleView() {
		return renderFluidModuleView;
	}

	public void setRenderFluidModuleView(Boolean renderFluidModuleView) {
		this.renderFluidModuleView = renderFluidModuleView;
	}

	public Boolean getRenderDampingModuleView() {
		return renderDampingModuleView;
	}

	public void setRenderDampingModuleView(Boolean renderDampingModuleView) {
		this.renderDampingModuleView = renderDampingModuleView;
	}


	public UploadedFile getYoungMaterialUploadedFile() {
		return youngMaterialUploadedFile;
	}


	public void setYoungMaterialUploadedFile(UploadedFile youngMaterialUploadedFile) {
		this.youngMaterialUploadedFile = youngMaterialUploadedFile;
	}


	public UploadedFile getDampingUploadedFile() {
		return dampingUploadedFile;
	}


	public void setDampingUploadedFile(UploadedFile dampingUploadedFile) {
		this.dampingUploadedFile = dampingUploadedFile;
	}

	public MaterialPropertyEB getMaterialPropertyEB() {
		return materialPropertyEB;
	}

	public void setMaterialPropertyEB(MaterialPropertyEB materialPropertyEB) {
		this.materialPropertyEB = materialPropertyEB;
	}
	
	


}
