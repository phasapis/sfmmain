package eu.sifem.ontologies;

public class DataAnalysisOntology {

	public static String ontoUri = "http://www.sifemontologies.com/ontologies/DataAnalysis.owl#";

	private static final String prefix = "dao:";
	public static final String prefixUsed = "dao";
	
	
	
	public class Class {
		//classes
		public static final String behaviourRegionClass = prefix + "BehaviourRegion";
		public static final String dataFeatureClass = prefix + "DataFeature";
		public static final String curveShapeClass = prefix + "CurveShape";
		public static final String growthClass = prefix + "Growth";
		public static final String inflexionPointClass = prefix + "InflexionPoint";
		public static final String optimumClass = prefix + "Optimum";		
		public static final String minimumClass = prefix + "Minimum";
		public static final String maximumClass = prefix + "Maximum";
		public static final String negativeMinimumClass = prefix + "NegativeMinimum";		
		public static final String positiveMinimumClass = prefix + "PositiveMinimum";
		public static final String slopeClass = prefix + "Slope";
		public static final String negativeSlopeClass = prefix + "NegativeSlope";
		public static final String positiveSlopeClass = prefix + "PositiveSlope";
		public static final String neutralSlopeClass = prefix + "NeutralSlope";		
		public static final String dataSetClass = prefix + "DataSet";
		public static final String dataViewClass = prefix + "DataView";
		public static final String dataViewDimensionClass = prefix + "DataViewDimension";
		public static final String dataViewDimensionPairClass = prefix + "DataViewDimensionPair";
		public static final String distanceFromCavityBaseClass = prefix + "DistanceFromCavityBase";		
	}
	
	public class Property {
		
		public class DataProperty{
			//data properties
			public static final String hasDataViewDimensionIndexProp = prefix + "hasDataViewDimensionIndex";
			public static final String hasDataViewDimensionValueProp = prefix + "hasDataViewDimensionValue";
			public static final String hasRegionIndexProp = prefix + "hasRegionIndex";
			public static final String hasSlopeValueProp = prefix + "hasSlopeValue";			
		}
		
		public class ObjectProperty{
			//object properties
			public static final String hasAssociatedDimensionProp = prefix + "hasAssociatedDimension";
			public static final String hasDataFeatureProp = prefix + "hasDataFeature";
			public static final String hasDataViewProp = prefix + "hasDataView";
			public static final String hasDataViewDimensionProp = prefix + "hasDataViewDimension";
			public static final String hasDataViewDimensionPairProp = prefix + "hasDataViewDimensionPair";			
			public static final String hasDerivedSourceProp = prefix + "hasDerivedSource";
			public static final String hasDirectSourceProp = prefix + "hasDirectSource";
			public static final String hasFeatureProp = prefix + "hasFeature";
			public static final String hasMinimumProp = prefix + "hasMinimum";
			public static final String hasMaximumProp = prefix + "hasMaximum";
			public static final String hasRegionProp = prefix + "hasRegion";
			public static final String hasRegionEndProp = prefix + "hasRegionEnd";
			public static final String hasRegionStartProp = prefix + "hasRegionStart";
			public static final String hasSegmentationDimensionProp = prefix + "hasSegmentationDimension";
			public static final String hasSlopeProp = prefix + "hasSlope";
			public static final String isAssociatedDimensionOfProp = prefix + "isAssociatedDimensionOf";
			public static final String isDataFeatureOfProp = prefix + "isDataFeatureOf";
			public static final String isDataViewDimensionOfProp = prefix + "isDataViewDimensionOf";
			public static final String isDataViewDimensionPairOfProp = prefix + "isDataViewDimensionPairOf";
			public static final String isDataViewOfProp = prefix + "isDataViewOf";
			public static final String isEndOfRegionProp = prefix + "isEndOfRegion";
			public static final String isFeatureOfProp = prefix + "isFeatureOf";
			public static final String isFollowedByFeatureProp = prefix + "isFollowedByFeature";
			public static final String isFollowedByRegionProp = prefix + "isFollowedByRegion";
			public static final String isMinimumOfProp = prefix + "isMinimumOf";
			public static final String isSegmentationDimensionOfProp = prefix + "isSegmentationDimensionOf";
			public static final String isSlopeOfProp = prefix + "isSlopeOf";
			public static final String isStartRegionProp = prefix + "isStartRegion";	
			public static final String isRegionOfProp = prefix + "isRegionOf";	
					
		}
			
	}
	
}
