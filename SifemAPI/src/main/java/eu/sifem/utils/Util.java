package eu.sifem.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import virtuoso.jena.driver.VirtDataset;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.resultset.ResultSetMem;

import eu.sifem.model.to.DataSetHashCacheTO;
import eu.sifem.model.to.ParamBean;


/**
 * 
 * @author kartik/jbjares
 * 
 */
public class Util {
	
	 static final String EMPTY_STRING = "";
	 static final String Y = "y";
	 static final String X = "x";
	 static final String FILE = "file:///";
	 static final String JAVA_UTIL_ARRAY_LIST = "java.util.ArrayList";
	 static final String HTTP_WWW_SIFEMONTOLOGIES_COM_ONTOLOGIES = "http://www.sifemontologies.com/ontologies";
	 
	 //TODO Refactoring is necessary, python.exe path must be transparent.
	 static final String C_PYTHON34_PYTHON_EXE = "C:\\ProgramFilesDevel\\python\\Python34\\python.exe ";
	 static final String DISTINCT = "DISTINCT";
	 static final String WHERE = "WHERE";
	 static final String SELECT = "SELECT";
	 static final String GRAPH = "graph";

	public static ParamBean getPakBean(List<ParamBean> lstOfBeans, String param) {
		ParamBean paramBeanTemp = new ParamBean();
		String paramName = null;
		if (param.equals(Constant.PAKSolver.DIV_L)) {
			paramName = Constant.PAKSolver.DIV_L;
		} else if (param.equals(Constant.PAKSolver.DIV_B)) {
			paramName = Constant.PAKSolver.DIV_B;
		} else if (param.equals(Constant.PAKSolver.DIV_W)) {
			paramName = Constant.PAKSolver.DIV_W;
		} else if (param.equals(Constant.PAKSolver.DIV_H)) {
			paramName = Constant.PAKSolver.DIV_H;
		} else if (param.equals(Constant.PAKSolver.DIV_h)) {
			paramName = Constant.PAKSolver.DIV_h;
		} else if (param.equals(Constant.PAKSolver.LENGTH)) {
			paramName = Constant.PAKSolver.LENGTH;
		} else if (param.equals(Constant.PAKSolver.HEIGHT)) {
			paramName = Constant.PAKSolver.HEIGHT;
		} else if (param.equals(Constant.PAKSolver.WIDTH)) {
			paramName = Constant.PAKSolver.WIDTH;
		} else if (param.equals(Constant.PAKSolver.WIDTH_OF_BM)) {
			paramName = Constant.PAKSolver.WIDTH_OF_BM;
		} else if (param.equals(Constant.PAKSolver.THICKNESS)) {
			paramName = Constant.PAKSolver.THICKNESS;
		} else if (param.equals(Constant.PAKSolver.LOAD_FREQUENCY)) {
			paramName = Constant.PAKSolver.LOAD_FREQUENCY;
		} else if (param.equals(Constant.PAKSolver.LOAD_VALUE)) {
			paramName = Constant.PAKSolver.LOAD_VALUE;
		} else if (param.equals(Constant.PAKSolver.DENSITY_RHO)) {
			paramName = Constant.PAKSolver.DENSITY_RHO;
		} else if (param.equals(Constant.PAKSolver.SPEED_OF_SOUND_C)) {
			paramName = Constant.PAKSolver.SPEED_OF_SOUND_C;
		} else if (param.equals(Constant.PAKSolver.YOUNG_MODULUS)) {
			paramName = Constant.PAKSolver.YOUNG_MODULUS;
		} else if (param.equals(Constant.PAKSolver.DAMPING)) {
			paramName = Constant.PAKSolver.DAMPING;
		} else if (param.equals(Constant.PAKSolver.DISP_X)) {
			paramName = Constant.PAKSolver.DISP_X;
		} else if (param.equals(Constant.PAKSolver.DISP_Y)) {
			paramName = Constant.PAKSolver.DISP_Y;
		} else if (param.equals(Constant.PAKSolver.DISP_Z)) {
			paramName = Constant.PAKSolver.DISP_Z;
		} else if (param.equals(Constant.PAKSolver.FUNCTION)) {
			paramName = Constant.PAKSolver.FUNCTION;
		}
		for (ParamBean paramBean : lstOfBeans) {
			if (paramBean.getPramaName().equals(paramName)) {
				paramBeanTemp = paramBean;
				break;
			}
		}
		return paramBeanTemp;
	}

