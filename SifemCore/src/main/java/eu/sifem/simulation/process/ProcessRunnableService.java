package eu.sifem.simulation.process;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.sifem.dao.mongo.SolverResultFilesDAO;
import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;
import eu.sifem.model.to.DatAndUnvSolverTO;
import eu.sifem.model.to.PAKCRestServiceTO;
import eu.sifem.model.to.PAKCRestServiceWrapperTO;
import eu.sifem.model.to.SessionIndexTO;
import eu.sifem.model.to.SimulationInstanceTO;
import eu.sifem.model.to.SolverResultFilesTO;
import eu.sifem.service.IPakSolverControlerService;
import eu.sifem.service.IProcessRunnableService;
import eu.sifem.service.dao.IConfigFileDAOService;
import eu.sifem.service.dao.IDatAndUnvSolverDAOService;
import eu.sifem.utils.BasicFileTools;
import eu.sifem.utils.Util;

@Configuration
@EnableScheduling
@Service("processRunnableService")
public class ProcessRunnableService implements IProcessRunnableService {

    static final String vmIp = "192.168.3.26";
    

	@Autowired
	private IPakSolverControlerService pakSolverControlerService;

	@Autowired
	private IConfigFileDAOService configFileDAO;

	@Autowired
	private IDatAndUnvSolverDAOService datAndUnvSolverDAO;
	
	@Autowired
	private SolverResultFilesDAO solverResultFilesDAO;

