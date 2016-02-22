package eu.sifem.transformations;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.hpl.jena.rdf.model.RDFNode;

import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;
import eu.sifem.model.to.DataSetHashCacheTO;
import eu.sifem.model.to.TransformationSimulationTO;
import eu.sifem.model.to.TransformationTO;
import eu.sifem.model.to.ViewTO;
import eu.sifem.service.IFeatureExtractorService;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.ITransformationService;
import eu.sifem.service.ITripleStoreSenderService;
import eu.sifem.service.ITurtleFormatRDFWriterService;
import eu.sifem.service.dao.IDataSetHashCacheDAOService;
import eu.sifem.service.dao.IGenericJenaQueryDAOService;
import eu.sifem.service.dao.ITransformationDAOService;
import eu.sifem.utils.BasicFileTools;
import eu.sifem.utils.CommandLineTools;
import eu.sifem.utils.Util;

/**
 * 
 * @author jbjares
 * 
 */
@Service(value = "transformation")
public class Transformation implements ITransformationService {


	@Autowired
	private ITransformationDAOService transformationDAO;

	@Autowired
	private ITurtleFormatRDFWriterService turtleFormatRDFWriterService;

	@Autowired
	private IResourceInjectionService resourceInjectionService;

	@Autowired
	private IFeatureExtractorService featureExtractorService;
	
	@Autowired
	private IDataSetHashCacheDAOService dataSetHashCacheDAO;
	
	@Autowired
	private IGenericJenaQueryDAOService genericJenaQuery;
	
	@Autowired
	private ITripleStoreSenderService tripleStoreSenderService;


	@Override
	public Map<String, String> getAttributesFromQueryAndScriptService(String query,
			String script) throws Exception {
		Map<String, String> attributes = new TreeMap<String, String>();
		new Util().fillQueryAttributes(query, attributes);
		return attributes;
	}

	//TODO Make Test case
//	public static void main(String[] args) throws Exception {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");
//        ITransformationService transformation = ctx.getBean(ITransformationService.class);
//        ITransformationDAOService transformationDAO = ctx.getBean(ITransformationDAOService.class);
//        TransformationTO transformationTO = new TransformationTO();
//        
//		if (!transformationDAO.findAll().isEmpty()) {
//			transformationTO = transformationDAO.findAll().get(0);
//		}
//		
//
//        System.out.println(transformation.retrieveDataView(transformationTO,"C:\\SifemWindowsResourceFiles\\workspace\\projectOne\\", "projectOne", transformationTO.getSimulationName(), "IDS", "TRANSLATIONXS",null));
//	}

