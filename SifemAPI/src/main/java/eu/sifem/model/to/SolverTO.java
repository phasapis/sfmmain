/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.sifem.model.to;

import java.util.List;


/**
 * 
 * @author jbjares
 * 
 */
public class SolverTO implements AbstractTO{
    /**
	 * 
	 */
	private static final long serialVersionUID = -466284845539798101L;
	private String name;
	
	private String type;
	
	private String method;
	
	private List<String> types;
	
	private List<String> methods;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public List<String> getMethods() {
		return methods;
	}

	public void setMethods(List<String> methods) {
		this.methods = methods;
	}


    
}
