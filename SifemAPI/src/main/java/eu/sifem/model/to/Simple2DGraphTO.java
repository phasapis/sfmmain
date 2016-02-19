package eu.sifem.model.to;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Simple2DGraphTO implements AbstractTO{

	private static final long serialVersionUID = -3372687427730611070L;

	private List<Double> xView = new LinkedList<Double>();
	
	private List<Double> yView = new LinkedList<Double>();

	public List<Double> getxView() {
		return xView;
	}

	public void setxView(List<Double> xView) {
		this.xView = xView;
	}

	public List<Double> getyView() {
		return yView;
	}

	public void setyView(List<Double> yView) {
		this.yView = yView;
	}

	

	
}
