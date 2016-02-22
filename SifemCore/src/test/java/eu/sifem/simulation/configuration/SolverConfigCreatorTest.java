package eu.sifem.simulation.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import eu.sifem.model.enums.ParameterTypes;
import eu.sifem.model.helper.CombinationHelper;
import eu.sifem.model.to.ConfigFileTO;
import eu.sifem.model.to.ExternalLoadParametersTO;
import eu.sifem.model.to.GeometrySetupTO;
import eu.sifem.model.to.MeshSetupTO;
import eu.sifem.model.to.ParameterTO;
import eu.sifem.model.to.SolverConfigCreatorTO;
import eu.sifem.service.ISolverConfigCreatorService;



//Simulates the GUI "save" button, for multi CFG files generation.
//The output is one cfg file for each parameter values combination, 
//as we can see: at C:\SifemWindowsResourceFiles\workspace\simulationFrequencyByFileUploadValues
//With five tests scenarios. 
@ContextConfiguration(locations={"classpath:SifemCore-applicationContext.xml"})
public class SolverConfigCreatorTest extends AbstractTestNGSpringContextTests{
	
	
	private static final String FLUID = "FLUID";
	private static final String DAMPING = "DAMPING";
	private static final String YOUNG = "YOUNG";
	private static final String FREQUENCY = "FREQUENCY";
	
	private static final String FLUID_ID = "4";
	private static final String DAMPING_ID = "6";
	private static final String YOUNG_ID = "5";
	private static final String FREQUENCY_ID = "3";

	private static final String C_SIFEM_WINDOWS_RESOURCE_FILES_WORKSPACE = "src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/workspaceTest";
	private static final String PAK = "PAK";
	private static final String INPUT_CFG = "input.cfg";
	
	private Integer size;
	
	@Autowired
	private ISolverConfigCreatorService solverConfigCreator;
	

	@Test(groups= { "default" },priority=0)
	public void testServiceIsNotNull() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Assert.assertNotNull(solverConfigCreator);
	}
	
	@Test(groups= { "default" },priority=1,enabled=true)
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
		InputStream inDamping = new FileInputStream(new File("src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/csvFiles/damping.csv"));
		loadParametersDamping.setParameterFile(inDamping);
		loadParametersDamping.setName(DAMPING);
		loadParametersDamping.setId(DAMPING_ID);
		loadParametersDamping.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersDamping);
		
		ParameterTO loadParametersYoungsModulus = new ParameterTO();
		//src/test/resources/unv_output.ttl
		InputStream inYoungsModulus = new FileInputStream(new File("src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/csvFiles/youngs.csv"));
		loadParametersYoungsModulus.setParameterFile(inYoungsModulus);
		loadParametersYoungsModulus.setName(YOUNG);
		loadParametersYoungsModulus.setId(YOUNG_ID);
		loadParametersYoungsModulus.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersYoungsModulus);

		
		String projectName = "projectTest";
		String simulationName = "simulationTest_testSolverConfigCreatorChangeMESHDIVISIONS";
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
		
/**
 * 
1 #MESH DIVISIONS 
2 #(UINT)DIV_L (UINT)DIV_W (UINT)DIV_B (UINT)DIV_H (UINT)DIV_h 
3 100.0 4.0 4.0 4.0 3.0
 */

		Assert.assertEquals(cFGFileAsStringLines.get(1).trim(), "#MESH DIVISIONS".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(2).trim(), "#(UINT)DIV_L (UINT)DIV_W (UINT)DIV_B (UINT)DIV_H (UINT)DIV_h".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(3).trim(), "100.0 4.0 4.0 4.0 3.0".trim());
		
	}


	@Test(groups= { "default" },priority=2,enabled=true)
	public void testSolverConfigCreatorChangeGEOMETRYPARAMETERS() throws Exception{
				
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
		InputStream inDamping = new FileInputStream(new File("src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/csvFiles/damping.csv"));
		loadParametersDamping.setParameterFile(inDamping);
		loadParametersDamping.setName(DAMPING);
		loadParametersDamping.setId(DAMPING_ID);
		loadParametersDamping.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersDamping);
		
		ParameterTO loadParametersYoungsModulus = new ParameterTO();
		//src/test/resources/unv_output.ttl
		InputStream inYoungsModulus = new FileInputStream(new File("src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/csvFiles/youngs.csv"));
		loadParametersYoungsModulus.setParameterFile(inYoungsModulus);
		loadParametersYoungsModulus.setName(YOUNG);
		loadParametersYoungsModulus.setId(YOUNG_ID);
		loadParametersYoungsModulus.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersYoungsModulus);

		
		String projectName = "projectTest";
		String simulationName = "simulationTest_testSolverConfigCreatorChangeGEOMETRYPARAMETERS";
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
		
