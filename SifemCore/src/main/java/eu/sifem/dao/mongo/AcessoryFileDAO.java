package eu.sifem.dao.mongo;

import java.io.InputStream;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

import eu.sifem.model.to.AcessoryFileTO;
import eu.sifem.model.to.SolverConfigCreatorTO;
import eu.sifem.service.dao.IAcessoryFileDAOService;
import eu.sifem.service.dao.IGenericJenaQueryDAOService;


/**
 * 
 * @author jbjares
 * 
 */
@Repository
public class AcessoryFileDAO implements IAcessoryFileDAOService{

	private static final String CASE_INSENSITIVE = "i";

	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private IGenericJenaQueryDAOService genericJenaQuery;
	
	@Autowired 
	public GridFsTemplate fsTemplate;

	
	@Override
	public String insert(AcessoryFileTO acessoryFileTO) throws Exception {
		 DBObject metaData = new BasicDBObject();
		 metaData.put("fileName",acessoryFileTO.getName());
		 metaData.put("fileType",acessoryFileTO.getExtension());
	     InputStream is = acessoryFileTO.getFile(); 
		 GridFSFile gfsfFile = fsTemplate.store(is,acessoryFileTO.getName(), metaData);
		 ObjectId fileID = (ObjectId) gfsfFile.getId();
		 acessoryFileTO.setFileId(fileID);
		 mongoOperations.insert(acessoryFileTO,AcessoryFileTO.class.getSimpleName());
		 
		 Query query = new Query();
			query.addCriteria(Criteria.where("projectName").regex(acessoryFileTO.getProjectName(), CASE_INSENSITIVE))
			.addCriteria(Criteria.where("simulationName").regex(acessoryFileTO.getSimulationName(), CASE_INSENSITIVE));
			acessoryFileTO = mongoOperations.findOne(query, AcessoryFileTO.class,AcessoryFileTO.class.getSimpleName());
			
		 return fileID.toString();
	}
	

	@Override
	public InputStream findAcessoryFile(String projectName,String simulationName,String acessoryFileName) throws Exception {
		InputStream result = null;
		Query query = new Query();
		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("name").regex(acessoryFileName, CASE_INSENSITIVE));
		List<AcessoryFileTO> acessoryFileTOList = mongoOperations.find(query, AcessoryFileTO.class,AcessoryFileTO.class.getSimpleName());
		if(acessoryFileTOList==null || acessoryFileTOList.isEmpty()){
			return null;
		}
		for(AcessoryFileTO acessoryFileTO:acessoryFileTOList){
			ObjectId id = acessoryFileTO.getFileId();
			GridFSDBFile file = fsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
			return file.getInputStream();
		}
		return result;
	}
	
	@Override
	public List<AcessoryFileTO> findAcessoryFileTO(String projectName,String simulationName,String acessoryFileName) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("name").regex(acessoryFileName, CASE_INSENSITIVE));
		List<AcessoryFileTO> acessoryFileTOList = mongoOperations.find(query, AcessoryFileTO.class,AcessoryFileTO.class.getSimpleName());
		if(acessoryFileTOList==null || acessoryFileTOList.isEmpty()){
			return null;
		}
		return acessoryFileTOList;
	}


	@Override
	public String updateAcessoryFile(InputStream acessoryFileIS,SolverConfigCreatorTO solverConfigCreatorTO, String parameterName,String extension) throws Exception {
		 DBObject metaData = new BasicDBObject();
		 metaData.put("fileName",parameterName);
		 metaData.put("fileType",extension);
	     InputStream is = acessoryFileIS;
		 GridFSFile gfsfFile = fsTemplate.store(is,parameterName, metaData);
		 ObjectId fileID = (ObjectId) gfsfFile.getId();
		 
		 List<AcessoryFileTO> acessoryFileTOList = findAcessoryFileTO(solverConfigCreatorTO.getProjectName(),solverConfigCreatorTO.getSimulationName(),parameterName);
		 if(acessoryFileTOList==null || acessoryFileTOList.isEmpty()){
			 return null;
		 }
		 AcessoryFileTO acessoryFileTO = acessoryFileTOList.get(0);
		 acessoryFileTO.setFileId(fileID);
		 mongoOperations.insert(acessoryFileTO,AcessoryFileTO.class.getSimpleName());
		
		return fileID.toString();
	}

	
	
