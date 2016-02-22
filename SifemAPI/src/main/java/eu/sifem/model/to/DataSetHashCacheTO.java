package eu.sifem.model.to;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class DataSetHashCacheTO  implements AbstractTO {

	private static final long serialVersionUID = 8039481850972177478L;
	
	private ObjectId _id;
	
	private String workspace;
	
	private  String projectName;
	
	private  String simulationName;
	
	private String projectID;
	
	private  String xName;
	
	private  String yName;
	
	private String zName = "";
	
	private String hash;
	
	private String frequency;
	
	private ViewTO viewTO;
	
	private String instanceName;
	
	
	

	public String getProjectID() {
		return projectID;
	}





	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}





	public String getInstanceName() {
		return instanceName;
	}





	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}





	public DataSetHashCacheTO() {}
	
	
	
	

	public ObjectId get_id() {
		return _id;
	}





	public void set_id(ObjectId _id) {
		this._id = _id;
	}





	public String getFrequency() {
		return frequency;
	}




	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}




	public String getWorkspace() {
		return workspace;
	}



	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}



	public DataSetHashCacheTO(String projectName, String simulationName,
			String xName, String yName, String zName) {
		super();
		this.projectName = projectName;
		this.simulationName = simulationName;
		this.xName = xName;
		this.yName = yName;
		this.zName = zName;
	}

	public DataSetHashCacheTO(String xName, String yName) {
		super();
		this.xName = xName;
		this.yName = yName;
	}

	public DataSetHashCacheTO(String projectName, String simulationName,
			String xName, String yName, String zName, String hash) {
		super();
		this.projectName = projectName;
		this.simulationName = simulationName;
		this.xName = xName;
		this.yName = yName;
		this.zName = zName;
		this.hash = hash;
	}

	

	public ViewTO getViewTO() {
		return viewTO;
	}



	public void setViewTO(ViewTO viewTO) {
		this.viewTO = viewTO;
	}



	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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
	
	

}
