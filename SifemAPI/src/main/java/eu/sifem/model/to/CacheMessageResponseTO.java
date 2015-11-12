package eu.sifem.model.to;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CacheMessageResponseTO implements AbstractTO{

	private static final long serialVersionUID = 2264973407094992053L;
	
	private Map<String,List<String>> dataSet = new LinkedHashMap<String,List<String>>();

	public Map<String, List<String>> getDataSet() {
		return dataSet;
	}

	public void setDataSet(Map<String, List<String>> dataSet) {
		this.dataSet = dataSet;
	}
	
	

}
