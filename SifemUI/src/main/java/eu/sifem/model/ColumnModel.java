package eu.sifem.model;

import java.io.Serializable;

public class ColumnModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String header;
	private String property;
	public ColumnModel(String header,String property) {
		 this.header=header;
		 this.property=property;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	@Override
	public boolean equals(Object obj) {
		ColumnModel col=(ColumnModel)obj;
		if(this.property.equalsIgnoreCase(col.property)){
			return true;
		}
		return false;
	}
}
