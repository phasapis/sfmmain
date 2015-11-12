package eu.sifem.model.to;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class MeshDict implements AbstractTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1928066585467013408L;
	public String convertToMeters;
	public List<String> vertices = new ArrayList<String>();
	public List<String> blocks  = new ArrayList<String>();
	public List<String> edges  = new ArrayList<String>();
	public Map<String, String> patchTypeMap = new HashMap<String, String>();
	
}
