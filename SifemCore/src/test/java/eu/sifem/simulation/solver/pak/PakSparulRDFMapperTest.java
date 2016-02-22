package eu.sifem.simulation.solver.pak;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import eu.sifem.model.to.SimulationInstanceTO;
import eu.sifem.service.IPakRDFMapperService;

@ContextConfiguration(locations = { "classpath:SifemCore-applicationContext.xml" })
public class PakSparulRDFMapperTest  extends AbstractTestNGSpringContextTests{
	
	@Autowired
	private IPakRDFMapperService mapper;
	
	@Test(groups= { "default" },priority=0)
	public void testSimulationServicenotNull(){
		Assert.assertNotNull(mapper);
	}
	
	
	@Test(groups= { "default" },priority=1)
	public void testNewCase() throws Exception{
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");
        PakSparulRDFMapper mapper = ctx.getBean(PakSparulRDFMapper.class);

		String caseDirPath = "src/test/resources/tmp/PAKC/caseDir/";
		String simulationName = "SimulationOne";
		String instanceName = "Instance_0";
		String simulationURLPrefix = "http://www.sifemontologies.com/ontologies/Box.owl#";
		String projectName = "ProjectOne";

		
		String simulationNamePrefix = "bw";
		String prefixesFile = "C:/SifemWindowsResourceFiles/ConceptualModel/PAK/utils/newPrefixesFile.txt";		
		mapper.setFemModelName(simulationName);
		mapper.setFemModelNamePrefix(simulationNamePrefix);
		String workspaceBasePath = "src/test/resources/tmp/PAKC/workspace/";
		SimulationInstanceTO simulationInstanceTO = new SimulationInstanceTO();
		simulationInstanceTO.setInstanceName(instanceName);
		simulationInstanceTO.setProjectName(projectName);

		simulationInstanceTO.setSimulationName(simulationName);
		simulationInstanceTO.setSimulationURLPrefix(simulationURLPrefix);
		simulationInstanceTO.setWorkspacebasePath(workspaceBasePath);
		
		mapper.setPrefixFile(prefixesFile);
		File[] files = (new File(caseDirPath)).listFiles();
		for(File file : files){

			
			if(StringUtils.endsWith(file.getName(),".dat")){	
				simulationInstanceTO.setFileName("dat_input.ttl");
				mapper.datToRDFService(simulationInstanceTO,new FileInputStream(file));
				System.out.println("DONE " + file.getName());
			} 
			if(StringUtils.endsWith(file.getName(),".unv")){
				simulationInstanceTO.setFileName("unv_output.ttl");
				mapper.unvToRDFService(simulationInstanceTO,new FileInputStream(file));
				System.out.println("DONE " + file.getName());
			}
		}
		
		Assert.assertNotNull(simulationInstanceTO);

		
	}
	

	public IPakRDFMapperService getMapper() {
		return mapper;
	}

	public void setMapper(IPakRDFMapperService mapper) {
		this.mapper = mapper;
	}

	
	
}
