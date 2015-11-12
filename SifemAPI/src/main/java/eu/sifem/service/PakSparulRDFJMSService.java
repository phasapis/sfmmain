package eu.sifem.service;

import eu.sifem.model.to.SimulationProcessManagerTO;

public interface PakSparulRDFJMSService {
	
	public void sendMessageService(SimulationProcessManagerTO simulationProcessManagerTO) throws Exception;

}
