package eu.sifem.model.to;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SessionIndexTO implements AbstractTO{
	
	private static final long serialVersionUID = 6939447796542184460L;
	
	private List<String> projectIdList = new ArrayList<String>();

	private List<String> cfgSessionIdList = new ArrayList<String>();
	
	private List<String> simulationIdList = new ArrayList<String>();

	public List<String> getCfgSessionIdList() {
		return cfgSessionIdList;
	}

	public void setCfgSessionIdList(List<String> cfgSessionIdList) {
		this.cfgSessionIdList = cfgSessionIdList;
	}

	public List<String> getSimulationIdList() {
		return simulationIdList;
	}

	public void setSimulationIdList(List<String> simulationIdList) {
		this.simulationIdList = simulationIdList;
	}

	public List<String> getProjectIdList() {
		return projectIdList;
	}

	public void setProjectIdList(List<String> projectIdList) {
		this.projectIdList = projectIdList;
	}
	
	

}
