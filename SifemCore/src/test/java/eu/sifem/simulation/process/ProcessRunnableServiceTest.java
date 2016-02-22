package eu.sifem.simulation.process;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import eu.sifem.utils.BasicFileTools;


//@ContextConfiguration(locations = { "classpath:SifemCore-applicationContext.xml" })
public class ProcessRunnableServiceTest //extends AbstractTestNGSpringContextTests{
{
	//FIXME CANT BE HARDCODE
	String LOCAL_HOST = "http://localhost:8088";
	String REMOTE_HOST = "http://213.249.38.66:7072";
	String DCENTER_LINE_LOCAL_FILE = "d_centerline_.csv";
	String PIMAG_LOCAL_FILE = "p_imag_.csv";
	String PREAL_LOCAL_FILE = "p_real_.csv";
	String VMAGN_LOCAL_FILE = "v_magn_.csv";
	String VPHASE_LOCAL_FILE = "v_phase_.csv";
	

	@Test
	public void pImagFile() throws Exception{
		String tempoeraryFileSystemLocation = PIMAG_LOCAL_FILE;
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(tempoeraryFileSystemLocation);
		System.out.println(IOUtils.toString(in, "UTF-8"));
	}
	
	@Test
	public void dCenterLineFile() throws Exception{
			String tempoeraryFileSystemLocation = DCENTER_LINE_LOCAL_FILE;
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(tempoeraryFileSystemLocation);
			System.out.println(IOUtils.toString(in, "UTF-8"));
	}
	
	@Test
	public void pRealFile() throws Exception{
			String tempoeraryFileSystemLocation = PREAL_LOCAL_FILE;
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(tempoeraryFileSystemLocation);
			System.out.println(IOUtils.toString(in, "UTF-8"));
	}
	
	@Test
	public void vMagnFile() throws Exception{
			String tempoeraryFileSystemLocation = VMAGN_LOCAL_FILE;
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(tempoeraryFileSystemLocation);
			System.out.println(IOUtils.toString(in, "UTF-8"));
	}
	
	@Test
	public void vPhaseFile() throws Exception{
			String tempoeraryFileSystemLocation = VPHASE_LOCAL_FILE;
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(tempoeraryFileSystemLocation);
			System.out.println(IOUtils.toString(in, "UTF-8"));
	}

		
		
		
//		@Test(groups= { "default" },priority=2)
//		public void testSetJsonParam(){
//			MessengerCollectorHelper garbageMessengerHelper = new MessengerCollectorHelper();
//			garbageMessengerHelper.setMessage("Simulation under processing.");
//			garbageMessengerHelper.setName("MessageOne");
//			garbageMessengerHelper.setType("Info.");
//			garbageMessengerHelper.setSimulationName("simulationOne");
//			InternalUserHelper internalUserHelper = new InternalUserHelper();
//			internalUserHelper.setUser(Constants.Global.INTERNAL_SERVLET_ACCESS_USER);
//			internalUserHelper.setPass(Constants.Global.INTERNAL_SERVLET_ACCESS_PASS);
//			internalUserHelper.setCmdHelper("0");
//			garbageMessengerHelper.setInternalUserHelper(internalUserHelper);
//			String parameter = Util.getJsonStrFromObject(garbageMessengerHelper);
//			Assert.assertNotNull(parameter);
//			setJsonParam(parameter);
//		}
//		
//		@Test(groups= { "default" },priority=3)
//		public void testFillSessionWithSimulationName() throws Exception{
//			//FIXME hard coded host
//			//add simulation name to web session.
//			Request.Post("http://localhost:8080/Sifem/SessionManagerTestPurposeServlet")
//			.bodyForm(Form.form().add(Constants.Global.MSG,this.jsonParam).build()).execute().returnContent();
//		}
		

}
