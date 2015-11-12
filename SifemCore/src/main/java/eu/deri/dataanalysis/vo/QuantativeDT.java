package eu.deri.dataanalysis.vo;

/**
 * @author swapnil/yaskha
 * 
 * Quantitative data is a numerical measurement expressed not by means of a natural 
 * language description, but rather in terms of numbers.
 */
public class QuantativeDT extends StatDataType implements Cloneable{
	
	private boolean isContinuous=false;
	private boolean isDiscrete=false;
	private boolean isInterval=false;
	private boolean isRatio=false;
	private boolean isCount=false;
	private boolean isUnique=false;
	private boolean isRealValAddptive=false;
	private boolean isRealValMultiplicative=false;
	
	public QuantativeDT() {
		setQuantative(true);
	}
	public boolean isContinuous() {
		return isContinuous;
	}
	public void setContinuous(boolean isContinuous) {
		this.isContinuous = isContinuous;
	}
	public boolean isDiscrete() {
		return isDiscrete;
	}
	public void setDiscrete(boolean isDiscrete) {
		this.isDiscrete = isDiscrete;
	}
	public boolean isInterval() {
		return isInterval;
	}
	public void setInterval(boolean isInterval) {
		this.isInterval = isInterval;
	}
	public boolean isRatio() {
		return isRatio;
	}
	public void setRatio(boolean isRatio) {
		this.isRatio = isRatio;
	}
	public boolean isCount() {
		return isCount;
	}
	public void setCount(boolean isCount) {
		this.isCount = isCount;
	}
	public boolean isUnique() {
		return isUnique;
	}
	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}
	public boolean isRealValAddptive() {
		return isRealValAddptive;
	}
	public void setRealValAddptive(boolean isRealValAddptive) {
		this.isRealValAddptive = isRealValAddptive;
	}
	public boolean isRealValMultiplicative() {
		return isRealValMultiplicative;
	}
	public void setRealValMultiplicative(boolean isRealValMultiplicative) {
		this.isRealValMultiplicative = isRealValMultiplicative;
	}
	public QuantativeDT clone() throws CloneNotSupportedException {
		 return (QuantativeDT)super.clone();
	}
}
