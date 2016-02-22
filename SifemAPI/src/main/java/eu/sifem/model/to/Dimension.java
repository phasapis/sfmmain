package eu.sifem.model.to;

/**
 * @author swapnil/jbjares
 * This class represent unit(m/s or m*m/s etc).
 */
public class Dimension implements AbstractTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 416320005462350415L;
	private double lengthDimension;
	private double timeDimension;
	
	public Dimension(double lengthDimension,double timeDimension) {
		 this.lengthDimension=lengthDimension;
		 this.timeDimension=timeDimension;
	}
	public double getLengthDimension() {
		return lengthDimension;
	}

	public void setLengthDimension(double lengthDimension) {
		this.lengthDimension = lengthDimension;
	}

	public double getTimeDimension() {
		return timeDimension;
	}

	public void setTimeDimension(double timeDimension) {
		this.timeDimension = timeDimension;
	}

}
