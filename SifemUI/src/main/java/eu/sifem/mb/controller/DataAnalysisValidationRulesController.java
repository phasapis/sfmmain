package eu.sifem.mb.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import eu.sifem.mb.entitybean.DataAnalysisValidationRulesEB;


@ManagedBean(name="dataAnalysisValidationRulesController") 
@RequestScoped
public class DataAnalysisValidationRulesController {
	

	@ManagedProperty(value="#{dataAnalysisValidationRulesEB}")
	private DataAnalysisValidationRulesEB dataAnalysisValidationRulesEB;


	

}
