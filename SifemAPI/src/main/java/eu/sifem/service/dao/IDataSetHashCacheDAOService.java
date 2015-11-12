package eu.sifem.service.dao;

import java.util.List;
import java.util.Map;

import eu.sifem.model.to.DataSetHashCacheTO;

public interface IDataSetHashCacheDAOService {

	Boolean existDataSetCache(DataSetHashCacheTO hash);

	DataSetHashCacheTO retrieveDataSetCacheByHash(String hash);

	DataSetHashCacheTO retrieveDataSetCacheByCompositeKey(String projectName,
			String simulationName, String xName, String yName);


	DataSetHashCacheTO insert(String workspace, String projectName, String simulationName,
			String xName, String yName, Map<String, String> cacheToInsert) throws Exception;

	DataSetHashCacheTO retrieveDataSetCacheByCompositeKey(String projectName,
			String simulationName);

	DataSetHashCacheTO insert(String worspace, String projectName,
			String simulationName, String xName, String yName,
			Map<String, List<String>> cacheToInsert, Boolean isMultipleAndRawAttributes)throws Exception;

	DataSetHashCacheTO retrieveDataSetCacheBySelectedParameters(
			String parameterX, String parmeterY);

	void insert(DataSetHashCacheTO dataSetHashCacheTO);

	DataSetHashCacheTO retrieveDataSetCacheBySelectedParametersAndFrequency(
			String parameterXStr, String parameterYStr, String frequency);

	DataSetHashCacheTO insert(String projectName, String simulationName,
			Map<String, List<String>> cacheToInsert) throws Exception;

	DataSetHashCacheTO retrieveXandYDataSetCacheByProjectAndSimulationName(
			String projectName, String simulationName);

	DataSetHashCacheTO insert(String projectName, String simulationName,
			String instanceName, Map<String, List<String>> values);


}
