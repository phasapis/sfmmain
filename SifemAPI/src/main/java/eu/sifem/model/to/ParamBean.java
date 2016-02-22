package eu.sifem.model.to;

import java.io.InputStream;

@Deprecated
public class ParamBean implements Cloneable,AbstractTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7191718935466913218L;
	private String materialType;
	private String pramaName;
	private String value="0";
	private InputStream uploadedData;
	
	
	
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getPramaName() {
		return pramaName;
	}
	public void setPramaName(String pramaName) {
		this.pramaName = pramaName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	public InputStream getUploadedData() {
		return uploadedData;
	}
	public void setUploadedData(InputStream uploadedData) {
		this.uploadedData = uploadedData;
	}
	
}
