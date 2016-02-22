package eu.sifem.service;

import eu.sifem.model.to.AsyncProcessRunMessageTO;

public interface ISimulationSenderService {


	void sendMessageService(AsyncProcessRunMessageTO asyncProcessRunMessageTO)
			throws Exception;

}
