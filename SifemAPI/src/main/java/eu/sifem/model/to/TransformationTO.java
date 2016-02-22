package eu.sifem.model.to;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author jbjares
 * 
 */
@Document(collection = "TransformationTO") 
public class TransformationTO implements AbstractTO{
	private static final long serialVersionUID = 3279994744899312169L;
	
	private ObjectId _id;
	
	private String name;
	
	private ObjectId queryByteArrayID;
	
	private ObjectId scriptByteArrayID;
	
	private String projectName;
	
	private String simulationName;
	
	private List<String> parameters = new ArrayList<String>();
	
	private Boolean isInUse;
	
	private List<String> namedGraphs = new ArrayList<String>();
	
	private String projectID;
	
	
	
	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public List<String> getNamedGraphs() {
		return namedGraphs;
	}

	public void setNamedGraphs(List<String> namedGraphs) {
		this.namedGraphs = namedGraphs;
	}

	@Transient
	private InputStream queryFile;
	
	@Transient
	private InputStream scriptFile;
	
	public TransformationTO(){}
	
	public TransformationTO(String projectName,String simulationName) {
		super();
		this.projectName = projectName;
		this.simulationName = simulationName;
	}

	public TransformationTO(ObjectId id) {
		super();
		this._id = id;
	}
	

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public ObjectId getQueryByteArrayID() {
		return queryByteArrayID;
	}

	public void setQueryByteArrayID(ObjectId queryByteArrayID) {
		this.queryByteArrayID = queryByteArrayID;
	}

	public ObjectId getScriptByteArrayID() {
		return scriptByteArrayID;
	}

	public void setScriptByteArrayID(ObjectId scriptByteArrayID) {
		this.scriptByteArrayID = scriptByteArrayID;
	}

	public InputStream getQueryFile() {
		return queryFile;
	}

	public void setQueryFile(InputStream queryFile) {
		this.queryFile = queryFile;
	}

	public InputStream getScriptFile() {
		return scriptFile;
	}

	public void setScriptFile(InputStream scriptFile) {
		this.scriptFile = scriptFile;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSimulationName() {
		return simulationName;
	}

	public void setSimulationName(String simulationName) {
		this.simulationName = simulationName;
	}

	public List<String> getParameters() {
		return parameters;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public Boolean getIsInUse() {
		return isInUse;
	}

	public void setIsInUse(Boolean isInUse) {
		this.isInUse = isInUse;
	}


}
