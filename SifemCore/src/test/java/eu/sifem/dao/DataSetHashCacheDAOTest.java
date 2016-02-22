package eu.sifem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import eu.sifem.model.to.DataSetHashCacheTO;
import eu.sifem.service.dao.IDataSetHashCacheDAOService;


@Test(groups= { "default" })
@ContextConfiguration(locations={"classpath:SifemCore-applicationContext_test.xml"})
public class DataSetHashCacheDAOTest  extends AbstractTestNGSpringContextTests{

	private static final String CASE_INSENSITIVE = "i";

	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private IDataSetHashCacheDAOService dataSetHashCacheDAOService;
	
	@Test(groups= { "default" })
	public void retrieveXandYDataSetCacheByProjectAndSimulationNameTest() {
		Query query = new Query();
		query
		.addCriteria(Criteria.where("projectName").regex("PROJECTTEST", CASE_INSENSITIVE))
		.addCriteria(Criteria.where("simulationName").regex("SIMULATIONUNITTEST", CASE_INSENSITIVE))
		.addCriteria(Criteria.where("viewTO.xView").exists(Boolean.TRUE))
		.addCriteria(Criteria.where("viewTO.yView").exists(Boolean.TRUE))
		.addCriteria(Criteria.where("viewTO.dimValMap.collection.value").exists(Boolean.FALSE));
		//.addCriteria(Criteria.where("viewTO.yView.collection.value").gt(0));
		//.addCriteria(Criteria.where("viewTO.dimValMap.value").exists(Boolean.TRUE));
		DataSetHashCacheTO cache = mongoOperations.findOne(query, DataSetHashCacheTO.class,DataSetHashCacheTO.class.getSimpleName());
		Assert.assertNotNull(cache);		
	}
	

}
