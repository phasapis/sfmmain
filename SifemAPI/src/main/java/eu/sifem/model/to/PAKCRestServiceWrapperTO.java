package eu.sifem.model.to;

import java.io.InputStream;

public class PAKCRestServiceWrapperTO implements AbstractTO{
	
private static final long serialVersionUID = -7622290983547686018L;

	private String sessionID;
	
	private InputStream cfgFile;
	
	private InputStream datFile;
	
	private InputStream unvFile;
	
	

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public InputStream getCfgFile() {
		return cfgFile;
	}

	public void setCfgFile(InputStream cfgFile) {
		this.cfgFile = cfgFile;
	}

	public InputStream getDatFile() {
		return datFile;
	}

	public void setDatFile(InputStream datFile) {
		this.datFile = datFile;
	}

	public InputStream getUnvFile() {
		return unvFile;
	}

	public void setUnvFile(InputStream unvFile) {
		this.unvFile = unvFile;
	}

}
