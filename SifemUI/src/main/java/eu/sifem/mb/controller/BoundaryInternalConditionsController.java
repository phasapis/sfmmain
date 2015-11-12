package eu.sifem.mb.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import eu.sifem.mb.entitybean.BoundaryInternalConditionsEB;
import eu.sifem.mb.entitybean.BoundaryInternalConditionsItemEB;
import eu.sifem.model.to.BoundaryConditionType;
import eu.sifem.model.to.Condition;
import eu.sifem.model.to.ConditionType;
import eu.sifem.model.to.Dimension;
import eu.sifem.model.to.Pressure;
import eu.sifem.model.to.Velocity;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.ISimulationService;

/**
 * @author swapnil/jbjares
 * 
 */
@ManagedBean(name = "boundaryInternalConditionsController") 
@RequestScoped
public class BoundaryInternalConditionsController extends GenericMB{

	
	private static final long serialVersionUID = -7406148937919664130L;

	@ManagedProperty(value="#{boundaryInternalConditionsEB}")
	private BoundaryInternalConditionsEB boundaryInternalConditionsEB;
	
	@ManagedProperty(value="#{boundaryInternalConditionsItemEB}")
	private BoundaryInternalConditionsItemEB boundaryInternalConditionsItemEB;
	
	private Map<String,String> conditionTypes = new HashMap<String,String>();
	
	private Map<String,String> conditions = new HashMap<String,String>();
	
	private Map<String,String> patchBoundaries  = new HashMap<String,String>();
	

	@ManagedProperty(value="#{simulationService}")
	private ISimulationService simulationService;
	
	@ManagedProperty(value="#{resourceInjectionService}")
	private IResourceInjectionService resourceInjectionService;
	
	
    private Boolean renderVelocity;  
    private Boolean renderPressure;
    private Boolean renderKinematicViscosity;  
    private Boolean renderIcoFoam;
    private Boolean render2Dplot;
    private Boolean rendervectorfield;
    private Boolean renderstreamline;
    private Boolean renderInternalVelocity;
    private Boolean renderInternalPressure;
	
	public BoundaryInternalConditionsController(){
		
		try{
			initializeDefaultValues();			
		}catch(Exception e){
			addExceptionMessage(e);
		}
	}
		


	private void initializeDefaultValues(){
		try{
			String selectedCondition = getParameterFromRequest("condition");
			if("pressure".equalsIgnoreCase(selectedCondition)){  
			    renderVelocity = Boolean.FALSE;  
			    renderPressure = Boolean.TRUE;  
			    renderInternalVelocity = Boolean.FALSE;
			    renderInternalPressure = Boolean.FALSE;
			}
			if("velocity".equalsIgnoreCase(selectedCondition)){  
			    renderVelocity = Boolean.TRUE;  
			    renderPressure = Boolean.FALSE;  
			    renderInternalVelocity = Boolean.FALSE;
			    renderInternalPressure = Boolean.FALSE;
			}
			if(selectedCondition==null || "".equalsIgnoreCase(selectedCondition)){  
			    renderVelocity = Boolean.TRUE;  
			    renderPressure = Boolean.FALSE;
			    renderKinematicViscosity = Boolean.TRUE;  
			    renderIcoFoam = Boolean.TRUE;
			    render2Dplot = Boolean.FALSE;
			    rendervectorfield = Boolean.FALSE;
			    renderstreamline = Boolean.FALSE;
			    renderInternalVelocity = Boolean.FALSE;
			    renderInternalPressure = Boolean.FALSE;			
			}
			
			if(conditions==null || conditions.isEmpty()){
				conditions.put("Internal Condition","Internal Condition");  
				conditions.put("Boundary Condition","Boundary Condition");			
			}			
		}catch(Exception e){
			addExceptionMessage(e);
		}


	}



