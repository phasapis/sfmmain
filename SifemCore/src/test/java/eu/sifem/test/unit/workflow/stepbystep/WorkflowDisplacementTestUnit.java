package eu.sifem.test.unit.workflow.stepbystep;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import virtuoso.jena.driver.VirtDataset;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

import eu.sifem.dao.jena.JenaModel;
import eu.sifem.featurextraction.FeatureExtractor;
import eu.sifem.model.enums.ParameterTypes;
import eu.sifem.model.helper.CombinationHelper;
import eu.sifem.model.to.ConfigFileTO;
import eu.sifem.model.to.DataSetHashCacheTO;
import eu.sifem.model.to.ExternalLoadParametersTO;
import eu.sifem.model.to.GeometrySetupTO;
import eu.sifem.model.to.MeshSetupTO;
import eu.sifem.model.to.PAKCRestServiceTO;
import eu.sifem.model.to.PAKCRestServiceWrapperTO;
import eu.sifem.model.to.ParameterTO;
import eu.sifem.model.to.SimulationInstanceTO;
import eu.sifem.model.to.SolverConfigCreatorTO;
import eu.sifem.model.to.ViewTO;
import eu.sifem.service.IPakRDFMapperService;
import eu.sifem.service.IPakSolverControlerService;
import eu.sifem.service.ISolverConfigCreatorService;
import eu.sifem.service.dao.IDataSetHashCacheDAOService;
import eu.sifem.utils.Util;


@ContextConfiguration(locations={"classpath:SifemCore-applicationContext_desenv.xml"})
public class WorkflowDisplacementTestUnit extends AbstractTestNGSpringContextTests{

	private static final String HTTP_WWW_SIFEMONTOLOGIES_COM_ONTOLOGIES_TEST = "http://www.sifemontologies.com/ontologies/test";
	private static final String FLUID = "FLUID";
	private static final String DAMPING = "DAMPING";
	private static final String YOUNG = "YOUNG";
	private static final String FREQUENCY = "FREQUENCY";
	
	private static final String FLUID_ID = "4";
	private static final String DAMPING_ID = "6";
	private static final String YOUNG_ID = "5";
	private static final String FREQUENCY_ID = "3";

	private static final String C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE = "src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/workspaceTest";
	private static final String PAK = "PAK";
	
	private Integer size;
	
	@Autowired
	private ISolverConfigCreatorService solverConfigCreator;
	

	@Autowired
	private VirtDataset virtDataset;
	
	@Autowired
	private IDataSetHashCacheDAOService dataSetHashCacheDAO;
	

	@Test(groups= { "default" },priority=0, enabled=false)
	public void testServiceIsNotNull() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Assert.assertNotNull(solverConfigCreator);
		Assert.assertNotNull(virtDataset);
		Assert.assertNotNull(dataSetHashCacheDAO);
	}
	
	//#1
	//Populate fields and generate new cfg file.
	@Test(groups= { "default" },priority=1,enabled=false)
	public void testSolverConfigCreatorChangeMESHDIVISIONS() throws Exception{
				
		//a) Create SolverConfigCreatorTO Object;
		SolverConfigCreatorTO solverConfigCreatorTO = new SolverConfigCreatorTO();
		
		//b) set workspace and solver name into SolverConfigCreatorTO;
		solverConfigCreatorTO.setWorkspace(C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE);
		solverConfigCreatorTO.setSolverName(PAK);
		
		//c) Create and configure MeshSetupTO, then set this object into SolverConfigCreatorTO;
		MeshSetupTO meshSetupTO = new MeshSetupTO();
		meshSetupTO.setDivisionB(4.0);
		meshSetupTO.setDivisionh(3.0);
		meshSetupTO.setDivisionH(4.0);
		meshSetupTO.setDivisionL(100.00);
		meshSetupTO.setDivisionW(4.0);
		solverConfigCreatorTO.setMeshSetupTO(meshSetupTO);
		
		//d) Create and configure GeometrySetupTO, then set this object into SolverConfigCreatorTO;
		GeometrySetupTO geometrySetupTO = new GeometrySetupTO();
		geometrySetupTO.setHeigthH(0.001);
		geometrySetupTO.setLengthL(0.035);
		geometrySetupTO.setThicknessh(5e-005);
		geometrySetupTO.setWidthBM(0.0003);
		geometrySetupTO.setWidthW(0.001);
		solverConfigCreatorTO.setGeometrySetupTO(geometrySetupTO);
		
		//e) Create and configure LoadParametersTO, then set this object into SolverConfigCreatorTO;
		List<ParameterTO> loadParametersTOList = new ArrayList<ParameterTO>();

		
		ParameterTO loadParametersTOFrequency = new ParameterTO();
		//50.0;100.0;200.0;300.0;500.0;1000.0;2000.0,3000.0;5000.0;10000.0;20000.0
		loadParametersTOFrequency.setParameterUniqueValue("1000.0");
		loadParametersTOFrequency.setName(FREQUENCY);
		loadParametersTOFrequency.setId(FREQUENCY_ID);
		loadParametersTOFrequency.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersTOFrequency);

		ParameterTO loadParametersFluid = new ParameterTO();
		loadParametersFluid.setParameterUniqueValue("1000.0");
		loadParametersFluid.setName(FLUID);
		loadParametersFluid.setId(FLUID_ID);
		loadParametersFluid.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersFluid);
		
		ParameterTO loadParametersDamping = new ParameterTO();
		InputStream inDamping = new FileInputStream(new File("src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/csvFiles/damping.csv"));
		loadParametersDamping.setParameterFile(inDamping);
		loadParametersDamping.setName(DAMPING);
		loadParametersDamping.setId(DAMPING_ID);
		loadParametersDamping.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersDamping);
		
		ParameterTO loadParametersYoungsModulus = new ParameterTO();
		//src/test/resources/unv_output.ttl
		InputStream inYoungsModulus = new FileInputStream(new File("src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/csvFiles/youngs.csv"));
		loadParametersYoungsModulus.setParameterFile(inYoungsModulus);
		loadParametersYoungsModulus.setName(YOUNG);
		loadParametersYoungsModulus.setId(YOUNG_ID);
		loadParametersYoungsModulus.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersYoungsModulus);

		
		String projectName = "projectTest";
		String simulationName = "simulationUnitTest";
		solverConfigCreatorTO.setLoadParametersTOList(loadParametersTOList);
		solverConfigCreatorTO.setSimulationName(simulationName);
		
					
		//i) Create and configure ExternalLoadParametersTO, then set this object into SolverConfigCreatorTO;
		ExternalLoadParametersTO externalLoadParametersTO = new ExternalLoadParametersTO();
		solverConfigCreatorTO.setExternalLoadParametersTO(externalLoadParametersTO);
		
		Map<ParameterTypes,ParameterTO>  mapTeplateKeysAndIds = new TreeMap<ParameterTypes,ParameterTO>();
		Map<String, List<String>> mapAttributeValues = solverConfigCreator.getAttribMapValuesService(solverConfigCreatorTO,mapTeplateKeysAndIds);
		Map<String,CombinationHelper> combinationMap = new TreeMap<String,CombinationHelper>();
		
		size = solverConfigCreator.applyCombinationMapService(mapAttributeValues,solverConfigCreatorTO.getLoadParametersTOList(),combinationMap);
		
		Assert.assertEquals(size.intValue(),1);
		

		List<String> cfgFile = solverConfigCreator.createCfgFilesAsStringService(solverConfigCreatorTO,mapAttributeValues,mapTeplateKeysAndIds);
		solverConfigCreator.saveOrUpdateCFGTO(solverConfigCreatorTO,cfgFile,projectName);
		
		List<ConfigFileTO> cfgTOList = solverConfigCreator.findConfigFiles(projectName,simulationName);
		Assert.assertNotNull(cfgTOList.get(0));
		
		//InputStream is = solverConfigCreator.findCFGFile(cfgTOList.get(0));
		List<String> cFGFileAsStringLines = FileUtils.readLines(new File(C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE+"/"+simulationName+"/instance_0/input.cfg"), "UTF-8");

