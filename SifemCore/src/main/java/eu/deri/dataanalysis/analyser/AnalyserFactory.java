package eu.deri.dataanalysis.analyser;

import eu.deri.dataanalysis.util.AnalyserType;

public class AnalyserFactory {
	
	/**
	 * @param type
	 * @return Analyser
	 */
	public static Analyser createInstance(AnalyserType type) {
		if (type == AnalyserType.DATATYPE) {
			return new DataTypeAnalyser();
		} else if(type == AnalyserType.OPERATION){
			return new OperationAnalyser();
		} else if(type == AnalyserType.PROJECTION){
			return new ProjectionAnalyser();
		} else if(type == AnalyserType.FEATUREVIEW){
			return new FeatureViewAnalyser();
		} else if(type == AnalyserType.INTERPRETATION){
			return new InterpretationAnalyser();
		} else if(type == AnalyserType.DATAANALYSER){
			return new DerivativeAnalyser();
		}  else if(type == AnalyserType.COMPAREANALYSER){
			return new CompareAnalyser();
		}else if(type == AnalyserType.FUNCTIONANALYSER){
			return new FunctionAnalyser();
		}else if(type == AnalyserType.EXTREMAANALYSER){
			return new ExtremaAnalyser();
		}else if(type == AnalyserType.MAXMINANALYSER){
			return new MaxMinAnalyser();
		} else if(type == AnalyserType.DERIVATIVEANALYSER){
			return new DerivativeAnalyser();
		} else if(type == AnalyserType.AVERAGEANALYSER){
			return new AverageAnalyser();
		} else {
			return null;
		}
	}
}
