package eu.sifem.model.to;

import java.io.InputStream;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DanAndUnvQueryResultTO {
	
	private String instanceName;
	
	private String instanceCode;
	
	private String fileName;
	
	private InputStream File;

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}


	public String getInstanceCode() {
		return instanceCode;
	}

	public void setInstanceCode(String instanceCode) {
		this.instanceCode = instanceCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getFile() {
		return File;
	}

	public void setFile(InputStream file) {
		File = file;
	}
	
	

}
