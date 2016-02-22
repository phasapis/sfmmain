package eu.deri.dataanalysis.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.deri.dataanalysis.operation.Operation;
import eu.deri.dataanalysis.util.OperationName;

/**
 * 
 * @author swapnil/yaskha
 *
 */
public class Feature implements Cloneable{
	private String attrName;
	private StatDataType statDataType;
	private List<Object> lstOfData;
	private Map<OperationName,Integer> lstOfOperation;
	private String axis;
	private String minima;
	private String maxima;
	private String feature;
	
	public Feature() {
		lstOfData=new ArrayList<Object>();
	}
	public List<Object> getLstOfData() {
		return lstOfData;
	}
	public void setLstOfData(List<Object> lstOfData) {
		this.lstOfData = lstOfData;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public StatDataType getStatDataType() {
		return statDataType;
	}
	public void setStatDataType(StatDataType statDataType) {
		this.statDataType = statDataType;
	}
	public  Map<OperationName,Integer> getLstOfOperation() {
		return lstOfOperation;
	}
	public void setLstOfOperation(Map<OperationName,Integer> lstOfOperation) {
		this.lstOfOperation = lstOfOperation;
	}
	public String getAxis() {
		return axis;
	}
	public void setAxis(String axis) {
		this.axis = axis;
	}
	public String getMinima() {
		return minima;
	}
	public void setMinima(String minima) {
		this.minima = minima;
	}
	public String getMaxima() {
		return maxima;
	}
	public void setMaxima(String maxima) {
		this.maxima = maxima;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public Feature clone() throws CloneNotSupportedException {
		 return (Feature)super.clone();
	}
}
