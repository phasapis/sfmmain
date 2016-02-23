package eu.sifem.dao.mongo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import eu.sifem.model.to.ProjectSimulationTO;
import eu.sifem.model.to.TransformationTO;
import eu.sifem.service.dao.ISimulationDAOService;



/**
 * 
 * @author jbjares
 * 
 */
@Repository
public class SimulationDAO implements ISimulationDAOService{
	
	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public ProjectSimulationTO findByName(String name) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		return mongoOperations.findOne(query, ProjectSimulationTO.class,ProjectSimulationTO.class.getSimpleName());
	}

	@Override
	public String insert(ProjectSimulationTO projectSimulationTO) throws Exception {
		mongoOperations.insert(projectSimulationTO,ProjectSimulationTO.class.getSimpleName());
		projectSimulationTO.setProjectSimulationID(projectSimulationTO.get_id().toString());
		return projectSimulationTO.get_id().toString();
	}

	@Override
	public List<ProjectSimulationTO> findAll() {
		return mongoOperations.findAll(ProjectSimulationTO.class,ProjectSimulationTO.class.getSimpleName());
	}

	@Override
	public void update(ProjectSimulationTO projectSimulationTO) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(projectSimulationTO.get_id()));

		Update update = new Update();
		
		update.set("resultGraphID", projectSimulationTO.getResultGraphID());
		update.set("projectName", projectSimulationTO.getProjectName());
		update.set("simulationName", projectSimulationTO.getSimulationName());
		update.set("transformations", projectSimulationTO.getTransformations());
		update.set("shinyHost", projectSimulationTO.getShinyHost());
		update.set("projectSimulationID", projectSimulationTO.getProjectSimulationID());
		update.set("simulationID", projectSimulationTO.getSimulationID());
		
		mongoOperations.findAndModify(query, update,
				new FindAndModifyOptions().returnNew(true), ProjectSimulationTO.class);
		
//		ObjectId _id = projectSimulationTO.get_id();
//		mongoOperations.remove(ProjectSimulationTO.class,ProjectSimulationTO.class.getSimpleName());
//		
//		projectSimulationTO.set_id(_id);
//		projectSimulationTO.setProjectSimulationID(_id.toString());
//		mongoOperations.insert(ProjectSimulationTO.class,ProjectSimulationTO.class.getSimpleName());
	}

	@Override
	public void delete(ProjectSimulationTO projectSimulationTO) throws Exception {
		mongoOperations.remove(ProjectSimulationTO.class,ProjectSimulationTO.class.getSimpleName());
	}

	@Override
	public ProjectSimulationTO findByID(ObjectId id) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		return mongoOperations.findOne(query, ProjectSimulationTO.class,ProjectSimulationTO.class.getSimpleName());
	}


}
