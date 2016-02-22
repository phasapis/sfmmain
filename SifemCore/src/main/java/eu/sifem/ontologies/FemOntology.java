package eu.sifem.ontologies;

import java.util.HashMap;
import java.util.Map;

public class FemOntology {	

	//public static final String ontoUri = "http://www.sifemontologies.com/ontologies/FEMOntology.owl#";
	public static final String ontoUri = "http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#";

	private static final String prefix = "fem:";

	public static final String prefixUsed = "fem";

	public static Map<String, String> objectValueClassMapOF = new HashMap<String, String>();

	public static Map<String, String> patchFieldPropertyMapOF = new HashMap<String, String>();

	static{
		objectValueClassMapOF.put("p", FemOntology.Class.pressureClass);
		objectValueClassMapOF.put("U", FemOntology.Class.velocityClass);
		patchFieldPropertyMapOF.put("type", FemOntology.Property.ObjectProperty.hasPatchFieldTypeObjProp);
	}

	public class Class {
		//classes
		public static final String dimensionClass = prefix + "Dimension";
		public static final String pressureClass = prefix + "Pressure";
		public static final String frequencyClass = prefix + "Frequency";		
		public static final String velocityClass = prefix + "Velocity";
		public static final String temperatureParameterClass = prefix + "TemperatureParameter";
		public static final String translationClass = prefix + "Translation";
		public static final String potentialClass = prefix + "Potential";		
		public static final String wallClass = prefix + "Wall";
		public static final String emptyClass = prefix + "Empty";
		public static final String zeroGradientClass = prefix + "ZeroGradient";
		public static final String fixedValueClass = prefix + "FixedValue";
		public static final String cellClass = prefix + "Cell";
		public static final String cellCoordinateClass = prefix + "CellCoordinate";
		public static final String femModelClass = prefix + "FEMModel";
		public static final String pakMeshClass = prefix + "PAKMesh";
		public static final String nodeClass = prefix + "Node";
		public static final String elementGroupClass = prefix + "ElementGroup";
		public static final String elementClass = prefix + "Element";	
		public static final String densityForFluidClass = prefix + "DensityForFluid";
		public static final String conductionCoefficientClass = prefix + "ConductionCoefficient";
		public static final String gravityAccelerationInXDirectionClass = prefix + "GravityAccelerationInXDirection";
		public static final String gravityAccelerationInYDirectionClass = prefix + "GravityAccelerationInYDirection";
		public static final String gravityAccelerationInZDirectionClass = prefix + "GravityAccelerationInZDirection";
		public static final String materialPropertyDynamicViscosityClass = prefix + "MaterialPropertyDynamicViscosity";
		public static final String boundaryConditionTypeClass = prefix + "BoundaryConditionType";
		public static final String uniformConditionClass = prefix + "UniformCondition";
//		public static final String unvNodalResultsBlockClass = prefix + "UNVNodalResultsBlock";
//		public static final String unvOutputFileClass = prefix + "UNVOutputFile";
//		public static final String unvNodeBlockClass = prefix + "UNVNodeBlock";
		public static final String argumentFunctionPairClass = prefix + "ArgumentFunctionPair";
		public static final String elementBoundaryClass = prefix + "ElementBoundary";		
		public static final String loadClass = prefix + "Load";
		public static final String subDomainGroupClass = prefix + "SubDomainGroup";
		public static final String meshClass = prefix + "Mesh";
		public static final String materialClass = prefix + "Material";
		public static final String youngsModulusClass = prefix + "YoungsModulus";
		public static final String dampingCoefficientClass = prefix + "DampingCoefficient";
		public static final String initialCondition = prefix + "InitialCondition";
		public static final String boundaryClass = prefix + "Boundary";	
		
	}

