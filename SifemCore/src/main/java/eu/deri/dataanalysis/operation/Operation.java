/**
 * 
 */
package eu.deri.dataanalysis.operation;

import java.util.List;

import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

/**
 * @author swapnil
 * It is an interface for all statistical operation.
 */
public interface Operation {
	public Object perform(List<PairFeature> object);
}
