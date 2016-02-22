package eu.sifem.model.helper;

import java.io.Serializable;

public class MessengerCollectorHelper implements Serializable{

	private static final long serialVersionUID = -151617887892515445L;
	
	private String name;
	
	private String type;
	
	private String message;
	
	private String simulationName;
	
	private InternalUserHelper internalUserHelper = new InternalUserHelper(); 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSimulationName() {
		return simulationName;
	}

	public void setSimulationName(String simulationName) {
		this.simulationName = simulationName;
	}

	public InternalUserHelper getInternalUserHelper() {
		return internalUserHelper;
	}

	public void setInternalUserHelper(InternalUserHelper internalUserHelper) {
		this.internalUserHelper = internalUserHelper;
	}
	
	

}
