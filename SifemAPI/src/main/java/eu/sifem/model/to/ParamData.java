package eu.sifem.model.to;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class ParamData implements AbstractTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5755519769914325390L;
	private String[] dimensions = new String[7];
	private String objectValue = null;
	private String fieldClass = null; 
	private String location = null;
	private boolean internalFieldUniform = false;

	private String uniformInternalFieldScalarValue = null;
	private List<String> nonUniformInternalFieldScalarValues = new ArrayList<String>();

	private String uniformInternalFieldVectorValue = null;
	private List<String> nonUniformInternalFieldVectorValues = new ArrayList<String>();

	private HashMap<String, HashMap<String, String>> boundaryPatches = new HashMap<String, HashMap<String, String>>();

	
	public String getLocation(){
		return location;	
	}

	
	public void setLocation(String location){
		this.location = location;
	}	

	
	public String[] getDimensions() {
		return dimensions;
	}

	
	public void setDimensions(String[] dimensions) {
		this.dimensions = dimensions;
	}

	
	public String getObjectValue() {
		return objectValue;
	}

	
	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	
	public String getFieldClass() {
		return fieldClass;
	}

	
	public void setFieldClass(String fieldClass) {
		this.fieldClass = fieldClass;
	}

	public boolean isInternalFieldUniform() {
		return internalFieldUniform;
	}

	
	public void setInternalFieldUniform(boolean internalFieldUniform) {
		this.internalFieldUniform = internalFieldUniform;
	}

	
	public String getUniformInternalFieldScalarValue() {
		return uniformInternalFieldScalarValue;
	}

	
	public void setUniformInternalFieldScalarValue(
			String uniformInternalFieldScalarValue) {
		this.uniformInternalFieldScalarValue = uniformInternalFieldScalarValue;
	}

	
	public List<String> getNonUniformInternalFieldScalarValues() {
		return nonUniformInternalFieldScalarValues;
	}

	
	public void setNonUniformInternalFieldScalarValues(
			List<String> nonUniformInternalFieldScalarValues) {
		this.nonUniformInternalFieldScalarValues = nonUniformInternalFieldScalarValues;
	}

	
	public String getUniformInternalFieldVectorValue() {
		return uniformInternalFieldVectorValue;
	}

	
	public void setUniformInternalFieldVectorValue(
			String uniformInternalFieldVectorValue) {
		this.uniformInternalFieldVectorValue = uniformInternalFieldVectorValue;
	}

	
	public List<String> getNonUniformInternalFieldVectorValues() {
		return nonUniformInternalFieldVectorValues;
	}

	
	public void setNonUniformInternalFieldVectorValues(
			List<String> nonUniformInternalFieldVectorValues) {
		this.nonUniformInternalFieldVectorValues = nonUniformInternalFieldVectorValues;
	}

	
	public HashMap<String, HashMap<String, String>> getBoundaryPatches() {
		return boundaryPatches;
	}

	
	public void setBoundaryPatches(HashMap<String, HashMap<String, String>> boundaryPatches) {
		this.boundaryPatches = boundaryPatches;
	}
	
	

}
