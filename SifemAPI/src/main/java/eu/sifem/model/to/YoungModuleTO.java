package eu.sifem.model.to;



@Deprecated
public class YoungModuleTO implements AbstractTO{
	private static final long serialVersionUID = 8112139706398636424L;
	
	private String filePathAndName;

	public String getFilePathAndName() {
		return filePathAndName;
	}

	public void setFilePathAndName(String filePathAndName) {
		this.filePathAndName = filePathAndName;
	}

}
