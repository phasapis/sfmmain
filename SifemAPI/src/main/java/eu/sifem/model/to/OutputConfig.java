package eu.sifem.model.to;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jbjares
 * 
 */
public class OutputConfig implements AbstractTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8299423098617354681L;
	private String type;
	private List<String> lstOfParams;

	public OutputConfig() {
		lstOfParams = new ArrayList<String>();
	}
	
	public void add(String param){
		lstOfParams.add(param);
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getLstOfParams() {
		return lstOfParams;
	}

	public void setLstOfParams(List<String> lstOfParams) {
		this.lstOfParams = lstOfParams;
	}
	
	
}
