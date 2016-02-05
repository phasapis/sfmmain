package eu.sifem.model.to;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * 
 * @author jbjares
 * 
 */
public class ProjectSimulationTO implements AbstractTO{

	private static final long serialVersionUID = 7643849882106237799L;
	
	private String id;

	private String projectName;
	
	private String simulationName;
	
	private List<String> instanceNames = new ArrayList<String>();
	
	private String datasourceTempPath;
	
	private Map<Integer,Map<String,File>> instanceFiles = new TreeMap<Integer,Map<String,File>>();
	
	private List<TransformationTO> transformations = new ArrayList<TransformationTO>();

	
	public ProjectSimulationTO() {}
	
	public ProjectSimulationTO(String projectName) {
		this.projectName=projectName;
	}

	
	public ProjectSimulationTO(String projectName,String simulationName) {
		this.projectName=projectName;
		this.simulationName=simulationName;
	}
	
	
	
	public List<String> getInstanceNames() {
		return instanceNames;
	}

	public void setInstanceNames(List<String> instanceNames) {
		this.instanceNames = instanceNames;
	}

	public String getDatasourceTempPath() {
		return datasourceTempPath;
	}

	public void setDatasourceTempPath(String datasourceTempPath) {
		this.datasourceTempPath = datasourceTempPath;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSimulationName() {
		return simulationName;
	}

	public void setSimulationName(String simulationName) {
		this.simulationName = simulationName;
	}

	public List<TransformationTO> getTransformations() {
		return transformations;
	}

	public void setTransformations(List<TransformationTO> transformations) {
		this.transformations = transformations;
	}

	public Map<Integer, Map<String, File>> getInstanceFiles() {
		return instanceFiles;
	}

	public void setInstanceFiles(Map<Integer, Map<String, File>> instanceFiles) {
		this.instanceFiles = instanceFiles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	

}