	@Override
	public void startAsync(SimulationInstanceTO simulationInstanceTO,
			SessionIndexTO sessionIndexTO) throws Exception {
                System.err.println(" --- startAsync1");
		List<DatAndUnvSolverTO> datAndUnvSolverTOList = callPakService(
				simulationInstanceTO, sessionIndexTO, null);
		System.out.println(simulationInstanceTO);
		System.out.println(sessionIndexTO);
		if (datAndUnvSolverTOList == null || datAndUnvSolverTOList.isEmpty()) {
			return;
		}
		List<AsyncTripleStoreInsertMessageTO> asyncTripleStoreInsertMessageTOList = pakSolverControlerService
				.semantifyOutputService(simulationInstanceTO,
						datAndUnvSolverTOList);
	}

	
	@Override
	public List<AsyncTripleStoreInsertMessageTO> startSync(SimulationInstanceTO simulationInstanceTO,
			SessionIndexTO sessionIndexTO, String commandLineArgument) throws Exception {
            
                System.err.println(" --- startAsync 2");

		List<DatAndUnvSolverTO> datAndUnvSolverTOList = callPakService(
				simulationInstanceTO, sessionIndexTO, commandLineArgument);
		System.out.println(simulationInstanceTO);
		System.out.println(sessionIndexTO);
		if (datAndUnvSolverTOList == null || datAndUnvSolverTOList.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		List<AsyncTripleStoreInsertMessageTO> asyncTripleStoreInsertMessageTOList = pakSolverControlerService
				.semantifyOutputService(simulationInstanceTO,
						datAndUnvSolverTOList);
		return asyncTripleStoreInsertMessageTOList;
	}
	
	public List<DatAndUnvSolverTO> callPakService(
			SimulationInstanceTO simulationInstanceTO,
			SessionIndexTO sessionIndexTO, String commandLineArgument) throws Exception {
		List<DatAndUnvSolverTO> datAndUnvSolverTOList = new ArrayList<DatAndUnvSolverTO>();
		//List<SolverResultFilesTO> solverResultFilesTOList = new ArrayList<SolverResultFilesTO>();
                
                int j=0;

		int instanceNumbers = new Util()
				.getInstacesNumberByConfigSessionID(sessionIndexTO
						.getCfgSessionIdList().get(0));
                System.err.println("instNumber=" + instanceNumbers);
		for (int i = 0; i < 1; i++) {
			String instanceName = "instance_" + i;
                        System.err.println("i = " + i);

			simulationInstanceTO.setInstanceName(instanceName);
			configFileDAO.updateInstanceName(
					simulationInstanceTO.getProjectName(),
					simulationInstanceTO.getSimulationName(), instanceName);
			List<InputStream> cfgIs = configFileDAO.findCFGFile(
					simulationInstanceTO.getProjectName(),
					simulationInstanceTO.getSimulationName(), instanceName);
			if (cfgIs == null) {
				cfgIs = configFileDAO.findCFGFile(
						simulationInstanceTO.getProjectName(),
						simulationInstanceTO.getSimulationName());
			}
			if (cfgIs == null) {
				return Collections.EMPTY_LIST;
			}
//			FileUtils.writeByteArrayToFile(new File("C:/SifemWindowsResourceFiles/input_panos_"+i+".cfg"),IOUtils.toByteArray(cfgIs.get(0)));
			
                        System.err.println(" ---- cfgIS.size" + cfgIs.size());
			//for (InputStream is : cfgIs) {
                                System.err.println(" j=" + j);
				PAKCRestServiceWrapperTO result = callService(
						simulationInstanceTO, sessionIndexTO, cfgIs.get(0), commandLineArgument);
				DatAndUnvSolverTO datAndUnvSolverTO = new DatAndUnvSolverTO();
				SolverResultFilesTO solverResultFilesTO = new SolverResultFilesTO();
				datAndUnvSolverTO.setProjectName(simulationInstanceTO
						.getProjectName());
				datAndUnvSolverTO.setSimulationName(simulationInstanceTO
						.getSimulationName());
				datAndUnvSolverTO.setInstanceName(instanceName);

				InputStream datIs = result.getDatFile();
				datAndUnvSolverTO.setDatFile(datIs);

				InputStream unvIs = result.getUnvFile();
				datAndUnvSolverTO.setUnvFile(unvIs);
				datAndUnvSolverTOList.add(datAndUnvSolverTO);
				datAndUnvSolverDAO.insert(datAndUnvSolverTO);
				
				solverResultFilesTO.setDcenterlineFile(result.getdCenterLineFile());
				solverResultFilesTO.setPimagFile(result.getpImagFile());
				solverResultFilesTO.setPrealFile(result.getpRealFile());
				solverResultFilesTO.setVmagnFile(result.getvMagnFile());
				solverResultFilesTO.setVphaseFile(result.getvPhaseFile());
				solverResultFilesDAO.insert(solverResultFilesTO);
				
                                j++;
			//}
		}
                
                System.err.println(" j = " + j);
		return datAndUnvSolverTOList;
	}

	
	

        private Map<List<String>, List<String>> parseAndSaveStreamAsXandYList(SolverResultFilesTO solverResultFilesTO) {
       
        	
		return null;
       }




		private static String topicName = "Job.queue";
        private static String initialContextFactory = "org.apache.activemq.jndi.ActiveMQInitialContextFactory";
        private static String connectionString = "tcp://"+vmIp+":61616";
        private static boolean messageReceived = false;    
        
	private PAKCRestServiceWrapperTO callService(
			SimulationInstanceTO simulationInstanceTO,
			SessionIndexTO sessionIndexTO, InputStream is, String commandLineArgument) throws Exception {

            String session = null;
            PAKCRestServiceWrapperTO result = new PAKCRestServiceWrapperTO();
            PAKCRestServiceTO simulationInstance = new PAKCRestServiceTO();
            
            //String StringFromInputStream = IOUtils.toString(is, "UTF-8");
            //System.out.println(StringFromInputStream);            

            byte[] cfgFileByteArr = IOUtils.toByteArray(is);
            simulationInstance.setCfgFile(cfgFileByteArr);
            String parameter = Util.getJsonStrFromObject(simulationInstance);

            if(commandLineArgument.equals("HeadModel") || commandLineArgument.equals("MiddleEar"))
            {
                System.out.println("http://"+vmIp+":8080/SolverInterface/webresources/solver/authenticate" + "/testusername/password");
                session = Request.Get("http://"+vmIp+":8080/SolverInterface/webresources/solver/authenticate" + "/testusername/password").execute().returnContent().asString();
                System.out.println(session);

                Request.Post("http://"+vmIp+":8080/SolverInterface/webresources/ConfigurationFile/upload/head/" + session).bodyForm(Form.form().add("simulationInstance",parameter).build()).execute();

                System.out.println("http://"+vmIp+":8080/SolverInterface/webresources/solver/cad/init" + "/" + commandLineArgument +"/"+ session );
                Request.Get("http://"+vmIp+":8080/SolverInterface/webresources/solver/cad/init" + "/" + commandLineArgument +"/"+ session ).execute();

                subscribeWithTopicLookup(session);
                this.messageReceived = false;
                
                System.out.println(" -------------- Done  " + "http://"+vmIp+":8080/SolverInterface/webresources/accessResults/simulation/headmodel/" + session);
                String responseContentStr = Request.Get("http://"+vmIp+":8080/SolverInterface/webresources/accessResults/simulation/headmodel/" + session)
                    .execute().returnContent().asString();            
                System.out.println(" -------------- Done");
                Gson gson = new GsonBuilder().create();
                simulationInstance  = gson.fromJson(responseContentStr, PAKCRestServiceTO.class);                
            }
            else
            {
                System.out.println("http://"+vmIp+":8080/SolverInterface/webresources/solver/authenticate/testusername/password");
                session = Request.Get("http://"+vmIp+":8080/SolverInterface/webresources/solver/authenticate/testusername/password").execute().returnContent().asString();
                System.out.println(session);

                Request.Post("http://"+vmIp+":8080/SolverInterface/webresources/ConfigurationFile/upload/"+ session).bodyForm(
                    Form.form().add("simulationInstance", parameter).build()).execute();

    //            Request.Get("http://"+vmIp+":8080/SolverInterface/webresources/solver/cad/init/" + "CochleaCoiledModel-WithLongitudinalCoupling" + "/" + session).execute();
                Request.Get("http://"+vmIp+":8080/SolverInterface/webresources/solver/cad/init/" + commandLineArgument + "/" + session).execute();

                subscribeWithTopicLookup(session);  
                this.messageReceived = false;

                System.out.println(" -------------- Done");
                String responseContentStr = Request.Get("http://"+vmIp+":8080/SolverInterface/webresources/accessResults/simulation/" + session)
                    .execute().returnContent().asString();            
                System.out.println(" -------------- Done");
                Gson gson = new GsonBuilder().create();
                simulationInstance  = gson.fromJson(responseContentStr, PAKCRestServiceTO.class);

                System.out.println(" -------------- Done");

            }
            
            InputStream datIS = new ByteArrayInputStream(simulationInstance.getDatFile());
            InputStream unvIS = new ByteArrayInputStream(simulationInstance.getUnvFile());
            

            InputStream pImagFile = pImagFile(simulationInstance);
            InputStream dCenterLineFile  = dCenterLineFile(simulationInstance);
            InputStream pRealFile  = pRealFile(simulationInstance);
            InputStream vMagnFile  = vMagnFile(simulationInstance);
            InputStream vPhaseFile  = vPhaseFile(simulationInstance);
            

            System.out.println(" -------------- Done");
            result.setSessionID(session);
            result.setDatFile(datIS);
            result.setUnvFile(unvIS);
            result.setpImagFile(pImagFile);
            result.setdCenterLineFile(dCenterLineFile);
            result.setpRealFile(pRealFile);
            result.setvMagnFile(vMagnFile);
            result.setvPhaseFile(vPhaseFile);
        	//System.out.println(IOUtils.toString(in, "UTF-8"));
            System.out.println(" -------------- Done Result");
            return result;
	}
	
	
	

	//TODO could be removed or refactored
	public InputStream pImagFile(PAKCRestServiceTO simulationInstance) throws Exception{
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(PIMAG_LOCAL_FILE);
		return BasicFileTools.getFileAsMock(simulationInstance.getpImagFile(),in);
	}
	public InputStream dCenterLineFile(PAKCRestServiceTO simulationInstance) throws Exception{
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(DCENTERLINE_LOCAL_FILE);
		return BasicFileTools.getFileAsMock(simulationInstance.getdCenterLineFile(),in);
	}
	public InputStream pRealFile(PAKCRestServiceTO simulationInstance) throws Exception{
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(PREAL_LOCAL_FILE);
		return BasicFileTools.getFileAsMock(simulationInstance.getpRealFile(),in);
	}
	public InputStream vMagnFile(PAKCRestServiceTO simulationInstance) throws Exception{
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(VMAGN_LOCAL_FILE);
		return BasicFileTools.getFileAsMock(simulationInstance.getvMagnFile(),in);
	}
	public InputStream vPhaseFile(PAKCRestServiceTO simulationInstance) throws Exception{
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(VPHASE_LOCAL_FILE);
		return BasicFileTools.getFileAsMock(simulationInstance.getvPhaseFile(),in);
	}

 public static void subscribeWithTopicLookup(String session)
 {
 
        Properties properties = new Properties();
        TopicConnection topicConnection = null;
        properties.put("java.naming.factory.initial", initialContextFactory);
        properties.put("connectionfactory.QueueConnectionFactory",
          connectionString);
        properties.setProperty(Context.PROVIDER_URL, connectionString);
        properties.put("topic." + topicName + "." + session, topicName + "." + session);
        try {
         InitialContext ctx = new InitialContext(properties);
         TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) ctx
           .lookup("QueueConnectionFactory");
         topicConnection = topicConnectionFactory.createTopicConnection();
         System.out
           .println("Create Topic Connection for Topic " + topicName + "." + session);

         while (!messageReceived) {
          try {
           TopicSession topicSession = topicConnection
             .createTopicSession(false, Session.AUTO_ACKNOWLEDGE);    

           Topic topic = (Topic) ctx.lookup(topicName + "." + session);
           // start the connection
           topicConnection.start();

           // create a topic subscriber
           javax.jms.TopicSubscriber topicSubscriber = topicSession
             .createSubscriber(topic);

           TestMessageListener messageListener = new TestMessageListener();
           topicSubscriber.setMessageListener(messageListener);

           Thread.sleep(5000);
           topicSubscriber.close();
           topicSession.close();
          } catch (JMSException e) {
           e.printStackTrace();
          } catch (InterruptedException e) {
           e.printStackTrace();
          }
         }

        } catch (NamingException e) {
         throw new RuntimeException("Error in initial context lookup", e);
        } catch (JMSException e) {
         throw new RuntimeException("Error in JMS operations", e);
        } finally {
         if (topicConnection != null) {
          try {
           topicConnection.close();
          } catch (JMSException e) {
           throw new RuntimeException(
             "Error in closing topic connection", e);
          }
         }
        }
 }
 
