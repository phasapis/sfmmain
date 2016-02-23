package eu.sifem.model.to;

import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SolverResultFilesTO implements AbstractTO{

	private static final long serialVersionUID = 2264973407094992053L;
	
	private ObjectId _id;
	
	private String projectID;
	
	private String projectName;
	
	private String simulationName;
	
	private String simulationID;
	
	private String instanceName;
	
	private ObjectId pimagFileID;
	
	private ObjectId dcenterlineFileID;
	
	private ObjectId prealFileID;
	
	private ObjectId vmagnFileID;
	
	private ObjectId vphaseFileID;
	
	
	@Transient
	private InputStream pimagFile;
	
	@Transient
	private InputStream dcenterlineFile;
	
	@Transient
	private InputStream prealFile;
	
	@Transient
	private InputStream vmagnFile;
	
	@Transient
	private InputStream vphaseFile;

	
	
	
	public String getSimulationID() {
		return simulationID;
	}

	public void setSimulationID(String simulationID) {
		this.simulationID = simulationID;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
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

	public ObjectId getPimagFileID() {
		return pimagFileID;
	}

	public void setPimagFileID(ObjectId pimagFileID) {
		this.pimagFileID = pimagFileID;
	}

	public ObjectId getDcenterlineFileID() {
		return dcenterlineFileID;
	}

	public void setDcenterlineFileID(ObjectId dcenterlineFileID) {
		this.dcenterlineFileID = dcenterlineFileID;
	}

	public ObjectId getPrealFileID() {
		return prealFileID;
	}

	public void setPrealFileID(ObjectId prealFileID) {
		this.prealFileID = prealFileID;
	}

	public ObjectId getVmagnFileID() {
		return vmagnFileID;
	}

	public void setVmagnFileID(ObjectId vmagnFileID) {
		this.vmagnFileID = vmagnFileID;
	}

	public ObjectId getVphaseFileID() {
		return vphaseFileID;
	}

	public void setVphaseFileID(ObjectId vphaseFileID) {
		this.vphaseFileID = vphaseFileID;
	}

	public InputStream getPimagFile() {
		return pimagFile;
	}

	public void setPimagFile(InputStream pimagFile) {
		this.pimagFile = pimagFile;
	}

	public InputStream getDcenterlineFile() {
		return dcenterlineFile;
	}

	public void setDcenterlineFile(InputStream dcenterlineFile) {
		this.dcenterlineFile = dcenterlineFile;
	}

	public InputStream getPrealFile() {
		return prealFile;
	}

	public void setPrealFile(InputStream prealFile) {
		this.prealFile = prealFile;
	}

	public InputStream getVmagnFile() {
		return vmagnFile;
	}

	public void setVmagnFile(InputStream vmagnFile) {
		this.vmagnFile = vmagnFile;
	}

	public InputStream getVphaseFile() {
		return vphaseFile;
	}

	public void setVphaseFile(InputStream vphaseFile) {
		this.vphaseFile = vphaseFile;
	}
	
	
	
	
}
