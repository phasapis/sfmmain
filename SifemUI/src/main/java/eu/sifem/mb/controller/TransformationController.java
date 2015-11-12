package eu.sifem.mb.controller;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.model.DualListModel;

import eu.sifem.mb.entitybean.ProjectSimulationEB;
import eu.sifem.mb.entitybean.TransformationEB;
import eu.sifem.mb.entitybean.VisualisationOutputEB;
import eu.sifem.model.to.TransformationTO;
import eu.sifem.service.ISimulationService;
import eu.sifem.service.ITransformationService;
import eu.sifem.utils.Util;


/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "transformationController")
@ViewScoped
public class TransformationController extends GenericMB {

	

	private static final long serialVersionUID = -994349763813942057L;

	@ManagedProperty(value = "#{transformation}")
	private ITransformationService transformationService;

	@ManagedProperty(value = "#{transformationEB}")
	private TransformationEB transformationEB;

	@ManagedProperty(value = "#{visualisationOutputEB}")
	private VisualisationOutputEB visualisationOutputEB;

	
	@ManagedProperty(value="#{simulationService}")
	private ISimulationService simulationService;

	@ManagedProperty(value = "#{projectSimulationEB}")
	private ProjectSimulationEB projectSimulationEB;
	
	private String scriptViewStr = "";
	
	private String queryViewStr = "";
	
	private String selectedTransformationName;
	
	private List<String> transformationList = new ArrayList<String>();

	private Boolean createAddTransformationRender = Boolean.FALSE;
	
	private DualListModel<String> transformationsDualListModel = new DualListModel<String>();

	
	
