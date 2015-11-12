package eu.sifem.mb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import eu.sifem.mb.entitybean.BoundaryInternalConditionsEB;
import eu.sifem.mb.entitybean.MaterialPropertyEB;
import eu.sifem.mb.entitybean.ProjectSimulationEB;
import eu.sifem.mb.entitybean.SolverSetupEB;
import eu.sifem.service.ISimulationService;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "solverSetupController")
@ViewScoped
public class SolverSetupController extends GenericMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = -994349763813942057L;

	private Map<String, String> solverNames = new HashMap<String, String>();
	
	private Map<String, String> solverTypes = new HashMap<String, String>();

	private Map<String, String> solverMethods = new HashMap<String, String>();


	private Boolean renderTypes = Boolean.FALSE;
	
	private Boolean renderMethods = Boolean.FALSE;


	@ManagedProperty(value = "#{solverSetupEB}")
	private SolverSetupEB solverSetupEB;



	@ManagedProperty(value = "#{projectSimulationEB}")
	private ProjectSimulationEB projectSimulationEB;

	@ManagedProperty(value = "#{materialPropertyEB}")
	private MaterialPropertyEB materialPropertyEB;

	@ManagedProperty(value = "#{boundaryInternalConditionsEB}")
	private BoundaryInternalConditionsEB boundaryInternalConditionsEB;

	@ManagedProperty(value = "#{simulationService}")
	private ISimulationService simulationService;




	public void handleSolverTypeListChange() {
		try {
			String solverName = solverSetupEB.getSolverTO().getName();
			if("".equals(solverName) || solverName.startsWith("Select")){
				return;
			}
			renderTypes = Boolean.TRUE;
			solverTypes.clear();
			for (String eachObject : simulationService.getSolverTypelistService(solverName)) {
				solverTypes.put(eachObject, eachObject);
			}

		} catch (Exception e) {
			addExceptionMessage(e);
		}
		
		
	}
	
	public void handleSolverMethodListChange() {
		try {
			String solverName = solverSetupEB.getSolverTO().getName();
			String solvertype = solverSetupEB.getSolverTO().getType();
			if("".equals(solverName) || solverName.startsWith("Select") ||
					"".equals(solvertype) || solvertype.startsWith("Select")){
				return;
			}
			renderTypes = Boolean.TRUE;
			renderMethods = Boolean.TRUE;
			solverMethods.clear();
			for (String eachObject : simulationService.getSolverMethodsService(solverName,solvertype)) {
				solverMethods.put(eachObject, eachObject);
			}
			
		} catch (Exception e) {
			addExceptionMessage(e);
		}
		
	}


	public ISimulationService getSimulationService() {
		return simulationService;
	}

	public void setSimulationService(ISimulationService simulationService) {
		this.simulationService = simulationService;
	}

	public SolverSetupEB getSolverSetupEB() {
		return solverSetupEB;
	}


	public void setSolverSetupEB(SolverSetupEB solverSetupEB) {
		this.solverSetupEB = solverSetupEB;
	}


	public ProjectSimulationEB getProjectSimulationEB() {
		return projectSimulationEB;
	}


	public void setProjectSimulationEB(ProjectSimulationEB projectSimulationEB) {
		this.projectSimulationEB = projectSimulationEB;
	}


	public MaterialPropertyEB getMaterialPropertyEB() {
		return materialPropertyEB;
	}


	public void setMaterialPropertyEB(MaterialPropertyEB materialPropertyEB) {
		this.materialPropertyEB = materialPropertyEB;
	}


	public BoundaryInternalConditionsEB getBoundaryInternalConditionsEB() {
		return boundaryInternalConditionsEB;
	}


	public void setBoundaryInternalConditionsEB(
			BoundaryInternalConditionsEB boundaryInternalConditionsEB) {
		this.boundaryInternalConditionsEB = boundaryInternalConditionsEB;
	}

	public Map<String, String> getSolverNames() throws Exception {
    	List<String> lstOfSolvers = simulationService.getSolverListService();
		for (String eachObject : lstOfSolvers) {
			solverNames.put(eachObject, eachObject);
		}
        return solverNames;
	}

	public void setSolverNames(Map<String, String> solverNames) {
		this.solverNames = solverNames;
	}

	public Map<String, String> getSolverTypes() {
		return solverTypes;
	}

	public void setSolverTypes(Map<String, String> solverTypes) {
		this.solverTypes = solverTypes;
	}

	public Map<String, String> getSolverMethods() {
		return solverMethods;
	}

	public void setSolverMethods(Map<String, String> solverMethods) {
		this.solverMethods = solverMethods;
	}

	public Boolean getRenderTypes() {
		return renderTypes;
	}

	public void setRenderTypes(Boolean renderTypes) {
		this.renderTypes = renderTypes;
	}

	public Boolean getRenderMethods() {
		return renderMethods;
	}

	public void setRenderMethods(Boolean renderMethods) {
		this.renderMethods = renderMethods;
	}


	
}
