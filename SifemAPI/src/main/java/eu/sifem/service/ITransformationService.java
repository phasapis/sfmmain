package eu.sifem.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;
import eu.sifem.model.to.TransformationTO;
import eu.sifem.model.to.ViewTO;


/**
 * 
 * @author jbjares
 * 
 */
public interface ITransformationService {
	
	 static final String EMPTY_STRING = "";
	 static final String Y = "y";
	 static final String X = "x";
	 static final String FILE = "file:///";
	 static final String JAVA_UTIL_ARRAY_LIST = "java.util.ArrayList";
	 static final String HTTP_WWW_SIFEMONTOLOGIES_COM_ONTOLOGIES = "http://www.sifemontologies.com/ontologies";
	 
	 //TODO Refactoring is necessary, python.exe path must be transparent.
	 static final String C_PYTHON34_PYTHON_EXE = "C:\\ProgramFilesDevel\\python\\Python34\\python.exe ";
	 static final String DISTINCT = "DISTINCT";
	 static final String WHERE = "WHERE";
	 static final String SELECT = "SELECT";
	 static final String GRAPH = "graph";



	Map<String, String> getAttributesFromQueryAndScriptService(String query,
			String script) throws Exception;

	//void fillQueryAttributes(String query, Map<String, String> attributes) throws Exception;

	TransformationTO findByNameService(String name) throws Exception;

	List<TransformationTO> findAllByPartialNameService(String query) throws Exception;

	List<TransformationTO> findAllService() throws Exception;

	List<String> findByTransformationNameService(String name) throws Exception;

	void updateService(TransformationTO transformationTO) throws Exception;

	List<TransformationTO> findAllByProjectNameService(String name) throws Exception;


	ViewTO retrieveDataViewService(TransformationTO transformationTO, String worspace,
			String projectName, String simulationName, String xName,
			String yName, List<File> semantificationFiles) throws Exception;


	Map<String, String> executeService(Map<String, List<String>> result,
			String scriptStr,String imagePath) throws Exception;

	String generateGraphicService(Map<String, List<String>> dimValMap, String imagePath,
			String transformationName, String xName, String yName) throws Exception;


	String generateGraphicService(List<String> datax, String varNamx,
			List<String> datay, String varNamy, String plotName, String basePath)
			throws Exception;

	void sendMessageService(
			List<AsyncTripleStoreInsertMessageTO> asyncTripleStoreInsertMessageList) throws Exception;

	InputStream findQueryByTransformationNameService(String transformationName)
			throws Exception;

	InputStream findScriptByTransformationNameService(String transformationName)
			throws Exception;

	void saveOrUpdateService(TransformationTO transformationTO, String workspacePath,
			String simulationName) throws Exception;


	TransformationTO findByProjectNameAndIsInUseService(String projectName,
			String simulationName);

	String generatePlyFor3DGraphicService(List<String> getxView, String xName,
			List<String> getyView, String yName, String transformationName,
			List<String> list, String canonicalPath, String string);

	String generatePlyFor3DGraphicService(Map<String, List<String>> dimValMap,
			String canonicalPath, String transformationName, String xName,
			String yName, String zName);
}
