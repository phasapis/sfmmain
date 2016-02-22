package eu.sifem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.dao.ISimulationDAOService;


@Test(groups= { "default" })
@ContextConfiguration(locations={"classpath:SifemCore-applicationContext.xml"})
public class SimulationDAOTest extends AbstractTestNGSpringContextTests{
	
	
	@Autowired
	private IResourceInjectionService resourceInjectionService;
	
	@Autowired
	private ISimulationDAOService simulationDAOService;
	
	@Test(groups= { "default" })
	public void testServiceIsNotNull() throws Exception{
		Assert.assertNotNull(simulationDAOService);
	}
		

}
