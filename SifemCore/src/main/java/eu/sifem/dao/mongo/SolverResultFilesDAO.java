package eu.sifem.dao.mongo;

import java.io.InputStream;
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

import eu.sifem.model.to.DatAndUnvSolverTO;
import eu.sifem.model.to.DatAndUnvTTLTO;
import eu.sifem.model.to.SolverResultFilesTO;
import eu.sifem.service.dao.IGenericJenaQueryDAOService;
import eu.sifem.service.dao.ISolverResultFilesDAO;


/**
 * 
 * @author jbjares
 * 
 */
@Repository
public class SolverResultFilesDAO implements ISolverResultFilesDAO{

		private static final String CASE_INSENSITIVE = "i";

		@Autowired
		private MongoOperations mongoOperations;
		
		@Autowired
		private IGenericJenaQueryDAOService genericJenaQuery;
		
		@Autowired 
		public GridFsTemplate fsTemplate;

		
		@Override
		public void insert(SolverResultFilesTO solverResultFilesTO) throws Exception {
			
			DBObject metaDatasolverResult = new BasicDBObject();
			 metaDatasolverResult.put("fileName","DcenterlineFile".trim().toLowerCase());
			 metaDatasolverResult.put("fileType","csv");
		     InputStream fisDat = solverResultFilesTO.getDcenterlineFile();  
			 GridFSFile gfsfDatFile = fsTemplate.store(fisDat,"DcenterlineFile".trim().toLowerCase(), metaDatasolverResult);
			 ObjectId DcenterlineStreamID = (ObjectId) gfsfDatFile.getId();
			 solverResultFilesTO.setDcenterlineFileID(DcenterlineStreamID);

			 
			 DBObject metaDatapImagFile = new BasicDBObject();
			 metaDatapImagFile.put("fileName","pImagFile".trim().toLowerCase());
			 metaDatapImagFile.put("fileType","csv");
		     InputStream fisDatpImagFile = solverResultFilesTO.getPimagFile();  
			 GridFSFile gfsfDatFilefisDatpImagFile = fsTemplate.store(fisDatpImagFile,"pImagFile".trim().toLowerCase(), metaDatapImagFile);
			 ObjectId pImagFileStreamID = (ObjectId) gfsfDatFilefisDatpImagFile.getId();
			 solverResultFilesTO.setPimagFileID(pImagFileStreamID);
			 
			 DBObject metaDatapRealFile = new BasicDBObject();
			 metaDatapRealFile.put("fileName","pRealFile".trim().toLowerCase());
			 metaDatapRealFile.put("fileType","csv");
		     InputStream fisDatpRealFile = solverResultFilesTO.getPrealFile();
			 GridFSFile gfsfDatpRealFile = fsTemplate.store(fisDatpRealFile,"pRealFile".trim().toLowerCase(), metaDatapRealFile);
			 ObjectId pRealFileStreamID = (ObjectId) gfsfDatpRealFile.getId();
			 solverResultFilesTO.setPrealFileID(pRealFileStreamID);
			 
			 
			 DBObject metaDatavMagn = new BasicDBObject();
			 metaDatavMagn.put("fileName","vMagn".trim().toLowerCase());
			 metaDatavMagn.put("fileType","csv");
		     InputStream fisDatvMagn = solverResultFilesTO.getVmagnFile();  
			 GridFSFile gfsfDatFilefisDatvMagn = fsTemplate.store(fisDatvMagn,"vMagn".trim().toLowerCase(), metaDatavMagn);
			 ObjectId vMagnStreamID = (ObjectId) gfsfDatFilefisDatvMagn.getId();
			 solverResultFilesTO.setVmagnFileID(vMagnStreamID);
			 
			 
			 DBObject metaDatavPhase = new BasicDBObject();
			 metaDatavPhase.put("fileName","vPhase".trim().toLowerCase());
			 metaDatavPhase.put("fileType","csv");
		     InputStream fisDatvPhase = solverResultFilesTO.getVphaseFile(); 
			 GridFSFile gfsfDatFilevPhase  = fsTemplate.store(fisDatvPhase,"vPhase".trim().toLowerCase(), metaDatavPhase);
			 ObjectId streamID = (ObjectId) gfsfDatFilevPhase.getId();
			 solverResultFilesTO.setVphaseFileID(streamID);
			 

			 	 
			mongoOperations.insert(solverResultFilesTO,SolverResultFilesTO.class.getSimpleName());
		}


		@Override
		public SolverResultFilesTO findByProjectID(String projectID) {
			
				Query query = new Query();
				query.addCriteria(Criteria.where("projectID").is(projectID));
				SolverResultFilesTO solverResultTO = mongoOperations.findOne(query, SolverResultFilesTO.class,SolverResultFilesTO.class.getSimpleName());
				if(solverResultTO==null){
					return null;
				}
		                
				
				ObjectId dcenterlineFileID = solverResultTO.getDcenterlineFileID();
				GridFSDBFile dcenterlineFile = fsTemplate.findOne(new Query(Criteria.where("_id").is(dcenterlineFileID)));
				InputStream dcenterlineIS = dcenterlineFile.getInputStream();
				solverResultTO.setDcenterlineFile(dcenterlineIS);
				
				ObjectId pimagFileID = solverResultTO.getPimagFileID();
				GridFSDBFile pimagFile = fsTemplate.findOne(new Query(Criteria.where("_id").is(pimagFileID)));
				InputStream pimagIS = dcenterlineFile.getInputStream();
				solverResultTO.setPimagFile(pimagIS);
				
				ObjectId prealFileID = solverResultTO.getPrealFileID();
				GridFSDBFile prealFile = fsTemplate.findOne(new Query(Criteria.where("_id").is(prealFileID)));
				InputStream prealIS = prealFile.getInputStream();
				solverResultTO.setPrealFile(prealIS);
				
				
				ObjectId vmagnFileID = solverResultTO.getVmagnFileID();
				GridFSDBFile vmagnFile = fsTemplate.findOne(new Query(Criteria.where("_id").is(vmagnFileID)));
				InputStream vmagnIS = vmagnFile.getInputStream();
				solverResultTO.setVmagnFile(vmagnIS);
				
				
				ObjectId vphaseFileID = solverResultTO.getVphaseFileID();
				GridFSDBFile vphaseFile = fsTemplate.findOne(new Query(Criteria.where("_id").is(vphaseFileID)));
				InputStream vphaseIS = vphaseFile.getInputStream();
				solverResultTO.setVmagnFile(vphaseIS);
				
				
			
			return solverResultTO;
		}
	
}
