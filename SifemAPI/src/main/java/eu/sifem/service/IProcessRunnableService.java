package eu.sifem.service;

import java.util.List;

import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;
import eu.sifem.model.to.SessionIndexTO;
import eu.sifem.model.to.SimulationInstanceTO;

public interface IProcessRunnableService {
	
	//FIXME CANT BE HARDCODE
	String LOCAL_HOST = "http://localhost:8088";
	String REMOTE_HOST = "http://213.249.38.66:7072";
	
	public List<AsyncTripleStoreInsertMessageTO> startSync(SimulationInstanceTO simulationInstanceTO,
			SessionIndexTO sessionIndexTO, String commandLineArgument) throws Exception;

	void startAsync(SimulationInstanceTO simulationInstanceTO,
			SessionIndexTO sessionIndexTO) throws Exception;

}
