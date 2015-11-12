package eu.sifem.data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

//TODO rewrite
public class PakCData {

	public static enum Card1 {
		TITLE;
	}

	public static enum Card2 {
		INDFOR;
	}

	public static enum Card3 {
		NP,NGET,NMATM,NDIN,NPER,PRINT;		
	}

	public static enum Card4 {
		INTEB,INDSC,IFORM,MAXIT,EPSTA,EPSTR,NJRAP,ISOLVER,FREQUENCY;
	}

	public static enum Card5 {
		IREST;
	}

	public static enum Card6 {
		NDTI, TIMEI;
	}

	public static enum Card7 {		  
		N, I1, I2, I3, I4, I5, I6, I7, I8, CORD1NX, CORD2NY, CORD3NZ;
	}

	public static enum Card8 {		  
		NETIP,NET,INDAX,IATYP,NMODM,IPN, Unknown1;
	}

	public static enum Card8_1_a {		 
		NP2DMX, ISWELL;	
	}

	public static enum Card8_1_b {		
		NN, NEL1, NEL2, NEL3, NEL4, NEL5, NEL6, NEL7, NEL8, MaterialID, YoungsModulus, DampingCoefficient;
	}

	public static enum Card9 {		
		Numzad;
	}

	public static enum Card10 {		
		Qx0, Qy0, Qz0,p0;
	}

	public static enum Card11 {		
		MAXSIL, INDPJ;
	}

	public static enum Card11_second {		
		Element, Node1, Node2, Node3, Node4, TimeFunc, IVal1, IVal2, IVal3, IVal4;
	}

	public static enum card12_1_number {		
		NoOfMaterials;
	}

	public static enum card12_1_a_materialCommon {		
		Model, MatId, Indjot, Mattype;
	}

	public static enum card12_1_a_materialType1_1 {		
		YoungsModulus, PoissonRatio, Density;
	}

	public static enum card12_1_a_materialType1_2 {		
		Porosity, Permeability, BulkModuluesOfSolid, BulkModulusOfFluid; 
	}

	public static enum card12_1_a_materialType2_1 {		
		DensityFluid, SpeedOfSound;
	}

	public static enum card12_1_a_materialType2_2 {		
		Porosity, Permeability, BulkModuluesOfSolid, BulkModulusOfFluid;			
	}

	public static enum card13 {		
		NTABFT, MAXTFT;			
	}

	public static enum card13_1_aPattern {		
		IBR, IMAX;		
	}

	public static enum card13_1_bPattern {		
		FN1IBRJ, FN2IBRJ;
	}

	public static enum card14Pattern {		
		NumberOfForces;
	}

	public static enum card14_1Pattern {		
		Node, Ind,TimeFunc, Values;
	}

	public static enum Card16 {
		STOP;
	}			


	public Map<String, String> card1 = new HashMap<String, String>();
	public Map<String, String> card2 = new HashMap<String, String>();
	public Map<String, String> card3 = new HashMap<String, String>();
	public Map<String, String> card4 = new HashMap<String, String>();
	public Map<String, String> card5 = new HashMap<String, String>();
	public Map<String, String> card6 = new HashMap<String, String>();
	public Map<String, Map<String, String>> card7 = new HashMap<String, Map<String, String>>();
	public Map<String, String> card8 = new HashMap<String, String>();
	public Map<String, String> card8_1_a = new HashMap<String, String>();
	public Map<String, Map<String, String>> card8_1_b = new HashMap<String, Map<String, String>>();
	public Map<String, String> card9 = new HashMap<String, String>();
	public Map<String, String> card10 = new HashMap<String, String>();
	public Map<String, String> card11 = new HashMap<String, String>();
	public Map<String, Map<String, String>> card11_second = new HashMap<String, Map<String, String>>();
	public Map<String, String> card12_1_number = new HashMap<String, String>();
	public Map<String, String> card12_1_a_materialCommon = new HashMap<String, String>();
	public Map<String, String> card12_1_a_materialType1_1 = new HashMap<String, String>();
	public Map<String, String> card12_1_a_materialType1_2 = new HashMap<String, String>();
	public Map<String, String> card12_1_a_materialType2_1 = new HashMap<String, String>();
	public Map<String, String> card12_1_a_materialType2_2 = new HashMap<String, String>();
	public Map<String, String> card13 = new HashMap<String, String>();
	public Map<String, String> card13_1_a = new HashMap<String, String>();
	public Map<String, String> card13_1_b = new HashMap<String, String>();
	public Map<String, String> card14 = new HashMap<String, String>();
	public Map<String, String> card14_1 = new HashMap<String, String>();
	public Map<String, String> card16 = new HashMap<String, String>();
}


