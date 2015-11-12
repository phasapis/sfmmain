package eu.sifem.service;

import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;

public interface ITripleStoreLoaderService {

	void executeService(AsyncTripleStoreInsertMessageTO asyncTripleStoreInsertMessageTO) throws Exception;

}
