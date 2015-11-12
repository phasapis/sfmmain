package eu.sifem.ontologies;

public class SimulationOntology {

	public static String ontoUri = "http://www.sifemontologies.com/ontologies/Simulation.owl#";

	private static final String prefix = "sim:";

	public static final String prefixUsed = "sim";

	public class Class {
		//classes
		public static final String scalarValueClass = prefix + "ScalarValue";
		public static final String vectorValueClass = prefix + "VectorValue"; 		
	}

	public class Property {

		public class DataProperty{
			//data properties
			public static final String hasScalarDataValueDatProp = prefix + "hasScalarDataValue";
			public static final String hasVectorXValueDatProp = prefix + "hasVectorXValue";
			public static final String hasVectorYValueDatProp = prefix + "hasVectorYValue";
			public static final String hasVectorZValueDatProp = prefix + "hasVectorZValue";
			public static final String hasTimeStamp = prefix + "hasTimeStamp";
			public static final String hasVectorMagnitude = prefix + "hasVectorMagnitude";
			public static final String isReal = prefix + "isReal";
			
			
		}

		public class ObjectProperty{
			//object properties
			public static final String hasScalarValueObjProp = prefix + "hasScalarValue";
			public static final String hasVectorValueObjProp = prefix + "hasVectorValue";

		}

	}

}
