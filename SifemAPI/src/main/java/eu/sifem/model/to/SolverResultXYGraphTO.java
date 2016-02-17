package eu.sifem.model.to;

import org.bson.types.ObjectId;

public class SolverResultXYGraphTO  implements AbstractTO{

	private static final long serialVersionUID = 2264973407094992053L;
	
	private ObjectId _id;
	
	private String projectID;
	
	private String simultionName;
	
	private  String xName;
	
	private  String yName;

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

	public String getSimultionName() {
		return simultionName;
	}

	public void setSimultionName(String simultionName) {
		this.simultionName = simultionName;
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
	
	
	
	
	
}
