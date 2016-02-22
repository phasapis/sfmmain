package eu.sifem.service.dao;

import java.io.InputStream;
import java.util.List;

import eu.sifem.model.to.ConfigFileTO;

public interface IConfigFileDAOService {

	String insert(ConfigFileTO configFileTO) throws Exception;

	List<InputStream> findCFGFile(String projectName, String simulationName,
			String instanceName) throws Exception;

	void removeAll(String projectName, String simulationName);

	List<ConfigFileTO> findConfigTO(String projectName, String simulationName)
			throws Exception;

	InputStream findCFGFile(ConfigFileTO configFileTO);

	List<InputStream> findCFGFile(String projectName, String simulationName)
			throws Exception;

	String updateInstanceName(ConfigFileTO configFileTO) throws Exception;

	void updateInstanceName(String projectName, String simulationName,
			String instanceName);


}
