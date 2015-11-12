package eu.sifem.model.to;

/**
 * @author swapnil/jbjares
 * This VO is representing boundary and initial condition in simulation.
 */
public class Condition implements AbstractTO{

	/**
	 * 
	 */
	private String name;
	private static final long serialVersionUID = 8095482595611758888L;
	private ConditionType simulationType;
	private double initialXValue=0.0;
	private double initialXStepValue=0.0;
	private double initialXStopValue=0.0;
	private BoundaryConditionType boundaryCondition;
	private Dimension lidNuDimension;
	private Dimension conditionDimension;
	private double materialPropertyVal;
	public Condition() {
		lidNuDimension = new Dimension(2.0, -1.0);
		conditionDimension = new Dimension(1, -1);
	}
	
	public ConditionType getSimulationType() {
		return simulationType;
	}

	public void setSimulationType(ConditionType simulationType) {
		this.simulationType = simulationType;
	}

	public double getInitialXValue() {
		return initialXValue;
	}

	public void setInitialXValue(double initialXValue) {
		this.initialXValue = initialXValue;
	}

	public double getInitialXStepValue() {
		return initialXStepValue;
	}

	public void setInitialXStepValue(double initialXStepValue) {
		this.initialXStepValue = initialXStepValue;
	}

	public double getInitialXStopValue() {
		return initialXStopValue;
	}

	public void setInitialXStopValue(double initialXStopValue) {
		this.initialXStopValue = initialXStopValue;
	}

	public BoundaryConditionType getBoundaryCondition() {
		return boundaryCondition;
	}

	public void setBoundaryCondition(BoundaryConditionType boundaryCondition) {
		this.boundaryCondition = boundaryCondition;
	}

	public Dimension getLidNuDimension() {
		return lidNuDimension;
	}

	public void setLidNuDimension(Dimension lidNuDimension) {
		this.lidNuDimension = lidNuDimension;
	}

	public Dimension getConditionDimension() {
		return conditionDimension;
	}

	public void setConditionDimension(Dimension conditionDimension) {
		this.conditionDimension = conditionDimension;
	}

	public double getMaterialPropertyVal() {
		return materialPropertyVal;
	}

	public void setMaterialPropertyVal(double materialPropertyVal) {
		this.materialPropertyVal = materialPropertyVal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
