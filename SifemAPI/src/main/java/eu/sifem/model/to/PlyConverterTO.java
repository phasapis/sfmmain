package eu.sifem.model.to;


public class PlyConverterTO implements AbstractTO{

	private static final long serialVersionUID = -7622290983547686018L;

	
	private byte[] inputDatFile;
	
	private String hash;
	
	private String plyName;

	public byte[] getInputDatFile() {
		return inputDatFile;
	}

	public void setInputDatFile(byte[] inputDatFile) {
		this.inputDatFile = inputDatFile;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPlyName() {
		return plyName;
	}

	public void setPlyName(String plyName) {
		this.plyName = plyName;
	}

	
}
