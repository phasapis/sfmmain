package eu.sifem.mb.controller;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import eu.sifem.model.ColumnModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import eu.sifem.mb.entitybean.LoadParametersEB;
import eu.sifem.model.to.ParameterTO;
import eu.sifem.service.ISimulationService;
import eu.sifem.utils.BasicFileTools;

import org.primefaces.event.CellEditEvent;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "loadParametersController") 
@ViewScoped
public class LoadParametersController extends GenericMB{

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		if(newValue != null && !newValue.equals(oldValue)) {
			//System.out.println( "Cell Changed Old: " + oldValue + ", New:" + newValue);
			StringWriter sw = new StringWriter();
			CSVPrinter csvFilePrinter = null;
			List<String> headers = new ArrayList<>();
			for(ColumnModel c : columns){
				headers.add(c.getProperty());
			}
			//csvFilePrinter.printRecord(headers);
			String[] tmp = new String[headers.size()];
			tmp = headers.toArray(tmp);
			CSVFormat csvFileFormat = CSVFormat.EXCEL.withHeader(tmp);
			try {
				csvFilePrinter = new CSVPrinter(sw, csvFileFormat);
				for (Map<String,String> row : rows) {
					List record = new ArrayList();
					for(String header : headers){
						record.add(row.get(header));
					}
					csvFilePrinter.printRecord(record);
				}
				csvFilePrinter.flush();
				csvFilePrinter.close();
				String newCsv = sw.toString().replace("\"","");
				//logger.info(newCsv);
				this.loadParametersTO.setAreaValue(newCsv);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


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
	private Boolean renderTextAreaView = Boolean.FALSE;
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

							renderTextAreaView = Boolean.TRUE;
                            String areaText = getFileContents(this.loadParametersDefaultValues.get(getComboBoxParamNameValue()).getDefaultFile());
                            this.loadParametersTO.setAreaValue(areaText);


							File csvData = new File(this.loadParametersDefaultValues.get(getComboBoxParamNameValue()).getDefaultFile());
							CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.EXCEL.withHeader());
							logger.info(parser.getHeaderMap());


							rows = new ArrayList<>();
							columns = new ArrayList<ColumnModel>();
							for(String columnKey : parser.getHeaderMap().keySet()) {
								columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
							}

							for (CSVRecord csvRecord : parser) {
								rows.add(csvRecord.toMap());
							}

                        }else{
							renderTextAreaView = Boolean.FALSE;
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
		//renderTextAreaView = Boolean.TRUE;
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
		FileInputStream f = new FileInputStream(new File(defaultFile)); //"/home/panos/Dropbox/assisting_files/" +


		return (org.apache.commons.io.IOUtils.toString(f));
	}


	/* Data table */

	public List<ColumnModel> columns;

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public List<Map<String,String>> rows;

	public void setRows(List<Map<String,String>> rows) {
		this.rows = rows;
	}



	public List<Map<String,String>> getRows() {

		return this.rows;
	}


	static public class ColumnModel implements Serializable {

		private String header;
		private String property;

		public ColumnModel(String header, String property) {
			this.header = header;
			this.property = property;
		}

		public String getHeader() {
			return header;
		}

		public String getProperty() {
			return property;
		}
	}
}
