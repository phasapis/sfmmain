package eu.sifem.dao.mongo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
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

import eu.sifem.model.to.TransformationTO;
import eu.sifem.service.dao.IGenericJenaQueryDAOService;
import eu.sifem.service.dao.ITransformationDAOService;




/**
 * 
 * @author jbjares
 * 
 */
@Repository
public class TransformationDAO implements ITransformationDAOService{
	
	private static final String CASE_INSENSITIVE = "i";

	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private IGenericJenaQueryDAOService genericJenaQuery;
	
	@Autowired 
	public GridFsTemplate fsTemplate;

	
	@Override
	public void saveOrUpdate(TransformationTO transformationTO) throws Exception {
		
		TransformationTO newTransformationTO = new TransformationTO();
		newTransformationTO = transformationTO;

		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(transformationTO.getName(), CASE_INSENSITIVE));
		transformationTO = findByName(transformationTO.getName());
		String idForRemoval = null;
		if(transformationTO!=null){
			idForRemoval = transformationTO.get_id().toString();
			//mongoOperations.remove(query,TransformationTO.class.getSimpleName());			
			BeanUtils.copyProperties(newTransformationTO,transformationTO);			
		}else{
			newTransformationTO.setIsInUse(Boolean.FALSE);			
		}
		
		
		 DBObject metaDataQuery = new BasicDBObject();
		 metaDataQuery.put("queryFileName",newTransformationTO.getName()+"Query");
		 metaDataQuery.put("queryFileType","sparql");
		 if(newTransformationTO!=null && newTransformationTO.getName()!=null && newTransformationTO.getQueryFile()==null){
			 InputStream queryIS = findQueryByTransformationName(newTransformationTO.getName());
			 if(queryIS!=null){
				 GridFSFile gfsfQuery = fsTemplate.store(queryIS, newTransformationTO.getName()+"Query",metaDataQuery);
				 ObjectId queryStreamID = (ObjectId) gfsfQuery.getId();
				 newTransformationTO.setQueryByteArrayID(queryStreamID);
			 }
		 }else{
			 InputStream fis = newTransformationTO.getQueryFile();   
			 GridFSFile gfsfQuery = fsTemplate.store(fis, newTransformationTO.getName()+"Query",metaDataQuery);
			 ObjectId queryStreamID = (ObjectId) gfsfQuery.getId();
			 newTransformationTO.setQueryByteArrayID(queryStreamID);
		 }
		 
		 DBObject metaDataScript = new BasicDBObject();
		 metaDataScript.put("scriptFileName",newTransformationTO.getName()+"Script");
		 metaDataScript.put("scriptFileType","py");
		 
		 if(newTransformationTO!=null && newTransformationTO.getName()!=null && newTransformationTO.getQueryFile()==null){
			 InputStream scriptIS = findScriptByTransformationName(newTransformationTO.getName());
			 if(scriptIS!=null){
				 GridFSFile gfsfScript = fsTemplate.store(scriptIS,newTransformationTO.getName()+"Script", metaDataScript);
				 ObjectId scriptStreamID = (ObjectId) gfsfScript.getId();
				 newTransformationTO.setScriptByteArrayID(scriptStreamID);
			 }
		 }else{
			 InputStream fisScript = newTransformationTO.getScriptFile();   
			 GridFSFile gfsfScript = fsTemplate.store(fisScript,newTransformationTO.getName()+"Script", metaDataScript);
			 ObjectId scriptStreamID =  (ObjectId) gfsfScript.getId();
			 newTransformationTO.setScriptByteArrayID(scriptStreamID);
		 }
	     
		 
		 
		
		if(transformationTO!=null && !"".equals(idForRemoval)){
			Query removeQuery = new Query();
			removeQuery.addCriteria(Criteria.where("_id").regex(idForRemoval, CASE_INSENSITIVE));
			mongoOperations.remove(query,TransformationTO.class.getSimpleName());	
			newTransformationTO.set_id(null);
		}
		
