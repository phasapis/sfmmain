package eu.sifem.service.dao;

import java.io.InputStream;

import eu.sifem.model.to.DatAndUnvTTLTO;

public interface IDatAndUnvTTLDAOService {

	void insert(DatAndUnvTTLTO datAndUnvTTLTO, String fileName) throws Exception;

	InputStream findDatFileByProjectName(String projectName,
			String simulationName, String instanceName);

	InputStream findUnvFileByProjectName(String projectName,
			String simulationName, String instanceName);

}
