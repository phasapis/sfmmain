package eu.sifem.model.to;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author jbjares
 * 
 */
@Document(collection = "TransformationSimulationTO") 
public class TransformationSimulationTO implements AbstractTO{
	private static final long serialVersionUID = 3279994744899312169L;

	private String queryStr;
	private String scriptStr;
	private String xName; 
	private String yName;
	private List<String> parameters;
	private String transformationName;
	private String datasourceTempPath;
	private List<String> datasourceTempPathByProjectNameList = new ArrayList<String>();
	private String workspace;
	
	
	
	
	
	public String getWorkspace() {
		return workspace;
	}
	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}
	public List<String> getDatasourceTempPathByProjectNameList() {
		return datasourceTempPathByProjectNameList;
	}
	public String getScriptStr() {
		return scriptStr;
	}
	public void setScriptStr(String scriptStr) {
		this.scriptStr = scriptStr;
	}
	public String getQueryStr() {
		return queryStr;
	}
	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	public String getxName() {
		return xName;
	}
	public void setxName(String xName) {
		this.xName = xName;
	}
	public String getyName() {
		return yName;
	}
	public void setyName(String yName) {
		this.yName = yName;
	}
	public List<String> getParameters() {
		return parameters;
	}
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}
	public String getTransformationName() {
		return transformationName;
	}
	public void setTransformationName(String transformationName) {
		this.transformationName = transformationName;
	}
	public String getDatasourceTempPath() {
		return datasourceTempPath;
	}
	public void setDatasourceTempPath(String datasourceTempPath) {
		this.datasourceTempPath = datasourceTempPath;
	}
	public List<String> getDatasourceTempPathBySimulationNameList() {
		return datasourceTempPathByProjectNameList;
	}
	public void setDatasourceTempPathByProjectNameList(
			List<String> datasourceTempPathByProjectNameList) {
		this.datasourceTempPathByProjectNameList = datasourceTempPathByProjectNameList;
	}

	
	

}