	public class Property {
		public class ObjectProperty {
			//object properties
			public static final String hasDimensionObjProp = prefix + "hasDimension";	
			public static final String hasPatchFieldTypeObjProp = prefix + "hasPatchFieldType";
			public static final String hasParameter = prefix + "hasParameter";
			public static final String hasCellCoordinateObjProp = prefix + "hasCellCoordinate";
			public static final String hasElementGroup = prefix + "hasElementGroup";
			public static final String hasFirstNode = prefix + "hasFirstNode";
			public static final String hasSecondNode = prefix + "hasSecondNode";
			public static final String hasThirdNode = prefix + "hasThirdNode";
			public static final String hasFourthNode = prefix + "hasFourthNode";
			public static final String hasFifthNode = prefix + "hasFifthNode";
			public static final String hasSixthNode = prefix + "hasSixthNode";
			public static final String hasSeventhNode = prefix + "hasSeventhNode";
			public static final String hasEighthNode = prefix + "hasEighthNode";
			public static final String hasNinthNode = prefix + "hasNinthNode";
			public static final String hasPrescribedParameter = prefix + "hasPrescribedParameter";
			public static final String isInternalConditionOf = prefix + "isInternalConditionOf";
			public static final String isParameterOf = prefix + "isParameterOf";
			public static final String hasFirstElement = prefix + "hasFirstElement";
			public static final String hasArgumentFunctionPair = prefix + "hasArgumentFunctionPair";
			public static final String hasElementBoundary = prefix + "hasElementBoundary";
			public static final String isElementBoundaryOf = prefix + "isElementBoundaryOf";
			public static final String isFirstNodeOf = prefix + "isFirstNodeOf";
			public static final String isSecondNodeOf = prefix + "isSecondNodeOf";
			public static final String holdsValueFor = prefix + "holdsValueFor";
			public static final String hasNode = prefix + "hasNode";			
			public static final String makesUp = prefix + "makesUp";
			public static final String hasMaterial = prefix + "hasMaterial";
			public static final String isNodeOf = prefix + "isNodeOf";
			public static final String hasMaterialProperty = prefix + "hasMaterialProperty";
			public static final String hasBoundaryNode = prefix + "hasBoundaryNode";
						
		}

