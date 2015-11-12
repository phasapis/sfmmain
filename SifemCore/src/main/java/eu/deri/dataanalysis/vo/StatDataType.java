package eu.deri.dataanalysis.vo;

/**
 * @author swapnil
 * It is abstract class contains the common category of data.
 */
public abstract class StatDataType implements Cloneable{
	private boolean isRealNo=false;
	private boolean isQualitative=false;
	private boolean isQuantative=false;
	
	public boolean isQualitative() {
		return isQualitative;
	}

	public void setQualitative(boolean isQualitative) {
		this.isQualitative = isQualitative;
	}

	public boolean isQuantative() {
		return isQuantative;
	}

	public void setQuantative(boolean isQuantative) {
		this.isQuantative = isQuantative;
	}

	public boolean isRealNo() {
		return isRealNo;
	}

	public void setRealNo(boolean isRealNo) {
		this.isRealNo = isRealNo;
	}
	public StatDataType clone() throws CloneNotSupportedException {
		 return (StatDataType)super.clone();
	}
}