//		Assert.assertEquals(cFGFileAsStringLines.get(1).trim(), "#MESH DIVISIONS".trim());
//		Assert.assertEquals(cFGFileAsStringLines.get(2).trim(), "#(UINT)DIV_L (UINT)DIV_W (UINT)DIV_B (UINT)DIV_H (UINT)DIV_h".trim());
//		Assert.assertEquals(cFGFileAsStringLines.get(3).trim(), "100.0 4.0 4.0 4.0 3.0".trim());
		
	}
	
	
	//#2
	//call solver rest service and generate .dat and .unv files
	private static final String OUTPUT_UNV = "src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/output/output.unv";
	private static final String OUTPUT_DAT = "src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/output/output.dat";
	private static final String C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE_PROJECT_ONE_SIMULATION_INSTANCE_0_INPUT_CFG = "src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/workspaceTest/simulationUnitTest/instance_0/input.cfg";
	private static final String HTTP_LOCALHOST_8080_SOLVER_WS_REST_SOLVER_REST_EXECUTE_SOLVER = "http://localhost:8080/SolverWS/rest/solverRest/executeSolver";
	private static final String SIMULATION_INSTANCE = "simulationInstance";

	@Test(groups= { "default" },priority=2,enabled=false)
	public void testHttpPostToRestService() throws Exception {
		PAKCRestServiceWrapperTO simulationInstance = new PAKCRestServiceWrapperTO();
		simulationInstance.setCfgFile(new FileInputStream(new File(C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE_PROJECT_ONE_SIMULATION_INSTANCE_0_INPUT_CFG)));
		String parameter = Util.getJsonStrFromObject(simulationInstance);
		String responseContentStr = Request.Post(HTTP_LOCALHOST_8080_SOLVER_WS_REST_SOLVER_REST_EXECUTE_SOLVER)
		.bodyForm(Form.form().add(SIMULATION_INSTANCE,parameter).build()).execute().returnContent().asString();
		//System.out.println(responseContentStr);
		
		Gson gson = new GsonBuilder().create();
		simulationInstance  = gson.fromJson(responseContentStr, PAKCRestServiceWrapperTO.class);
		InputStream datByteArr = simulationInstance.getDatFile();
		InputStream unvByteArr = simulationInstance.getUnvFile();
		Assert.assertNotNull(datByteArr);
		Assert.assertNotNull(unvByteArr);
		
		File outputDat = new File(OUTPUT_DAT);
		File outputUnv = new File(OUTPUT_UNV);
		
		FileUtils.copyInputStreamToFile(datByteArr,outputDat);
		FileUtils.copyInputStreamToFile(unvByteArr,outputUnv);
		
		Assert.assertTrue(outputDat.exists());
		Assert.assertTrue(outputUnv.exists());
	}
	
	
	//#3
	//Convert .dat and .unv to .ttl files
	@Autowired
	private IPakSolverControlerService pakSolverControlerService;
	
	@Autowired
	private IPakRDFMapperService pakRDFMapperService;
	
	@Autowired
	private IPakRDFMapperService pakRDFMapper;
	
	private SimulationInstanceTO simulationInstanceTO = new SimulationInstanceTO();
	
	

	@Test(groups= { "default" },priority=3,enabled=false)
	public void testConvertDatAndunvToTTL() throws Exception{
		Assert.assertNotNull(pakSolverControlerService);
		simulationInstanceTO.setProjectName("project");
		simulationInstanceTO.setSimulationName("simulationUnitTest");
		simulationInstanceTO.setInstanceName("instance_0");
		simulationInstanceTO.setWorkspacebasePath("src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/workspaceTest/");
		
		
		
		File outputDat = new File(OUTPUT_DAT);
		File outputUnv = new File(OUTPUT_UNV);
		InputStream datFile = pakRDFMapper.datToRDFService(simulationInstanceTO,new FileInputStream(outputDat));
		Assert.assertNotNull(datFile);
		
		InputStream unvFile = pakRDFMapper.unvToRDFService(simulationInstanceTO,new FileInputStream(outputUnv));
		Assert.assertNotNull(unvFile);
		
	}



	//#4
	//Insert .ttl data into virtuoso
	private static final String TTL = "*.ttl";
	private static final String NAMED_GRAPH = "NAMED_GRAPH";
	private static final String FILE_TYPE = "FILE_TYPE";
	private static final String SECURITY_FOLDER = "SECURITY_FOLDER";
	
	@Test(groups= { "default" },priority=4,enabled=false)
	public void testInsertTTlIntoVirtuoso() throws Exception{
		
		System.out.println("START");
	    ProcessBuilder pb = new ProcessBuilder("src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/workspaceTest/simulationUnitTest/batch/uploadTTLWindows.bat");
	    
		Map<String, String> env = pb.environment();
		env.put(SECURITY_FOLDER,"C:/SifemWindowsResourceFiles/securityDumpFolder");
		env.put(FILE_TYPE, TTL);
		env.put(NAMED_GRAPH,"http://www.sifemontologies.com/ontologies/displacement_1000");
		pb.redirectOutput(new File("src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/workspaceTest/simulationUnitTest/batch/log.txt"));
		Process p = pb.start();
		p.waitFor(); 		
		System.out.println("DONE");
		
	}
	
	//#5
	//Test if namedgraph was properly inserted into virtuoso.
	@Test(groups= { "default" },priority=5,enabled=false)
	public void testQueryOnVirtuoso() throws Exception{
		String query = "ASK WHERE {GRAPH <"+HTTP_WWW_SIFEMONTOLOGIES_COM_ONTOLOGIES_TEST+"> {?s ?p ?o.}}";
		VirtGraph vg = new VirtGraph(virtDataset.getGraphUrl(),
				virtDataset.getGraphUser(), virtDataset.getGraphPassword());
		VirtuosoQueryExecution vur = VirtuosoQueryExecutionFactory.create(query, vg);
		Boolean result = vur.execAsk();
		Assert.assertTrue(result);
		
	}
	

	//#6
	//Test if namedgraph was properly inserted into virtuoso.
	@Test(groups= { "default" },priority=6,enabled=false)
	public void testQueryIntoTtlFiles() throws Exception{
		String datTurtle = "src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/workspaceTest/simulationUnitTest/instance_0/ttl/dat_input.ttl";
		String unvTurtle = "src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/workspaceTest/simulationUnitTest/instance_0/ttl/unv_output.ttl";
		JenaModel model = new JenaModel();
		model.importDataService(datTurtle);
		model.importDataService(unvTurtle);
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

		Map<String, List<RDFNode>> queryModel = model.queryModelService(query, queryVariables);		

		//List<RDFNode> materials = queryModel.get(queryVariables.get(0));
		//List<RDFNode> materialTypes = queryModel.get(queryVariables.get(1));		
		List<RDFNode> nodes = queryModel.get(queryVariables.get(2));
		List<RDFNode> xCoords = queryModel.get(queryVariables.get(3));
		//List<RDFNode> yCoords = queryModel.get(queryVariables.get(4));
		//List<RDFNode> zCoords = queryModel.get(queryVariables.get(5));
		List<RDFNode> translationXs = queryModel.get(queryVariables.get(6));
		//List<RDFNode> translationYs = queryModel.get(queryVariables.get(7));
		//List<RDFNode> translationZs = queryModel.get(queryVariables.get(8));
		List<RDFNode> subDomains = queryModel.get(queryVariables.get(9));
		List<RDFNode> ids = queryModel.get(queryVariables.get(10));

		System.out.println("Nodes number: " + nodes.size());

		Assert.assertEquals(""+nodes.size()+"".trim(),"9600".trim());
		
		
		//Set<RDFNode> nodesCovered = new HashSet<RDFNode>();
		Map<RDFNode, Float> nodeXDisplacement = new HashMap<RDFNode, Float>();
		Map<Integer, RDFNode> idNodeMap = new HashMap<Integer, RDFNode>();
		Map<RDFNode, Float> nodeXCoord = new HashMap<RDFNode, Float>();

		int size = subDomains.size();
		for(int i=0; i<size; i++){			
			Float displacementX =  translationXs.get(i).asLiteral().getFloat();
			Float currentXCoord = xCoords.get(i).asLiteral().getFloat();
			//System.out.println(currentXCoord);
			RDFNode node = nodes.get(i);
			RDFNode id = ids.get(i);
			int nodeNumber = id.asLiteral().getInt();
			nodeXDisplacement.put(node, displacementX);
			nodeXCoord.put(node, currentXCoord);
			//nodesCovered.add(node);
			idNodeMap.put(nodeNumber, node);
		}
		
		System.out.println("Final Nodes Remaining: " + idNodeMap.size());
		Assert.assertEquals(""+idNodeMap.size()+"".trim(),"220".trim());
		
		List<String> datax = new ArrayList<String>();
		List<String> datay = new ArrayList<String>();
		List<Integer> nodeSortedIds = new ArrayList<Integer>();
		for(int i : idNodeMap.keySet()) 
			nodeSortedIds.add(i);
		Collections.sort(nodeSortedIds);
		Float previousXCoord = Float.MIN_VALUE;		
		for(Integer id : nodeSortedIds){
			RDFNode node = idNodeMap.get(id);
			Float displacement = nodeXDisplacement.get(node);
			Float currentXCoord = nodeXCoord.get(node);
			if(currentXCoord > previousXCoord){
				datax.add(currentXCoord.toString());
				datay.add(displacement.toString());
				previousXCoord = currentXCoord;				
			}
		}
		
		
//		
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

	//#7
	//Test query into virtuoso.
	@Test(groups= { "default" },priority=7,enabled=false)
	public void testQueryIntoVirtuoso() throws Exception{
		JenaModel model = new JenaModel();

		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
				+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id  "
				+ "FROM NAMED <http://www.sifemontologies.com/ontologies/test>  "
				+ "WHERE {  "
				+ "GRAPH ?g {  "
				+ "?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .   "
				+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  "
				+ "} "
				+ " }";
		
		List<String> queryVariables = new ArrayList<String>();

		ResultSet results = Util.executeQuery(query, virtDataset);
		
		queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
		queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
		queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
		queryVariables.add("subDomain");queryVariables.add("id");

		 Map<String, List<RDFNode>> queryModel = model.queryModelWithResultService(results,queryVariables);	

		//List<RDFNode> materials = queryModel.get(queryVariables.get(0));
		//List<RDFNode> materialTypes = queryModel.get(queryVariables.get(1));		
		List<RDFNode> nodes = queryModel.get(queryVariables.get(2));
		List<RDFNode> xCoords = queryModel.get(queryVariables.get(3));
		//List<RDFNode> yCoords = queryModel.get(queryVariables.get(4));
		//List<RDFNode> zCoords = queryModel.get(queryVariables.get(5));
		List<RDFNode> translationXs = queryModel.get(queryVariables.get(6));
		//List<RDFNode> translationYs = queryModel.get(queryVariables.get(7));
		//List<RDFNode> translationZs = queryModel.get(queryVariables.get(8));
		List<RDFNode> subDomains = queryModel.get(queryVariables.get(9));
		List<RDFNode> ids = queryModel.get(queryVariables.get(10));

		System.out.println("Nodes number: " + nodes.size());

		Assert.assertEquals(""+nodes.size()+"".trim(),"9600".trim());
		
		
		//Set<RDFNode> nodesCovered = new HashSet<RDFNode>();
		Map<RDFNode, Float> nodeXDisplacement = new HashMap<RDFNode, Float>();
		Map<Integer, RDFNode> idNodeMap = new HashMap<Integer, RDFNode>();
		Map<RDFNode, Float> nodeXCoord = new HashMap<RDFNode, Float>();

		int size = subDomains.size();
		for(int i=0; i<size; i++){			
			Float displacementX =  translationXs.get(i).asLiteral().getFloat();
			Float currentXCoord = xCoords.get(i).asLiteral().getFloat();
			//System.out.println(currentXCoord);
			RDFNode node = nodes.get(i);
			RDFNode id = ids.get(i);
			int nodeNumber = id.asLiteral().getInt();
			nodeXDisplacement.put(node, displacementX);
			nodeXCoord.put(node, currentXCoord);
			//nodesCovered.add(node);
			idNodeMap.put(nodeNumber, node);
		}
		
		System.out.println("Final Nodes Remaining: " + idNodeMap.size());
		Assert.assertEquals(""+idNodeMap.size()+"".trim(),"2020".trim());
		
		List<String> datax = new ArrayList<String>();
		List<String> datay = new ArrayList<String>();
		List<Integer> nodeSortedIds = new ArrayList<Integer>();
		for(int i : idNodeMap.keySet()) 
			nodeSortedIds.add(i);
		Collections.sort(nodeSortedIds);
		Float previousXCoord = Float.MIN_VALUE;		
		for(Integer id : nodeSortedIds){
			RDFNode node = idNodeMap.get(id);
			Float displacement = nodeXDisplacement.get(node);
			Float currentXCoord = nodeXCoord.get(node);
			if(currentXCoord > previousXCoord){
				datax.add(currentXCoord.toString());
				datay.add(displacement.toString());
				previousXCoord = currentXCoord;				
			}
		}
		
		
//		
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
	
	//#8
	//Test query cache generation 
	@Test(groups= { "default" },priority=8,enabled=false)
	public void testQueryCacheGenerationFromVirtuosoToMongoDB() throws Exception{
		JenaModel model = new JenaModel();

		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
				+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id  "
				+ "FROM NAMED <http://www.sifemontologies.com/ontologies/test>  "
				+ "WHERE {  "
				+ "GRAPH ?g {  "
				+ "?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .   "
				+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  "
				+ "} "
				+ " }";
		
		List<String> queryVariables = new ArrayList<String>();

		ResultSet results = Util.executeQuery(query, virtDataset);
		
		queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
		queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
		queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
		queryVariables.add("subDomain");queryVariables.add("id");

		 Map<String, List<RDFNode>> queryModel = model.queryModelWithResultService(results,queryVariables);	

		//List<RDFNode> materials = queryModel.get(queryVariables.get(0));
		//List<RDFNode> materialTypes = queryModel.get(queryVariables.get(1));		
		List<RDFNode> nodes = queryModel.get(queryVariables.get(2));
		List<RDFNode> xCoords = queryModel.get(queryVariables.get(3));
		//List<RDFNode> yCoords = queryModel.get(queryVariables.get(4));
		//List<RDFNode> zCoords = queryModel.get(queryVariables.get(5));
		List<RDFNode> translationXs = queryModel.get(queryVariables.get(6));
		//List<RDFNode> translationYs = queryModel.get(queryVariables.get(7));
		//List<RDFNode> translationZs = queryModel.get(queryVariables.get(8));
		List<RDFNode> subDomains = queryModel.get(queryVariables.get(9));
		List<RDFNode> ids = queryModel.get(queryVariables.get(10));

		System.out.println("Nodes number: " + nodes.size());

		Assert.assertEquals(""+nodes.size()+"".trim(),"9600".trim());


			DataSetHashCacheTO dataSetHashCacheTO = null;
			if(queryModel!=null && !queryModel.isEmpty()){
				Map<String, List<String>> values = new Util().getValues(queryModel,query);
				
				Map<String, String> result = new TreeMap<String,String>();
				for (Entry<String, List<String>> entry : values.entrySet()) {
					result.put(entry.getKey().trim(),entry.getValue().toString().trim());				
				}
				dataSetHashCacheTO = dataSetHashCacheDAO.insert("workspaceTestUnit","projectTestUnit","simulationTestUnit","","",result);
				Assert.assertNotNull(dataSetHashCacheTO);
			}

		
		
	}
	
	@Test(groups= { "default" },priority=8,enabled=false)
	public void testPreparedQueryCacheGenerationFromVirtuosoToMongoDB() throws Exception{
		String projectName = "projectTest";
		String simulationName = "simulationUnitTest";
		
		JenaModel model = new JenaModel();

		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
				+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id  "
				+ "FROM NAMED <http://www.sifemontologies.com/ontologies/test>  "
				+ "WHERE {  "
				+ "GRAPH ?g {  "
				+ "?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .   "
				+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  "
				+ "} "
				+ " }";
		
		List<String> queryVariables = new ArrayList<String>();

		ResultSet results = Util.executeQuery(query, virtDataset);
		
		queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
		queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
		queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
		queryVariables.add("subDomain");queryVariables.add("id");

		 Map<String, List<RDFNode>> queryModel = model.queryModelWithResultService(results,queryVariables);	

		//List<RDFNode> materials = queryModel.get(queryVariables.get(0));
		//List<RDFNode> materialTypes = queryModel.get(queryVariables.get(1));		
		List<String> nodes = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(2)));
		List<String> xCoords = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(3)));
		//List<RDFNode> yCoords = queryModel.get(queryVariables.get(4));
		//List<RDFNode> zCoords = queryModel.get(queryVariables.get(5));
		List<String> translationXs = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(6)));
		//List<RDFNode> translationYs = queryModel.get(queryVariables.get(7));
		//List<RDFNode> translationZs = queryModel.get(queryVariables.get(8));
		List<String> subDomains = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(9)));
		List<String> ids = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(10)));

		System.out.println("Nodes number: " + nodes.size());

		//Assert.assertEquals(""+nodes.size()+"".trim(),"9600".trim());


