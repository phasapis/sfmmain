package eu.sifem.model.to;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * 
 * @author jbjares
 * 
 */
public class ViewTO implements AbstractTO{
	
	private static final long serialVersionUID = 3403992771061945377L;

	private List<String> xView = new LinkedList<String>();
	
	private List<String> yView = new LinkedList<String>();
	
	private List<String> zView = new LinkedList<String>();
	
	private Map<String, List<String>> dimValMap = new TreeMap<String, List<String>>();

	public List<String> getxView() {
		return xView;
	}

	public void setxView(List<String> xView) {
		this.xView = xView;
	}

	public List<String> getyView() {
		return yView;
	}

	public void setyView(List<String> yView) {
		this.yView = yView;
	}

	public List<String> getzView() {
		return zView;
	}

	public void setzView(List<String> zView) {
		this.zView = zView;
	}

	public Map<String, List<String>> getDimValMap() {
		return dimValMap;
	}

	public void setDimValMap(Map<String, List<String>> dimValMap) {
		this.dimValMap = dimValMap;
	}


	
	

}