/**
 * 
#GEOMETRY PARAMETERS 
#(DOUBLE)LENGTH (DOUBLE)HEIGHT (DOUBLE)WIDTH (DOUBLE)WIDTH_OF_BM (DOUBLE)THICKNESS 
0.035 0.001 0.001 3.0E-4 5.0E-5
 */

		Assert.assertEquals(cFGFileAsStringLines.get(6).trim(), "#GEOMETRY PARAMETERS".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(7).trim(), "#(DOUBLE)LENGTH (DOUBLE)HEIGHT (DOUBLE)WIDTH (DOUBLE)WIDTH_OF_BM (DOUBLE)THICKNESS".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(8).trim(), "0.035 0.001 0.001 3.0E-4 5.0E-5".trim());
		
	}
	
	@Test(groups= { "default" },priority=3,enabled=true)
	public void testSolverConfigCreatorChangeLOADPARAMETERS() throws Exception{
				
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
		InputStream inDamping = new FileInputStream(new File("src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/csvFiles/damping.csv"));
		loadParametersDamping.setParameterFile(inDamping);
		loadParametersDamping.setName(DAMPING);
		loadParametersDamping.setId(DAMPING_ID);
		loadParametersDamping.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersDamping);
		
		ParameterTO loadParametersYoungsModulus = new ParameterTO();
		//src/test/resources/unv_output.ttl
		InputStream inYoungsModulus = new FileInputStream(new File("src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/csvFiles/youngs.csv"));
		loadParametersYoungsModulus.setParameterFile(inYoungsModulus);
		loadParametersYoungsModulus.setName(YOUNG);
		loadParametersYoungsModulus.setId(YOUNG_ID);
		loadParametersYoungsModulus.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersYoungsModulus);

		
		String projectName = "projectTest";
		String simulationName = "simulationTest_testSolverConfigCreatorChangeLOADPARAMETERS";
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
		
/**
 * 
##LOAD PARAMETERS 
#(DOUBLE)FREQUENCY (DOUBLE)VALUE 
1000.0  1.0
 */

		Assert.assertEquals(cFGFileAsStringLines.get(11).trim(), "##LOAD PARAMETERS ".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(12).trim(), "#(DOUBLE)FREQUENCY (DOUBLE)VALUE".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(13).trim(), "1000.0  1.0".trim());
		
	}

	
	@Test(groups= { "default" },priority=4,enabled=true)
	public void testSolverConfigCreatorChangeYOUNGMODULUSFUNCTION() throws Exception{
				
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
		InputStream inDamping = new FileInputStream(new File("src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/csvFiles/damping.csv"));
		loadParametersDamping.setParameterFile(inDamping);
		loadParametersDamping.setName(DAMPING);
		loadParametersDamping.setId(DAMPING_ID);
		loadParametersDamping.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersDamping);
		
		ParameterTO loadParametersYoungsModulus = new ParameterTO();
		//src/test/resources/unv_output.ttl
		InputStream inYoungsModulus = new FileInputStream(new File("src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/csvFiles/youngs.csv"));
		loadParametersYoungsModulus.setParameterFile(inYoungsModulus);
		loadParametersYoungsModulus.setName(YOUNG);
		loadParametersYoungsModulus.setId(YOUNG_ID);
		loadParametersYoungsModulus.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersYoungsModulus);

		
		String projectName = "projectTest";
		String simulationName = "simulationTest_testSolverConfigCreatorChangeYOUNGMODULUS";
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
		
