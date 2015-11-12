package eu.sifem.model.to;

public class DataSetTO implements AbstractTO {

	private static final long serialVersionUID = 8039481850972177478L;
	
	private DataSetHashCacheTO cache = new DataSetHashCacheTO();
	
	private String namedGraph;

	
	public DataSetTO() {}

	public DataSetTO(DataSetHashCacheTO cache, String namedGraph) {
		super();
		this.cache = cache;
		this.namedGraph = namedGraph;
	}
	

	public String getNamedGraph() {
		return namedGraph;
	}

	public void setNamedGraph(String namedGraph) {
		this.namedGraph = namedGraph;
	}

	public DataSetHashCacheTO getCache() {
		return cache;
	}

	public void setCache(DataSetHashCacheTO cache) {
		this.cache = cache;
	}
	
	

}