	public static boolean isIntegerParseInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException nfe) {
		}
		return false;
	}
	
	public Integer getInstacesNumberByConfigSessionID(String configSessionID){
		Integer instanceNumber = null;
                System.err.println("configSession:" + configSessionID);
		if(configSessionID!=null && !"".equals(configSessionID)){
			if(configSessionID.contains(";")){
				instanceNumber = configSessionID.split(";").length;						
			}else{
				instanceNumber = 1;
			}
		}
		return instanceNumber;
	}

	public String fixNumericValueForCacheList(String testStr){
		if(testStr.contains("^^")){
			testStr = testStr.substring(0,testStr.indexOf("^^"));								
		}
		if(!NumberUtils.isNumber(testStr)){
			if(testStr.contains("[")){
				testStr = testStr.replace("[","");
			}
			if(testStr.contains("]")){
				testStr = testStr.replace("]","");
			}
			if(testStr.contains("'")){
				testStr = testStr.replace("'","");
			}
			if(NumberUtils.isNumber(testStr)){
				return testStr;
			}
		}else{
			return testStr;
		}
		return null;
	}
	
//	public static void main(String[] args) {
//		String testStr = "['0.0']";
//		if(!NumberUtils.isNumber(testStr)){
//			if(testStr.contains("[")){
//				testStr = testStr.replace("[","");
//			}
//			if(testStr.contains("]")){
//				testStr = testStr.replace("]","");
//			}
//			if(testStr.contains("'")){
//				testStr = testStr.replace("'","");
//			}
//			if(NumberUtils.isNumber(testStr)){
//				System.out.println(testStr);
//			}
//		}
//	}

	public static String shuffleString(String str) {
		ArrayList<Character> chars = new ArrayList<Character>(str.length());
		for (char c : str.toCharArray()) {
			chars.add(c);
		}
		Collections.shuffle(chars);
		char[] shuffled = new char[chars.size()];
		for (int i = 0; i < shuffled.length; i++) {
			shuffled[i] = chars.get(i);
		}
		String shuffledWord = new String(shuffled);
		return shuffledWord;
	}

	public static Map<String, String> getMappedObjectWithJustStringsAttributes(Object obj) throws Exception {
		Map<String, String> mappedObject = new TreeMap<String, String>();
		Field[] fields = obj.getClass().getDeclaredFields();
		Method[] methods = obj.getClass().getDeclaredMethods();
		for (Field field : fields) {
			for (Method method : methods) {
				if (method.getName().trim().toUpperCase()
						.contains(field.getName().trim().toUpperCase())
						&& method.getName().startsWith("get")) {
					mappedObject.put(field.getName(),
							(String) method.invoke(obj));
				}
			}
		}
		return mappedObject;
	}
	
	

	
	public String getMappedObjectAsAtring(Map<String, String> propsWithValues) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> item : propsWithValues.entrySet()) {
			  String key = item.getKey();
			  String value = item.getValue();
			  sb.append(key);
			  sb.append(value);
		}
		return sb.toString();
	}

	private static byte[] linebreak = {}; // Remove Base64 encoder default
											// linebreak
	private static String secret = "tvnw63ufg9gh5392"; // secret key length must
														// be 16
	private static SecretKey key;
	private static Cipher cipher;
	private static Base64 coder;


	public  static String encryptAES(String plainText)
			throws Exception {
		key = new SecretKeySpec(secret.getBytes(), "MD5");
		cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
		coder = new Base64(32, linebreak, true);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(plainText.getBytes());
		return new String(coder.encode(cipherText));
	}
	
	public  static byte[] encryptBase64(String plainText)
			throws Exception {
		byte[]   bytesEncoded = Base64.encodeBase64(plainText.getBytes());
		return bytesEncoded;
	}

	public  static byte[] dencryptBase64(String plainText)
			throws Exception {
		byte[] valueDecoded= Base64.decodeBase64(plainText);
		return valueDecoded;
	}
	
	
	
	public static String generateHashCacheID(DataSetHashCacheTO cache) throws Exception{
		String projectName = cache.getProjectName()==null?"":cache.getProjectName();
		String simulationName = cache.getSimulationName()==null?"":cache.getSimulationName();
		String xName = cache.getxName()==null?"":cache.getxName();
		String yName = cache.getyName()==null?"":cache.getyName();
		String zName = cache.getzName()==null?"":cache.getzName();
		String result = projectName+"_"+simulationName+"_"+xName+"_"+yName+"_"+zName;
		return result.toUpperCase();
	}

    public String toProperCase(String s) {
         String temp=s.trim();
         String spaces="";
         if(temp.length()!=s.length())
         {
         int startCharIndex=s.charAt(temp.indexOf(0));
         spaces=s.substring(0,startCharIndex);
         }
         temp=temp.substring(0, 1).toUpperCase() +
         spaces+temp.substring(1).toLowerCase()+" ";
         return temp;

     }
     
     public String replaceSpacesForUnderline(String in){
    	 if(in==null || "".equals(in)){
    		 return in;
    	 }
    	 
    	 while(in.contains(" ")){
    		 in = in.replace(" ","_");
    	 }
    	 return in;
     }
     
     public String replaceUnderlinesForSpace(String in){
    	 if(in==null || "".equals(in)){
    		 return in;
    	 }
    	 
    	 while(in.contains("_")){
    		 in = in.replace("_"," ");
    	 }
    	 return in;
     }

     public String removeSpecialIdentationCharsFromString(String str){
    	 return str.replaceAll("[\n\r\t]", " ");
     }
	
     public void createDirectoriesWithMkdirsIfItNotExist(File file){
 		if(!file.exists()){
			file.mkdirs();
		}else{
			file.delete();
			file.mkdirs();
		}
     }

 	public static String changeStringToDefaultSeparator(String parameterValuesFromFile) {

		parameterValuesFromFile = StringUtils.deleteWhitespace(parameterValuesFromFile);
		if(StringUtils.containsIgnoreCase(parameterValuesFromFile,";")){
			return parameterValuesFromFile;
		}
		if(StringUtils.containsIgnoreCase(parameterValuesFromFile,"\n") && !StringUtils.containsIgnoreCase(parameterValuesFromFile,";")){
			parameterValuesFromFile = StringUtils.replace(parameterValuesFromFile,"\n", ";");
			return parameterValuesFromFile;
		}
		if(StringUtils.containsIgnoreCase(parameterValuesFromFile,",") && !StringUtils.containsIgnoreCase(parameterValuesFromFile,";")){
			parameterValuesFromFile = StringUtils.replace(parameterValuesFromFile,",", ";");
			return parameterValuesFromFile;
		}
		if(StringUtils.containsIgnoreCase(parameterValuesFromFile,System.getProperty("file.separator")) && !StringUtils.containsIgnoreCase(parameterValuesFromFile,";")){
			parameterValuesFromFile = StringUtils.replace(parameterValuesFromFile, System.getProperty("file.separator"), ";");
			return parameterValuesFromFile;
		}
		
		return parameterValuesFromFile;
	}

	public static int countPossiblesCombinationsBetweenMapKeysAndValues(Map<String, List<String>> mapAttributeValues) {
		int values = 0;
		for (Map.Entry<String,List<String>> entry : mapAttributeValues.entrySet()) {
				values = values + entry.getValue().size();				
		}
		return mapAttributeValues.size()*values;
	}
