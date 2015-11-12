package eu.sifem.mb.controller;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.sifem.mb.entitybean.BoundaryInternalConditionsEB;
import eu.sifem.mb.entitybean.LoadParametersEB;
import eu.sifem.mb.entitybean.ProjectSimulationEB;
import eu.sifem.mb.entitybean.SolverSetupEB;
import eu.sifem.mb.entitybean.TransformationEB;
import eu.sifem.mb.entitybean.VisualisationOutputEB;
import eu.sifem.model.to.DataSetHashCacheTO;
import eu.sifem.model.to.DataSetTO;
import eu.sifem.model.to.ImageLocationTO;
import eu.sifem.model.to.ParameterTO;
import eu.sifem.model.to.PlyConverterTO;
import eu.sifem.model.to.ProjectSimulationTO;
import eu.sifem.model.to.TransformationTO;
import eu.sifem.model.to.ViewTO;
import eu.sifem.service.IFeatureExtractorService;
import eu.sifem.service.IFileDownloaderService;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.ISimulationService;
import eu.sifem.service.ITransformationService;
import eu.sifem.service.IVisualizationService;
import eu.sifem.utils.BasicFileTools;
import eu.sifem.utils.Util;
//import org.apache.commons.io.comparator.LastModifiedFileComparator;


/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "visualisationOutputController") 
@ViewScoped
public class VisualisationOutputController extends GenericMB {
	

	private static final String UM = "1";

	private static final String ZERO = "0";

	private static final long serialVersionUID = -7304350271436783287L;
	
	private static final String VECTOR_FIELD_GRAPH_FILE = "VectorFieldGraphFile";

	private static final String D_LINE_GRAPH_FILE = "DLineGraphFile";

	private static final String VISUALISATION_OUTPUT_EB = "visualisationOutputEB";

	private static final String PAK = "PAK";

	private static final String JPEG = ".jpg";
	
	private static final String _2D_PLOT = "2D plot";
	
	private static final String _3D_PLOT = "3D plot";

	private static final String VECTOR_FIELD = "Vector Field";

	private static final String STREAMLINE = "Streamline";

	//private static final String PAK_3D = "PAK 3D";

	private Map<String,String> visualisationAndOutputlist = new HashMap<String, String>();
	
	private Boolean render2Dplot = Boolean.FALSE;
	
	private Boolean render3Dplot = Boolean.FALSE;
	
	private Boolean rendervectorfield = Boolean.FALSE;
	
	private Boolean renderstreamline = Boolean.FALSE;
	
	private Boolean outputAvail = Boolean.FALSE;
	
	private Boolean renderPAKImages = Boolean.FALSE; 
	
	private Map<String, String> paramsList = new TreeMap<String, String>();
	
	private Properties config = new Properties();
	
	private Boolean filterAttributesByTransformation = Boolean.FALSE;
	
	private String selectedTransformationName = new String();
	
	private Boolean renderPAK3D = Boolean.FALSE;
	
	private Boolean renderGreenWoodsemanticInterpretation;
	
	private Boolean renderDisplacementsemanticInterpretation;

	private String plyFileLocation;
	
	private String shinyAppHostName;
	
	
	@ManagedProperty(value="#{projectSimulationController}")
	private ProjectSimulationController projectSimulationController;
	
	@ManagedProperty(value="#{projectSimulationEB}")
	private ProjectSimulationEB projectSimulationEB;
	
	@ManagedProperty(value="#{visualisationOutputEB}")
	private VisualisationOutputEB visualisationOutputEB;
	
	@ManagedProperty(value="#{solverSetupEB}")
	private SolverSetupEB solverSetupEB;
	
	@ManagedProperty(value="#{resourceInjectionService}")
	private IResourceInjectionService resourceInjectionService;
	
	@ManagedProperty(value="#{fileDownloader}")
	private IFileDownloaderService fileDownloader;
	
	@ManagedProperty(value="#{boundaryInternalConditionsEB}")
	private BoundaryInternalConditionsEB boundaryInternalConditionsEB;
	
