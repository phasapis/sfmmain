package eu.sifem.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.resultset.ResultSetMem;

import eu.sifem.dao.jena.JenaModel;
import eu.sifem.service.dao.IDatAndUnvTTLDAOService;


@ContextConfiguration(locations={"classpath:SifemCore-applicationContext_desenv.xml"})
public class MultiScratchTest extends AbstractTestNGSpringContextTests{
	
    private static final String projectName = "TheOne";
    private static final String simulationName = "sim1915_1037";
    private static final String instanceName = "instance_0";
	
	@Autowired
	private IDatAndUnvTTLDAOService datAndUnvTTLDAO;
	
	@Test
	public void testJenaModelCreationBasedOnDatAndUnvInputStream() throws Exception{
		Assert.assertNotNull(datAndUnvTTLDAO);
		
		InputStream datIs = datAndUnvTTLDAO.findDatFileByProjectName(projectName,simulationName,instanceName);
		InputStream unvIs = datAndUnvTTLDAO.findUnvFileByProjectName(projectName,simulationName,instanceName);
		
		Assert.assertNotNull(datIs);
		Assert.assertNotNull(unvIs);
		
//		FileUtils.writeByteArrayToFile(new File("C:/SifemWindowsResourceFiles/tmp/dat.txt"),IOUtils.toByteArray(datIs));
//		FileUtils.writeByteArrayToFile(new File("C:/SifemWindowsResourceFiles/tmp/unv.txt"),IOUtils.toByteArray(unvIs));
		
		JenaModel model = new JenaModel();
		model.importDataService(datIs);
		model.importDataService(unvIs);
//		model.getOntModel().write(System.out);
		testQueryIntoTtlFiles(model.getOntModel());
		
	}
	
