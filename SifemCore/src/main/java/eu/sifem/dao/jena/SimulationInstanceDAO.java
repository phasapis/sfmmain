package eu.sifem.dao.jena;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import virtuoso.jena.driver.VirtDataset;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;
import virtuoso.jena.driver.VirtuosoUpdateFactory;
import virtuoso.jena.driver.VirtuosoUpdateRequest;

import com.hp.hpl.jena.query.ResultSet;

import eu.sifem.service.ITurtleFormatRDFWriterService;
import eu.sifem.service.dao.IDatAndUnvTTLDAOService;
import eu.sifem.service.dao.ISimulationInstanceDAOService;
import eu.sifem.service.dao.ISimulationProcessManagerDAO;

/**
 * 
 * @author jbjares
 * 
 */
@Repository
public class SimulationInstanceDAO implements ISimulationInstanceDAOService {

//	@Autowired
//	private VirtDataset virtDataset;

	@Autowired
	private JenaModel jenaModel;

	@Autowired
	private ISimulationProcessManagerDAO simulationProcessManagerDAO;
	
	@Autowired
	private ITurtleFormatRDFWriterService turtleFormatRDFWriter;
	
	@Autowired
	private IDatAndUnvTTLDAOService datAndUnvTTLDAOService;

//	@Override
//	public List<String> insert(String namedGraph, String triple)
//			throws Exception {
//		List<String> insertFailList = new ArrayList<String>();
//		StringBuilder sb = new StringBuilder();
//
//		if (namedGraph == null || "".equals(namedGraph)) {
//			// TODO maybe throw an exception.
//			return Collections.EMPTY_LIST;
//		}
//		if (triple == null || "".equals(triple)) {
//			// TODO maybe throw an exception.
//			return Collections.EMPTY_LIST;
//		}
//		int i = 0;
//		long startTime = System.currentTimeMillis();
//		virtDataset.begin(ReadWrite.WRITE);
//		sb.append(" INSERT INTO GRAPH ");
//		sb.append(" <" + namedGraph + "> { ");
//		if (triple.trim().endsWith(".")) {
//			sb.append(triple);
//		} else {
//			sb.append(triple + " . ");
//		}
//		sb.append(" } ");
//		System.out.println(sb.toString());
//		try {
//			executeUpdate(namedGraph, sb.toString(), virtDataset);
//		} catch (Exception e) {
//			insertFailList.add(sb.toString());
//		} finally {
//			sb = new StringBuilder();
//			virtDataset.commit();
//			long endTime = System.currentTimeMillis();
//			System.out.println(i + ") endTime :" + (endTime - startTime)
//					/ (1000));
//			System.out
//					.println("************************************************");
//		}
//
//		return insertFailList;
//	}
//
//	@Override
//	public List<SimulationProcessSPOGTO> findAll() throws Exception {
//		List<SimulationProcessSPOGTO> result = new ArrayList<SimulationProcessSPOGTO>();
//		StringBuilder sb = new StringBuilder();
//		List<SimulationProcessManagerTO> simulationProcessManagerTOList = simulationProcessManagerDAO
//				.findAll();
//		sb.append("SELECT ?s ?p ?o ?g ");
//		for (SimulationProcessManagerTO simulationProcessManagerTO : simulationProcessManagerTOList) {
//			for (String namedGraph : simulationProcessManagerTO
//					.getSimulationInstanceName()) {
//				sb.append("FROM NAMED ");
//				sb.append(namedGraph);
//				sb.append(" ");
//			}
//		}
//		sb.append("WHERE { GRAPH ?g { ?s ?p ?o } }");
//
//		try {
//			virtDataset.begin(ReadWrite.READ);
//			ResultSet rs = executeQuery(sb.toString(), virtDataset);
//			while (rs.hasNext()) {
//				SimulationProcessSPOGTO spog = new SimulationProcessSPOGTO();
//				QuerySolution qs = rs.next();
//				String s = qs.get("?s").toString();
//				String p = qs.get("?p").toString();
//				String o = qs.get("?o").toString();
//				String g = qs.get("?g").toString();
//				spog.setSubject(s);
//				spog.setPredicate(p);
//				spog.setObject(o);
//				spog.setGraph(g);
//				result.add(spog);
//			}
//		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage(), e);
//		} finally {
//			sb = new StringBuilder();
//		}
//
//		return result;
//	}
//
//	@Override
//	public void findAllGreenWood() throws Exception {
//		StringBuilder sb = new StringBuilder();
//		List<SimulationProcessManagerTO> simulationProcessManagerTOList = simulationProcessManagerDAO
//				.findAll();
//		sb.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
//				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
//				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
//				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
//				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
//				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
//				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> ");
//		sb.append(" SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id ?excitationFreqVal ");
//
//		//
//		sb.append("FROM NAMED ");
//		String namedGraph = simulationProcessManagerTOList.get(0)
//				.getSimulationInstanceName().get(0);
//		System.out.println(namedGraph);
//		sb.append(namedGraph);
//		sb.append(" ");
//		//
//
//		sb.append("WHERE { GRAPH ?g { ");
//		sb.append(" ?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType.  ?node rdf:type fem:Node .   "
//				+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .   "
//				+ "?loadResource rdf:type fem:Load. ?loadResource fem:holdsValueFor ?excitationFreq. ?excitationFreq rdf:type fem:Frequency.  "
//				+ "?excitationFreq sim:hasScalarValue ?excitationFreqValObj. ?excitationFreqValObj sim:hasScalarDataValue ?excitationFreqVal. ");
//		sb.append(" } }");
//
//		try {
//			virtDataset.begin(ReadWrite.READ);
//			ResultSet rs = executeQuery(sb.toString(), virtDataset);
//			while (rs.hasNext()) {
//				QuerySolution qs = rs.next();
//				String material = qs.get("?material").toString();
//				String materialType = qs.get("?materialType").toString();
//				String node = qs.get("?node").toString();
//				String xCoord = qs.get("?xCoord").toString();
//				String yCoord = qs.get("?yCoord").toString();
//				String zCoord = qs.get("?zCoord").toString();
//				String translationX = qs.get("?translationX").toString();
//				String translationY = qs.get("?translationY").toString();
//				String translationZ = qs.get("?translationZ").toString();
//				String subDomain = qs.get("?subDomain").toString();
//				String id = qs.get("?id").toString();
//				String excitationFreqVal = qs.get("?excitationFreqVal")
//						.toString();
//
//				System.out.println(material);
//				System.out.println(materialType);
//				System.out.println(node);
//				System.out.println(xCoord);
//				System.out.println(yCoord);
//				System.out.println(zCoord);
//				System.out.println(translationX);
//				System.out.println(translationY);
//				System.out.println(translationZ);
//				System.out.println(subDomain);
//				System.out.println(id);
//				System.out.println(excitationFreqVal);
//
//			}
//		} catch (Exception e) {
//			throw new RuntimeException(e.getMessage(), e);
//		} finally {
//			sb = new StringBuilder();
//		}
//
//	}

