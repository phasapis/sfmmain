package eu.sifem.model.to;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SimulationInstanceProcessManagerTO") 
public class SimulationInstanceProcessManagerTO implements Serializable{
	
	private static final long serialVersionUID = 3404632803584050585L;

	private String namedGraph;


	public String getNamedGraph() {
		return namedGraph;
	}

	public void setNamedGraph(String namedGraph) {
		this.namedGraph = namedGraph;
	}
	
	

}
