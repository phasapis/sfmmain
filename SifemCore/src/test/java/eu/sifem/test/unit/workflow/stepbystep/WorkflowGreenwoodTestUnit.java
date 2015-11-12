package eu.sifem.test.unit.workflow.stepbystep;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import virtuoso.jena.driver.VirtDataset;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

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
import eu.sifem.model.to.SolverMessageTO;
import eu.sifem.model.to.ViewTO;
import eu.sifem.service.IPakRDFMapperService;
import eu.sifem.service.IPakSolverControlerService;
import eu.sifem.service.ISolverConfigCreatorService;
import eu.sifem.service.dao.IDataSetHashCacheDAOService;
import eu.sifem.utils.CommandLineTools;
import eu.sifem.utils.Util;


@ContextConfiguration(locations={"classpath:SifemCore-applicationContext.xml"})
public class WorkflowGreenwoodTestUnit extends AbstractTestNGSpringContextTests{

	private static final String HTTP_WWW_SIFEMONTOLOGIES_COM_ONTOLOGIES_TEST = "http://www.sifemontologies.com/ontologies/test";
	private static final String FLUID = "FLUID";
	private static final String DAMPING = "DAMPING";
	private static final String YOUNG = "YOUNG";
	private static final String FREQUENCY = "FREQUENCY";
	
	private static final String FLUID_ID = "4";
	private static final String DAMPING_ID = "6";
	private static final String YOUNG_ID = "5";
	private static final String FREQUENCY_ID = "3";

	private static final String C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE = "src/test/resources/eu_sifem_test_unit_workflow_stepbystepGreenwood/WorkflowUnit/workspaceTest";
	private static final String PAK = "PAK";
	
	private Integer size;
	
	@Autowired
	private ISolverConfigCreatorService solverConfigCreator;
	

	@Autowired
	private VirtDataset virtDataset;
	
	@Autowired
	private IDataSetHashCacheDAOService dataSetHashCacheDAO;
	

	@Test(groups= { "default" },priority=0)
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

		
//		ParameterTO loadParametersTOFrequency = new ParameterTO();
//		loadParametersTOFrequency.setParameterUniqueValue("1000.0");
//		loadParametersTOFrequency.setName(FREQUENCY);
//		loadParametersTOFrequency.setId(FREQUENCY_ID);
//		loadParametersTOFrequency.setIsIncrementalGroup(Boolean.FALSE);
//		loadParametersTOList.add(loadParametersTOFrequency);
		
		ParameterTO loadParametersTOAreaFrequency = new ParameterTO();
		loadParametersTOAreaFrequency.setAreaValue("50.0;100.0;200.0;300.0;500.0;1000.0;2000.0,3000.0;5000.0;10000.0;20000.0");
		loadParametersTOAreaFrequency.setName(FREQUENCY);
		loadParametersTOAreaFrequency.setId(FREQUENCY_ID);
		loadParametersTOAreaFrequency.setIsIncrementalGroup(Boolean.TRUE);
		loadParametersTOList.add(loadParametersTOAreaFrequency);

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
		
		Assert.assertEquals(size.intValue(),10);
		

		List<String> cfgFile = solverConfigCreator.createCfgFilesAsStringService(solverConfigCreatorTO,mapAttributeValues,mapTeplateKeysAndIds);
		solverConfigCreator.saveOrUpdateCFGTO(solverConfigCreatorTO,cfgFile,projectName);
		
		List<ConfigFileTO> cfgTOList = solverConfigCreator.findConfigFiles(projectName,simulationName);
		Assert.assertNotNull(cfgTOList.get(0));
		
		//InputStream is = solverConfigCreator.findCFGFile(cfgTOList.get(0));
		List<String> cFGFileAsStringLines = FileUtils.readLines(new File(C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE+"/"+simulationName+"/instance_0/input.cfg"), "UTF-8");

		Assert.assertEquals(cFGFileAsStringLines.get(0).trim(), "#MESH DIVISIONS".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(1).trim(), "#(UINT)DIV_L (UINT)DIV_W (UINT)DIV_B (UINT)DIV_H (UINT)DIV_h".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(2).trim(), "100.0 4.0 4.0 4.0 3.0".trim());
		
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
		String projectName = "projectTest";
		String simulationName = "simulationUnitTest";
		PAKCRestServiceWrapperTO simulationInstance = new PAKCRestServiceWrapperTO();
		File instancesDirPath = new File(C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE+"/"+simulationName+"/");
		
