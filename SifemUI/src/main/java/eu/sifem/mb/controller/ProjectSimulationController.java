package eu.sifem.mb.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.sifem.mb.entitybean.BoundaryInternalConditionsEB;
import eu.sifem.mb.entitybean.DataAnalysisValidationRulesEB;
import eu.sifem.mb.entitybean.GeometrySetupEB;
import eu.sifem.mb.entitybean.LoadParametersEB;
import eu.sifem.mb.entitybean.MaterialPropertyEB;
import eu.sifem.mb.entitybean.MeshSetupEB;
import eu.sifem.mb.entitybean.ProjectSimulationEB;
import eu.sifem.mb.entitybean.SolverSetupEB;
import eu.sifem.mb.entitybean.TransformationEB;
import eu.sifem.mb.entitybean.VisualisationOutputEB;
import eu.sifem.model.helper.Constants;
import eu.sifem.model.helper.MessengerCollectorHelper;
import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;
import eu.sifem.model.to.ExternalLoadParametersTO;
import eu.sifem.model.to.ParameterTO;
import eu.sifem.model.to.ProcessTO;
import eu.sifem.model.to.ProjectSimulationTO;
import eu.sifem.model.to.SessionIndexTO;
import eu.sifem.model.to.SimulationInstanceTO;
import eu.sifem.model.to.SolverConfigCreatorTO;
import eu.sifem.model.to.TransformationTO;
import eu.sifem.service.IIndexingService;
import eu.sifem.service.IPakSolverControlerService;
import eu.sifem.service.ISimulationService;
import eu.sifem.service.ISolverConfigCreatorService;
import eu.sifem.service.ITransformationService;


/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "projectSimulationController")
@ViewScoped
public class ProjectSimulationController extends GenericMB {




	private static final long serialVersionUID = -7041575044935576065L;



	@ManagedProperty(value = "#{projectSimulationEB}")
	private ProjectSimulationEB projectSimulationEB;

	@ManagedProperty(value = "#{dataAnalysisValidationRulesEB}")
	private DataAnalysisValidationRulesEB dataAnalysisValidationRulesEB;
	
	@ManagedProperty(value = "#{solverSetupEB}")
	private SolverSetupEB solverSetupEB;


	@ManagedProperty(value = "#{materialPropertyEB}")
	private MaterialPropertyEB materialPropertyEB;

	@ManagedProperty(value = "#{solverConfigCreator}")
	private ISolverConfigCreatorService solverConfigCreator;

	@ManagedProperty(value = "#{boundaryInternalConditionsEB}")
	private BoundaryInternalConditionsEB boundaryInternalConditionsEB;

	@ManagedProperty(value = "#{meshSetupEB}")
	private MeshSetupEB meshSetupEB;

	@ManagedProperty(value = "#{geometrySetupEB}")
	private GeometrySetupEB geometrySetupEB;

	@ManagedProperty(value = "#{loadParametersEB}")
	private LoadParametersEB loadParametersEB;

	@ManagedProperty(value = "#{pakSolverControlerService}")
	private IPakSolverControlerService pakSolverControlerService;
	
	@ManagedProperty(value = "#{simulationService}")
	private ISimulationService simulationService;
	
	@ManagedProperty(value = "#{transformationEB}")
	private TransformationEB transformationEB;
	
	@ManagedProperty(value = "#{transformation}")
	private ITransformationService transformation;
	
	@ManagedProperty(value = "#{indexingService}")
	private IIndexingService indexingService;


	@ManagedProperty(value="#{visualisationOutputEB}")
	private VisualisationOutputEB visualisationOutputEB;
	
	
	private Map<String, String> paramsList = new TreeMap<String, String>();
	
	private Boolean renderPogressBar = Boolean.FALSE;


	public void throwException(){
		try {
			throw new RuntimeException("Error!");
		} catch (Exception e) {
			addExceptionMessage(e);
		}
		
	}

