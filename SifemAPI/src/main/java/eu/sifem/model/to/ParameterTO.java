package eu.sifem.model.to;

import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import eu.sifem.annotation.HowTranslateMe;

/**
 * 
 * @author jbjares
 * 
 */
@SuppressWarnings("rawtypes")
@HowTranslateMe(method="json",rdfhashcode="e69d7522-cd2e-432a-94c9-9775bf5d3999", ignoreRdfhashcodeIfAttributeIsFilled = "simulation")
public class ParameterTO implements Comparable,AbstractTO{

	private static final long serialVersionUID = -7863956824726636961L;
	
	@Expose(serialize = true, deserialize = true)
	@SerializedName("id")
	private String id;
	
	@Expose(serialize = true, deserialize = true)
	@SerializedName("name")
	private String name;
	
	@Expose(serialize = true, deserialize = true)
	@SerializedName("values")
	private Map<String,String> values = new TreeMap<String, String>();
	
	@Expose(serialize = true, deserialize = true)
	@SerializedName("isUniqueValue")
	private Boolean isIncrementalGroup = Boolean.FALSE;
	
	@Expose(serialize = true, deserialize = true)
	@SerializedName("description")
	private String description;

        
	@Expose(serialize = true, deserialize = true)
	@SerializedName("simulation")
	private String simulation;
        
	@Expose(serialize = true, deserialize = true)
	@SerializedName("templateName")
	private String templateName;
	
	private Boolean showAsMaterial;

	private String parameterUniqueValue = new String("");
	
	private String incrementalInitialValue;
	
	private String incrementalFinalValue;
	
	private String incrementalIncrementValue;

	private String defaultFile = new String("");
        
        private String measurementUnit = new String("");
                
	private InputStream parameterFile;
	
	private String areaValue;

        public String getDefaultFile() {
            return defaultFile;
        }

        public void setDefaultFile(String defaultFile) {
            this.defaultFile = defaultFile;
        }
        
	public Boolean getShowAsMaterial() {
		return showAsMaterial;
	}

	public void setShowAsMaterial(Boolean showAsMaterial) {
		this.showAsMaterial = showAsMaterial;
	}

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public ParameterTO(String name) {
		this.name=name;
	}
	
	public ParameterTO() {}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParameterUniqueValue() {
		return parameterUniqueValue;
	}

	public void setParameterUniqueValue(String parameterUniqueValue) {
		this.parameterUniqueValue = parameterUniqueValue;
	}

	public String getIncrementalInitialValue() {
		return incrementalInitialValue;
	}

	public void setIncrementalInitialValue(String incrementalInitialValue) {
		this.incrementalInitialValue = incrementalInitialValue;
	}

	public String getIncrementalFinalValue() {
		return incrementalFinalValue;
	}

	public void setIncrementalFinalValue(String incrementalFinalValue) {
		this.incrementalFinalValue = incrementalFinalValue;
	}

	public String getIncrementalIncrementValue() {
		return incrementalIncrementValue;
	}

	public void setIncrementalIncrementValue(String incrementalIncrementValue) {
		this.incrementalIncrementValue = incrementalIncrementValue;
	}

	public InputStream getParameterFile() {
		return parameterFile;
	}

	public void setParameterFile(InputStream parameterFile) {
		this.parameterFile = parameterFile;
	}

	public String getAreaValue() {
		return areaValue;
	}

	public void setAreaValue(String areaValue) {
		this.areaValue = areaValue;
	}

	public Boolean getIsIncrementalGroup() {
		return isIncrementalGroup;
	}

	public void setIsIncrementalGroup(Boolean isIncrementalGroup) {
		this.isIncrementalGroup = isIncrementalGroup;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSimulation() {
		return simulation;
	}

	public void setSimulation(String simulation) {
		this.simulation = simulation;
	}

        public String getMeasurementUnit() {
            return measurementUnit;
        }

        public void setMeasurementUnit(String measurementUnit) {
            this.measurementUnit = measurementUnit;
        }



	@Override
	public int compareTo(Object o) {
		return this.getId().compareTo(((ParameterTO)o).getId());
	}
}
