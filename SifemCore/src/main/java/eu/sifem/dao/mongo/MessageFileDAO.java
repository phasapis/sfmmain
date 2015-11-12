package eu.sifem.dao.mongo;

import java.io.FileInputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSFile;

import eu.sifem.model.to.MessageFileTO;
import eu.sifem.model.to.TransformationTO;
import eu.sifem.service.dao.IGenericJenaQueryDAOService;
import eu.sifem.service.dao.IMessageFileDAOService;



@Repository
public class MessageFileDAO implements IMessageFileDAOService{
	
	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private IGenericJenaQueryDAOService genericJenaQuery;
	
	@Autowired 
	public GridFsTemplate fsTemplate;
	

	@Override
	public String saveMessageFile(MessageFileTO messageFile){
		 DBObject metaData = new BasicDBObject();
		 metaData.put("queryFileName",messageFile.getFileName());
		 metaData.put("queryFileType","ttl");
		 GridFSFile gfsfQuery = fsTemplate.store(messageFile.getFisMessageFile(),messageFile.getFileName(), metaData);
		 ObjectId queryStreamID =  (ObjectId) gfsfQuery.getId();
		 messageFile.set_id(queryStreamID);
		 mongoOperations.insert(messageFile,MessageFileTO.class.getSimpleName());
		 return queryStreamID.toString();
	}
	
	
	

}
