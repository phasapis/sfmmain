package eu.sifem.mb.entitybean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.SolverTO;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "solverSetupEB") 
@SessionScoped
public class SolverSetupEB implements Serializable{

	private static final long serialVersionUID = -1082251346108322630L;

	private SolverTO solverTO = new SolverTO();

	public SolverTO getSolverTO() {
		return solverTO;
	}

	public void setSolverTO(SolverTO solverTO) {
		this.solverTO = solverTO;
	}


	
	

}
