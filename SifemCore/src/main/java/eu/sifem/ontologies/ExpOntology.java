package eu.sifem.ontologies;

public class ExpOntology {

	public static String ontoUri = "http://www.sifemontologies.com/ontologies/Experiment.owl#";

	public static final String prefixUsed = "exp";
	
	
	public class Class {
		//classes
		public static final String scalarValueClass = "exp:ScalarValue";
		public static final String vectorValueClass = "exp:VectorValue"; 		
	}
	
	public class Property {
		
		public class DataProperty{
			//data properties
			public static final String hasScalarDataValueDatProp = "exp:hasScalarDataValue";
			public static final String hasVectorXValueDatProp = "exp:hasVectorXValue";
			public static final String hasVectorYValueDatProp = "exp:hasVectorYValue";
			public static final String hasVectorZValueDatProp = "exp:hasVectorZValue";
			public static final String hasTimeStamp = "exp:hasTimeStamp";
		}
		
		public class ObjectProperty{
			//object properties
			public static final String hasScalarValueObjProp = "exp:hasScalarValue";
			public static final String hasVectorValueObjProp = "exp:hasVectorValue";			
		}
			
	}
	
}
