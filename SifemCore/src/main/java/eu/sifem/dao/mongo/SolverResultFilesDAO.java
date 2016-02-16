package eu.sifem.dao.mongo;

import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSFile;

import eu.sifem.model.to.DatAndUnvSolverTO;
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
	
}
