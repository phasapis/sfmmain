package eu.sifem.model.to;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Simple2DGraphTO implements AbstractTO{

	private static final long serialVersionUID = -3372687427730611070L;

	private HashMap<Double, Double> xyMap = new HashMap<Double, Double>();

	public HashMap<Double, Double> getXyMap() {
		return xyMap;
	}

	public void setXyMap(HashMap<Double, Double> xyMap) {
		this.xyMap = xyMap;
	}

	

	
}
