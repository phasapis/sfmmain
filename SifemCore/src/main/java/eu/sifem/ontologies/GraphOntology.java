package eu.sifem.ontologies;

public class GraphOntology {

	public static String ontoUri = "http://www.sifemontologies.com/ontologies/Graph.owl#";

	private static final String prefix = "graph:";
	public static final String prefixUsed = "graph";
		
	public class Class {
		//classes
		public static final String axisClass = prefix + "Axis";
		public static final String xaxisClass = prefix + "X-Axis";
		public static final String yaxisClass = prefix + "Y-Axis";
		public static final String graphClass = prefix + "Graph";
		public static final String graphBehaviorClass = prefix + "GraphBehaviour";
		public static final String graphPointClass = prefix + "GraphPoint";
		public static final String optimumClass = prefix + "Optimum";		
		public static final String minimumClass = prefix + "Minimum";
		public static final String maximumClass = prefix + "Maximum";
		
	}
	
	public class Property {
		
		public class DataProperty{
			//data properties
			public static final String hasAxisIncrementValueDatProp = prefix + "hasAxisIncrementValue";
			public static final String hasAxisStartValueDatProp = prefix + "hasAxisStartValue";
			public static final String hasAxisStopValueDatProp = prefix + "hasAxisStopValue";
			public static final String hasNumberOfDataPointsDatProp = prefix + "hasNumberOfDataPoints";
			public static final String hasSlopeValueDatProp = prefix + "hasSlopeValue";
			public static final String hasTitleDatProp = prefix + "hasTitle";
			public static final String hasXValueDatProp = prefix + "hasXValue";
			public static final String hasYValueDatProp = prefix + "hasYValue";
			public static final String hasZValueDatProp = prefix + "hasZValue";		
		}
		
		public class ObjectProperty{
			//object properties
			public static final String hasGraphPointObjProp = prefix + "hasGraphPoint";
			public static final String hasMinimaObjProp = prefix + "hasMinima";
			public static final String hasxAxisObjProp = prefix + "hasX-Axis";
			public static final String hasyAxisObjProp = prefix + "hasY-Axis";
			
		}
			
	}
	
}