//TODO create testcase	
//	public static void main(String[] args) {
//		Map<String, List<String>> mapAttributeValues = new TreeMap<String, List<String>>();
//		
//		for(int i = 0; i< 3; i ++){
//			List<String> valueList = new ArrayList<String>();
//			for(int j = 0; j< 3; j ++){
//				valueList.add(new Integer(j).toString());
//				//System.out.println(j);
//				System.out.println(j);
//			}
//			mapAttributeValues.put(new Integer(i).toString(),valueList);
//		}		
//		System.out.println(mapAttributeValues.size());
//
//		
//		System.out.println(countPossiblesCombinationsBetweenMapKeysAndValues(mapAttributeValues));
//	}

	public static String tryCleanToNumeric(String value) {
		StringBuilder sb = new StringBuilder();
		char[] charArr = value.toCharArray();
		for(Character realChar:charArr){
			String charStr = realChar.toString();
			if(".".equals(charStr)){
				sb.append(charStr);
			}
			if(",".equals(charStr)){
				sb.append(charStr);
			}
			if("-".equals(charStr)){
				sb.append(charStr);
			}
			if(StringUtils.isNumeric(charStr)){
				sb.append(charStr);
			}
		}
		return sb.toString();
	}

	public static String removeListKeysFromString(String arg){
		if(arg==null || "".equals(arg)){
			return arg;
		}
		arg = StringUtils.replaceChars(arg,"[","");
		arg = StringUtils.replaceChars(arg,"]","");
		return arg;
	}
	
	//TODO create test case
