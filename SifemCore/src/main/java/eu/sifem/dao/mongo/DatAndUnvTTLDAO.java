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
import eu.sifem.model.to.DatAndUnvTTLTO;
import eu.sifem.service.dao.IDatAndUnvTTLDAOService;
import eu.sifem.service.dao.IGenericJenaQueryDAOService;


/**
 * 
 * @author jbjares
 * 
 */
@Repository
public class DatAndUnvTTLDAO implements IDatAndUnvTTLDAOService{

	private static final String CASE_INSENSITIVE = "i";

	@Autowired
	private MongoOperations mongoOperations;
	
	
	@Autowired 
	public GridFsTemplate fsTemplate;

	
	@Override
	public void insert(DatAndUnvTTLTO datAndUnvTTLTO,String fileName) throws Exception {
		System.out.println(datAndUnvTTLTO);
		System.out.println(fileName);
		if(datAndUnvTTLTO.getDatFile()!=null){
			 DBObject metaDataDat = new BasicDBObject();
			 metaDataDat.put("fileName","Pak");
			 metaDataDat.put("fileType","dat");
		     InputStream fisDat = datAndUnvTTLTO.getDatFile();   
			 GridFSFile gfsfDatFile = fsTemplate.store(fisDat,fileName, metaDataDat);
			 ObjectId datStreamID = (ObjectId) gfsfDatFile.getId();
			 datAndUnvTTLTO.setDatFileID(datStreamID);
			 datAndUnvTTLTO.setType("dat");
		}

		if(datAndUnvTTLTO.getUnvFile()!=null){
			 DBObject metaDataUnv = new BasicDBObject();
			 metaDataUnv.put("fileName","Pak");
			 metaDataUnv.put("fileType","unv");
			 InputStream fisUnv = datAndUnvTTLTO.getUnvFile();   
			 GridFSFile gfsfUnvFile = fsTemplate.store(fisUnv,fileName, metaDataUnv);
			 ObjectId unvStreamID = (ObjectId) gfsfUnvFile.getId();
			 datAndUnvTTLTO.setUnvFileID(unvStreamID);
			 datAndUnvTTLTO.setType("unv");
		}

		 	 
		mongoOperations.insert(datAndUnvTTLTO,DatAndUnvTTLTO.class.getSimpleName());
	}
	
	@Override
	public InputStream findDatFileByProjectName(String projectName,
			String simulationName, String instanceName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("instanceName").regex(instanceName, CASE_INSENSITIVE));
		List<DatAndUnvTTLTO> datAndUnvFileTOList = mongoOperations.find(query, DatAndUnvTTLTO.class,DatAndUnvTTLTO.class.getSimpleName());
		if(datAndUnvFileTOList==null || datAndUnvFileTOList.isEmpty()){
			return null;
		}
                
		ObjectId datId = datAndUnvFileTOList.get(0).getDatFileID();
		GridFSDBFile datFile = fsTemplate.findOne(new Query(Criteria.where("_id").is(datId)));
                System.err.println(datFile.getFilename() + " " + datId.toString());
		return datFile.getInputStream();
		
	}
	
	@Override
	public InputStream findUnvFileByProjectName(String projectName,String simulationName, String instanceName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("instanceName").regex(instanceName, CASE_INSENSITIVE));
		List<DatAndUnvTTLTO> datAndUnvFileTOList = mongoOperations.find(query, DatAndUnvTTLTO.class,DatAndUnvTTLTO.class.getSimpleName());
		if(datAndUnvFileTOList==null || datAndUnvFileTOList.isEmpty()){
			return null;
		}
		ObjectId unvID = datAndUnvFileTOList.get(1).getUnvFileID();
		GridFSDBFile unvFile = fsTemplate.findOne(new Query(Criteria.where("_id").is(unvID)));
                System.err.println(unvFile.getFilename() + " " + unvFile.toString());                
		return unvFile.getInputStream();
		
	}
	



	
}
