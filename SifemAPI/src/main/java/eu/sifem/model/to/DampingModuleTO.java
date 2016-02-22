package eu.sifem.model.to;



/**
 * 
 * @author jbjares
 * 
 */
public class DampingModuleTO implements AbstractTO {

	private static final long serialVersionUID = 3996057136983211602L;
	
	private String filePathAndName;

	public String getFilePathAndName() {
		return filePathAndName;
	}

	public void setFilePathAndName(String filePathAndName) {
		this.filePathAndName = filePathAndName;
	}

}