		public class DataProperty {
			//data properties
			public static final String massDimensionDatProp = prefix + "hasMassDimension";
			public static final String lengthDimensionDatProp = prefix + "hasLengthDimension";
			public static final String timeDimensionDatProp = prefix + "hasTimeDimension";
			public static final String temperatureDimensionDatProp = prefix + "hasTemperatureDimension";
			public static final String quantityDimensionDatProp = prefix + "hasQuantityDimension";
			public static final String currentDimensionDatProp = prefix + "hasCurrentDimension";
			public static final String luminousDimensionDatProp = prefix + "hasLuminousDimension";
			public static final String convertToMetersDatProp = prefix + "convertToMeters";
			public static final String hasXVertexDatProp = prefix + "hasXVertex";
			public static final String hasYVertexProperty = prefix + "hasYVertex";
			public static final String hasZVertexProperty = prefix + "fem:hasZVertex";
			public static final String hasVertexProperty = prefix + "hasVertex";
			public static final String hasHeadingProperty = prefix + "hasHeading";
			public static final String hasFreeFormatProperty = prefix + "hasFreeFormat";
			public static final String hasFixedFormatProperty = prefix + "hasFixedFormat";
			public static final String hasNumberOfNodalPoints = prefix + "hasNumberOfNodalPoints";
			public static final String hasNumberOfElementGroups = prefix + "hasNumberOfElementGroups";
			public static final String hasNumberOfDifferentMaterialModels = prefix + "hasNumberOfDifferentMaterialModels";
			public static final String usesStationaryAnalysis = prefix + "usesStationaryAnalysis";
			public static final String usesNumberOfTimePeriods = prefix + "usesNumberOfTimePeriods";
			public static final String hasOutputPrintingInterval = prefix + "hasOutputPrintingInterval";
			public static final String usesAnalysisOfFluidFlowWithHeatTransfer = prefix + "usesAnalysisOfFluidFlowWithHeatTransfer";
			public static final String usesAnalysisOfFluidFlowOnly = prefix + "usesAnalysisOfFluidFlowOnly";
			public static final String usesAnalysisOfHeatTransferOnly = prefix + "usesAnalysisOfHeatTransferOnly";
			public static final String usesAnalysisOfMixedFormulation = prefix + "usesAnalysisOfMixedFormulation";
			public static final String usesAnalysisOfPenaltyMethod = prefix + "usesAnalysisOfPenaltyMethod";
			public static final String usesTimeIntegrationMethod = prefix + "usesTimeIntegrationMethod";
			public static final String hasMaximumNumberOfIterations = prefix + "hasMaximumNumberOfIterations";
			public static final String hasAbsoluteAccuracyAtIterations = prefix + "hasAbsoluteAccuracyAtIterations";
			public static final String hasRelativeAccuracyAtIterations = prefix + "hasRelativeAccuracyAtIterations";
			public static final String usesIterationMethod = prefix + "usesIterationMethod";
			public static final String hasSequentialEquationDefinition = prefix + "hasSequentialEquationDefinition";
			public static final String hasPressureEquationsAtTheEnd = prefix + "hasPressureEquationsAtTheEnd";
			public static final String hasIndicatorForJobExecution = prefix + "hasIndicatorForJobExecution";
			public static final String hasNumberOfTimeStepsinIthPeriod = prefix + "hasNumberOfTimeStepsinIthPeriod";
			public static final String hasTimeInStepInIthPeriod = prefix + "hasTimeInStepInIthPeriod";
			public static final String hasNodalPointNumber = prefix + "hasNodalPointNumber";
			public static final String velocityXValueIsPrescribed = prefix + "VelocityXValueIsPrescribed";
			public static final String velocityYValueIsPrescribed = prefix + "VelocityYValueIsPrescribed";
			public static final String velocityZValueIsPrescribed = prefix + "VelocityZValueIsPrescribed";
			public static final String presureValueIsPrescribed = prefix + "PresureValueIsPrescribed";
			public static final String temperatureValueIsPrescribed = prefix + "TemperatureValueIsPrescribed";
			public static final String hasXCoordinate = prefix + "hasXCoordinate";
			public static final String hasYCoordinate = prefix + "hasYCoordinate";
			public static final String hasZCoordinate = prefix + "hasZCoordinate";
			public static final String hasProblemDimension = prefix + "hasProblemDimension";
			public static final String hasNumberOfElements = prefix + "hasNumberOfElements";
			public static final String hasIndicatorForAxisymmetricElements = prefix + "hasIndicatorForAxisymmetricElements";
			public static final String hasNumberOfNodesEqualTo = prefix + "hasNumberOfNodesEqualTo";
			public static final String hasPenaltyFactor = prefix + "hasPenaltyFactor";
			public static final String hasElementNumber = prefix + "hasElementNumber";
			public static final String hasMaterialNumber = prefix + "hasMaterialNumber";			
			public static final String hasMaterialPropertyValue = prefix + "hasMaterialPropertyValue";
			public static final String hasNonNewtonianFluidIndicator = prefix + "hasNonNewtonianFluidIndicator";
			public static final String hasTotalNumberOfPrescribedValues = prefix + "hasTotalNumberOfPrescribedValues";
			public static final String hasNumberOfSurfaceTractionCalculations = prefix + "hasNumberOfSurfaceTractionCalculations";
			public static final String hasPrescribedValue = prefix + "hasPrescribedValue";			
			public static final String hasHowManyTimeFunctions = prefix + "hasHowManyTimeFunctions";
			public static final String hasMaxPointsForTimeFunctionDefinition = prefix + "hasMaxPointsForTimeFunctionDefinition";
			public static final String hasFunctionSequenceNumber = prefix + "hasFunctionSequenceNumber";
			public static final String hasTotalArgumentFunctionPairs = prefix + "hasTotalArgumentFunctionPairs";
			public static final String hasColour = prefix + "hasColour";
			public static final String hasNumberOfUnusedBlock15Values = prefix + "hasNumberOfUnusedBlock15Values";
			public static final String hasNumberOfUnusedBlock757Values = prefix + "hasNumberOfUnusedBlock757Values";
			public static final String hasNumberOfUnusedBlock71Values = prefix + "hasNumberOfUnusedBlock71Values";		
			public static final String hasConstraintColour = prefix + "hasConstraintColour";
			public static final String hasXDisplacementConstraint = prefix + "hasXDisplacementConstraint";		
			public static final String hasYDisplacementConstraint = prefix + "hasYDisplacementConstraint";		
			public static final String hasZDisplacementConstraint = prefix + "hasZDisplacementConstraint";
			public static final String hasPressureConstraint = prefix + "hasPressureConstraint";		
			public static final String hasStepNumber = prefix + "hasStepNumber";		
			public static final String hasNumberOfValuesPerNode = prefix + "hasNumberOfValuesPerNode";
			public static final String hasTimeStep = prefix + "hasTimeStep";
			public static final String hasArgumentFunctionPairIndex = prefix + "hasArgumentFunctionPairIndex";
			public static final String hasFunction = prefix + "hasFunction";
			public static final String hasArgument = prefix + "hasArgument";
			public static final String hasTimeFunctionNumber = prefix + "hasTimeFunctionNumber";
			public static final String surfaceTractionInXDirectionIsIncluded = prefix + "SurfaceTractionInXDirectionIsIncluded";
			public static final String surfaceTractionInYDirectionIsIncluded = prefix + "SurfaceTractionInYDirectionIsIncluded";
			public static final String hasNodeID = prefix + "hasNodeID";
			public static final String hasSpatialDimension = prefix + "hasSpatialDimension";
			public static final String hasMaximumNumberOfNodesPerElement = prefix + "hasMaximumNumberOfNodesPerElement";
			public static final String hasTimeDimension = prefix + "hasTimeDimension";
			public static final String hasLengthDimension = prefix + "hasLenghtDimension";			
			public static final String hasMassDimension = prefix + "hasMassDimension";			
			public static final String hasTemperatureDimension = prefix + "hasTemperatureDimension";
			public static final String isUniform = prefix + "isUniform";			
			
						
		}
	}

}