//	@Override
//	public void updateAcessoryFile(InputStream acessoryFileIS,SolverConfigCreatorTO solverConfigCreatorTO, String parameterName,String extension) {
//		 DBObject metaData = new BasicDBObject();
//		 metaData.put("fileName",parameterName);
//		 metaData.put("fileType",extension);
//	     InputStream is = acessoryFileTO.getFile(); 
//		 GridFSFile gfsfFile = fsTemplate.store(is,acessoryFileTO.getName(), metaData);
//		 ObjectId fileID = (ObjectId) gfsfFile.getId();
//		 acessoryFileTO.setFileId(fileID);
//		 mongoOperations.insert(acessoryFileTO,AcessoryFileTO.class.getSimpleName());
//		
//		 Query query = new Query();
//			query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
//			.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE))
//			.addCriteria(Criteria.where("name").regex(parameterName, CASE_INSENSITIVE));
//		
//		Update update=new Update();
//		update.set("instanceName",configFileTO.getInstanceName());
//		mongoOperations.upsert(query,update, ConfigFileTO.class);
//		return configFileTO.get_id().toString();
//		
//	}


	
	
	//TODO remove code below, it's just for reference, by now.
	
//	@Override
//	public String updateInstanceName(ConfigFileTO configFileTO) throws Exception {
//		if(configFileTO.getInstanceName()==null || "".equals(configFileTO.getInstanceName()) || configFileTO.get_id()==null){
//			return null;
//		}
//		 Query query = new Query();
//			query.addCriteria(Criteria.where("projectName").regex(configFileTO.getProjectName(), CASE_INSENSITIVE))
//			.addCriteria(Criteria.where("simulationName").regex(configFileTO.getSimulationName(), CASE_INSENSITIVE));
//		
//		Update update=new Update();
//		update.set("instanceName",configFileTO.getInstanceName());
//		mongoOperations.upsert(query,update, ConfigFileTO.class);
//		 return configFileTO.get_id().toString();
//	}
	
//	@Override
//	public List<InputStream> findCFGFile(String projectName,String simulationName, String instanceName) throws Exception {
//		List<InputStream> result = new ArrayList<InputStream>();
//		Query query = new Query();
//		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
//		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE))
//		.addCriteria(Criteria.where("instanceName").regex(instanceName, CASE_INSENSITIVE));
//		List<ConfigFileTO> configFileTOList = mongoOperations.find(query, ConfigFileTO.class,ConfigFileTO.class.getSimpleName());
//		if(configFileTOList==null || configFileTOList.isEmpty()){
//			return null;
//		}
//		for(ConfigFileTO cfg:configFileTOList){
//			ObjectId id = cfg.getCfgFileID();
//			GridFSDBFile file = fsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
//			result.add(file.getInputStream());
//		}
//		return result;
//	}
//	
//
//	
//	
//	@Override
//	public List<ConfigFileTO> findConfigTO(String projectName,String simulationName) throws Exception {
//		Query query = new Query();
//		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
//		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE));
//		List<ConfigFileTO> configFileTOList = mongoOperations.find(query, ConfigFileTO.class,ConfigFileTO.class.getSimpleName());
//		return configFileTOList;
//	}
//
//
//	@Override
//	public void removeAll(String projectName, String simulationName) {
//		Query query = new Query();
//		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
//		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE));
//		mongoOperations.remove(query, ConfigFileTO.class,ConfigFileTO.class.getSimpleName());
//		
//	}
//
//	@Override
//	public InputStream findCFGFile(ConfigFileTO configFileTO) {
//		ObjectId id = configFileTO.getCfgFileID();
//		GridFSDBFile file = fsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
//		return file.getInputStream();
//	}
//
//	@Override
//	public void updateInstanceName(String projectName, String simulationName,String instanceName) {
//		if(instanceName==null || "".equals(instanceName) ){
//			return;
//		}
//		 Query query = new Query();
//			query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
//			.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE));
//		
//		Update update=new Update();
//		update.set("instanceName",instanceName);
//		mongoOperations.upsert(query,update, ConfigFileTO.class);
//	}

}
