package eu.sifem.simulation.solver.pak;


import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVReader;
import eu.sifem.model.enums.ParameterTypes;
import eu.sifem.model.helper.CombinationHelper;
import eu.sifem.model.to.AcessoryFileTO;
import eu.sifem.model.to.ConfigFileTO;
import eu.sifem.model.to.ExternalLoadParametersTO;
import eu.sifem.model.to.GeometrySetupTO;
import eu.sifem.model.to.MeshSetupTO;
import eu.sifem.model.to.ParameterTO;
import eu.sifem.model.to.SolverConfigCreatorTO;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.ISolverConfigCreatorService;
import eu.sifem.service.dao.IAcessoryFileDAOService;
import eu.sifem.service.dao.IConfigFileDAOService;
import eu.sifem.service.dao.IParameterDAOService;
import eu.sifem.utils.BasicFileTools;
import eu.sifem.utils.Util;
import java.io.IOException;

@Service(value="solverConfigCreator")
public class SolverConfigCreatorService implements ISolverConfigCreatorService{



	private static final String CSV = "csv";

	@Autowired
	private IResourceInjectionService iResourceInjectionService;

	@Autowired
	private IParameterDAOService parameterDAO;
	
	@Autowired
	private IConfigFileDAOService configFileDAO;
	
	@Autowired
	private IAcessoryFileDAOService acessoryFileDAO;
	

	@Override
	public String configFileCreationService(SolverConfigCreatorTO solverConfigCreatorTO,String projectName) throws Exception{
		String cfgSessionId = null;
		List<String> cfgFileList = new ArrayList<String>();
		Map<ParameterTypes,ParameterTO>  mapTeplateKeysAndIds = new TreeMap<ParameterTypes,ParameterTO>();
		List<ParameterTO> updatedParameters = addTemplateName(solverConfigCreatorTO);
		solverConfigCreatorTO.setLoadParametersTOList(updatedParameters);
		Map<String,List<String>> mapAttributeValues = getAttribMapValuesService(solverConfigCreatorTO,mapTeplateKeysAndIds);
		List<String> cfgFileStr = createCfgFilesAsStringService(solverConfigCreatorTO,mapAttributeValues,mapTeplateKeysAndIds);
		System.err.println("----------------------------");
                System.err.println(cfgFileStr.get(0));
                System.err.println("----------------------------");
                cfgFileList.addAll(cfgFileStr);
		cfgSessionId = cfginsertCfgList(cfgFileList,solverConfigCreatorTO);
		saveOrUpdateCFGTO(solverConfigCreatorTO, cfgFileStr, cfgSessionId);
		return cfgSessionId;
	}
	
