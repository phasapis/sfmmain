package eu.sifem.dao;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import eu.sifem.model.to.TransformationTO;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.dao.ITransformationDAOService;



@ContextConfiguration(locations={"classpath:SifemCore-applicationContext.xml"})
public class TransformationDAOTest extends AbstractTestNGSpringContextTests{
	

	private static final String SIMULATION_NAME = "simulationNameJUnitTest";
	
	private static final String TRANSFORMATION_NAME = "transformationNameJUnitTest";
	
	private static final String QUERY_NAME = "C:/SifemWindowsResourceFiles/workspace/simulationName/output/transformations/queryFileNameJUnitTest";
	
	private static final String SCRIPT_NAME = "C:/SifemWindowsResourceFiles/workspace/simulationName/output/transformations/scriptFileNameJUnitTest";
	
	private static final String PARAMETERS_NAME_LIST = "a;b;c";
	
	

	@Autowired
	private IResourceInjectionService resourceInjectionService;
	
	@Autowired
	private ITransformationDAOService transformationDAOService;
	
	
	@Test(groups= { "default" })
	public void testServiceIsNotNull() throws Exception{
		Assert.assertNotNull(transformationDAOService);
	}
	
	@Test(groups= { "default" },enabled=true)
	public void testInsert() throws Exception{
		try{
	        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");
	        ITransformationDAOService transformationDAO = (ITransformationDAOService) ctx.getBean("transformationDAO");
			
	        TransformationTO transformationTO = new TransformationTO();
	        transformationTO.setIsInUse(Boolean.TRUE);
	        transformationTO.setParameters(Arrays.asList(new String[]{"ids;nodes;translationXs;translationY;translationZ;xCoords;yCoord;zCoord"}));
	        transformationTO.setName("Displacement");
	        byte[] pyArr = FileUtils.readFileToByteArray(new File("C:/SifemWindowsResourceFiles/workspace/projectOne/Greenwood/output/transformations/script/Greenwood.py"));
	        
	        //TODO uncomment
//	        transformationTO.setScript(""+pyArr+"");
	        byte[] queryArr = FileUtils.readFileToByteArray(new File("C:/SifemWindowsResourceFiles/workspace/projectOne/Greenwood/output/transformations/sparql/Greenwood.sparql"));
	        //TODO uncomment
//	        transformationTO.setQuery(""+queryArr+"");
	        transformationTO.setProjectName("projectOne");
	        transformationDAO.saveOrUpdate(transformationTO);
	        
	        
	        TransformationTO trans = transformationDAO.findByName("Displacement");
	        System.out.println(trans.getName());
	        Assert.assertNotNull(trans);
	        
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	@Test(groups= { "default" },enabled=true)
	public void testFindByName() throws Exception{
		try{
	        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");
	        ITransformationDAOService transformationDAO = (ITransformationDAOService) ctx.getBean("transformationDAO");

	        TransformationTO trans = transformationDAO.findByName("Displacement");
	        System.out.println(trans.getName());
	        Assert.assertNotNull(trans);
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	@Test(groups= { "default" },enabled=true)
	public void testAllByPartialName() throws Exception{
		try{
		    ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");
		    ITransformationDAOService transformationDAO = (ITransformationDAOService) ctx.getBean("transformationDAO");

		    List<TransformationTO> transList = transformationDAO.findAllByPartialName("Dis");
		    Assert.assertNotNull(transList);
		    Assert.assertNotSame(transList,Collections.EMPTY_LIST);
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	

}
