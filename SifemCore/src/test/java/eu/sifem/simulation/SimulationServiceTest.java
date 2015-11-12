package eu.sifem.simulation;

import java.util.Arrays;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.testng.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import eu.sifem.model.helper.Constants;
import eu.sifem.model.helper.InternalUserHelper;
import eu.sifem.model.helper.MessengerCollectorHelper;
import eu.sifem.model.to.ProjectSimulationTO;
import eu.sifem.model.to.TransformationTO;
import eu.sifem.service.ISimulationService;
import eu.sifem.utils.Util;

@ContextConfiguration(locations = { "classpath:SifemCore-applicationContext.xml" })
public class SimulationServiceTest  extends AbstractTestNGSpringContextTests{
  
	@Autowired
	private ISimulationService simulationService;
	
	private String jsonParam;
	
	@Test(groups= { "default" },priority=1)
	public void testSimulationServicenotNull(){
		Assert.assertNotNull(simulationService);
	}
	
	@Test(groups= { "default" },priority=2)
	public void testSetJsonParam(){
		MessengerCollectorHelper garbageMessengerHelper = new MessengerCollectorHelper();
		garbageMessengerHelper.setMessage("Simulation under processing.");
		garbageMessengerHelper.setName("MessageOne");
		garbageMessengerHelper.setType("Info.");
		garbageMessengerHelper.setSimulationName("simulationOne");
		InternalUserHelper internalUserHelper = new InternalUserHelper();
		internalUserHelper.setUser(Constants.Global.INTERNAL_SERVLET_ACCESS_USER);
		internalUserHelper.setPass(Constants.Global.INTERNAL_SERVLET_ACCESS_PASS);
		internalUserHelper.setCmdHelper("0");
		garbageMessengerHelper.setInternalUserHelper(internalUserHelper);
		String parameter = Util.getJsonStrFromObject(garbageMessengerHelper);
		Assert.assertNotNull(parameter);
		setJsonParam(parameter);
	}
	
	@Test(groups= { "default" },priority=3)
	public void testFillSessionWithSimulationName() throws Exception{
		//FIXME hard coded host
		//add simulation name to web session.
		Request.Post("http://localhost:8080/Sifem/SessionManagerTestPurposeServlet")
		.bodyForm(Form.form().add(Constants.Global.MSG,this.jsonParam).build()).execute().returnContent();
	}
	
	@Test(groups= { "default" },priority=4,enabled=true)
	public void testShowViewMessage() throws Exception {
		
		//FIXME hard coded host
		//call message servlet.
		Request.Post("http://localhost:8080/Sifem/ExternalMessageServlet")
	    .bodyForm(Form.form().add(Constants.Global.MSG,this.jsonParam).build()).execute().returnContent();


	}
	

	@Test(groups= { "default" },priority=3,enabled=false)
	public void testInsertSimulation() throws Exception{
		ProjectSimulationTO projectSimulationTO = new ProjectSimulationTO();
		projectSimulationTO.setProjectName("simulationNameJUnitTest");
		projectSimulationTO.setTransformations(Arrays.asList(new TransformationTO[]{new TransformationTO("projectNameTest","simulationNameTest")}));
		
		//TODO Deprecating..
		//projectSimulationTO.setWorkspace("C:/SifemWindowsResourceFiles/workspace/simulationNameJUnitTest/");
		simulationService.insertService(projectSimulationTO);
	}

	public String getJsonParam() {
		return jsonParam;
	}

	public void setJsonParam(String jsonParam) {
		this.jsonParam = jsonParam;
	}
	
	
	
	
}
