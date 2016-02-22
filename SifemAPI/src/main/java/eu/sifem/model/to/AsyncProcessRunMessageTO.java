package eu.sifem.model.to;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AsyncProcessRunMessageTO implements AbstractTO{

	private static final long serialVersionUID = 2264973407094992053L;
	
	private SimulationInstanceTO simulationInstanceTO;
	
	private SessionIndexTO sessionIndexTO;

	public SimulationInstanceTO getSimulationInstanceTO() {
		return simulationInstanceTO;
	}

	public void setSimulationInstanceTO(SimulationInstanceTO simulationInstanceTO) {
		this.simulationInstanceTO = simulationInstanceTO;
	}

	public SessionIndexTO getSessionIndexTO() {
		return sessionIndexTO;
	}

	public void setSessionIndexTO(SessionIndexTO sessionIndexTO) {
		this.sessionIndexTO = sessionIndexTO;
	}
	
	
	

}
