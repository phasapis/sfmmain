package eu.sifem.service.dao;

import java.util.List;

import eu.sifem.model.to.SolverTO;


/**
 * 
 * @author jbjares
 * 
 */
public interface ISolverDAOService {

	List<SolverTO> findAll();
	
	public List<String> findTypeByName(String name) throws Exception;

	List<SolverTO> findAllByType(String type) throws Exception;

	List<String> findMethodByName(String name) throws Exception;

}