//	public static void main(String[] args) {
//		String yCoord_="[['1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '7.5E-5',  '0.0',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '7.5E-5',  '0.0',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '7.5E-5',  '0.0',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '0.0',  '0.0',  '7.5E-5',  '7.5E-5',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '3.0E-4',  '3.0E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '7.5E-5',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '3.0E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '2.25E-4',  '1.5E-4',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4',  '7.5E-5',  '7.5E-5',  '1.5E-4',  '1.5E-4']]";
//		yCoord_ = new Util().removeListKeysFromString(yCoord_.replace(" ",""));
//		System.out.println(Arrays.asList(yCoord_.split(",")).toString());
//	}

	public static String getJsonStrFromObject(Object obj) {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(obj);
	}
	
	public static ResultSet executeQuery(String query, VirtDataset virtDataset) {
		VirtGraph vg = new VirtGraph(virtDataset.getGraphUrl(),
				virtDataset.getGraphUser(), virtDataset.getGraphPassword());
		VirtuosoQueryExecution vur = VirtuosoQueryExecutionFactory.create(
				query, vg);
		ResultSet rs = vur.execSelect();
		return rs;
	}
	
	public static ResultSet executeQuery(String query, VirtGraph virtGraph) {
		VirtuosoQueryExecution vur = VirtuosoQueryExecutionFactory.create(
				query, virtGraph);
		ResultSet rs = vur.execSelect();
		return rs;
	}
	
	
	
	
	// format values
	public  Map<String, List<String>> getValues(Map<String, List<RDFNode>> queryModel,Map<String, String> queryAttributes) throws Exception {
		Map<String, List<String>> result = new LinkedHashMap<String, List<String>>();

		Set<String> checkAttributes = new TreeSet<String>();
		Set<String> usedKeys = new TreeSet<String>();
		List<RDFNode> usedResources = new ArrayList<RDFNode>();

		for (Map.Entry<String, String> entry : queryAttributes.entrySet()) {

			for (Map.Entry entryQueryModel : queryModel.entrySet()) {
				if (usedKeys.contains(entryQueryModel.getKey())) {
					continue;
				}
				String mainkey = (String) entryQueryModel.getKey();
				usedKeys.add(mainkey);

				if (JAVA_UTIL_ARRAY_LIST.equals(entryQueryModel.getValue()
						.getClass().getName())) {
					@SuppressWarnings("unchecked")
					List<RDFNode> rdfnodeList = (List<RDFNode>) entryQueryModel
							.getValue();

					List nodeList = new ArrayList(new ArrayList<String>());
					int count = 0;
					for (RDFNode node : rdfnodeList) {
						if (node.isLiteral()) {
							String literalNodeAsString = node.asLiteral().toString();
							if(literalNodeAsString.contains("^^")){
								literalNodeAsString = literalNodeAsString.substring(0,literalNodeAsString.indexOf("^^"));								
							}
							nodeList.add(literalNodeAsString);
							count++;								
						}
						if (node.isResource()) {
							if (!usedResources
									.toString()
									.toString()
									.contains(
											node.asResource().toString()
													.toString())) {
								usedResources.add(node);
							}

							if (!node
									.asResource()
									.getURI()
									.contains(
											HTTP_WWW_SIFEMONTOLOGIES_COM_ONTOLOGIES)) {
								continue;
							}
							nodeList.add(node.asResource().getURI());
							count++;
						}
						int rdfnodeListSize = rdfnodeList.size();

						if (rdfnodeListSize == count) {
							String key = (String) entryQueryModel.getKey();
							List<String> resultList = new ArrayList<String>();
							resultList.addAll(nodeList);
							result.put(new String(key), resultList);
							checkAttributes.add(key);
							count = 0;
							nodeList.clear();
							continue;
						}

					}

				}

			}

		}

		return result;

	}
	
	// format values
	public  void addConvertedValuesToList(String key,String value,Map<String, List<String>> values) throws Exception {
		Map<String, List<String>> result = new LinkedHashMap<String, List<String>>();

		String mainkey = key;
		List<String> valueList = Collections.EMPTY_LIST;
		if(values==null || values.get(mainkey)==null || values.get(mainkey).isEmpty()){
			valueList = new ArrayList<String>();
		}else{
			valueList = values.get(mainkey);
		}
		
		String literalNodeAsString = value;
		if(literalNodeAsString.contains("^^")){
			literalNodeAsString = literalNodeAsString.substring(0,literalNodeAsString.indexOf("^^"));
			valueList.add(literalNodeAsString);
		}else{
			valueList.add(value);
		}
		
                //System.err.println(" result.size=" + result.size() + " | " + result.keySet());

                //System.err.println(" ---- Util " + key + " " + valueList + " " + literalNodeAsString);
		result.put(key, valueList);
                values.put(key, valueList);
                //values = result;

	}


	// format values
	public  Map<String, List<String>> getValues(Map<String, List<RDFNode>> queryModel, String queryStr) throws Exception {
		Map<String, List<String>> result = new LinkedHashMap<String, List<String>>();

		Map<String, String> queryAttributes = new LinkedHashMap<String, String>();
		Set<String> checkAttributes = new TreeSet<String>();
		fillQueryAttributes(queryStr, queryAttributes);

		Set<String> usedKeys = new TreeSet<String>();
		List<RDFNode> usedResources = new ArrayList<RDFNode>();

		for (Map.Entry<String, String> entry : queryAttributes.entrySet()) {

			for (Map.Entry entryQueryModel : queryModel.entrySet()) {
				if (usedKeys.contains(entryQueryModel.getKey())) {
					continue;
				}
				String mainkey = (String) entryQueryModel.getKey();
				usedKeys.add(mainkey);

				if (JAVA_UTIL_ARRAY_LIST.equals(entryQueryModel.getValue()
						.getClass().getName())) {
					@SuppressWarnings("unchecked")
					List<RDFNode> rdfnodeList = (List<RDFNode>) entryQueryModel
							.getValue();

					List pyList = new ArrayList(new ArrayList<String>());
					int count = 0;
					for (RDFNode node : rdfnodeList) {
						if (node.isLiteral()) {
							String literalNodeAsString = node.asLiteral().toString();
							if(literalNodeAsString.contains("^^")){
								literalNodeAsString = literalNodeAsString.substring(0,literalNodeAsString.indexOf("^^"));								
							}
							pyList.add("'" + literalNodeAsString + "'");
							count++;								
						}
						if (node.isResource()) {
							if (!usedResources
									.toString()
									.toString()
									.contains(
											node.asResource().toString()
													.toString())) {
								usedResources.add(node);
							}

							if (!node
									.asResource()
									.getURI()
									.contains(
											HTTP_WWW_SIFEMONTOLOGIES_COM_ONTOLOGIES)) {
								continue;
							}
							pyList.add("'" + node.asResource().getURI() + "'");
							count++;
						}
						int rdfnodeListSize = rdfnodeList.size();

						if (rdfnodeListSize == count) {
							String key = (String) entryQueryModel.getKey();
							List<String> resultList = new ArrayList<String>();
							resultList.addAll(pyList);
							result.put(new String(key), resultList);
							checkAttributes.add(key);
							count = 0;
							pyList.clear();
							continue;
						}

					}

				}

			}

		}

		return result;

	}
	
	public String convertNoNumberToNumber(String numer){
		Boolean isNumber = NumberUtils.isNumber(numer);
		if(!isNumber){
			if(numer.startsWith("-")){
				numer = numer.substring(1,numer.length());
			}
			if(numer.contains("-")){
				numer = numer.replace("-","E-");
			}
			isNumber = NumberUtils.isNumber(numer);
			if(!isNumber){
				return numer;
			}
			
		}
		return numer;
	}

	
	public void fillQueryAttributes(String originalQuery,
			Map<String, String> attributes) throws Exception {
		String query = BasicFileTools
				.verifyAndConvertFileToString(originalQuery);
		if (query == null || "".equals(query)) {
			throw new RuntimeException("Empty or invalid query!");
		}

		if (!query.trim().toUpperCase().contains(SELECT.trim().toUpperCase())
				&& !query.trim().toUpperCase()
						.contains(WHERE.trim().toUpperCase())) {
			return;
		}

		Boolean thisQueryContainsWehereClause = StringUtils.containsIgnoreCase(
				query, WHERE);
		if (thisQueryContainsWehereClause) {
			query = query.trim().substring(
					StringUtils.indexOfIgnoreCase(query, SELECT)
							+ SELECT.length(),
					StringUtils.indexOfIgnoreCase(query, WHERE));
		}

		Boolean thisQueryContainsGraphClause = StringUtils.containsIgnoreCase(
				query, GRAPH);
		if (thisQueryContainsGraphClause) {
			query = query.trim().substring(
					StringUtils.indexOfIgnoreCase(query, SELECT)
							+ SELECT.length(),
					StringUtils.indexOfIgnoreCase(query, GRAPH));
		}

		if (StringUtils.startsWithIgnoreCase(query, DISTINCT)) {
			StringUtils.removeStartIgnoreCase(query, DISTINCT);
		}
		
		
		
		
		if (!query.trim().startsWith("?")) {
			throw new RuntimeException(
					"Malformed SPARQL Query. This version assumes that all query is followed by attributes (starting with ?). ");
		}
		query = query.substring(1, query.length());
		if (query.contains("?")) {
			query = query.replace("?", ";").trim();
			String[] attributesArr = query.split(";");
			for (String attribute : attributesArr) {
				
				
				if ("".equals(attribute)) {
					continue;
				}
				if (StringUtils.contains(attribute, " ")){
					attribute = attribute.replace(" ","_");
				}
				if (StringUtils.contains(attribute, "_")){
					attribute = attribute.substring(0,attribute.indexOf("_"));
				}
				if (StringUtils.contains(attribute, "_")){
					attribute.replace("_","");
				}
				if (attribute.trim().contains("{")) {
					attribute = StringUtils.remove(attribute, "{");
				}
				attributes.put(attribute.trim(), attribute.trim());
			}
			return;
		}
		String uniqueAttribute = query;
		attributes.put(uniqueAttribute, uniqueAttribute);
		return;
	}
	
	public void fillQueryAttributes(String originalQuery,List<String> attributes) throws Exception {
		Map<String,String> attributesMap = new TreeMap<String,String>();
		fillQueryAttributes(originalQuery, attributesMap);
		for(Map.Entry<String,String> entry:attributesMap.entrySet()){
			attributes.add(entry.getValue());
		}
		return;
	}

	
	public List<String> removeDuplicatedValues(List<String> original){
		List<String> finalList = new ArrayList<String>();

		for (String s : original) {
		    if (!finalList.contains(s)) {
		        finalList.add(s);
		    }
		}
		return finalList;
	}

	public String addNamedGraphsToQuery(String queryStr,List<String> namedGraphs) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		for(String namedGraph:namedGraphs){
				sb.append("FROM NAMED <");
				sb.append(namedGraph);
				sb.append(">");
				sb.append("\n");
		}
		String queryInitial = queryStr;
		String replacePart = "";
		String newPart = "WHERE { GRAPH ?g ";
		String resultAfterReplacement = "";
		replacePart = queryStr.substring(queryStr.indexOf("WHERE"),queryStr.indexOf("WHERE")+5);
		resultAfterReplacement = queryStr.replace(replacePart, newPart);
		resultAfterReplacement = resultAfterReplacement+" }";

		resultAfterReplacement = resultAfterReplacement.replace("#displacement.names#", sb.toString());
		
		//System.out.println(resultAfterReplacement);
		queryStr = queryStr.replace("#displacement.names#", sb.toString());
		return queryStr;
	}

	public List<Double> convertListStrToDoubleList(List<String> stringList) {
		List<Double> result = new ArrayList<Double>();
		for(String value:stringList){
			if(!NumberUtils.isNumber(value)){
				continue;
			}
			result.add(new Double(value));
		}
		
		return result;
	}

	public List<String> convertDoubleListToStrList(List<Double> doubleList) {
		List<String> result = new ArrayList<String>();
		for(Double value:doubleList){
			result.add(value.toString());
		}
		
		return result;
	}

	public List<String> changeFromRDFNodeToStringList(List<RDFNode> list) {
		List<String> result = new ArrayList<String>();
		for(RDFNode node:list){
			result.add(node.toString());
		}
		return result;
	}

	public Map<String, List<String>> getValuesFromMemMode(ResultSetMem inMem,Map<String, String> attributes) {
		return null;
	}


	
	//create tescase
