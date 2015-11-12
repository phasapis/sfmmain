package eu.sifem.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Test(groups= { "default" })
@ContextConfiguration(locations={"classpath:SifemCore-applicationContext.xml"})
public class MongoGrdiFsTempleTest extends AbstractTestNGSpringContextTests{

	@Autowired 
	public GridFsTemplate template;
	 
	 @Test 
	 public void shouldListExistingFiles() throws FileNotFoundException {
		 
		 DBObject metaData = new BasicDBObject();
		 metaData.put("meta1lename","name");
		 metaData.put("meta2ntentType","type");
	     FileInputStream fis = new FileInputStream(new File("C:/SifemWindowsResourceFiles/workspace/DisplacementTransformationSample/sparql/Displacement.sparql"));   
		 GridFSFile gfsf = template.store(fis, "filename", metaData);
		 ObjectId id =  (ObjectId) gfsf.getId();
		 System.out.println(gfsf);
		 GridFSDBFile file = template.findOne(new Query(Criteria.where("_id").is(id)));
		 System.out.println(file);
	 }
	
	
}