//		List<String> nodes = Collections.EMPTY_LIST;
//		List<String> xCoords = Collections.EMPTY_LIST;
//		List<String> translationXs = Collections.EMPTY_LIST;
//		List<String> subDomains = Collections.EMPTY_LIST;
//		List<String> ids = Collections.EMPTY_LIST;
//		
		if(nodes.toString().contains("[")){
			String nodesStr = new Util().removeListKeysFromString(nodes.toString().replace(" ",""));
			nodes = Arrays.asList(nodesStr.split(","));
			
			String xCoordStr = new Util().removeListKeysFromString(xCoords.toString().replace(" ",""));
			xCoords = Arrays.asList(xCoordStr.split(","));
			
			String translationXStr = new Util().removeListKeysFromString(translationXs.toString().replace(" ",""));
			translationXs = Arrays.asList(translationXStr.split(","));
			
			String subDomainStr = new Util().removeListKeysFromString(subDomains.toString().replace(" ",""));
			subDomains = Arrays.asList(subDomainStr.split(","));
			
			String idsStr = new Util().removeListKeysFromString(ids.toString().replace(" ",""));
			ids = Arrays.asList(idsStr.split(","));
		}
		
		int size = subDomains.size();
		Map<String, Double> nodeXDisplacement = new HashMap<String, Double>();
		Map<Integer, String> idNodeMap = new HashMap<Integer, String>();
		Map<String, Double> nodeXCoord = new HashMap<String, Double>();
		System.out.println("size: "+size);
		for(int i=0; i<size; i++){		
			String displacementXStr = new Util().fixNumericValueForCacheList(translationXs.get(i));
			String currentXCoordStr = new Util().fixNumericValueForCacheList(xCoords.get(i));
			String idStr = new Util().fixNumericValueForCacheList(ids.get(i));
			if(displacementXStr==null || currentXCoordStr==null || idStr==null){
				continue;
			}
			Double displacementX =  new Double(displacementXStr);
			Double currentXCoord = new Double(currentXCoordStr); 
			String node = nodes.get(i);
			int nodeNumber = new Integer(idStr);
			nodeXDisplacement.put(node, displacementX);
			nodeXCoord.put(node, currentXCoord);
			idNodeMap.put(nodeNumber, node);
		}
		
		System.out.println("Final Nodes Remaining: " + idNodeMap.size());
		//Assert.assertEquals(""+idNodeMap.size()+"".trim(),"2020".trim());
		
		List<String> datax = new ArrayList<String>();
		List<String> datay = new ArrayList<String>();
		List<Integer> nodeSortedIds = new ArrayList<Integer>();
		for(int i : idNodeMap.keySet()) {
			nodeSortedIds.add(i);			
		}
		Collections.sort(nodeSortedIds);
		Double previousXCoord = new Double(Float.MIN_VALUE);		
		for(Integer id : nodeSortedIds){
			String node = idNodeMap.get(id);
			Double displacement = nodeXDisplacement.get(node);
			Double currentXCoord = nodeXCoord.get(node);
			if(currentXCoord > previousXCoord){
				datax.add(currentXCoord.toString());
				datay.add(displacement.toString());
				previousXCoord = currentXCoord;				
			}
		}
		
		ViewTO viewTO = new ViewTO();
		for (String value : datax) {
			viewTO.getxView().add(value);
		}
		for (String value : datay) {
			viewTO.getyView().add(value);
		}
		
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(
//				"classpath:SifemCore-applicationContext.xml");
//		MongoOperations mongoOperations = (MongoOperations) ctx
//				.getBean(MongoOperations.class);
//
//		if (!mongoOperations.collectionExists(DataSetHashCacheTO.class
//				.getSimpleName())) {
//			mongoOperations.createCollection(DataSetHashCacheTO.class
//					.getSimpleName());
//		}
		DataSetHashCacheTO greenwoodCache = new DataSetHashCacheTO();
		greenwoodCache.setProjectName(projectName.toUpperCase());
		greenwoodCache.setSimulationName(simulationName.toUpperCase());
		greenwoodCache.setxName("BasilarMembraneMagnitude".toUpperCase());
		greenwoodCache.setyName("DistanceFromTheCochleaBasis".toUpperCase());
		greenwoodCache.setHash(Util.generateHashCacheID(greenwoodCache));
		greenwoodCache.setViewTO(viewTO);
		dataSetHashCacheDAO.insert(greenwoodCache);
		//mongoOperations.insert(greenwoodCache,DataSetHashCacheTO.class.getSimpleName());
		
		
	}
	
	//#9
	//Test query on data cache 
	@Test(groups= { "default" },priority=9,enabled=false)
	public void testQueryOnDataCache() throws Exception{
		DataSetHashCacheTO dataSetHashCacheTO = dataSetHashCacheDAO.retrieveDataSetCacheBySelectedParameters("id","xcoord");


		List<String> nodes = Collections.EMPTY_LIST;
		List<String> xCoords = Collections.EMPTY_LIST;
		List<String> translationXs = Collections.EMPTY_LIST;
		List<String> subDomains = Collections.EMPTY_LIST;
		List<String> ids = Collections.EMPTY_LIST;
		if(dataSetHashCacheTO.getViewTO().getDimValMap().get("subDomain").toString().contains("[")){
			String nodesStr = new Util().removeListKeysFromString(dataSetHashCacheTO.getViewTO().getDimValMap().get("node").toString().replace(" ",""));
			nodes = Arrays.asList(nodesStr.split(","));
			
			String xCoordStr = new Util().removeListKeysFromString(dataSetHashCacheTO.getViewTO().getDimValMap().get("xCoord").toString().replace(" ",""));
			xCoords = Arrays.asList(xCoordStr.split(","));
			
			String translationXStr = new Util().removeListKeysFromString(dataSetHashCacheTO.getViewTO().getDimValMap().get("translationX").toString().replace(" ",""));
			translationXs = Arrays.asList(translationXStr.split(","));
			
			String subDomainStr = new Util().removeListKeysFromString(dataSetHashCacheTO.getViewTO().getDimValMap().get("subDomain").toString().replace(" ",""));
			subDomains = Arrays.asList(subDomainStr.split(","));
			
			String idsStr = new Util().removeListKeysFromString(dataSetHashCacheTO.getViewTO().getDimValMap().get("id").toString().replace(" ",""));
			ids = Arrays.asList(idsStr.split(","));
			
			
		}else{
			nodes = dataSetHashCacheTO.getViewTO().getDimValMap().get("node");
			xCoords = dataSetHashCacheTO.getViewTO().getDimValMap().get("xCoord");
			translationXs = dataSetHashCacheTO.getViewTO().getDimValMap().get("translationX");
			subDomains = dataSetHashCacheTO.getViewTO().getDimValMap().get("subDomain");
			ids = dataSetHashCacheTO.getViewTO().getDimValMap().get("id");
		}
		
		int size = subDomains.size();
		Map<String, Float> nodeXDisplacement = new HashMap<String, Float>();
		Map<Integer, String> idNodeMap = new HashMap<Integer, String>();
		Map<String, Float> nodeXCoord = new HashMap<String, Float>();
		System.out.println("size: "+size);
		for(int i=0; i<size; i++){		
			String displacementXStr = new Util().fixNumericValueForCacheList(translationXs.get(i));
			String currentXCoordStr = new Util().fixNumericValueForCacheList(xCoords.get(i));
			String idStr = new Util().fixNumericValueForCacheList(ids.get(i));
			if(displacementXStr==null || currentXCoordStr==null || idStr==null){
				continue;
			}
			Float displacementX =  new Float(displacementXStr);
			Float currentXCoord = new Float(currentXCoordStr); 
			String node = nodes.get(i);
			int nodeNumber = new Integer(idStr);
			nodeXDisplacement.put(node, displacementX);
			nodeXCoord.put(node, currentXCoord);
			idNodeMap.put(nodeNumber, node);
		}
		
		System.out.println("Final Nodes Remaining: " + idNodeMap.size());
		Assert.assertEquals(""+idNodeMap.size()+"".trim(),"2020".trim());
		
	}
	
	//#10
	//Test generate image from data cache 
	@Test(groups= { "default" },priority=10,enabled=false)
	public void testGenerateImageFromDataCache() throws Exception{
		DataSetHashCacheTO dataSetHashCacheTO = dataSetHashCacheDAO.retrieveDataSetCacheBySelectedParameters("id","xcoord");


		List<String> nodes = Collections.EMPTY_LIST;
		List<String> xCoords = Collections.EMPTY_LIST;
		List<String> translationXs = Collections.EMPTY_LIST;
		List<String> subDomains = Collections.EMPTY_LIST;
		List<String> ids = Collections.EMPTY_LIST;
		if(dataSetHashCacheTO.getViewTO().getDimValMap().get("subDomain").toString().contains("[")){
			String nodesStr = new Util().removeListKeysFromString(dataSetHashCacheTO.getViewTO().getDimValMap().get("node").toString().replace(" ",""));
			nodes = Arrays.asList(nodesStr.split(","));
			
			String xCoordStr = new Util().removeListKeysFromString(dataSetHashCacheTO.getViewTO().getDimValMap().get("xCoord").toString().replace(" ",""));
			xCoords = Arrays.asList(xCoordStr.split(","));
			
			String translationXStr = new Util().removeListKeysFromString(dataSetHashCacheTO.getViewTO().getDimValMap().get("translationX").toString().replace(" ",""));
			translationXs = Arrays.asList(translationXStr.split(","));
			
			String subDomainStr = new Util().removeListKeysFromString(dataSetHashCacheTO.getViewTO().getDimValMap().get("subDomain").toString().replace(" ",""));
			subDomains = Arrays.asList(subDomainStr.split(","));
			
			String idsStr = new Util().removeListKeysFromString(dataSetHashCacheTO.getViewTO().getDimValMap().get("id").toString().replace(" ",""));
			ids = Arrays.asList(idsStr.split(","));
			
			
		}else{
			nodes = dataSetHashCacheTO.getViewTO().getDimValMap().get("node");
			xCoords = dataSetHashCacheTO.getViewTO().getDimValMap().get("xCoord");
			translationXs = dataSetHashCacheTO.getViewTO().getDimValMap().get("translationX");
			subDomains = dataSetHashCacheTO.getViewTO().getDimValMap().get("subDomain");
			ids = dataSetHashCacheTO.getViewTO().getDimValMap().get("id");
		}
		
		int size = subDomains.size();
		Map<String, Double> nodeXDisplacement = new HashMap<String, Double>();
		Map<Integer, String> idNodeMap = new HashMap<Integer, String>();
		Map<String, Double> nodeXCoord = new HashMap<String, Double>();
		System.out.println("size: "+size);
		for(int i=0; i<size; i++){		
			String displacementXStr = new Util().fixNumericValueForCacheList(translationXs.get(i));
			String currentXCoordStr = new Util().fixNumericValueForCacheList(xCoords.get(i));
			String idStr = new Util().fixNumericValueForCacheList(ids.get(i));
			if(displacementXStr==null || currentXCoordStr==null || idStr==null){
				continue;
			}
			Double displacementX =  new Double(displacementXStr);
			Double currentXCoord = new Double(currentXCoordStr); 
			String node = nodes.get(i);
			int nodeNumber = new Integer(idStr);
			nodeXDisplacement.put(node, displacementX);
			nodeXCoord.put(node, currentXCoord);
			idNodeMap.put(nodeNumber, node);
		}
		
		System.out.println("Final Nodes Remaining: " + idNodeMap.size());
		Assert.assertEquals(""+idNodeMap.size()+"".trim(),"2020".trim());
		
		List<String> datax = new ArrayList<String>();
		List<String> datay = new ArrayList<String>();
		List<Integer> nodeSortedIds = new ArrayList<Integer>();
		for(int i : idNodeMap.keySet()) {
			nodeSortedIds.add(i);			
		}
		Collections.sort(nodeSortedIds);
		Double previousXCoord = new Double(Float.MIN_VALUE);		
		for(Integer id : nodeSortedIds){
			String node = idNodeMap.get(id);
			Double displacement = nodeXDisplacement.get(node);
			Double currentXCoord = nodeXCoord.get(node);
			if(currentXCoord > previousXCoord){
				datax.add(currentXCoord.toString());
				datay.add(displacement.toString());
				previousXCoord = currentXCoord;				
			}
		}
		
		
		
		System.out.println(nodeSortedIds.size());
		System.out.println(datax);
		System.out.println(datax.size());		
		System.out.println(datay);
		System.out.println(datay.size());	
		
		File file = new File("src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/img/");
		file.mkdir();
		
		FeatureExtractor f = new FeatureExtractor();
		f.plot2dGFeatService(datax, "NodesInIncreasingOrder", datay, "XDisplacement", "DisplacementGraph",file.getCanonicalPath()+"/");
		//System.exit(0);

		
	}
	
	//#10
	//Test generate image from data cache 
	@Test(groups= { "default" },priority=10,enabled=false)
	public void testGenerateImageFromPreparedDataCache() throws Exception{
		DataSetHashCacheTO dataSetHashCacheTO = dataSetHashCacheDAO.retrieveDataSetCacheBySelectedParameters("BasilarMembraneMagnitude","DistanceFromTheCochleaBasis");


		Assert.assertNotNull(dataSetHashCacheTO);
		
		
		List<Double> datax = new Util().convertListStrToDoubleList(dataSetHashCacheTO.getViewTO().getxView());
		List<Double> datay = new Util().convertListStrToDoubleList(dataSetHashCacheTO.getViewTO().getyView());

		List<Double> xdata = new ArrayList<Double>(datax);
		Collections.sort(xdata);
		List<Double> dataxVals = new ArrayList<Double>();
		List<Double> datayVals = new ArrayList<Double>();

		for(Double xval : xdata) {
			int index = datax.indexOf(xval);			
			dataxVals.add(Math.log10(xval));
			datayVals.add(datay.get(index));
		}

		int i = 0;
		for(double x : dataxVals){
			double basillarDistance = dataxVals.get(i);
			double xDisplacement = datayVals.get(i);
			System.out.println(basillarDistance + " , " + xDisplacement);
			i++;
		}
		
		File file = new File("src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/img/");
		file.mkdir();
		

		FeatureExtractor f = new FeatureExtractor();
		f.plot2dGFeatService(dataxVals, "NodesInIncreasingOrder", datayVals, "XDisplacement", "Displacement",file.getCanonicalPath()+"/");

		
	}
	
	//================================= Generate Cache using frequency names for presentation purpose ======================

	//50
	@Test(groups= { "default" },priority=8,enabled=false)
	public void testPreparedQueryCacheGenerationFromVirtuosoToMongoDB_50() throws Exception{
		String projectName = "projectTest";
		String simulationName = "simulationUnitTest";
		System.out.println("STARTING 50");
		JenaModel model = new JenaModel();

		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
				+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id  "
				+ "FROM NAMED <http://www.sifemontologies.com/ontologies/displacement_50>  "
				+ "WHERE {  "
				+ "GRAPH ?g {  "
				+ "?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .   "
				+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  "
				+ "} "
				+ " }";
		
		List<String> queryVariables = new ArrayList<String>();

		ResultSet results = Util.executeQuery(query, virtDataset);
		
		queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
		queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
		queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
		queryVariables.add("subDomain");queryVariables.add("id");

		 Map<String, List<RDFNode>> queryModel = model.queryModelWithResultService(results,queryVariables);	

		//List<RDFNode> materials = queryModel.get(queryVariables.get(0));
		//List<RDFNode> materialTypes = queryModel.get(queryVariables.get(1));		
		List<String> nodes = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(2)));
		List<String> xCoords = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(3)));
		//List<RDFNode> yCoords = queryModel.get(queryVariables.get(4));
		//List<RDFNode> zCoords = queryModel.get(queryVariables.get(5));
		List<String> translationXs = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(6)));
		//List<RDFNode> translationYs = queryModel.get(queryVariables.get(7));
		//List<RDFNode> translationZs = queryModel.get(queryVariables.get(8));
		List<String> subDomains = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(9)));
		List<String> ids = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(10)));

		System.out.println("Nodes number: " + nodes.size());

		//Assert.assertEquals(""+nodes.size()+"".trim(),"9600".trim());


