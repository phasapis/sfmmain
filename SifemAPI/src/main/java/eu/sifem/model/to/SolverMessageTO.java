package eu.sifem.model.to;

import java.util.Set;
import java.util.TreeSet;

import eu.sifem.model.helper.ProjectSimulationPathHelper;


public class SolverMessageTO implements AbstractTO{

	private static final long serialVersionUID = -5248365283479822274L;
	
	private String id;
	
	private byte[] dotDatOutput;
	
	private byte[] dotUnvOutput;
	
	private String jmsCorelationID;

	private Set<ProjectSimulationPathHelper> projectSimulationPaths = new TreeSet<ProjectSimulationPathHelper>();


	public byte[] getDotDatOutput() {
		return dotDatOutput;
	}

	public void setDotDatOutput(byte[] dotDatOutput) {
		this.dotDatOutput = dotDatOutput;
	}

	public byte[] getDotUnvOutput() {
		return dotUnvOutput;
	}

	public void setDotUnvOutput(byte[] dotUnvOutput) {
		this.dotUnvOutput = dotUnvOutput;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJmsCorelationID() {
		return jmsCorelationID;
	}

	public void setJmsCorelationID(String jmsCorelationID) {
		this.jmsCorelationID = jmsCorelationID;
	}

	public Set<ProjectSimulationPathHelper> getProjectSimulationPaths() {
		return projectSimulationPaths;
	}

	public void setProjectSimulationPaths(
			Set<ProjectSimulationPathHelper> projectSimulationPaths) {
		this.projectSimulationPaths = projectSimulationPaths;
	}

		
}
