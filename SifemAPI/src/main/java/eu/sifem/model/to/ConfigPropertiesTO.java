package eu.sifem.model.to;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author jbjares
 * 
 */
public class ConfigPropertiesTO {

	private Map<String,Map<String,String>> propertiesOfConfigFile = new HashMap<String,Map<String,String>>();

	public Map<String, Map<String, String>> getPropertiesOfConfigFile() {
		return propertiesOfConfigFile;
	}

	public void setPropertiesOfConfigFile(
			Map<String, Map<String, String>> propertiesOfConfigFile) {
		this.propertiesOfConfigFile = propertiesOfConfigFile;
	}

}