//		List<String> nodes = Collections.EMPTY_LIST;
//		List<String> xCoords = Collections.EMPTY_LIST;
//		List<String> translationXs = Collections.EMPTY_LIST;
//		List<String> subDomains = Collections.EMPTY_LIST;
//		List<String> ids = Collections.EMPTY_LIST;
//		
		if(nodes.toString().contains("[")){
			String nodesStr = new Util().removeListKeysFromString(nodes.toString().replace(" ",""));
			nodes = Arrays.asList(nodesStr.split(","));
			
			String xCoordStr = new Util().removeListKeysFromString(xCoords.toString().replace(" ",""));
			xCoords = Arrays.asList(xCoordStr.split(","));
			
			String translationXStr = new Util().removeListKeysFromString(translationXs.toString().replace(" ",""));
			translationXs = Arrays.asList(translationXStr.split(","));
			
			String subDomainStr = new Util().removeListKeysFromString(subDomains.toString().replace(" ",""));
			subDomains = Arrays.asList(subDomainStr.split(","));
			
			String idsStr = new Util().removeListKeysFromString(ids.toString().replace(" ",""));
			ids = Arrays.asList(idsStr.split(","));
		}
		
		int size = subDomains.size();
		Map<String, Double> nodeXDisplacement = new HashMap<String, Double>();
		Map<Integer, String> idNodeMap = new HashMap<Integer, String>();
		Map<String, Double> nodeXCoord = new HashMap<String, Double>();
		System.out.println("size: "+size);
		for(int i=0; i<size; i++){		
			String displacementXStr = new Util().fixNumericValueForCacheList(translationXs.get(i));
			String currentXCoordStr = new Util().fixNumericValueForCacheList(xCoords.get(i));
			String idStr = new Util().fixNumericValueForCacheList(ids.get(i));
			if(displacementXStr==null || currentXCoordStr==null || idStr==null){
				continue;
			}
			Double displacementX =  new Double(displacementXStr);
			Double currentXCoord = new Double(currentXCoordStr); 
			String node = nodes.get(i);
			int nodeNumber = new Integer(idStr);
			nodeXDisplacement.put(node, displacementX);
			nodeXCoord.put(node, currentXCoord);
			idNodeMap.put(nodeNumber, node);
		}
		
		System.out.println("Final Nodes Remaining: " + idNodeMap.size());
		//Assert.assertEquals(""+idNodeMap.size()+"".trim(),"2020".trim());
		
		List<String> datax = new ArrayList<String>();
		List<String> datay = new ArrayList<String>();
		List<Integer> nodeSortedIds = new ArrayList<Integer>();
		for(int i : idNodeMap.keySet()) {
			nodeSortedIds.add(i);			
		}
		Collections.sort(nodeSortedIds);
		Double previousXCoord = new Double(Float.MIN_VALUE);		
		for(Integer id : nodeSortedIds){
			String node = idNodeMap.get(id);
			Double displacement = nodeXDisplacement.get(node);
			Double currentXCoord = nodeXCoord.get(node);
			if(currentXCoord > previousXCoord){
				datax.add(currentXCoord.toString());
				datay.add(displacement.toString());
				previousXCoord = currentXCoord;				
			}
		}
		
		ViewTO viewTO = new ViewTO();
		for (String value : datax) {
			viewTO.getxView().add(value);
		}
		for (String value : datay) {
			viewTO.getyView().add(value);
		}
		
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(
//				"classpath:SifemCore-applicationContext.xml");
//		MongoOperations mongoOperations = (MongoOperations) ctx
//				.getBean(MongoOperations.class);
//
//		if (!mongoOperations.collectionExists(DataSetHashCacheTO.class
//				.getSimpleName())) {
//			mongoOperations.createCollection(DataSetHashCacheTO.class
//					.getSimpleName());
//		}
		DataSetHashCacheTO greenwoodCache = new DataSetHashCacheTO();
		greenwoodCache.setProjectName(projectName.toUpperCase());
		greenwoodCache.setSimulationName(simulationName.toUpperCase());
		greenwoodCache.setxName("BasilarMembraneMagnitude".toUpperCase());
		greenwoodCache.setyName("DistanceFromTheCochleaBasis".toUpperCase());
		greenwoodCache.setHash(Util.generateHashCacheID(greenwoodCache));
		greenwoodCache.setViewTO(viewTO);
		greenwoodCache.setFrequency("50");
		dataSetHashCacheDAO.insert(greenwoodCache);
		//mongoOperations.insert(greenwoodCache,DataSetHashCacheTO.class.getSimpleName());
		System.out.println("DONE!");
		
	}

	
	//100
	@Test(groups= { "default" },priority=9,enabled=false)
	public void testPreparedQueryCacheGenerationFromVirtuosoToMongoDB_100() throws Exception{
		System.out.println("STARTING 100");
		String projectName = "projectTest";
		String simulationName = "simulationUnitTest";
		
		JenaModel model = new JenaModel();

		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
				+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id  "
				+ "FROM NAMED <http://www.sifemontologies.com/ontologies/displacement_100>  "
				+ "WHERE {  "
				+ "GRAPH ?g {  "
				+ "?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .   "
				+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  "
				+ "} "
				+ " }";
		
		List<String> queryVariables = new ArrayList<String>();

		ResultSet results = Util.executeQuery(query, virtDataset);
		
		queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
		queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
		queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
		queryVariables.add("subDomain");queryVariables.add("id");

		 Map<String, List<RDFNode>> queryModel = model.queryModelWithResultService(results,queryVariables);	

		//List<RDFNode> materials = queryModel.get(queryVariables.get(0));
		//List<RDFNode> materialTypes = queryModel.get(queryVariables.get(1));		
		List<String> nodes = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(2)));
		List<String> xCoords = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(3)));
		//List<RDFNode> yCoords = queryModel.get(queryVariables.get(4));
		//List<RDFNode> zCoords = queryModel.get(queryVariables.get(5));
		List<String> translationXs = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(6)));
		//List<RDFNode> translationYs = queryModel.get(queryVariables.get(7));
		//List<RDFNode> translationZs = queryModel.get(queryVariables.get(8));
		List<String> subDomains = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(9)));
		List<String> ids = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(10)));

		System.out.println("Nodes number: " + nodes.size());

		//Assert.assertEquals(""+nodes.size()+"".trim(),"9600".trim());


