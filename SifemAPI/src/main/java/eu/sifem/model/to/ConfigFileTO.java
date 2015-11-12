package eu.sifem.model.to;

import java.io.File;
import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ConfigFileTO implements AbstractTO{

	private static final long serialVersionUID = 2264973407094992053L;
	
	private ObjectId _id;
	
	private String projectName;
	
	private String simulationName;
	
	private String instanceName;
	
	private ObjectId cfgFileID;
	
	@Deprecated
	@Transient
	private File cfgFile;
	
	@Transient
	private InputStream  cfgInputStream;

	
	
	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public InputStream getCfgInputStream() {
		return cfgInputStream;
	}

	public void setCfgInputStream(InputStream cfgInputStream) {
		this.cfgInputStream = cfgInputStream;
	}

	@Deprecated
	public File getCfgFile() {
		return cfgFile;
	}

	@Deprecated
	public void setCfgFile(File cfgFile) {
		this.cfgFile = cfgFile;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public ObjectId getCfgFileID() {
		return cfgFileID;
	}

	public void setCfgFileID(ObjectId cfgFileID) {
		this.cfgFileID = cfgFileID;
	}

	
	
}
