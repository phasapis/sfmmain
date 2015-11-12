package eu.sifem.model.helper;

import java.io.Serializable;

public class ProjectSimulationPathHelper implements Comparable, Serializable{

	private static final long serialVersionUID = 207105821093202025L;
	
	private String name;
	
	private String canonicaPath;
	
	
	public ProjectSimulationPathHelper(String name, String canonicaPath) {
		super();
		this.name = name;
		this.canonicaPath = canonicaPath;
	}
	
	public ProjectSimulationPathHelper() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCanonicaPath() {
		return canonicaPath;
	}

	public void setCanonicaPath(String canonicaPath) {
		this.canonicaPath = canonicaPath;
	}

	@Override
	public int compareTo(Object arg0) {
		return ((ProjectSimulationPathHelper)arg0).getName().compareTo(this.name);
	}
	
	

}