//		List<String> nodes = Collections.EMPTY_LIST;
//		List<String> xCoords = Collections.EMPTY_LIST;
//		List<String> translationXs = Collections.EMPTY_LIST;
//		List<String> subDomains = Collections.EMPTY_LIST;
//		List<String> ids = Collections.EMPTY_LIST;
//		
		if(nodes.toString().contains("[")){
			String nodesStr = new Util().removeListKeysFromString(nodes.toString().replace(" ",""));
			nodes = Arrays.asList(nodesStr.split(","));
			
			String xCoordStr = new Util().removeListKeysFromString(xCoords.toString().replace(" ",""));
			xCoords = Arrays.asList(xCoordStr.split(","));
			
			String translationXStr = new Util().removeListKeysFromString(translationXs.toString().replace(" ",""));
			translationXs = Arrays.asList(translationXStr.split(","));
			
			String subDomainStr = new Util().removeListKeysFromString(subDomains.toString().replace(" ",""));
			subDomains = Arrays.asList(subDomainStr.split(","));
			
			String idsStr = new Util().removeListKeysFromString(ids.toString().replace(" ",""));
			ids = Arrays.asList(idsStr.split(","));
		}
		
		int size = subDomains.size();
		Map<String, Double> nodeXDisplacement = new HashMap<String, Double>();
		Map<Integer, String> idNodeMap = new HashMap<Integer, String>();
		Map<String, Double> nodeXCoord = new HashMap<String, Double>();
		System.out.println("size: "+size);
		for(int i=0; i<size; i++){		
			String displacementXStr = new Util().fixNumericValueForCacheList(translationXs.get(i));
			String currentXCoordStr = new Util().fixNumericValueForCacheList(xCoords.get(i));
			String idStr = new Util().fixNumericValueForCacheList(ids.get(i));
			if(displacementXStr==null || currentXCoordStr==null || idStr==null){
				continue;
			}
			Double displacementX =  new Double(displacementXStr);
			Double currentXCoord = new Double(currentXCoordStr); 
			String node = nodes.get(i);
			int nodeNumber = new Integer(idStr);
			nodeXDisplacement.put(node, displacementX);
			nodeXCoord.put(node, currentXCoord);
			idNodeMap.put(nodeNumber, node);
		}
		
		System.out.println("Final Nodes Remaining: " + idNodeMap.size());
		//Assert.assertEquals(""+idNodeMap.size()+"".trim(),"2020".trim());
		
		List<String> datax = new ArrayList<String>();
		List<String> datay = new ArrayList<String>();
		List<Integer> nodeSortedIds = new ArrayList<Integer>();
		for(int i : idNodeMap.keySet()) {
			nodeSortedIds.add(i);			
		}
		Collections.sort(nodeSortedIds);
		Double previousXCoord = new Double(Float.MIN_VALUE);		
		for(Integer id : nodeSortedIds){
			String node = idNodeMap.get(id);
			Double displacement = nodeXDisplacement.get(node);
			Double currentXCoord = nodeXCoord.get(node);
			if(currentXCoord > previousXCoord){
				datax.add(currentXCoord.toString());
				datay.add(displacement.toString());
				previousXCoord = currentXCoord;				
			}
		}
		
		ViewTO viewTO = new ViewTO();
		for (String value : datax) {
			viewTO.getxView().add(value);
		}
		for (String value : datay) {
			viewTO.getyView().add(value);
		}
		
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(
//				"classpath:SifemCore-applicationContext.xml");
//		MongoOperations mongoOperations = (MongoOperations) ctx
//				.getBean(MongoOperations.class);
//
//		if (!mongoOperations.collectionExists(DataSetHashCacheTO.class
//				.getSimpleName())) {
//			mongoOperations.createCollection(DataSetHashCacheTO.class
//					.getSimpleName());
//		}
		DataSetHashCacheTO greenwoodCache = new DataSetHashCacheTO();
		greenwoodCache.setProjectName(projectName.toUpperCase());
		greenwoodCache.setSimulationName(simulationName.toUpperCase());
		greenwoodCache.setxName("BasilarMembraneMagnitude".toUpperCase());
		greenwoodCache.setyName("DistanceFromTheCochleaBasis".toUpperCase());
		greenwoodCache.setHash(Util.generateHashCacheID(greenwoodCache));
		greenwoodCache.setViewTO(viewTO);
		greenwoodCache.setFrequency("100");
		dataSetHashCacheDAO.insert(greenwoodCache);
		//mongoOperations.insert(greenwoodCache,DataSetHashCacheTO.class.getSimpleName());
		
		
	}


	//1000
	@Test(groups= { "default" },priority=10,enabled=false)
	public void testPreparedQueryCacheGenerationFromVirtuosoToMongoDB_1000() throws Exception{
		
		System.out.println("STARTING 1000");
		String projectName = "projectTest";
		String simulationName = "simulationUnitTest";
		
		JenaModel model = new JenaModel();

		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
				+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id  "
				+ "FROM NAMED <http://www.sifemontologies.com/ontologies/displacement_1000>  "
				+ "WHERE {  "
				+ "GRAPH ?g {  "
				+ "?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .   "
				+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  "
				+ "} "
				+ " }";
		
		List<String> queryVariables = new ArrayList<String>();

		ResultSet results = Util.executeQuery(query, virtDataset);
		
		queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
		queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
		queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
		queryVariables.add("subDomain");queryVariables.add("id");

		 Map<String, List<RDFNode>> queryModel = model.queryModelWithResultService(results,queryVariables);	

		//List<RDFNode> materials = queryModel.get(queryVariables.get(0));
		//List<RDFNode> materialTypes = queryModel.get(queryVariables.get(1));		
		List<String> nodes = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(2)));
		List<String> xCoords = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(3)));
		//List<RDFNode> yCoords = queryModel.get(queryVariables.get(4));
		//List<RDFNode> zCoords = queryModel.get(queryVariables.get(5));
		List<String> translationXs = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(6)));
		//List<RDFNode> translationYs = queryModel.get(queryVariables.get(7));
		//List<RDFNode> translationZs = queryModel.get(queryVariables.get(8));
		List<String> subDomains = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(9)));
		List<String> ids = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(10)));

		System.out.println("Nodes number: " + nodes.size());

		//Assert.assertEquals(""+nodes.size()+"".trim(),"9600".trim());


