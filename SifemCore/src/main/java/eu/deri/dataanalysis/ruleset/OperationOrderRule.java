/**
 * 
 */
package eu.deri.dataanalysis.ruleset;

import java.util.ArrayList;
import java.util.List;

import eu.deri.dataanalysis.util.OperationName;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.PairFeature;

/**
 * @author swapnil
 *
 */
public class OperationOrderRule implements Rule {

	/* (non-Javadoc)
	 * @see eu.deri.dataanalysis.ruleset.Rule#test(java.util.List)
	 */
	public Object test(Object object) {
		PairFeature pairFeature=(PairFeature)object;
		List<OperationName> lstOfoperationName=new ArrayList<OperationName>();
		for(OperationName operationName:pairFeature.getComposer().getLstOfoperationName()){
			if(operationName==OperationName.DERVIATION){
				if(lstOfoperationName.size()<=0){
					lstOfoperationName.add(OperationName.DERVIATION);
				} else {
					lstOfoperationName.add(operationName);
					calculateOrder(operationName,lstOfoperationName);
				}
			} else if(operationName==OperationName.AVERAGE){
				if(lstOfoperationName.size()<=0){
					lstOfoperationName.add(OperationName.AVERAGE);
				} else {
					lstOfoperationName.add(operationName);
					calculateOrder(operationName,lstOfoperationName);
				}
			} else if(operationName==OperationName.AGGREGATION){
				if(lstOfoperationName.size()<=0){
					lstOfoperationName.add(OperationName.AGGREGATION);
				} else {
					lstOfoperationName.add(operationName);
					calculateOrder(operationName,lstOfoperationName);
				}
			} else if (operationName == OperationName.DATABEHAVIOUR) {
				if(lstOfoperationName.size()<=0){
					lstOfoperationName.add(OperationName.DATABEHAVIOUR);
				} else {
					lstOfoperationName.add(operationName);
					calculateOrder(operationName,lstOfoperationName);
				}
			} else if (operationName == OperationName.AMPLITUDECALCULATION) {
				if(lstOfoperationName.size()<=0){
					lstOfoperationName.add(OperationName.AMPLITUDECALCULATION);
				} else {
					lstOfoperationName.add(operationName);
					calculateOrder(operationName,lstOfoperationName);
				}
			}  else if (operationName == OperationName.DISTANCECALCULATION) {
				if(lstOfoperationName.size()<=0){
					lstOfoperationName.add(OperationName.DISTANCECALCULATION);
				} else {
					lstOfoperationName.add(operationName);
					calculateOrder(operationName,lstOfoperationName);
				}
			}
		}
		return lstOfoperationName;
	}
	private void  calculateOrder(OperationName operationName,List<OperationName> lstOfoperationName){
		for(Integer i=0;i<lstOfoperationName.size();i++) {
			if(lstOfoperationName.get(i)==OperationName.DERVIATION) {
				for(Integer j=0;j<lstOfoperationName.size();j++) {
					if(lstOfoperationName.get(j)==OperationName.AVERAGE && i<j){
						 
						lstOfoperationName.set(i,OperationName.AVERAGE);
						lstOfoperationName.set(j,OperationName.DERVIATION);
					} else if(lstOfoperationName.get(j)==OperationName.AGGREGATION && i>j){
						 
						lstOfoperationName.set(i,OperationName.DERVIATION);
						lstOfoperationName.set(j,OperationName.AGGREGATION);
					} else if(lstOfoperationName.get(j)==OperationName.AMPLITUDECALCULATION && i<j){
						
						lstOfoperationName.set(i,OperationName.AMPLITUDECALCULATION);
						lstOfoperationName.set(j,OperationName.DERVIATION);
					}
				}
			} else if(lstOfoperationName.get(i)==OperationName.AVERAGE) {
				for(Integer j=0;j<lstOfoperationName.size();j++) {
					if(lstOfoperationName.get(j)==OperationName.DERVIATION && i>j){
						 
						lstOfoperationName.set(i,OperationName.DERVIATION);
						lstOfoperationName.set(j,OperationName.AVERAGE);
					} else if(lstOfoperationName.get(j)==OperationName.AGGREGATION && i>j){
						 
						lstOfoperationName.set(i,OperationName.AVERAGE);
						lstOfoperationName.set(j,OperationName.AGGREGATION);
					} 
				}
			} else if(lstOfoperationName.get(i)==OperationName.AGGREGATION) {
				for(Integer j=0;j<lstOfoperationName.size();j++) {
					if(lstOfoperationName.get(j)==OperationName.DERVIATION && i<j){
						 
						lstOfoperationName.set(i,OperationName.DERVIATION);
						lstOfoperationName.set(j,OperationName.AGGREGATION);
						
					} else if(lstOfoperationName.get(j)==OperationName.AVERAGE && i<j){
						 
						lstOfoperationName.set(i,OperationName.AVERAGE);
						lstOfoperationName.set(j,OperationName.AGGREGATION);
					} 
				}
			} else if(lstOfoperationName.get(i)==OperationName.AMPLITUDECALCULATION) {
				for(Integer j=0;j<lstOfoperationName.size();j++) {
					if(lstOfoperationName.get(j)==OperationName.DERVIATION && i>j){
						 
						lstOfoperationName.set(i,OperationName.AMPLITUDECALCULATION);
						//lstOfoperationName.set(j,OperationName.DERVIATION);
						
					}
				}
			} else if(lstOfoperationName.get(i)==OperationName.DATABEHAVIOUR) {
				for(Integer j=0;j<lstOfoperationName.size();j++) {
					if(lstOfoperationName.get(j)==OperationName.DERVIATION && i<j){
						 
						lstOfoperationName.set(i,OperationName.DATABEHAVIOUR);
						lstOfoperationName.set(j,OperationName.DERVIATION);
						
					} else if(lstOfoperationName.get(j)==OperationName.AVERAGE && i<j){
						 
						lstOfoperationName.set(i,OperationName.DATABEHAVIOUR);
						lstOfoperationName.set(j,OperationName.AVERAGE);
					}  else if(lstOfoperationName.get(j)==OperationName.COUNT && i<j){
						
						lstOfoperationName.set(i,OperationName.DATABEHAVIOUR);
						lstOfoperationName.set(j,OperationName.COUNT);
					} else if(lstOfoperationName.get(j)==OperationName.AGGREGATION && i>j){
						 
						lstOfoperationName.set(i,OperationName.DATABEHAVIOUR);
						lstOfoperationName.set(j,OperationName.AGGREGATION);
					} 
				}
			} else if(lstOfoperationName.get(i)==OperationName.DISTANCECALCULATION) {
				for(Integer j=0;j<lstOfoperationName.size();j++) {
					if(lstOfoperationName.get(j)==OperationName.DERVIATION && i<j){
						 
						lstOfoperationName.set(i,OperationName.DATABEHAVIOUR);
						lstOfoperationName.set(j,OperationName.DERVIATION);
						
					} else if(lstOfoperationName.get(j)==OperationName.AVERAGE && i<j){
						 
						lstOfoperationName.set(i,OperationName.DATABEHAVIOUR);
						lstOfoperationName.set(j,OperationName.AVERAGE);
					}  else if(lstOfoperationName.get(j)==OperationName.COUNT && i<j){
						
						lstOfoperationName.set(i,OperationName.DISTANCECALCULATION);
						lstOfoperationName.set(j,OperationName.COUNT);
					} else if(lstOfoperationName.get(j)==OperationName.AGGREGATION && i>j){
						 
						lstOfoperationName.set(i,OperationName.DATABEHAVIOUR);
						lstOfoperationName.set(j,OperationName.AGGREGATION);
					} 
				}
			}
		}
	}
}