//	public static void main(String[] args) {
//		String number = "-3.43305-250";
//		Boolean isNumber = NumberUtils.isNumber(number);
//		System.out.println(isNumber);
//		if(!isNumber){
//			if(number.startsWith("-")){
//				number = number.substring(1,number.length());
//			}
//		}
//		if(number.contains("-")){
//			number = number.replace("-","E-");
//		}
//		System.out.println(number);
//		isNumber = NumberUtils.isNumber(number);
//		System.out.println(isNumber);
//	}
	
//TODO create test case
//	public static void main(String[] args) {
//		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
//				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
//				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
//				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
//				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
//				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
//				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
//				+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id  "
//				+ "#displacement.names#  "
//				+ "WHERE { "
//				//+ "GRAPH ?g {  "
//				+ "?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .   "
//				+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .   "
//				//+ "} "
//				+ "}";
//		
//		List<String> namedGraphs = new ArrayList<String>();
//		//FROM NAMED <
//		//>
//		namedGraphs.add("http://www.sifemontologies.com/ontologies/projectThree_simi1_instance_0");
//		namedGraphs.add("http://www.sifemontologies.com/ontologies/projectThree_simi1_instance_1");
//		namedGraphs.add("http://www.sifemontologies.com/ontologies/projectThree_simi1_instance_2");
//		
//		StringBuilder sb = new StringBuilder();
//		sb.append("\n");
//		for(String name:namedGraphs){
//			sb.append("FROM NAMED <");
//			sb.append(name);
//			sb.append(">");
//			sb.append("\n");
//		}
//		//System.out.println(sb.toString());
//		String queryInitial = query;
//		String replacePart = "";
//		String newPart = "WHERE { GRAPH ?g ";
//		String resultAfterReplacement = "";
//		replacePart = query.substring(query.indexOf("WHERE"),query.indexOf("WHERE")+5);
//		resultAfterReplacement = query.replace(replacePart, newPart);
//		resultAfterReplacement = resultAfterReplacement+" }";
//
//		resultAfterReplacement = resultAfterReplacement.replace("#displacement.names#", sb.toString());
//		
//		System.out.println(resultAfterReplacement);
//	}
	
}