	private String cfginsertCfgList(List<String> cfgFileList,SolverConfigCreatorTO solverConfigCreatorTO) throws Exception {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<cfgFileList.size();i++){
			String str = cfgFileList.get(i);
			InputStream stream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
			String cfgId = saveGeneratedConfigData(solverConfigCreatorTO.getProjectName(),solverConfigCreatorTO.getSimulationName(),stream);
			if(i==cfgFileList.size()){
				sb.append(cfgId);
			}else{
				sb.append(cfgId+";");	
			}
			
		}
		String result = "";
		if(sb.toString()!=null && !"".equals(sb.toString())){
			if(sb.toString().endsWith(";")){
				result = sb.toString().substring(0,sb.toString().length()-1);
			}else{
				result = sb.toString();
			}
		}
		return result;
	}
	private List<ParameterTO> addTemplateName(SolverConfigCreatorTO solverConfigCreatorTO) throws Exception {
		List<ParameterTO> result = new ArrayList<ParameterTO>();
		List<ParameterTO> updatedParameters = parameterDAO.findAllParameters();
		for(ParameterTO parameterWithoutTemplateName:solverConfigCreatorTO.getLoadParametersTOList()){
			for(ParameterTO parameter:updatedParameters){
				if(parameterWithoutTemplateName.getId().equals(parameter.getId())){
					parameterWithoutTemplateName.setTemplateName(parameter.getTemplateName());
					result.add(parameterWithoutTemplateName);
				}
			}
		}
		return result;
	}

	private String saveOrUpdateCSVAcessoryFiles(String parameterName, String csvStrContent,SolverConfigCreatorTO solverConfigCreatorTO) throws Exception {
		String fileInputStreamID = "";
		InputStream acessoryFileIS = acessoryFileDAO.findAcessoryFile(solverConfigCreatorTO.getProjectName(), solverConfigCreatorTO.getSimulationName(), parameterName);
		if(acessoryFileIS!=null){
			fileInputStreamID = acessoryFileDAO.updateAcessoryFile(acessoryFileIS,solverConfigCreatorTO, parameterName,CSV);
		}else{
			AcessoryFileTO acessoryFileTO = new AcessoryFileTO();
			acessoryFileTO.setExtension(CSV);
			acessoryFileTO.setFile(IOUtils.toInputStream(csvStrContent));
			acessoryFileTO.setName(parameterName);
			acessoryFileTO.setProjectName(solverConfigCreatorTO.getProjectName());
			acessoryFileTO.setSimulationName(solverConfigCreatorTO.getSimulationName());
			fileInputStreamID = acessoryFileDAO.insert(acessoryFileTO);
		}
		
		return fileInputStreamID;
	}
	

	@Override
	public void saveOrUpdateCFGTO(SolverConfigCreatorTO solverConfigCreatorTO, List<String> cfgFileStrList,String id) throws Exception {

		int count=0;
		String simulationName = solverConfigCreatorTO.getSimulationName();
		if(cfgFileStrList.isEmpty()){
			return;
		}
		for(String fileStr:cfgFileStrList){
			String instanceName = "instance_"+count;
			String projectName = solverConfigCreatorTO.getProjectName();
			List<ConfigFileTO> cfgTOList = configFileDAO.findConfigTO(projectName, simulationName);
			if(cfgTOList==null || cfgTOList.isEmpty()){
				ConfigFileTO cfgTO = new ConfigFileTO();
				cfgTO.setProjectName(projectName);
				cfgTO.setSimulationName(simulationName);
				cfgTO.setInstanceName(instanceName);
				InputStream cfgIS = IOUtils.toInputStream(fileStr);
				cfgTO.setCfgInputStream(cfgIS);
				configFileDAO.insert(cfgTO);
				continue;
			}
			
			ConfigFileTO cfgTO = cfgTOList.get(0);
			if(cfgTO.getInstanceName()==null){
				cfgTO.setInstanceName(instanceName);
				configFileDAO.updateInstanceName(projectName, simulationName, instanceName);
				continue;
			}
			
			count++;
		}

		
	}
	
	private String saveGeneratedConfigData(String projectName, String simulationName, InputStream  cfgInputStream) throws Exception {
		ConfigFileTO configFileTO = new ConfigFileTO();
		configFileTO.setProjectName(projectName);
		configFileTO.setSimulationName(simulationName);
		configFileTO.setCfgInputStream(cfgInputStream);
		return configFileDAO.insert(configFileTO);
	}
	
	@Deprecated
	private void saveCache(String projectName, String simulationName,String instanceName, File cfgFile) throws Exception {
		ConfigFileTO configFileTO = new ConfigFileTO();
		configFileTO.setProjectName(projectName);
		configFileTO.setSimulationName(simulationName);
		configFileTO.setInstanceName(instanceName);
		configFileTO.setCfgFile(cfgFile);
		configFileDAO.insert(configFileTO);
	}

        
        
	@Override
	public List<String> createCfgFilesAsStringService(SolverConfigCreatorTO solverConfigCreatorTO, Map<String, List<String>> mapAttributeValues, Map<ParameterTypes, ParameterTO> mapTeplateKeysAndIds) throws Exception{
            if(solverConfigCreatorTO.getMeshSetupTO().getName().equals("Cochlea Tapered Model UCL"))
                return handleCocheaTaperedUCLCase(solverConfigCreatorTO, mapAttributeValues, mapTeplateKeysAndIds);

            if(solverConfigCreatorTO.getMeshSetupTO().getName().equals("Cochlea Box Model Longitudinal Coupling"))
                return handleCocheaBoxwithLongitudinalCouplingCase(solverConfigCreatorTO, mapAttributeValues, mapTeplateKeysAndIds);

            if(solverConfigCreatorTO.getMeshSetupTO().getName().equals("Cochlea Coiled Model Longitudinal Coupling"))
                return handleCochleaCoiledwithLongitudinalCouplingCase(solverConfigCreatorTO, mapAttributeValues, mapTeplateKeysAndIds);

            if(solverConfigCreatorTO.getMeshSetupTO().getName().equals("Cochlea Tapered Model LIU"))
                return handleCocheaTaperedLIUCase(solverConfigCreatorTO, mapAttributeValues, mapTeplateKeysAndIds);

            if(solverConfigCreatorTO.getMeshSetupTO().getName().equals("Cochlea Slice Model 1") || 
               solverConfigCreatorTO.getMeshSetupTO().getName().equals("Cochlea Slice Model 2") ||
               solverConfigCreatorTO.getMeshSetupTO().getName().equals("Cochlea Slice Model 3"))
                return handleSliceModelOneCase(solverConfigCreatorTO, mapAttributeValues, mapTeplateKeysAndIds);

            if(solverConfigCreatorTO.getMeshSetupTO().getName().equals("Head Model"))
                return handleHeadModelCase(solverConfigCreatorTO, mapAttributeValues, mapTeplateKeysAndIds);
            
            
            return null;
	}


	private Map<ParameterTypes, Integer> getMapCounter() {
		Map<ParameterTypes,Integer> mapCount = new TreeMap<ParameterTypes, Integer>();

		Set<ParameterTypes> parameterTypes = EnumSet.allOf(ParameterTypes.class);
		for(ParameterTypes type : parameterTypes) {
			mapCount.put(type, BigDecimal.ZERO.intValue());
		}
		return mapCount;
	}

	@Override
	public int applyCombinationMapService(Map<String, List<String>> map, List<ParameterTO> LoadParametersTOList, Map<String, CombinationHelper> combinationMap) {
		int result = 1;
		List<String> keyList = new ArrayList<String>();
		Boolean isFirstLoop = Boolean.TRUE;
		Set<String> setOfIds = new TreeSet<String>();
		for(ParameterTO loadParametersTO:LoadParametersTOList){
			if(loadParametersTO.getIsIncrementalGroup()){
				for (Map.Entry<String, List<String>> entry : map.entrySet()) {
					if(StringUtils.equalsIgnoreCase(loadParametersTO.getId(),entry.getKey())){
						if(setOfIds.contains(loadParametersTO.getId())){
							continue;
						}else{
							setOfIds.add(loadParametersTO.getId());							
						}
						CombinationHelper ch = new CombinationHelper();
						ch.setName(entry.getKey());
						ch.setMaxIndex(entry.getValue().size()-1);
						ch.setMinIndex(0);
						combinationMap.put(entry.getKey(),ch);
						keyList.add(entry.getKey());
						if(isFirstLoop){
							result = entry.getValue().size();
							isFirstLoop = Boolean.FALSE;
						}else{
							result = result*entry.getValue().size();							
						}
					}
				}
			}
		}
		

		return result;
	}



	@Override
	public Map<String, List<String>> getAttribMapValuesService(SolverConfigCreatorTO solverConfigCreatorTO,Map<ParameterTypes,ParameterTO> mapTeplateKeysAndIds) throws Exception{
		Map<String,List<String>> mapAttributeValues = new TreeMap<String,List<String>>();
		Map<String,String> propertyGenericValues = new TreeMap<String,String>();
		List<ParameterTO> updatedParameters = addTemplateName(solverConfigCreatorTO);
		solverConfigCreatorTO.setLoadParametersTOList(updatedParameters);
		for(ParameterTO loadParametersTO : solverConfigCreatorTO.getLoadParametersTOList()){
			System.out.println("Parameters to 1: "+loadParametersTO+ "++++"+loadParametersTO.getTemplateName());
			System.out.println("Parameters to 2: "+ParameterTypes.getParameterTypesByTemplateName(loadParametersTO.getTemplateName()));
			mapTeplateKeysAndIds.put(ParameterTypes.getParameterTypesByTemplateName(loadParametersTO.getTemplateName()),loadParametersTO);
			List<String> parameterValueList = new ArrayList<String>();
			
			String parameterUniqueValue = loadParametersTO.getParameterUniqueValue();
			if(parameterUniqueValue!=null && !"".equals(parameterUniqueValue)){
				parameterValueList.add(parameterUniqueValue);
				propertyGenericValues.put("parameterUniqueValue",parameterUniqueValue);
				mapAttributeValues.put(loadParametersTO.getId(),parameterValueList);
				continue;
			}
			
			if(loadParametersTO.getParameterFile()!=null){	
				StringBuilder sb = new StringBuilder();
				InputStream in = loadParametersTO.getParameterFile();
				InputStreamReader isr = new InputStreamReader(in);
				if(!isr.ready()){continue;}
				CSVReader reader = new CSVReader(isr);
				synchronized (reader) {
					try{
						List<String[]> lstOfData=new BasicFileTools().csvReader(reader);
						for(String row[]:lstOfData){
                                                    for(int j=0;j<row.length;j++)
							sb.append(row[j] + SPACE);
                                                    
                                                    //sb.append("\r\n");
						}						
					}finally{
						reader.close();
					}
				}

				String csv = sb.toString();
                                
                                System.err.println(" ---- " + csv);
                                
				parameterValueList.add(csv);
				String fileID = saveOrUpdateCSVAcessoryFiles(loadParametersTO.getName(), csv, solverConfigCreatorTO);
				propertyGenericValues.put("parameterFile",fileID);
				mapAttributeValues.put(loadParametersTO.getId(),parameterValueList);
				continue;
			}
			
			
			if(loadParametersTO.getAreaValue()!=null && !"".equals(loadParametersTO.getAreaValue())){
				StringBuilder sb = new StringBuilder();                            
				String areaParam = Util.changeStringToDefaultSeparator(loadParametersTO.getAreaValue());
				String[] areaParamArr = areaParam.split("\n");

                                System.out.println(" areaParam " + areaParam);
                                System.out.println(" areaParam.le " + areaParamArr.length);

                                String[] areaParamArrNoSemi = null;

                                for(int i=0;i<areaParamArr.length;i++)
                                {
                                    areaParamArrNoSemi = areaParamArr[i].split(";");
                                    for(int j=0;j<areaParamArrNoSemi.length;j++)
                                        sb.append(areaParamArrNoSemi[j] + SPACE);
                                }                                
                                
				if(areaParamArr.length>0){
					parameterValueList.addAll(Arrays.asList(areaParamArr));	
					//String fileID = saveOrUpdateCSVAcessoryFiles(loadParametersTO.getName(), areaParam, solverConfigCreatorTO);
                                        String fileID = saveOrUpdateCSVAcessoryFiles(loadParametersTO.getName(), sb.toString(), solverConfigCreatorTO);
					propertyGenericValues.put("areaValue",fileID);
				}                                                               
                                
				mapAttributeValues.put(loadParametersTO.getId(),parameterValueList);
				continue;
			}

			
			if(loadParametersTO.getIncrementalInitialValue()!=null && loadParametersTO.getIncrementalFinalValue()!=null && loadParametersTO.getIncrementalIncrementValue()!=null){
				String incrementalValues = loadParametersTO.getIncrementalInitialValue()+";"+
						loadParametersTO.getIncrementalFinalValue()+";"+
						loadParametersTO.getIncrementalIncrementValue();
				if(incrementalValues!=null && !"".equals(incrementalValues)){
					if(StringUtils.countMatches(incrementalValues, ";")==2){
						String[] valuesArr = incrementalValues.split(";");
						if(valuesArr.length>0){
							parameterValueList.addAll(getIncrementedValueList(Arrays.asList(valuesArr)));
							propertyGenericValues.put("incrementalInitialValue",valuesArr[0]);
							propertyGenericValues.put("incrementalFinalValue",valuesArr[1]);
							propertyGenericValues.put("incrementalIncrementValue",valuesArr[2]);							
						}
					}
				}
				mapAttributeValues.put(loadParametersTO.getId(),parameterValueList);
				continue;
			}
			

		}
                
                System.err.println(" - "+ mapAttributeValues);
		return mapAttributeValues;
	}


	public List<String> getIncrementedValueList(List<String> list) {
		if(list==null || list.isEmpty()){
			return Collections.emptyList();
		}
		
		if(list.get(0)==null || !StringUtils.isNumeric(list.get(0))){
			return Collections.emptyList();
		}
		
		if(list.get(2)==null || !StringUtils.isNumeric(list.get(2))){
			return Collections.emptyList();
		}
		Integer inicialValue = new Integer(list.get(0));
		Integer finalValue = new Integer(list.get(1));
		Double incrementalValue = new Double(list.get(2));
		

		
		List<String> result = new ArrayList<String>();
		double i = inicialValue;
		while(i<finalValue){
			i = i+incrementalValue;
			result.add(Double.toString(i));
		}
		
		return result;
	}

	@Override
	public List<ConfigFileTO> findConfigFiles(String projectName,String simulationName) throws Exception {
		return configFileDAO.findConfigTO(projectName, simulationName);
	}

	@Override
	public InputStream findCFGFile(ConfigFileTO configFileTO)throws Exception {
		return configFileDAO.findCFGFile(configFileTO);
	}

    @Override
    public List<String> handleCocheaBoxwithLongitudinalCouplingCase(SolverConfigCreatorTO solverConfigCreatorTO, Map<String, List<String>> mapAttributeValues, Map<ParameterTypes, ParameterTO> mapTeplateKeysAndIds) throws Exception {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("inputcfgtemplate.txt");
		String templateStr = IOUtils.toString(in);
		
		List<String> resultFilesAsString = new ArrayList<String>();
		Map<ParameterTypes,Integer> mapCount = getMapCounter();
		
		Boolean isMeshDivisionFilled = Boolean.FALSE;
		Boolean isGeometryFilled = Boolean.FALSE;
		Boolean isLoadParametersFilled = Boolean.FALSE;
		Boolean isMaterialPropertyFilled = Boolean.FALSE;
		Boolean isYoungsFilled = Boolean.FALSE;
		Boolean isDampingFilled = Boolean.FALSE;
		Boolean isExternalparametersFilled = Boolean.FALSE;
		
		Map<String,CombinationHelper> combinationMap = new TreeMap<String,CombinationHelper>();
		
		int size = applyCombinationMapService(mapAttributeValues,solverConfigCreatorTO.getLoadParametersTOList(),combinationMap);
                System.err.println("size = " + size);
		for(int i=0;i<size;i++){
			StringBuilder sb = new StringBuilder();
			int count = 0;
			//MESH PART
			++count;
			String paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.MESH.getName())){
				if(mapCount.get(ParameterTypes.MESH)==BigDecimal.ZERO.intValue()){
					sb.append(paramHeader);
					MeshSetupTO meshSetupTO = solverConfigCreatorTO.getMeshSetupTO();
					sb.append(meshSetupTO.getDivisionL());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionW());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionB());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionH());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionh());
					sb.append("\r\n\r\n\"");
					isMeshDivisionFilled = Boolean.TRUE;				
				}
				mapCount.put(ParameterTypes.MESH, BigDecimal.ONE.intValue());
			}
				
                        System.err.println("1 -- " + sb.toString());
			//GEOMETRY PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.GEOMETRY.getName())){
				if(mapCount.get(ParameterTypes.GEOMETRY).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					GeometrySetupTO geometrySetupTO = solverConfigCreatorTO.getGeometrySetupTO();
					sb.append("0");
					sb.append(SPACE);                                        
					sb.append(geometrySetupTO.getLengthL());
					sb.append(SPACE);
					sb.append(geometrySetupTO.getHeigthH());
					sb.append(SPACE);
					sb.append(geometrySetupTO.getWidthW());
					sb.append(SPACE);
					sb.append(geometrySetupTO.getWidthBM());
					sb.append(SPACE);
					sb.append(geometrySetupTO.getThicknessh());
					sb.append("\r\n\r\n\"");
					isGeometryFilled = Boolean.TRUE;				
				}
				mapCount.put(ParameterTypes.GEOMETRY, BigDecimal.ONE.intValue());
			}
                        System.err.println("2 -- " + sb.toString());

			
			//LOAD PARAMETERS 
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.FREQUENCY.getName())){
				if(mapCount.get(ParameterTypes.FREQUENCY).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
								sb.append(valueStr +"  "+FREQUENCY_CONSTANT+ "\r\n\r\n\"");		
							}	
						isLoadParametersFilled = Boolean.TRUE;		
					}
		
					mapCount.put(ParameterTypes.FREQUENCY, BigDecimal.ONE.intValue());
				}
			}
                        System.err.println("3 -- " + sb.toString());
				

			//MATERIAL PROPERTIES PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.FLUID.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.FLUID)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FLUID).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.FLUID).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						String value = NA;
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FLUID).getId());
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.FLUID).getId());
						int currentIndex = 0;
						if(ch!=null && ch.getCurrentIndex()!=null){
							currentIndex = ch.getCurrentIndex();						
						}
						if(!valueList.isEmpty()){
							value = valueList.get(currentIndex);
							if(ch!=null && ch.getCurrentIndex()!=null){
								ch.setCurrentIndex(++currentIndex);						
							}
												
						}
						if(!NA.equals(value) && !"".equals(value)){
							sb.append(value+"  "+FLUID_CONSTANT+" \r\n\r\n\"");
						}
						isMaterialPropertyFilled = Boolean.TRUE;				
					}
					mapCount.put(ParameterTypes.FLUID, BigDecimal.ONE.intValue());
				}

			}
                        System.err.println("4 -- " + sb.toString());

                        
			//YOUNG_MODULUS_FUNCTION PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.YOUNG.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.YOUNG)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.YOUNG).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.YOUNG).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.YOUNG).getId());
						if(valueList!=null){
							sb.append(Util.removeListKeysFromString(valueList.toString()));
							isYoungsFilled = Boolean.TRUE;										
						}
					}
					mapCount.put(ParameterTypes.YOUNG, BigDecimal.ONE.intValue());
				}

			}
				
				
			//DAMPING_MODULUS_FUNCTION PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.DAMPING.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.DAMPING)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.DAMPING).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.DAMPING).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
							List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.DAMPING).getId());		
							if(valueList!=null){
								sb.append(Util.removeListKeysFromString(valueList.toString()));
								isDampingFilled = Boolean.TRUE;
							}
						}
						mapCount.put(ParameterTypes.DAMPING, BigDecimal.ONE.intValue());	
				}
	
				
			}
                        System.err.println("5 -- " + sb.toString());
			
				
			//EXTERNAL_LOAD_PARAMETERS PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.EXTERNAL.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.EXTERNAL)!=null &&!mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.EXTERNAL).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.EXTERNAL).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						ExternalLoadParametersTO externalLoadParametersTO = solverConfigCreatorTO.getExternalLoadParametersTO();
						sb.append(externalLoadParametersTO.getDispX());
						sb.append(SPACE);
						sb.append(externalLoadParametersTO.getDispY());
						sb.append(SPACE);
						sb.append(externalLoadParametersTO.getDispZ());
						sb.append("\r\n\r\n\"");
						isExternalparametersFilled = Boolean.TRUE;				
					}
					mapCount.put(ParameterTypes.EXTERNAL, BigDecimal.ONE.intValue());
				}

			}
                        System.err.println("6 -- " + sb.toString());

                        
