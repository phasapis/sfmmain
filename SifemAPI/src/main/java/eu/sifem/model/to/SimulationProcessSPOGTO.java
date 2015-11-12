package eu.sifem.model.to;

public class SimulationProcessSPOGTO implements AbstractTO{
	
	private static final long serialVersionUID = -6889955818508912963L;

	private String subject;
	
	private String predicate;
	
	private String object;
	
	private String graph;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getGraph() {
		return graph;
	}

	public void setGraph(String graph) {
		this.graph = graph;
	}
	
	

}
