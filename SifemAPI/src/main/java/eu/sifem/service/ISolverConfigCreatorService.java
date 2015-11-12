package eu.sifem.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import eu.sifem.model.enums.ParameterTypes;
import eu.sifem.model.helper.CombinationHelper;
import eu.sifem.model.to.ConfigFileTO;
import eu.sifem.model.to.ParameterTO;
import eu.sifem.model.to.SolverConfigCreatorTO;


/**
 * 
 * @author jbjares
 * 
 */
public interface ISolverConfigCreatorService {

	 static final String SPACE = " ";
	 static final String NA = "NA";
	 static final String FREQUENCY = "FREQUENCY";
	 static final String INPUT_CFG = "input.cfg";
	 static final String PAK = "PAK";

	 //TODO understand BIORC if it's correct 
	 static final String FREQUENCY_CONSTANT = "1.0";
	 static final String FLUID_CONSTANT = "1500.0";
	
	public String configFileCreationService(SolverConfigCreatorTO solverConfigCreatorTO, String simulationName) throws Exception;


	Map<String, List<String>> getAttribMapValuesService(SolverConfigCreatorTO solverConfigCreatorTO,Map<ParameterTypes,ParameterTO> mapTeplateKeysAndIds) throws Exception;

	int applyCombinationMapService(Map<String, List<String>> map,List<ParameterTO> LoadParametersTOList,Map<String, CombinationHelper> combinationMap);


	List<String> createCfgFilesAsStringService(
			SolverConfigCreatorTO solverConfigCreatorTO,
			Map<String, List<String>> mapAttributeValues,
			Map<ParameterTypes, ParameterTO> mapTeplateKeysAndIds)
			throws Exception;

	List<String> handleCocheaBoxwithLongitudinalCouplingCase(
			SolverConfigCreatorTO solverConfigCreatorTO,
			Map<String, List<String>> mapAttributeValues,
			Map<ParameterTypes, ParameterTO> mapTeplateKeysAndIds)
			throws Exception;        

	List<String> handleCocheaTaperedUCLCase(
			SolverConfigCreatorTO solverConfigCreatorTO,
			Map<String, List<String>> mapAttributeValues,
			Map<ParameterTypes, ParameterTO> mapTeplateKeysAndIds)
			throws Exception;        

	List<String> handleCocheaTaperedLIUCase(
			SolverConfigCreatorTO solverConfigCreatorTO,
			Map<String, List<String>> mapAttributeValues,
			Map<ParameterTypes, ParameterTO> mapTeplateKeysAndIds)
			throws Exception;
        
        List<String> handleCochleaCoiledwithLongitudinalCouplingCase(
			SolverConfigCreatorTO solverConfigCreatorTO,
			Map<String, List<String>> mapAttributeValues,
			Map<ParameterTypes, ParameterTO> mapTeplateKeysAndIds)
			throws Exception;

        List<String> handleSliceModelOneCase(
			SolverConfigCreatorTO solverConfigCreatorTO,
			Map<String, List<String>> mapAttributeValues,
			Map<ParameterTypes, ParameterTO> mapTeplateKeysAndIds)
			throws Exception;
        
        
	void saveOrUpdateCFGTO(SolverConfigCreatorTO solverConfigCreatorTO,
			List<String> cfgFileStrList, String projectName) throws Exception;


	public List<ConfigFileTO> findConfigFiles(String projectName, String simulationName)throws Exception;


	public InputStream findCFGFile(ConfigFileTO configFileTO)throws Exception;


}
