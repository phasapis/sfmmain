package eu.sifem.model.to;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jbjares
 * 
 */

public class SolverConfigCreatorTO implements AbstractTO{
	private static final long serialVersionUID = -5819627184210752604L;
	
	private String simulationName;
	
	private String projectName;
	
	private String solverName;
	
	private String workspace;

	private MeshSetupTO meshSetupTO = new MeshSetupTO();
	
	private	GeometrySetupTO geometrySetupTO = new GeometrySetupTO();
	
	private List<ParameterTO> loadParametersTOList = new ArrayList<ParameterTO>();
		
	private ExternalLoadParametersTO externalLoadParametersTO = new ExternalLoadParametersTO();
	
	private File function;
	
	private String sessionID;
	
	

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public MeshSetupTO getMeshSetupTO() {
		return meshSetupTO;
	}

	public void setMeshSetupTO(MeshSetupTO meshSetupTO) {
		this.meshSetupTO = meshSetupTO;
	}

	public GeometrySetupTO getGeometrySetupTO() {
		return geometrySetupTO;
	}

	public void setGeometrySetupTO(GeometrySetupTO geometrySetupTO) {
		this.geometrySetupTO = geometrySetupTO;
	}

	public ExternalLoadParametersTO getExternalLoadParametersTO() {
		return externalLoadParametersTO;
	}

	public void setExternalLoadParametersTO(
			ExternalLoadParametersTO externalLoadParametersTO) {
		this.externalLoadParametersTO = externalLoadParametersTO;
	}

	public File getFunction() {
		return function;
	}

	public void setFunction(File function) {
		this.function = function;
	}

	public String getSolverName() {
		return solverName;
	}

	public void setSolverName(String solverName) {
		this.solverName = solverName;
	}

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}

	public String getSimulationName() {
		return simulationName;
	}

	public void setSimulationName(String simulationName) {
		this.simulationName = simulationName;
	}

	public List<ParameterTO> getLoadParametersTOList() {
		return loadParametersTOList;
	}

	public void setLoadParametersTOList(List<ParameterTO> loadParametersTOList) {
		this.loadParametersTOList = loadParametersTOList;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
	
	
}
