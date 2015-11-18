package eu.sifem.model.enums;

import java.util.EnumSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


public enum ParameterTypes {
	
	MESH("MESH DIVISIONS"),
	GEOMETRY("GEOMETRY PARAMETERS"),
	FREQUENCY("LOAD PARAMETERS"),
	FLUID("MATERIAL PROPERTIES"),
	YOUNG("YOUNG MODULUS FUNCTION"),
	DAMPING("DAMPING FUNCTION"),
	EXTERNAL("EXTERNAL LOAD PARAMETERS"),
        
        // UCL Cochlea Tapered model specific parameters
        CONDUCTION("CONDUCTION TYPE"),
        SOLID("SOLID"),
        ROUNDWINDOW("ROUND WINDOW"),
        BASILARMEMMBRANE("BASILAR MEMMBRANE"),
        ANALYSIS("ANALYSIS TYPE"),
        
        
        FUNCT("FUNCTION"),
        DAMPING_MULTIPLIER("DAMPING MULTIPLIER"),
        
        DENSITY("DENSITY"),
        
        SHOULD_PRESCRIBE_PRESSURE("SHOULD_PRESCRIBE_PRESSURE"),
        SHOULD_PRESCRIBE_FORCE("SHOULD_PRESCRIBE_FORCE"),
        SLICE_FREQUENCY("FREQUENCY_VALUE"),

        MATERIALS("MATERIALS");
        
	private String name;
	
	ParameterTypes(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public static ParameterTypes getParameterTypesByTemplateName(String templateName){
		if(templateName==null || "".equals(templateName)){
			return null;
		}
		Set<ParameterTypes> parameterTypes = EnumSet.allOf(ParameterTypes.class);
		for(ParameterTypes parameterType : parameterTypes) {
			if(StringUtils.equalsIgnoreCase(StringUtils.deleteWhitespace(templateName).trim(), StringUtils.deleteWhitespace(parameterType.getName()).trim())){
				return parameterType;				
			}
		}
		return null;
	}

}