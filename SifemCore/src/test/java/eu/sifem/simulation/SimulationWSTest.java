package eu.sifem.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.sifem.model.to.PAKCRestServiceTO;
import eu.sifem.model.to.PAKCRestServiceWrapperTO;
import eu.sifem.utils.Util;

@ContextConfiguration(locations = { "classpath:SifemCore-applicationContext.xml" })
public class SimulationWSTest  extends AbstractTestNGSpringContextTests{
  
	
	private static final String OUTPUT_UNV = "src/test/resources/eu_sifem_simulation/SimulationWSTest/output/output.unv";
	private static final String OUTPUT_DAT = "src/test/resources/eu_sifem_simulation/SimulationWSTest/output/output.dat";
	private static final String C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE_PROJECT_ONE_SIMULATION_INSTANCE_0_INPUT_CFG = "src/test/resources/eu_sifem_simulation/SimulationWSTest/input/input.cfg";
	private static final String HTTP_LOCALHOST_8080_SOLVER_WS_REST_SOLVER_REST_EXECUTE_SOLVER = "http://localhost:8080/SolverWS/rest/solverRest/executeSolver";
	private static final String SIMULATION_INSTANCE = "simulationInstance";

	@Test
	public void testHttpPostToRestService() throws Exception {
		PAKCRestServiceWrapperTO simulationInstance = new PAKCRestServiceWrapperTO();
		byte[] cfgFileByteArr = org.apache.commons.io.FileUtils.readFileToByteArray(new File(C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE_PROJECT_ONE_SIMULATION_INSTANCE_0_INPUT_CFG));
		simulationInstance.setCfgFile(new FileInputStream(new File(C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE_PROJECT_ONE_SIMULATION_INSTANCE_0_INPUT_CFG)));
		String parameter = Util.getJsonStrFromObject(simulationInstance);
		String responseContentStr = Request.Post(HTTP_LOCALHOST_8080_SOLVER_WS_REST_SOLVER_REST_EXECUTE_SOLVER)
		.bodyForm(Form.form().add(SIMULATION_INSTANCE,parameter).build()).execute().returnContent().asString();
		//System.out.println(responseContentStr);
		
		Gson gson = new GsonBuilder().create();
		simulationInstance  = gson.fromJson(responseContentStr, PAKCRestServiceWrapperTO.class);
		InputStream datByteArr = simulationInstance.getDatFile();
		InputStream unvByteArr = simulationInstance.getUnvFile();
		Assert.assertNotNull(datByteArr);
		Assert.assertNotNull(unvByteArr);
		
		File outputDat = new File(OUTPUT_DAT);
		File outputUnv = new File(OUTPUT_UNV);
		
		FileUtils.copyInputStreamToFile(datByteArr, outputDat);
		FileUtils.copyInputStreamToFile(unvByteArr, outputUnv);
		
		Assert.assertTrue(outputDat.exists());
		Assert.assertTrue(outputUnv.exists());
	}
	
}
