package eu.sifem.simulation.solver.pak;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import eu.sifem.model.to.SimulationProcessManagerTO;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.PakSparulRDFJMSService;
//import eu.sifem.test.integrated.PakFData;

public class PakSparulRDFJMS implements PakSparulRDFJMSService {

    private static String defaultBrokerName;
    private static String defaultPassword;
    private static String defaultUserName;
    private static String sifemSparulProcessQueueEnt;
    private ConnectionFactory factory = null;
    private Connection connection     = null;
    private Session sendSession       = null;
    private MessageProducer sender    = null;
	
	@Autowired
	protected IResourceInjectionService resourceInjectionService;


	@Override
	public void sendMessageService(SimulationProcessManagerTO simulationProcessManagerTO) throws Exception {
        
		for(String simulationInstanceProcessManager:simulationProcessManagerTO.getSimulationInstanceName()){
	        factory = new ActiveMQConnectionFactory(defaultUserName, defaultPassword, defaultBrokerName);
	        connection = factory.createConnection(defaultUserName, defaultPassword);
	        sendSession = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
	    	javax.jms.Queue queue = sendSession.createQueue(sifemSparulProcessQueueEnt);
	        sender = sendSession.createProducer(queue);
	        connection.start();
	        ObjectMessage objMsg = sendSession.createObjectMessage(simulationInstanceProcessManager);
	        sender.send(objMsg);
		}
    	

		
	}


	public static String getDefaultBrokerName() {
		return defaultBrokerName;
	}


	public static void setDefaultBrokerName(String defaultBrokerName) {
		PakSparulRDFJMS.defaultBrokerName = defaultBrokerName;
	}


	public static String getDefaultPassword() {
		return defaultPassword;
	}


	public static void setDefaultPassword(String defaultPassword) {
		PakSparulRDFJMS.defaultPassword = defaultPassword;
	}


	public static String getDefaultUserName() {
		return defaultUserName;
	}


	public static void setDefaultUserName(String defaultUserName) {
		PakSparulRDFJMS.defaultUserName = defaultUserName;
	}


	public static String getSifemSparulProcessQueueEnt() {
		return sifemSparulProcessQueueEnt;
	}


	public static void setSifemSparulProcessQueueEnt(
			String sifemSparulProcessQueueEnt) {
		PakSparulRDFJMS.sifemSparulProcessQueueEnt = sifemSparulProcessQueueEnt;
	}



}


