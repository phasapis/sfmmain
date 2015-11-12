package eu.sifem.model.to;

import java.io.InputStream;


/**
 * 
 * @author jbjares
 * 
 */
public class StreamedContentTO implements AbstractTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -305833406578669863L;
	private InputStream stream;
	
	private String contentType;
    
	private String name;
    
    private String contentEncoding;
	
	public StreamedContentTO() {}
	
	public StreamedContentTO(InputStream stream) {
		this.stream = stream;
	}
	
	public StreamedContentTO(InputStream stream, String contentType) {
		this.contentType = contentType;
		this.stream = stream;
	}
	
	public StreamedContentTO(InputStream stream, String contentType, String name) {
		this.contentType = contentType;
		this.stream = stream;
		this.name = name;
	}
    
    public StreamedContentTO(InputStream stream, String contentType, String name, String contentEncoding) {
		this.contentType = contentType;
		this.stream = stream;
		this.name = name;
        this.contentEncoding = contentEncoding;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }    
    public String getContentEncoding() {
        return contentEncoding;
    }

}
