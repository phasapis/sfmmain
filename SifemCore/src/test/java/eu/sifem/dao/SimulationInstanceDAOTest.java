package eu.sifem.dao;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;


@ContextConfiguration(locations={"classpath:SifemCore-applicationContext.xml"})
public class SimulationInstanceDAOTest extends AbstractTestNGSpringContextTests{


	
//	@Test(groups= { "default" },enabled=true, priority=0)
//	public void insertTest() throws Exception{
//      ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");
//      ISimulationInstanceDAOService simulationInstanceDAO = (ISimulationInstanceDAOService) ctx.getBean("simulationInstanceDAO");
//      List<SimulationProcessSPOGTO> result = simulationInstanceDAO.findAll();
//      int i =0;
//      for(SimulationProcessSPOGTO spog: result){
//    	  System.out.println(spog.getSubject()+" "+spog.getPredicate()+" "+spog.getObject()+" "+spog.getGraph());
//    	  i++;
//      }
//      Assert.assertTrue(i==1507274);
//	}
//	
}
