package eu.sifem.dao.mongo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

import eu.sifem.model.to.DanAndUnvQueryResultTO;
import eu.sifem.model.to.DatAndUnvSolverTO;
import eu.sifem.service.dao.IDatAndUnvSolverDAOService;
import eu.sifem.service.dao.IGenericJenaQueryDAOService;


/**
 * 
 * @author jbjares
 * 
 */
@Repository
public class DatAndUnvSolverDAO implements IDatAndUnvSolverDAOService{

	private static final String CASE_INSENSITIVE = "i";

	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private IGenericJenaQueryDAOService genericJenaQuery;
	
	@Autowired 
	public GridFsTemplate fsTemplate;

	
	@Override
	public void insert(DatAndUnvSolverTO datAndUnvSolverTO) throws Exception {
		
		 DBObject metaDataDat = new BasicDBObject();
		 metaDataDat.put("fileName","Pak");
		 metaDataDat.put("fileType","dat");
	     InputStream fisDat = datAndUnvSolverTO.getDatFile();   
		 GridFSFile gfsfDatFile = fsTemplate.store(fisDat,"Pak", metaDataDat);
		 ObjectId datStreamID = (ObjectId) gfsfDatFile.getId();
		 datAndUnvSolverTO.setDatFileID(datStreamID);
		 
		 DBObject metaDataUnv = new BasicDBObject();
		 metaDataUnv.put("fileName","Pak");
		 metaDataUnv.put("fileType","unv");
		 InputStream fisUnv = datAndUnvSolverTO.getUnvFile();   
		 GridFSFile gfsfUnvFile = fsTemplate.store(fisUnv,"Pak", metaDataUnv);
		 ObjectId unvStreamID = (ObjectId) gfsfUnvFile.getId();
		 datAndUnvSolverTO.setUnvFileID(unvStreamID);
		 	 
		mongoOperations.insert(datAndUnvSolverTO,DatAndUnvSolverTO.class.getSimpleName());
	}
	
	@Override
	public List<DanAndUnvQueryResultTO> findDatAndUnvFiles(String projectName,String simulationName, String instanceName) throws Exception {
		List<DanAndUnvQueryResultTO> result = new ArrayList<DanAndUnvQueryResultTO>();
		Query query = new Query();
		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("instanceName").regex(instanceName, CASE_INSENSITIVE));
		List<DatAndUnvSolverTO> datAndUnvFileTOList = mongoOperations.find(query, DatAndUnvSolverTO.class,DatAndUnvSolverTO.class.getSimpleName());
		if(datAndUnvFileTOList==null || datAndUnvFileTOList.isEmpty()){
			return null;
		}
		for(DatAndUnvSolverTO datAndUnvSolverTO:datAndUnvFileTOList){
			ObjectId datId = datAndUnvSolverTO.getDatFileID();
			ObjectId unvId = datAndUnvSolverTO.getUnvFileID();
			GridFSDBFile datFile = fsTemplate.findOne(new Query(Criteria.where("_id").is(datId)));
			String instanceCode = instanceName.substring(instanceName.indexOf("_")+1,instanceName.length());
			System.err.println(" --- dat filename" + datFile.getFilename());
			DanAndUnvQueryResultTO danAndUnvQueryResultDAT = new DanAndUnvQueryResultTO();
			danAndUnvQueryResultDAT.setInstanceName(instanceName);
			danAndUnvQueryResultDAT.setInstanceCode(instanceCode);
			danAndUnvQueryResultDAT.setFileName("dat");
			danAndUnvQueryResultDAT.setFile(datFile.getInputStream());
			result.add(danAndUnvQueryResultDAT);
			
			GridFSDBFile unvFile = fsTemplate.findOne(new Query(Criteria.where("_id").is(unvId)));
                        System.err.println(" --- unv filename" + unvFile.getFilename());
			DanAndUnvQueryResultTO danAndUnvQueryResultUNV = new DanAndUnvQueryResultTO();
			danAndUnvQueryResultUNV.setInstanceName(instanceName);
			danAndUnvQueryResultUNV.setInstanceCode(instanceCode);
			danAndUnvQueryResultUNV.setFileName("unv");
			danAndUnvQueryResultUNV.setFile(unvFile.getInputStream());
			result.add(danAndUnvQueryResultUNV);
		}
		return result;
	}
	
	@Override
	public InputStream findDatFileByProjectName(String projectName) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE));
		List<DatAndUnvSolverTO> datAndUnvFileTOList = mongoOperations.find(query, DatAndUnvSolverTO.class,DatAndUnvSolverTO.class.getSimpleName());
		if(datAndUnvFileTOList==null || datAndUnvFileTOList.isEmpty()){
			return null;
		}
		for(DatAndUnvSolverTO datAndUnvSolverTO:datAndUnvFileTOList){
			ObjectId datId = datAndUnvSolverTO.getDatFileID();
			GridFSDBFile datFile = fsTemplate.findOne(new Query(Criteria.where("_id").is(datId)));
			return datFile.getInputStream();
		}
		
		throw new RuntimeException(".dat File not found for given project name: "+ projectName);
	}
	
	@Override
	public InputStream findDatFileByProjectName(String projectName,
			String simulationName, String instanceName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("instanceName").regex(instanceName, CASE_INSENSITIVE));
		List<DatAndUnvSolverTO> datAndUnvFileTOList = mongoOperations.find(query, DatAndUnvSolverTO.class,DatAndUnvSolverTO.class.getSimpleName());
		if(datAndUnvFileTOList==null || datAndUnvFileTOList.isEmpty()){
			return null;
		}
		for(DatAndUnvSolverTO datAndUnvSolverTO:datAndUnvFileTOList){
			ObjectId datId = datAndUnvSolverTO.getDatFileID();
			GridFSDBFile datFile = fsTemplate.findOne(new Query(Criteria.where("_id").is(datId)));
			return datFile.getInputStream();
		}
		
		throw new RuntimeException(".dat File not found for given project name: "+ projectName);
	}
	
	@Override
	public InputStream findUnvFileByProjectName(String projectName,String simulationName, String instanceName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("instanceName").regex(instanceName, CASE_INSENSITIVE));
		List<DatAndUnvSolverTO> datAndUnvFileTOList = mongoOperations.find(query, DatAndUnvSolverTO.class,DatAndUnvSolverTO.class.getSimpleName());
		if(datAndUnvFileTOList==null || datAndUnvFileTOList.isEmpty()){
			return null;
		}
		for(DatAndUnvSolverTO datAndUnvSolverTO:datAndUnvFileTOList){
			ObjectId unvID = datAndUnvSolverTO.getUnvFileID();
			GridFSDBFile unvFile = fsTemplate.findOne(new Query(Criteria.where("_id").is(unvID)));
			return unvFile.getInputStream();
		}
		
		throw new RuntimeException(".dat File not found for given project name: "+ projectName);
	}
	
	
	

	@Override
	public void removeAll(String projectName, String simulationName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE));
		mongoOperations.remove(query, DatAndUnvSolverTO.class,DatAndUnvSolverTO.class.getSimpleName());
		
	}


	
}
