package eu.deri.dataanalysis.vo;

public class Aggregate {
	private double sum;
	private double avg;
	private double max;
	private double min;
	private double total;
	public Aggregate(double sum,double avg,double max,double min,double total) {
		this.sum=sum;
		this.avg=avg;
		this.max=max;
		this.min=min;
		this.total=total;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
}
