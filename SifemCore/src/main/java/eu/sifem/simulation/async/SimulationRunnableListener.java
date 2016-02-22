package eu.sifem.simulation.async;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;

import eu.sifem.model.to.AsyncProcessRunMessageTO;
import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;
import eu.sifem.service.IProcessRunnableService;

public class SimulationRunnableListener implements MessageListener {


	@Autowired
	private IProcessRunnableService processRunnableService;

	@Override
	public void onMessage(Message message) {

		try {
			if (message instanceof ObjectMessage) {
				ObjectMessage objMessage = (ObjectMessage) message;
				if (objMessage instanceof ActiveMQObjectMessage) {
					Object realObjMessage = ((ObjectMessage) message)
							.getObject();
					if (realObjMessage instanceof AsyncProcessRunMessageTO) {
						AsyncProcessRunMessageTO asyncProcessRunMessageTO = (eu.sifem.model.to.AsyncProcessRunMessageTO) realObjMessage;
						processRunnableService.startAsync(asyncProcessRunMessageTO.getSimulationInstanceTO(), asyncProcessRunMessageTO.getSessionIndexTO());
					}
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}



}
