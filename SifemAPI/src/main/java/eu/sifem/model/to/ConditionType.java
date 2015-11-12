package eu.sifem.model.to;

/**
 * @author swapnil/jbjares
 * Type of Initial condition type.
 */
public enum ConditionType {
	PRESSURE("Pressure"), VELOCITY("Velocity");
	private String type;

	private ConditionType(String type) {
		this.type = type;
	}
}
