package eu.sifem.dao.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;


import eu.sifem.model.to.ParameterTO;
import eu.sifem.service.dao.IParameterDAOService;


/**
 * 
 * @author jbjares
 * 
 */
@Repository
public class ParameterDAO implements IParameterDAOService{


	@Autowired
	private MongoOperations mongoOperations;
	
	
	@Override
	public List<ParameterTO> findAllParameters() throws Exception {
		List<ParameterTO> parameterTOResult = mongoOperations.findAll(ParameterTO.class,ParameterTO.class.getSimpleName());
                
		return parameterTOResult;

	}

}
