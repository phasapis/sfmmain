package eu.sifem.mb.entitybean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.DataAnalysisValidationRulesTO;
import eu.sifem.model.to.Rule;

/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name="dataAnalysisValidationRulesEB") 
@SessionScoped
public class DataAnalysisValidationRulesEB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6184303382033232414L;
	private DataAnalysisValidationRulesTO dataAnalysisValidationRulesTO = new DataAnalysisValidationRulesTO();

	public DataAnalysisValidationRulesTO getDataAnalysisValidationRulesTO() {
		return dataAnalysisValidationRulesTO;
	}

	public void setDataAnalysisValidationRulesTO(
			DataAnalysisValidationRulesTO dataAnalysisValidationRulesTO) {
		this.dataAnalysisValidationRulesTO = dataAnalysisValidationRulesTO;
	}
	
	
	
}
