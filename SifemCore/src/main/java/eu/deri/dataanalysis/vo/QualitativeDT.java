package eu.deri.dataanalysis.vo;

/**
 * @author swapnil
 * Qualitative data is a categorical measurement expressed not in terms 
 * of numbers, but rather by means of a natural language description
 */
public class QualitativeDT extends StatDataType implements Cloneable{
	private boolean isNominal;
	private boolean isOrdinal;
	private boolean isBionimal;
	private boolean isCategorical;
	
	public QualitativeDT() {
		setQualitative(true);
	}
	
	public boolean isCategorical() {
		return isCategorical;
	}

	public void setCategorical(boolean isCategorical) {
		this.isCategorical = isCategorical;
	}

	public boolean isNominal() {
		return isNominal;
	}
	public void setNominal(boolean isNominal) {
		this.isNominal = isNominal;
	}
	public boolean isOrdinal() {
		return isOrdinal;
	}
	public void setOrdinal(boolean isOrdinal) {
		this.isOrdinal = isOrdinal;
	}
	public boolean isBionimal() {
		return isBionimal;
	}
	public void setBionimal(boolean isBionimal) {
		this.isBionimal = isBionimal;
	}
	public QualitativeDT clone() throws CloneNotSupportedException {
		 return (QualitativeDT)super.clone();
	}
}
