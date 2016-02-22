package eu.sifem.model.helper;
public class CombinationHelper{
	
	private Integer id;
	
	private String name;
	
	private Integer minIndex = 0;
	
	private Integer maxIndex;
	
	private Integer currentIndex;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMinIndex() {
		return minIndex;
	}

	public void setMinIndex(Integer minIndex) {
		this.minIndex = minIndex;
	}

	public Integer getMaxIndex() {
		return maxIndex;
	}

	public void setMaxIndex(Integer maxIndex) {
		this.maxIndex = maxIndex;
	}

	public Integer getCurrentIndex() {
		if(currentIndex==null){
			return getMinIndex();
		}
		if(currentIndex>getMaxIndex()){
			return getMinIndex();
		}
		return currentIndex;
	}

	public void setCurrentIndex(Integer currentIndex) {
		if(currentIndex==null){
			this.currentIndex = getMinIndex();
		}
		if(currentIndex>getMaxIndex()){
			this.currentIndex = getMinIndex();
		}
		this.currentIndex = currentIndex;
	}

	
	
	
	
}