  public static class TestMessageListener implements MessageListener {
        public void onMessage(Message message) {
         try {
          System.out.println("Got the Message : "
            + ((TextMessage) message).getText());
          messageReceived = true;
         } catch (JMSException e) {
          e.printStackTrace();
         }
        }
 }
 
        
	private int getStatus(String session) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		int result = 0;
		try{
			HttpGet getRequest = new HttpGet(LOCAL_HOST+"/SolverInterface/webresources/accessResults/simulation"+ "/" + session);
			getRequest.addHeader("accept", "application/json");
			
			HttpResponse response = httpclient.execute(getRequest);
			result = response.getStatusLine().getStatusCode();
		}finally{
			httpclient.close();
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

//	public void subscribeWithTopicLookup(String session) throws InterruptedException {
//
//		Properties properties = new Properties();
//		TopicConnection topicConnection = null;
//		properties.put("java.naming.factory.initial", initialContextFactory);
//		properties.put("connectionfactory.QueueConnectionFactory",
//				connectionString);
//		properties.put("topic." + topicName + "." + session, topicName + "."
//				+ session);
//		try {
//			InitialContext ctx = new InitialContext(properties);
//			TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) ctx
//					.lookup("QueueConnectionFactory");
//			topicConnection = topicConnectionFactory.createTopicConnection();
//			System.out.println("Create Topic Connection for Topic " + topicName
//					+ "." + session);
//
//			while (!messageReceived) {
//					TopicSession topicSession = topicConnection
//							.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
//
//					Topic topic = (Topic) ctx.lookup(topicName + "." + session);
//					// start the connection
//					topicConnection.start();
//
//					// create a topic subscriber
//					javax.jms.TopicSubscriber topicSubscriber = topicSession
//							.createSubscriber(topic);
//
//					TestMessageListener messageListener = new TestMessageListener();
//					topicSubscriber.setMessageListener(messageListener);
//
//					Thread.sleep(5000);
//					topicSubscriber.close();
//					topicSession.close();
//			}
//
//		} catch (NamingException e) {
//			throw new RuntimeException("Error in initial context lookup", e);
//		} catch (JMSException e) {
//			throw new RuntimeException("Error in JMS operations", e);
//		} finally {
//			if (topicConnection != null) {
//				try {
//					topicConnection.close();
//				} catch (JMSException e) {
//					throw new RuntimeException(
//							"Error in closing topic connection", e);
//				}
//			}
//		}
//	}
//
//	public class TestMessageListener implements MessageListener {
//		public void onMessage(Message message) {
//			try {
//				System.out.println("Got the Message : "
//						+ ((TextMessage) message).getText());
//				messageReceived = true;
//			} catch (JMSException e) {
//				throw new RuntimeException(e.getMessage(),e);
//			}
//		}
//	}
}
