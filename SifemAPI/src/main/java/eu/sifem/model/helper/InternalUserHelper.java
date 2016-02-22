package eu.sifem.model.helper;

import java.io.Serializable;

public class InternalUserHelper implements Serializable{

	private static final long serialVersionUID = -151617887892515445L;
	
	
	private String user;
	
	
	private String pass;


	private String cmdHelper;
	
	
	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}


	public String getCmdHelper() {
		return cmdHelper;
	}


	public void setCmdHelper(String cmdHelper) {
		this.cmdHelper = cmdHelper;
	}
	
	
	
	

}
