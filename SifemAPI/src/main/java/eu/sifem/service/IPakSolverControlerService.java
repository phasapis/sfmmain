package eu.sifem.service;

import java.util.List;

import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;
import eu.sifem.model.to.DatAndUnvSolverTO;
import eu.sifem.model.to.ProcessTO;
import eu.sifem.model.to.SessionIndexTO;
import eu.sifem.model.to.SimulationInstanceTO;

/**
 * 
 * @author jbjares
 * 
 */
public interface IPakSolverControlerService{
	
	static final String UNV_OUTPUT_TTL = "/unv_output.ttl";

	static final String DAT_INPUT_TTL = "/dat_input.ttl";

	static final String PAK_UNV = "/Pak.unv";

	static final String PAK_DAT = "/Pak.dat";

	static final String FOLDER_AS_NECESSARY = "] folder, as necessary.";

	static final String PAK_NOT_INSTALLED_AT = "PAK not installed at [";

	static final String INPUT_CFG = "/input.cfg";

	static final String PAKC = "/PAKC";

	List<AsyncTripleStoreInsertMessageTO> semantifyOutputService(
			SimulationInstanceTO simulationInstanceTO, List<DatAndUnvSolverTO> datAndUnvSolverTOList) throws Exception;

	void saveOrUpdateProcessStatus(ProcessTO sifemProcess);

}