	@Override
	public ViewTO retrieveDataViewService(TransformationTO transformationTO, String workspace,String projectName, String simulationName, String xName,String yName,List<File> semantificationFiles) throws Exception {

		if(transformationTO!=null && transformationTO.getSimulationName()!=null && !"".equals(transformationTO)){
			simulationName = transformationTO.getSimulationName();			
		}
		Map<String, List<RDFNode>> queryResultMap = new LinkedHashMap<String, List<RDFNode>>();
		InputStream queryIs = null;
		InputStream scriptIS = null;

		if (transformationTO!=null) {
			
			queryIs = transformationDAO.findQueryByTransformationName(transformationTO.getName());
			scriptIS = transformationDAO.findScriptByTransformationName(transformationTO.getName());
		
		} else {
			throw new RuntimeException("Transformation was not selected. Please, try again.");
		}
		

		TransformationSimulationTO transformationSimulationTO = new TransformationSimulationTO();
		String queryStr = "";
		if(queryIs!=null){
			queryStr = BasicFileTools.extractText(queryIs);			
		}
		
		String scriptStr = "";
		if(scriptIS!=null){
			scriptStr = BasicFileTools.extractText(scriptIS);			
		}
		
		
		
		transformationSimulationTO.setQueryStr(queryStr);
		transformationSimulationTO.setScriptStr(scriptStr);
		transformationSimulationTO.setxName(xName);
		transformationSimulationTO.setyName(yName);
		transformationSimulationTO.setParameters(transformationTO
				.getParameters());
		transformationSimulationTO.setTransformationName(transformationTO
				.getName());
		
		Boolean isInvalidSemantificationFileList = (semantificationFiles==null || semantificationFiles.isEmpty());
		if(isInvalidSemantificationFileList){
			transformationSimulationTO
			.setDatasourceTempPathByProjectNameList(BasicFileTools
					.locateDatasourceTempPath(workspace, simulationName));				
		}
		
		DataSetHashCacheTO cache = null;
        Map<String, String> attributes = getAttributesFromQueryAndScriptService(queryStr,scriptStr);

        cache = retrieveCacheIfExists(projectName,simulationName,xName,yName);
        if(cache==null || (cache.getViewTO().getxView().isEmpty() || cache.getViewTO().getyView().isEmpty())){
        	cache = retrieveCacheIfExists(projectName,simulationName);
        	
        }
        
        if(cache!=null && !cache.getViewTO().getDimValMap().isEmpty()){
        	return cache.getViewTO();	
        }

        
		
		if(cache==null || cache.getViewTO()==null || 
				(cache.getViewTO().getxView().isEmpty() || cache.getViewTO().getyView().isEmpty() &&
						cache.getViewTO().getDimValMap().isEmpty())){
			transformationSimulationTO.setWorkspace(workspace);
			queryResultMap = genericJenaQuery.findGenericGraph(transformationSimulationTO,attributes,semantificationFiles,transformationTO.getProjectName(),transformationTO.getSimulationName(),transformationTO);
			
			if(queryResultMap==null || queryResultMap.isEmpty()){
				if(transformationTO!=null && transformationTO.getNamedGraphs()!=null && !transformationTO.getNamedGraphs().isEmpty()){
					queryStr = new Util().addNamedGraphsToQuery(queryStr, transformationTO.getNamedGraphs());
					transformationSimulationTO.setQueryStr(queryStr);
					queryResultMap = genericJenaQuery.findGenericGraph(transformationSimulationTO,attributes,semantificationFiles,transformationTO.getProjectName(),transformationTO.getSimulationName(),transformationTO);					
				}
			}
			
			if(queryResultMap!=null && !queryResultMap.isEmpty()){
				if(xName!=null && yName!=null){
					Map<String, String> cacheToInsert = executeService(new Util().getValues(queryResultMap,queryStr),scriptStr,new BasicFileTools()
					.getImagePath(workspace, projectName, simulationName, transformationTO.getName()).getCanonicalPath());
					DataSetHashCacheTO dataSetHashCacheTO = new DataSetHashCacheTO();
					dataSetHashCacheTO = dataSetHashCacheDAO.insert(workspace,transformationTO.getProjectName(),transformationTO.getSimulationName(),xName,yName,cacheToInsert);
					return dataSetHashCacheTO.getViewTO();
				}else{
					Map<String, List<String>> cacheToInsert = new Util().getValues(queryResultMap,queryStr);
					DataSetHashCacheTO dataSetHashCacheTO = new DataSetHashCacheTO();
					dataSetHashCacheTO = dataSetHashCacheDAO.insert(workspace,transformationTO.getProjectName(),transformationTO.getSimulationName(),xName,yName,cacheToInsert,Boolean.TRUE);
					return dataSetHashCacheTO.getViewTO();				
				}
				
			}

		}else{
			return cache.getViewTO();
		}

		throw new RuntimeException("Fatal! The method retrieveDataView at Transformation class, cant return null.");
	}

	private DataSetHashCacheTO retrieveCacheIfExists(String projectName,String simulationName) {
		DataSetHashCacheTO cache = dataSetHashCacheDAO.retrieveDataSetCacheByCompositeKey(projectName,simulationName);
		return cache;
	}

