package eu.sifem.model.to;

import java.io.FileInputStream;
import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MessageFileTO implements Serializable{
	

	private static final long serialVersionUID = 2666985270324979723L;

	private ObjectId _id;
	
	private String fileName;

	private String canonicalPath;
	
	private Boolean processed = Boolean.FALSE;
	
	private String msgName;
	
		
	public String getMsgName() {
		return msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}

	@Transient
	private FileInputStream fisMessageFile;
	

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCanonicalPath() {
		return canonicalPath;
	}

	public void setCanonicalPath(String canonicalPath) {
		this.canonicalPath = canonicalPath;
	}

	public FileInputStream getFisMessageFile() {
		return fisMessageFile;
	}

	public void setFisMessageFile(FileInputStream fisMessageFile) {
		this.fisMessageFile = fisMessageFile;
	}
	
	
	

}
