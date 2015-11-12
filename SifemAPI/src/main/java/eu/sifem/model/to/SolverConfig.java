package eu.sifem.model.to;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jbjares
 * 
 */
public class SolverConfig implements AbstractTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3009027605456915605L;
	private String solverName;
	private String parameter;
	private String numMethod;
	private List<String> lstOfAttr;

	
	public SolverConfig() {
		lstOfAttr = new ArrayList<String>();
	}

	public void add(String param){
		lstOfAttr.add(param);
	}
	
	public String getSolverName() {
		return solverName;
	}

	public void setSolverName(String solverName) {
		this.solverName = solverName;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getNumMethod() {
		return numMethod;
	}

	public void setNumMethod(String numMethod) {
		this.numMethod = numMethod;
	}

	public List<String> getLstOfAttr() {
		return lstOfAttr;
	}

	public void setLstOfAttr(List<String> lstOfAttr) {
		this.lstOfAttr = lstOfAttr;
	}
	
	
}