	@Override
	public Map<String, String> executeService(Map<String, List<String>> result, String scriptStr, String imagePath) throws Exception{
		 StringBuilder sb = new StringBuilder();
			List<String> usedKey = new ArrayList<String>();
			for (Map.Entry<String, List<String>> entry : result.entrySet()) {
				if (usedKey.contains(entry.getKey())) {
					continue;
				}
				usedKey.add(entry.getKey());
				
				sb.append(entry.getKey()+"_" + " = " + entry.getValue() + "\n");
			}

			sb.append("\n");
			Map<String, String> map = new TreeMap<String, String>();
			synchronized (map) {
				
				String valueAsStr = sb.toString();
				if(valueAsStr.contains(" ")){
					valueAsStr = valueAsStr.replace(" ","");
				}
				if(valueAsStr.contains("[[")){
					valueAsStr = valueAsStr.replace("[[","[");
				}
				if(valueAsStr.contains("]]")){
					valueAsStr = valueAsStr.replace("]]","]");
				}
				
				sb.append(scriptStr);
				String scriptPath = imagePath.substring(0,imagePath.indexOf(".jpg"));
				scriptPath = scriptPath+".py";
				
				File scriptFile = new File(scriptPath);
				
				FileUtils.writeStringToFile(scriptFile, valueAsStr,
						Boolean.FALSE);
				String executionStr = resourceInjectionService.getPythonExe().getFile().getCanonicalPath() +" "+ scriptFile.getCanonicalPath();
				map = CommandLineTools.runCommandAndGetMap(executionStr);
			}
			return map;
	}


	private DataSetHashCacheTO retrieveCacheIfExists(String projectName,String simulationName, String xName, String yName) {
		if(xName==null || yName==null){
			return null;
		}
		DataSetHashCacheTO cache = dataSetHashCacheDAO.retrieveDataSetCacheByCompositeKey(projectName,simulationName, xName, yName);
		return cache;
	}
	

	
	@Override
	public void saveOrUpdateService(TransformationTO transformationTO, String workspacePath,String simulationName)
			throws Exception {
		if(transformationTO==null && simulationName!=null){
			List<TransformationTO> transformationList = findAllService();
			for(TransformationTO transformation:transformationList){
				if(transformation.getIsInUse()!=null && transformation.getIsInUse()){
					transformationTO = transformation;
				}
			}
			transformationTO.setSimulationName(simulationName);
			transformationDAO.saveOrUpdate(transformationTO);	
			return;
		}
		transformationDAO.saveOrUpdate(transformationTO);
	}

	@Override
	public TransformationTO findByNameService(String name) throws Exception {
		return transformationDAO.findByName(name);
	}

	@Override
	public List<TransformationTO> findAllByPartialNameService(String query)
			throws Exception {
		return transformationDAO.findAllByPartialName(query);
	}

	@Override
	public List<TransformationTO> findAllService() throws Exception {
		return transformationDAO.findAll();
	}

	
	@Override
	public String generateGraphicService(List<String> datax, String varNamx, List<String> datay, String varNamy, String plotName,String basePath) throws Exception {
		String imageLocation = featureExtractorService.plot2dGFeatService(datax, varNamx, datay, varNamy,
				plotName, basePath);
		return imageLocation;
	}

	@Override
	public List<String> findByTransformationNameService(String transformationName)
			throws Exception {
		List<String> result = transformationDAO
				.findAllParametersByTransformationName(transformationName);
		return result;
	}

	@Override
	public void updateService(TransformationTO transformationTO) throws Exception {
		transformationDAO.update(transformationTO);
	}

	@Override
	public List<TransformationTO> findAllByProjectNameService(String name)
			throws Exception {
		return transformationDAO.findAllByProjectName(name);
	}

