package eu.sifem.simulation.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import eu.sifem.model.to.AsyncProcessRunMessageTO;
import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.ISimulationSenderService;

public class SimulationSenderService implements ISimulationSenderService{

	
    private static String defaultBrokerName;
    private static String defaultPassword;
    private static String defaultUserName;
    private static String runProcessQueueEnt;
    private ConnectionFactory factory = null;
    private Connection connection     = null;
    private Session sendSession       = null;
    private MessageProducer sender    = null;
	
	@Autowired
	protected IResourceInjectionService resourceInjectionService;

	@Override
	public void sendMessageService(AsyncProcessRunMessageTO asyncProcessRunMessageTO) throws Exception {
	        factory = new ActiveMQConnectionFactory(defaultUserName, defaultPassword, defaultBrokerName);
	        connection = factory.createConnection(defaultUserName, defaultPassword);
	        sendSession = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
	    	javax.jms.Queue queue = sendSession.createQueue(runProcessQueueEnt);
	        sender = sendSession.createProducer(queue);
	        connection.start();

	       ObjectMessage objMsg = sendSession.createObjectMessage(asyncProcessRunMessageTO);
	       sender.send(objMsg);

	}


	public static String getDefaultBrokerName() {
		return defaultBrokerName;
	}




	public static void setDefaultBrokerName(String defaultBrokerName) {
		SimulationSenderService.defaultBrokerName = defaultBrokerName;
	}




	public static String getDefaultPassword() {
		return defaultPassword;
	}




	public static void setDefaultPassword(String defaultPassword) {
		SimulationSenderService.defaultPassword = defaultPassword;
	}




	public static String getDefaultUserName() {
		return defaultUserName;
	}




	public static void setDefaultUserName(String defaultUserName) {
		SimulationSenderService.defaultUserName = defaultUserName;
	}

	public static String getRunProcessQueueEnt() {
		return runProcessQueueEnt;
	}


	public static void setRunProcessQueueEnt(String runProcessQueueEnt) {
		SimulationSenderService.runProcessQueueEnt = runProcessQueueEnt;
	}


	public ConnectionFactory getFactory() {
		return factory;
	}


	public void setFactory(ConnectionFactory factory) {
		this.factory = factory;
	}


	public Connection getConnection() {
		return connection;
	}


	public void setConnection(Connection connection) {
		this.connection = connection;
	}


	public Session getSendSession() {
		return sendSession;
	}


	public void setSendSession(Session sendSession) {
		this.sendSession = sendSession;
	}


	public MessageProducer getSender() {
		return sender;
	}


	public void setSender(MessageProducer sender) {
		this.sender = sender;
	}


	public IResourceInjectionService getResourceInjectionService() {
		return resourceInjectionService;
	}


	public void setResourceInjectionService(
			IResourceInjectionService resourceInjectionService) {
		this.resourceInjectionService = resourceInjectionService;
	}



}