//			System.out.println("Before test: "+sb.toString().trim());                        
			Boolean isAllParametersFilled = isMeshDivisionFilled && isGeometryFilled && isLoadParametersFilled &&
					isMaterialPropertyFilled &&  isYoungsFilled && isDampingFilled;// && isExternalparametersFilled;
                        
                        System.err.println("allParams -- " + isAllParametersFilled);

                        
			if(isAllParametersFilled && !"".equals(sb.toString())){
//				System.out.println("into if");
//				System.out.println("isAllParametersFilled: "+isAllParametersFilled);
//				System.out.println("Before test: "+sb.toString().trim());
				resultFilesAsString.add(sb.toString().trim());	
				mapCount = getMapCounter();
			}else{
//				System.out.println("into else");
//				System.out.println("isAllParametersFilled: "+isAllParametersFilled);
//				System.out.println("Before test: "+sb.toString().trim());
				continue;
			}
		}

//		System.out.println("After Loop");
//		System.out.println("resultFilesAsString: "+resultFilesAsString);
//		System.out.println("================");
//
//		Boolean isAllParametersFilled = isMeshDivisionFilled && isGeometryFilled && isLoadParametersFilled &&
//				isMaterialPropertyFilled &&  isYoungsFilled && isDampingFilled; //&& isExternalparametersFilled
//		
//		System.out.println("isAllParametersFilled: "+isAllParametersFilled);
//		System.out.println("isMeshDivisionFilled: "+isMeshDivisionFilled);
//		System.out.println("isGeometryFilled: "+isGeometryFilled);
//		System.out.println("isLoadParametersFilled: "+isLoadParametersFilled);
//		System.out.println("isMaterialPropertyFilled: "+isMaterialPropertyFilled);
//		System.out.println("isYoungsFilled: "+isYoungsFilled);
//		System.out.println("isDampingFilled: "+isDampingFilled);
//		System.out.println("isExternalparametersFilled: "+isExternalparametersFilled);
		
                System.err.println("Res: " + resultFilesAsString);
		return resultFilesAsString;
    }

    @Override
    public List<String> handleCocheaTaperedUCLCase(SolverConfigCreatorTO solverConfigCreatorTO, Map<String, List<String>> mapAttributeValues, Map<ParameterTypes, ParameterTO> mapTeplateKeysAndIds) throws Exception {
		//InputStream in = this.getClass().getClassLoader().getResourceAsStream("inputcfgtemplate.txt");
                InputStream in = this.getClass().getClassLoader().getResourceAsStream("input_CochleaTaperedModel-UCLtemplate.txt");
		String templateStr = IOUtils.toString(in);
		
		List<String> resultFilesAsString = new ArrayList<String>();
		Map<ParameterTypes,Integer> mapCount = getMapCounter();
		
		Boolean isMeshDivisionFilled = Boolean.FALSE;
		Boolean isGeometryFilled = Boolean.FALSE;
		Boolean isLoadParametersFilled = Boolean.FALSE;
		Boolean isMaterialPropertyFilled = Boolean.FALSE;
		Boolean isConductFilled = Boolean.FALSE;
		Boolean isSolidFilled = Boolean.FALSE;                
		Boolean isRoundWindowFiled = Boolean.FALSE;
		Boolean isBasilarMembraneFiled = Boolean.FALSE;
                Boolean isAnalysisFilled = Boolean.FALSE;
                
		Map<String,CombinationHelper> combinationMap = new TreeMap<String,CombinationHelper>();
		
		int size = applyCombinationMapService(mapAttributeValues,solverConfigCreatorTO.getLoadParametersTOList(),combinationMap);
                System.err.println("size = " + size);
		for(int i=0;i<1;i++){
			StringBuilder sb = new StringBuilder();
			int count = 0;
			//MESH PART
			++count;
			String paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.MESH.getName())){
				if(mapCount.get(ParameterTypes.MESH)==BigDecimal.ZERO.intValue()){
					sb.append(paramHeader);
					MeshSetupTO meshSetupTO = solverConfigCreatorTO.getMeshSetupTO();
					sb.append(meshSetupTO.getDivisionL().intValue());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionW().intValue());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionB().intValue());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionH().intValue());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionh().intValue());
					sb.append("\r\n\r\n");
					isMeshDivisionFilled = Boolean.TRUE;				
				}
				mapCount.put(ParameterTypes.MESH, BigDecimal.ONE.intValue());
			}
				
                        System.err.println("1 -- " + sb.toString());
			//GEOMETRY PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");

			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.GEOMETRY.getName())){
				if(mapCount.get(ParameterTypes.GEOMETRY).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					GeometrySetupTO geometrySetupTO = solverConfigCreatorTO.getGeometrySetupTO();
					sb.append(geometrySetupTO.getLengthL());
					sb.append(SPACE);
					sb.append(geometrySetupTO.getHeigthH());
					sb.append(SPACE);
					sb.append(geometrySetupTO.getWidthW());
					sb.append(SPACE);
                                        sb.append(geometrySetupTO.getWidthBMstart());
					sb.append(SPACE);
                                        sb.append(geometrySetupTO.getWidthBMend());
					sb.append(SPACE);                                        
                                        sb.append(geometrySetupTO.getThicknesshstart());
					sb.append(SPACE);
                                        sb.append(geometrySetupTO.getThicknesshend());
					sb.append(SPACE);                                        
                                        sb.append(geometrySetupTO.getHelicotrema());                                        
					sb.append("\r\n\r\n");
					isGeometryFilled = Boolean.TRUE;				
				}
				mapCount.put(ParameterTypes.GEOMETRY, BigDecimal.ONE.intValue());
			}
                        /*
                        isGeometryFilled = Boolean.TRUE;
                        sb.append(paramHeader);
                        sb.append("3.5e+01 1.0e+00 1.0e+00 6.0e-05 6.0e-04 5.0e-06 5.e-06 0.001\n");
                        */
                        System.err.println("2 -- " + sb.toString());

			
			//LOAD PARAMETERS 
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.FREQUENCY.getName())){
				if(mapCount.get(ParameterTypes.FREQUENCY).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
								sb.append(valueStr +"  "+FREQUENCY_CONSTANT+"\r\n\r\n");
							}	
						isLoadParametersFilled = Boolean.TRUE;		
					}
		
					mapCount.put(ParameterTypes.FREQUENCY, BigDecimal.ONE.intValue());
				}
			}
                        System.err.println("3 -- " + sb.toString());
                        
			//CONDUCT PARAMETERS 
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.CONDUCTION.getName())){
				if(mapCount.get(ParameterTypes.CONDUCTION).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.CONDUCTION)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.CONDUCTION).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.CONDUCTION).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.CONDUCTION).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
                                                        
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
								sb.append(valueStr + "\r\n\r\n");
							}
						isConductFilled = Boolean.TRUE;		
					}
		
					mapCount.put(ParameterTypes.CONDUCTION, BigDecimal.ONE.intValue());
				}
			}
                        System.err.println("3 -- " + sb.toString());
                        
                        
			//MATERIAL PROPERTIES PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.FLUID.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.FLUID)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FLUID).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.FLUID).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						String value = NA;
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FLUID).getId());
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.FLUID).getId());
						int currentIndex = 0;
						if(ch!=null && ch.getCurrentIndex()!=null){
							currentIndex = ch.getCurrentIndex();						
						}
						if(!valueList.isEmpty()){
							value = valueList.get(currentIndex);
							if(ch!=null && ch.getCurrentIndex()!=null){
								ch.setCurrentIndex(++currentIndex);						
							}
												
						}
                                                
						if(!NA.equals(value) && !"".equals(value)){
							sb.append(value + " \r\n");
						}

						isMaterialPropertyFilled = Boolean.TRUE;				
					}
					mapCount.put(ParameterTypes.FLUID, BigDecimal.ONE.intValue());
				}

			}
                        System.err.println("4 -- " + sb.toString());

			//SOLID PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.SOLID.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.SOLID)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.SOLID).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.SOLID).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.SOLID).getId());
						if(valueList!=null){
							sb.append(Util.removeListKeysFromString(valueList.toString()));
							isSolidFilled = Boolean.TRUE;										
						}
					}
					mapCount.put(ParameterTypes.SOLID, BigDecimal.ONE.intValue());
				}

			}
                        sb.append("\n");
                        
			//ROUND WINDOW PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.ROUNDWINDOW.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.ROUNDWINDOW)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.ROUNDWINDOW).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.ROUNDWINDOW).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.ROUNDWINDOW).getId());
						if(valueList!=null){
							sb.append(Util.removeListKeysFromString(valueList.toString()));
							isRoundWindowFiled = Boolean.TRUE;										
						}
					}
					mapCount.put(ParameterTypes.ROUNDWINDOW, BigDecimal.ONE.intValue());
				}

			}                        
			sb.append("\n");	

			//BASILAR MEMRBANE PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.BASILARMEMMBRANE.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.BASILARMEMMBRANE)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.BASILARMEMMBRANE).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.BASILARMEMMBRANE).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.BASILARMEMMBRANE).getId());
						if(valueList!=null){
							sb.append(Util.removeListKeysFromString(valueList.toString()));
							isBasilarMembraneFiled = Boolean.TRUE;										
						}
					}
					mapCount.put(ParameterTypes.BASILARMEMMBRANE, BigDecimal.ONE.intValue());
				}

			}                        
			sb.append("\n");                        

			//ANALYSIS PARAMETERS 
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.ANALYSIS.getName())){
				if(mapCount.get(ParameterTypes.ANALYSIS).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.ANALYSIS)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.ANALYSIS).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.ANALYSIS).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.ANALYSIS).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
                                                        
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
								sb.append(valueStr + "\r\n");
							}
                                                                
						isAnalysisFilled = Boolean.TRUE;		
					}
		
					mapCount.put(ParameterTypes.ANALYSIS, BigDecimal.ONE.intValue());
				}
			}
                        System.err.println("3 -- " + sb.toString());
                        
                        
			System.out.println("Before test: "+sb.toString().trim());                        
			Boolean isAllParametersFilled = isMeshDivisionFilled && isGeometryFilled && isLoadParametersFilled &&
					isMaterialPropertyFilled &&  isConductFilled && isSolidFilled && isRoundWindowFiled && isBasilarMembraneFiled && isAnalysisFilled; // && isExternalparametersFilled;
                        
                        System.err.println("allParams -- " + sb.toString());

                        
			if(isAllParametersFilled && !"".equals(sb.toString())){
//				System.out.println("into if");
//				System.out.println("isAllParametersFilled: "+isAllParametersFilled);
//				System.out.println("Before test: "+sb.toString().trim());
				resultFilesAsString.add(sb.toString().trim());	
				mapCount = getMapCounter();
			}else{
//				System.out.println("into else");
//				System.out.println("isAllParametersFilled: "+isAllParametersFilled);
//				System.out.println("Before test: "+sb.toString().trim());
				continue;
			}
		}