	public void init() {
		try {
			String cmd = getRequestParameter(Constants.Global.CMD);
			if (Constants.Global.INIT.equals(cmd)) {
				removeMessage(Constants.Global.MAIN_DESK_BOARD);
				addInfoMessage("Welcome!",
						"Please try File, New Project to Start a Simulation.");
				if (projectSimulationEB != null) {
					Object ProjectSimulationTOObj = getSessionBean("projectSimulationEB");
					if (ProjectSimulationTOObj != null && ProjectSimulationTOObj instanceof ProjectSimulationEB) {
						((ProjectSimulationEB)ProjectSimulationTOObj).setProjectSimulationTO(new ProjectSimulationTO());
						putSessionBean("projectSimulationEB",ProjectSimulationTOObj);
						
					}
				}
				fillListOfParams();	
			}

		} catch (Exception e) {
			addExceptionMessage(e);
		}
		
	}
	
	public void showStatusProcesses(){
		System.out.println("showStatusProcesses...");
	}

	public void createNewProjectActionListener() {
		
		try {
			if (projectSimulationEB != null) {
				//TODO FIX After presentation
				String projectName = null;
//				String workspaceLocation = "/usr/local/SifemResourceFiles/workspace/";
				if(projectSimulationEB!=null && projectSimulationEB.getProjectSimulationTO()!=null && 
						projectSimulationEB.getProjectSimulationTO().getProjectName()!=null && !"".equals(projectSimulationEB.getProjectSimulationTO().getProjectName())){
					projectName = projectSimulationEB.getProjectSimulationTO().getProjectName();					
				}else{
					addErrorMessage("ERROR","Project name not defined!");
				}
				
                                System.err.println(" ---- createNewProjectActionListener" + projectSimulationEB.getProjectSimulationTO().getSimulationName());
                                
				//TODO Deprecating..
//				if(projectSimulationEB!=null && projectSimulationEB.getProjectSimulationTO()!=null && 
//						projectSimulationEB.getProjectSimulationTO().getWorkspace()!=null && !"".equals(projectSimulationEB.getProjectSimulationTO().getWorkspace())){
//					workspaceLocation = projectSimulationEB.getProjectSimulationTO().getWorkspace();
//				}
				
				//FIXME After presentation
				//workspaceLocation = workspaceLocation+"\\"+projectName;
//				workspaceLocation = "/usr/local/SifemResourceFiles/workspace/TheOne/";
				
				//File workspaceFile = new File(workspaceLocation);
				ProjectSimulationTO simulation = simulationService.findByNameService(projectName);
				
				if(simulation==null){
					
				}
				transformationEB.getTransformationTO().setProjectName(projectName);
				List<TransformationTO> transformations = transformation.findAllByProjectNameService(projectName);
				ProjectSimulationTO projectSimulationTO  = new ProjectSimulationTO();
				projectSimulationTO.setProjectName(projectName);
				
				//TODO Deprecating..
				//projectSimulationTO.setWorkspace(workspaceLocation);
				projectSimulationTO.setTransformations(null);
				setCurrentSimulationName(projectName);
				simulationService.insertService(projectSimulationTO);
				if(transformations!=null && !transformations.isEmpty()){
					projectSimulationEB.getProjectSimulationTO().setTransformations(transformations);
				}

				if (projectName != null || !"".equals(projectName)) {
					addInfoMessage("Good!",
							"Try to configure \"Material Property\" and \"Mesh\". Then you can create and run this new simulation through \"Save All\" and \"Run\".");
					projectSimulationEB.getProjectSimulationTO().setProjectName(projectName);
					
					//TODO Deprecating..
//					projectSimulationEB.getProjectSimulationTO().setWorkspace(workspaceLocation);
					return;
				}
				
				renderPogressBar = Boolean.TRUE;
			}
		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}


	public void run() {
		try {
			renderPogressBar = Boolean.TRUE;
			SimulationInstanceTO simulationInstanceTO = new SimulationInstanceTO();
			
			String projectName = projectSimulationEB.getProjectSimulationTO().getProjectName();
			String simulationName = projectSimulationEB.getProjectSimulationTO().getSimulationName();
			
                        System.out.println(" --- simulationName upon run() = " + simulationName);
                        
			//TODO Deprecating..
			//String workspaceName = projectSimulationEB.getProjectSimulationTO().getWorkspace();
			simulationInstanceTO.setProjectName(projectName);
			simulationInstanceTO.setSimulationName(simulationName);
			
			//TODO Deprecating..
			//simulationInstanceTO.setWorkspacebasePath(workspaceName);
			
			ProcessTO sifemProcess = new ProcessTO();
			sifemProcess.setProjectName(projectName);
			sifemProcess.setSimulationName(simulationName);
			
			//TODO Deprecating..
			//sifemProcess.setWorkspaceName(workspaceName);
			sifemProcess.setIsNotStartedPhase(Boolean.TRUE);
			pakSolverControlerService.saveOrUpdateProcessStatus(sifemProcess);
			
			SessionIndexTO sessionIndexTO = (SessionIndexTO) getSession().getAttribute(CONFIG_SESSION_OBJ);
			

			List<AsyncTripleStoreInsertMessageTO> asyncTripleStoreInsertMessageTOList = 
					simulationService.runSync(simulationInstanceTO,sessionIndexTO, meshSetupEB.getMeshSetupTO().getCommandLineArgument());
			
			//PROJECT_SESSION_OBJ <- "projectStreamObj"
			//getSession().setAttribute(PROJECT_SESSION_OBJ+"_"+projectName+"_"+simulationName,asyncTripleStoreInsertMessageTOList);
			indexingService.indexStreamService(asyncTripleStoreInsertMessageTOList,projectName,simulationName);
			
			if(sessionIndexTO==null){
				addErrorMessage("ERROR","Session Expired!");
			}

			
			

			
		} catch (Exception e) {
			addExceptionMessage(e);
		}
				
		
	}
	
	private List<InputStream> retrieveSemantificationFiles(List<AsyncTripleStoreInsertMessageTO> asyncTripleStoreInsertMessageList) {
		List<InputStream> result = new LinkedList<InputStream>();
		for(AsyncTripleStoreInsertMessageTO asyncTripleStoreInsertMessageTO: asyncTripleStoreInsertMessageList){
			result.addAll(asyncTripleStoreInsertMessageTO.getSemantificationFiles());
		}
		return result;
	}

	private void cleanCacheEnvironment(SimulationInstanceTO simulationInstanceTO) {
		simulationService.cleanCacheEnvironmentService(simulationInstanceTO);
		
	}

	private TransformationTO validateTransformationOrQueryView(TransformationEB transformationEB) throws Exception {
		List<String> inexistentTransformationNameList = new ArrayList<String>();
		List<TransformationTO> transformationWithoutNecessaryDataList = new ArrayList<TransformationTO>();
		List<TransformationTO> transformationList = new ArrayList<TransformationTO>();
		for(String transformationName: transformationEB.getTransformationsTarget()){
			TransformationTO transformationTO = transformation.findByNameService(transformationName);
			
			if(transformationTO==null){	
				inexistentTransformationNameList.add(transformationName);
				continue;
			}

			if(!isPersistedTransformationDataIsCompleted(transformationTO)){
				transformationWithoutNecessaryDataList.add(transformationTO);
				continue;
			}
			
			transformationList.add(transformationTO);
			continue;

		}
		if(!inexistentTransformationNameList.isEmpty()){
			//TODO return a message to final user about this warn!
			return null;
		}
		if(!transformationWithoutNecessaryDataList.isEmpty()){
			//TODO return a message to final user about this warn!
			return null;
		}
		if(transformationList.isEmpty()){
			//TODO return a message to final user about this warn!
			return null;
		}
		if(transformationList.size()>1){
			//TODO return a message to final user about this warn!
			return null;
		}
		
		for(TransformationTO transformation:transformationList){
			if(!transformation.getIsInUse()){
				continue;
			}
			return transformation;
		}
		
		//TODO return a message to final user about this warn!
		return null;
	}
	


	private boolean isPersistedTransformationDataIsCompleted(TransformationTO transformationTO) {
		
		if(transformationTO.getName()==null || "".equals(transformationTO.getName())){
			return Boolean.FALSE;
		}
		
		if(transformationTO.getParameters()==null || transformationTO.getParameters().isEmpty()){
			return Boolean.FALSE;
		}
		
		if(transformationTO.getProjectName()==null || "".equals(transformationTO.getProjectName())){
			return Boolean.FALSE;
		}
		
		if(transformationTO.getQueryByteArrayID()==null || "".equals(transformationTO.getQueryByteArrayID())){
			return Boolean.FALSE;
		}

		//script's transformation is optional. Other attributes transients.
//		if(transformationTO.getScriptByteArrayID()==null || "".equals(transformationTO.getScriptByteArrayID())){
//			return Boolean.FALSE;
//		}

		return Boolean.TRUE;
	}

	private Boolean isValidTransformationTarget(List<String> transformationsTarget) {
		if(transformationsTarget!=null && transformationsTarget.size()>=0 && !transformationsTarget.isEmpty()){
			return Boolean.TRUE;
		}
		//TODO return a message to final user about this warn!
		return Boolean.FALSE;
		
	}



	public void save() {
		try {
			if (solverSetupEB != null && solverSetupEB.getSolverTO().getName() != null) {
				String simulationName = projectSimulationEB.getProjectSimulationTO().getSimulationName();
                                System.err.println(" ----- " + simulationName);
				String projectName = projectSimulationEB.getProjectSimulationTO().getProjectName();
				
				SolverConfigCreatorTO solverConfigCreatorTO = new SolverConfigCreatorTO();
				
				solverConfigCreatorTO.setSolverName(solverSetupEB.getSolverTO().getName());
				solverConfigCreatorTO.setMeshSetupTO(meshSetupEB.getMeshSetupTO());
				solverConfigCreatorTO.setGeometrySetupTO(geometrySetupEB.getGeometrySetupTO());
				solverConfigCreatorTO.setLoadParametersTOList(new ArrayList<ParameterTO>());
				solverConfigCreatorTO.getLoadParametersTOList().addAll(loadParametersEB.getLoadParametersTOList());
				solverConfigCreatorTO.setExternalLoadParametersTO(new ExternalLoadParametersTO());
				solverConfigCreatorTO.setSimulationName(simulationName);
				solverConfigCreatorTO.setProjectName(projectName);

				projectName = projectSimulationEB.getProjectSimulationTO().getProjectName();
				SimulationInstanceTO simulationInstanceTO = new SimulationInstanceTO();
				simulationInstanceTO.setProjectName(projectName);
				simulationInstanceTO.setSimulationName(simulationName);
				
                                System.err.println(" --- " + (solverConfigCreatorTO==null) + " " + (projectName==null));
				String configSessionID = solverConfigCreator.configFileCreationService(solverConfigCreatorTO,projectName);
				
				if(configSessionID==null || "".equals(configSessionID)){
					addWarnMessage("WARN","The requested operation was aborted due an unexpected attribute value. Please review \"Material Property\" and \"Mesh\" Config sections.");
					return;
				}
				SessionIndexTO sessionIndexTO = (SessionIndexTO) getSession().getAttribute(CONFIG_SESSION_OBJ);
				
				List<String> cfgSessionIdList = new ArrayList<String>();
				cfgSessionIdList.add(configSessionID);
				if(sessionIndexTO!=null){
					sessionIndexTO.setCfgSessionIdList(cfgSessionIdList);
					getSession().setAttribute(CONFIG_SESSION_OBJ, sessionIndexTO);
				}else{
					sessionIndexTO = new SessionIndexTO();
					sessionIndexTO.setCfgSessionIdList(cfgSessionIdList);
					getSession().setAttribute(CONFIG_SESSION_OBJ, sessionIndexTO);
				}
				
				System.out.println("save DONE -- CONFIG_SESSION_OBJ ID: "+configSessionID);
                                
				addInfoMessage("Done!","Project saved with successful!");
				
				
				//TODO Deprecating..
//				if(transformationEB!=null && transformationEB.getTransformationTO()!=null && 
//						transformationEB.getTransformationsTarget()!=null && !transformationEB.getTransformationsTarget().isEmpty()){
//					if(isValidTransformationTarget(transformationEB.getTransformationsTarget())){
//						TransformationTO transformationTO = validateTransformationOrQueryView(transformationEB);
//						if(transformationTO!=null){
//							ProjectSimulationTO projectSimulationTO  = new ProjectSimulationTO();
//							projectSimulationTO.setWorkspace(workspace);
//							projectSimulationTO.getTransformations().add(transformationTO);
//							setCurrentSimulationName(simulationInstanceTO.getSimulationName());
//							
//							String namedGraph = "";
//							transformationTO.getNamedGraphs().clear();
//							for(int i=0;i<instanceNumber;i++){
//								namedGraph = "http://www.sifemontologies.com/ontologies/"+
//										projectName+"_"+simulationName+"_instance_"+i;
//								transformationTO.getNamedGraphs().add(namedGraph);
//							}
//							transformationTO.setSimulationName(simulationName);
//							transformationTO.setProjectName(projectName);
//							transformation.saveOrUpdateService(transformationTO, workspace,null);
//						}
//					}
//				}

		}

		} catch (Exception e) {
			addExceptionMessage(e);
		}
		
	}

	public ProjectSimulationEB getProjectSimulationEB() {
		return projectSimulationEB;
	}

	public void setProjectSimulationEB(ProjectSimulationEB projectSimulationEB) {
		this.projectSimulationEB = projectSimulationEB;
	}

	
	public void fillListOfParams()  {
		try {
			List<String> lstOfParams = simulationService.getParameterNamesService();
			for (String eachObject : lstOfParams) {
				paramsList.put(eachObject, eachObject);
			}
			
//			String projectName = projectSimulationEB.getProjectSimulationTO().getProjectName();
//			if(projectName==null || "".equals(projectName)){
//				return;
//			}
			
			List<TransformationTO> transformationTOList = transformation.findAllService();
			for(TransformationTO to:transformationTOList){
				if(to.getIsInUse()==null){
					to.setIsInUse(Boolean.FALSE);
					transformation.saveOrUpdateService(to, null,null);
				}
				if(to.getIsInUse().booleanValue()){
					for(String parameter:to.getParameters()){
						paramsList.put(parameter,parameter);
					}
				}
			}
	//TODO insertcode here
		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}

	//TODO remove
	public void garbageMessenger(){
		try {
			String garbageMessengerHelperStr =  (String) getSession().getServletContext().getAttribute(Constants.Global.WEBSESSION_COLLECTOR_BATCH_MSG);
			System.out.println(garbageMessengerHelperStr);
			Gson gson = new GsonBuilder().create();
			MessengerCollectorHelper garbageMessengerHelper = gson.fromJson(garbageMessengerHelperStr, MessengerCollectorHelper.class);
			if(garbageMessengerHelperStr==null){return;}
			System.out.println(garbageMessengerHelperStr);
			
			//TODO just show the message for the properly simulation session
//		String currentSimulationNameStr = (String) getSession().getAttribute(Constants.Global.WEBSESSION_SIMULATION_NAME);
//		if(!StringUtils.equals(currentSimulationNameStr,garbageMessengerHelper.getSimulationName())){
//			return;
//		}
			addInfoMessage(garbageMessengerHelper.getType(),garbageMessengerHelper.getMessage(),Constants.Global.BATCH_DESK_BOARD);
			//getSession().getServletContext().removeAttribute(Constants.Global.WEBSESSION_COLLECTOR_BATCH_MSG);
			//TODO insertcode here
		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}


	public DataAnalysisValidationRulesEB getDataAnalysisValidationRulesEB() {
		return dataAnalysisValidationRulesEB;
	}

	public void setDataAnalysisValidationRulesEB(
			DataAnalysisValidationRulesEB dataAnalysisValidationRulesEB) {
		this.dataAnalysisValidationRulesEB = dataAnalysisValidationRulesEB;
	}

	public SolverSetupEB getSolverSetupEB() {
		return solverSetupEB;
	}

	public void setSolverSetupEB(SolverSetupEB solverSetupEB) {
		this.solverSetupEB = solverSetupEB;
	}



	public MaterialPropertyEB getMaterialPropertyEB() {
		return materialPropertyEB;
	}

	public void setMaterialPropertyEB(MaterialPropertyEB materialPropertyEB) {
		this.materialPropertyEB = materialPropertyEB;
	}

	public ISolverConfigCreatorService getSolverConfigCreator() {
		return solverConfigCreator;
	}

	public void setSolverConfigCreator(
			ISolverConfigCreatorService solverConfigCreator) {
		this.solverConfigCreator = solverConfigCreator;
	}

	public BoundaryInternalConditionsEB getBoundaryInternalConditionsEB() {
		return boundaryInternalConditionsEB;
	}

	public void setBoundaryInternalConditionsEB(
			BoundaryInternalConditionsEB boundaryInternalConditionsEB) {
		this.boundaryInternalConditionsEB = boundaryInternalConditionsEB;
	}

	public MeshSetupEB getMeshSetupEB() {
		return meshSetupEB;
	}

	public void setMeshSetupEB(MeshSetupEB meshSetupEB) {
		this.meshSetupEB = meshSetupEB;
	}

	public GeometrySetupEB getGeometrySetupEB() {
		return geometrySetupEB;
	}

	public void setGeometrySetupEB(GeometrySetupEB geometrySetupEB) {
		this.geometrySetupEB = geometrySetupEB;
	}

	public LoadParametersEB getLoadParametersEB() {
		return loadParametersEB;
	}

	public void setLoadParametersEB(LoadParametersEB loadParametersEB) {
		this.loadParametersEB = loadParametersEB;
	}

	public IPakSolverControlerService getPakSolverControlerService() {
		return pakSolverControlerService;
	}

	public void setPakSolverControlerService(
			IPakSolverControlerService pakSolverControlerService) {
		this.pakSolverControlerService = pakSolverControlerService;
	}

	public ISimulationService getSimulationService() {
		return simulationService;
	}

	public void setSimulationService(ISimulationService simulationService) {
		this.simulationService = simulationService;
	}

	public TransformationEB getTransformationEB() {
		return transformationEB;
	}

	public void setTransformationEB(TransformationEB transformationEB) {
		this.transformationEB = transformationEB;
	}

	public ITransformationService getTransformation() {
		return transformation;
	}

	public void setTransformation(ITransformationService transformation) {
		this.transformation = transformation;
	}
	
	public Map<String, String> getParamsList() throws Exception {
		return paramsList;
	}

	public void setParamsList(Map<String, String> paramsList) {
		this.paramsList = paramsList;
	}

	public Boolean getRenderPogressBar() {
		return renderPogressBar;
	}

	public void setRenderPogressBar(Boolean renderPogressBar) {
		this.renderPogressBar = renderPogressBar;
	}

	public VisualisationOutputEB getVisualisationOutputEB() {
		return visualisationOutputEB;
	}

	public void setVisualisationOutputEB(VisualisationOutputEB visualisationOutputEB) {
		this.visualisationOutputEB = visualisationOutputEB;
	}

	public IIndexingService getIndexingService() {
		return indexingService;
	}

	public void setIndexingService(IIndexingService indexingService) {
		this.indexingService = indexingService;
	}


}