/**
 * 
##YOUNG MODULUS FUNCTION 
#(DOUBLE)X_AXIS (DOUBLE)Y_AXIS 
 */

		Assert.assertEquals(cFGFileAsStringLines.get(21).trim(), "##YOUNG MODULUS FUNCTION".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(22).trim(), "#(DOUBLE)X_AXIS (DOUBLE)Y_AXIS".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(23).trim(), "0.000000  14101716.454877".trim());
		//Assert.assertEquals(cFGFileAsStringLines.get(80).trim(), "0.570000  47183.856149".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(123).trim(), "1.000000  640.216937".trim());
		
	}
	
	@Test(groups= { "default" },priority=5,enabled=true)
	public void testSolverConfigCreatorChangeDAMPINGFUNCTION() throws Exception{
				
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
		InputStream inDamping = new FileInputStream(new File("src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/csvFiles/damping.csv"));
		loadParametersDamping.setParameterFile(inDamping);
		loadParametersDamping.setName(DAMPING);
		loadParametersDamping.setId(DAMPING_ID);
		loadParametersDamping.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersDamping);
		
		ParameterTO loadParametersYoungsModulus = new ParameterTO();
		//src/test/resources/unv_output.ttl
		InputStream inYoungsModulus = new FileInputStream(new File("src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/csvFiles/youngs.csv"));
		loadParametersYoungsModulus.setParameterFile(inYoungsModulus);
		loadParametersYoungsModulus.setName(YOUNG);
		loadParametersYoungsModulus.setId(YOUNG_ID);
		loadParametersYoungsModulus.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersYoungsModulus);

		
		String projectName = "projectTest";
		String simulationName = "simulationTest_testSolverConfigCreatorChangeDAMPINGFUNCTION";
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
		
/**
 * 
#DAMPING FUNCTION 
#(DOUBLE)X_AXIS (DOUBLE)Y_AXIS 
0.000000  0.010000
 */

		Assert.assertEquals(cFGFileAsStringLines.get(125).trim(), "#DAMPING FUNCTION".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(126).trim(), "#(DOUBLE)X_AXIS (DOUBLE)Y_AXIS ".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(227).trim(), "1.000000  1.484132".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(123).trim(), "1.000000  640.216937".trim());
		
	}

	@Test(groups= { "default" },priority=6,enabled=true)
	public void testSolverConfigCreatorChangeMATERIALPROPERTIES() throws Exception{
				
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
		InputStream inDamping = new FileInputStream(new File("src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/csvFiles/damping.csv"));
		loadParametersDamping.setParameterFile(inDamping);
		loadParametersDamping.setName(DAMPING);
		loadParametersDamping.setId(DAMPING_ID);
		loadParametersDamping.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersDamping);
		
		ParameterTO loadParametersYoungsModulus = new ParameterTO();
		//src/test/resources/unv_output.ttl
		InputStream inYoungsModulus = new FileInputStream(new File("src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/csvFiles/youngs.csv"));
		loadParametersYoungsModulus.setParameterFile(inYoungsModulus);
		loadParametersYoungsModulus.setName(YOUNG);
		loadParametersYoungsModulus.setId(YOUNG_ID);
		loadParametersYoungsModulus.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersYoungsModulus);

		
		String projectName = "projectTest";
		String simulationName = "simulationTest_testSolverConfigCreatorChangeMATERIALPROPERTIES";
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
		
/**
 * 
#MATERIAL PROPERTIES 
#(DOUBLE)DENSITY_RHO (DOUBLE)SPEED_OF_SOUND_C 
1000.0  1500.0 
 */

		Assert.assertEquals(cFGFileAsStringLines.get(16).trim(), "#MATERIAL PROPERTIES".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(17).trim(), "#(DOUBLE)DENSITY_RHO (DOUBLE)SPEED_OF_SOUND_C".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(18).trim(), "1000.0  1500.0".trim());
		
	}
	
	@Test(groups= { "default" },priority=6,enabled=true)
	public void testSolverConfigCreatorChangeEXTERNALLOADPARAMETERS() throws Exception{
				
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
		InputStream inDamping = new FileInputStream(new File("src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/csvFiles/damping.csv"));
		loadParametersDamping.setParameterFile(inDamping);
		loadParametersDamping.setName(DAMPING);
		loadParametersDamping.setId(DAMPING_ID);
		loadParametersDamping.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersDamping);
		
		ParameterTO loadParametersYoungsModulus = new ParameterTO();
		//src/test/resources/unv_output.ttl
		InputStream inYoungsModulus = new FileInputStream(new File("src/test/resources/eu_sifem_simulation_configuration/solverConfigCreatorTest/csvFiles/youngs.csv"));
		loadParametersYoungsModulus.setParameterFile(inYoungsModulus);
		loadParametersYoungsModulus.setName(YOUNG);
		loadParametersYoungsModulus.setId(YOUNG_ID);
		loadParametersYoungsModulus.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersYoungsModulus);

		
		String projectName = "projectTest";
		String simulationName = "simulationTest_testSolverConfigCreatorChangeEXTERNALLOADPARAMETERS";
		solverConfigCreatorTO.setLoadParametersTOList(loadParametersTOList);
		solverConfigCreatorTO.setSimulationName(simulationName);
		
					
		//i) Create and configure ExternalLoadParametersTO, then set this object into SolverConfigCreatorTO;
		ExternalLoadParametersTO externalLoadParametersTO = new ExternalLoadParametersTO();
		externalLoadParametersTO.setDispX("1");
		externalLoadParametersTO.setDispY("2");
		externalLoadParametersTO.setDispZ("3");
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
		
