package eu.sifem.model.to;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SolverResultXYGraphTO  implements AbstractTO{

	private static final long serialVersionUID = 2264973407094992053L;
	
	private ObjectId _id;
	
	private String projectID;
	
	private CenterlineTO centerlineTO;
	
	private PimagTO pimagTO;
	
	private PrealTO prealTO;
	
	private VmagnTO vmagnTO;
	
	private VphaseTO vphaseTO;
	
	

	public CenterlineTO getCenterlineTO() {
		return centerlineTO;
	}

	public void setCenterlineTO(CenterlineTO centerlineTO) {
		this.centerlineTO = centerlineTO;
	}

	public PimagTO getPimagTO() {
		return pimagTO;
	}

	public void setPimagTO(PimagTO pimagTO) {
		this.pimagTO = pimagTO;
	}

	public PrealTO getPrealTO() {
		return prealTO;
	}

	public void setPrealTO(PrealTO prealTO) {
		this.prealTO = prealTO;
	}

	public VmagnTO getVmagnTO() {
		return vmagnTO;
	}

	public void setVmagnTO(VmagnTO vmagnTO) {
		this.vmagnTO = vmagnTO;
	}

	public VphaseTO getVphaseTO() {
		return vphaseTO;
	}

	public void setVphaseTO(VphaseTO vphaseTO) {
		this.vphaseTO = vphaseTO;
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


	
	
}
