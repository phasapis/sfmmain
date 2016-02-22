package eu.deri.dataanalysis.util;

public enum OperationName {
	DERVIATION("Derivation"),AVERAGE("Average"),AGGREGATION("Aggregation"),
	EQUALITY_RATIO("Equality Ratio"),STD_VARIATION("Standared Variation"),
	PRODUCT_CORRELATION("Product-Moment Correlation"),COEFF_VARIATION("Coefficient Variation"),
	LOG_TRANSFORMATION("Logarithmic transformations"),COUNT("Count"),DATABEHAVIOUR("Data behaviour"),
	AMPLITUDECALCULATION("Amplitude calcuation"),DISTANCECALCULATION("Distance function calculation");
	
	private String type=null;

	private OperationName(String type) {
		this.type = type;
	}
}