	@ManagedProperty(value="#{loadParametersEB}")
	private LoadParametersEB loadParametersEB;
	
	private List<ImageLocationTO> pakImagesStreamedContentList = new ArrayList<ImageLocationTO>();

	
	@ManagedProperty(value="#{featureExtractor}")
	private IFeatureExtractorService featureExtractor;
	
	@ManagedProperty(value="#{simulationService}")
	private ISimulationService simulationService;
	
	@ManagedProperty(value="#{transformationEB}")
	private TransformationEB transformationEB;
	
	@ManagedProperty(value="#{transformation}")
	private ITransformationService transformationService;
	
	@ManagedProperty(value="#{visualizationService}")
	private IVisualizationService visualizationService;
	
	@ManagedProperty(value="#{dataAnalysisController}")
	private DataAnalysisController dataAnalysisController;
	
	
	private Map<String,String> transformationsBySimulation = new TreeMap<String,String>();
	
    public void handleVisualizationAndOutputListChange()  {
    	try {
    		dataAnalysisController.setRenderSemanticIntepretation(Boolean.FALSE);
        	if (_2D_PLOT.equals(visualisationOutputEB.getVisualisationOutputTO().getGraphType())) {
        		fillListOfParams();
        		this.render2Dplot = Boolean.TRUE;
        		this.rendervectorfield = Boolean.FALSE;
        		this.renderstreamline = Boolean.FALSE;
        		this.render3Dplot = Boolean.FALSE;
        	}
        	if (VECTOR_FIELD.equals(visualisationOutputEB.getVisualisationOutputTO().getGraphType())) {
        		this.render2Dplot = Boolean.FALSE;
        		this.rendervectorfield = Boolean.TRUE;
        		this.renderstreamline = Boolean.FALSE;
        		this.render3Dplot = Boolean.FALSE;
        	}
        	if (STREAMLINE.equals(visualisationOutputEB.getVisualisationOutputTO().getGraphType())) {
        		this.render2Dplot = Boolean.FALSE;
        		this.rendervectorfield = Boolean.FALSE;
        		this.renderstreamline = Boolean.TRUE;
        		this.render3Dplot = Boolean.FALSE;
        	}
        	if (_3D_PLOT.equals(visualisationOutputEB.getVisualisationOutputTO().getGraphType())) {
        		this.render3Dplot = Boolean.TRUE;
        		this.render2Dplot = Boolean.FALSE;
        		this.rendervectorfield = Boolean.FALSE;
        		this.renderstreamline = Boolean.FALSE;
        	}
        	this.filterAttributesByTransformation = Boolean.FALSE;
        	fillListOfParams();

		} catch (Exception e) {
			addExceptionMessage(e);
		}
    }

    