		mongoOperations.insert(newTransformationTO,TransformationTO.class.getSimpleName());
	}


	@Override
	public TransformationTO findByName(String name) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(name, CASE_INSENSITIVE));
		return mongoOperations.findOne(query, TransformationTO.class,TransformationTO.class.getSimpleName());
	}
	
	@Override
	public List<TransformationTO> findByListName(List<String> names) throws Exception {
		List<TransformationTO> result = new ArrayList<TransformationTO>();
		for(String name:names){
			Query query = new Query();
			query.addCriteria(Criteria.where("name").regex(name, CASE_INSENSITIVE));
			TransformationTO transformation = mongoOperations.findOne(query, TransformationTO.class,TransformationTO.class.getSimpleName());
			result.add(transformation);
		}
		return result;
	}


	@Override
	public List<TransformationTO> findAll() throws Exception {
		return mongoOperations.findAll(TransformationTO.class,TransformationTO.class.getSimpleName());
	}


	@Override
	public List<TransformationTO> findAllByPartialName(String partialName)throws Exception {
		List<TransformationTO> result = new ArrayList<TransformationTO>();
		List<TransformationTO> resultListToFilter = mongoOperations.findAll(TransformationTO.class,TransformationTO.class.getSimpleName());
		for(TransformationTO transformationTO: resultListToFilter){
			if(StringUtils.containsIgnoreCase(transformationTO.getName(), partialName)){
				result.add(transformationTO);
			}
		}
		return result;
	}


	@Override
	public void update(TransformationTO transformationTO) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(transformationTO.getName(), CASE_INSENSITIVE));
		mongoOperations.remove(query,TransformationTO.class.getSimpleName());
		saveOrUpdate(transformationTO);
	}


	@Override
	public void delete(TransformationTO transformationTO) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(transformationTO.getName(), CASE_INSENSITIVE));
		mongoOperations.remove(query,TransformationTO.class.getSimpleName());
	}


	@Override
	public List<String> findAllParametersByTransformationName(String selectedTransformationName) throws Exception {
		 List<String> result = new  ArrayList<String>();
			Query query = new Query();
			query.addCriteria(Criteria.where("name").regex(selectedTransformationName, CASE_INSENSITIVE));
		TransformationTO transformationTO = mongoOperations.findOne(query, TransformationTO.class,TransformationTO.class.getSimpleName());

		for(String name:transformationTO.getParameters()){
			if(name.contains(";")){
				result.addAll(Arrays.asList(name.split(";")));
			}
			if(!name.contains(";")){
				result.add(name);
			}
		}
		return result;
	}


	@Override
	public List<TransformationTO> findAllByProjectName(String projectName)throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE));
		return mongoOperations.find(query,TransformationTO.class,TransformationTO.class.getSimpleName());
	}

	@Override
	public InputStream findScriptByTransformationName(String transformationName) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(transformationName, CASE_INSENSITIVE));
		TransformationTO transformationTO = mongoOperations.findOne(query, TransformationTO.class,TransformationTO.class.getSimpleName());
		if(transformationTO==null || transformationTO.getScriptByteArrayID()==null || "".equals(transformationTO.getScriptByteArrayID())){
			return null;
		}
		ObjectId scriptStreamID = transformationTO.getScriptByteArrayID();
		GridFSDBFile file = fsTemplate.findOne(new Query(Criteria.where("_id").is(scriptStreamID)));
		return file.getInputStream();
	}
	
	@Override
	public InputStream findQueryByTransformationName(String transformationName) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(transformationName, CASE_INSENSITIVE));
		TransformationTO transformationTO = mongoOperations.findOne(query, TransformationTO.class,TransformationTO.class.getSimpleName());
		if(transformationTO==null || transformationTO.getQueryByteArrayID()==null || "".equals(transformationTO.getQueryByteArrayID())){
			return null;
		}
		ObjectId queryStreamID = transformationTO.getQueryByteArrayID();
		GridFSDBFile file = fsTemplate.findOne(new Query(Criteria.where("_id").is(queryStreamID)));
		return file.getInputStream();
	}


	@Override
	public InputStream findScriptByProjectAndSimulationName(String transformationName,String projectName, String simulationName) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(transformationName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE));
		TransformationTO transformationTO = mongoOperations.findOne(query, TransformationTO.class,TransformationTO.class.getSimpleName());
		if(transformationTO==null || transformationTO.getScriptByteArrayID()==null || "".equals(transformationTO.getScriptByteArrayID())){
			return null;
		}
		ObjectId scriptStreamID = transformationTO.getScriptByteArrayID();
		GridFSDBFile file = fsTemplate.findOne(new Query(Criteria.where("_id").is(scriptStreamID)));
		return file.getInputStream();
	}
	
	@Override
	public InputStream findQueryByProjectAndSimulationName(String transformationName,String projectName, String simulationName) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(transformationName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE));
		TransformationTO transformationTO = mongoOperations.findOne(query, TransformationTO.class,TransformationTO.class.getSimpleName());
		if(transformationTO==null || transformationTO.getQueryByteArrayID()==null || "".equals(transformationTO.getQueryByteArrayID())){
			return null;
		}
		ObjectId queryStreamID = transformationTO.getQueryByteArrayID();
		GridFSDBFile file = fsTemplate.findOne(new Query(Criteria.where("_id").is(queryStreamID)));
		return file.getInputStream();
	}
	
	//TODO remove
