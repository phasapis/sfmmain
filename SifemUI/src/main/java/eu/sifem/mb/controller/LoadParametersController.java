package eu.sifem.mb.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import java.io.File;
import java.io.FileInputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;

import org.apache.commons.lang.StringUtils;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import eu.sifem.mb.entitybean.LoadParametersEB;
import eu.sifem.model.to.ParameterTO;
import eu.sifem.service.ISimulationService;
import eu.sifem.utils.BasicFileTools;
import java.io.IOException;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "loadParametersController") 
@ViewScoped
public class LoadParametersController extends GenericMB{

	private static final String UPLOAD = "apload";

	private static final String AREA = "area";

	private static final String INCREMENT = "increment";

	private static final String UNIQUE = "unique";

	private static final long serialVersionUID = -8702564978402821144L;

	private Map<String,String> loadParametersType = new TreeMap<String,String>();

	private Map<String,ParameterTO> loadParametersDefaultValues = new TreeMap<String,ParameterTO>();
                
	@ManagedProperty(value="#{loadParametersEB}")
	private LoadParametersEB loadParametersEB;
	
	private ParameterTO loadParametersTO = new ParameterTO();
	
	private UploadedFile fileUpload;
	
	private Boolean renderUploaderView = Boolean.TRUE;
	private Boolean renderTextAreaView = Boolean.TRUE;
	private Boolean renderLimitsValueView = Boolean.TRUE;
	private Boolean renderUniqueValueInputText = Boolean.TRUE;
	private Boolean renderFinalButtons = Boolean.TRUE;
	