	// TODO Create a test case
	// public static void main(String[] args) throws Exception {
	// ApplicationContext ctx = new
	// ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");
	// ISimulationInstanceDAOService simulationInstanceDAO =
	// (ISimulationInstanceDAOService) ctx.getBean("simulationInstanceDAO");
	// simulationInstanceDAO.findAllGreenWood();
	// }

	public String stackTraceToString(Throwable e) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement element : e.getStackTrace()) {
			sb.append(element.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

	public void executeUpdate(String namedGraph, String query,
			VirtDataset virtDataset) {
		VirtGraph vg = new VirtGraph(namedGraph, virtDataset.getGraphUrl(),
				virtDataset.getGraphUser(), virtDataset.getGraphPassword());
		VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(query, vg);
		vur.exec();
		if (!vg.isClosed()) {
			vg.close();
		}
		return;
	}

	public ResultSet executeQuery(String query, VirtDataset virtDataset) {
		VirtGraph vg = new VirtGraph(virtDataset.getGraphUrl(),
				virtDataset.getGraphUser(), virtDataset.getGraphPassword());
		VirtuosoQueryExecution vur = VirtuosoQueryExecutionFactory.create(
				query, vg);
		ResultSet rs = vur.execSelect();
		// if (!vg.isClosed()) {
		// vg.close();
		// }
		return rs;
	}

	protected void clearGraph(String namedGraph, VirtDataset virtDataset) {
		StringBuilder sb = new StringBuilder();
		sb.append("CLEAR GRAPH " + namedGraph);
		VirtuosoUpdateRequest vur = VirtuosoUpdateFactory.create(
				sb.toString(),
				new VirtGraph(virtDataset.getGraphUrl(), virtDataset
						.getGraphUser(), virtDataset.getGraphPassword()));
		vur.exec();
	}



//	public static void main(String[] args) throws Exception {
//	
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:SifemCore-applicationContext.xml");
//        PakSparulRDFMapper mapper = ctx.getBean(PakSparulRDFMapper.class);
//
//		String caseDirPath = "src/test/resources/tmp/PAKC/caseDir/";
//		String simulationName = "SimulationOne";
//		String instanceName = "Instance_0";
//		String simulationURLPrefix = "http://www.sifemontologies.com/ontologies/Box.owl#";
//		String projectName = "ProjectOne";
//
//		String simulationNamePrefix = "bw";
//		String prefixesFile = "C:/SifemWindowsResourceFiles/ConceptualModel/PAK/utils/newPrefixesFile.txt";		
//		mapper.setFemModelName(simulationName);
//		mapper.setFemModelNamePrefix(simulationNamePrefix);
//		String workspaceBasePath = "src/test/resources/tmp/PAKC/workspace/";
//		SimulationInstanceTO simulationInstanceTO = new SimulationInstanceTO();
//		simulationInstanceTO.setInstanceName(instanceName);
//		simulationInstanceTO.setProjectName(projectName);
//		simulationInstanceTO.setSimulationName(simulationName);
//		simulationInstanceTO.setSimulationURLPrefix(simulationURLPrefix);
//		simulationInstanceTO.setWorkspacebasePath(workspaceBasePath);
//		
//		
//		mapper.setPrefixFile(prefixesFile);
//		File[] files = (new File(caseDirPath)).listFiles();
//		for(File file : files){
//			if(StringUtils.endsWith(file.getName(),".dat")){	
//				simulationInstanceTO.setDataFileLocalPath(file.getCanonicalPath());
//				simulationInstanceTO.setFileName("dat_input.ttl");
//				mapper.datToRDF(simulationInstanceTO);
//				System.out.println("DONE " + file.getName());
//			} 
//			if(StringUtils.endsWith(file.getName(),".unv")){
//				simulationInstanceTO.setUnvFileLocalPath(file.getCanonicalPath());
//				simulationInstanceTO.setFileName("unv_output.ttl");
//				mapper.unvToRDF(simulationInstanceTO);
//				System.out.println("DONE " + file.getName());
//			}
//		}
//		
//	}

}
