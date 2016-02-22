package eu.sifem.service.dao;

import java.io.InputStream;
import java.util.List;

import eu.sifem.model.to.DanAndUnvQueryResultTO;
import eu.sifem.model.to.DatAndUnvSolverTO;

public interface IDatAndUnvSolverDAOService {

	void insert(DatAndUnvSolverTO datAndUnvSolverTO) throws Exception;

	void removeAll(String projectName, String simulationName);

	List<DanAndUnvQueryResultTO> findDatAndUnvFiles(String projectName,
			String simulationName, String instanceName) throws Exception;

	InputStream findDatFileByProjectName(String projectName) throws Exception;

	InputStream findDatFileByProjectName(String projectName,
			String simulationName, String instanceName);

	InputStream findUnvFileByProjectName(String projectName,
			String simulationName, String instanceName);


}
