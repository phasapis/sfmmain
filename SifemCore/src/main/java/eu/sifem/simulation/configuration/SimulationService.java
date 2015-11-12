package eu.sifem.simulation.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.sifem.dao.mongo.MessageFileDAO;
import eu.sifem.model.to.AsyncProcessRunMessageTO;
import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;
import eu.sifem.model.to.DataSetHashCacheTO;
import eu.sifem.model.to.DataSetTO;
import eu.sifem.model.to.ExtractTheCacheSyntaxTO;
import eu.sifem.model.to.MessageFileTO;
import eu.sifem.model.to.ParameterTO;
import eu.sifem.model.to.ProcessTO;
import eu.sifem.model.to.ProjectSimulationTO;
import eu.sifem.model.to.SessionIndexTO;
import eu.sifem.model.to.SimulationInstanceTO;
import eu.sifem.model.to.SolverTO;
import eu.sifem.model.to.TransformationTO;
import eu.sifem.service.IJenaModelService;
import eu.sifem.service.IPakSolverControlerService;
import eu.sifem.service.IProcessRunnableService;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.ISimulationSenderService;
import eu.sifem.service.ISimulationService;
import eu.sifem.service.ITransformationService;
import eu.sifem.service.IVisualizationService;
import eu.sifem.service.dao.IConfigFileDAOService;
import eu.sifem.service.dao.IDatAndUnvSolverDAOService;
import eu.sifem.service.dao.IParameterDAOService;
import eu.sifem.service.dao.ISimulationDAOService;
import eu.sifem.service.dao.ISolverDAOService;
import eu.sifem.service.dao.ITransformationDAOService;
import eu.sifem.utils.ConceptualConstant;

/**
 * 
 * @author jbjares
 * 
 */
@Service(value="simulationService")
public class SimulationService implements ISimulationService{
	
	private static final String UTF_8 = "UTF-8";

	private static final String UNDERLINE = "_";

	private static final String HTTP_WWW_SIFEMONTOLOGIES_COM_ONTOLOGIES_BOX_MODEL = "http://www.sifemontologies.com/ontologies/BoxModel#";

	@Autowired
	private IResourceInjectionService resourceInjectionService;
	
	@Autowired
	private IJenaModelService jenaModel;
	
	@Autowired
	private IVisualizationService visualization; 
	
	@Autowired
	private IParameterDAOService parameterDAOService;
	
	@Autowired
	private ISimulationDAOService simulationDAO;
	
	
	@Autowired
	private ISolverDAOService solverDAO;
	
	@Autowired
	private ITransformationDAOService transformationDAO;
	
	@Autowired
	private ITransformationService transformationService;

	@Autowired
	private MessageFileDAO messageFileDAO;

	@Autowired
	private IConfigFileDAOService configFileDAO;
	
	@Autowired
	private IDatAndUnvSolverDAOService datAndUnvSolverDAO;
	
	@Autowired
	private ISimulationSenderService simulationSenderService;
	
	@Autowired
	private IPakSolverControlerService pakSolverControlerService;
	
	@Autowired
	private IProcessRunnableService iprocessRunnableService;
	
	@Override
	public void runAsync(SimulationInstanceTO simulationInstanceTO,SessionIndexTO sessionIndexTO) throws Exception {
		AsyncProcessRunMessageTO asyncProcessRunMessageTO = new AsyncProcessRunMessageTO();
		asyncProcessRunMessageTO.setSessionIndexTO(sessionIndexTO);
		asyncProcessRunMessageTO.setSimulationInstanceTO(simulationInstanceTO);
		//simulationSenderService.sendMessageService(asyncProcessRunMessageTO);
		
		//Running on sync mode.
		iprocessRunnableService.startSync(asyncProcessRunMessageTO.getSimulationInstanceTO(),asyncProcessRunMessageTO.getSessionIndexTO(), null);
		
		ProcessTO sifemProcess = new ProcessTO();
		sifemProcess.setProjectName(simulationInstanceTO.getProjectName());
		sifemProcess.setSimulationName(simulationInstanceTO.getSimulationName());
		sifemProcess.setIsNotStartedPhase(Boolean.FALSE);
		sifemProcess.setIsInQueuePhase(Boolean.TRUE);
		pakSolverControlerService.saveOrUpdateProcessStatus(sifemProcess);
	}
	