//		System.out.println("After Loop");
//		System.out.println("resultFilesAsString: "+resultFilesAsString);
//		System.out.println("================");
//
//		Boolean isAllParametersFilled = isMeshDivisionFilled && isGeometryFilled && isLoadParametersFilled &&
//				isMaterialPropertyFilled &&  isYoungsFilled && isDampingFilled; //&& isExternalparametersFilled
//		
//		System.out.println("isAllParametersFilled: "+isAllParametersFilled);
//		System.out.println("isMeshDivisionFilled: "+isMeshDivisionFilled);
//		System.out.println("isGeometryFilled: "+isGeometryFilled);
//		System.out.println("isLoadParametersFilled: "+isLoadParametersFilled);
//		System.out.println("isMaterialPropertyFilled: "+isMaterialPropertyFilled);
//		System.out.println("isYoungsFilled: "+isYoungsFilled);
//		System.out.println("isDampingFilled: "+isDampingFilled);
//		System.out.println("isExternalparametersFilled: "+isExternalparametersFilled);
		
                System.err.println("Res: " + resultFilesAsString);
		return resultFilesAsString;
   
    }

    public List<String> handleCochleaCoiledwithLongitudinalCouplingCase(SolverConfigCreatorTO solverConfigCreatorTO, Map<String, List<String>> mapAttributeValues, Map<ParameterTypes, ParameterTO> mapTeplateKeysAndIds) throws IOException
    {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("input_CochleaCoiledModel-WithLongitudinalCouplingtemplate.txt");
		String templateStr = IOUtils.toString(in);
		
		List<String> resultFilesAsString = new ArrayList<String>();
		Map<ParameterTypes,Integer> mapCount = getMapCounter();
		
		Boolean isMeshDivisionFilled = Boolean.FALSE;
		Boolean isGeometryFilled = Boolean.FALSE;
		Boolean isLoadParametersFilled = Boolean.FALSE;
		Boolean isMaterialPropertyFilled = Boolean.FALSE;
		Boolean isYoungsFilled = Boolean.FALSE;
		Boolean isDampingFilled = Boolean.FALSE;
                Boolean isDampingMuptiplierFilled = Boolean.FALSE;
		Boolean isExternalparametersFilled = Boolean.FALSE;
                Boolean isFunctionFilled = Boolean.FALSE;
		
		Map<String,CombinationHelper> combinationMap = new TreeMap<String,CombinationHelper>();
		
		int size = applyCombinationMapService(mapAttributeValues,solverConfigCreatorTO.getLoadParametersTOList(),combinationMap);
                System.err.println("size = " + size);
		for(int i=0;i<size;i++){
			StringBuilder sb = new StringBuilder();
			int count = 0;
			//MESH PART
			++count;
			String paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.MESH.getName())){
				if(mapCount.get(ParameterTypes.MESH)==BigDecimal.ZERO.intValue()){
					sb.append(paramHeader);
					MeshSetupTO meshSetupTO = solverConfigCreatorTO.getMeshSetupTO();
					sb.append(meshSetupTO.getDivisionL());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionW());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionB());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionH());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionh());
					sb.append("\r\n\r\n");
					isMeshDivisionFilled = Boolean.TRUE;				
				}
				mapCount.put(ParameterTypes.MESH, BigDecimal.ONE.intValue());
			}
				
                        System.err.println("1 -- " + sb.toString());
			//GEOMETRY PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.GEOMETRY.getName())){
				if(mapCount.get(ParameterTypes.GEOMETRY).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					GeometrySetupTO geometrySetupTO = solverConfigCreatorTO.getGeometrySetupTO();
					sb.append("0");
					sb.append(SPACE);                                        
					sb.append(geometrySetupTO.getLengthL());
					sb.append(SPACE);
					sb.append(geometrySetupTO.getHeigthH());
					sb.append(SPACE);
					sb.append(geometrySetupTO.getWidthW());
					sb.append(SPACE);
					sb.append(geometrySetupTO.getWidthBM());
					sb.append(SPACE);
					sb.append(geometrySetupTO.getThicknessh());
					sb.append("\r\n\r\n");
					isGeometryFilled = Boolean.TRUE;				
				}
				mapCount.put(ParameterTypes.GEOMETRY, BigDecimal.ONE.intValue());
			}
                        System.err.println("2 -- " + sb.toString());

			
			//LOAD PARAMETERS 
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.FREQUENCY.getName())){
				if(mapCount.get(ParameterTypes.FREQUENCY).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
								sb.append(valueStr +"  "+FREQUENCY_CONSTANT+ "\r\n\r\n");		
							}	
						isLoadParametersFilled = Boolean.TRUE;		
					}
		
					mapCount.put(ParameterTypes.FREQUENCY, BigDecimal.ONE.intValue());
				}
			}
                        System.err.println("3 -- " + sb.toString());
				

			//MATERIAL PROPERTIES PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.FLUID.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.FLUID)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FLUID).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.FLUID).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						String value = NA;
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FLUID).getId());
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.FLUID).getId());
						int currentIndex = 0;
						if(ch!=null && ch.getCurrentIndex()!=null){
							currentIndex = ch.getCurrentIndex();						
						}
						if(!valueList.isEmpty()){
							value = valueList.get(currentIndex);
							if(ch!=null && ch.getCurrentIndex()!=null){
								ch.setCurrentIndex(++currentIndex);						
							}
												
						}
						if(!NA.equals(value) && !"".equals(value)){
							sb.append(value+"  "+FLUID_CONSTANT+" \r\n\r\n");
						}
						isMaterialPropertyFilled = Boolean.TRUE;				
					}
					mapCount.put(ParameterTypes.FLUID, BigDecimal.ONE.intValue());
				}

			}
                        System.err.println("4 -- " + sb.toString());

                        
			//YOUNG_MODULUS_FUNCTION PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.YOUNG.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.YOUNG)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.YOUNG).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.YOUNG).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.YOUNG).getId());
						if(valueList!=null){
							sb.append(Util.removeListKeysFromString(valueList.toString()));
							isYoungsFilled = Boolean.TRUE;										
						}
					}
					mapCount.put(ParameterTypes.YOUNG, BigDecimal.ONE.intValue());
				}

			}

			//DAMPING MULTIPLIER PARAMETERS 
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.DAMPING_MULTIPLIER.getName())){
				if(mapCount.get(ParameterTypes.DAMPING_MULTIPLIER).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.DAMPING_MULTIPLIER)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.DAMPING_MULTIPLIER).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.DAMPING_MULTIPLIER).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.DAMPING_MULTIPLIER).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
								sb.append(valueStr +"  "+FREQUENCY_CONSTANT+ "\r\n\r\n");		
							}	
						isDampingMuptiplierFilled = Boolean.TRUE;		
					}
		
					mapCount.put(ParameterTypes.DAMPING_MULTIPLIER, BigDecimal.ONE.intValue());
				}
			}
                        System.err.println("3 -- " + sb.toString());
                        
			//DAMPING REST PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.DAMPING.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.DAMPING)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.DAMPING).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.DAMPING).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.DAMPING).getId());
						if(valueList!=null){
							sb.append(Util.removeListKeysFromString(valueList.toString()));
							isDampingFilled = Boolean.TRUE;										
						}
					}
					mapCount.put(ParameterTypes.DAMPING, BigDecimal.ONE.intValue());
				}

			}			
				
			//EXTERNAL_LOAD_PARAMETERS PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.EXTERNAL.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.EXTERNAL)!=null &&!mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.EXTERNAL).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.EXTERNAL).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						ExternalLoadParametersTO externalLoadParametersTO = solverConfigCreatorTO.getExternalLoadParametersTO();
						sb.append(externalLoadParametersTO.getDispX());
						sb.append(SPACE);
						sb.append(externalLoadParametersTO.getDispY());
						sb.append(SPACE);
						sb.append(externalLoadParametersTO.getDispZ());
						sb.append("\r\n\r\n");
						isExternalparametersFilled = Boolean.TRUE;				
					}
					mapCount.put(ParameterTypes.EXTERNAL, BigDecimal.ONE.intValue());
				}

			}
                        System.err.println("6 -- " + sb.toString());

			//FUNCTION PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.FUNCT.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.FUNCT)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FUNCT).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.FUNCT).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.YOUNG).getId());
						if(valueList!=null){
							sb.append(Util.removeListKeysFromString(valueList.toString()));
							isFunctionFilled = Boolean.TRUE;										
						}
					}
					mapCount.put(ParameterTypes.FUNCT, BigDecimal.ONE.intValue());
				}

			}                        
                        