	@ManagedProperty(value="#{simulationService}")
	private ISimulationService simulationService;

	
	public void fileUploadListener(FileUploadEvent event){
		try {
			fileUpload=event.getFile();
			if(fileUpload!=null){
				this.loadParametersTO.setParameterFile(fileUpload.getInputstream());
				renderUploaderView = Boolean.TRUE;
				renderTextAreaView = Boolean.FALSE;
				renderLimitsValueView = Boolean.FALSE;
				renderUniqueValueInputText = Boolean.FALSE;
				renderFinalButtons = Boolean.TRUE;
				blockTabs(UPLOAD);
				addInfoMessage("Info","File uploaded with success!");
			}
		} catch (Exception e) {
			addExceptionMessage(e);
		}

    }
	public void ajaxBehaviorParamComboChanged() {
		try {
			this.loadParametersTO = getParameterTOFromSessionByName(getComboBoxParamNameValue());

                        System.err.println(" --- " + getComboBoxParamNameValue());
                        System.err.println(" ---- " + this.loadParametersDefaultValues.get(getComboBoxParamNameValue()));
                        
                        if(this.loadParametersDefaultValues.get(getComboBoxParamNameValue()).getParameterUniqueValue().equals("")!=true)
                            this.loadParametersTO.setParameterUniqueValue(this.loadParametersDefaultValues.get(getComboBoxParamNameValue()).getParameterUniqueValue());

                        if(this.loadParametersDefaultValues.get(getComboBoxParamNameValue()).getDefaultFile().equals("")!=true)
                        {
                            String areaText = getFileContents(this.loadParametersDefaultValues.get(getComboBoxParamNameValue()).getDefaultFile());
                            this.loadParametersTO.setAreaValue(areaText);
                        }
                        
                        
			unBlockAllTabs();
		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}
	

	public void resetAllActionListener(){
		try {
			unBlockAllTabs();
			loadParametersTO = new ParameterTO();
		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}
	
	public void saveAllActionListener(){
		try {
			unBlockAllTabs();
			blockNecessaryTabs();
			String id = getComboBoxParamID();
			String name = getComboBoxParamNameValue();
			this.loadParametersTO.setId(id);
			this.loadParametersTO.setName(name);
			
			loadParametersEB.getLoadParametersTOList().add(this.loadParametersTO);
		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}
	
	private void blockNecessaryTabs(){
		try {
			if(loadParametersTO==null){
				unBlockAllTabs();
				return;
			}
			if(loadParametersTO.getParameterFile()!=null && !BasicFileTools.isAnEmptyInputStream(loadParametersTO.getParameterFile())){
				loadParametersTO.setIsIncrementalGroup(Boolean.FALSE);
				blockTabs(UPLOAD);
			}
			if(loadParametersTO.getParameterUniqueValue()!=null && !"".equals(loadParametersTO.getParameterUniqueValue())){
				loadParametersTO.setIsIncrementalGroup(Boolean.FALSE);
				blockTabs(UNIQUE);
			}
			if(loadParametersTO.getAreaValue()!=null && !"".equals(loadParametersTO.getAreaValue())){
				loadParametersTO.setIsIncrementalGroup(Boolean.TRUE);
				blockTabs(AREA);
			}
			
			if(loadParametersTO.getIncrementalInitialValue()!=null && !"".equals(loadParametersTO.getIncrementalInitialValue()) &&
					loadParametersTO.getIncrementalFinalValue()!=null && !"".equals(loadParametersTO.getIncrementalFinalValue()) &&
					loadParametersTO.getIncrementalIncrementValue()!=null && !"".equals(loadParametersTO.getIncrementalIncrementValue())){
				loadParametersTO.setIsIncrementalGroup(Boolean.TRUE);
				blockTabs(INCREMENT);
			}
		
		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}
	
	private String getComboBoxParamNameValue() {
		UIComponent loadParameters = findComponent(PARAMETER_NAME_COMBOBOX);
		org.primefaces.component.selectonemenu.SelectOneMenu selectOneMenuLoadparameters = (SelectOneMenu) loadParameters;
		String code = (String) selectOneMenuLoadparameters.getValue();
		ParameterTO ParameterTO = adjustNameAndCodeOfLoadParametersObject(code, loadParametersType);
		return ParameterTO.getName();
	}
	
	private String getComboBoxParamID() {
		UIComponent loadParameters = findComponent(PARAMETER_NAME_COMBOBOX);
		org.primefaces.component.selectonemenu.SelectOneMenu selectOneMenuLoadparameters = (SelectOneMenu) loadParameters;
		String code = (String) selectOneMenuLoadparameters.getValue();
		ParameterTO ParameterTO = adjustNameAndCodeOfLoadParametersObject(code, loadParametersType);
		return ParameterTO.getId();
	}
	
	private void blockTabs(String methodName){
		if(UNIQUE.equals(methodName)){
			renderUploaderView = Boolean.FALSE;
			renderTextAreaView = Boolean.FALSE;
			renderLimitsValueView = Boolean.FALSE;
			renderUniqueValueInputText = Boolean.TRUE;
			renderFinalButtons = Boolean.TRUE;
			return;
		}
		if(INCREMENT.equals(methodName)){
			renderUploaderView = Boolean.FALSE;
			renderTextAreaView = Boolean.FALSE;
			renderLimitsValueView = Boolean.TRUE;
			renderUniqueValueInputText = Boolean.FALSE;
			renderFinalButtons = Boolean.TRUE;
			return;
		}
		if(AREA.equals(methodName)){
			renderUploaderView = Boolean.FALSE;
			renderTextAreaView = Boolean.TRUE;
			renderLimitsValueView = Boolean.FALSE;
			renderUniqueValueInputText = Boolean.FALSE;
			renderFinalButtons = Boolean.TRUE;
			return;
		}
		if(UPLOAD.equals(methodName)){
			renderUploaderView = Boolean.TRUE;
			renderTextAreaView = Boolean.FALSE;
			renderLimitsValueView = Boolean.FALSE;
			renderUniqueValueInputText = Boolean.FALSE;
			renderFinalButtons = Boolean.TRUE;
			return;
		}

	}
	
	private void unBlockAllTabs(){
		renderUploaderView = Boolean.TRUE;
		renderTextAreaView = Boolean.TRUE;
		renderLimitsValueView = Boolean.TRUE;
		renderUniqueValueInputText = Boolean.TRUE;
		renderFinalButtons = Boolean.TRUE;
		return;
	}
	
	private ParameterTO getParameterTOFromSessionByName(String name){
		ParameterTO selectedParam = new ParameterTO(name); 
		if(StringUtils.isNumeric(name)){
			selectedParam = adjustNameAndCodeOfLoadParametersObject(name, loadParametersType);
		}
		Iterator<ParameterTO> it = loadParametersEB.getLoadParametersTOList().iterator();while(it.hasNext()){
			ParameterTO loopParam = it.next();
			if(StringUtils.equalsIgnoreCase(selectedParam.getId(),loopParam.getId())){
				return loopParam;				
			}
		}

		return new ParameterTO(getComboBoxParamNameValue());
	}
	
	public ISimulationService getSimulationService() {
		return simulationService;
	}

	public void setSimulationService(ISimulationService simulationService) {
		this.simulationService = simulationService;
	}

	public LoadParametersEB getLoadParametersEB() {
		return loadParametersEB;
	}

	public void setLoadParametersEB(LoadParametersEB loadParametersEB) {
		this.loadParametersEB = loadParametersEB;
	}

	public Map<String, String> getLoadParametersType() {
		try {
			List<ParameterTO> lstOfParams = simulationService.findAllParametersService();
                        
                        for(int i=0;i<lstOfParams.size();i++)
                        {
                            ParameterTO p = lstOfParams.get(i);
                            System.out.println("--- " + p.getAreaValue() + " " + p.getId() + " " + p.getSimulation() + " " + p.getName() + " " + p.getShowAsMaterial());
                        }
                        
			for (ParameterTO parameterTO : lstOfParams) {
                                /*System.err.println("--- " + (parameterTO.getAreaValue()==null) +
                                                   " \n" + (parameterTO.getShowAsMaterial()==null) );
				if(!parameterTO.getShowAsMaterial()){
					continue;
				}*/
				this.loadParametersType.put(parameterTO.getName(), parameterTO.getId());
                                this.loadParametersDefaultValues.put(parameterTO.getName(), parameterTO);
			}
		} catch (Exception e) {
			addExceptionMessage(e);
		}
		return loadParametersType;
	}

	public void setLoadParametersType(Map<String, String> loadParametersType) {
		this.loadParametersType = loadParametersType;
	}

	public UploadedFile getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(UploadedFile fileUpload) {
		this.fileUpload = fileUpload;
	}

	public Boolean getRenderUploaderView() {
		return renderUploaderView;
	}

	public void setRenderUploaderView(Boolean renderUploaderView) {
		this.renderUploaderView = renderUploaderView;
	}

	public Boolean getRenderTextAreaView() {
		return renderTextAreaView;
	}

	public void setRenderTextAreaView(Boolean renderTextAreaView) {
		this.renderTextAreaView = renderTextAreaView;
	}

	public Boolean getRenderLimitsValueView() {
		return renderLimitsValueView;
	}

	public void setRenderLimitsValueView(Boolean renderLimitsValueView) {
		this.renderLimitsValueView = renderLimitsValueView;
	}

	public Boolean getRenderUniqueValueInputText() {
		return renderUniqueValueInputText;
	}

	public void setRenderUniqueValueInputText(Boolean renderUniqueValueInputText) {
		this.renderUniqueValueInputText = renderUniqueValueInputText;
	}


	public Boolean getRenderFinalButtons() {
		return renderFinalButtons;
	}

	public void setRenderFinalButtons(Boolean renderFinalButtons) {
		this.renderFinalButtons = renderFinalButtons;
	}
	public ParameterTO getLoadParametersTO() {
		return loadParametersTO;
	}
	public void setLoadParametersTO(ParameterTO loadParametersTO) {
		this.loadParametersTO = loadParametersTO;
	}

        private String getFileContents(String defaultFile)
        throws IOException
        {
            FileInputStream f = new FileInputStream(new File("/home/panos/Dropbox/assisting_files/" + defaultFile));
            return (org.apache.commons.io.IOUtils.toString(f));
        }


}
