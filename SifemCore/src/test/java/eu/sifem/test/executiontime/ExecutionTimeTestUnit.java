package eu.sifem.test.executiontime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
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
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.resultset.ResultSetMem;

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
import eu.sifem.model.to.SessionIndexTO;
import eu.sifem.model.to.SimulationInstanceTO;
import eu.sifem.model.to.SolverConfigCreatorTO;
import eu.sifem.model.to.ViewTO;
import eu.sifem.service.IPakRDFMapperService;
import eu.sifem.service.IPakSolverControlerService;
import eu.sifem.service.ISolverConfigCreatorService;
import eu.sifem.service.dao.IDatAndUnvTTLDAOService;
import eu.sifem.service.dao.IDataSetHashCacheDAOService;
import eu.sifem.simulation.process.ProcessRunnableService;
import eu.sifem.utils.Util;


@ContextConfiguration(locations={"classpath:SifemCore-applicationContext_desenv.xml"})
public class ExecutionTimeTestUnit extends AbstractTestNGSpringContextTests{

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
	
	@Autowired
	private ProcessRunnableService processRunnableService;
	

	@Test(groups= { "default" },priority=0, enabled=false)
	public void testServiceIsNotNull() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Assert.assertNotNull(solverConfigCreator);
		Assert.assertNotNull(virtDataset);
		Assert.assertNotNull(dataSetHashCacheDAO);
	}
	
	//#1. The execution time to create input.cfg - OK
	@Test(groups= { "default" },priority=1,enabled=false)
	public void testSolverConfigCreatorChangeMESHDIVISIONS() throws Exception{
		
		long startTime = System.nanoTime();
				
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
		solverConfigCreatorTO.setProjectName(projectName);
					
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
		
		Assert.assertNotNull(cFGFileAsStringLines);
		Assert.assertNotSame(Collections.EMPTY_LIST,cFGFileAsStringLines);
		
		long endTime = System.nanoTime();
		long duration = ((endTime - startTime)/1000000);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		System.out.println(duration);
		System.out.println(seconds);
		/**RESULT**/
		//219 MILLISECONDS
		//0 SECONDS
		/***RESULT***/
	}
	
	//#4. The execution time of solver - OK
	@Test(groups= { "default" },priority=2,enabled=false)
	public void testSolverExecutionTime() throws Exception{
		long startTime = System.nanoTime();
		
		SimulationInstanceTO simulationInstanceTO = new SimulationInstanceTO();
		simulationInstanceTO.setInstanceName("instance_0");
		simulationInstanceTO.setDatFileList(Collections.EMPTY_LIST);
		simulationInstanceTO.setInstanceFiles(Collections.EMPTY_MAP);
		simulationInstanceTO.setProjectName("TheOne");
		String id = UUID.randomUUID().toString();
		simulationInstanceTO.setSimulationName("sim_060615_2125");
		
		SessionIndexTO sessionIndexTO = new SessionIndexTO();
		List<String> cfgSessionIdList = new ArrayList<String>();
		cfgSessionIdList.add(id);
		sessionIndexTO.setCfgSessionIdList(cfgSessionIdList);
		
		processRunnableService.callPakService(simulationInstanceTO, sessionIndexTO,null);
		
		long endTime = System.nanoTime();
		long duration = ((endTime - startTime)/1000000);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		System.out.println(duration);
		System.out.println(seconds);
		/**RESULT**/
		//74233 MILLISECONDS
		//74 SECONDS
		/***RESULT***/
	}
	
	private static final String OUTPUT_UNV = "src/test/resources/eu_sifem_test_executiontime/test.unv";
	private static final String OUTPUT_DAT = "src/test/resources/eu_sifem_test_executiontime/test.dat";
	@Autowired
	private IPakSolverControlerService pakSolverControlerService;
	
	@Autowired
	private IPakRDFMapperService pakRDFMapperService;
	
	@Autowired
	private IPakRDFMapperService pakRDFMapper;
	
	private SimulationInstanceTO simulationInstanceTO = new SimulationInstanceTO();
	//#3. The execution time to REDFize solver output - OK
	@Test(groups= { "default" },priority=3,enabled=false)
	public void RDFizeSolverOutputUNVTest() throws Exception{
		long startTime = System.nanoTime();
		
		Assert.assertNotNull(pakSolverControlerService);
		simulationInstanceTO.setProjectName("project");
		simulationInstanceTO.setSimulationName("simulationUnitTest");
		simulationInstanceTO.setInstanceName("instance_0");
		simulationInstanceTO.setWorkspacebasePath("src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/workspaceTest/");
		
		
//		File outputDat = new File(OUTPUT_DAT);
		File outputUnv = new File(OUTPUT_UNV);
//		InputStream datFile = pakRDFMapper.datToRDFService(simulationInstanceTO,new FileInputStream(outputDat));
//		Assert.assertNotNull(datFile);
		
		InputStream unvFile = pakRDFMapper.unvToRDFService(simulationInstanceTO,new FileInputStream(outputUnv));
		Assert.assertNotNull(unvFile);
		
		long endTime = System.nanoTime();
		long duration = ((endTime - startTime)/1000000);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		System.out.println(duration);
		System.out.println(seconds);
		/**RESULT**/
		//2050 MILLISECONDS
		//2 SECONDS
		/***RESULT***/
	}

	//#3. The execution time to REDFize solver output - OK
	@Test(groups= { "default" },priority=3,enabled=true)
	public void RDFizeSolverOutputDATTest() throws Exception{
		long startTime = System.nanoTime();
		
		Assert.assertNotNull(pakSolverControlerService);
		simulationInstanceTO.setProjectName("project");
		simulationInstanceTO.setSimulationName("simulationUnitTest");
		simulationInstanceTO.setInstanceName("instance_0");
		simulationInstanceTO.setWorkspacebasePath("src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/workspaceTest/");
		
		
		File outputDat = new File(OUTPUT_DAT);
//		File outputUnv = new File(OUTPUT_UNV);
		InputStream datFile = pakRDFMapper.datToRDFService(simulationInstanceTO,new FileInputStream(outputDat));
		Assert.assertNotNull(datFile);
		
//		InputStream unvFile = pakRDFMapper.unvToRDFService(simulationInstanceTO,new FileInputStream(outputUnv));
//		Assert.assertNotNull(unvFile);
		
		long endTime = System.nanoTime();
		long duration = ((endTime - startTime)/1000000);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		System.out.println(duration);
		System.out.println(seconds);
		/**RESULT**/
		//5308 MILLISECONDS
		//5 SECONDS
		/***RESULT***/
	}

	
	
	//#5. The execution time for data analysis - OK
	@Test(groups= { "default" },priority=4,enabled=false)
	public void timeForDataAnalysis() throws ClientProtocolException, IOException{
		long startTime = System.nanoTime();
		
        Request.Get("http://localhost:8080/Sifem/rest/semanticInterpretation/show?xName=xCoord&yName=yCoord").execute();
		
		long endTime = System.nanoTime();
		long duration = ((endTime - startTime)/1000000);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		System.out.println(duration);
		System.out.println(seconds);
		/**RESULT**/
		//1038 MILLISECONDS
		//1 SECONDS
		/***RESULT***/
	}
	
	
	   private static final String projectName = "TheOne";
	    private static final String simulationName = "sim1915_1037";
	    private static final String instanceName = "instance_0";
		
		@Autowired
		private IDatAndUnvTTLDAOService datAndUnvTTLDAO;
		//# 7. The time it takes to query Triple store as inputstream
		@Test(groups= { "default" },priority=5,enabled=false)
		public void testJenaModelCreationBasedOnDatAndUnvInputStream() throws Exception{
			long startTime = System.nanoTime();
			
			Assert.assertNotNull(datAndUnvTTLDAO);
			
			InputStream datIs = datAndUnvTTLDAO.findDatFileByProjectName(projectName,simulationName,instanceName);
			InputStream unvIs = datAndUnvTTLDAO.findUnvFileByProjectName(projectName,simulationName,instanceName);
			
			Assert.assertNotNull(datIs);
			Assert.assertNotNull(unvIs);
				
			JenaModel model = new JenaModel();
			model.importDataService(datIs);
			model.importDataService(unvIs);
			testQueryIntoTtlFiles(model.getOntModel());
		
			long endTime = System.nanoTime();
			long duration = ((endTime - startTime)/1000000);
			long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
			System.out.println(duration);
			System.out.println(seconds);
			/**RESULT**/
			//16603 MILLISECONDS
			//16 SECONDS
			/***RESULT***/
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

			QueryExecution qexec = QueryExecutionFactory.create(query, model);	
			ResultSet results = qexec.execSelect();
			ResultSetMem inMem = new ResultSetMem(results);
			Assert.assertNotNull(model);
			
		}
		
		//# 7. The time it takes to query Triple store as cache
		@Test(groups= { "default" },priority=8,enabled=false)
		public void testQueryOnDataCache() throws Exception{
			long startTime = System.nanoTime();
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
			
			long endTime = System.nanoTime();
			long duration = ((endTime - startTime)/1000000);
			long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
			System.out.println(duration);
			System.out.println(seconds);
			/**RESULT**/
			//509 MILLISECONDS
			//0 SECONDS
			/***RESULT***/
		}
		
		@Test(groups= { "default" },priority=9,enabled=false)
		public void testQueryIntoVirtuoso() throws Exception{
			long startTime = System.nanoTime();
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
			
			long endTime = System.nanoTime();
			long duration = ((endTime - startTime)/1000000);
			long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
			System.out.println(duration);
			System.out.println(seconds);
			/**RESULT**/
			//432817 MILLISECONDS
			//432 SECONDS
			/***RESULT***/

		}
		
}