//			System.out.println("Before test: "+sb.toString().trim());                        
			Boolean isAllParametersFilled = isMeshDivisionFilled && isGeometryFilled && isLoadParametersFilled &&
					isMaterialPropertyFilled &&  isYoungsFilled && isDampingFilled && isDampingMuptiplierFilled && isExternalparametersFilled && isFunctionFilled;// && isExternalparametersFilled;
                        
                        System.err.println("allParams -- " + isAllParametersFilled);

                        
			if(isAllParametersFilled && !"".equals(sb.toString())){
//				System.out.println("into if");
//				System.out.println("isAllParametersFilled: "+isAllParametersFilled);
//				System.out.println("Before test: "+sb.toString().trim());
				resultFilesAsString.add(sb.toString().trim());	
				mapCount = getMapCounter();
			}else{
//				System.out.println("into else");
//				System.out.println("isAllParametersFilled: "+isAllParametersFilled);
//				System.out.println("Before test: "+sb.toString().trim());
				continue;
			}
		}

//		System.out.println("After Loop");
//		System.out.println("resultFilesAsString: "+resultFilesAsString);
//		System.out.println("================");
//
//		Boolean isAllParametersFilled = isMeshDivisionFilled && isGeometryFilled && isLoadParametersFilled &&
//				isMaterialPropertyFilled &&  isYoungsFilled && isDampingFilled; //&& isExternalparametersFilled
//		
//		System.out.println("isAllParametersFilled: "+isAllParametersFilled);
//		System.out.println("isMeshDivisionFilled: "+isMeshDivisionFilled);
//		System.out.println("isGeometryFilled: "+isGeometryFilled);
//		System.out.println("isLoadParametersFilled: "+isLoadParametersFilled);
//		System.out.println("isMaterialPropertyFilled: "+isMaterialPropertyFilled);
//		System.out.println("isYoungsFilled: "+isYoungsFilled);
//		System.out.println("isDampingFilled: "+isDampingFilled);
//		System.out.println("isExternalparametersFilled: "+isExternalparametersFilled);
		
                System.err.println("Res: " + resultFilesAsString);
		return resultFilesAsString;
        
        
    }

    public List<String> handleCocheaTaperedLIUCase(SolverConfigCreatorTO solverConfigCreatorTO, Map<String, List<String>> mapAttributeValues, Map<ParameterTypes, ParameterTO> mapTeplateKeysAndIds) throws IOException {
                InputStream in = this.getClass().getClassLoader().getResourceAsStream("input_Cochlea_Tapered_Model_LIUtemplate.txt");
		String templateStr = IOUtils.toString(in);
		
		List<String> resultFilesAsString = new ArrayList<String>();
		Map<ParameterTypes,Integer> mapCount = getMapCounter();
		
		Boolean isMeshDivisionFilled = Boolean.FALSE;
		Boolean isGeometryFilled = Boolean.FALSE;
		Boolean isLoadParametersFilled = Boolean.FALSE;
		Boolean isMaterialPropertyFilled = Boolean.FALSE;
		Boolean isSolidFilled = Boolean.FALSE;                
		Boolean isRoundWindowFiled = Boolean.FALSE;
                Boolean isDensityFilled = Boolean.FALSE;
		Boolean isExternalparametersFilled = Boolean.FALSE;
		Boolean isBasilarMembraneFiled = Boolean.FALSE;
                Boolean isAnalysisFilled = Boolean.FALSE;
                
		Map<String,CombinationHelper> combinationMap = new TreeMap<String,CombinationHelper>();
		
		int size = applyCombinationMapService(mapAttributeValues,solverConfigCreatorTO.getLoadParametersTOList(),combinationMap);
                System.err.println("size = " + size);
		for(int i=0;i<size;i++){
			StringBuilder sb = new StringBuilder();
			int count = 0;
			//MESH PART
			++count;
			String paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.MESH.getName())){
				if(mapCount.get(ParameterTypes.MESH)==BigDecimal.ZERO.intValue()){
					sb.append(paramHeader);
					MeshSetupTO meshSetupTO = solverConfigCreatorTO.getMeshSetupTO();
					sb.append(meshSetupTO.getDivisionL().intValue());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionW().intValue());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionB().intValue());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionH().intValue());
					sb.append(SPACE);
					sb.append(meshSetupTO.getDivisionh().intValue());
					sb.append("\r\n\r\n");
					isMeshDivisionFilled = Boolean.TRUE;				
				}
				mapCount.put(ParameterTypes.MESH, BigDecimal.ONE.intValue());
			}
				
                        System.err.println("1 -- " + sb.toString());
			//GEOMETRY PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");

			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.GEOMETRY.getName())){
				if(mapCount.get(ParameterTypes.GEOMETRY).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					GeometrySetupTO geometrySetupTO = solverConfigCreatorTO.getGeometrySetupTO();
					sb.append(geometrySetupTO.getLengthL());
					sb.append(SPACE);
					sb.append(geometrySetupTO.getHeigthH());
					sb.append(SPACE);
					sb.append(geometrySetupTO.getWidthW());
					sb.append(SPACE);
                                        sb.append(geometrySetupTO.getWidthBMstart());
					sb.append(SPACE);
                                        sb.append(geometrySetupTO.getWidthBMend());
					sb.append(SPACE);                                        
                                        sb.append(geometrySetupTO.getThicknesshstart());
					sb.append(SPACE);
                                        sb.append(geometrySetupTO.getThicknesshend());
					sb.append(SPACE);                                        
                                        sb.append(geometrySetupTO.getHelicotrema());                                        
					sb.append("\r\n\r\n");
					isGeometryFilled = Boolean.TRUE;				
				}
				mapCount.put(ParameterTypes.GEOMETRY, BigDecimal.ONE.intValue());
			}
                        /*
                        isGeometryFilled = Boolean.TRUE;
                        sb.append(paramHeader);
                        sb.append("3.5e+01 1.0e+00 1.0e+00 6.0e-05 6.0e-04 5.0e-06 5.e-06 0.001\n");
                        */
                        System.err.println("2 -- " + sb.toString());

			
			//LOAD PARAMETERS 
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.FREQUENCY.getName())){
				if(mapCount.get(ParameterTypes.FREQUENCY).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.FREQUENCY).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
								sb.append(valueStr +"  "+FREQUENCY_CONSTANT+"\r\n\r\n");
							}	
						isLoadParametersFilled = Boolean.TRUE;		
					}
		
					mapCount.put(ParameterTypes.FREQUENCY, BigDecimal.ONE.intValue());
				}
			}
                        System.err.println("3 -- " + sb.toString());
                        
			//MATERIAL PROPERTIES PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.FLUID.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.FLUID)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FLUID).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.FLUID).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						String value = NA;
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.FLUID).getId());
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.FLUID).getId());
						int currentIndex = 0;
						if(ch!=null && ch.getCurrentIndex()!=null){
							currentIndex = ch.getCurrentIndex();						
						}
						if(!valueList.isEmpty()){
							value = valueList.get(currentIndex);
							if(ch!=null && ch.getCurrentIndex()!=null){
								ch.setCurrentIndex(++currentIndex);						
							}
												
						}
                                                
						if(!NA.equals(value) && !"".equals(value)){
							sb.append(value + " \r\n");
						}

						isMaterialPropertyFilled = Boolean.TRUE;				
					}
					mapCount.put(ParameterTypes.FLUID, BigDecimal.ONE.intValue());
				}

			}
                        System.err.println("4 -- " + sb.toString());

			//SOLID PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.SOLID.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.SOLID)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.SOLID).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.SOLID).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.SOLID).getId());
						if(valueList!=null){
							sb.append(Util.removeListKeysFromString(valueList.toString()));
							isSolidFilled = Boolean.TRUE;										
						}
					}
					mapCount.put(ParameterTypes.SOLID, BigDecimal.ONE.intValue());
				}

			}
                        sb.append("\n");
                        
			//ROUND WINDOW PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.ROUNDWINDOW.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.ROUNDWINDOW)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.ROUNDWINDOW).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.ROUNDWINDOW).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.ROUNDWINDOW).getId());
						if(valueList!=null){
							sb.append(Util.removeListKeysFromString(valueList.toString()));
							isRoundWindowFiled = Boolean.TRUE;										
						}
					}
					mapCount.put(ParameterTypes.ROUNDWINDOW, BigDecimal.ONE.intValue());
				}

			}                        
			sb.append("\n");	

			//BASILAR MEMRBANE PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.BASILARMEMMBRANE.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.BASILARMEMMBRANE)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.BASILARMEMMBRANE).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.BASILARMEMMBRANE).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.BASILARMEMMBRANE).getId());
						if(valueList!=null){
							sb.append(Util.removeListKeysFromString(valueList.toString()));
							isBasilarMembraneFiled = Boolean.TRUE;										
						}
					}
					mapCount.put(ParameterTypes.BASILARMEMMBRANE, BigDecimal.ONE.intValue());
				}

			}                        
			sb.append("\n");                        

			//ANALYSIS PARAMETERS 
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.ANALYSIS.getName())){
				if(mapCount.get(ParameterTypes.ANALYSIS).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.ANALYSIS)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.ANALYSIS).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.ANALYSIS).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.ANALYSIS).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
                                                        
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
								sb.append(valueStr + "\r\n");
							}
                                                                
						isAnalysisFilled = Boolean.TRUE;		
					}
		
					mapCount.put(ParameterTypes.ANALYSIS, BigDecimal.ONE.intValue());
				}
			}
                        System.err.println("3 -- " + sb.toString());

			//DENSITY PARAMETERS
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.DENSITY.getName())){
				if(mapCount.get(ParameterTypes.DENSITY).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.DENSITY)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.DENSITY).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.DENSITY).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.DENSITY).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
                                                        
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
								sb.append(valueStr + "\r\n\r\n");
							}
						isDensityFilled = Boolean.TRUE;		
					}
		
					mapCount.put(ParameterTypes.DENSITY, BigDecimal.ONE.intValue());
				}
			}
                        System.err.println("3 -- " + sb.toString());                        
                        
			System.out.println("Before test: "+sb.toString().trim());                        
			Boolean isAllParametersFilled = isMeshDivisionFilled && isGeometryFilled && isLoadParametersFilled &&
					isMaterialPropertyFilled && isDensityFilled &&isSolidFilled && isRoundWindowFiled && isBasilarMembraneFiled && isAnalysisFilled; // && isExternalparametersFilled;
                        
                        System.err.println("allParams -- " + sb.toString());

                        
			if(isAllParametersFilled && !"".equals(sb.toString())){
				resultFilesAsString.add(sb.toString().trim());	
				mapCount = getMapCounter();
			}else{
				continue;
			}
		}

                System.err.println("Res: " + resultFilesAsString);
		return resultFilesAsString;
    }

    @Override
    public List<String> handleSliceModelOneCase(SolverConfigCreatorTO solverConfigCreatorTO, Map<String, List<String>> mapAttributeValues, Map<ParameterTypes, ParameterTO> mapTeplateKeysAndIds) throws Exception
    {
                InputStream in = this.getClass().getClassLoader().getResourceAsStream("input_CochleaSliceModeltemplate.txt");
		String templateStr = IOUtils.toString(in);
		
		List<String> resultFilesAsString = new ArrayList<String>();
		Map<ParameterTypes,Integer> mapCount = getMapCounter();
		
		Boolean isShouldProvidePressureFilled = Boolean.FALSE;
		Boolean isShouldProvideForceFilled = Boolean.FALSE;
		Boolean isFrequencyFilled = Boolean.FALSE;                
                Boolean isMaterialsFilled = Boolean.FALSE;
                Boolean isAnalysisFilled = Boolean.FALSE;
                
		Map<String,CombinationHelper> combinationMap = new TreeMap<String,CombinationHelper>();
		
		int size = applyCombinationMapService(mapAttributeValues,solverConfigCreatorTO.getLoadParametersTOList(),combinationMap);
                System.err.println("size = " + size);
		for(int i=0;i<size;i++){
			StringBuilder sb = new StringBuilder();
			int count = 0;
			++count;
			//LOAD PARAMETERS pressure
			String paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.SHOULD_PRESCRIBE_PRESSURE.getName())){
				if(mapCount.get(ParameterTypes.SHOULD_PRESCRIBE_PRESSURE).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.SHOULD_PRESCRIBE_PRESSURE)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.SHOULD_PRESCRIBE_PRESSURE).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.SHOULD_PRESCRIBE_PRESSURE).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.SHOULD_PRESCRIBE_PRESSURE).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
								sb.append(valueStr +"  "+FREQUENCY_CONSTANT);
							}	
						isShouldProvidePressureFilled = Boolean.TRUE;		
					}
		
					mapCount.put(ParameterTypes.SHOULD_PRESCRIBE_PRESSURE, BigDecimal.ONE.intValue());
				}
			}
                        System.err.println("3 -- " + sb.toString());
			
                        ++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.SHOULD_PRESCRIBE_FORCE.getName())){
				if(mapCount.get(ParameterTypes.SHOULD_PRESCRIBE_FORCE).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.SHOULD_PRESCRIBE_PRESSURE)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.SHOULD_PRESCRIBE_FORCE).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.SHOULD_PRESCRIBE_FORCE).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.SHOULD_PRESCRIBE_FORCE).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
								sb.append(valueStr +"  "+FREQUENCY_CONSTANT);
							}	
						isShouldProvideForceFilled = Boolean.TRUE;		
					}
		
					mapCount.put(ParameterTypes.SHOULD_PRESCRIBE_FORCE, BigDecimal.ONE.intValue());
				}
			}
                        System.err.println("3 -- " + sb.toString());

                        ++count;                        
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.SLICE_FREQUENCY.getName())){
				if(mapCount.get(ParameterTypes.SLICE_FREQUENCY).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.SLICE_FREQUENCY)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.SLICE_FREQUENCY).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.SLICE_FREQUENCY).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.SLICE_FREQUENCY).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
								sb.append(valueStr +"\r\n");
							}	
						isFrequencyFilled = Boolean.TRUE;		
					}
		
					mapCount.put(ParameterTypes.SLICE_FREQUENCY, BigDecimal.ONE.intValue());
				}
			}
                        System.err.println("3 -- " + sb.toString());
                        
			//MATERIAL PROPERTIES PART
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
                        
                        System.err.println("Where am i = " + paramHeader);
                        
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.MATERIALS.getName())){
				if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.MATERIALS)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.MATERIALS).getId()).isEmpty()){
					if(mapCount.get(ParameterTypes.MATERIALS).equals(BigDecimal.ZERO.intValue())){
						sb.append(paramHeader);
						String value = NA;
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.MATERIALS).getId());
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.MATERIALS).getId());
						int currentIndex = 0;
						if(ch!=null && ch.getCurrentIndex()!=null){
							currentIndex = ch.getCurrentIndex();						
						}
						if(!valueList.isEmpty()){
							value = valueList.get(currentIndex);
							if(ch!=null && ch.getCurrentIndex()!=null){
								ch.setCurrentIndex(++currentIndex);						
							}
												
						}
                                                
						if(!NA.equals(value) && !"".equals(value)){
							sb.append(value + " \r\n");
						}

						isMaterialsFilled = Boolean.TRUE;				
					}
					mapCount.put(ParameterTypes.MATERIALS, BigDecimal.ONE.intValue());
				}

			}
                        System.err.println("4 -- " + sb.toString());
                       

			//ANALYSIS PARAMETERS 
			++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.ANALYSIS.getName())){
				if(mapCount.get(ParameterTypes.ANALYSIS).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.ANALYSIS)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.ANALYSIS).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.ANALYSIS).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.ANALYSIS).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
                                                        
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
								sb.append(valueStr + "\r\n");
							}
                                                                
						isAnalysisFilled = Boolean.TRUE;		
					}
		
					mapCount.put(ParameterTypes.ANALYSIS, BigDecimal.ONE.intValue());
				}
			}
                        System.err.println("3 -- " + sb.toString());

                                                
			System.out.println("Before test: "+sb.toString().trim());                        
			Boolean isAllParametersFilled = isShouldProvideForceFilled && isShouldProvidePressureFilled 
                                                        && isFrequencyFilled && isMaterialsFilled && isAnalysisFilled; // && isExternalparametersFilled;
                        
                        System.err.println("allParams -- " + sb.toString());

                        
			if(isAllParametersFilled && !"".equals(sb.toString())){
				resultFilesAsString.add(sb.toString().trim());	
				mapCount = getMapCounter();
			}else{
				continue;
			}
		}

                System.err.println("Res: " + resultFilesAsString);
		return resultFilesAsString;        
    }

    private List<String> handleHeadModelCase(SolverConfigCreatorTO solverConfigCreatorTO, Map<String, List<String>> mapAttributeValues, Map<ParameterTypes, ParameterTO> mapTeplateKeysAndIds) throws IOException
    {
                InputStream in = this.getClass().getClassLoader().getResourceAsStream("input_HeadModel.txt");
		String templateStr = IOUtils.toString(in);
		
		List<String> resultFilesAsString = new ArrayList<String>();
		Map<ParameterTypes,Integer> mapCount = getMapCounter();
		
		Boolean isMaterial1Filled = Boolean.FALSE;
		Boolean isMaterial2Filled = Boolean.FALSE;
                
		Map<String,CombinationHelper> combinationMap = new TreeMap<String,CombinationHelper>();
		
		int size = applyCombinationMapService(mapAttributeValues,solverConfigCreatorTO.getLoadParametersTOList(),combinationMap);
                System.err.println("size = " + size);
		for(int i=0;i<size;i++){
			StringBuilder sb = new StringBuilder();
			int count = 0;
			++count;
			
			String paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.MATERIAL1.getName())){
				if(mapCount.get(ParameterTypes.MATERIAL1).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.MATERIAL1)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.MATERIAL1).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.MATERIAL1).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.MATERIAL1).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
                                                                valueStr = valueStr.replace(';', ' ');                                                            
								sb.append(valueStr);
							}	
						isMaterial1Filled = Boolean.TRUE;		
					}
		
					//mapCount.put(ParameterTypes.SHOULD_PRESCRIBE_PRESSURE, BigDecimal.ONE.intValue());
				}
			}
                        System.err.println("3 -- " + sb.toString());
			
                        ++count;
			paramHeader = StringUtils.substringBetween(templateStr, "#-"+(count)+"#", "#+"+(count)+"#");
			if(StringUtils.containsIgnoreCase(paramHeader,ParameterTypes.MATERIAL2.getName())){
				if(mapCount.get(ParameterTypes.MATERIAL2).equals(BigDecimal.ZERO.intValue())){
					sb.append(paramHeader);
					
					if(!mapAttributeValues.isEmpty() && mapTeplateKeysAndIds.get(ParameterTypes.MATERIAL2)!=null && !mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.MATERIAL2).getId()).isEmpty()){
						List<String> valueList = mapAttributeValues.get(mapTeplateKeysAndIds.get(ParameterTypes.MATERIAL2).getId());
						String valueStr = NA;
						CombinationHelper ch = combinationMap.get(mapTeplateKeysAndIds.get(ParameterTypes.MATERIAL2).getId());
						int currentIndex = 0;
							if(ch!=null && ch.getCurrentIndex()!=null){
								currentIndex = ch.getCurrentIndex();						
							}
							if(!valueList.isEmpty()){
								valueStr = valueList.get(currentIndex);
								if(ch!=null && ch.getCurrentIndex()!=null){
									ch.setCurrentIndex(++currentIndex);						
								}
							}
							if(!NA.equals(valueStr) && !"".equals(valueStr)){
                                                                valueStr = valueStr.replace(';', ' ');
								sb.append(valueStr);
							}	
						isMaterial2Filled = Boolean.TRUE;		
					}
		
				}
			}
                        System.err.println("3 -- " + sb.toString());

                                                
			System.out.println("Before test: "+sb.toString().trim());                        
			Boolean isAllParametersFilled = isMaterial1Filled && isMaterial2Filled; // && isExternalparametersFilled;
                        
                        System.err.println("allParams -- " + sb.toString());

                        
			if(isAllParametersFilled && !"".equals(sb.toString())){
				resultFilesAsString.add(sb.toString().trim());	
				mapCount = getMapCounter();
			}else{
				continue;
			}
		}

                System.err.println("Res: " + resultFilesAsString);
		return resultFilesAsString;        
        
    }

}
