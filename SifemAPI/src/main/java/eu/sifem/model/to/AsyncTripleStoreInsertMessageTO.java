package eu.sifem.model.to;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AsyncTripleStoreInsertMessageTO implements AbstractTO{

	private static final long serialVersionUID = 2264973407094992053L;
	
	private String workspace;
	
	private String projectName;
	
	private String simulationName;
	
	private String instanceName;
	
	private Map<String,InputStream> tripleFiles = new LinkedHashMap<String,InputStream>();
	
	@Transient
	private List<InputStream> semantificationFiles = new LinkedList<InputStream>();


	
	
	public List<InputStream> getSemantificationFiles() {
		return semantificationFiles;
	}

	public void setSemantificationFiles(List<InputStream> semantificationFiles) {
		this.semantificationFiles = semantificationFiles;
	}

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
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

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public Map<String, InputStream> getTripleFiles() {
		return tripleFiles;
	}

	public void setTripleFiles(Map<String, InputStream> tripleFiles) {
		this.tripleFiles = tripleFiles;
	}


	
	

}