/**
 * 
#EXTERNAL LOAD PARAMETERS 
#(DOUBLE)DISP_X (DOUBLE)DISP_Y (DOUBLE)DISP_Z 
1 2 3 
 */

		Assert.assertEquals(cFGFileAsStringLines.get(229).trim(), "#EXTERNAL LOAD PARAMETERS ".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(230).trim(), "#(DOUBLE)DISP_X (DOUBLE)DISP_Y (DOUBLE)DISP_Z ".trim());
		Assert.assertEquals(cFGFileAsStringLines.get(231).trim(), "1 2 3".trim());
		
	}
	
	@Test(groups= { "default" },priority=1,enabled=false)
	public void testSolverConfigCreator_simulationAllInOne_1() throws Exception{
				
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
		ParameterTO loadParametersTOArea = new ParameterTO();
		loadParametersTOArea.setAreaValue("100.0;200.0;300.0");
		loadParametersTOArea.setName(FREQUENCY);
		loadParametersTOArea.setId(FREQUENCY_ID);
		loadParametersTOArea.setIsIncrementalGroup(Boolean.TRUE);
		loadParametersTOList.add(loadParametersTOArea);

		ParameterTO loadParametersFluid = new ParameterTO();
		loadParametersFluid.setParameterUniqueValue("1000.0");
		loadParametersFluid.setName(FLUID);
		loadParametersFluid.setId(FLUID_ID);
		loadParametersFluid.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersFluid);
		
		ParameterTO loadParametersDamping = new ParameterTO();
		InputStream inDamping = new FileInputStream(new File("C:/SifemWindowsResourceFiles/DataAnalysis_config/damping.csv"));
		loadParametersDamping.setParameterFile(inDamping);
		loadParametersDamping.setName(DAMPING);
		loadParametersDamping.setId(DAMPING_ID);
		loadParametersDamping.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersDamping);
		
		ParameterTO loadParametersYoungsModulus = new ParameterTO();
		InputStream inYoungsModulus = new FileInputStream(new File("C:/SifemWindowsResourceFiles/DataAnalysis_config/youngs.csv"));
		loadParametersYoungsModulus.setParameterFile(inYoungsModulus);
		loadParametersYoungsModulus.setName(YOUNG);
		loadParametersYoungsModulus.setId(YOUNG_ID);
		loadParametersYoungsModulus.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersYoungsModulus);

		solverConfigCreatorTO.setLoadParametersTOList(loadParametersTOList);
		solverConfigCreatorTO.setSimulationName("projectOne");
		
					
		//i) Create and configure ExternalLoadParametersTO, then set this object into SolverConfigCreatorTO;
		ExternalLoadParametersTO externalLoadParametersTO = new ExternalLoadParametersTO();
		solverConfigCreatorTO.setExternalLoadParametersTO(externalLoadParametersTO);
		
		Map<ParameterTypes,ParameterTO>  mapTeplateKeysAndIds = new TreeMap<ParameterTypes,ParameterTO>();
		Map<String, List<String>> result = solverConfigCreator.getAttribMapValuesService(solverConfigCreatorTO,mapTeplateKeysAndIds);
		Map<String,CombinationHelper> combinationMap = new TreeMap<String,CombinationHelper>();
		
		size = solverConfigCreator.applyCombinationMapService(result,solverConfigCreatorTO.getLoadParametersTOList(),combinationMap);
		
		Assert.assertEquals(size.intValue(),3);

	}
	
	@Test(groups= { "default" },priority=2,enabled=false)
	public void testSolverConfigCreator_simulationAllInOne_2() throws Exception{
				
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
		ParameterTO loadParametersTOArea = new ParameterTO();
		loadParametersTOArea.setAreaValue("1000.0;2000.0;3000.0");
		loadParametersTOArea.setName(FREQUENCY);
		loadParametersTOArea.setId(FREQUENCY_ID);
		loadParametersTOArea.setIsIncrementalGroup(Boolean.TRUE);
		loadParametersTOList.add(loadParametersTOArea);

		ParameterTO loadParametersFluid = new ParameterTO();
		loadParametersFluid.setParameterUniqueValue("1000.0");
		loadParametersFluid.setName(FLUID);
		loadParametersFluid.setId(FLUID_ID);
		loadParametersFluid.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersFluid);
		
		ParameterTO loadParametersDamping = new ParameterTO();
		InputStream inDamping = new FileInputStream(new File("C:/SifemWindowsResourceFiles/DataAnalysis_config/damping.csv"));
		loadParametersDamping.setParameterFile(inDamping);
		loadParametersDamping.setName(DAMPING);
		loadParametersDamping.setId(DAMPING_ID);
		loadParametersDamping.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersDamping);
		
		ParameterTO loadParametersYoungsModulus = new ParameterTO();
		InputStream inYoungsModulus = new FileInputStream(new File("C:/SifemWindowsResourceFiles/DataAnalysis_config/youngs.csv"));
		loadParametersYoungsModulus.setParameterFile(inYoungsModulus);
		loadParametersYoungsModulus.setName(YOUNG);
		loadParametersYoungsModulus.setId(YOUNG_ID);
		loadParametersYoungsModulus.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersYoungsModulus);

		solverConfigCreatorTO.setLoadParametersTOList(loadParametersTOList);
		solverConfigCreatorTO.setSimulationName("projectOne");
		
					
		//i) Create and configure ExternalLoadParametersTO, then set this object into SolverConfigCreatorTO;
		ExternalLoadParametersTO externalLoadParametersTO = new ExternalLoadParametersTO();
		solverConfigCreatorTO.setExternalLoadParametersTO(externalLoadParametersTO);
			
		String location = "";

		//l) run the PAK execution service;
		solverConfigCreator.configFileCreationService(solverConfigCreatorTO,"SimulationName");
		
		for(int count=0;count<size;count++){
			location=solverConfigCreatorTO.getWorkspace()+"/"+solverConfigCreatorTO.getSimulationName()+"/simulationNumber_"+count+"/"+INPUT_CFG;
			System.out.println(location);
			File createdCFGFile = new File(location);
			Assert.assertTrue(createdCFGFile.exists());
			Assert.assertTrue(createdCFGFile.isFile());
			Assert.assertTrue(createdCFGFile.length()>1);
			 
		}
		
	}

	@Test(groups= { "default" },priority=3,enabled=false)
	public void testSolverConfigCreator_simulationAllInOne_3() throws Exception{
				
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
		ParameterTO loadParametersTOArea = new ParameterTO();
		loadParametersTOArea.setAreaValue("100;200;300");
		loadParametersTOArea.setName(FREQUENCY);
		loadParametersTOArea.setId(FREQUENCY_ID);
		loadParametersTOArea.setIsIncrementalGroup(Boolean.TRUE);
		loadParametersTOList.add(loadParametersTOArea);

		ParameterTO loadParametersFluid = new ParameterTO();
		loadParametersFluid.setIncrementalInitialValue("0");
		loadParametersFluid.setIncrementalFinalValue("1000");
		loadParametersFluid.setIncrementalIncrementValue("1");
		loadParametersFluid.setName(FLUID);
		loadParametersFluid.setId(FLUID_ID);
		loadParametersFluid.setIsIncrementalGroup(Boolean.TRUE);
		loadParametersTOList.add(loadParametersFluid);
		
		ParameterTO loadParametersDamping = new ParameterTO();
		InputStream inDamping = new FileInputStream(new File("C:/SifemWindowsResourceFiles/DataAnalysis_config/damping.csv"));
		loadParametersDamping.setParameterFile(inDamping);
		loadParametersDamping.setName(DAMPING);
		loadParametersDamping.setId(DAMPING_ID);
		loadParametersDamping.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersDamping);
		
		ParameterTO loadParametersYoungsModulus = new ParameterTO();
		InputStream inYoungsModulus = new FileInputStream(new File("C:/SifemWindowsResourceFiles/DataAnalysis_config/youngs.csv"));
		loadParametersYoungsModulus.setParameterFile(inYoungsModulus);
		loadParametersYoungsModulus.setName(YOUNG);
		loadParametersYoungsModulus.setId(YOUNG_ID);
		loadParametersYoungsModulus.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersYoungsModulus);

		solverConfigCreatorTO.setLoadParametersTOList(loadParametersTOList);
		solverConfigCreatorTO.setSimulationName("simulationFrequencyByFileUploadValues");
		
					
		//i) Create and configure ExternalLoadParametersTO, then set this object into SolverConfigCreatorTO;
		ExternalLoadParametersTO externalLoadParametersTO = new ExternalLoadParametersTO();
		solverConfigCreatorTO.setExternalLoadParametersTO(externalLoadParametersTO);
		
		
		//l) run the PAK execution service;
		solverConfigCreator.configFileCreationService(solverConfigCreatorTO,"SimulationName");
		Map<ParameterTypes,ParameterTO>  mapTeplateKeysAndIds = new TreeMap<ParameterTypes,ParameterTO>();
		Map<String, List<String>> result = solverConfigCreator.getAttribMapValuesService(solverConfigCreatorTO,mapTeplateKeysAndIds);
		Map<String,CombinationHelper> combinationMap = new TreeMap<String,CombinationHelper>();
		
		int size = solverConfigCreator.applyCombinationMapService(result,solverConfigCreatorTO.getLoadParametersTOList(),combinationMap);
		System.out.println(result);
		
		String location = "";
		
		Assert.assertEquals(size,3000);
		
		for(int count=0;size<3000;count++){
			location=solverConfigCreatorTO.getWorkspace()+"/"+solverConfigCreatorTO.getSimulationName()+"/simulationNumber_"+count+"/"+INPUT_CFG;
			System.out.println(location);
			File createdCFGFile = new File(location);
			Assert.assertTrue(createdCFGFile.exists());
			Assert.assertTrue(createdCFGFile.isFile());
			Assert.assertTrue(createdCFGFile.length()>1);
			 
		}
		
	}


	
	
	@Test(groups= { "default" },priority=4,enabled=false)
	public void testSolverConfigCreator_simulationAllInOne__WithoutName_justID() throws Exception{
				
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
		ParameterTO loadParametersTOArea = new ParameterTO();
		loadParametersTOArea.setAreaValue("100.0;200.0;300.0");
		loadParametersTOArea.setId(FREQUENCY_ID);
		loadParametersTOArea.setIsIncrementalGroup(Boolean.TRUE);
		loadParametersTOList.add(loadParametersTOArea);

		ParameterTO loadParametersFluid = new ParameterTO();
		loadParametersFluid.setParameterUniqueValue("1000.0");
		loadParametersFluid.setId(FLUID_ID);
		loadParametersFluid.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersFluid);
		
		ParameterTO loadParametersDamping = new ParameterTO();
		InputStream inDamping = new FileInputStream(new File("C:/SifemWindowsResourceFiles/DataAnalysis_config/damping.csv"));
		loadParametersDamping.setParameterFile(inDamping);
		loadParametersDamping.setId(DAMPING_ID);
		loadParametersDamping.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersDamping);
		
		ParameterTO loadParametersYoungsModulus = new ParameterTO();
		InputStream inYoungsModulus = new FileInputStream(new File("C:/SifemWindowsResourceFiles/DataAnalysis_config/youngs.csv"));
		loadParametersYoungsModulus.setParameterFile(inYoungsModulus);
		loadParametersYoungsModulus.setId(YOUNG_ID);
		loadParametersYoungsModulus.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersYoungsModulus);

		solverConfigCreatorTO.setLoadParametersTOList(loadParametersTOList);
		solverConfigCreatorTO.setSimulationName("simulationFrequencyByFileUploadValues");
		
					
		//i) Create and configure ExternalLoadParametersTO, then set this object into SolverConfigCreatorTO;
		ExternalLoadParametersTO externalLoadParametersTO = new ExternalLoadParametersTO();
		solverConfigCreatorTO.setExternalLoadParametersTO(externalLoadParametersTO);
		
		
	
		String location = "";
		
		//l) run the PAK execution service;
		solverConfigCreator.configFileCreationService(solverConfigCreatorTO,"SimulationName");
		
		for(int count=0;count<size;count++){
			location=solverConfigCreatorTO.getWorkspace()+"/"+solverConfigCreatorTO.getSimulationName()+"/simulationNumber_"+count+"/"+INPUT_CFG;
			System.out.println(location);
			File createdCFGFile = new File(location);
			Assert.assertTrue(createdCFGFile.exists());
			Assert.assertTrue(createdCFGFile.isFile());
			Assert.assertTrue(createdCFGFile.length()>1);
			 
		}
		
	}

	@Test(groups= { "default" },priority=5,enabled=false)
	public void testSolverConfigCreator_simulationAllInOne_WithoutName_justID() throws Exception{
				
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
		ParameterTO loadParametersTOArea = new ParameterTO();
		loadParametersTOArea.setAreaValue("100;200;300");
		loadParametersTOArea.setId(FREQUENCY_ID);
		loadParametersTOArea.setIsIncrementalGroup(Boolean.TRUE);
		loadParametersTOList.add(loadParametersTOArea);

		ParameterTO loadParametersFluid = new ParameterTO();
		loadParametersFluid.setIncrementalInitialValue("0");
		loadParametersFluid.setIncrementalFinalValue("1000");
		loadParametersFluid.setIncrementalIncrementValue("1");
		loadParametersFluid.setId(FLUID_ID);
		loadParametersFluid.setIsIncrementalGroup(Boolean.TRUE);
		loadParametersTOList.add(loadParametersFluid);
		
		ParameterTO loadParametersDamping = new ParameterTO();
		InputStream inDamping = new FileInputStream(new File("C:/SifemWindowsResourceFiles/DataAnalysis_config/damping.csv"));
		loadParametersDamping.setParameterFile(inDamping);
		loadParametersDamping.setId(DAMPING_ID);
		loadParametersDamping.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersDamping);
		
		ParameterTO loadParametersYoungsModulus = new ParameterTO();
		InputStream inYoungsModulus = new FileInputStream(new File("C:/SifemWindowsResourceFiles/DataAnalysis_config/youngs.csv"));
		loadParametersYoungsModulus.setParameterFile(inYoungsModulus);
		loadParametersYoungsModulus.setId(YOUNG_ID);
		loadParametersYoungsModulus.setIsIncrementalGroup(Boolean.FALSE);
		loadParametersTOList.add(loadParametersYoungsModulus);

		solverConfigCreatorTO.setLoadParametersTOList(loadParametersTOList);
		solverConfigCreatorTO.setSimulationName("simulationFrequencyByFileUploadValues");
		
					
		//i) Create and configure ExternalLoadParametersTO, then set this object into SolverConfigCreatorTO;
		ExternalLoadParametersTO externalLoadParametersTO = new ExternalLoadParametersTO();
		solverConfigCreatorTO.setExternalLoadParametersTO(externalLoadParametersTO);
		
		
		//l) run the PAK execution service;
		solverConfigCreator.configFileCreationService(solverConfigCreatorTO,"SimulationName");
		Map<ParameterTypes,ParameterTO>  mapTeplateKeysAndIds = new TreeMap<ParameterTypes,ParameterTO>();
		Map<String, List<String>> result = solverConfigCreator.getAttribMapValuesService(solverConfigCreatorTO,mapTeplateKeysAndIds);
		Map<String,CombinationHelper> combinationMap = new TreeMap<String,CombinationHelper>();
		
		int size = solverConfigCreator.applyCombinationMapService(result,solverConfigCreatorTO.getLoadParametersTOList(),combinationMap);
		
		String location = "";
		
		Assert.assertEquals(size,3000);
		
		for(int count=0;count<size;count++){
			location=solverConfigCreatorTO.getWorkspace()+"/"+solverConfigCreatorTO.getSimulationName()+"/simulationNumber_"+count+"/"+INPUT_CFG;
			System.out.println(location);
			File createdCFGFile = new File(location);
			Assert.assertTrue(createdCFGFile.exists());
			Assert.assertTrue(createdCFGFile.isFile());
			Assert.assertTrue(createdCFGFile.length()>1);
			 
		}
		
	}
	
	//===
	public ISolverConfigCreatorService getSolverConfigCreator() {
		return solverConfigCreator;
	}

	public void setSolverConfigCreator(
			ISolverConfigCreatorService solverConfigCreator) {
		this.solverConfigCreator = solverConfigCreator;
	}

	
}