	public void fillTransformationSourceSidePickList()  {
		try {
			transformationEB.setTransformationsSource(Collections.EMPTY_LIST);
			transformationEB.setTransformationsTarget(Collections.EMPTY_LIST);
			getTransformationsDualListModel().getSource().clear();
			getTransformationsDualListModel().getTarget().clear();
			setTransformationsDualListModel(new DualListModel<String>(transformationEB.getTransformationsSource(),transformationEB.getTransformationsTarget()));
			
			Set<String> tmpIsInUse = new HashSet<String>();
			Set<String> tmpIsNotInUse = new HashSet<String>();
			String projectName = projectSimulationEB.getProjectSimulationTO().getProjectName();

			for(TransformationTO transformationTO:transformationService.findAllService()){

				Boolean isInUse = transformationTO.getIsInUse();
				if(isInUse==null){
					isInUse = Boolean.FALSE;
					tmpIsNotInUse.add(transformationTO.getName());
					fillFileContent(transformationTO);
				}
				if(isInUse){
					tmpIsInUse.add(transformationTO.getName());					
					fillFileContent(transformationTO);
				}
				
				if(!isInUse){
					tmpIsNotInUse.add(transformationTO.getName());	
					fillFileContent(transformationTO);
				}
				
			}
			
			List<String> tmpIsInUseList = new ArrayList<String>();
			List<String> tmpIsNotInUseList = new ArrayList<String>();
			Iterator<String> itIsNotInUse = tmpIsNotInUse.iterator(); while(itIsNotInUse.hasNext()){
				String transfStr = itIsNotInUse.next();
				tmpIsNotInUseList.add(transfStr);
			}
			Iterator<String> itIsInUse = tmpIsInUse.iterator(); while(itIsInUse.hasNext()){
				String transfStr = itIsInUse.next();
				tmpIsInUseList.add(transfStr);
			}
			transformationEB.setTransformationsSource(tmpIsNotInUseList);
			transformationEB.setTransformationsTarget(tmpIsInUseList);
			


			setTransformationsDualListModel(new DualListModel<String>(transformationEB.getTransformationsSource(),transformationEB.getTransformationsTarget()));



		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}
	

	private void fillFileContent(TransformationTO transformationTO) {
		try {
			if(transformationTO==null || transformationTO.getName()==null || "".equals(transformationTO.getName())){return;}
			
			String transformationName = transformationTO.getName();
			InputStream queryInputStream = transformationService.findQueryByTransformationNameService(transformationName);
			InputStream scriptInputStream = transformationService.findScriptByTransformationNameService(transformationName);
			
			if(queryInputStream!=null){
				this.queryViewStr = IOUtils.toString(queryInputStream, "UTF-8");
			}else{
				//TODO maybe throw exception here. must think about it.			
			}
			
			if(scriptInputStream!=null){
				scriptViewStr = IOUtils.toString(scriptInputStream,"UTF-8");
			}else{
				//script is not an necessary attribute. Do nothing.
			}

			//set combo value
			UIComponent transformationNameSelect = findComponent("transformationNameSelectID");
			org.primefaces.component.selectonemenu.SelectOneMenu selectOneMenuLoadparameters = (SelectOneMenu) transformationNameSelect;
			String code = (String) selectOneMenuLoadparameters.getValue();
			if(code==null || "".equals(code)){
				selectOneMenuLoadparameters.setValue(transformationName);
			}

		} catch (Exception e) {
			addExceptionMessage(e);
		}
				
	}

	public void handlerTransformationViewByName() {
		try {
			String name = getAutoCompleteTransformationNameValue();
			if(transformationEB.getTransformationTO()==null || name==null || "".equals(name)){
				transformationEB.setTransformationTO(new TransformationTO());
				this.scriptViewStr = "";
				this.queryViewStr = "";
				return;
			}
			TransformationTO transformationTO = getSelectedTransformationByName();
			if(transformationTO==null){
				return;
			}
			name = new Util().replaceUnderlinesForSpace(name);
			transformationEB.setTransformationTO(transformationTO);
			getScriptAndQueryContent();
			return;
		} catch (Exception e) {
			addExceptionMessage(e);
		}
		
	}
	
	public void handlerTransformationViewByNameRefresh() {
		try {
			String name = getAutoCompleteTransformationNameValue();
			if(transformationEB.getTransformationTO()==null || name==null || "".equals(name)){
				transformationEB.setTransformationTO(new TransformationTO());
				return;
			}
			TransformationTO transformationTO = getSelectedTransformationByName();
			if(transformationTO==null){
				transformationEB.setTransformationTO(new TransformationTO());
			}
			getScriptAndQueryContent();
			return;
		} catch (Exception e) {
			addExceptionMessage(e);
		}
		
	}
	
	private String getAutoCompleteTransformationNameValue() {
		UIComponent transformationNameSelect = findComponent("transformationNameSelectID");
		org.primefaces.component.selectonemenu.SelectOneMenu selectOneMenuLoadparameters = (SelectOneMenu) transformationNameSelect;
		String code = (String) selectOneMenuLoadparameters.getValue();
		return code;
	}


	public void saveOrUpdateTransformationSelection() {
		try {
			List<TransformationTO> transfformationNames = new ArrayList<TransformationTO>();
			TransformationTO transformationTO = null;
			
			List<String> actualDualList = this.transformationsDualListModel.getTarget();
			if(!actualDualList.isEmpty()){
				actualDualList.size();
				if(actualDualList.size()==2){
					if(!StringUtils.equalsIgnoreCase(actualDualList.get(0), actualDualList.get(1))){
						actualDualList.remove(actualDualList.size()-1);
						this.transformationsDualListModel.getTarget().add(actualDualList.get(0));
					}
				}
				if(actualDualList.size()>2){
					this.transformationsDualListModel.getTarget().add(actualDualList.get(0));
				}
				
			}
			
			for(String transformation:getTransformationsDualListModel().getTarget()){
				transformationTO =  transformationService.findByNameService(transformation);
				if(transformationTO==null){
					continue;
				}
				if(transformationTO.getIsInUse()==null || transformationTO.getIsInUse()){
					transformationTO.setIsInUse(Boolean.FALSE);
					transfformationNames.add(transformationTO);
					transformationService.saveOrUpdateService(transformationTO, null,null);
					fillTransformationSourceSidePickList();
					return;
				}
				if(!transformationTO.getIsInUse()){
					transformationTO.setIsInUse(Boolean.TRUE);
					transfformationNames.add(transformationTO);
					transformationService.saveOrUpdateService(transformationTO, null,null);
					fillTransformationSourceSidePickList();
					return;
					
				}
				fillFileContent(transformationTO);

			}
		} catch (Exception e) {
			addExceptionMessage(e);
		}


				
	}
	

	public void newTransformation(Boolean isNew) {
		try {
			createAddTransformationRender = isNew;
			fillTransformationSourceSidePickList();	
			for(TransformationTO  transformation:transformationService.findAllService()){
				this.transformationList.add(transformation.getName());			
			}
			
			this.scriptViewStr = "";
			this.queryViewStr = "";

		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}
	
	private TransformationTO getSelectedTransformationByName() throws Exception{
		List<String> actualDualList = this.transformationsDualListModel.getTarget();
		String transformationName = "";
		if(!actualDualList.isEmpty()){
			transformationName	= actualDualList.get(0);
		}
		if("".equals(transformationName)){
			addErrorMessage("Warn!", "Transformation not found!");
		}
		TransformationTO transformationTO = transformationService.findByNameService(transformationName);
		return transformationTO;
	}

	public void saveTransformationCreation()  {
		try {
			//TODO Deprecating..
//			if(projectSimulationEB==null || 
//					projectSimulationEB.getProjectSimulationTO()==null ||
//					"".equals(projectSimulationEB.getProjectSimulationTO().getWorkspace())){
//				addErrorMessage("Erro","Workspace path not defined!");
//			}
//			File transformationTempFile = new File(projectSimulationEB.getProjectSimulationTO().getWorkspace()+"/tranformations/");
//			FileUtils.deleteDirectory(transformationTempFile);
//			FileUtils.forceMkdir(transformationTempFile);
//			String workspacePath = projectSimulationEB.getProjectSimulationTO().getWorkspace();
//			File scriptFile = new File(transformationTempFile+"\\script.py");
//			File queryFile = new File(transformationTempFile+"\\query.sparql");
//			FileUtils.writeStringToFile(scriptFile,this.scriptViewStr);
//			FileUtils.writeStringToFile(queryFile,this.queryViewStr);
			
			
			transformationEB.getTransformationTO().setScriptFile(IOUtils.toInputStream(this.scriptViewStr));
			transformationEB.getTransformationTO().setQueryFile(IOUtils.toInputStream(this.queryViewStr));
			String transformationName = getAutoCompleteTransformationNameValue();
			transformationEB.getTransformationTO().setName(transformationName);
			transformationEB.getTransformationTO().setProjectName(projectSimulationEB.getProjectSimulationTO().getProjectName());
			
			List<String> parameters = new ArrayList<String>();
			new Util().fillQueryAttributes(this.queryViewStr, parameters);
			transformationEB.getTransformationTO().setParameters(parameters);

			transformationService.saveOrUpdateService(transformationEB.getTransformationTO(),null,null);
			createAddTransformationRender = Boolean.FALSE;
			fillTransformationSourceSidePickList();

		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}


	public void mapAttributesFromQuery() {
		try {
			getScriptAndQueryContent();
			//TODO insertcode here
		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}

	
	private void getScriptAndQueryContent()  {
		try {
			//TODO insertcode here
			TransformationTO transformationTO = getSelectedTransformationByName();
			fillFileContent(transformationTO);
		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}

	public TransformationEB getTransformationEB() {
		return transformationEB;
	}

	public void setTransformationEB(TransformationEB transformationEB) {
		this.transformationEB = transformationEB;
	}

	public ITransformationService getTransformationService() {
		return transformationService;
	}

	public void setTransformationService(
			ITransformationService transformationService) {
		this.transformationService = transformationService;
	}

	public VisualisationOutputEB getVisualisationOutputEB() {
		return visualisationOutputEB;
	}

	public void setVisualisationOutputEB(
			VisualisationOutputEB visualisationOutputEB) {
		this.visualisationOutputEB = visualisationOutputEB;
	}

	public Boolean getCreateAddTransformationRender() {
		return createAddTransformationRender;
	}

	public void setCreateAddTransformationRender(
			Boolean createAddTransformationRender) {
		this.createAddTransformationRender = createAddTransformationRender;
	}

	public DualListModel<String> getTransformationsDualListModel()throws Exception {
		return transformationsDualListModel;
		
	}

	public void setTransformationsDualListModel(DualListModel<String> transformationsDualListModel) {
		try {
			this.transformationsDualListModel.getSource().addAll(transformationsDualListModel.getSource());
			this.transformationsDualListModel.getTarget().addAll(transformationsDualListModel.getTarget());
			//TODO insertcode here
		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}


	public ISimulationService getSimulationService() {
		return simulationService;
	}


	public void setSimulationService(ISimulationService simulationService) {
		this.simulationService = simulationService;
	}


	public ProjectSimulationEB getProjectSimulationEB() {
		return projectSimulationEB;
	}


	public void setProjectSimulationEB(ProjectSimulationEB projectSimulationEB) {
		this.projectSimulationEB = projectSimulationEB;
	}

	public String getScriptViewStr() {
		return scriptViewStr;
	}

	public void setScriptViewStr(String scriptViewStr) {
		this.scriptViewStr = scriptViewStr;
	}

	public String getQueryViewStr() {
		return queryViewStr;
	}

	public void setQueryViewStr(String queryViewStr) {
		this.queryViewStr = queryViewStr;
	}

	public String getSelectedTransformationName() {
		return selectedTransformationName;
	}

	public void setSelectedTransformationName(String selectedTransformationName) {
		this.selectedTransformationName = selectedTransformationName;
	}

	public List<String> getTransformationList() {
		return transformationList;
	}

	public void setTransformationList(List<String> transformationList) {
		this.transformationList = transformationList;
	}
	
	
	
	
	
}