	//TODO add all capabilities than velocity, pressure relationation between Internal and boundary condition types
    public void handleConditionTypeChange() {
    	try{
			String conditionType = boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getConditionType();
			if(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getCondition()==null){
				return;
			}
			if("Velocity".equalsIgnoreCase(conditionType)){
				this.setRenderVelocity(false);
				this.setRenderPressure(false);
				this.setRenderInternalVelocity(true);
				this.setRenderInternalPressure(false);
			} else if("Pressure".equalsIgnoreCase(conditionType)){
				this.setRenderVelocity(false);
				this.setRenderPressure(false);
				this.setRenderInternalVelocity(false);
				this.setRenderInternalPressure(true);
			}
			boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getCondition().setName(null);
		}catch(Exception e){
			addExceptionMessage(e);
		}
    	
    }
    



	//TODO add all capabilities than velocity, pressure relationation between Internal and boundary condition types
    public void handleConditionListChange()  {
    	try{
    	    String conditionType = boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getConditionType();
    		if(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getCondition()==null){
    			return;
    		}
    		String condition = boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getCondition().getName();
    	    
    	 	if("Velocity".equalsIgnoreCase(conditionType) && 
    	 			"Internal Condition".equalsIgnoreCase(condition)){
    	 		this.setRenderInternalVelocity(true);
    	 		this.setRenderInternalPressure(false);
    	 		this.setRenderVelocity(false);
    	 		this.setRenderPressure(false);
    	 	} else if("Pressure".equalsIgnoreCase(conditionType) && 
    	 			"Boundary Condition".equalsIgnoreCase(condition)){
    	 		this.setRenderInternalVelocity(false);
    	 		this.setRenderInternalPressure(false);
    	 		this.setRenderVelocity(false);
    	 		this.setRenderPressure(true);
    	 	} else if("Velocity".equalsIgnoreCase(conditionType) && 
    	 			"Boundary Condition".equalsIgnoreCase(condition)){
    	 		this.setRenderInternalVelocity(false);
    	 		this.setRenderInternalPressure(false);
    	 		this.setRenderVelocity(true);
    	 		this.setRenderPressure(false);
    	 	} else if("Pressure".equalsIgnoreCase(conditionType) && 
    	 			"Internal Condition".equalsIgnoreCase(condition)){
    	 		this.setRenderInternalVelocity(false);
    	 		this.setRenderInternalPressure(true);
    	 		this.setRenderVelocity(false);
    	 		this.setRenderPressure(false);
    	 	}

    	}catch(Exception e){
			addExceptionMessage(e);
		}

 }  

	

	public BoundaryInternalConditionsEB getBoundaryInternalConditionsTO() {
		return boundaryInternalConditionsEB;
	}

	public void setBoundaryInternalConditionsTO(
			BoundaryInternalConditionsEB boundaryInternalConditionsTO) {
		this.boundaryInternalConditionsEB = boundaryInternalConditionsTO;
	}


	public Map<String, String> getConditionTypes() throws Exception {
		try{
			List<String> lstOfParams = simulationService.getParameterNamesService();
			for (String eachObject : lstOfParams) {
				this.conditionTypes.put(eachObject, eachObject);
			}
		}catch(Exception e){
			addExceptionMessage(e);
		}
		return conditionTypes;
	}


	public void setConditionTypes(Map<String, String> conditionTypes) {
		this.conditionTypes = conditionTypes;
	}




	public ISimulationService getSimulationService() {
		return simulationService;
	}



	public void setSimulationService(ISimulationService simulationService) {
		this.simulationService = simulationService;
	}



	public Map<String, String> getConditions() {
		return conditions;
	}


	public void setConditions(Map<String, String> conditions) {
		this.conditions = conditions;
	}




	//TODO The experimentName can't be hardcoded.
	public Map<String, String> getPatchBoundaries()  {
//		try{
//			String experimentName = "lidABox1:";
//			reader.readBlockMeshDict(resourceInjectionService.getBlockMeshLocation().getFile().getPath(), experimentName);
//			patchBoundaries=reader.getDict().patchTypeMap;
//		}catch(Exception e){
//			addExceptionMessage(e);
//		}
//		return patchBoundaries;
		return Collections.EMPTY_MAP;
	}
	
