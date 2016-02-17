package eu.sifem.dao.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import eu.sifem.model.to.ProjectSimulationTO;
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
	public void insert(ProjectSimulationTO projectSimulationTO) throws Exception {
		mongoOperations.insert(projectSimulationTO,ProjectSimulationTO.class.getSimpleName());
		System.out.println(projectSimulationTO.getId());
	}

	@Override
	public List<ProjectSimulationTO> findAll() {
		return mongoOperations.findAll(ProjectSimulationTO.class,ProjectSimulationTO.class.getSimpleName());
	}

	@Override
	public void update(ProjectSimulationTO projectSimulationTO) throws Exception {
		mongoOperations.remove(ProjectSimulationTO.class,ProjectSimulationTO.class.getSimpleName());
		mongoOperations.insert(ProjectSimulationTO.class,ProjectSimulationTO.class.getSimpleName());
	}

	@Override
	public void delete(ProjectSimulationTO projectSimulationTO) throws Exception {
		mongoOperations.remove(ProjectSimulationTO.class,ProjectSimulationTO.class.getSimpleName());
	}
	


}
