package eu.sifem.model.to;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author jbjares
 * 
 */
public class BoundaryInternalConditionsTO implements AbstractTO{

	private static final long serialVersionUID = 2264973407094992053L;
	
	private List<BoundaryInternalConditionsItemTO> boundaryInternalConditionsItems = new ArrayList<BoundaryInternalConditionsItemTO>();
	
	private Integer lenOut;


	public List<BoundaryInternalConditionsItemTO> getBoundaryInternalConditionsItems() {
		return boundaryInternalConditionsItems;
	}

	public void setBoundaryInternalConditionsItems(
			List<BoundaryInternalConditionsItemTO> boundaryInternalConditionsItems) {
		this.boundaryInternalConditionsItems = boundaryInternalConditionsItems;
	}
	
	public void addBoundaryInternalConditionsItems(BoundaryInternalConditionsItemTO boundaryInternalConditionsItems) {
		this.boundaryInternalConditionsItems.add(boundaryInternalConditionsItems);
	}

	public Integer getLenOut() {
		return lenOut;
	}

	public void setLenOut(Integer lenOut) {
		this.lenOut = lenOut;
	}
	
	

}
