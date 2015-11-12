/**
 * 
 */
package eu.deri.dataanalysis.util;

/**
 * @author swapnil It contains name of all type of analyser
 */
public enum AnalyserType {
	DATATYPE("DataType"),OPERATION("Operation"),
	PROJECTION("Projection"),INTERPRETATION("Interpretation"),
	FEATUREVIEW("FeatureView"),DATAANALYSER("DataAnalyser"),COMPAREANALYSER("CompareAnalyser"),ALL("All"),
	FUNCTIONANALYSER("FunctionAnalyser"),EXTREMAANALYSER("ExtremaAnalyser"),MAXMINANALYSER("MaxMinAnalyser"),
	DERIVATIVEANALYSER("DerivativeAnalyser"),AVERAGEANALYSER("AverageAnalyser");
	private String type;

	private AnalyserType(String type) {
		this.type = type;
	}
}
