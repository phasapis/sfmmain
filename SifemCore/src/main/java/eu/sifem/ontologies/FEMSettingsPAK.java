package eu.sifem.ontologies;

import java.util.HashMap;
import java.util.Map;

public class FEMSettingsPAK {	

	public static final String ontoUri = "http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#";

	private static final String prefix = "pakFemSet:";

	public static final String prefixUsed = "pakFemSet";

	public static Map<String, String> objectValueClassMapOF = new HashMap<String, String>();

	public static Map<String, String> patchFieldPropertyMapOF = new HashMap<String, String>();

	public class Class {
		//classes
		public static final String finiteElementModelSettingsClass = prefix + "FiniteElementModelSettings";
		public static final String nodeSettingsClass = prefix + "NodeSettings";
		public static final String solverSettingsClass = prefix + "SolverSettings";
		public static final String elementGroupSettingsClass = prefix + "ElementGroupSettings";
		public static final String meshSettingsClass = prefix + "MeshSettings";
		public static final String unvOutputFileClass = prefix + "UNVOutputFile";
		public static final String unvBlockClass = prefix + "UNVBlock";	
		public static final String unvblockSettingsClass = prefix + "UNVBlockSettings";
		public static final String materialSettingsClass = prefix + "MaterialSettings";
		public static final String boundarySettingsClass = prefix + "BoundarySettings";

	}

	public class Property {
		public class ObjectProperty {
			//object properties
			public static final String hasDimensionObjProp = prefix + "hasDimension";
			public static final String hasNodeSettings = prefix + "hasNodeSettings";
			public static final String hasUNVBlock = prefix + "hasUNVBlock";
			public static final String isUNVBlockOf = prefix + "isUNVBlockOf";
			public static final String hasUNVBlockSettings = prefix + "hasUNVBlockSettings";
			public static final String hasMaterialSettings = prefix + "hasMaterialSettings";
			public static final String hasBoundarySettings = prefix + "hasBoundarySettings";

		}

		public class DataProperty {
			//data properties
			public static final String hasHeadingDatProp = prefix + "hasHeading";
			public static final String INDFOR = prefix + "INDFOR";			
			public static final String NDIN = prefix + "NDIN";
			public static final String NPER = prefix + "NPER";
			public static final String PRINT = prefix + "PRINT";
			public static final String INTEB = prefix + "INTEB";
			public static final String INDSC = prefix + "INDSC";
			public static final String EPSTA = prefix + "EPSTA";
			public static final String EPSTR = prefix + "EPSTR";
			public static final String NJRAP = prefix + "NJRAP";
			public static final String IFORM = prefix + "IFORM";
			public static final String MAXIT = prefix + "MAXIT";
			public static final String ISOLVER = prefix + "ISOLVER";
			public static final String IREST = prefix + "IREST";
			public static final String numberOfTimeSteps = prefix + "numberOfTimeSteps";
			public static final String timeStepDuration = prefix + "timeStepDuration";
			public static final String ID_N1 = prefix + "ID_N1";
			public static final String ID_N2 = prefix + "ID_N2";		
			public static final String ID_N3 = prefix + "ID_N3";		
			public static final String ID_N4 = prefix + "ID_N4";		
			public static final String ID_N5 = prefix + "ID_N5";		
			public static final String ID_N6 = prefix + "ID_N6";		
			public static final String ID_N7 = prefix + "ID_N7";		
			public static final String ID_N8 = prefix + "ID_N8";		
			public static final String ID_N9 = prefix + "ID_N9";		
			public static final String ID_N10 = prefix + "ID_N10";		
			public static final String ID_N11 = prefix + "ID_N11";
			public static final String INDAX = prefix + "INDAX";
			public static final String IAYTP = prefix + "IAYTP";
			public static final String NMODM = prefix + "NMODM";
			public static final String IPN = prefix + "IPN";
			public static final String ISWELL = prefix + "ISWELL";
			public static final String hasUNVBlockIdPartOne = prefix + "hasUNVBlockIdPartOne";
			public static final String hasUNVBlockIdPartTwo = prefix + "hasUNVBlockIdPartTwo";
			public static final String MATTYPE = prefix + "MATTYPE";
			public static final String MAXSIL = prefix + "MAXSIL";
			public static final String INDJP = prefix + "INDJP";
			
			
		}
	}

}
