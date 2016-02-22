package eu.sifem.model.to;

import java.io.InputStream;

public class PAKCRestServiceWrapperTO implements AbstractTO{
	
private static final long serialVersionUID = -7622290983547686018L;

	private String sessionID;
	
	private InputStream cfgFile;
	
	private InputStream datFile;
	
	private InputStream unvFile;
	
    private InputStream pImagFile;
    
    private InputStream dCenterLineFile;
    
    private InputStream pRealFile;
    
    private InputStream vMagnFile;
    
    private InputStream vPhaseFile;

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

	public InputStream getpImagFile() {
		return pImagFile;
	}

	public void setpImagFile(InputStream pImagFile) {
		this.pImagFile = pImagFile;
	}

	public InputStream getdCenterLineFile() {
		return dCenterLineFile;
	}

	public void setdCenterLineFile(InputStream dCenterLineFile) {
		this.dCenterLineFile = dCenterLineFile;
	}

	public InputStream getpRealFile() {
		return pRealFile;
	}

	public void setpRealFile(InputStream pRealFile) {
		this.pRealFile = pRealFile;
	}

	public InputStream getvMagnFile() {
		return vMagnFile;
	}

	public void setvMagnFile(InputStream vMagnFile) {
		this.vMagnFile = vMagnFile;
	}

	public InputStream getvPhaseFile() {
		return vPhaseFile;
	}

	public void setvPhaseFile(InputStream vPhaseFile) {
		this.vPhaseFile = vPhaseFile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    


}
