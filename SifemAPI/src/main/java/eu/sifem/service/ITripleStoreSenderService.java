package eu.sifem.service;

import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;

public interface ITripleStoreSenderService {

	void sendMessageService(
			AsyncTripleStoreInsertMessageTO asyncTripleStoreInsertMessageTO)
			throws Exception;

}
