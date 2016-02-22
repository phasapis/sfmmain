package eu.sifem.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import eu.sifem.model.to.DataSetTO;
import eu.sifem.model.to.ViewTO;


/**
 * 
 * @author jbjares
 * 
 */
public interface IVisualizationService {

																								
	ViewTO retrieveDataViewService(DataSetTO dataSetTO,String sparqlView, List<File> semantificationFiles, String workspace, String projectName, String simulationName) throws Exception;

	InputStream retrieveFirstDatFileByProjectName(String projectName) throws Exception;

	ViewTO retrieveDataXandYViewService(DataSetTO dataSetHashCacheTO,
			List emptyList, String workspacePath, String projectName,
			String simulationName);
}
