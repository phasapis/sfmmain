package eu.sifem.simulation.solver.pak;

import eu.sifem.utils.BasicFileTools;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PakCDatReaderLatest {


	private PakCData pakCdata = new PakCData();

	//private static String cardInfoPatternString = "C\\s*/(\\d*)/\\s*([\\w\\s]*)\\((.*)\\)";
	private static String card1PatternString = "C\\s*/\\s*1\\s*\\/.*\\sC.*\\s(.*)";	
	private static String card2PatternString = "C\\s*/\\s*2\\s*/.*\\s*C\\s*.*\\s(.{5})";
	private static String card3PatternString = "C\\s*/\\s*3\\s*/\\s*.*\\sC.*\\s(.{10})(.{5})(.{5})(.{5})(.{5})(.{5})(.{5})(.{5})";
	//private static String card4PatternString = "C\\s*/\\s*4\\s*/.*\\sC.*\\s(.{5})(.{5})(.{5})(.{5})(.{10})(.{10})(.{5})(.{5})(.{10})";
	private static String card4PatternString = "C\\s*/\\s*4\\s*/.*\\sC.*\\s(.{5})(.{5})(.{5})(.{5})(.{10})(.{10})(.{5})(.{5})(.{10})(.{5})";
	
	private static String card5PatternString = "C\\s*/\\s*5\\s*/.*\\sC.*\\s(.{5})";	
	private static String card6PatternString = "C\\s*/\\s*6\\s*/.*\\sC\\s*.*\\s(.{5})(.{10})";
	private static String card7TextPatternString = "C\\s*/\\s*7\\s*/.*\\sC.*\\)(.*)C\\s*/\\s*8\\s*/";
	private static String card7PatternString = ".{5}(.{5}).{1}(.{2})(.{2})(.{2})(.{2})(.{2})(.{2})(.{2})(.{2}).{2}(.{15})(.{15})(.{15})";
	private static String card8PatternString = "C\\s*/\\s*8/.*\\sC.*\\s(.{5})(.{10})(.{5})(.{5})(.{5})(.{5})";
	//private static String card8PatternString = "C\\s*/\\s*8/.*\\sC.*\\s(.{5})(.{10})(.{5})(.{5})(.{5})(.{5})(.{5})";
	private static String card8_1_aPatternString = "C\\s*/\\s*8\\s*-\\s*1.*\\sC.*\\s(.{5})(.{5})";
	//private static String card8_1_bTextPatternString = "C\\s*/\\s*8\\s*-\\s*2.*\\sC.*\\)\\s*(.*)C\\s*/\\s*9";
	//private static String card8_1_bTextPatternString = "C\\s*/\\s*8\\s*-\\s*2.*\\sC\\s*NN.*\\)\\s(.*)C\\s*/\\s*9\\s*/";	
	//private static String card8_1_bTextPatternString = "C\\s*/\\s*8\\s*-\\s*2.*\\sC\\s*var.*Coefficient\\s(.*)C\\s*/\\s*9";
	private static String card8_1_bTextPatternString = "C\\s*/\\s*8\\s*-\\s*2.*6\\)(.*)C\\s*/\\s*10";
	//private static String card8_1_bPatternString = "(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})(.{15})(.{15})";
	private static String card8_1_bPatternString = "(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})\\s(.{13})(.{13})(.{13})(.{13})(.{13})(.{13})(.{13})(.{13})(.{13})(.{13})";
	//private static String card9PatternString = "C\\s*/\\s*9\\s*/.*\\)\\s(.*)";
	private static String card9PatternString = "C\\s*/\\s*9\\s*/.*\\sC.*\\s(.*)";
	private static String card10PatternString = "C\\s*\\/\\s*10\\s*\\/.*\\sC.*\\s(.{10})(.{10})(.{10})(.{10})";
	//private static String card11PatternString = "C\\s*\\/\\s*11\\s*\\/.*\\sC.*\\)\\s(.{10})(.{5})";
	private static String card11PatternString = "C\\s*\\/\\s*11\\s*\\/.*\\sC.*\\s(.{10})(.{5})";
	//private static String card11_secondTextPatternString = "C\\s*\\/\\s*11\\s*\\/.*\\sC.*\\)\\s.*\\sC.*\\)(.)*C\\s*/\\s*12";
	//private static String card11_secondTextPatternString = "C\\s*\\/\\s*11\\s*.*\\sC\\s*MAXSIL(.*)C\\s*/\\s*12\\s*/";
	//private static String card11_secondTextPatternString = "C\\s*\\/\\s*11\\s*.*\\sC.*IVal4(.*)C\\s*/\\s*12\\s*/";
	private static String card11_secondTextPatternString = "C\\s*\\/\\s*11\\s*.*\\sC.*F10.3\\)(.*)C\\s*/12/";	
	private static String card11_secondPatternString = "(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})(.{10})";
	private static String card12_1_numberPatternString = "C\\s*\\/\\s*12-1\\s*\\/\\s*NUMBER.*\\s*(.{5})";
	//private static String card12_1_numberPatternString = "C\\s*\\/\\s*12-1\\s*\\/\\s*NUMBER.*\\sC.*\\s(.{5})";
	//private static String card12_1_a_materialCommonPatternString = "C\\s*\\/\\s*12-1\\s*\\/\\s*MATERIAL.*\\sC.*\\s(.{5})(.{5})(.{5})(.{5})";
	private static String card12_1_a_materialCommonPatternString = "C\\s*\\/\\s*12-1\\s*\\/\\s*MATERIAL.*\\sC.*\\sC.*\\s(.{5})(.{5})(.{5})(.{5})";
	//private static String card12_1_a_materialType1_1PatternString = "C\\s*\\/\\s*12-1\\s*\\/\\s*MATERIAL.*\\sC.*\\s.*\\sC\\s*YOUNG.*\\s(.{10})(.{10})(.{10})";
	private static String card12_1_a_materialType1_1PatternString = "C\\s*\\/\\s*12-1\\s*\\/\\s*MATERIAL.*\\sC.*\\sC.*\\s.*\\sC.*Young.*\\s(.{10})(.{10})(.{10})";
	//private static String card12_1_a_materialType1_2PatternString = "C\\s*\\/\\s*12-1\\s*\\/\\s*MATERIAL.*\\sC.*\\s.*\\sC\\s*YOUNG.*\\s.*\\sC\\s*POROSITY.*\\s(.{10})(.{10})(.{10})(.{10})";
	private static String card12_1_a_materialType1_2PatternString = "C\\s*\\/\\s*12-1\\s*\\/\\s*MATERIAL.*\\sC.*\\sC.*\\s.*\\sC.*Young.*\\s.*\\sC.*Porosity.*\\s(.{10})(.{10})(.{10})(.{10})";
	//private static String card12_1_a_materialType2_1PatternString = "C\\s*\\/\\s*12-1\\s*\\/\\s*MATERIAL.*\\sC.*\\s.*\\sC\\s*DENS.*\\s(.{10})(.{10})";
	private static String card12_1_a_materialType2_1PatternString = "C\\s*\\/\\s*12-1\\s*\\/\\s*MATERIAL.*\\sC.*\\sC.*\\s.*\\sC.*Density.*\\s(.{10})(.{10})";
	//private static String card12_1_a_materialType2_2PatternString = "C\\s*\\/\\s*12-1\\s*\\/\\s*MATERIAL.*\\sC.*\\s.*\\sC\\s*DENS.*\\s.*\\sC\\s*POROSITY.*\\s(.{10})(.{10})(.{10})(.{10})";
	private static String card12_1_a_materialType2_2PatternString = "C\\s*\\/\\s*12-1\\s*\\/\\s*MATERIAL.*\\sC.*\\sC.*\\s.*\\sC.*Density.*\\s.*\\sC.*Porosity.*\\s(.{10})(.{10})(.{10})(.{10})";
	private static String card13PatternString = "C\\s*\\/\\s*13\\s*\\/.*\\sC\\s*.*\\s(.{10})(.{5})";	
	private static String card13_1_aPatternString = "C\\s*\\/\\s*13-1\\s*\\/.*\\sC.*\\sC.*\\s(.{10})(.{5})";
	//private static String card13_1_bTextPatternString = "C\\s*\\/\\s*13-1\\s*\\/.*\\sC.*\\sC.*\\s.*\\sC.*\\sC.*\\)\\s(.*)C\\s*/\\s*14";
	//private static String card13_1_bTextPatternString = "C\\s*\\/13-1\\s*\\/.*IMAX\\)\\s(.*)C\\s*/14\\s*/\\s*FORCES";
	//private static String card13_1_bTextPatternString = "C\\s*/13-1\\s*/.*FN2IBRJ\\s(.*)C\\s*/14\\s*/";
	private static String card13_1_bTextPatternString = "C\\s*/13-1\\s*/.*IMAX\\)(.*)\\sC\\s*/";
	private static String card13_1_bPatternString = "(.{10})(.{10})";	
	private static String card14PatternString = "C\\s*\\/\\s*14\\s*\\/\\s*FORCES\\sC.*\\s(.{10})";
	//private static String card14_1TextPatternString = "C\\s*\\/\\s*14-1\\s*\\s*.*\\sC.*\\)(.*)C";
	//private static String card14_1TextPatternString = "C\\s*\\/\\s*14-1\\s*/.*\\sC.*Values\\s(.*)C\\s*/\\s*16\\s*/";
	private static String card14_1TextPatternString = "C\\s*\\/\\s*14-1\\s*/.*\\sC.*VALUES.*\\)\\s(.*)C\\s*/\\s*16\\s*/";
	private static String card14_1PatternString = "(.{10})(.{5})(.{10})(.{10})";
	//private static String card16PatternString = "C\\s*/\\s*16\\s*.*\\sSTOP";
	private static String card16PatternString = "C\\s*/\\s*16\\s*/.*FINAL";

	//private static Pattern cardInfoPattern = Pattern.compile(cardInfoPatternString);
	private static Pattern card1Pattern = Pattern.compile(card1PatternString);
	private static Pattern card2Pattern = Pattern.compile(card2PatternString);
	private static Pattern card3Pattern = Pattern.compile(card3PatternString);
	private static Pattern card4Pattern = Pattern.compile(card4PatternString);
	private static Pattern card5Pattern = Pattern.compile(card5PatternString);
	private static Pattern card6Pattern = Pattern.compile(card6PatternString);
	private static Pattern card7TextPattern = Pattern.compile(card7TextPatternString, Pattern.DOTALL);
	private static Pattern card7Pattern = Pattern.compile(card7PatternString);
	private static Pattern card8Pattern = Pattern.compile(card8PatternString);
	private static Pattern card8_1_aPattern = Pattern.compile(card8_1_aPatternString);
	private static Pattern card8_1_bTextPattern = Pattern.compile(card8_1_bTextPatternString, Pattern.DOTALL);	
	private static Pattern card8_1_bPattern = Pattern.compile(card8_1_bPatternString);
	private static Pattern card9Pattern = Pattern.compile(card9PatternString);
	private static Pattern card10Pattern = Pattern.compile(card10PatternString);
	private static Pattern card11Pattern = Pattern.compile(card11PatternString);
	private static Pattern card11_secondTextPattern = Pattern.compile(card11_secondTextPatternString, Pattern.DOTALL);
	private static Pattern card11_secondPattern = Pattern.compile(card11_secondPatternString);
	private static Pattern card12_1_numberPattern = Pattern.compile(card12_1_numberPatternString);
	private static Pattern card12_1_a_materialCommonPattern = Pattern.compile(card12_1_a_materialCommonPatternString);
	private static Pattern card12_1_a_materialType1_1Pattern = Pattern.compile(card12_1_a_materialType1_1PatternString);
	private static Pattern card12_1_a_materialType1_2Pattern = Pattern.compile(card12_1_a_materialType1_2PatternString);
	private static Pattern card12_1_a_materialType2_1Pattern = Pattern.compile(card12_1_a_materialType2_1PatternString);
	private static Pattern card12_1_a_materialType2_2Pattern = Pattern.compile(card12_1_a_materialType2_2PatternString);
	private static Pattern card13Pattern = Pattern.compile(card13PatternString);
	private static Pattern card13_1_aPattern = Pattern.compile(card13_1_aPatternString);
	private static Pattern card13_1_bTextPattern = Pattern.compile(card13_1_bTextPatternString, Pattern.DOTALL);
	private static Pattern card13_1_bPattern = Pattern.compile(card13_1_bPatternString);
	private static Pattern card14Pattern = Pattern.compile(card14PatternString);
	private static Pattern card14_1TextPattern = Pattern.compile(card14_1TextPatternString, Pattern.DOTALL);
	private static Pattern card14_1Pattern = Pattern.compile(card14_1PatternString);
	private static Pattern card16Pattern = Pattern.compile(card16PatternString);


	public PakCData read(String pakCfilePath) {
		String text = BasicFileTools.extractText(pakCfilePath);
		//Matcher cardInfoMatcher = cardInfoPattern.matcher(text);
		Matcher card1PatternMatcher = card1Pattern.matcher(text);
		Matcher card2PatternMatcher = card2Pattern.matcher(text);
		Matcher card3PatternMatcher = card3Pattern.matcher(text);
		Matcher card4PatternMatcher = card4Pattern.matcher(text);
		Matcher card5PatternMatcher = card5Pattern.matcher(text);
		Matcher card6PatternMatcher = card6Pattern.matcher(text);

		Matcher card7TextPatternMatcher = card7TextPattern.matcher(text);
		Matcher card7PatternMatcher = null;
		String card7Text = null;
		if(card7TextPatternMatcher.find())
			card7Text = card7TextPatternMatcher.group(1); 
		if(card7Text!= null)
			card7PatternMatcher = card7Pattern.matcher(card7Text);

		Matcher card8PatternMatcher = card8Pattern.matcher(text);
		Matcher card8_1_aPatternMatcher = card8_1_aPattern.matcher(text);		
		Matcher card8_1_bTextPatternMatcher = card8_1_bTextPattern.matcher(text);
		Matcher card8_1_bPatternMatcher = null;
		String card8_1_bText = null;
		if(card8_1_bTextPatternMatcher.find())
			card8_1_bText = card8_1_bTextPatternMatcher.group(1);		
		if(card8_1_bText!= null)
			card8_1_bPatternMatcher = card8_1_bPattern.matcher(card8_1_bText);		

		Matcher card9PatternMatcher = card9Pattern.matcher(text);
		Matcher card10PatternMatcher = card10Pattern.matcher(text);
		Matcher card11PatternMatcher = card11Pattern.matcher(text);		
		Matcher card11_secondTextPatternMatcher = card11_secondTextPattern.matcher(text);
		Matcher card11_secondPatternMatcher = null;
		String card11_secondText = null;
		if(card11_secondTextPatternMatcher.find())
			card11_secondText = card11_secondTextPatternMatcher.group(1); 
		if(card11_secondText != null)
			card11_secondPatternMatcher = card11_secondPattern.matcher(card11_secondText);				

		Matcher card12_1_numberPatternMatcher = card12_1_numberPattern.matcher(text);
		Matcher card12_1_a_materialCommonPatternMatcher = card12_1_a_materialCommonPattern.matcher(text);		
		Matcher card12_1_a_materialType1_1PatternMatcher = card12_1_a_materialType1_1Pattern.matcher(text);
		Matcher card12_1_a_materialType1_2PatternMatcher = card12_1_a_materialType1_2Pattern.matcher(text);
		Matcher card12_1_a_materialType2_1PatternMatcher = card12_1_a_materialType2_1Pattern.matcher(text);
		Matcher card12_1_a_materialType2_2PatternMatcher = card12_1_a_materialType2_2Pattern.matcher(text);

		Matcher card13PatternMatcher = card13Pattern.matcher(text);
		Matcher card13_1_aPatternMatcher = card13_1_aPattern.matcher(text);
		Matcher card13_1_bTextPatternMatcher = card13_1_bTextPattern.matcher(text);

		Matcher card13_1_bPatternMatcher = null;
		String card13_1_bText = null;
		if(card13_1_bTextPatternMatcher.find()){
			card13_1_bText = card13_1_bTextPatternMatcher.group(1);
		}

		if(card13_1_bText != null){
			card13_1_bPatternMatcher = card13_1_bPattern.matcher(card13_1_bText);
		}

		Matcher card14PatternMatcher = card14Pattern.matcher(text);
		Matcher card14_1TextPatternMatcher = card14_1TextPattern.matcher(text);
		Matcher card14_1PatternMatcher = null;
		String card14_1Text = null;
		if(card14_1TextPatternMatcher.find())
			card14_1Text = card14_1TextPatternMatcher.group(1);
		if(card14_1Text != null)
			card14_1PatternMatcher = card14_1Pattern.matcher(card14_1Text);	

		Matcher card16PatternMatcher = card16Pattern.matcher(text); 

//		while(cardInfoMatcher.find()){
//			String cardNumber = cardInfoMatcher.group(1).trim();
//			String cardName = cardInfoMatcher.group(2).trim();
//			String cardReadFormat = cardInfoMatcher.group(3).trim();
//			//	System.out.println(cardNumber + "\t" + cardName + "\t" + cardReadFormat);
//		}	

		while(card1PatternMatcher.find()){
			String title = card1PatternMatcher.group(1).trim();
			pakCdata.card1.put(PakCData.Card1.TITLE.toString(), title);
			//	System.out.println(title);
		}

		while(card2PatternMatcher.find()){
			String indfor = card2PatternMatcher.group(1).trim();
			pakCdata.card2.put(PakCData.Card2.INDFOR.toString(), indfor);
		}

		while(card3PatternMatcher.find()){
			String np = card3PatternMatcher.group(1).trim();
			pakCdata.card3.put(PakCData.Card3.NP.toString(), np);
			String nget = card3PatternMatcher.group(2).trim();
			pakCdata.card3.put(PakCData.Card3.NGET.toString(), nget);
			String nmatm = card3PatternMatcher.group(3).trim();
			pakCdata.card3.put(PakCData.Card3.NMATM.toString(), nmatm);
			String ndin = card3PatternMatcher.group(4).trim();
			pakCdata.card3.put(PakCData.Card3.NDIN.toString(), ndin);
			String nper = card3PatternMatcher.group(5).trim();
			pakCdata.card3.put(PakCData.Card3.NPER.toString(), nper);
			String prin = card3PatternMatcher.group(6).trim();
			pakCdata.card3.put(PakCData.Card3.PRINT.toString(), prin);
			//	System.out.println(np + "\t" + nget + "\t" + nmatm + 
			//			"\t" + nstac + "\t" + nper + "\t" + prin + "\t");
		}

		while(card4PatternMatcher.find()){
			String inteb = card4PatternMatcher.group(1).trim();
			pakCdata.card4.put(PakCData.Card4.INTEB.toString(), inteb);
			String indsc = card4PatternMatcher.group(2).trim();
			pakCdata.card4.put(PakCData.Card4.INDSC.toString(), indsc);
			String iform = card4PatternMatcher.group(3).trim();
			pakCdata.card4.put(PakCData.Card4.IFORM.toString(), iform);
			String maxit = card4PatternMatcher.group(4).trim();
			pakCdata.card4.put(PakCData.Card4.MAXIT.toString(), maxit);
			String epsta = card4PatternMatcher.group(5).trim();
			pakCdata.card4.put(PakCData.Card4.EPSTA.toString(), epsta);
			String epstr = card4PatternMatcher.group(6).trim();
			pakCdata.card4.put(PakCData.Card4.EPSTR.toString(), epstr);
			String njrap = card4PatternMatcher.group(7).trim();
			pakCdata.card4.put(PakCData.Card4.NJRAP.toString(), njrap);
			String isolver = card4PatternMatcher.group(8).trim();
			pakCdata.card4.put(PakCData.Card4.ISOLVER.toString(), isolver);
			String frequency = card4PatternMatcher.group(9).trim();
			pakCdata.card4.put(PakCData.Card4.FREQUENCY.toString(), frequency);
			//	System.out.println(inteb + "\t" + indsc + "\t" + iform + "\t" + maxit + 
			//			"\t" + epsta + "\t" + epstr + "\t" + njrap + "\t" + isolver + "\t" + frequency);
		}

		while(card5PatternMatcher.find()){
			String irest = card5PatternMatcher.group(1).trim();
			pakCdata.card5.put(PakCData.Card5.IREST.toString(), irest);
			//	System.out.println(irest);
		}

		while(card6PatternMatcher.find()){
			String ndti = card6PatternMatcher.group(1).trim();
			String timei = card6PatternMatcher.group(2).trim();
			pakCdata.card6.put(PakCData.Card6.NDTI.toString(), ndti);
			pakCdata.card6.put(PakCData.Card6.TIMEI.toString(), timei);
			//	System.out.println(ndti + "\t" + timei);
		}

		int count = 1;
		while(card7PatternMatcher.find()) {
			Map<String, String> card7Row = new HashMap<String, String>();
			String n = card7PatternMatcher.group(1).trim();
			card7Row.put(PakCData.Card7.N.toString(), n);
			String i1 = card7PatternMatcher.group(2).trim();
			card7Row.put(PakCData.Card7.I1.toString(), i1);
			String i2 = card7PatternMatcher.group(3).trim();
			card7Row.put(PakCData.Card7.I2.toString(), i2);
			String i3 = card7PatternMatcher.group(4).trim();
			card7Row.put(PakCData.Card7.I3.toString(), i3);
			String i4 = card7PatternMatcher.group(5).trim();
			card7Row.put(PakCData.Card7.I4.toString(), i4);
			String i5 = card7PatternMatcher.group(6).trim();
			card7Row.put(PakCData.Card7.I5.toString(), i5);
			String i6 = card7PatternMatcher.group(7).trim();
			card7Row.put(PakCData.Card7.I6.toString(), i6);
			String i7 = card7PatternMatcher.group(8).trim();
			card7Row.put(PakCData.Card7.I7.toString(), i7);
			String i8 = card7PatternMatcher.group(9).trim();
			card7Row.put(PakCData.Card7.I8.toString(), i8);
			String cord1nx = card7PatternMatcher.group(10).trim();
			card7Row.put(PakCData.Card7.CORD1NX.toString(), cord1nx);
			String cord2ny = card7PatternMatcher.group(11).trim();
			card7Row.put(PakCData.Card7.CORD2NY.toString(), cord2ny);
			String cord3nz = card7PatternMatcher.group(12).trim();
			card7Row.put(PakCData.Card7.CORD3NZ.toString(), cord3nz);
			pakCdata.card7.put(String.valueOf(count++), card7Row);
			//System.out.println(n + "\t" + i1 + "\t" + i2 + "\t" + i3 + "\t" + i4 + 
			//		"\t" + i5 + "\t" + i6 + "\t" + i7 + "\t" + i8  + cord1nx + "\t" + cord2ny + "\t" + cord3nz + "\t");			
		}

		while(card8PatternMatcher.find()) {
			String netip = card8PatternMatcher.group(1).trim();			
			String net = card8PatternMatcher.group(2).trim();
			String indax = card8PatternMatcher.group(3).trim();
			String iatyp = card8PatternMatcher.group(4).trim();
			String nmodm = card8PatternMatcher.group(5).trim();
			String ipn = card8PatternMatcher.group(6).trim();
			//String unknown1 = card8PatternMatcher.group(7).trim();
			pakCdata.card8.put(PakCData.Card8.NETIP.toString(), netip);
			pakCdata.card8.put(PakCData.Card8.NET.toString(), net);
			pakCdata.card8.put(PakCData.Card8.INDAX.toString(), indax);
			pakCdata.card8.put(PakCData.Card8.IATYP.toString(), iatyp);
			pakCdata.card8.put(PakCData.Card8.NMODM.toString(), nmodm);
			pakCdata.card8.put(PakCData.Card8.IPN.toString(), ipn);
			//pakCdata.card8.put(PakCData.Card8.Unknown1.toString(), unknown1);		
			//System.out.println(netip + "\t" + net + "\t" + indax + "\t" + iatyp + "\t" + nmodm + 
			//		"\t" + ipn + "\t" + unknown1);
		}	

		while(card8_1_aPatternMatcher.find()) {
			String np2dmx = card8_1_aPatternMatcher.group(1).trim();
			String iswell = card8_1_aPatternMatcher.group(2).trim();
			pakCdata.card8_1_a.put(PakCData.Card8_1_a.NP2DMX.toString(), np2dmx);
			pakCdata.card8_1_a.put(PakCData.Card8_1_a.ISWELL.toString(), iswell);
			//System.out.println(np2dmx + "\t" + iswell);
		}

		count = 1;
		while(card8_1_bPatternMatcher.find()) {
			Map<String, String> card8_1_bRow = new HashMap<String, String>();
			String nn = card8_1_bPatternMatcher.group(1).trim();
			card8_1_bRow.put(PakCData.Card8_1_b.NN.toString(), nn);
			String nel1 = card8_1_bPatternMatcher.group(2).trim();
			card8_1_bRow.put(PakCData.Card8_1_b.NEL1.toString(), nel1);
			String nel2 = card8_1_bPatternMatcher.group(3).trim();
			card8_1_bRow.put(PakCData.Card8_1_b.NEL2.toString(), nel2);
			String nel3 = card8_1_bPatternMatcher.group(4).trim();
			card8_1_bRow.put(PakCData.Card8_1_b.NEL3.toString(), nel3);
			String nel4 = card8_1_bPatternMatcher.group(5).trim();
			card8_1_bRow.put(PakCData.Card8_1_b.NEL4.toString(), nel4);
			String nel5 = card8_1_bPatternMatcher.group(6).trim();
			card8_1_bRow.put(PakCData.Card8_1_b.NEL5.toString(), nel5);
			String nel6 = card8_1_bPatternMatcher.group(7).trim();
			card8_1_bRow.put(PakCData.Card8_1_b.NEL6.toString(), nel6);
			String nel7 = card8_1_bPatternMatcher.group(8).trim();
			card8_1_bRow.put(PakCData.Card8_1_b.NEL7.toString(), nel7);
			String nel8 = card8_1_bPatternMatcher.group(9).trim();
			card8_1_bRow.put(PakCData.Card8_1_b.NEL8.toString(), nel8);
			String materialId = card8_1_bPatternMatcher.group(10).trim();
			card8_1_bRow.put(PakCData.Card8_1_b.MaterialID.toString(), materialId);
			String youngsMod = card8_1_bPatternMatcher.group(11).trim();
			card8_1_bRow.put(PakCData.Card8_1_b.YoungsModulus.toString(), youngsMod);
			String dampCoeff = card8_1_bPatternMatcher.group(14).trim();
			card8_1_bRow.put(PakCData.Card8_1_b.DampingCoefficient.toString(), dampCoeff);
			//System.out.println(nel1 + "\t" + nel2 + "\t" + nel3 + "\t" + nel4 + 
			//		"\t" + nel5 + "\t" + nel6 + "\t" + nel7 + "\t" + nel8 + "\t" + 
			//		materialId + "\t" + youngsMod + "\t" + dampCoeff);					
			pakCdata.card8_1_b.put(String.valueOf(count++), card8_1_bRow);
		}

		while(card9PatternMatcher.find()){
			String numzad = card9PatternMatcher.group(1).trim();
			pakCdata.card9.put(PakCData.Card9.Numzad.toString(), numzad);
			//System.out.println(numzad);
		}

		while(card10PatternMatcher.find()){
			String qx0 = card10PatternMatcher.group(1).trim();
			String qy0 = card10PatternMatcher.group(2).trim();
			String qz0 = card10PatternMatcher.group(3).trim();
			String p0 = card10PatternMatcher.group(4).trim();			
			pakCdata.card10.put(PakCData.Card10.Qx0.toString(), qx0);
			pakCdata.card10.put(PakCData.Card10.Qy0.toString(), qy0);
			pakCdata.card10.put(PakCData.Card10.Qz0.toString(), qz0);
			pakCdata.card10.put(PakCData.Card10.p0.toString(), p0);
			//	System.out.println(qx0 + "\t" + qy0 + "\t" + qz0 + "\t" + p0);
		}

		while(card11PatternMatcher.find()){
			String maxsil = card11PatternMatcher.group(1).trim();
			String indpj = card11PatternMatcher.group(2).trim();
			pakCdata.card11.put(PakCData.Card11.MAXSIL.toString(), maxsil);
			pakCdata.card11.put(PakCData.Card11.INDPJ.toString(), indpj);
			//	System.out.println(maxsil + "\t" + indpj);
		}

		count = 1;
		while(card11_secondPatternMatcher.find()) {
			Map<String, String> card11_secondMap = new HashMap<String, String>();
			//Element, Node1, Node2, Node3, Node4, TimeFunc, IVal1, IVal2, IVal3, IVal4;
			String element = card11_secondPatternMatcher.group(1).trim();
			String node1 = card11_secondPatternMatcher.group(2).trim();
			String node2 = card11_secondPatternMatcher.group(3).trim();
			String node3 = card11_secondPatternMatcher.group(4).trim();
			String node4 = card11_secondPatternMatcher.group(5).trim();
			String timeFunc = card11_secondPatternMatcher.group(6).trim();
			String ival1 = card11_secondPatternMatcher.group(7).trim();
			String ival2 = card11_secondPatternMatcher.group(8).trim();
			String ival3 = card11_secondPatternMatcher.group(9).trim();
			String ival4 = card11_secondPatternMatcher.group(10).trim();
			card11_secondMap.put(PakCData.Card11_second.Element.toString(), element);
			card11_secondMap.put(PakCData.Card11_second.Node1.toString(), node1);
			card11_secondMap.put(PakCData.Card11_second.Node2.toString(), node2);
			card11_secondMap.put(PakCData.Card11_second.Node3.toString(), node3);
			card11_secondMap.put(PakCData.Card11_second.Node4.toString(), node4);
			card11_secondMap.put(PakCData.Card11_second.TimeFunc.toString(), timeFunc);
			card11_secondMap.put(PakCData.Card11_second.IVal1.toString(), ival1);
			card11_secondMap.put(PakCData.Card11_second.IVal2.toString(), ival2);
			card11_secondMap.put(PakCData.Card11_second.IVal3.toString(), ival3);
			card11_secondMap.put(PakCData.Card11_second.IVal4.toString(), ival4);
			//	System.out.println(element + "\t"  + node1 + "\t" + node2 + "\t" + node3 + "\t" + node4 + "\t" +  
			//		timeFunc + "\t" + ival1 + "\t" + ival2 + "\t" + ival3 + "\t" + ival4);
			pakCdata.card11_second.put(String.valueOf(count++), card11_secondMap);
		}
		//System.out.println("CARD12_1");
		while(card12_1_numberPatternMatcher.find()){
			String number = card12_1_numberPatternMatcher.group(1).trim();
			pakCdata.card12_1_number.put(PakCData.Card12_1_number.NoOfMaterials.toString(), number);
			//System.out.println("NUMBER: "+pakCdata.card12_1_number);
		}

		count = 1;		
		while(card12_1_a_materialCommonPatternMatcher.find()){
			Map<String, String> card12_1_aMap = new HashMap<String, String>();
			String model = card12_1_a_materialCommonPatternMatcher.group(1).trim();
			card12_1_aMap.put(PakCData.Card12_1_a_materialCommon.Model.toString(), model);
			String matid = card12_1_a_materialCommonPatternMatcher.group(2).trim();
			card12_1_aMap.put(PakCData.Card12_1_a_materialCommon.MatId.toString(), matid);
			String indjot = card12_1_a_materialCommonPatternMatcher.group(3).trim();
			card12_1_aMap.put(PakCData.Card12_1_a_materialCommon.Indjot.toString(), indjot);
			String mattype = card12_1_a_materialCommonPatternMatcher.group(4).trim();
			card12_1_aMap.put(PakCData.Card12_1_a_materialCommon.Mattype.toString(), mattype);
			pakCdata.card12_1_a_materialCommon.put(String.valueOf(count++), card12_1_aMap);
		}		

		while(card12_1_a_materialType1_1PatternMatcher.find()){
			String youngsMod = card12_1_a_materialType1_1PatternMatcher.group(1).trim();
			String poissonRatio = card12_1_a_materialType1_1PatternMatcher.group(2).trim();
			String density = card12_1_a_materialType1_1PatternMatcher.group(3).trim();			
			pakCdata.card12_1_a_materialType1_1.put(PakCData.Card12_1_a_materialType1_1.YoungsModulus.toString(), youngsMod);
			pakCdata.card12_1_a_materialType1_1.put(PakCData.Card12_1_a_materialType1_1.PoissonRatio.toString(), poissonRatio);
			pakCdata.card12_1_a_materialType1_1.put(PakCData.Card12_1_a_materialType1_1.Density.toString(), density);
		}		

		while(card12_1_a_materialType1_2PatternMatcher.find()){
			//Porosity, Permeability, BulkModuluesOfSolid, BulkModulusOfFluid; 			
			String porosity = card12_1_a_materialType1_2PatternMatcher.group(1).trim();
			String permeability = card12_1_a_materialType1_2PatternMatcher.group(2).trim();
			String solidBulkModulus = card12_1_a_materialType1_2PatternMatcher.group(3).trim();
			String fluidBulkModulus = card12_1_a_materialType1_2PatternMatcher.group(4).trim();			
			pakCdata.card12_1_a_materialType1_2.put(PakCData.Card12_1_a_materialType1_2.Porosity.toString(), porosity);
			pakCdata.card12_1_a_materialType1_2.put(PakCData.Card12_1_a_materialType1_2.Permeability.toString(), permeability);
			pakCdata.card12_1_a_materialType1_2.put(PakCData.Card12_1_a_materialType1_2.BulkModuluesOfSolid.toString(), solidBulkModulus);
			pakCdata.card12_1_a_materialType1_2.put(PakCData.Card12_1_a_materialType1_2.BulkModulusOfFluid.toString(), fluidBulkModulus);
		}

		while(card12_1_a_materialType2_1PatternMatcher.find()){
			String fluidDensity = card12_1_a_materialType2_1PatternMatcher.group(1).trim();
			String speedOfSound = card12_1_a_materialType2_1PatternMatcher.group(2).trim();
			pakCdata.card12_1_a_materialType2_1.put(PakCData.Card12_1_a_materialType2_1.DensityFluid.toString(), fluidDensity);
			pakCdata.card12_1_a_materialType2_1.put(PakCData.Card12_1_a_materialType2_1.SpeedOfSound.toString(), speedOfSound);
		}		

		while(card12_1_a_materialType2_2PatternMatcher.find()){
			//Porosity, Permeability, BulkModuluesOfSolid, BulkModulusOfFluid; 			
			String porosity = card12_1_a_materialType2_2PatternMatcher.group(1).trim();
			String permeability = card12_1_a_materialType2_2PatternMatcher.group(2).trim();
			String solidBulkModulus = card12_1_a_materialType2_2PatternMatcher.group(3).trim();
			String fluidBulkModulus = card12_1_a_materialType2_2PatternMatcher.group(4).trim();			
			pakCdata.card12_1_a_materialType2_2.put(PakCData.Card12_1_a_materialType1_2.Porosity.toString(), porosity);
			pakCdata.card12_1_a_materialType2_2.put(PakCData.Card12_1_a_materialType1_2.Permeability.toString(), permeability);
			pakCdata.card12_1_a_materialType2_2.put(PakCData.Card12_1_a_materialType1_2.BulkModuluesOfSolid.toString(), solidBulkModulus);
			pakCdata.card12_1_a_materialType2_2.put(PakCData.Card12_1_a_materialType1_2.BulkModulusOfFluid.toString(), fluidBulkModulus);
		}

		while(card13PatternMatcher.find()){
			//	NTABFT, MAXTFT;			
			String ntabft = 	card13PatternMatcher.group(1).trim();
			String maxtft = 	card13PatternMatcher.group(2).trim();
			pakCdata.card13.put(PakCData.Card13.NTABFT.toString(), ntabft);
			pakCdata.card13.put(PakCData.Card13.MAXTFT.toString(), maxtft);
		}

		while(card13_1_aPatternMatcher.find()){
			//	IBR, IMAX;	
			String ibr = 	card13_1_aPatternMatcher.group(1).trim();
			String imax = 	card13_1_aPatternMatcher.group(2).trim();
			pakCdata.card13_1_a.put(PakCData.Card13_1_aPattern.IBR.toString(), ibr);
			pakCdata.card13_1_a.put(PakCData.Card13_1_aPattern.IMAX.toString(), imax);
		}

		count = 1;
		while(card13_1_bPatternMatcher.find()){
			System.out.println(count);
			Map<String, String> map = new HashMap<String, String>();
			//	FN1IBRJ, FN2IBRJ;
			String fn1ibrj = card13_1_bPatternMatcher.group(1).trim();
			String fn2ibrj = card13_1_bPatternMatcher.group(2).trim();
			map.put(PakCData.Card13_1_bPattern.FN1IBRJ.toString(), fn1ibrj);
			map.put(PakCData.Card13_1_bPattern.FN2IBRJ.toString(), fn2ibrj);
			pakCdata.card13_1_b.put(String.valueOf(count++), map);					
		}

		while(card14PatternMatcher.find()){
			String numberOfForces = card14PatternMatcher.group(1).trim();
			pakCdata.card14.put(PakCData.Card14Pattern.NumberOfForces.toString(), numberOfForces);
		}

		count = 1;
		while(card14_1PatternMatcher != null && card14_1PatternMatcher.find()){
			System.out.println(count);
			Map<String, String> map = new HashMap<String, String>();
			String node = card14_1PatternMatcher.group(1).trim();
			String ind = card14_1PatternMatcher.group(2).trim();
			String timeFunc = card14_1PatternMatcher.group(3).trim();
			String value = card14_1PatternMatcher.group(4).trim();
			map.put(PakCData.Card14_1Pattern.Node.toString(), node);
			map.put(PakCData.Card14_1Pattern.Ind.toString(), ind);
			map.put(PakCData.Card14_1Pattern.TimeFunc.toString(), timeFunc);
			map.put(PakCData.Card14_1Pattern.Values.toString(), value);
			pakCdata.card14_1.put(String.valueOf(count++), map);					
		}

		if(card16PatternMatcher.find())
			System.out.println("Stop Card Found");

		return pakCdata;
	}	


	public static void main(String[] args) {
		PakCDatReaderLatest pakCReader = new PakCDatReaderLatest();
		//String pakCfilePath = "src/main/resources/PAK/Pak.dat";
//		String pakCfilePath = "/home/panos/Desktop/TaperedLIUPak.dat";
		String pakCfilePath = "/media/panos/USB/Cochlea Box Model Longitudinal Coupling/out/Pak.dat";
		
		
		PakCData pakCData1 = pakCReader.read(pakCfilePath);
		System.out.println(pakCData1);
		//System.out.println(pakCData1.card1.get(PakCData.Card1.TITLE.toString()));

        }

}