	@Override
	public List<AsyncTripleStoreInsertMessageTO> runSync(SimulationInstanceTO simulationInstanceTO,SessionIndexTO sessionIndexTO, String commandLineArgument) throws Exception {
		AsyncProcessRunMessageTO asyncProcessRunMessageTO = new AsyncProcessRunMessageTO();
		asyncProcessRunMessageTO.setSessionIndexTO(sessionIndexTO);
		asyncProcessRunMessageTO.setSimulationInstanceTO(simulationInstanceTO);
		//simulationSenderService.sendMessageService(asyncProcessRunMessageTO);
		
                System.err.println(" ---- about to start Sync");
		//Running on sync mode.
		List<AsyncTripleStoreInsertMessageTO> asyncTripleStoreInsertMessageTOList = 
				iprocessRunnableService.startSync(asyncProcessRunMessageTO.getSimulationInstanceTO(),asyncProcessRunMessageTO.getSessionIndexTO(), commandLineArgument);
		
		ProcessTO sifemProcess = new ProcessTO();
		sifemProcess.setProjectName(simulationInstanceTO.getProjectName());
		sifemProcess.setSimulationName(simulationInstanceTO.getSimulationName());
		sifemProcess.setIsNotStartedPhase(Boolean.FALSE);
		sifemProcess.setIsInQueuePhase(Boolean.TRUE);
		pakSolverControlerService.saveOrUpdateProcessStatus(sifemProcess);
		return asyncTripleStoreInsertMessageTOList;
	}
	
	
	@Override
	public Boolean executeSimulationService(List<File> semantificationFiles, SimulationInstanceTO simulationInstanceTO, TransformationTO transformationTO) throws Exception {
		ExtractTheCacheSyntaxTO extractTheCacheSyntaxMessage = new ExtractTheCacheSyntaxTO();
		fillMessage(extractTheCacheSyntaxMessage,semantificationFiles,simulationInstanceTO);
		extractTheCacheSyntaxMessage.setDataSetTO(new DataSetTO(new DataSetHashCacheTO(simulationInstanceTO.getProjectName(),simulationInstanceTO.getSimulationName(),simulationInstanceTO.getxName(),simulationInstanceTO.getyName(),"")
		,HTTP_WWW_SIFEMONTOLOGIES_COM_ONTOLOGIES_BOX_MODEL+simulationInstanceTO.getProjectName()+UNDERLINE+simulationInstanceTO.getSimulationName()));
		extractTheCacheSyntaxMessage.setTransformationTO(transformationTO);
		extractTheCacheSyntaxMessage.getDataSetTO().getCache().setWorkspace(simulationInstanceTO.getWorkspacebasePath());
		createCache(extractTheCacheSyntaxMessage,semantificationFiles);
		
		return Boolean.FALSE;
	}
	
	private void createCache(ExtractTheCacheSyntaxTO extractTheCacheSyntaxTO, List<File> semantificationFiles) throws Exception {
		DataSetHashCacheTO cache = extractTheCacheSyntaxTO.getDataSetTO().getCache();
		TransformationTO transformation = extractTheCacheSyntaxTO.getTransformationTO();
		InputStream scriptAsInputStream = transformationDAO.findScriptByTransformationName(transformation.getName());
		InputStream queryAsInputStream = transformationDAO.findQueryByTransformationName(transformation.getName());
				
//		Map<String, List<Double>> result = getTransformationGraph(extractTheCacheSyntaxTO,transformation,scriptAsInputStream,queryAsInputStream);
		execute(extractTheCacheSyntaxTO,scriptAsInputStream,queryAsInputStream,semantificationFiles,cache);
	}
	
	private void execute(ExtractTheCacheSyntaxTO extractTheCacheSyntaxTO,InputStream scriptAsInputStream, InputStream queryAsInputStream, List<File> semantificationFiles, DataSetHashCacheTO cache) throws Exception {
		
		if(queryAsInputStream==null){
			throw new RuntimeException("Fail on locate query stream for the given Transformation: "+extractTheCacheSyntaxTO.getTransformationTO().getName()+" . " );
		}

		String sparqlView = IOUtils.toString(queryAsInputStream, UTF_8);
		visualization.retrieveDataViewService(extractTheCacheSyntaxTO.getDataSetTO(), sparqlView,semantificationFiles,cache.getWorkspace() , cache.getProjectName(), cache.getSimulationName());
		
		
	}

