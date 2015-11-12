package eu.sifem.dao.mongo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import eu.sifem.model.to.DataSetHashCacheTO;
import eu.sifem.model.to.ViewTO;
import eu.sifem.service.dao.IDataSetHashCacheDAOService;
import eu.sifem.utils.Util;

@Repository
public class DataSetHashCacheDAO implements IDataSetHashCacheDAOService{
	
	private static final String CASE_INSENSITIVE = "i";

	@Autowired
	private MongoOperations mongoOperations;
	
	
	@Override
	public Boolean existDataSetCache(DataSetHashCacheTO hash) {
		Query query = new Query();
		query.addCriteria(Criteria.where("hash").is(hash.getHash()));
		DataSetHashCacheTO cache = mongoOperations.findOne(query, DataSetHashCacheTO.class,DataSetHashCacheTO.class.getSimpleName());
		return cache!=null;
	}

	
	@Override
	public DataSetHashCacheTO retrieveDataSetCacheByHash(String hash) {
		Query query = new Query();
		query.addCriteria(Criteria.where("hash").is(hash));
		DataSetHashCacheTO cache = mongoOperations.findOne(query, DataSetHashCacheTO.class,DataSetHashCacheTO.class.getSimpleName());
		return cache;
	}


	@Override
	public DataSetHashCacheTO retrieveDataSetCacheByCompositeKey(String projectName, String simulationName, String xName,String yName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("xName").regex(xName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("yName").regex(yName, CASE_INSENSITIVE));
		DataSetHashCacheTO cache = mongoOperations.findOne(query, DataSetHashCacheTO.class,DataSetHashCacheTO.class.getSimpleName());
		return cache;
	}
	


	@Override
	public DataSetHashCacheTO insert(String workspace, String projectName, String simulationName, String xName, String yName,Map<String, String> cacheToInsert) throws Exception {
		DataSetHashCacheTO dataSetHashCacheTO = new DataSetHashCacheTO();
		dataSetHashCacheTO.setWorkspace(workspace);
		dataSetHashCacheTO.setProjectName(projectName);
		dataSetHashCacheTO.setSimulationName(simulationName);
		
		String x = xName==null || "".endsWith(xName)?"":xName;
		String y = yName==null || "".endsWith(yName)?"":yName;
		String z = "";
		dataSetHashCacheTO.setxName(x);
		dataSetHashCacheTO.setyName(y);
		dataSetHashCacheTO.setzName(z);
		dataSetHashCacheTO.setHash(Util.generateHashCacheID(dataSetHashCacheTO));
		
		ViewTO viewTO = new ViewTO();
		
                System.err.println(" --- in insert DaatsetCache");
		//TODO convert string to List for each object
		if(cacheToInsert.get("x")!=null && !"".equals(cacheToInsert.get("x"))){
			viewTO.getxView().addAll(Arrays.asList(cacheToInsert.get("x").split(",")));			
		}
		if(cacheToInsert.get("0")!=null && !"".equals(cacheToInsert.get("0"))){
			viewTO.getxView().addAll(Arrays.asList(cacheToInsert.get("0").split(",")));			
		}
		if(cacheToInsert.get("y")!=null && !"".equals(cacheToInsert.get("y"))){
			viewTO.getyView().addAll(Arrays.asList(cacheToInsert.get("y").split(",")));			
		}
		if(cacheToInsert.get("1")!=null && !"".equals(cacheToInsert.get("1"))){
			viewTO.getyView().addAll(Arrays.asList(cacheToInsert.get("1").split(",")));			
		}

		
		
		if(viewTO.getxView()==null || viewTO.getyView()==null || viewTO.getxView().isEmpty() || viewTO.getyView().isEmpty()){
                    System.err.println(" --- in insert DaatsetCache empty stuff");
			//TODO create addAll for the map case
			for(Map.Entry<String, String> entry: cacheToInsert.entrySet()){
				String value = entry.getValue();
				if(value.contains("[")){
					value = value.replace("[","");
				}
				if(value.contains("]")){
					value = value.replace("]","");
				}
				if(value.contains("'")){
					value = value.replace("'","");
				}
				viewTO.getDimValMap().put(entry.getKey(),Arrays.asList(entry.getValue().split(",")));
			}
			
		}
		
                System.err.println("cacheToInsert: " + cacheToInsert);
		dataSetHashCacheTO.setViewTO(viewTO);
		mongoOperations.insert(dataSetHashCacheTO,DataSetHashCacheTO.class.getSimpleName());
		return retrieveDataSetCacheByCompositeKey(projectName, simulationName, xName, yName);
		
	}


