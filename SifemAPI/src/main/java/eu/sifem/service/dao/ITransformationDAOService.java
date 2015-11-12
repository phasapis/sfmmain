package eu.sifem.service.dao;

import java.io.InputStream;
import java.util.List;

import eu.sifem.model.to.TransformationTO;


/**
 * 
 * @author jbjares
 * 
 */
public interface ITransformationDAOService {
	
	
	void saveOrUpdate(TransformationTO transformationTO) throws Exception;

	TransformationTO findByName(String name) throws Exception;

	List<TransformationTO> findAll() throws Exception;

	List<TransformationTO> findAllByPartialName(String partialName) throws Exception;

	void update(TransformationTO transformationTO) throws Exception;


	void delete(TransformationTO transformationTO)
			throws Exception;

	List<String> findAllParametersByTransformationName(String selectedTransformationName) throws Exception;

	List<TransformationTO> findAllByProjectName(String simultionName) throws Exception;

	InputStream findQueryByProjectAndSimulationName(String transformationName,
			String ProjectName, String simulationName) throws Exception;

	InputStream findScriptByProjectAndSimulationName(String transformationName,
			String projectName, String simulationName) throws Exception;

//
//	Map<String, List<Double>> findTransformationGraphMap(
//			TransformationSimulationTO transformationSimulationTO) throws Exception;

	List<TransformationTO> findByListName(List<String> names) throws Exception;

	InputStream findScriptByTransformationName(String transformationName)
			throws Exception;

	InputStream findQueryByTransformationName(String transformationName)
			throws Exception;


	TransformationTO findByProjectNameAndIsInUse(String projectName,
			String simulationName);

	List<String> findAllTempParameters();


}
