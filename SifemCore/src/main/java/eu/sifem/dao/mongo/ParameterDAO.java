package eu.sifem.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.google.gson.GsonBuilder;

import eu.sifem.model.to.ParameterTO;
import eu.sifem.model.to.TransformationTO;
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