	@Override
	public String generateGraphicService(Map<String, List<String>> dimValMap,
			String imagePath, String transformationName, String xName,
			String yName) throws Exception {
			//
		TransformationTO transformationTO = transformationDAO.findByName(transformationName); 
		InputStream scriptIS = null;
		if (transformationTO.getName() != null
				&& transformationTO.getProjectName() != null
				&& transformationTO.getSimulationName() != null) {
			scriptIS = transformationDAO.findScriptByProjectAndSimulationName(
					transformationTO.getName(),
					transformationTO.getProjectName(),
					transformationTO.getSimulationName());
			if(scriptIS==null){
				scriptIS = transformationDAO.findScriptByTransformationName(transformationTO.getName());
			}
		}
		
		Map<String, String> executedMap = new LinkedHashMap<String, String>();
		if(scriptIS!=null){
			executedMap = executeService(dimValMap, BasicFileTools.extractText(scriptIS),imagePath);
		}
		
		List<String> xdata = Collections.EMPTY_LIST;
		List<String> ydata = Collections.EMPTY_LIST;
		
		if(executedMap.isEmpty()){
			xdata = getDataFromMap(dimValMap,xName);
			ydata = getDataFromMap(dimValMap,yName);			
		}else{
			xdata = transformStringToList(executedMap.get(0),xName);
			ydata = transformStringToList(executedMap.get(1),yName);
		}
		
		
		if(xdata.isEmpty() || ydata.isEmpty()){
			//TODO throw exception
			System.err.println("Data not fount for given variables: "+xName+" and "+yName+". ");
		}
		
		//(List<Double> datax, String varNamx, List<Double> datay, String varNamy, String plotName,String basePath)
		String imageLocation = generateGraphicService(xdata,xName,ydata,yName,transformationName,imagePath);
		return imageLocation;
		
		
	}

	private List<String> transformStringToList(String resultPyList, String varName) {
		List<String> result = new LinkedList<String>();
		String[] resultArr = null;
		if(resultPyList!=null && !"".equals(resultPyList)){
			if(resultPyList.contains("[")){
				resultPyList = resultPyList.replace("[", "");
			}
			if(resultPyList.contains("]")){
				resultPyList = resultPyList.replace("]", "");
			}
			if(resultPyList.contains(",")){
				resultArr = resultPyList.split(",");
			}
		}
		
		List<String> resultList = Arrays.asList(resultArr);
		
		
		for(String data:resultList){
			data = data.replace("'","");
			result.add(data);
			
		}
		return result;
	}

	private List<String> getDataFromMap(Map<String, List<String>> dimValMap,String variableNameIn) {
		List<String> result = new ArrayList<String>();
		for(Entry<String, List<String>> entry: dimValMap.entrySet()){
			String variableName = entry.getKey();
			if(StringUtils.equalsIgnoreCase(variableNameIn,variableName)){
				String value = entry.getValue().toString();
				
				if(value.contains("[")){
					value = value.replace("[","");
				}
				if(value.contains("]")){
					value = value.replace("]","");
				}
				if(value.contains("'")){
					value = value.replace("'","");
				}
				for(String data:value.split(";")){
						result.add(data);									
				}
			}
		}
		return result;
	}

	@Override
	public void sendMessageService(List<AsyncTripleStoreInsertMessageTO> asyncTripleStoreInsertMessageList) throws Exception {
		for(AsyncTripleStoreInsertMessageTO asyncTripleStoreInsertMessageTO:asyncTripleStoreInsertMessageList){
			tripleStoreSenderService.sendMessageService(asyncTripleStoreInsertMessageTO);
		}
	}

	@Override
	public InputStream findScriptByTransformationNameService(String transformationName)
			throws Exception{
		return transformationDAO.findScriptByTransformationName(transformationName);
	}

	@Override
	public InputStream findQueryByTransformationNameService(String transformationName)
			throws Exception{
		return transformationDAO.findQueryByTransformationName(transformationName);
	}

	@Override
	public TransformationTO findByProjectNameAndIsInUseService(String projectName,String simulationName) {
		return transformationDAO.findByProjectNameAndIsInUse(projectName,simulationName);
	}

	@Override
	public String generatePlyFor3DGraphicService(List<String> getxView,
			String xName, List<String> getyView, String yName,
			String transformationName, List<String> list, String canonicalPath,
			String string) {
		
		
		return null;
	}

	@Override
	public String generatePlyFor3DGraphicService(
			Map<String, List<String>> dimValMap, String canonicalPath,
			String transformationName, String xName, String yName, String zName) {
		
		
		
		return null;
	}
	
}
