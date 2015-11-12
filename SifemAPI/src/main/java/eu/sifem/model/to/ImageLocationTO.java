package eu.sifem.model.to;


import java.io.Serializable;
 

/**
 * 
 * @author jbjares
 * 
 */
public class ImageLocationTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String location;
	private int n;
	private StreamedContentTO content;
	public String getLocation() {
		return location;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public StreamedContentTO getContent() {
		return content;
	}

	public void setContent(StreamedContentTO content) {
		this.content = content;
	}
	
	
	 
}
