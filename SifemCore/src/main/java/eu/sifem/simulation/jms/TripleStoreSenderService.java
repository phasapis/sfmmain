package eu.sifem.simulation.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.ITripleStoreSenderService;


public class TripleStoreSenderService implements ITripleStoreSenderService{

	
    private static String defaultBrokerName;
    private static String defaultPassword;
    private static String defaultUserName;
    private static String tripleStoreQueueEnt;
    private ConnectionFactory factory = null;
    private Connection connection     = null;
    private Session sendSession       = null;
    private MessageProducer sender    = null;
	
	@Autowired
	protected IResourceInjectionService resourceInjectionService;


	@Override
	public void sendMessageService(AsyncTripleStoreInsertMessageTO asyncTripleStoreInsertMessageTO) throws Exception {
	        factory = new ActiveMQConnectionFactory(defaultUserName, defaultPassword, defaultBrokerName);
	        connection = factory.createConnection(defaultUserName, defaultPassword);
	        sendSession = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
	    	javax.jms.Queue queue = sendSession.createQueue(tripleStoreQueueEnt);
	        sender = sendSession.createProducer(queue);
	        connection.start();

	       ObjectMessage objMsg = sendSession.createObjectMessage(asyncTripleStoreInsertMessageTO);
	       sender.send(objMsg);

		
	}


	public static String getDefaultBrokerName() {
		return defaultBrokerName;
	}


	public static void setDefaultBrokerName(String defaultBrokerName) {
		TripleStoreSenderService.defaultBrokerName = defaultBrokerName;
	}


	public static String getDefaultPassword() {
		return defaultPassword;
	}


	public static void setDefaultPassword(String defaultPassword) {
		TripleStoreSenderService.defaultPassword = defaultPassword;
	}


	public static String getDefaultUserName() {
		return defaultUserName;
	}


	public static void setDefaultUserName(String defaultUserName) {
		TripleStoreSenderService.defaultUserName = defaultUserName;
	}


	public static String getTripleStoreQueueEnt() {
		return tripleStoreQueueEnt;
	}


	public static void setTripleStoreQueueEnt(String tripleStoreQueueEnt) {
		TripleStoreSenderService.tripleStoreQueueEnt = tripleStoreQueueEnt;
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