	private void fillMessage(ExtractTheCacheSyntaxTO extractTheCacheSyntaxMessage,List<File> semantificationFiles,SimulationInstanceTO simulationInstanceTO) throws Exception {
		extractTheCacheSyntaxMessage.setSemantificatedFilesID(new ArrayList<MessageFileTO>());
		for(File file:semantificationFiles){
			FileInputStream fis = new FileInputStream(file);
			MessageFileTO messageFile = new MessageFileTO();
			messageFile.setCanonicalPath(file.getCanonicalPath());
			messageFile.setFileName(file.getName());
			messageFile.setFisMessageFile(fis);
			messageFile.setMsgName(ExtractTheCacheSyntaxTO.class.getCanonicalName());
			String id = messageFileDAO.saveMessageFile(messageFile);
			messageFile.set_id(new ObjectId(id));
			extractTheCacheSyntaxMessage.getSemantificatedFilesID().add(messageFile);
		}
	}
	
	
	public List<String> getGraphTypesService() throws Exception {
		jenaModel.importOntologyService(resourceInjectionService.getSimOntologyPath().getFile().getPath(), null);
		return jenaModel.getSubclassOfService(ConceptualConstant.SIM_NAME_SPACE,
				ConceptualConstant.GRAPH_TYPE_CLASS);
	}

	
	
	public List<String> getSolverListService() throws Exception {
		List<String> result = new ArrayList<String>();
		for(SolverTO solver:solverDAO.findAll()){
			result.add(solver.getName());
		}
		return result;
	}

	
	public List<ParameterTO> findAllParametersService() throws Exception {
		return parameterDAOService.findAllParameters();
	}
	
	
	public List<String> getSolverMethodsService(String name, String type) throws Exception {
		return solverDAO.findMethodByName(name);
	}
	
	
	
	public List<String> getNumericalMethodPropService() throws IOException {
		
		List<String> lstOfNumParam=new ArrayList<String>();
		jenaModel.importOntologyService(resourceInjectionService.getFemOntologyPath().getFile().getPath(), null);
		List<String> lstOfParams= jenaModel.getPropertiesOfService(ConceptualConstant.FEM_NAME_SPACE,
				ConceptualConstant.NUMMETHOD_TYPE_CLASS);
		StringTokenizer tokens=new StringTokenizer(ConceptualConstant.FILTER_ATTR_NUM,",");
		while(tokens.hasMoreTokens()){
			String token=tokens.nextToken();
			if(lstOfParams.contains(token)){
				lstOfNumParam.add(token.substring(3,token.length()));
			}
		}
		return lstOfNumParam;
	}
	




	@Override
	public void insertService(ProjectSimulationTO projectSimulationTO) throws Exception {
		simulationDAO.insert(projectSimulationTO);
	}



	@Override
	public List<String> getNumMethodAttributesService(String solverName,String solverType) throws Exception {
		return solverDAO.findAllByType(solverType).get(0).getMethods();
	}



	@Override
	public List<String> getSolverTypelistService(String solverName) throws Exception {
		return solverDAO.findTypeByName(solverName);
	}



	@Override
	public List<String> findAllParametersByTransformationNameService(String selectedTransformationName) throws Exception {
		List<String> result = transformationDAO.findAllParametersByTransformationName(selectedTransformationName);
		return result;
	}
	
	
	//TODO waiting for queries quick fix that asked to Kartik.
	@Override
	public List<String> findAllTempParameters() throws Exception {
		List<String> result = transformationDAO.findAllTempParameters();
		return result;
	}



	@Override
	public List<TransformationTO> findAllTransformationsBySimulationNameService(String projectName) throws Exception {
		return transformationDAO.findAllByProjectName(projectName);
	}


	@Override
	public void updateService(ProjectSimulationTO projectSimulationTO) throws Exception {
		simulationDAO.update(projectSimulationTO);
	}



	@Override
	public ProjectSimulationTO findByNameService(String name) throws Exception {
		return simulationDAO.findByName(name);
	}



	@Override
	public List<String> getParameterNamesService() throws Exception {
		List<String> result = new ArrayList<String>();
		for(ParameterTO parameter:findAllParametersService()){
			result.add(parameter.getName());
		}
		return result;
	}

	@Override
	public void cleanCacheEnvironmentService(SimulationInstanceTO simulationInstanceTO) {
		configFileDAO.removeAll(simulationInstanceTO.getProjectName(),simulationInstanceTO.getSimulationName());	
		datAndUnvSolverDAO.removeAll(simulationInstanceTO.getProjectName(),simulationInstanceTO.getSimulationName());	
	}



	
}
