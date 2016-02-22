package eu.sifem.simulation.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import eu.sifem.model.to.ProjectSimulationTO;
import eu.sifem.model.to.SimulationInstanceTO;
import eu.sifem.service.IPakRDFMapperService;
import eu.sifem.service.IPakSolverControlerService;

/**
 * 
 * @author jbjares/yaskha
 * 
 * Simulates the GUI "run" PAK-C button 
 * With just one necessary test scenario. 
 * 
 * The structure of directories is "workspace --> Project Name --> Scenario --> Simulation"
 * e.g. "C:/SifemWindowsResourceFiles/workspace/ --> projectOne/ --> Displacement/ --> simulationNumber_0/"
 * 
 * Input Directory is "C:/SifemWindowsResourceFiles/workspace/projectOne/Displacement/simulationNumber_0/"
 * Input File : input.cfg
 * 
 * The output is .dat and .unv files that are copied to folder listed below 
 * We can see the result at C:/SifemWindowsResourceFiles/workspace/projectOne/Displacement/simulationNumber_0/output/
 *
 */

@ContextConfiguration(locations={"classpath:SifemCore-applicationContext.xml"})
public class PakSolverControlerServiceTest extends AbstractTestNGSpringContextTests{

	
	private static final String UNV_FILE = "C:\\SifemWindowsResourceFiles\\workspace\\projectOne\\simulation\\instance_0\\ttl\\unv_output.ttl";

	private static final String DAT_FILE = "C:\\SifemWindowsResourceFiles\\workspace\\projectOne\\simulation\\instance_0\\ttl\\dat_input.ttl";

	private static final String OUTPUT_UNV = "src/test/resources/eu_sifem_simulation/SimulationWSTest/output/output.unv";
	private static final String OUTPUT_DAT = "src/test/resources/eu_sifem_simulation/SimulationWSTest/output/output.dat";
	
	private static final String UNV_TTL = "src/test/resources/eu_sifem_simulation_configuration/PakSolverControlerServiceTest/ttl/unv.ttl";
	private static final String DAT_TTL = "src/test/resources/eu_sifem_simulation_configuration/PakSolverControlerServiceTest/ttl/dat.ttl";
	
	@Autowired
	private IPakSolverControlerService pakSolverControlerService;
	
	@Autowired
	private IPakRDFMapperService pakRDFMapperService;
	
	@Autowired
	private IPakRDFMapperService pakRDFMapper;
	
	private SimulationInstanceTO simulationInstanceTO = new SimulationInstanceTO();
	
	@Test(groups= { "default" },priority=1)
	public void testServiceIsNotNull() throws Exception{
		Assert.assertNotNull(pakSolverControlerService);
		simulationInstanceTO.setProjectName("projectOne");
		simulationInstanceTO.setSimulationName("simulation");
		simulationInstanceTO.setInstanceName("instance_0");
		simulationInstanceTO.setWorkspacebasePath("src/test/resources/eu_sifem_simulation_configuration/PakSolverControlerServiceTest/workspaceTest/");
		
	}

	@Test(groups= { "default" },priority=2,enabled=true)
	public void testConvertDatAndunvToTTL() throws Exception{

		File outputDat = new File(OUTPUT_DAT);
		File outputUnv = new File(OUTPUT_UNV);
		InputStream datFile = pakRDFMapper.datToRDFService(simulationInstanceTO,new FileInputStream(outputDat));
		Assert.assertNotNull(datFile);
		
		InputStream unvFile = pakRDFMapper.unvToRDFService(simulationInstanceTO,new FileInputStream(outputUnv));
		Assert.assertNotNull(unvFile);
		
	}
	
	@Test(groups= { "default" },priority=2,enabled=false)
	public void testRunPakSolver() throws Exception{
		//TODO Deprecating..
		//pakSolverControlerService.runPakcSolverService(simulationInstanceTO);
		File datFile = new File(DAT_FILE);
		File unvFile = new File(UNV_FILE);
		Assert.assertTrue(datFile.exists());
		Assert.assertTrue(unvFile.exists());
		
		BufferedReader br = new BufferedReader(new FileReader(DAT_FILE));     
		Assert.assertNotNull(br.readLine());
		
		br = new BufferedReader(new FileReader(UNV_FILE));     
		Assert.assertNotNull(br.readLine());

	}
	

	public IPakSolverControlerService getPakSolverControlerService() {
		return pakSolverControlerService;
	}

	public void setPakSolverControlerService(
			IPakSolverControlerService pakSolverControlerService) {
		this.pakSolverControlerService = pakSolverControlerService;
	}
	
	

}