//		List<String> nodes = Collections.EMPTY_LIST;
//		List<String> xCoords = Collections.EMPTY_LIST;
//		List<String> translationXs = Collections.EMPTY_LIST;
//		List<String> subDomains = Collections.EMPTY_LIST;
//		List<String> ids = Collections.EMPTY_LIST;
//		
		if(nodes.toString().contains("[")){
			String nodesStr = new Util().removeListKeysFromString(nodes.toString().replace(" ",""));
			nodes = Arrays.asList(nodesStr.split(","));
			
			String xCoordStr = new Util().removeListKeysFromString(xCoords.toString().replace(" ",""));
			xCoords = Arrays.asList(xCoordStr.split(","));
			
			String translationXStr = new Util().removeListKeysFromString(translationXs.toString().replace(" ",""));
			translationXs = Arrays.asList(translationXStr.split(","));
			
			String subDomainStr = new Util().removeListKeysFromString(subDomains.toString().replace(" ",""));
			subDomains = Arrays.asList(subDomainStr.split(","));
			
			String idsStr = new Util().removeListKeysFromString(ids.toString().replace(" ",""));
			ids = Arrays.asList(idsStr.split(","));
		}
		
		int size = subDomains.size();
		Map<String, Double> nodeXDisplacement = new HashMap<String, Double>();
		Map<Integer, String> idNodeMap = new HashMap<Integer, String>();
		Map<String, Double> nodeXCoord = new HashMap<String, Double>();
		System.out.println("size: "+size);
		for(int i=0; i<size; i++){		
			String displacementXStr = new Util().fixNumericValueForCacheList(translationXs.get(i));
			String currentXCoordStr = new Util().fixNumericValueForCacheList(xCoords.get(i));
			String idStr = new Util().fixNumericValueForCacheList(ids.get(i));
			if(displacementXStr==null || currentXCoordStr==null || idStr==null){
				continue;
			}
			Double displacementX =  new Double(displacementXStr);
			Double currentXCoord = new Double(currentXCoordStr); 
			String node = nodes.get(i);
			int nodeNumber = new Integer(idStr);
			nodeXDisplacement.put(node, displacementX);
			nodeXCoord.put(node, currentXCoord);
			idNodeMap.put(nodeNumber, node);
		}
		
		System.out.println("Final Nodes Remaining: " + idNodeMap.size());
		//Assert.assertEquals(""+idNodeMap.size()+"".trim(),"2020".trim());
		
		List<String> datax = new ArrayList<String>();
		List<String> datay = new ArrayList<String>();
		List<Integer> nodeSortedIds = new ArrayList<Integer>();
		for(int i : idNodeMap.keySet()) {
			nodeSortedIds.add(i);			
		}
		Collections.sort(nodeSortedIds);
		Double previousXCoord = new Double(Float.MIN_VALUE);		
		for(Integer id : nodeSortedIds){
			String node = idNodeMap.get(id);
			Double displacement = nodeXDisplacement.get(node);
			Double currentXCoord = nodeXCoord.get(node);
			if(currentXCoord > previousXCoord){
				datax.add(currentXCoord.toString());
				datay.add(displacement.toString());
				previousXCoord = currentXCoord;				
			}
		}
		
		ViewTO viewTO = new ViewTO();
		for (String value : datax) {
			viewTO.getxView().add(value);
		}
		for (String value : datay) {
			viewTO.getyView().add(value);
		}
		
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(
//				"classpath:SifemCore-applicationContext.xml");
//		MongoOperations mongoOperations = (MongoOperations) ctx
//				.getBean(MongoOperations.class);
//
//		if (!mongoOperations.collectionExists(DataSetHashCacheTO.class
//				.getSimpleName())) {
//			mongoOperations.createCollection(DataSetHashCacheTO.class
//					.getSimpleName());
//		}
		DataSetHashCacheTO greenwoodCache = new DataSetHashCacheTO();
		greenwoodCache.setProjectName(projectName.toUpperCase());
		greenwoodCache.setSimulationName(simulationName.toUpperCase());
		greenwoodCache.setxName("BasilarMembraneMagnitude".toUpperCase());
		greenwoodCache.setyName("DistanceFromTheCochleaBasis".toUpperCase());
		greenwoodCache.setHash(Util.generateHashCacheID(greenwoodCache));
		greenwoodCache.setViewTO(viewTO);
		greenwoodCache.setFrequency("1000");
		dataSetHashCacheDAO.insert(greenwoodCache);
		//mongoOperations.insert(greenwoodCache,DataSetHashCacheTO.class.getSimpleName());
		System.out.println("ALL DONE!");
		
	}

	
	//================================= Generate 3D view Cache using frequency names for presentation purpose ======================

	//50
	@Test(groups= { "default" },priority=8,enabled=true)
	public void testPreparedQueryCacheGeneration3DFromVirtuosoToMongoDB_50() throws Exception{
		String projectName = "projectTest";
		String simulationName = "simulationUnitTest";
		System.out.println("STARTING 50");
		JenaModel model = new JenaModel();

		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
				+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id  "
				+ "FROM NAMED <http://www.sifemontologies.com/ontologies/displacement_50>  "
				+ "WHERE {  "
				+ "GRAPH ?g {  "
				+ "?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .   "
				+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  "
				+ "} "
				+ " }";
		
		List<String> queryVariables = new ArrayList<String>();

		ResultSet results = Util.executeQuery(query, virtDataset);
		
		queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
		queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
		queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
		queryVariables.add("subDomain");queryVariables.add("id");

		 Map<String, List<RDFNode>> queryModel = model.queryModelWithResultService(results,queryVariables);	

		//List<RDFNode> materials = queryModel.get(queryVariables.get(0));
		//List<RDFNode> materialTypes = queryModel.get(queryVariables.get(1));		
		List<String> nodes = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(2)));
		List<String> xCoords = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(3)));
		//List<RDFNode> yCoords = queryModel.get(queryVariables.get(4));
		List<String> zCoords = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(5)));
		List<String> translationXs = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(6)));
		//List<RDFNode> translationYs = queryModel.get(queryVariables.get(7));
		//List<String> translationZs = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(8)));
		List<String> subDomains = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(9)));
		List<String> ids = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(10)));

		System.out.println("Nodes number: " + nodes.size());

		//Assert.assertEquals(""+nodes.size()+"".trim(),"9600".trim());