//	@Override
//	public Map<String, List<Double>> findTransformationGraphMap(TransformationSimulationTO transformationSimulationTO) throws Exception {
//		Map<String, List<Double>> xyMapresult = new TreeMap<String, List<Double>>();
//		String transformationName = transformationSimulationTO.getTransformationName();
//		List<Double> datax = new ArrayList<Double>();
//		List<Double> datay = new ArrayList<Double>();
//		if(transformationName!=null && !"".equals(transformationName) && StringUtils.equalsIgnoreCase("displacement",transformationName)){
//			Map<String, List<RDFNode>> result = genericJenaQuery.findDisplacementGraph(transformationSimulationTO);
//			
//			//TODO python script representation. Must change for the merge between wuery result and scrpit.py
//			
//			List<RDFNode> nodes = result.get("node");
//			List<RDFNode> xCoords = result.get("xCoord");
//			List<RDFNode> translationXs = result.get("translationX");
//			List<RDFNode> subDomains = result.get("subDomain");
//			List<RDFNode> ids = result.get("id");
//			
//			Map<RDFNode, Float> nodeXDisplacement = new HashMap<RDFNode, Float>();
//			Map<Integer, RDFNode> idNodeMap = new HashMap<Integer, RDFNode>();
//			Map<RDFNode, Float> nodeXCoord = new HashMap<RDFNode, Float>();
//
//			int size = subDomains.size();
//			for(int i=0; i<size; i++){			
//				Float displacementX =  translationXs.get(i).asLiteral().getFloat();
//				Float currentXCoord = xCoords.get(i).asLiteral().getFloat();
//				System.out.println(currentXCoord);
//				RDFNode node = nodes.get(i);
//				RDFNode id = ids.get(i);
//				int nodeNumber = id.asLiteral().getInt();
//				nodeXDisplacement.put(node, displacementX);
//				nodeXCoord.put(node, currentXCoord);
//				//nodesCovered.add(node);
//				idNodeMap.put(nodeNumber, node);
//			}
//			
//			System.out.println("Final Nodes Remaining: " + idNodeMap.size());
//
//			List<Integer> nodeSortedIds = new ArrayList<Integer>();
//			for(int i : idNodeMap.keySet()) 
//				nodeSortedIds.add(i);
//			Collections.sort(nodeSortedIds);
//			Float previousXCoord = Float.MIN_VALUE;		
//			for(Integer id : nodeSortedIds){
//				RDFNode node = idNodeMap.get(id);
//				Float displacement = nodeXDisplacement.get(node);
//				Float currentXCoord = nodeXCoord.get(node);
//				if(currentXCoord > previousXCoord){
//					datax.add(currentXCoord.doubleValue());
//					datay.add(displacement.doubleValue());
//					previousXCoord = currentXCoord;				
//				}
//			}
//		}
//		xyMapresult.put("x",datax);
//		xyMapresult.put("y",datay);
//		return xyMapresult;
//	}
	
	
	//TODO create testcase.
//	public static void main(String[] args) throws Exception {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");
//        ITransformationDAOService transformationDAO = (ITransformationDAOService) ctx.getBean("transformationDAO");
//        
//        MongoOperations mongoOperations = (MongoOperations) ctx.getBean(MongoOperations.class);
//        List<String> params = transformationDAO.findAllParametersByTransformationName("Displacement");
//        for(String param:params){
//        	System.out.println(param);
//        }
//
//        System.out.println("DONE");
//	}


	@Override
	public TransformationTO findByProjectNameAndIsInUse(String projectName,String simulationName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("projectName").regex(projectName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex(simulationName, CASE_INSENSITIVE))
		.addCriteria(Criteria.where("isInUse").regex("true", CASE_INSENSITIVE));
		TransformationTO transformationTO = mongoOperations.findOne(query, TransformationTO.class,TransformationTO.class.getSimpleName());
		if(transformationTO==null || transformationTO.getQueryByteArrayID()==null || "".equals(transformationTO.getQueryByteArrayID())){
			return null;
		}

		return transformationTO;
	}


	//TODO waiting for queries quick fix that asked to Kartik.
	@Override
	public List<String> findAllTempParameters() {
		List<String> result = new ArrayList<String>();
		result.add("DistanceFromTheCochleaApex");
		result.add("FrequencyAtStaples");
		result.add("BasilarMembraneMagnitude");
		result.add("DistanceFromTheCochleaBasis");
		
		//TODO rename it properly
		result.add("ZValueForDisplacement");
		return result;
	}


	
	
	//TODO create test unit
//	public static void main(String[] args) throws Exception {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");
//        ITransformationDAOService transformationDAO = (ITransformationDAOService) ctx.getBean("transformationDAO");
//        
//        MongoOperations mongoOperations = (MongoOperations) ctx.getBean(MongoOperations.class);
//        List<String> params = transformationDAO.findAllParametersByTransformationName("Displacement");
//        for(String param:params){
//        	System.out.println(param);
//        }
//
//        System.out.println("DONE");
//	}
	
	
}