		for(File file:instancesDirPath.listFiles()){
			
			if(StringUtils.startsWithIgnoreCase(file.getName(),"instance_")){
				String fileStr = file.getCanonicalPath()+"/input.cfg";
				System.out.println("====> "+ fileStr);
				simulationInstance.setCfgFile(new FileInputStream(new File(fileStr)));
				//String parameter = Util.getJsonStrFromObject(simulationInstance);
				
				File outputDat = new File(file.getCanonicalPath()+"/output/output.dat");
				File outputUnv = new File(file.getCanonicalPath()+"/output/output.unv");
				
				simulationInstance = simulateSolver(simulationInstance);
				//Gson gson = new GsonBuilder().create();
				//simulationInstance  = gson.fromJson(responseContentStr, PAKCRestServiceTO.class);
				InputStream datByteArr = simulationInstance.getDatFile();
				InputStream unvByteArr = simulationInstance.getUnvFile();
				Assert.assertNotNull(datByteArr);
				Assert.assertNotNull(unvByteArr);
				
				
				FileUtils.copyInputStreamToFile(datByteArr, outputDat);
				FileUtils.copyInputStreamToFile(unvByteArr, outputUnv);
			}
			
		}
		
		

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
		
		String projectName = "projectTest";
		String simulationName = "simulationUnitTest";
		
		simulationInstanceTO.setProjectName(projectName);
		simulationInstanceTO.setSimulationName(simulationName);
		simulationInstanceTO.setWorkspacebasePath("src/test/resources/eu_sifem_test_unit_workflow_stepbystepGreenwood/WorkflowUnit/workspaceTest/");
		
		File instancesDirPath = new File(C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE+"/"+simulationName+"/");
		