	public void fillListOfParams() {
		try {
			if(paramsList==null){
				return;
			}
			paramsList.clear();
			List<String> lstOfParams = simulationService.getParameterNamesService();
			for (String eachObject : lstOfParams) {
				paramsList.put(eachObject, eachObject);
			}
			for (String eachObject : simulationService.findAllTempParameters()) {
				paramsList.put(eachObject, eachObject);
			}
			List<TransformationTO> transformationTOList = transformationService.findAllService();
			for(TransformationTO to:transformationTOList){
				if(to.getIsInUse().booleanValue()){
					for(String parameter:to.getParameters()){
						if(parameter.contains(";")){
							String[] paramsArr =  parameter.split(";");
							for(String paramStr:paramsArr){
								paramsList.put(paramStr,paramStr);							
							}
						}else{
							paramsList.put(parameter, parameter);
							continue;
						}
						
					}
				}
			}

		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}

	public void handlerAttributesByTransformation(AjaxBehaviorEvent event) {
		try {
			if(selectedTransformationName==null || "".equals(selectedTransformationName)){
	    		return;
	    	}
			List<String> lstOfParams = simulationService.findAllParametersByTransformationNameService(selectedTransformationName);
			if(lstOfParams==null || lstOfParams.isEmpty()){
				return;
			}
			for (String eachObject : lstOfParams) {
				paramsList.put(eachObject, eachObject);
			}
			for (String eachObject : simulationService.findAllTempParameters()) {
				paramsList.put(eachObject, eachObject);
			}
			
	    
		} catch (Exception e) {
			addExceptionMessage(e);
		}
    		
    	
    }
	
	@SuppressWarnings("unchecked")
	public List<String> transformationAutoCompleteComboBox(String query)  {
		List<String> result = new ArrayList<String>();
		try {
			String name = "";
			if(query==null || "".equals(query)){
				List<TransformationTO> resultList = transformationService.findAllService();
				resultList = transformationService.findAllService();
				for(TransformationTO transformationTO:resultList){
					name = transformationTO.getName();
					name = new Util().replaceUnderlinesForSpace(name);
					result.add(name);
				}
				return result;
			}
			
			List<TransformationTO> resultList = transformationService.findAllByPartialNameService(query);
			if(resultList==null || resultList.isEmpty()){return Collections.EMPTY_LIST;}
			for(TransformationTO transformationTO:resultList){
				name = transformationTO.getName();
				name = new Util().replaceUnderlinesForSpace(name);
				result.add(name);
			}

		} catch (Exception e) {
			addExceptionMessage(e);
		}
		return result;
	}

	
    
    public void showOutput() {
    	try {
    		String projectName = projectSimulationEB.getProjectSimulationTO().getProjectName();
        	String selectedSolverName = solverSetupEB.getSolverTO().getName();
        	
                System.err.println("InShow");
        	if(selectedSolverName==null || "".equalsIgnoreCase(selectedSolverName)){
        		addWarnMessage("Warn","This feature can't work without the solver name selection.");
        	}

    		outputAvail = Boolean.TRUE;
    		Boolean isSelectedSolverPAK = PAK.equalsIgnoreCase(selectedSolverName);
    		TransformationTO transformationTO = transformationEB.getTransformationTO();
    		//String workspacePath = projectSimulationEB.getProjectSimulationTO().getWorkspace();
    		
    		//TODO refactoring after presentation
    		//String transformationName = transformationEB.getTransformationsTarget().get(0); //uncomment
    		//transformationTO = transformationService.findByNameService(transformationName); //uncomment
    		String transformationName = "newTransformation"; //remove it
    		transformationTO = transformationService.findByNameService(transformationName);
    		
    		renderPAK3D = Boolean.FALSE;
    		renderPAKImages = Boolean.FALSE;
    		visualisationOutputEB.getVisualisationOutputTO().getLstOfImageLoc().clear();
    		
    		if(isSelectedSolverPAK && _2D_PLOT.equals(visualisationOutputEB.getVisualisationOutputTO().getGraphType())){
    			

    			String xName = visualisationOutputEB.getVisualisationOutputTO().getPlot2DX();
    			String yName = visualisationOutputEB.getVisualisationOutputTO().getPlot2DY();
    			
    			
    			ProjectSimulationTO projectSimulation = projectSimulationEB.getProjectSimulationTO();

    			String simulationName = projectSimulationEB.getProjectSimulationTO().getSimulationName();
    			if(transformationTO==null || StringUtils.equalsIgnoreCase(transformationTO.getName(),"")){
    				System.err.println("ERROR! SimulatioName/Transformation not defined.");
    			}else{
    				simulationName = transformationTO.getName();
    			}

//    			visualizationService
//    			ViewTO dataView = transformationService.retrieveDataViewService(transformationTO,projectSimulation.getWorkspace(),projectName,simulationName,xName,yName,null);
    																		//(DataSetTO dataSetTO,String sparqlView, List<File> semantificationFiles,String workspace, String projectName, String simulationName) throws Exception{
    			DataSetTO dataSetHashCacheTO =  new DataSetTO(new DataSetHashCacheTO(projectName,simulationName,xName,yName,""),"");
    			
    			String frequencyValue = "";
    			if(loadParametersEB!=null && loadParametersEB.getLoadParametersTOList()!=null 
    					&& !loadParametersEB.getLoadParametersTOList().isEmpty()){
    				for(ParameterTO parameter:loadParametersEB.getLoadParametersTOList()){
    					if(StringUtils.equalsIgnoreCase(parameter.getName(),"frequency")){
    						if(parameter.getParameterUniqueValue()==null || "".equals(parameter.getParameterUniqueValue())){
    							continue;
    						}
    						frequencyValue = parameter.getParameterUniqueValue();
    					}
    				}
    			}
    			
    			dataSetHashCacheTO.getCache().setFrequency(frequencyValue);
    			ViewTO dataView = visualizationService.retrieveDataViewService(dataSetHashCacheTO,null,Collections.EMPTY_LIST,null,projectName,simulationName);
    			if(dataView==null){
    				dataView = transformationService.retrieveDataViewService(transformationTO,null,projectName,simulationName,xName,yName,null);
    			}
    			File imageFile = new BasicFileTools().getImagePath(null, projectName, simulationName, transformationName);
    			
    			String imageLocation = null;
    			
    			// if datax and datay are not empty: OK			
    			if(dataView!=null && dataView.getxView()!=null && !dataView.getxView().isEmpty() && !dataView.getyView().isEmpty()){
    				//(List<Double> datax, String varNamx, List<Double> datay, String varNamy, String plotName,String basePath)
    				imageLocation = transformationService.generateGraphicService(dataView.getxView(),xName,dataView.getyView(),yName,transformationName,imageFile.getCanonicalPath());				
    			}
    			// If datax and datay are empty try to get the map from dataView, check x and y attributes then convert it (if a script.py file exists)  
    			if(!dataView.getDimValMap().isEmpty()){
    				//(List<Double> datax, String varNamx, List<Double> datay, String varNamy, String plotName,String basePath)
    				imageLocation = transformationService.generateGraphicService(dataView.getDimValMap(),imageFile.getCanonicalPath(),transformationName,xName,yName);
    			}
    			if((dataView.getxView().isEmpty() && dataView.getyView().isEmpty())&& dataView.getDimValMap().isEmpty()){
    				//TODO throw exception
    				System.err.println("(datax.isEmpty() && datay.isEmpty())&& dataView.getDimValMap().isEmpty()");
    			}
    			
    			

    			while(true){
    				if(new File(imageLocation).exists()){
    					//TODO maybe Throw exception... improve it.
    					break;
    				}
    			}
    			visualisationOutputEB.getVisualisationOutputTO().setLstOfImageLoc(fileDownloader.setPAKOutputLocationPathNameAndExtService(imageLocation, visualisationOutputEB.getVisualisationOutputTO().getLstOfImageLoc()));
    			renderPAK3D = Boolean.FALSE;
    			renderPAKImages = Boolean.TRUE;
    			return;
    			
    		}			

    		if(PAK.equalsIgnoreCase(solverSetupEB.getSolverTO().getName()) && _3D_PLOT.equals(visualisationOutputEB.getVisualisationOutputTO().getGraphType())){
    				InputStream datFileIs = visualizationService.retrieveFirstDatFileByProjectName(projectSimulationEB.getProjectSimulationTO().getProjectName());
    				byte[] datFileByteArr = IOUtils.toByteArray(datFileIs);
    				PlyConverterTO plyConverterTO = new PlyConverterTO();
    				plyConverterTO.setInputDatFile(datFileByteArr);
    				
    				//FIXME Find other way to get the id
    				plyConverterTO.setHash(UUID.randomUUID().toString());
    				String parameter = Util.getJsonStrFromObject(plyConverterTO);

    				//FIXME fixed this url below
    				String responseContentStr = Request.Post("http://localhost:8080/SifemPlyConverter/rest/PlyConverterService/converter")
    				.bodyForm(Form.form().add("plyConverterTO",parameter).build()).execute().returnContent().asString();

    				Gson gson = new GsonBuilder().create();
    				plyConverterTO  = gson.fromJson(responseContentStr, PlyConverterTO.class);
    				plyFileLocation = plyConverterTO.getPlyName();
                                System.err.println(plyFileLocation);
        			renderPAKImages = Boolean.FALSE;
    				renderPAK3D = Boolean.TRUE;
    		}		
    	}
    	catch(Exception e){
    			throw new RuntimeException(e.getMessage(),e);
    	}
    	
    	clear();

    	
	}




	private boolean isListContainsTwoSelectedAttributes(List<TransformationTO> transformationTOList, String param1, String param2) {
		for(TransformationTO to : transformationTOList){
			String strList = Arrays.deepToString(to.getParameters().toArray());
			if(StringUtils.containsIgnoreCase(strList,param1) &&
			StringUtils.containsIgnoreCase(strList,param2)){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
    
	private void clearSessionImages() {
		if(visualisationOutputEB.getVisualisationOutputTO()!=null && visualisationOutputEB.getVisualisationOutputTO().getLstOfImageLoc()!=null && visualisationOutputEB.getVisualisationOutputTO().getLstOfImageLoc().isEmpty()){
			visualisationOutputEB.getVisualisationOutputTO().getLstOfImageLoc().clear();
			if(visualisationOutputEB!=null){
				Object visualisationOutputEBTOObj = getSessionBean(VISUALISATION_OUTPUT_EB);
				if(visualisationOutputEBTOObj!=null && visualisationOutputEBTOObj instanceof VisualisationOutputEB){
					putSessionBean(VISUALISATION_OUTPUT_EB, new VisualisationOutputEB());
				}
			}			
		}
	}

	private void clear() {
		visualisationOutputEB.getVisualisationOutputTO().setPlot2DY(null);
		visualisationOutputEB.getVisualisationOutputTO().setGraphType(null);
		visualisationOutputEB.getVisualisationOutputTO().setPlot2DX(null);
		visualisationOutputEB.getVisualisationOutputTO().setVectorFieldVelocity(null);	
	}

	public Map<String, String> getVisualisationAndOutputlist(){
		List<String> lstOfGraphTypes = Collections.EMPTY_LIST;
//		try {
//			lstOfGraphTypes = simulationService.getGraphTypesService();
//			for (String eachObject : lstOfGraphTypes) {
//				if (eachObject.equalsIgnoreCase(D_LINE_GRAPH_FILE)) {
//					visualisationAndOutputlist.put(_2D_PLOT, _2D_PLOT);
//					continue;
//				} 
//				
//				if (eachObject.equalsIgnoreCase(VECTOR_FIELD_GRAPH_FILE)) {
//					visualisationAndOutputlist.put(VECTOR_FIELD, VECTOR_FIELD);
//					continue;
//				} 
//				
//				if ("".equalsIgnoreCase(eachObject)) {
//					visualisationAndOutputlist.put(STREAMLINE, STREAMLINE);
//					continue;
//				}
//				
//				visualisationAndOutputlist.put(_3D_PLOT,_3D_PLOT);
//				continue;
//			}
//
//		} catch (Exception e) {
//			addExceptionMessage(e);
//		}
		
		try {
			visualisationAndOutputlist.put(_2D_PLOT, _2D_PLOT);
			visualisationAndOutputlist.put(_3D_PLOT,_3D_PLOT);
		} catch (Exception e) {
			addExceptionMessage(e);
		}
		return visualisationAndOutputlist;
	}

	
	
	
	public Boolean getRenderPAK3D() {
		return renderPAK3D;
	}

	public void setRenderPAK3D(Boolean renderPAK3D) {
		this.renderPAK3D = renderPAK3D;
	}

	public void setVisualisationAndOutputlist(
			Map<String, String> visualisationAndOutputlist) {
		this.visualisationAndOutputlist = visualisationAndOutputlist;
	}

	public Boolean getRender2Dplot() {
		return render2Dplot;
	}

	public void setRender2Dplot(Boolean render2Dplot) {
		this.render2Dplot = render2Dplot;
	}

	public Boolean getRendervectorfield() {
		return rendervectorfield;
	}

	public void setRendervectorfield(Boolean rendervectorfield) {
		this.rendervectorfield = rendervectorfield;
	}

	public Boolean getRenderstreamline() {
		return renderstreamline;
	}

	public void setRenderstreamline(Boolean renderstreamline) {
		this.renderstreamline = renderstreamline;
	}


	public Map<String, String> getParamsList() throws Exception {
		return paramsList;
	}

	public void setParamsList(Map<String, String> paramsList) {
		this.paramsList = paramsList;
	}

	public Boolean getOutputAvail() {
		return outputAvail;
	}

	public void setOutputAvail(Boolean outputAvail) {
		this.outputAvail = outputAvail;
	}

	public IResourceInjectionService getResourceInjectionService() {
		return resourceInjectionService;
	}

	public void setResourceInjectionService(
			IResourceInjectionService resourceInjectionService) {
		this.resourceInjectionService = resourceInjectionService;
	}

	public IFileDownloaderService getFileDownloader() {
		return fileDownloader;
	}

	public void setFileDownloader(IFileDownloaderService fileDownloader) {
		this.fileDownloader = fileDownloader;
	}


	public VisualisationOutputEB getVisualisationOutputEB() {
		return visualisationOutputEB;
	}

	public void setVisualisationOutputEB(VisualisationOutputEB visualisationOutputEB) {
		this.visualisationOutputEB = visualisationOutputEB;
	}

	public SolverSetupEB getSolverSetupEB() {
		return solverSetupEB;
	}

	public void setSolverSetupEB(SolverSetupEB solverSetupEB) {
		this.solverSetupEB = solverSetupEB;
	}

	public BoundaryInternalConditionsEB getBoundaryInternalConditionsEB() {
		return boundaryInternalConditionsEB;
	}

	public void setBoundaryInternalConditionsEB(
			BoundaryInternalConditionsEB boundaryInternalConditionsEB) {
		this.boundaryInternalConditionsEB = boundaryInternalConditionsEB;
	}

	public List<ImageLocationTO> getPakImagesStreamedContentList() {
		return pakImagesStreamedContentList;
	}

	public void setPakImagesStreamedContentList(
			List<ImageLocationTO> pakImagesStreamedContentList) {
		this.pakImagesStreamedContentList = pakImagesStreamedContentList;
	}

	public Boolean getRenderPAKImages() {
		return renderPAKImages;
	}

	public void setRenderPAKImages(Boolean renderPAKImages) {
		this.renderPAKImages = renderPAKImages;
	}

	public ProjectSimulationController getProjectSimulationController() {
		return projectSimulationController;
	}

	public void setProjectSimulationController(
			ProjectSimulationController projectSimulationController) {
		this.projectSimulationController = projectSimulationController;
	}


	public IFeatureExtractorService getFeatureExtractor() {
		return featureExtractor;
	}

	public void setFeatureExtractor(IFeatureExtractorService featureExtractor) {
		this.featureExtractor = featureExtractor;
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

	public ProjectSimulationEB getProjectSimulationEB() {
		return projectSimulationEB;
	}

	public void setProjectSimulationEB(ProjectSimulationEB projectSimulationEB) {
		this.projectSimulationEB = projectSimulationEB;
	}

	public Boolean getFilterAttributesByTransformation() {
		return filterAttributesByTransformation;
	}

	public void setFilterAttributesByTransformation(
			Boolean filterAttributesByTransformation) {
		this.filterAttributesByTransformation = filterAttributesByTransformation;
	}

	public String getSelectedTransformationName() {
		return selectedTransformationName;
	}

	public void setSelectedTransformationName(String selectedTransformationName) {
		this.selectedTransformationName = selectedTransformationName;
	}

	public Map<String, String> getTransformationsBySimulation() {
		return transformationsBySimulation;
	}

	public void setTransformationsBySimulation(
			Map<String, String> transformationsBySimulation) {
		this.transformationsBySimulation = transformationsBySimulation;
	}

	public ITransformationService getTransformationService() {
		return transformationService;
	}

	public void setTransformationService(
			ITransformationService transformationService) {
		this.transformationService = transformationService;
	}

	public Boolean getRenderGreenWoodsemanticInterpretation() {
		return renderGreenWoodsemanticInterpretation;
	}

	public void setRenderGreenWoodsemanticInterpretation(
			Boolean renderGreenWoodsemanticInterpretation) {
		this.renderGreenWoodsemanticInterpretation = renderGreenWoodsemanticInterpretation;
	}

	public Boolean getRenderDisplacementsemanticInterpretation() {
		return renderDisplacementsemanticInterpretation;
	}

	public void setRenderDisplacementsemanticInterpretation(
			Boolean renderDisplacementsemanticInterpretation) {
		this.renderDisplacementsemanticInterpretation = renderDisplacementsemanticInterpretation;
	}


	public DataAnalysisController getDataAnalysisController() {
		return dataAnalysisController;
	}


	public void setDataAnalysisController(
			DataAnalysisController dataAnalysisController) {
		this.dataAnalysisController = dataAnalysisController;
	}




	public IVisualizationService getVisualizationService() {
		return visualizationService;
	}




	public void setVisualizationService(IVisualizationService visualizationService) {
		this.visualizationService = visualizationService;
	}




	public LoadParametersEB getLoadParametersEB() {
		return loadParametersEB;
	}




	public void setLoadParametersEB(LoadParametersEB loadParametersEB) {
		this.loadParametersEB = loadParametersEB;
	}




	public Boolean getRender3Dplot() {
		return render3Dplot;
	}




	public void setRender3Dplot(Boolean render3Dplot) {
		this.render3Dplot = render3Dplot;
	}




	public String getPlyFileLocation() {
		return getApplicationUri();
	}




	public void setPlyFileLocation(String plyFileLocation) {
		this.plyFileLocation = getApplicationUri();
	}

	private String getShinyHostNameAndParameters(){
		String simulationName = projectSimulationEB.getProjectSimulationTO().getSimulationName();
		//String selectedSolverName = solverSetupEB.getSolverTO().getName();
		String projectName = projectSimulationEB.getProjectSimulationTO().getProjectName();
		
                
		//TODO FIXME - After tests, we need to use the commented version in place of the version with fixed project and simulation name, below.

		shinyAppHostName = resourceInjectionService.getShinyVisualizationAppHostName()
				+"?interpretationHostName="+resourceInjectionService.getApplicationServerURL()+
				"&workspacePath=NULL&getProjectName="+projectName+"&simulationName="+simulationName+"".trim();
                   
                
/*                
		shinyAppHostName = resourceInjectionService.getShinyVisualizationAppHostName()
				+"?interpretationHostName=192.168.7.146:8080"+
				"&workspacePath=NULL&getProjectName="+projectName+"&simulationName="+simulationName+"".trim();
                
		shinyAppHostName = resourceInjectionService.getShinyVisualizationAppHostName()
				+"?interpretationHostName="+getApplicationUri()+
				"&workspacePath=NULL&getProjectName=PROJECTTEST&simulationName=SIMULATIONUNITTEST".trim();
 */
		//Eg.:http://127.0.0.1:9876/?interpretationHostName=localhost:8080&workspacePath=NULL&getProjectName=PROJECTTEST&simulationName=SIMULATIONUNITTEST
                System.err.println("  shinyAppHostName = " + shinyAppHostName);
                
		return shinyAppHostName;
	}


	public String getShinyAppHostName() {
		shinyAppHostName = getShinyHostNameAndParameters();
		return shinyAppHostName;
	}



	//TODO Think about remove or refactoring this method.
	public void setShinyAppHostName(String shinyAppHostName) {
		shinyAppHostName = getShinyHostNameAndParameters();
		this.shinyAppHostName = shinyAppHostName;
	}

	

	
}