//		List<String> nodes = Collections.EMPTY_LIST;
//		List<String> xCoords = Collections.EMPTY_LIST;
//		List<String> translationXs = Collections.EMPTY_LIST;
//		List<String> subDomains = Collections.EMPTY_LIST;
//		List<String> ids = Collections.EMPTY_LIST;
//		
		if(nodes.toString().contains("[")){
			String nodesStr = new Util().removeListKeysFromString(nodes.toString().replace(" ",""));
			nodes = Arrays.asList(nodesStr.split(","));
			
			String xCoordStr = new Util().removeListKeysFromString(xCoords.toString().replace(" ",""));
			xCoords = Arrays.asList(xCoordStr.split(","));
			
			String translationXStr = new Util().removeListKeysFromString(translationXs.toString().replace(" ",""));
			translationXs = Arrays.asList(translationXStr.split(","));
			
			String zCoordsStr = new Util().removeListKeysFromString(zCoords.toString().replace(" ",""));
			zCoords = Arrays.asList(zCoordsStr.split(","));
			
			String subDomainStr = new Util().removeListKeysFromString(subDomains.toString().replace(" ",""));
			subDomains = Arrays.asList(subDomainStr.split(","));
			
			String idsStr = new Util().removeListKeysFromString(ids.toString().replace(" ",""));
			ids = Arrays.asList(idsStr.split(","));
		}
		
		int size = subDomains.size();
		Map<String, Double> nodeXDisplacement = new HashMap<String, Double>();
		Map<String, Double> nodeZCoord = new HashMap<String, Double>();
		Map<Integer, String> idNodeMap = new HashMap<Integer, String>();
		Map<String, Double> nodeXCoord = new HashMap<String, Double>();
		System.out.println("size: "+size);
		for(int i=0; i<size; i++){		
			String displacementXStr = new Util().fixNumericValueForCacheList(translationXs.get(i));
			String currentZCoordStr = new Util().fixNumericValueForCacheList(zCoords.get(i));
			String currentXCoordStr = new Util().fixNumericValueForCacheList(xCoords.get(i));
			String idStr = new Util().fixNumericValueForCacheList(ids.get(i));
			if(displacementXStr==null || currentXCoordStr==null || idStr==null || currentZCoordStr==null){
				continue;
			}
			Double displacementX =  new Double(displacementXStr);
			Double currentZCoord = new Double(currentZCoordStr);
			Double currentXCoord = new Double(currentXCoordStr); 
			String node = nodes.get(i);
			int nodeNumber = new Integer(idStr);
			nodeXDisplacement.put(node, displacementX);
			nodeZCoord.put(node, currentZCoord);
			nodeXCoord.put(node, currentXCoord);
			idNodeMap.put(nodeNumber, node);
		}
		
		System.out.println("Final Nodes Remaining: " + idNodeMap.size());
		//Assert.assertEquals(""+idNodeMap.size()+"".trim(),"2020".trim());
		
		List<String> datax = new ArrayList<String>();
		List<String> datay = new ArrayList<String>();
		List<String> dataz = new ArrayList<String>();
		List<Integer> nodeSortedIds = new ArrayList<Integer>();
		for(int i : idNodeMap.keySet()) {
			nodeSortedIds.add(i);			
		}
		Collections.sort(nodeSortedIds);
		Double previousXCoord = new Double(Float.MIN_VALUE);		
		for(Integer id : nodeSortedIds){
			String node = idNodeMap.get(id);
			Double displacement = nodeXDisplacement.get(node);
			Double currentXCoord = nodeXCoord.get(node);
			Double currentZCoord = nodeZCoord.get(node);
			if(currentXCoord > previousXCoord){
				datax.add(currentXCoord.toString());
				datay.add(displacement.toString());
				dataz.add(currentZCoord.toString());
				previousXCoord = currentXCoord;				
			}
		}
		
		ViewTO viewTO = new ViewTO();
		for (String value : datax) {
			viewTO.getxView().add(value);
		}
		for (String value : datay) {
			viewTO.getyView().add(value);
		}
		for (String value : dataz) {
			viewTO.getzView().add(value);
		}
				

		DataSetHashCacheTO greenwoodCache = new DataSetHashCacheTO();
		greenwoodCache.setProjectName(projectName.toUpperCase());
		greenwoodCache.setSimulationName(simulationName.toUpperCase());
		greenwoodCache.setxName("BasilarMembraneMagnitude".toUpperCase());
		greenwoodCache.setyName("DistanceFromTheCochleaBasis".toUpperCase());
		greenwoodCache.setyName("ZValueForDisplacement".toUpperCase());
		greenwoodCache.setHash(Util.generateHashCacheID(greenwoodCache));
		greenwoodCache.setViewTO(viewTO);
		greenwoodCache.setFrequency("50");
		dataSetHashCacheDAO.insert(greenwoodCache);
		System.out.println("DONE!");
		
	}

	
	//100
	@Test(groups= { "default" },priority=9,enabled=false)
	public void testPreparedQueryCacheGeneration3DFromVirtuosoToMongoDB_100() throws Exception{
		System.out.println("STARTING 100");
		String projectName = "projectTest";
		String simulationName = "simulationUnitTest";
		
		JenaModel model = new JenaModel();

		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
				+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id  "
				+ "FROM NAMED <http://www.sifemontologies.com/ontologies/displacement_100>  "
				+ "WHERE {  "
				+ "GRAPH ?g {  "
				+ "?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .   "
				+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  "
				+ "} "
				+ " }";
		
		List<String> queryVariables = new ArrayList<String>();

		ResultSet results = Util.executeQuery(query, virtDataset);
		
		queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
		queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
		queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
		queryVariables.add("subDomain");queryVariables.add("id");

		 Map<String, List<RDFNode>> queryModel = model.queryModelWithResultService(results,queryVariables);	

		//List<RDFNode> materials = queryModel.get(queryVariables.get(0));
		//List<RDFNode> materialTypes = queryModel.get(queryVariables.get(1));		
		List<String> nodes = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(2)));
		List<String> xCoords = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(3)));
		//List<RDFNode> yCoords = queryModel.get(queryVariables.get(4));
		//List<RDFNode> zCoords = queryModel.get(queryVariables.get(5));
		List<String> translationXs = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(6)));
		//List<RDFNode> translationYs = queryModel.get(queryVariables.get(7));
		//List<RDFNode> translationZs = queryModel.get(queryVariables.get(8));
		List<String> subDomains = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(9)));
		List<String> ids = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(10)));

		System.out.println("Nodes number: " + nodes.size());

		//Assert.assertEquals(""+nodes.size()+"".trim(),"9600".trim());


