package eu.sifem.dao;

import java.util.List;
import java.util.UUID;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import eu.sifem.model.to.ParameterTO;
import eu.sifem.model.to.SolverTO;
import eu.sifem.model.to.TransformationTO;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.dao.IParameterDAOService;
import eu.sifem.service.dao.ISolverDAOService;
import eu.sifem.service.dao.ITransformationDAOService;



@ContextConfiguration(locations={"classpath:SifemCore-applicationContext.xml"})
public class SolverDAOTest extends AbstractTestNGSpringContextTests{
	
	@Autowired
	private ISolverDAOService solverDAOService;
	
	@Test(groups= { "default" })
	public void testServiceIsNotNull() throws Exception{
		Assert.assertNotNull(solverDAOService);
	}
	
	@Test(groups= { "default" })
	public void testFindByTypePAK() throws Exception{
		List<SolverTO> solverList = solverDAOService.findAllByType("PAK");
		Assert.assertNotNull(solverList);
		Assert.assertFalse(solverList.isEmpty());
		Assert.assertEquals(1,solverList.size());
		Assert.assertEquals("a",solverList.get(0).getMethods().get(0));
		Assert.assertEquals("b",solverList.get(0).getMethods().get(1));
		Assert.assertEquals("c",solverList.get(0).getMethods().get(2));
	}
	
	@Test(groups= { "default" })
	public void testFindByTypeFoam() throws Exception{
		List<SolverTO> solverList = solverDAOService.findAllByType("OpenFoam");
		Assert.assertNotNull(solverList);
		Assert.assertFalse(solverList.isEmpty());
		Assert.assertEquals(1,solverList.size());
		Assert.assertEquals("1",solverList.get(0).getMethods().get(0));
		Assert.assertEquals("2",solverList.get(0).getMethods().get(1));
		Assert.assertEquals("3",solverList.get(0).getMethods().get(2));
	}

	
	


	

}
