package eu.sifem.dao;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test(groups= { "default" })
@ContextConfiguration(locations={"classpath:SifemCore-applicationContext.xml"})
public class ParameterDAOTest extends AbstractTestNGSpringContextTests{
	
	
//	@Autowired
//	private IResourceInjectionService resourceInjectionService;
//	
//	@Autowired
//	private IParameterDAOService parameterDAOService;
//	
//	@Test(groups= { "default" })
//	public void testServiceIsNotNull() throws Exception{
//		Assert.assertNotNull(parameterDAOService);
//	}
//	
//	@Test(groups= { "default" })
//	public void testTransformationDAOServiceInsertAndQuery() throws Exception{
//		String randomName = "name_"+UUID.randomUUID();
//		parameterDAOService.insert(new ParameterTO(randomName));
//		
//		ParameterTO  parameterTO = parameterDAOService.findByName(randomName);
//		Assert.assertEquals(randomName, parameterTO.getName());
//	}
//	
//	@Test(groups= { "default" })
//	public void testTransformationDAOServiceFindAll() throws Exception{
//		List<ParameterTO> result = parameterDAOService.findAll();
//		for(ParameterTO parameterTO:result){
//			Assert.assertTrue(parameterTO.getName().trim().toUpperCase().startsWith("name".trim().toUpperCase()));
//		}
//	}
//	
	


	

}