//		List<String> nodes = Collections.EMPTY_LIST;
//		List<String> xCoords = Collections.EMPTY_LIST;
//		List<String> translationXs = Collections.EMPTY_LIST;
//		List<String> subDomains = Collections.EMPTY_LIST;
//		List<String> ids = Collections.EMPTY_LIST;
//		
		if(nodes.toString().contains("[")){
			String nodesStr = new Util().removeListKeysFromString(nodes.toString().replace(" ",""));
			nodes = Arrays.asList(nodesStr.split(","));
			
			String xCoordStr = new Util().removeListKeysFromString(xCoords.toString().replace(" ",""));
			xCoords = Arrays.asList(xCoordStr.split(","));
			
			String translationXStr = new Util().removeListKeysFromString(translationXs.toString().replace(" ",""));
			translationXs = Arrays.asList(translationXStr.split(","));
			
			String subDomainStr = new Util().removeListKeysFromString(subDomains.toString().replace(" ",""));
			subDomains = Arrays.asList(subDomainStr.split(","));
			
			String idsStr = new Util().removeListKeysFromString(ids.toString().replace(" ",""));
			ids = Arrays.asList(idsStr.split(","));
		}
		
		int size = subDomains.size();
		Map<String, Double> nodeXDisplacement = new HashMap<String, Double>();
		Map<Integer, String> idNodeMap = new HashMap<Integer, String>();
		Map<String, Double> nodeXCoord = new HashMap<String, Double>();
		System.out.println("size: "+size);
		for(int i=0; i<size; i++){		
			String displacementXStr = new Util().fixNumericValueForCacheList(translationXs.get(i));
			String currentXCoordStr = new Util().fixNumericValueForCacheList(xCoords.get(i));
			String idStr = new Util().fixNumericValueForCacheList(ids.get(i));
			if(displacementXStr==null || currentXCoordStr==null || idStr==null){
				continue;
			}
			Double displacementX =  new Double(displacementXStr);
			Double currentXCoord = new Double(currentXCoordStr); 
			String node = nodes.get(i);
			int nodeNumber = new Integer(idStr);
			nodeXDisplacement.put(node, displacementX);
			nodeXCoord.put(node, currentXCoord);
			idNodeMap.put(nodeNumber, node);
		}
		
		System.out.println("Final Nodes Remaining: " + idNodeMap.size());
		//Assert.assertEquals(""+idNodeMap.size()+"".trim(),"2020".trim());
		
		List<String> datax = new ArrayList<String>();
		List<String> datay = new ArrayList<String>();
		List<Integer> nodeSortedIds = new ArrayList<Integer>();
		for(int i : idNodeMap.keySet()) {
			nodeSortedIds.add(i);			
		}
		Collections.sort(nodeSortedIds);
		Double previousXCoord = new Double(Float.MIN_VALUE);		
		for(Integer id : nodeSortedIds){
			String node = idNodeMap.get(id);
			Double displacement = nodeXDisplacement.get(node);
			Double currentXCoord = nodeXCoord.get(node);
			if(currentXCoord > previousXCoord){
				datax.add(currentXCoord.toString());
				datay.add(displacement.toString());
				previousXCoord = currentXCoord;				
			}
		}
		
		ViewTO viewTO = new ViewTO();
		for (String value : datax) {
			viewTO.getxView().add(value);
		}
		for (String value : datay) {
			viewTO.getyView().add(value);
		}
		
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(
//				"classpath:SifemCore-applicationContext.xml");
//		MongoOperations mongoOperations = (MongoOperations) ctx
//				.getBean(MongoOperations.class);
//
//		if (!mongoOperations.collectionExists(DataSetHashCacheTO.class
//				.getSimpleName())) {
//			mongoOperations.createCollection(DataSetHashCacheTO.class
//					.getSimpleName());
//		}
		DataSetHashCacheTO greenwoodCache = new DataSetHashCacheTO();
		greenwoodCache.setProjectName(projectName.toUpperCase());
		greenwoodCache.setSimulationName(simulationName.toUpperCase());
		greenwoodCache.setxName("BasilarMembraneMagnitude".toUpperCase());
		greenwoodCache.setyName("DistanceFromTheCochleaBasis".toUpperCase());
		greenwoodCache.setHash(Util.generateHashCacheID(greenwoodCache));
		greenwoodCache.setViewTO(viewTO);
		greenwoodCache.setFrequency("100");
		dataSetHashCacheDAO.insert(greenwoodCache);
		//mongoOperations.insert(greenwoodCache,DataSetHashCacheTO.class.getSimpleName());
		
		
	}


	//1000
	@Test(groups= { "default" },priority=10,enabled=false)
	public void testPreparedQueryCacheGeneration3DFromVirtuosoToMongoDB_1000() throws Exception{
		
		System.out.println("STARTING 1000");
		String projectName = "projectTest";
		String simulationName = "simulationUnitTest";
		
		JenaModel model = new JenaModel();

		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
				+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id  "
				+ "FROM NAMED <http://www.sifemontologies.com/ontologies/displacement_1000>  "
				+ "WHERE {  "
				+ "GRAPH ?g {  "
				+ "?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .   "
				+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  "
				+ "} "
				+ " }";
		
		List<String> queryVariables = new ArrayList<String>();

		ResultSet results = Util.executeQuery(query, virtDataset);
		
		queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
		queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
		queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
		queryVariables.add("subDomain");queryVariables.add("id");

		 Map<String, List<RDFNode>> queryModel = model.queryModelWithResultService(results,queryVariables);	

		//List<RDFNode> materials = queryModel.get(queryVariables.get(0));
		//List<RDFNode> materialTypes = queryModel.get(queryVariables.get(1));		
		List<String> nodes = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(2)));
		List<String> xCoords = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(3)));
		//List<RDFNode> yCoords = queryModel.get(queryVariables.get(4));
		//List<RDFNode> zCoords = queryModel.get(queryVariables.get(5));
		List<String> translationXs = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(6)));
		//List<RDFNode> translationYs = queryModel.get(queryVariables.get(7));
		//List<RDFNode> translationZs = queryModel.get(queryVariables.get(8));
		List<String> subDomains = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(9)));
		List<String> ids = new Util().changeFromRDFNodeToStringList(queryModel.get(queryVariables.get(10)));

		System.out.println("Nodes number: " + nodes.size());

		//Assert.assertEquals(""+nodes.size()+"".trim(),"9600".trim());


//		List<String> nodes = Collections.EMPTY_LIST;
//		List<String> xCoords = Collections.EMPTY_LIST;
//		List<String> translationXs = Collections.EMPTY_LIST;
//		List<String> subDomains = Collections.EMPTY_LIST;
//		List<String> ids = Collections.EMPTY_LIST;
//		
		if(nodes.toString().contains("[")){
			String nodesStr = new Util().removeListKeysFromString(nodes.toString().replace(" ",""));
			nodes = Arrays.asList(nodesStr.split(","));
			
			String xCoordStr = new Util().removeListKeysFromString(xCoords.toString().replace(" ",""));
			xCoords = Arrays.asList(xCoordStr.split(","));
			
			String translationXStr = new Util().removeListKeysFromString(translationXs.toString().replace(" ",""));
			translationXs = Arrays.asList(translationXStr.split(","));
			
			String subDomainStr = new Util().removeListKeysFromString(subDomains.toString().replace(" ",""));
			subDomains = Arrays.asList(subDomainStr.split(","));
			
			String idsStr = new Util().removeListKeysFromString(ids.toString().replace(" ",""));
			ids = Arrays.asList(idsStr.split(","));
		}
		
		int size = subDomains.size();
		Map<String, Double> nodeXDisplacement = new HashMap<String, Double>();
		Map<Integer, String> idNodeMap = new HashMap<Integer, String>();
		Map<String, Double> nodeXCoord = new HashMap<String, Double>();
		System.out.println("size: "+size);
		for(int i=0; i<size; i++){		
			String displacementXStr = new Util().fixNumericValueForCacheList(translationXs.get(i));
			String currentXCoordStr = new Util().fixNumericValueForCacheList(xCoords.get(i));
			String idStr = new Util().fixNumericValueForCacheList(ids.get(i));
			if(displacementXStr==null || currentXCoordStr==null || idStr==null){
				continue;
			}
			Double displacementX =  new Double(displacementXStr);
			Double currentXCoord = new Double(currentXCoordStr); 
			String node = nodes.get(i);
			int nodeNumber = new Integer(idStr);
			nodeXDisplacement.put(node, displacementX);
			nodeXCoord.put(node, currentXCoord);
			idNodeMap.put(nodeNumber, node);
		}
		
		System.out.println("Final Nodes Remaining: " + idNodeMap.size());
		//Assert.assertEquals(""+idNodeMap.size()+"".trim(),"2020".trim());
		
		List<String> datax = new ArrayList<String>();
		List<String> datay = new ArrayList<String>();
		List<Integer> nodeSortedIds = new ArrayList<Integer>();
		for(int i : idNodeMap.keySet()) {
			nodeSortedIds.add(i);			
		}
		Collections.sort(nodeSortedIds);
		Double previousXCoord = new Double(Float.MIN_VALUE);		
		for(Integer id : nodeSortedIds){
			String node = idNodeMap.get(id);
			Double displacement = nodeXDisplacement.get(node);
			Double currentXCoord = nodeXCoord.get(node);
			if(currentXCoord > previousXCoord){
				datax.add(currentXCoord.toString());
				datay.add(displacement.toString());
				previousXCoord = currentXCoord;				
			}
		}
		
		ViewTO viewTO = new ViewTO();
		for (String value : datax) {
			viewTO.getxView().add(value);
		}
		for (String value : datay) {
			viewTO.getyView().add(value);
		}
		
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(
//				"classpath:SifemCore-applicationContext.xml");
//		MongoOperations mongoOperations = (MongoOperations) ctx
//				.getBean(MongoOperations.class);
//
//		if (!mongoOperations.collectionExists(DataSetHashCacheTO.class
//				.getSimpleName())) {
//			mongoOperations.createCollection(DataSetHashCacheTO.class
//					.getSimpleName());
//		}
		DataSetHashCacheTO greenwoodCache = new DataSetHashCacheTO();
		greenwoodCache.setProjectName(projectName.toUpperCase());
		greenwoodCache.setSimulationName(simulationName.toUpperCase());
		greenwoodCache.setxName("BasilarMembraneMagnitude".toUpperCase());
		greenwoodCache.setyName("DistanceFromTheCochleaBasis".toUpperCase());
		greenwoodCache.setHash(Util.generateHashCacheID(greenwoodCache));
		greenwoodCache.setViewTO(viewTO);
		greenwoodCache.setFrequency("1000");
		dataSetHashCacheDAO.insert(greenwoodCache);
		//mongoOperations.insert(greenwoodCache,DataSetHashCacheTO.class.getSimpleName());
		System.out.println("ALL DONE!");
		
	}

	
	
}