	//TODO improve for other scenario
    public void addVelocityPatch() {
		try{
			Condition initAndBounCondition = new Condition();
	    	if(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getConditionType().equalsIgnoreCase("Velocity")){
	    		initAndBounCondition.setInitialXStepValue(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getVelocity().getValidationStepX());
				initAndBounCondition.setInitialXStopValue(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getVelocity().getFinalValueX());
				initAndBounCondition.setInitialXValue(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getVelocity().getValueX());
				initAndBounCondition.setSimulationType(ConditionType.VELOCITY);
				if ("movingWall".equalsIgnoreCase(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getVelocity().getGeometry())) {
					initAndBounCondition.setBoundaryCondition(BoundaryConditionType.MOVINGWALL);
					//this.setLenOut((int)(this.velocity.getFinalValueX().intValue())-2);
					boundaryInternalConditionsEB.getBoundaryInternalConditionsTO().setLenOut(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getVelocity().getFinalValueX().intValue()-2);
				}else if("fixedWall1".equalsIgnoreCase(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getVelocity().getGeometry())){
					initAndBounCondition.setBoundaryCondition(BoundaryConditionType.FIXEDWALL1);
				}else if("frontEmpty".equalsIgnoreCase(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getVelocity().getGeometry())){
					initAndBounCondition.setBoundaryCondition(BoundaryConditionType.FRONTEMPTY);
				}else if("backEmpty".equalsIgnoreCase(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getVelocity().getGeometry())){
					initAndBounCondition.setBoundaryCondition(BoundaryConditionType.BACKEMPTY);
				}else if("fixedWall2".equalsIgnoreCase(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getVelocity().getGeometry())){
					initAndBounCondition.setBoundaryCondition(BoundaryConditionType.FIXEDWALL2);
				}else if("fixedWall3".equalsIgnoreCase(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getVelocity().getGeometry())){
					initAndBounCondition.setBoundaryCondition(BoundaryConditionType.FIXEDWALL3);
				}
	    	}else if("Pressure".equalsIgnoreCase(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getConditionType())){
				initAndBounCondition.setInitialXStepValue(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getPressure().getValidationStepX());
				initAndBounCondition.setInitialXStopValue(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getPressure().getFinalValueX());
				initAndBounCondition.setInitialXValue(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getPressure().getValueX());
				initAndBounCondition.setSimulationType(ConditionType.PRESSURE);
				//TODO Review this hard coded : initAndBounCondition.setConditionDimension(new Dimension(2, -2));
				initAndBounCondition.setConditionDimension(new Dimension(2, -2));
				
				if ("movingWall".equalsIgnoreCase(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getPressure().getGeometry())) {
					initAndBounCondition.setBoundaryCondition(BoundaryConditionType.MOVINGWALL);
				} else if("fixedWall1".equalsIgnoreCase(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getPressure().getGeometry())){
					initAndBounCondition.setBoundaryCondition(BoundaryConditionType.FIXEDWALL1);
				} else if("frontEmpty".equalsIgnoreCase(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getPressure().getGeometry())){
					initAndBounCondition.setBoundaryCondition(BoundaryConditionType.FRONTEMPTY);
				} else if("backEmpty".equalsIgnoreCase(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getPressure().getGeometry())){
					initAndBounCondition.setBoundaryCondition(BoundaryConditionType.BACKEMPTY);
				} else if("fixedWall2".equalsIgnoreCase(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getPressure().getGeometry())){
					initAndBounCondition.setBoundaryCondition(BoundaryConditionType.FIXEDWALL2);
				} else if("fixedWall3".equalsIgnoreCase(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().getPressure().getGeometry())){
					initAndBounCondition.setBoundaryCondition(BoundaryConditionType.FIXEDWALL3);
				}
	    	}

	    	boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().setCondition(initAndBounCondition);
	    	boundaryInternalConditionsEB.getBoundaryInternalConditionsTO().addBoundaryInternalConditionsItems(boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO());
	    	addInfoMessage("Thank you for this input.","After that, don't forget to fill the Material Property form.");
	    	clear();
			
		}catch(Exception e){
			addExceptionMessage(e);
		}
    }  
    
