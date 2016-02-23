package eu.sifem.model.to;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SimulationInstanceTO implements AbstractTO{

	private static final long serialVersionUID = -7622290983547686018L;

	private ObjectId _id;
	
	private byte[] cfgFile;
	
	private List<File> datFileList = new ArrayList<File>();
	
	private List<File> unvFileList = new ArrayList<File>();
	
	private File dataFile;
	
	private File unvFile;
	
	private String preparedQuery;
	
	private String simulationURLPrefix;
	
	private  String outputTempBasePath;
	
	private  String outputTempTTLPath;
	
	private  String projectName;
	
	private  String simulationName;
	
	private String simulationID;
	
	private String projectID;
	
	private  String instanceName;
	
	private String workspacebasePath;
	
	private String fileName;
	
	private String xName;
	
	private String yName;
	
	private String zName;
	
	private Map<Integer,Map<String,File>> instanceFiles = new TreeMap<Integer,Map<String,File>>();
	
	
	

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getSimulationID() {
		return simulationID;
	}

	public void setSimulationID(String simulationID) {
		this.simulationID = simulationID;
	}

	public Map<Integer, Map<String, File>> getInstanceFiles() {
		return instanceFiles;
	}

	public void setInstanceFiles(Map<Integer, Map<String, File>> instanceFiles) {
		this.instanceFiles = instanceFiles;
	}
	
	

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
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

	public String getzName() {
		return zName;
	}

	public void setzName(String zName) {
		this.zName = zName;
	}

	public SimulationInstanceTO() {}

	public SimulationInstanceTO(String preparedQuery) {
		this.preparedQuery=preparedQuery;
	}


	public String getPreparedQuery() {
		return preparedQuery;
	}


	public void setPreparedQuery(String preparedQuery) {
		this.preparedQuery = preparedQuery;
	}

	public byte[] getCfgFile() {
		return cfgFile;
	}

	public void setCfgFile(byte[] cfgFile) {
		this.cfgFile = cfgFile;
	}



	public String getSimulationURLPrefix() {
		return simulationURLPrefix;
	}

	public void setSimulationURLPrefix(String simulationURLPrefix) {
		this.simulationURLPrefix = simulationURLPrefix;
	}


	
	public String getOutputTempBasePath() {
		return outputTempBasePath;
	}

	public void setOutputTempBasePath(String outputTempBasePath) {
		this.outputTempBasePath = outputTempBasePath;
	}

	public String getOutputTempTTLPath() {
		return outputTempTTLPath;
	}

	public void setOutputTempTTLPath(String outputTempTTLPath) {
		this.outputTempTTLPath = outputTempTTLPath;
	}

	public String getSimulationName() {
		return simulationName;
	}

	public void setSimulationName(String simulationName) {
		this.simulationName = simulationName;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getWorkspacebasePath() {
		return workspacebasePath;
	}

	public void setWorkspacebasePath(String workspacebasePath) {
		this.workspacebasePath = workspacebasePath;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public File getDataFile() {
		return dataFile;
	}

	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}

	public File getUnvFile() {
		return unvFile;
	}

	public void setUnvFile(File unvFile) {
		this.unvFile = unvFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<File> getDatFileList() {
		return datFileList;
	}

	public void setDatFileList(List<File> datFileList) {
		this.datFileList = datFileList;
	}

	public List<File> getUnvFileList() {
		return unvFileList;
	}

	public void setUnvFileList(List<File> unvFileList) {
		this.unvFileList = unvFileList;
	}



	

}
