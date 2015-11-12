package eu.sifem.model.to;

import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AcessoryFileTO implements AbstractTO{

	private static final long serialVersionUID = -7564208361537858630L;
	
	private ObjectId _id;
	
	private String projectName;
	
	private String simulationName;
	
	private String name;
	
	private String extension;
	
	@Transient
	private InputStream file;

	private ObjectId fileId;
	
	
	


	public ObjectId getFileId() {
		return fileId;
	}

	public void setFileId(ObjectId fileId) {
		this.fileId = fileId;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
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

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}
	
	

}