    private void clear(){
    	Velocity velocity = new Velocity();
    	velocity.setValidationStepX(0.0);
    	velocity.setFinalValueX(0.0);
    	velocity.setValueX(0.0);
    	boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().setVelocity(velocity);
    	
    	Pressure pressure = new Pressure();
    	pressure.setValidationStepX(0.0);
    	pressure.setFinalValueX(0.0);
    	pressure.setValueX(0.0);
    	boundaryInternalConditionsItemEB.getBoundaryInternalConditionsItemTO().setPressure(pressure);
    }



	public void setPatchBoundaries(Map<String, String> patchBoundaries) {
		this.patchBoundaries = patchBoundaries;
	}


	public BoundaryInternalConditionsItemEB getBoundaryInternalConditionsItemTO() {
		return boundaryInternalConditionsItemEB;
	}


	public void setBoundaryInternalConditionsItemTO(
			BoundaryInternalConditionsItemEB boundaryInternalConditionsItemTO) {
		this.boundaryInternalConditionsItemEB = boundaryInternalConditionsItemTO;
	}

	public Boolean getRenderVelocity() {
		return renderVelocity;
	}

	public void setRenderVelocity(Boolean renderVelocity) {
		this.renderVelocity = renderVelocity;
	}

	public Boolean getRenderPressure() {
		return renderPressure;
	}

	public void setRenderPressure(Boolean renderPressure) {
		this.renderPressure = renderPressure;
	}

	public Boolean getRenderKinematicViscosity() {
		return renderKinematicViscosity;
	}

	public void setRenderKinematicViscosity(Boolean renderKinematicViscosity) {
		this.renderKinematicViscosity = renderKinematicViscosity;
	}

	public Boolean getRenderIcoFoam() {
		return renderIcoFoam;
	}

	public void setRenderIcoFoam(Boolean renderIcoFoam) {
		this.renderIcoFoam = renderIcoFoam;
	}

	public Boolean getRender2Dplot() {
		return render2Dplot;
	}

	public void setRender2Dplot(Boolean render2Dplot) {
		this.render2Dplot = render2Dplot;
	}

	public Boolean getRendervectorfield() {
		return rendervectorfield;
	}

	public void setRendervectorfield(Boolean rendervectorfield) {
		this.rendervectorfield = rendervectorfield;
	}

	public Boolean getRenderstreamline() {
		return renderstreamline;
	}

	public void setRenderstreamline(Boolean renderstreamline) {
		this.renderstreamline = renderstreamline;
	}

	public Boolean getRenderInternalVelocity() {
		return renderInternalVelocity;
	}

	public void setRenderInternalVelocity(Boolean renderInternalVelocity) {
		this.renderInternalVelocity = renderInternalVelocity;
	}

	public Boolean getRenderInternalPressure() {
		return renderInternalPressure;
	}

	public void setRenderInternalPressure(Boolean renderInternalPressure) {
		this.renderInternalPressure = renderInternalPressure;
	}


	public IResourceInjectionService getResourceInjectionService() {
		return resourceInjectionService;
	}



	public void setResourceInjectionService(
			IResourceInjectionService resourceInjectionService) {
		this.resourceInjectionService = resourceInjectionService;
	}



	public BoundaryInternalConditionsEB getBoundaryInternalConditionsEB() {
		return boundaryInternalConditionsEB;
	}



	public void setBoundaryInternalConditionsEB(
			BoundaryInternalConditionsEB boundaryInternalConditionsEB) {
		this.boundaryInternalConditionsEB = boundaryInternalConditionsEB;
	}



	public BoundaryInternalConditionsItemEB getBoundaryInternalConditionsItemEB() {
		return boundaryInternalConditionsItemEB;
	}



	public void setBoundaryInternalConditionsItemEB(
			BoundaryInternalConditionsItemEB boundaryInternalConditionsItemEB) {
		this.boundaryInternalConditionsItemEB = boundaryInternalConditionsItemEB;
	}


	

}