	@Override
	public DataSetHashCacheTO retrieveDataSetCacheByCompositeKey(String projectName, String simulationName) {
		Query query = new Query();
		if(projectName==null || simulationName==null){
			return null;
		}
		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE));
		List<DataSetHashCacheTO> caches = mongoOperations.find(query, DataSetHashCacheTO.class,DataSetHashCacheTO.class.getSimpleName());
		for(DataSetHashCacheTO cacheTO:caches){
			if(cacheTO.getViewTO()!=null && !cacheTO.getViewTO().getDimValMap().isEmpty()){
				return cacheTO;
			}
		}
		return null;
	}


	@Override
	public DataSetHashCacheTO insert(String projectName, String simulationName, Map<String, List<String>> cacheToInsert) throws Exception {
			DataSetHashCacheTO dataSetHashCacheTO = new DataSetHashCacheTO();
			dataSetHashCacheTO.setProjectName(projectName);
			dataSetHashCacheTO.setSimulationName(simulationName);

			dataSetHashCacheTO.setHash(Util.generateHashCacheID(dataSetHashCacheTO));
			
			ViewTO viewTO = new ViewTO();
			
			//TODO convert string to List for each object
			viewTO.setDimValMap(cacheToInsert);
			dataSetHashCacheTO.setViewTO(viewTO);
			
			mongoOperations.insert(dataSetHashCacheTO,DataSetHashCacheTO.class.getSimpleName());
			return retrieveDataSetCacheByCompositeKey(projectName, simulationName);
	}

	@Override
	public DataSetHashCacheTO insert(String workspace, String projectName,
			String simulationName, String xName, String yName,
			Map<String, List<String>> cacheToInsert,
			Boolean isMultipleAndRawAttributes) throws Exception {
			if(!isMultipleAndRawAttributes){
				return null;
			}

			
			DataSetHashCacheTO dataSetHashCacheTO = new DataSetHashCacheTO();
			dataSetHashCacheTO.setWorkspace(workspace);
			dataSetHashCacheTO.setProjectName(projectName);
			dataSetHashCacheTO.setSimulationName(simulationName);

			dataSetHashCacheTO.setHash(Util.generateHashCacheID(dataSetHashCacheTO));
			
			ViewTO viewTO = new ViewTO();
			
			//TODO convert string to List for each object
			viewTO.setDimValMap(cacheToInsert);
			dataSetHashCacheTO.setViewTO(viewTO);
			
			mongoOperations.insert(dataSetHashCacheTO,DataSetHashCacheTO.class.getSimpleName());
			return retrieveDataSetCacheByCompositeKey(projectName, simulationName, xName, yName);
	}

	@Override
	public DataSetHashCacheTO retrieveXandYDataSetCacheByProjectAndSimulationName(String projectName, String simulationName) {
		Query query = new Query();
		query
		.addCriteria(Criteria.where("projectName").regex("PROJECTTEST", CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex("SIMULATIONUNITTEST", CASE_INSENSITIVE))
		.addCriteria(Criteria.where("viewTO.xView").exists(Boolean.TRUE))
		.addCriteria(Criteria.where("viewTO.yView").exists(Boolean.TRUE))
		.addCriteria(Criteria.where("viewTO.dimValMap.collection.value").exists(Boolean.FALSE));
		DataSetHashCacheTO cache = mongoOperations.findOne(query, DataSetHashCacheTO.class,DataSetHashCacheTO.class.getSimpleName());
		return cache;			
	}
	
	@Override
	public DataSetHashCacheTO retrieveDataSetCacheBySelectedParameters(String parameterX, String parmeterY) {

		//First query, prepared data cache with properly x and y names
		Query query = new Query();
		query
		.addCriteria(Criteria.where("xName").regex(parameterX, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("yName").regex(parmeterY, CASE_INSENSITIVE));
		DataSetHashCacheTO cache = mongoOperations.findOne(query, DataSetHashCacheTO.class,DataSetHashCacheTO.class.getSimpleName());
		
		if(cache!=null){
			return cache;			
		}
		
		//Then query generic data cache without properly well defined into DataSetHashCacheTO, x and y names
		List<DataSetHashCacheTO> caches = mongoOperations.findAll(DataSetHashCacheTO.class, DataSetHashCacheTO.class.getSimpleName());
		for(DataSetHashCacheTO cacheTO:caches){
			if(cacheTO.getViewTO()!=null && !cacheTO.getViewTO().getDimValMap().isEmpty()){
				Map<String,List<String>> cacheMap = cacheTO.getViewTO().getDimValMap();
				if(cacheMap.containsKey(parameterX) && cacheMap.containsKey(parameterX)){
					return cacheTO;
				}
				
			}
		}
		return null;
	}


	@Override
	public void insert(DataSetHashCacheTO dataSetHashCacheTO) {
		mongoOperations.insert(dataSetHashCacheTO,DataSetHashCacheTO.class.getSimpleName());
	}


	@Override
	public DataSetHashCacheTO retrieveDataSetCacheBySelectedParametersAndFrequency(
			String parameterXStr, String parameterYStr, String frequency) {

		//First query, prepared data cache with properly x and y names
		Query query = new Query();

		if(frequency!=null && !"".equals(frequency)){
			query
			.addCriteria(Criteria.where("xName").regex(parameterXStr, CASE_INSENSITIVE))
			.addCriteria(Criteria.where("yName").regex(parameterYStr, CASE_INSENSITIVE))
			.addCriteria(Criteria.where("frequency").regex(frequency, CASE_INSENSITIVE));
		}else{
			query
			.addCriteria(Criteria.where("xName").regex(parameterXStr, CASE_INSENSITIVE))
			.addCriteria(Criteria.where("yName").regex(parameterYStr, CASE_INSENSITIVE));			
		}
		
		DataSetHashCacheTO cache = mongoOperations.findOne(query, DataSetHashCacheTO.class,DataSetHashCacheTO.class.getSimpleName());
		
		return cache;					
	}


	@Override
	public DataSetHashCacheTO insert(String projectName, String simulationName,String instanceName, Map<String, List<String>> values) {
		DataSetHashCacheTO dataSetHashCacheTO = new DataSetHashCacheTO();
		ViewTO viewTO = new ViewTO();
		dataSetHashCacheTO.setProjectName(projectName);
		dataSetHashCacheTO.setSimulationName(simulationName);
		dataSetHashCacheTO.setInstanceName(instanceName);
		
		Query query = new Query();
		query
		.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("instanceName").regex(instanceName, CASE_INSENSITIVE));
		DataSetHashCacheTO cache = mongoOperations.findOne(query, DataSetHashCacheTO.class,DataSetHashCacheTO.class.getSimpleName());
		mongoOperations.remove(cache, DataSetHashCacheTO.class.getSimpleName());

                System.err.println(" ---- Q=" + query.toString() );
                //System.err.println(" values.size=" + values.size() + " | " + values.keySet());

		for(Map.Entry<String,List<String>> entry: values.entrySet()){
			                    
			String name = entry.getKey();
                        
                        
                        System.err.print(" -- Name:" + name);
                        System.err.println(" -- Values:" + entry.getValue().get(1));
                        
			List<String> listValues = entry.getValue();
			if(name==null || "".equals(name) || listValues==null || listValues.isEmpty()){
				continue;
			}
			viewTO.getDimValMap().put(entry.getKey(),listValues);
			System.err.println("  ----- " + entry.getKey() + " " + listValues.size());
		}
                //System.err.println(" ---- " + viewTO.getDimValMap());
		dataSetHashCacheTO.setViewTO(viewTO);
		mongoOperations.insert(dataSetHashCacheTO,DataSetHashCacheTO.class.getSimpleName());				
		DataSetHashCacheTO cacheAsResult = mongoOperations.findOne(query, DataSetHashCacheTO.class,DataSetHashCacheTO.class.getSimpleName());
                
                System.err.println(" ----------- QueryString=" + query.toString() );
                System.err.println(" ----------- cacheAsResult=" + cacheAsResult.getProjectName() + " X=" + cacheAsResult.getxName() + " Y=" + cacheAsResult.getxName());
                
		return cacheAsResult;	
	}
	
	//TODO Create test case
//	public static void main(String[] args) {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");
//        DataSetHashCacheDAO dao = ctx.getBean(DataSetHashCacheDAO.class);
//        System.out.println(dao.retrieveDataSetCacheBySelectedParameters("id","xcoord").getProjectName());
//	}

}