		for(File file:instancesDirPath.listFiles()){
			if(StringUtils.startsWithIgnoreCase(file.getName(),"instance_")){
				
				simulationInstanceTO.setInstanceName(file.getName());
				File outputDat = new File("src/test/resources/eu_sifem_test_unit_workflow_stepbystepGreenwood/"
						+ "WorkflowUnit/workspaceTest/simulationUnitTest/"+file.getName()+"/output/output.dat");
				File outputUnv = new File("src/test/resources/eu_sifem_test_unit_workflow_stepbystepGreenwood/"
						+ "WorkflowUnit/workspaceTest/simulationUnitTest/"+file.getName()+"/output/output.unv");
				InputStream datFile = pakRDFMapper.datToRDFService(simulationInstanceTO,new FileInputStream(outputDat));
				Assert.assertNotNull(datFile);
				
				InputStream unvFile = pakRDFMapper.unvToRDFService(simulationInstanceTO,new FileInputStream(outputUnv));
				Assert.assertNotNull(unvFile);
				
			}
			
		}
			
		

		
	}
	
	//#4
	//Insert .ttl data into virtuoso
	private static final String TTL = "*.ttl";
	private static final String NAMED_GRAPH = "NAMED_GRAPH";
	private static final String FILE_TYPE = "FILE_TYPE";
	private static final String SECURITY_FOLDER = "SECURITY_FOLDER";
	private static final String SECURITY_FOLDER_PATH = "C:/SifemWindowsResourceFiles/securityDumpFolder/";
	
	@Test(groups= { "default" },priority=4,enabled=false)
	public void testInsertTTlIntoVirtuoso() throws Exception{
	    ProcessBuilder pb = new ProcessBuilder("src/test/resources/eu_sifem_test_unit_workflow_stepbystepGreenwood/WorkflowUnit/workspaceTest/simulationUnitTest/batch/uploadTTLWindows.bat");
	    
		String projectName = "projectTest";
		String simulationName = "simulationUnitTest";
		
		simulationInstanceTO.setProjectName(projectName);
		simulationInstanceTO.setSimulationName(simulationName);
		simulationInstanceTO.setWorkspacebasePath("src/test/resources/eu_sifem_test_unit_workflow_stepbystepGreenwood/WorkflowUnit/workspaceTest/");
		
		File instancesDirPath = new File(C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE+"/"+simulationName+"/");
		
		for(File file:instancesDirPath.listFiles()){
			if(StringUtils.startsWithIgnoreCase(file.getName(),"instance_")){
				
				File outputDatTTL = new File("src/test/resources/eu_sifem_test_unit_workflow_stepbystepGreenwood/"
						+ "WorkflowUnit/workspaceTest/simulationUnitTest/"+file.getName()+"/ttl/dat_input.ttl");
				File outputUnvTTL = new File("src/test/resources/eu_sifem_test_unit_workflow_stepbystepGreenwood/"
						+ "WorkflowUnit/workspaceTest/simulationUnitTest/"+file.getName()+"/ttl/unv_output.ttl");
				
				FileUtils.copyFileToDirectory(outputDatTTL, new File(SECURITY_FOLDER_PATH));
				FileUtils.copyFileToDirectory(outputUnvTTL, new File(SECURITY_FOLDER_PATH));
				
				
				simulationInstanceTO.setInstanceName(file.getName());
				Map<String, String> env = pb.environment();
				env.put(SECURITY_FOLDER,SECURITY_FOLDER_PATH);
				env.put(FILE_TYPE, TTL);
				env.put(NAMED_GRAPH,HTTP_WWW_SIFEMONTOLOGIES_COM_ONTOLOGIES_TEST+"/"+simulationName+"/"+file.getName());
				pb.redirectOutput(new File("src/test/resources/eu_sifem_test_unit_workflow_stepbystepGreenwood/WorkflowUnit/workspaceTest/simulationUnitTest/batch/log.txt"));
				Process p = pb.start();
				p.waitFor(); 
				
			}
			
		}
	    
		
		
		
	}
	
	//#5
	//Test if namedgraph was properly inserted into virtuoso.
	@Test(groups= { "default" },priority=5,enabled=false)
	public void testQueryOnVirtuoso() throws Exception{
	
		String projectName = "projectTest";
		String simulationName = "simulationUnitTest";
		
		simulationInstanceTO.setProjectName(projectName);
		simulationInstanceTO.setSimulationName(simulationName);
		simulationInstanceTO.setWorkspacebasePath("src/test/resources/eu_sifem_test_unit_workflow_stepbystepGreenwood/WorkflowUnit/workspaceTest/");
		
		File instancesDirPath = new File(C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE+"/"+simulationName+"/");
		VirtGraph vg = new VirtGraph(virtDataset.getGraphUrl(),
				virtDataset.getGraphUser(), virtDataset.getGraphPassword());
		
		for(File file:instancesDirPath.listFiles()){
			if(StringUtils.startsWithIgnoreCase(file.getName(),"instance_")){
				String query = "ASK WHERE {GRAPH <"+HTTP_WWW_SIFEMONTOLOGIES_COM_ONTOLOGIES_TEST+"/"+simulationName+"/"+file.getName()+"> {?s ?p ?o.}}";
				VirtuosoQueryExecution vur = VirtuosoQueryExecutionFactory.create(query, vg);
				Boolean result = vur.execAsk();
				Assert.assertTrue(result);
			}
			
		}
	    
		
	}
	
	//#8
	//Test query cache generation 
	@Test(groups= { "default" },priority=6,enabled=false)
	public void testQueryIntoFromVirtuoso() throws Exception{
		JenaModel model = new JenaModel();
		String projectName = "projectTest";
		String simulationName = "simulationUnitTest";
		File instancesDirPath = new File(C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE+"/"+simulationName+"/");
		
		VirtGraph vg = new VirtGraph(virtDataset.getGraphUrl(),
				virtDataset.getGraphUser(), virtDataset.getGraphPassword());
		
		List<Double> datax = new ArrayList<Double>();
		List<Double> datay = new ArrayList<Double>();
		for(File file:instancesDirPath.listFiles()){
			if(StringUtils.startsWithIgnoreCase(file.getName(),"instance_")){
				String NAMED_GRAPH = "<"+HTTP_WWW_SIFEMONTOLOGIES_COM_ONTOLOGIES_TEST+"/"+simulationName+"/"+file.getName()+">";

				String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
						+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
						+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
						+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
						+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
						+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
						+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id ?excitationFreqVal  "
						+ "FROM NAMED "+NAMED_GRAPH+" "
						+ "WHERE {  "
						+ "GRAPH ?g { "
						+ "?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .  "
						+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  "
						+ "?loadResource rdf:type fem:Load. ?loadResource fem:holdsValueFor ?excitationFreq. ?excitationFreq rdf:type fem:Frequency.  "
						+ "?excitationFreq sim:hasScalarValue ?excitationFreqValObj. ?excitationFreqValObj sim:hasScalarDataValue ?excitationFreqVal.  "
						+ "}"+ 
						"}";
				List<String> queryVariables = new ArrayList<String>();
				queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
				queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
				queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
				queryVariables.add("subDomain");queryVariables.add("id");queryVariables.add("excitationFreqVal");

				ResultSet results = Util.executeQuery(query, vg);
				
				Map<String, List<RDFNode>> queryModel = model.queryModelWithResultService(results,queryVariables);	
				//Map<String, List<RDFNode>> queryModel = model.queryModelService(query, queryVariables);		
				List<RDFNode> xCoords = queryModel.get(queryVariables.get(3));
				List<RDFNode> translationXs = queryModel.get(queryVariables.get(6));
				List<RDFNode> ids = queryModel.get(queryVariables.get(10));
				List<RDFNode> frequencyVals = queryModel.get(queryVariables.get(11));
				double excitationFreq = frequencyVals.get(0).asLiteral().getDouble();
				double maxDisplacement = Double.MIN_VALUE;
				double xCoordWithMaxDisplacement = Double.MIN_VALUE;
				for(int i=0; i<ids.size(); i++){
					double displacementX =  translationXs.get(i).asLiteral().getDouble();
					if(displacementX < 0.0)
						displacementX = displacementX * -1;
					if(displacementX > maxDisplacement){
						maxDisplacement = displacementX;
						xCoordWithMaxDisplacement = xCoords.get(i).asLiteral().getDouble();
					}				
				}	
				datax.add(excitationFreq);				
				datay.add(xCoordWithMaxDisplacement);
				
			}
			
		}
		
		System.out.println(datax);
		System.out.println(datay);

			
	}
	
	@Test(groups= { "default" },priority=7,enabled=false)
	public void testQueryCacheGenerationFromVirtuosoToMongoDB() throws Exception{

		String projectName = "projectTest";
		String simulationName = "simulationUnitTest";
		
		//[50.0, 100.0, 200.0, 300.0, 500.0, 1000.0, 2000.0, 5000.0, 10000.0, 20000.0]
		//[0.03465, 0.0329, 0.0287, 0.0287, 0.0252, 0.0217, 0.0168, 0.01085, 0.0105, 0.00875]
		
		
		List<Double> datax = new ArrayList<Double>();
		List<Double> datay = new ArrayList<Double>();
		
		datax.addAll(Arrays.asList(new Double[]{50.0, 100.0, 200.0, 300.0, 500.0, 1000.0, 2000.0, 5000.0, 10000.0, 20000.0}));
		datay.addAll(Arrays.asList(new Double[]{0.03465, 0.0329, 0.0287, 0.0287, 0.0252, 0.0217, 0.0168, 0.01085, 0.0105, 0.00875}));
		
		System.out.println(datax);
		System.out.println(datay);
		
		ViewTO viewTO = new ViewTO();
		for (Double value : datax) {
			viewTO.getxView().add(value.toString());
		}
		for (Double value : datay) {
			viewTO.getyView().add(value.toString());
		}

		
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath:SifemCore-applicationContext.xml");
		MongoOperations mongoOperations = (MongoOperations) ctx
				.getBean(MongoOperations.class);

		if (!mongoOperations.collectionExists(DataSetHashCacheTO.class
				.getSimpleName())) {
			mongoOperations.createCollection(DataSetHashCacheTO.class
					.getSimpleName());
		}
		DataSetHashCacheTO greenwoodCache = new DataSetHashCacheTO();
		greenwoodCache.setProjectName(projectName.toUpperCase());
		greenwoodCache.setSimulationName(simulationName.toUpperCase());
		greenwoodCache.setxName("DistanceFromTheCochleaApex".toUpperCase());
		greenwoodCache.setyName("FrequencyAtStaples".toUpperCase());
		greenwoodCache.setHash(Util.generateHashCacheID(greenwoodCache));
		greenwoodCache.setViewTO(viewTO);
		mongoOperations.insert(greenwoodCache,DataSetHashCacheTO.class.getSimpleName());
		
	}
	

	
	
	
	//#10
	//Test generate image from data cache 
	@Test(groups= { "default" },priority=10,enabled=true)
	public void testGenerateImageFromDataCache() throws Exception{
		DataSetHashCacheTO dataSetHashCacheTO = dataSetHashCacheDAO.retrieveDataSetCacheBySelectedParameters("DistanceFromTheCochleaApex","FrequencyAtStaples");
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

		System.out.println("Frequency" + " , " + "PositionOfHairCell");
		int i = 0;
		for(double x : dataxVals){
			double basillarDistance = dataxVals.get(i);
			double xDisplacement = datayVals.get(i);
			System.out.println(basillarDistance + " , " + xDisplacement);
			i++;
		}
		
		
		File file = new File("src/test/resources/eu_sifem_test_unit_workflow_stepbystepGreenwood/WorkflowUnit/workspaceTest/simulationUnitTest/img/");
		file.mkdir();
		
		FeatureExtractor f = new FeatureExtractor();
		f.plot2dGFeatService(dataxVals, "DistanceFromTheCochleaApex", datayVals, "FrequencyAtStaples", "Greenwood",file.getCanonicalPath()+"/");
		System.exit(0);

		
	}
	
	
	public PAKCRestServiceWrapperTO simulateSolver(PAKCRestServiceWrapperTO pakcCRestServiceto) throws Exception {
		//Gson gson = new GsonBuilder().create();
//		SimulationInstanceTO simulationInstanceTO = gson.fromJson(simulationInstanceJSON, SimulationInstanceTO.class);
		//PAKCRestServiceTO simulationInstanceTO = gson.fromJson(PAKCRestServiceto, PAKCRestServiceTO.class);
		FileUtils.copyInputStreamToFile( pakcCRestServiceto.getCfgFile(),new File("C:/ProgramFilesDevel/PAK/input.cfg"));
		
		SolverMessageTO solverMonitor = new SolverMessageTO();
		
		File pakcPakDat = new File("C:/ProgramFilesDevel/PAK/PAKC/Pak.dat");
		File pakcPakUnv = new File("C:/ProgramFilesDevel/PAK/PAKC/Pak.unv");
		
		synchronized (solverMonitor) {
			CommandLineTools.runCommand("C:/ProgramFilesDevel/PAK/CAD.exe");
			solverMonitor.wait(2000);
			verifyIfNewDatAndUnvFilesWasCreated(solverMonitor,pakcPakDat,pakcPakUnv);
		}
		
		InputStream pakcPakDatByteArr = new FileInputStream(pakcPakDat);
		InputStream pakcPakUnvByteArr = new FileInputStream(pakcPakUnv);
		
		
		pakcCRestServiceto.setDatFile(pakcPakDatByteArr);
		pakcCRestServiceto.setUnvFile(pakcPakUnvByteArr);

		
		Assert.assertNotNull(pakcPakDatByteArr);
		Assert.assertNotNull(pakcPakUnvByteArr);
		
		
		return pakcCRestServiceto;
	}

	
	private void verifyIfNewDatAndUnvFilesWasCreated(SolverMessageTO solverMonitor, File pakcPakDat,File pakcPakUnv) throws InterruptedException, IOException {
		label: for(int i = 0; i<=100; i++){
			while(!pakcPakDat.exists()){
				Thread.currentThread().sleep(1000);
				if(i==99){
					throw new RuntimeException("The paf.unv, old version, can't be removed!");
				}
			}
			
			while(!pakcPakUnv.exists()){
				Thread.currentThread().sleep(1000);
				if(i==99){
					throw new RuntimeException("The paf.unv, old version, can't be removed!");
				}
			}
			if(pakcPakDat.exists() && pakcPakUnv.exists()){
				solverMonitor.notifyAll();
				break label;
			}

		}
		solverMonitor.notifyAll();
		
	}

	
}
