package eu.sifem.mb.entitybean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import eu.sifem.model.to.RulesValidationTO;

/**
 * 
 * @author yaskha
 *
 */

@ManagedBean(name = "rulesValidationEB") 
@SessionScoped
public class RulesValidationEB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1902987753477851605L;
	
	private List<RulesValidationTO> rulesValidationTOList = new ArrayList<RulesValidationTO>();
	
	private List<RulesValidationTO> selectedRulesValidationTOList = new ArrayList<RulesValidationTO>();

	public List<RulesValidationTO> getRulesValidationTOList() {
		return rulesValidationTOList;
	}

	public void setRulesValidationTOList(
			List<RulesValidationTO> rulesValidationTOList) {
		this.rulesValidationTOList = rulesValidationTOList;
	}

	public List<RulesValidationTO> getSelectedRulesValidationTOList() {
		return selectedRulesValidationTOList;
	}

	public void setSelectedRulesValidationTOList(
			List<RulesValidationTO> selectedRulesValidationTOList) {
		this.selectedRulesValidationTOList = selectedRulesValidationTOList;
	}

	public void addRulesValidationTOList(List<RulesValidationTO> rulesValidationTOList) {
				
		Iterator<RulesValidationTO> rulIter = rulesValidationTOList.iterator();
		while(rulIter.hasNext()) {
			RulesValidationTO rulesValidationTO = rulIter.next();
			System.out.println("Label of Rule: " + rulesValidationTO.getRuleLabel());
			this.rulesValidationTOList.add(rulesValidationTO);
		}
	}

}
