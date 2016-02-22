package eu.sifem.model.to;

import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DatAndUnvTTLTO implements AbstractTO{

	private static final long serialVersionUID = 2264973407094992053L;
	
	private String projectName;
	
	private String simulationName;
	
	private String instanceName;
	
	private ObjectId datFileID;
	
	private ObjectId unvFileID;
	
	private String type;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Transient
	private InputStream datFile;

	@Transient
	private InputStream unvFile;

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

	public ObjectId getDatFileID() {
		return datFileID;
	}

	public void setDatFileID(ObjectId datFileID) {
		this.datFileID = datFileID;
	}

	public ObjectId getUnvFileID() {
		return unvFileID;
	}

	public void setUnvFileID(ObjectId unvFileID) {
		this.unvFileID = unvFileID;
	}

	public InputStream getDatFile() {
		return datFile;
	}

	public void setDatFile(InputStream datFile) {
		this.datFile = datFile;
	}

	public InputStream getUnvFile() {
		return unvFile;
	}

	public void setUnvFile(InputStream unvFile) {
		this.unvFile = unvFile;
	}


	

	
}
