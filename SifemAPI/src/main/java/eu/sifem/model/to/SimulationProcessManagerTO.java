package eu.sifem.model.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 
 * @author jbjares
 * 
 */
@Document(collection = "SimulationProcessManagerTO") 
public class SimulationProcessManagerTO implements Serializable{

	private static final long serialVersionUID = 1355360620835378559L;

	private String simulationName;
	
	private List<String> simulationInstanceName= new ArrayList<String>();

	public String getSimulationName() {
		return simulationName;
	}

	public void setSimulationName(String simulationName) {
		this.simulationName = simulationName;
	}

	public List<String> getSimulationInstanceName() {
		return simulationInstanceName;
	}

	public void setSimulationInstanceName(List<String> simulationInstanceName) {
		this.simulationInstanceName = simulationInstanceName;
	}



	

	
}
