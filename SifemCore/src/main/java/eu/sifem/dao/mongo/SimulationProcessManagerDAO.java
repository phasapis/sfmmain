package eu.sifem.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import eu.sifem.model.to.SimulationProcessManagerTO;
import eu.sifem.service.dao.ISimulationProcessManagerDAO;


/**
 * 
 * @author jbjares
 * 
 */
@Repository
public class SimulationProcessManagerDAO implements ISimulationProcessManagerDAO{

	@Autowired
	private MongoOperations mongoOperations;
	

	@Override
	public void insert(SimulationProcessManagerTO simulationProcessManagerTO) throws Exception {
		mongoOperations.insert(simulationProcessManagerTO,SimulationProcessManagerTO.class.getSimpleName());
	}


	@Override
	public SimulationProcessManagerTO findBySimulationName(SimulationProcessManagerTO simulationProcessManagerTO) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("simulationName").is(simulationProcessManagerTO.getSimulationName()));
		return mongoOperations.findOne(query, SimulationProcessManagerTO.class,SimulationProcessManagerTO.class.getSimpleName());
	}


	@Override
	public List<SimulationProcessManagerTO> findAll() throws Exception {
		return mongoOperations.findAll(SimulationProcessManagerTO.class,SimulationProcessManagerTO.class.getSimpleName());
	}


	@Override
	public void update(SimulationProcessManagerTO simulationProcessManagerTO) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("simulationName").is(simulationProcessManagerTO.getSimulationName()));
		mongoOperations.remove(query,SimulationProcessManagerTO.class.getSimpleName());
		insert(simulationProcessManagerTO);
		
	}


	@Override
	public void delete(SimulationProcessManagerTO simulationProcessManagerTO) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("simulationName").is(simulationProcessManagerTO.getSimulationName()));
		mongoOperations.remove(query,SimulationProcessManagerTO.class.getSimpleName());
	}
	
	public static void main(String[] args) throws Exception {
      ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");
      ISimulationProcessManagerDAO simulationProcessManagerDAO = (ISimulationProcessManagerDAO) ctx.getBean("simulationProcessManagerDAO");
      
      List<SimulationProcessManagerTO> simulationProcessManagerTO = new ArrayList<SimulationProcessManagerTO>();
      simulationProcessManagerTO = simulationProcessManagerDAO.findAll();

      for(SimulationProcessManagerTO to: simulationProcessManagerTO){
    	  System.out.println(to.getSimulationInstanceName());
      }

      System.out.println("DONE");
	}
	
	//TODO remove (committed just to history purpose) //maybe a good idea make a test case with this scenario. 
//	public static void main(String[] args) throws Exception {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");
//        ISimulationInstanceDAOService simulationInstanceDAO = (ISimulationInstanceDAOService) ctx.getBean("simulationInstanceDAO");
//        ISimulationProcessManagerDAO simulationProcessManagerDAO = (ISimulationProcessManagerDAO) ctx.getBean("simulationProcessManagerDAO");
//
//        
//		String baseFolder = "C:/Users/JoaoBoscoJares/Desktop/tmp/sifem2bkp/SifemCore/src/test/resources/outputRDFDir/";
//		String[] datFiles = "datInput_100.ttl;datInput_1000.ttl;datInput_10000.ttl;datInput_200.ttl;datInput_2000.ttl;datInput_20000.ttl;datInput_300.ttl;datInput_3000.ttl;datInput_50.ttl;datInput_500.ttl;datInput_5000.ttl".split(";");
//		String[] unvFiles = "unvOutput_100.ttl;unvOutput_1000.ttl;unvOutput_10000.ttl;unvOutput_200.ttl;unvOutput_2000.ttl;unvOutput_20000.ttl;unvOutput_300.ttl;unvOutput_3000.ttl;unvOutput_50.ttl;unvOutput_500.ttl;unvOutput_5000.ttl".split(";");
//       
//		
//        SimulationProcessManagerTO simulationProcessManagerTO = new SimulationProcessManagerTO();
//        simulationProcessManagerTO.setSimulationInstanceProcessManagerTO(new ArrayList<SimulationInstanceProcessManagerTO>());
//
//
//        ExecutorService executor = Executors.newCachedThreadPool();
//        Thread execution = null;
//        SimulationInstanceProcessManagerTO simulationInstanceProcessManagerTO = null;
//        
//		for(int i = 0;i<datFiles.length;i++){
//			simulationInstanceProcessManagerTO = new SimulationInstanceProcessManagerTO();
//
//	        simulationProcessManagerTO.setStartTime(System.currentTimeMillis());
//			simulationInstanceProcessManagerTO.setNumber(String.valueOf(i));
//			simulationInstanceProcessManagerTO.setTripleList(new ArrayList<Triple>());
//			simulationProcessManagerTO.setSimulationName("sim");
//			
//			DatasetGraph dataset = RDFDataMgr.loadDataset(baseFolder+datFiles[i], Lang.TTL).asDatasetGraph();
//			//dataset = RDFDataMgr.loadDataset(baseFolder+unvFiles[i], Lang.TTL).asDatasetGraph();
//			Iterator<Quad> quads = dataset.find();
//			while ( quads.hasNext() ) {
//				Quad quad = quads.next();
//				Triple triple = quad.asTriple();
//				simulationInstanceProcessManagerTO.getTripleList().add(triple);
//			}	
//			
//			simulationInstanceProcessManagerTO.setNamedGraph(simulationProcessManagerTO.getSimulationName()+"_"+i);
//			simulationProcessManagerTO.getSimulationInstanceProcessManagerTO().add(simulationInstanceProcessManagerTO);
//
//		}
//		int count = 0;
//		for(SimulationInstanceProcessManagerTO simulationInstanceProcessManager:simulationProcessManagerTO.getSimulationInstanceProcessManagerTO()){
//			execution = new Thread(new ExecuteTmp(simulationProcessManagerDAO,simulationInstanceDAO,simulationProcessManagerTO,simulationInstanceProcessManager,count));
//			execution.start();
//			count++;
//		}
//	
//	}



	
}


