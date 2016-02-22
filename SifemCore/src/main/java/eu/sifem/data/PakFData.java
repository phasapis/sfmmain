package eu.sifem.data;



import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;


//TODO rewrite
public class PakFData {

	public static enum Card1 {
		TITLE;
	}

	public static enum Card2 {
		INDFOR;
	}

	public static enum Card3 {
		C,  NP, NGET, NMATM, NSTAC, NPER, PRIN, INDF, IPENAL;
	}

	public static enum Card4 {
		C, INTEB, INDSC, IFORM, MAXIT, EPSTA, EPSTR, NJRAP, MBAND
	}

	public static enum Card5 {
		IREST;
	}

	public static enum Card6 {
		NDTI, TIMEI;
	}

	public static enum Card7 {
		N, IUN, IVN, IWN, IPN, ITN, CORD1NX, CORD2NY, CORD3NZ;
	}
	
	public static enum Card8 {
		NETIP, NET, INDAX;
	}

	public static enum Card8_1_a {
		NMAT2D, MAT2D, NP2DMX, PENALTY;
	}
	
	public static enum Card8_1_b {
		NN, NEL1NN, NEL2NN, NEL3NN, NEL4NN;
	}
	
	public static enum Card8_1_c {
		NN, NEL1NN, NEL2NN, NEL3NN, NEL4NN, NEL5NN, NEL6NN, NEL7NN, NEL8NN, NEL9NN, NMAT;
	}

	public static enum Card9_1 {
		DENSIT, INDAMI;
	}
	
	public static enum Card9_2 {
		CONDUC;
	}
	
	public static enum Card9_3 {
		SPECH;
	}	
	
	public static enum Card9_4 {
		GX, GY, GZ, AMI, BETA, TETA0;
	}	
	
	public static enum Card10 {
		NUMZAD, NUMST;
	}
	
	public static enum Card10_1 {
		NNODE, INDPR, ITIMF, VALUE;
	}	
	
	public static enum Card11 {
		UINIT, VINIT, WINIT, PINIT, TINIT;
	}
	
	public static enum Card12 {
		NTABFT, MAXTFT;
	}	

	public static enum Card12_1_a {
		IBR, IMAX;
	}		
	
	public static enum Card12_1_b {
		FN1IBRJ, FN2IBRJ;
	}			
	
	public static enum Card13_a {
		NELEM, NBC1, NBC2, INDSTX, INDSTY;
	}			
	
	public static enum Card13_b {
		NELEM, NBC1, NBC2, NBC3, NBC4, INDSTX, INDSTY, INDSTZ;
	}			
	
	public static enum Card14 {
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
	public Map<String, Map<String, String>> card8_1_c = new HashMap<String, Map<String, String>>();
	public Map<String, String> card9_1 = new HashMap<String, String>();
	public Map<String, String> card9_2 = new HashMap<String, String>();
	public Map<String, String> card9_3 = new HashMap<String, String>();
	public Map<String, String> card9_4 = new HashMap<String, String>();
	public Map<String, String> card10 = new HashMap<String, String>();
	public Map<String, Map<String, String>> card10_1 = new HashMap<String, Map<String, String>>();
	public Map<String, String> card11 = new HashMap<String, String>();
	public Map<String, String> card12 = new HashMap<String, String>();
	public Map<String, String> card12_1_a = new HashMap<String, String>();
	public Map<String, Map<String, String>> card12_1_b = new HashMap<String, Map<String, String>>();			
	public Map<String, Map<String, String>> card13_a = new HashMap<String, Map<String, String>>();
	public Map<String, Map<String, String>> card13_b = new HashMap<String, Map<String, String>>();
	public Map<String, String> card14 = new HashMap<String, String>();
}