	public void testQueryIntoTtlFiles(Model model) throws Exception{

		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
				"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"+
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"+
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
				"PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#>\n"+
				"PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#>\n"+
				"PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#>\n"+
				"SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id WHERE { ?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .  " +
				"?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  } " +
				"";
		
		List<String> queryVariables = new ArrayList<String>();
		queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
		queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
		queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
		queryVariables.add("subDomain");queryVariables.add("id");

//		Map<String, List<RDFNode>> queryModel =  model.queryModelService(query, queryVariables);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);	
		ResultSet results = qexec.execSelect();
		ResultSetMem inMem = new ResultSetMem(results);
		Assert.assertNotNull(model);
//		while (results.hasNext()){
//			QuerySolution qs = results.next();
//			RDFNode node = qs.get("s");
//			System.out.println(node.toString());
//		}

		//List<RDFNode> materials = queryModel.get(queryVariables.get(0));
		//List<RDFNode> materialTypes = queryModel.get(queryVariables.get(1));		
//		List<RDFNode> nodes = queryModel.get(queryVariables.get(2));
//		List<RDFNode> xCoords = queryModel.get(queryVariables.get(3));
		//List<RDFNode> yCoords = queryModel.get(queryVariables.get(4));
		//List<RDFNode> zCoords = queryModel.get(queryVariables.get(5));
//		List<RDFNode> translationXs = queryModel.get(queryVariables.get(6));
		//List<RDFNode> translationYs = queryModel.get(queryVariables.get(7));
		//List<RDFNode> translationZs = queryModel.get(queryVariables.get(8));
//		List<RDFNode> subDomains = queryModel.get(queryVariables.get(9));
//		List<RDFNode> ids = queryModel.get(queryVariables.get(10));

//		System.out.println("Nodes number: " + nodes.size());

//		Assert.assertEquals(""+nodes.size()+"".trim(),"9600".trim());
		
		
		//Set<RDFNode> nodesCovered = new HashSet<RDFNode>();
//		Map<RDFNode, Float> nodeXDisplacement = new HashMap<RDFNode, Float>();
//		Map<Integer, RDFNode> idNodeMap = new HashMap<Integer, RDFNode>();
//		Map<RDFNode, Float> nodeXCoord = new HashMap<RDFNode, Float>();
//
////		int size = subDomains.size();
////		for(int i=0; i<size; i++){			
////			Float displacementX =  translationXs.get(i).asLiteral().getFloat();
////			Float currentXCoord = xCoords.get(i).asLiteral().getFloat();
////			//System.out.println(currentXCoord);
////			RDFNode node = nodes.get(i);
////			RDFNode id = ids.get(i);
////			int nodeNumber = id.asLiteral().getInt();
////			nodeXDisplacement.put(node, displacementX);
////			nodeXCoord.put(node, currentXCoord);
////			//nodesCovered.add(node);
////			idNodeMap.put(nodeNumber, node);
////		}
//		
//		System.out.println("Final Nodes Remaining: " + idNodeMap.size());
//		Assert.assertEquals(""+idNodeMap.size()+"".trim(),"220".trim());
//		
//		List<String> datax = new ArrayList<String>();
//		List<String> datay = new ArrayList<String>();
//		List<Integer> nodeSortedIds = new ArrayList<Integer>();
//		for(int i : idNodeMap.keySet()) 
//			nodeSortedIds.add(i);
//		Collections.sort(nodeSortedIds);
//		Float previousXCoord = Float.MIN_VALUE;		
//		for(Integer id : nodeSortedIds){
//			RDFNode node = idNodeMap.get(id);
//			Float displacement = nodeXDisplacement.get(node);
//			Float currentXCoord = nodeXCoord.get(node);
//			if(currentXCoord > previousXCoord){
//				datax.add(currentXCoord.toString());
//				datay.add(displacement.toString());
//				previousXCoord = currentXCoord;				
//			}
//		}
//		
//		
////		
//		System.out.println(nodeSortedIds.size());
//		System.out.println(datax);
//		System.out.println(datax.size());		
//		System.out.println(datay);
//		System.out.println(datay.size());	
//		
//		
//		
//		FeatureExtractor f = new FeatureExtractor();
//		f.plot2dGFeatService(datax, "NodesInIncreasingOrder", datay, "XDisplacement", "DisplacementGraph","C:/Users/JoaoBoscoJares/workspace/sifem3/Sifem/SifemCore/src/test/resources/" );
//		System.exit(0);
		
	}
	
//	
//	@Test(enabled=false)
//	public void translateAllTTlsToSimpleTriplesMap() throws Exception{
//		String baseFolder = "C:/Users/JoaoBoscoJares/Desktop/tmp/sifem2bkp/SifemCore/src/test/resources/outputRDFDir/";
//		String[] datFiles = "datInput_100.ttl;datInput_1000.ttl;datInput_10000.ttl;datInput_200.ttl;datInput_2000.ttl;datInput_20000.ttl;datInput_300.ttl;datInput_3000.ttl;datInput_50.ttl;datInput_500.ttl;datInput_5000.ttl".split(";");
//		String[] unvFiles = "unvOutput_100.ttl;unvOutput_1000.ttl;unvOutput_10000.ttl;unvOutput_200.ttl;unvOutput_2000.ttl;unvOutput_20000.ttl;unvOutput_300.ttl;unvOutput_3000.ttl;unvOutput_50.ttl;unvOutput_500.ttl;unvOutput_5000.ttl".split(";");
//
//		
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");       
//        SimulationInstanceDAO simulationInstanceDAO = (SimulationInstanceDAO) ctx.getBean(SimulationInstanceDAO.class);
//        
//        Map<String, List<String>> triplesMap = new TreeMap<String, List<String>>();
//        int i=0;
//        long startTime = System.currentTimeMillis();
//        System.out.println(i+") startTime :"+startTime);
//		for(;i<datFiles.length;i++){
//			String namedGraph = "http://www.sifemontologies.com/ontologies/Simulation/Instance_"+i;
//			triplesMap.put(namedGraph,new ArrayList<String>());
//			Model model = ModelFactory.createDefaultModel();
//			DatasetGraph dataset = RDFDataMgr.loadDataset(baseFolder+datFiles[i], Lang.TTL).asDatasetGraph();
//			dataset = RDFDataMgr.loadDataset(baseFolder+unvFiles[i], Lang.TTL).asDatasetGraph();
//			Iterator<Quad> quads = dataset.find();
//			while ( quads.hasNext() ) {
//				Quad quad = quads.next();
//				Triple triple = quad.asTriple();
//				Statement statement = model.asStatement(triple);
//				String s = convertIfURL(statement.getSubject());
//				String p = convertIfURL(statement.getPredicate());
//				String o = convertIfURL(statement.getObject());
//				Boolean isSPOCompleted = isSPOCompleted(s,p,o); 
//				if(!isSPOCompleted){
//					continue;
//				}
//				String spo = s+" "+p+" "+o+" . \n";
//				simulationInstanceDAO.insert(namedGraph,spo);
//			}	
//	        long endTime = System.currentTimeMillis();
//	        System.out.println(i+") endTime :"+(endTime - startTime) / (1000));
//		}
//
//	}
	
	
//	@Test(enabled=false)
//	public void translateAllTTlsToSimpleTriplesSysout() throws Exception{
//		String baseFolder = "C:/Users/JoaoBoscoJares/Desktop/tmp/sifem2bkp/SifemCore/src/test/resources/outputRDFDir/";
//		String[] datFiles = "datInput_100.ttl;datInput_1000.ttl;datInput_10000.ttl;datInput_200.ttl;datInput_2000.ttl;datInput_20000.ttl;datInput_300.ttl;datInput_3000.ttl;datInput_50.ttl;datInput_500.ttl;datInput_5000.ttl".split(";");
//		String[] unvFiles = "unvOutput_100.ttl;unvOutput_1000.ttl;unvOutput_10000.ttl;unvOutput_200.ttl;unvOutput_2000.ttl;unvOutput_20000.ttl;unvOutput_300.ttl;unvOutput_3000.ttl;unvOutput_50.ttl;unvOutput_500.ttl;unvOutput_5000.ttl".split(";");
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");       
//        SimulationInstanceDAO simulationInstanceDAO = (SimulationInstanceDAO) ctx.getBean(SimulationInstanceDAO.class);
//        Map<String, List<String>> triples = new TreeMap<String, List<String>>();
//
//		//String[] datFiles = "sample1.ttl;sample2.ttl;sample3.ttl;sample4.ttl".split(";");
//		int i = 0;
//		long startTime = System.currentTimeMillis();
//		System.out.println(i+") started");
//		for(;i<datFiles.length;i++){
//			triples.put("simulation_"+i,new ArrayList<String>());
//			Model model = ModelFactory.createDefaultModel();
//			DatasetGraph dataset = RDFDataMgr.loadDataset(baseFolder+datFiles[i], Lang.TTL).asDatasetGraph();
//			dataset = RDFDataMgr.loadDataset(baseFolder+unvFiles[i], Lang.TTL).asDatasetGraph();
//			Iterator<Quad> quads = dataset.find();
//			while ( quads.hasNext() ) {
//				Quad quad = quads.next();
//				Triple triple = quad.asTriple();
//				//System.out.println(triple.toString());
//				Statement statement = model.asStatement(triple);
//				//System.out.println(statement.getSubject()+" "+statement.getPredicate()+" "+statement.getObject());
//				String s = convertIfURL(statement.getSubject());
//				String p = convertIfURL(statement.getPredicate());
//				String o = convertIfURL(statement.getObject());
//				Boolean isSPOCompleted = isSPOCompleted(s,p,o); 
//				if(!isSPOCompleted){
//					continue;
//				}
//				String spo = s+" "+p+" "+o+" . \n";
//				triples.get("simulation_"+i).add(spo);
//				
//			}	
//			Thread execution = new Thread(new AsyncThreadProcess(simulationInstanceDAO,i,triples));
//			execution.start();
//			System.out.println("tName: "+execution.getName());
//		}
//		
//	}
//	
//	@Test(enabled=false)
//	public void translateAllTTlsToSimpleTriplesSysout2() throws Exception{
//		String baseFolder = "C:/Users/JoaoBoscoJares/Desktop/tmp/sifem2bkp/SifemCore/src/test/resources/outputRDFDir/";
//		String[] datFiles = "datInput_100.ttl;datInput_1000.ttl;datInput_10000.ttl;datInput_200.ttl;datInput_2000.ttl;datInput_20000.ttl;datInput_300.ttl;datInput_3000.ttl;datInput_50.ttl;datInput_500.ttl;datInput_5000.ttl".split(";");
//		String[] unvFiles = "unvOutput_100.ttl;unvOutput_1000.ttl;unvOutput_10000.ttl;unvOutput_200.ttl;unvOutput_2000.ttl;unvOutput_20000.ttl;unvOutput_300.ttl;unvOutput_3000.ttl;unvOutput_50.ttl;unvOutput_500.ttl;unvOutput_5000.ttl".split(";");
//		
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");       
//        SimulationInstanceDAO simulationInstanceDAO = (SimulationInstanceDAO) ctx.getBean(SimulationInstanceDAO.class);
//        
//        Map<String, List<Triple>> triples = new TreeMap<String, List<Triple>>();
//        long startTime = System.currentTimeMillis();
//		for(int i=0;i<datFiles.length;i++){
//			SimulationProcessManagerTO simulationProcessManager = new SimulationProcessManagerTO();
//			System.out.println(i+") startTime :"+startTime);
//			triples.put("simulation_"+i,new ArrayList<Triple>());
//			DatasetGraph dataset = RDFDataMgr.loadDataset(baseFolder+datFiles[i], Lang.TTL).asDatasetGraph();
//			dataset = RDFDataMgr.loadDataset(baseFolder+unvFiles[i], Lang.TTL).asDatasetGraph();
//			Iterator<Quad> quads = dataset.find();
//			while ( quads.hasNext() ) {
//				Quad quad = quads.next();
//				Triple triple = quad.asTriple();
//				String simulationName = "simulation_"+i;
//				triples.get(simulationName).add(triple);
//				//simulationProcessManager.setTriples(triples);
//				simulationProcessManager.setSimulationName(simulationName);
//			}	
//			//simulationInstanceDAO.insertAsTriples(simulationProcessManager);
//		}
//		long endTime = System.currentTimeMillis();
//		System.out.println("(0) totalTime :"+(endTime - startTime) / (1000));
//		System.out.println("end!");
//		
//	}
//	
//	@Test(enabled=false)
//	public void translateAllTTlsToSimpleTriplesSysout3() throws Exception{
//		String baseFolder = "C:/Users/JoaoBoscoJares/Desktop/tmp/sifem2bkp/SifemCore/src/test/resources/outputRDFDir/";
//		String[] datFiles = "datInput_100.ttl;datInput_1000.ttl;datInput_10000.ttl;datInput_200.ttl;datInput_2000.ttl;datInput_20000.ttl;datInput_300.ttl;datInput_3000.ttl;datInput_50.ttl;datInput_500.ttl;datInput_5000.ttl".split(";");
//		String[] unvFiles = "unvOutput_100.ttl;unvOutput_1000.ttl;unvOutput_10000.ttl;unvOutput_200.ttl;unvOutput_2000.ttl;unvOutput_20000.ttl;unvOutput_300.ttl;unvOutput_3000.ttl;unvOutput_50.ttl;unvOutput_500.ttl;unvOutput_5000.ttl".split(";");
//		
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");       
//        SimulationInstanceDAO simulationInstanceDAO = (SimulationInstanceDAO) ctx.getBean(SimulationInstanceDAO.class);
//        
//        Map<String, List<Triple>> triples = new TreeMap<String, List<Triple>>();
//		for(int i=0;i<datFiles.length;i++){
//			long startTime = System.currentTimeMillis();
//			System.out.println(i+") startTime :"+startTime);
//			triples.put("simulation_"+i,new ArrayList<Triple>());
//			DatasetGraph dataset = RDFDataMgr.loadDataset(baseFolder+datFiles[i], Lang.TTL).asDatasetGraph();
//			dataset = RDFDataMgr.loadDataset(baseFolder+unvFiles[i], Lang.TTL).asDatasetGraph();
//			Iterator<Quad> quads = dataset.find();
//			while ( quads.hasNext() ) {
//				Quad quad = quads.next();
//				Triple triple = quad.asTriple();
//				triples.get("simulation_"+i).add(triple);
//				
//			}	
//			//insertTest(triples);
//	        long endTime = System.currentTimeMillis();
//	        System.out.println(i+") endTime :"+(endTime - startTime) / (1000));
//		}
//		System.out.println("end!");
//		
//	}
//	
//	@Test(enabled=false)
//	public void insertTest(/*Map<String, List<Triple>> triples*/) throws SQLException {
//		 Connection conn = DriverManager.getConnection("jdbc:virtuoso://localhost:1111/", "dba", "dba");
//		 System.out.println(conn);
////		DataSource dataSource = (DataSource) new
////		ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml").getBean("dataSource"); 
////		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
////		jdbcTemplate.execute("sparql " + "insert into graph <mttest> { <xxx> <P01> \"test5\" }");
//	}
//
//	private Boolean isSPOCompleted(String s, String p, String o) {
//		return!((s==null || "".equals(s))||(p==null || "".equals(p))||(o==null || "".equals(o)));
//	}
//


}
