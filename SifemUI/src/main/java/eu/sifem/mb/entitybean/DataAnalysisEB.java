package eu.sifem.mb.entitybean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.DataAnalysisTO;

/**
 * 
 * @author yaskha
 *
 */

@ManagedBean(name = "dataAnalysisEB") 
@SessionScoped
public class DataAnalysisEB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4236248141437790561L;
	
	private DataAnalysisTO dataAnalysisTO = new DataAnalysisTO();

	public DataAnalysisTO getDataAnalysisTO() {
		return dataAnalysisTO;
	}

	public void setDataAnalysisTO(DataAnalysisTO dataAnalysisTO) {
		this.dataAnalysisTO = dataAnalysisTO;
	}

}
