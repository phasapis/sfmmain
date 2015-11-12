package eu.sifem.visualization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import virtuoso.jena.driver.VirtDataset;
import eu.sifem.model.to.DataSetHashCacheTO;
import eu.sifem.model.to.DataSetTO;
import eu.sifem.service.IVisualizationService;
import eu.sifem.utils.Util;



@ContextConfiguration(locations = { "classpath:SifemCore-applicationContext.xml" })
public class VisualizationServiceTest extends AbstractTestNGSpringContextTests{
	
	@Autowired
	private VirtDataset virtDataset;
	
	@Autowired
	private IVisualizationService visualizationService;
	
	
	@Test(groups= { "default" }, enabled=true)
	public void testFindAllBySimulationName() throws Exception{
//		DataSetTO dataSetTO = new DataSetTO();
//		fillDataSetTO(dataSetTO);
//		ViewTO viewTO = visualizationService.retrieveDataView(dataSetTO,null, Collections.EMPTY_LIST,null,null,null);
//		System.out.println(viewTO);
		

	}


	private void fillDataSetTO(DataSetTO dataSetTO) throws Exception {
		DataSetHashCacheTO cache = new DataSetHashCacheTO();
		cache.setProjectName("projectOne");
		cache.setSimulationName("displacement");
		cache.setxName("ids");
		cache.setyName("translationXs");
		cache.setHash(Util.generateHashCacheID(cache));
		dataSetTO.setNamedGraph("http://www.sifemontologies.com/ontologies/BoxModel#"+cache.getProjectName()+"_"+cache.getSimulationName());
		dataSetTO.setCache(cache);
		return;
	}

	
	
}
