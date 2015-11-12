package eu.sifem.dao.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import eu.sifem.model.to.SolverTO;
import eu.sifem.model.to.TransformationTO;
import eu.sifem.service.dao.ISolverDAOService;

/**
 * 
 * @author jbjares
 * 
 */
@Repository
public class SolverDAO implements ISolverDAOService {

	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public List<SolverTO> findAll() {
		return mongoOperations.findAll(SolverTO.class,SolverTO.class.getSimpleName());
	}


	@Override
	public List<SolverTO> findAllByType(String type) throws Exception {
		List<SolverTO> result = new ArrayList<SolverTO>(); 
		List<SolverTO> solverList =  mongoOperations.findAll(SolverTO.class,SolverTO.class.getSimpleName());
		for(SolverTO solver:solverList){
			if(StringUtils.containsIgnoreCase(solver.getType(), type)){
				result.add(solver);
			}
		}
		return result;
	}


	@Override
	public List<String> findTypeByName(String name) throws Exception {	
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		SolverTO solverTO = mongoOperations.findOne(query, SolverTO.class,SolverTO.class.getSimpleName());
		if(solverTO.getType()==null || "".equals(solverTO.getType())){
			return Collections.EMPTY_LIST;
		}
		if(solverTO.getType().contains(";")){
			return Arrays.asList(solverTO.getType().split(";"));			
		}
		List<String> result = new ArrayList<String>();
		result.add(solverTO.getType());
		return result;
	}
	
	@Override
	public List<String> findMethodByName(String name) throws Exception {	
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		SolverTO solverTO = mongoOperations.findOne(query, SolverTO.class,SolverTO.class.getSimpleName());
		if(solverTO.getMethod()==null || "".equals(solverTO.getMethod())){
			return Collections.EMPTY_LIST;
		}
		if(solverTO.getMethod().contains(";")){
			return Arrays.asList(solverTO.getMethod().split(";"));			
		}
		List<String> result = new ArrayList<String>();
		result.add(solverTO.getType());
		return result;
	}

	

	
	public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");
        ISolverDAOService solverDAO = (SolverDAO) ctx.getBean("solverDAO");
        
        List<String> result = solverDAO.findMethodByName("PAK");
        for(String solver:result){
        	System.out.println(solver);
        }

        System.out.println("DONE");
	
	}


}
