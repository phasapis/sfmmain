package eu.sifem.service;

import java.util.List;

import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;

public interface IIndexingService {

	void indexStreamService(
			List<AsyncTripleStoreInsertMessageTO> asyncTripleStoreInsertMessageTOList,
			String projectName, String simulationName) throws Exception;

}
