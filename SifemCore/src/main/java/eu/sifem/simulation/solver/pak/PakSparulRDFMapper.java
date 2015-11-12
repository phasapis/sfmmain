package eu.sifem.simulation.solver.pak;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.sifem.model.to.DatAndUnvTTLTO;
import eu.sifem.model.to.SimulationInstanceTO;
import eu.sifem.ontologies.FEMSettingsPAK;
import eu.sifem.ontologies.FemOntology;
import eu.sifem.ontologies.SimulationOntology;
import eu.sifem.service.IPakRDFMapperService;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.dao.IDatAndUnvTTLDAOService;
import eu.sifem.service.dao.ISimulationInstanceDAOService;
import eu.sifem.utils.BasicFileTools;
import eu.sifem.utils.Util;
import java.util.logging.Level;
import java.util.logging.Logger;
//import eu.sifem.test.integrated.PakFData;

@Service(value="pakSparulRDFMapper")
public class PakSparulRDFMapper implements IPakRDFMapperService {

	private String prefixesFile = "src/main/resources/Prefixes.txt";
	private static String prefixPatternString = "PREFIX\\s*(.*):\\s*<(.*)#>";		
	private String femModelName = "BoxModel1";
	private String femModelNamePrefix = "bw";	
	private int roundOffDecimalPlaces = 9;
	private static final String PREFIX = "Prefix ";
	private static final String PREFIX_XSD_HTTP_WWW_W3_ORG_2001_XML_SCHEMA = "Prefix xsd: <http://www.w3.org/2001/XMLSchema#> ";
	//private String experimentNameUri = "http://www.sifemontologies.com/ontologies/BoxModel#";
	
	@Autowired
	private ISimulationInstanceDAOService simulationInstanceDAO;

	@Autowired
	private IResourceInjectionService resourceInjectionService;
	
	@Autowired
	private IDatAndUnvTTLDAOService datAndUnvTTLDAOService;
	
	
	
	public PakSparulRDFMapper(){
	}

	public PakSparulRDFMapper(String femModelName, String femModelNamePrefix) {
		this.femModelName = femModelName;
		this.femModelNamePrefix = femModelNamePrefix;
	}

	public void setFemModelBasicData(String femModelName, String femModelNamePrefix) {
		this.femModelName = femModelName;
		this.femModelNamePrefix = femModelNamePrefix;
	}

	public double round(double a, int roundOffDecimalPlaces){
		double pow = Math.pow(10, roundOffDecimalPlaces);
		return (double) Math.round(a * pow) / pow;
	}

	public void setPrefixFile(String prefixesFile){
		this.prefixesFile = prefixesFile;
	}

	
	@Override
	public InputStream unvToRDFService(SimulationInstanceTO simulationInstanceTO,InputStream simulationFile) throws Exception {
	    
		StringBuilder builder = new StringBuilder();
		//addPrefixes(getPrefixMap(), builder);
		String prefixes = "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> . \n"
				+ "@prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#> .  \n"
				+ "@prefix dao:<http://www.sifemontologies.com/ontologies/DataAnalysis.owl#> .  \n"
				+ "@prefix omlprop:<http://codata.jp/OML-Property.owl#> .  \n"
				+ "@prefix fem:<http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> .  \n"
				+ "@prefix xsd:<http://www.w3.org/2001/XMLSchema#> .  \n"
				+ "@prefix owl:<http://www.w3.org/2002/07/owl#> .  \n"
				+ "@prefix sim:<http://www.sifemontologies.com/ontologies/Simulation.owl#> .  \n"
				+ "@prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> .  \n"
				+ "@prefix pakFemSet:<http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> .  \n"
				+ "@prefix bw:<http://www.sifemontologies.com/ontologies/BoxModel#> . \n";
		//addPrefix(this.femModelNamePrefix, this.experimentNameUri, builder);	
		builder.append(prefixes);
		builder.append(" \n ");
		//builder.append("INSERT DATA { \n");
		
		
		//TODO remove it
		//String simulationFileAsStr = IOUtils.toString(simulationFile, "UTF-8"); 
//		if(simulationFileAsStr!=null){
//			FileUtils.writeStringToFile(new File("C:\\SifemWindowsResourceFiles\\tmp\\unv.txt"), simulationFileAsStr);
//		}
//		BufferedReader reader = BasicFileTools.getBufferedReaderFile(simulationFileAsStr);
		BufferedReader reader = BasicFileTools.getBufferedReaderFile(simulationFile);
		String line = null;
		//model
		String modelResource = femModelNamePrefix +":" + femModelName;
		String unvOutputFileResource = modelResource + "unvOutputFile";
		Map<String, String> unvOutputFileResourceProps = new HashMap<String, String>();
		int numOf55Blocks = 1;
		try {
			while((line = reader.readLine()) != null){
				line = line.trim();				
				if(line.equals("-1")){
					while((line = reader.readLine()) != null){
						line = line.trim();						
						if(line.equals("15")){
							String block15Resource = modelResource + "Block15";
							Map<String, String> block15ResourceProps = new HashMap<String, String>();
							//Block 15 props: hasUNVBlockIdPartOne
							block15ResourceProps.put(FEMSettingsPAK.Property.DataProperty.hasUNVBlockIdPartOne, 
									"15");
							block15ResourceProps.put(FEMSettingsPAK.Property.ObjectProperty.isUNVBlockOf, unvOutputFileResource);
							unvOutputFileResourceProps.put(FEMSettingsPAK.Property.ObjectProperty.hasUNVBlock, block15Resource);
							boolean isFirstNode = true;
							while((line = reader.readLine()) != null){
								line = line.trim();
								if(line.equals("-1"))
									break;
								//System.out.println(line);
								String[] split = line.split("\\s+");
								String count = split[0].trim();
								//String unusedValue1 = split[1].trim();
								//String unusedValue2 = split[2].trim();
								String color = split[3].trim();
								String nodeCoordinateX = split[4].trim();
								nodeCoordinateX = String.valueOf(round(Double.parseDouble(nodeCoordinateX), roundOffDecimalPlaces));								
								String nodeCoordinateY = split[5].trim();
								nodeCoordinateY = String.valueOf(round(Double.parseDouble(nodeCoordinateY), roundOffDecimalPlaces));								
								String nodeCoordinateZ = split[6].trim();
								nodeCoordinateZ = String.valueOf(round(Double.parseDouble(nodeCoordinateZ), roundOffDecimalPlaces));								
								//node
								String nodeResource = modelResource + "Node" + count;
								Map<String, String> nodeResourceProps = new HashMap<String, String>();								
								//node props: hasNodalPointNumber, hasX,Y,ZCoordinate		
								nodeResourceProps.put(FemOntology.Property.DataProperty.hasColour, color);
								nodeResourceProps.put(FemOntology.Property.DataProperty.hasXCoordinate, "\""+new Util().convertNoNumberToNumber(nodeCoordinateX)+"\"^^xsd:double");
								nodeResourceProps.put(FemOntology.Property.DataProperty.hasYCoordinate, "\""+new Util().convertNoNumberToNumber(nodeCoordinateY)+"\"^^xsd:double");
								nodeResourceProps.put(FemOntology.Property.DataProperty.hasZCoordinate, "\""+new Util().convertNoNumberToNumber(nodeCoordinateZ)+"\"^^xsd:double");
								nodeResourceProps.put(FemOntology.Property.DataProperty.hasNumberOfUnusedBlock15Values, String.valueOf(2));
								if(isFirstNode) {
									block15ResourceProps.put(FemOntology.Property.ObjectProperty.hasFirstNode, 
											nodeResource);
									isFirstNode = false;
								}								
								//adding node
								addRDFService(builder, nodeResource, FemOntology.Class.nodeClass, nodeResourceProps);
							}
							//adding Block15
							addRDFService(builder, block15Resource, FEMSettingsPAK.Class.unvBlockClass, block15ResourceProps);
						}

						if(line.equals("757")) {
							String block757Resource = modelResource + "Block757";
							Map<String, String> block757ResourceProps = new HashMap<String, String>();
							//Block 757 props: hasUNVBlockIdPartOne
							block757ResourceProps.put(FEMSettingsPAK.Property.DataProperty.hasUNVBlockIdPartOne, 
									"757");							
							unvOutputFileResourceProps.put(FEMSettingsPAK.Property.ObjectProperty.hasUNVBlock, block757Resource);
							boolean isFirstNode = true;

							while((line = reader.readLine()) != null){
								line = line.trim();
								if(line.equals("-1"))
									break;
								//System.out.println(line);
								String[] split = line.split("\\s+");

								if(split.length == 10){
									String count = split[0].trim();
									String constraintColor = split[1].trim();
									String xdisplacementConstraint = split[2].trim();
									String ydisplacementConstraint = split[3].trim();
									String zdisplacementConstraint = split[4].trim();
									String pressureConstraint = split[5].trim();									
									//node
									String nodeResource = modelResource + "Node" + count;
									Map<String, String> nodeResourceProps = new HashMap<String, String>();								
									//node props: hasConstraintColour, hasX,Y,ZCoordinate		
									nodeResourceProps.put(FemOntology.Property.DataProperty.hasConstraintColour, constraintColor);
									nodeResourceProps.put(FemOntology.Property.DataProperty.hasXDisplacementConstraint, "\""+new Util().convertNoNumberToNumber(xdisplacementConstraint)+"\"^^xsd:double");
									nodeResourceProps.put(FemOntology.Property.DataProperty.hasYDisplacementConstraint, "\""+new Util().convertNoNumberToNumber(ydisplacementConstraint)+"\"^^xsd:double");
									nodeResourceProps.put(FemOntology.Property.DataProperty.hasZDisplacementConstraint, "\""+new Util().convertNoNumberToNumber(zdisplacementConstraint)+"\"^^xsd:double");
									nodeResourceProps.put(FemOntology.Property.DataProperty.hasPressureConstraint, pressureConstraint);
									nodeResourceProps.put(FemOntology.Property.DataProperty.hasNumberOfUnusedBlock757Values, String.valueOf(4));
									if(isFirstNode){
										block757ResourceProps.put(FemOntology.Property.ObjectProperty.hasFirstNode, 
												nodeResource);

										isFirstNode = false;
									}
									//adding node
									addRDFService(builder, nodeResource, FemOntology.Class.nodeClass, nodeResourceProps);
								}
							}
							//adding Block757
							addRDFService(builder, block757Resource, FEMSettingsPAK.Class.unvBlockClass, block757ResourceProps);					
						}

						if(line.equals("71")){
							String block71Resource = modelResource + "Block71";
							Map<String, String> block71ResourceProps = new HashMap<String, String>();
							//Block 71 props: hasUNVBlockIdPartOne
							block71ResourceProps.put(FEMSettingsPAK.Property.DataProperty.hasUNVBlockIdPartOne, 
									"71");							
							unvOutputFileResourceProps.put(FEMSettingsPAK.Property.ObjectProperty.hasUNVBlock, block71Resource);
							boolean isFirstElement = true;
							while((line = reader.readLine()) != null){
								line = line.trim();
								if(line.equals("-1"))
									break;
								//System.out.println(line);
								String[] split = line.split("\\s+");
								if(split.length == 7){
									String count = split[0].trim();
									//String constraintColor = split[1].trim();
									//String xdisplacementConstraint = split[2].trim();
									//String ydisplacementConstraint = split[3].trim();
									//String zdisplacementConstraint = split[4].trim();
									String noOfNodes = split[6].trim();		
									//element
									String elementResource = modelResource + "Element" + count;
									Map<String, String> elementResourceProps = new HashMap<String, String>();
									elementResourceProps.put(FemOntology.Property.DataProperty.hasNumberOfUnusedBlock71Values, String.valueOf(3));									
									String secondLine = reader.readLine();
									secondLine = secondLine.trim();
									String[] secondSplit = secondLine.split("\\s+");
									if(secondSplit.length == Integer.parseInt(noOfNodes)){
										String firstNode = secondSplit[0];
										String secondNode = secondSplit[1];
										String thirdNode = secondSplit[2];
										String fourthNode = secondSplit[3];
										String fifthNode = secondSplit[4];
										String sixthNode = secondSplit[5];
										String seventhNode = secondSplit[6];
										String eighthNode = secondSplit[7];											
										//node
										String nodeResourcePrefix = modelResource + "Node";
										elementResourceProps.put(FemOntology.Property.ObjectProperty.hasFirstNode,
												nodeResourcePrefix + firstNode);
										elementResourceProps.put(FemOntology.Property.ObjectProperty.hasSecondNode,
												nodeResourcePrefix + secondNode);
										elementResourceProps.put(FemOntology.Property.ObjectProperty.hasThirdNode,
												nodeResourcePrefix + thirdNode);
										elementResourceProps.put(FemOntology.Property.ObjectProperty.hasFourthNode,
												nodeResourcePrefix + fourthNode);
										elementResourceProps.put(FemOntology.Property.ObjectProperty.hasFifthNode,
												nodeResourcePrefix + fifthNode);
										elementResourceProps.put(FemOntology.Property.ObjectProperty.hasSixthNode,
												nodeResourcePrefix + sixthNode);
										elementResourceProps.put(FemOntology.Property.ObjectProperty.hasSeventhNode,
												nodeResourcePrefix + seventhNode);
										elementResourceProps.put(FemOntology.Property.ObjectProperty.hasEighthNode,
												nodeResourcePrefix + eighthNode);
										if(isFirstElement){
											block71ResourceProps.put(FemOntology.Property.ObjectProperty.hasFirstElement, elementResource);
											isFirstElement = false;
										}
										//adding element
										addRDFService(builder, elementResource, FemOntology.Class.elementClass, elementResourceProps);
									}

								}
							}
							//adding Block71
							addRDFService(builder, block71Resource, FEMSettingsPAK.Class.unvBlockClass, block71ResourceProps);					
						}

						if(line.equals("55")){	
							String block55Resource = modelResource + "Block55" + numOf55Blocks++;
							Map<String, String> block55ResourceProps = new HashMap<String, String>();
							String unvBlockSettingsResource = block55Resource + "UNVBlockSettings";
							Map<String, String> unvBlockSettingsResourceProps = new HashMap<String, String>();
							block55ResourceProps.put(FEMSettingsPAK.Property.ObjectProperty.hasUNVBlockSettings, 
									unvBlockSettingsResource);							
							unvOutputFileResourceProps.put(FEMSettingsPAK.Property.ObjectProperty.hasUNVBlock, block55Resource);
							//hasUNVBlockIdPartOne
							unvBlockSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.hasUNVBlockIdPartOne, 
									"55");						
							boolean foundTheStepNumberLine = false;

							while((line = reader.readLine()) != null){
								line = line.trim();
								if(line.equals("-1"))
									break;
								if(foundTheStepNumberLine) {
									String[] split = line.split("\\s+");
									String partIdTwo = split[3].trim();
									String noOfValuesPerNode = split[5].trim();
									unvBlockSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.hasUNVBlockIdPartTwo, 
											partIdTwo);	 								
									String parameter = modelResource;
									if(noOfValuesPerNode.equals("1")){
										if(partIdTwo.equals("21"))
											parameter = parameter + "NodalPotential";
										if(partIdTwo.equals("13"))
											parameter = parameter + "FluidPressureReal";
										if(partIdTwo.equals("14"))
											parameter = parameter + "FluidPressureImag";
										line = reader.readLine().trim();
										//String[] split2 = line.split("\\s+");
										line = reader.readLine().trim();
										while((line = reader.readLine()) != null){
											line = line.trim();	
											if(line.equals("-1"))
												break;

											//node
											//String nodeResource = modelResource + "Node" + line;										
											//Map<String, String> nodeResourceProps = new HashMap<String, String>();
											line = reader.readLine();
										}
									}
									else if(noOfValuesPerNode.equals("6")){
										if(partIdTwo.equals("9")){
											while((line = reader.readLine()) != null){
												line = line.trim();											
												if(line.equals("-1"))
													break;				
											}

										}
										if(partIdTwo.equals("8")){
											parameter = parameter + "NodalTranslation";
											//	if(partIdTwo.equals("9"))
											//		parameter = parameter + "NodalTranslation";
											line = reader.readLine().trim();
											line.split("\\s+");
											line = reader.readLine().trim();
											while((line = reader.readLine()) != null){
												line = line.trim();
												if(line.equals("-1"))
													break;											
												//node
												String nodeResource = modelResource + "Node" + line;										
												Map<String, String> nodeResourceProps = new HashMap<String, String>();
												String physicalParameterResource = parameter + line;
												Map<String, String> physicalParameterResourceProps = new HashMap<String, String>();
												line = reader.readLine().trim();
												String[] split2 = line.split("\\s+");

												nodeResourceProps.put(FemOntology.Property.ObjectProperty.holdsValueFor, physicalParameterResource);
												String vectorValueResource = physicalParameterResource + "VectorValue";
												Map<String, String> vectorValueResourceProps = new HashMap<String, String>();
												vectorValueResourceProps.put(SimulationOntology.Property.DataProperty.hasVectorXValueDatProp, "\""+new Util().convertNoNumberToNumber(split2[0])+"\"^^xsd:double");
												vectorValueResourceProps.put(SimulationOntology.Property.DataProperty.hasVectorYValueDatProp, "\""+new Util().convertNoNumberToNumber(split2[1])+"\"^^xsd:double");
												vectorValueResourceProps.put(SimulationOntology.Property.DataProperty.hasVectorZValueDatProp, "\""+new Util().convertNoNumberToNumber(split2[2])+"\"^^xsd:double");

												physicalParameterResourceProps.put(SimulationOntology.Property.ObjectProperty.hasVectorValueObjProp, vectorValueResource);
												if(partIdTwo.equals("8")){
													vectorValueResourceProps.put(SimulationOntology.Property.DataProperty.isReal, "true");
												}

												addRDFService(builder, vectorValueResource, SimulationOntology.Class.vectorValueClass, vectorValueResourceProps);
												addRDFService(builder, physicalParameterResource, FemOntology.Class.translationClass, physicalParameterResourceProps);
												addRDFService(builder, nodeResource, FemOntology.Class.nodeClass, nodeResourceProps);
											}

										}
									}
									else if(noOfValuesPerNode.equals("3")){
										if(partIdTwo.equals("11"))
											parameter = parameter + "NodalVelocities";
										line = reader.readLine().trim();
										line.split("\\s+");
										line = reader.readLine().trim();
										while((line = reader.readLine()) != null){
											line = line.trim();	
											if(line.equals("-1"))
												break;

											//node
											//String nodeResource = modelResource + "Node" + line;										
											//Map<String, String> nodeResourceProps = new HashMap<String, String>();
											line = reader.readLine();
										}
									}
								}
								if(line!=null){
									if(line.matches(".*:.*\\d+")){
										line = line.substring(line.indexOf(":"), line.length()).trim();
										//String stepNumber = line;
										foundTheStepNumberLine = true;
									}
								}
								if(line.equals("-1"))
									break;							
							}

							//adding Block55
							addRDFService(builder, block55Resource, FEMSettingsPAK.Class.unvBlockClass, block55ResourceProps);					
						}
						if(line.equals("57")){
							while((line = reader.readLine()) != null){
								line = line.trim();
								if(line.equals("-1"))
									break;
							}
						}
					}					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(),e);
		}
		addRDFService(builder, unvOutputFileResource, FEMSettingsPAK.Class.unvOutputFileClass, unvOutputFileResourceProps);
		//builder.append("\n } ");
		
		String fileName = "unv_output.ttl";
		simulationInstanceTO.setPreparedQuery(builder.toString());
		simulationInstanceTO.setFileName(fileName);
		
		InputStream stream = new ByteArrayInputStream(builder.toString().getBytes(StandardCharsets.UTF_8));
		DatAndUnvTTLTO datAndUnvTTLTO = new DatAndUnvTTLTO();
		datAndUnvTTLTO.setUnvFile(stream);
		datAndUnvTTLTO.setProjectName(simulationInstanceTO.getProjectName());
		datAndUnvTTLTO.setSimulationName(simulationInstanceTO.getSimulationName());
		datAndUnvTTLTO.setInstanceName(simulationInstanceTO.getInstanceName());
                System.err.println(" Filename: " + fileName);
		datAndUnvTTLDAOService.insert(datAndUnvTTLTO,fileName);
		return stream;
	}



	private Map<String, String> getPrefixMap() throws Exception{
		Pattern prefixPattern = Pattern.compile(prefixPatternString);
		Map<String, String> prefixMap = new HashMap<String, String>();
		BufferedReader reader = BasicFileTools.getBufferedReaderFile(prefixesFile);
		String line = null;
		try {
			while((line=reader.readLine())!=null){
				line = line.trim();
				Matcher matcher = prefixPattern.matcher(line);
				while(matcher.find()) {
					String prefix = matcher.group(1); 
					String value = matcher.group(2);
					prefixMap.put(prefix, value+"#");
					System.out.println(prefix + " " + value);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(),e);
		}
		return prefixMap;
	}

	public void addRDFService(StringBuilder builder, String resource, String type, Map<String, String> propsWithValues){
	//	addXSDPrefix(builder);
		builder.append("\n\n" + resource + "\n" + "a" + " " + type + ";\n\n");
		for(String property : propsWithValues.keySet()) {
			String value = propsWithValues.get(property);
			if(value.matches("[-+\\d]*\\."))
				value = value + "0";
			builder.append(property + "\t" + value + ";" + "\n\n");
		}
		builder.append(".\n\n");
	}

	private void addXSDPrefix(StringBuilder builder) {
		if(!builder.toString().contains(PREFIX_XSD_HTTP_WWW_W3_ORG_2001_XML_SCHEMA)){
			builder.append(PREFIX_XSD_HTTP_WWW_W3_ORG_2001_XML_SCHEMA+"\n");			
		}
	}

	private void addPrefixes(Map<String, String> prefixUriMap, StringBuilder builder){
		addXSDPrefix(builder);
		for(String prefix : prefixUriMap.keySet()){
			String uri = prefixUriMap.get(prefix);			
			builder.append(PREFIX +  prefix + ":" +  "<" + uri + ">  "  + "\n");			
		}
	}

	private void addPrefix(String prefix, String uri, StringBuilder builder){
		addXSDPrefix(builder);
		builder.append(PREFIX +  prefix + ":" +  "<" + uri + ">  "  + "\n");	
	}





	@Override
	public InputStream datToRDFService(SimulationInstanceTO simulationInstanceTO,InputStream simulationFile) throws Exception {
		
		StringBuilder builder = new StringBuilder();		
		PakCDatReaderLatest pakCReader = new PakCDatReaderLatest();
		String simulationFileAsStr = IOUtils.toString(simulationFile, "UTF-8"); 
		
		
		//TODO Remove it.
//		if(simulationFileAsStr!=null){
//			FileUtils.writeStringToFile(new File("C:\\SifemWindowsResourceFiles\\tmp\\dat.txt"), simulationFileAsStr);
//		}
		
		
		PakCData pakCData1 = pakCReader.read(simulationFileAsStr);
		System.out.println(pakCData1.card1.get(PakCData.Card1.TITLE.toString()));
		//addPrefixes(getPrefixMap(), builder);
		String prefixes = "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> . \n"
				+ "@prefix rdfs:<http://www.w3.org/2000/01/rdf-schema#> .  \n"
				+ "@prefix dao:<http://www.sifemontologies.com/ontologies/DataAnalysis.owl#> .  \n"
				+ "@prefix omlprop:<http://codata.jp/OML-Property.owl#> .  \n"
				+ "@prefix fem:<http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> .  \n"
				+ "@prefix xsd:<http://www.w3.org/2001/XMLSchema#> .  \n"
				+ "@prefix owl:<http://www.w3.org/2002/07/owl#> .  \n"
				+ "@prefix sim:<http://www.sifemontologies.com/ontologies/Simulation.owl#> .  \n"
				+ "@prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> .  \n"
				+ "@prefix pakFemSet:<http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> .  \n"
				+ "@prefix bw:<http://www.sifemontologies.com/ontologies/BoxModel#> . \n";
		//addPrefix(this.femModelNamePrefix, this.experimentNameUri, builder);	
		builder.append(prefixes);
		builder.append(" \n ");
		//model resource
		String modelResource = femModelNamePrefix +":" + femModelName;
		//Map<String, String> modelResourceProps = new HashMap<String, String>();

		//femSettingsPak resource
		String femSettingsPakResource = modelResource + "FemSettingsPAK";
		Map<String, String> femSettingsPakResourceProps = new HashMap<String, String>();

		//femSettingsPak props: heading		
		femSettingsPakResourceProps.put(FEMSettingsPAK.Property.DataProperty.hasHeadingDatProp, "\"" + pakCData1.card1.get(PakCData.Card1.TITLE.toString()) + "\"");
		//femSettingsPak props: format
		String format = pakCData1.card2.get(PakCData.Card2.INDFOR.toString()).trim();
		femSettingsPakResourceProps.put(FEMSettingsPAK.Property.DataProperty.INDFOR, format);		

		//SolverSettings resource
		String solverSettingsResource = modelResource + "SolverSettings";
		Map<String, String> solverSettingsResourceProps = new HashMap<String, String>();

		System.out.println(pakCData1);
		System.out.println(pakCData1.card3);
		System.out.println(PakCData.Card3.NDIN);
		System.out.println(PakCData.Card3.NDIN.toString());
		System.out.println(PakCData.Card3.NDIN.toString().trim());
		
		String ndin = pakCData1.card3.get(PakCData.Card3.NDIN.toString()).trim();		
		solverSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.NDIN, ndin);

		String nper = pakCData1.card3.get(PakCData.Card3.NPER.toString()).trim();		
		solverSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.NPER, nper);

		String print = pakCData1.card3.get(PakCData.Card3.PRINT.toString()).trim();		
		solverSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.PRINT, print);

		String inteb = pakCData1.card4.get(PakCData.Card4.INTEB.toString()).trim();
		solverSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.INTEB, inteb);

		String indsc = pakCData1.card4.get(PakCData.Card4.INDSC.toString()).trim();
		solverSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.INDSC, indsc);

		String epsta = pakCData1.card4.get(PakCData.Card4.EPSTA.toString()).trim();
		solverSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.EPSTA, epsta);

		String epstr = pakCData1.card4.get(PakCData.Card4.EPSTR.toString()).trim();
		solverSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.EPSTR, epstr);

		String njrap = pakCData1.card4.get(PakCData.Card4.NJRAP.toString()).trim();
		solverSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.NJRAP, njrap);

		String iform = pakCData1.card4.get(PakCData.Card4.IFORM.toString()).trim();
		solverSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.IFORM, iform);		

		String maxit = pakCData1.card4.get(PakCData.Card4.MAXIT.toString()).trim();
		solverSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.MAXIT, maxit);

		String isolver = pakCData1.card4.get(PakCData.Card4.ISOLVER.toString()).trim();
		solverSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.ISOLVER, isolver);

		String frequency = pakCData1.card4.get(PakCData.Card4.FREQUENCY.toString()).trim();		
		//Load Resource
		String loadResource = modelResource + "Load";
		Map<String, String> loadResourceProps = new HashMap<String, String>();

		//Frequency Resource
		String excitationFrequencyResource = modelResource + "ExcitationFrequency";
		Map<String, String> excitationFrequencyResourceProps = new HashMap<String, String>();

		//ExcitationFrequencyValue Resource
		String excitationFrequencyValueResource = modelResource + "ExcitationFrequencyValue";
		Map<String, String> excitationFrequencyValueResourceProps = new HashMap<String, String>();

		excitationFrequencyValueResourceProps.put(SimulationOntology.Property.DataProperty.
				hasScalarDataValueDatProp, frequency);
		addRDFService(builder, excitationFrequencyValueResource, SimulationOntology.Class.scalarValueClass, 
				excitationFrequencyValueResourceProps);

		excitationFrequencyResourceProps.put(SimulationOntology.Property.ObjectProperty.
				hasScalarValueObjProp, excitationFrequencyValueResource);		
		addRDFService(builder, excitationFrequencyResource, FemOntology.Class.frequencyClass, 
				excitationFrequencyResourceProps);

		loadResourceProps.put(FemOntology.Property.ObjectProperty.holdsValueFor, excitationFrequencyResource);		
		addRDFService(builder, loadResource, FemOntology.Class.loadClass, 
				loadResourceProps);

		String irest = pakCData1.card5.get(PakCData.Card5.IREST.toString()).trim();
		solverSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.IREST, irest);

		String ndti = pakCData1.card6.get(PakCData.Card6.NDTI.toString()).trim();
		solverSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.numberOfTimeSteps, ndti);

		String timei = pakCData1.card6.get(PakCData.Card6.TIMEI.toString()).trim();
		solverSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.timeStepDuration, timei);

                System.err.println("TIM" + timei);
		//Nodal Data
		for(String count : pakCData1.card7.keySet()) {
			Map<String, String> card7Map = pakCData1.card7.get(count);
			//node
			String nodeResource = modelResource + "Node" + count ;
			Map<String, String> nodeResourceProps = new HashMap<String, String>();	

			String n = card7Map.get(PakCData.Card7.N.toString()).trim();
			nodeResourceProps.put(FemOntology.Property.DataProperty.hasNodeID, n);
			String xCoordinate = card7Map.get(PakCData.Card7.CORD1NX.toString()).trim();
			xCoordinate = String.valueOf(round(Double.parseDouble(xCoordinate), roundOffDecimalPlaces));
			nodeResourceProps.put(FemOntology.Property.DataProperty.hasXCoordinate, "\""+new Util().convertNoNumberToNumber(xCoordinate)+"\"^^xsd:double");			
			String yCoordinate = card7Map.get(PakCData.Card7.CORD2NY.toString()).trim();
			yCoordinate = String.valueOf(round(Double.parseDouble(yCoordinate), roundOffDecimalPlaces));			
			nodeResourceProps.put(FemOntology.Property.DataProperty.hasYCoordinate, "\""+new Util().convertNoNumberToNumber(yCoordinate)+"\"^^xsd:double");		
			String zCoordinate = card7Map.get(PakCData.Card7.CORD3NZ.toString()).trim();
			zCoordinate = String.valueOf(round(Double.parseDouble(zCoordinate), roundOffDecimalPlaces));			
			nodeResourceProps.put(FemOntology.Property.DataProperty.hasZCoordinate, "\""+new Util().convertNoNumberToNumber(zCoordinate)+"\"^^xsd:double" );			

			//nodeSettings Resource
			String nodeSettingsResource = modelResource + "NodeSettings" + count;
			Map<String, String> nodeSettingsResourceProps = new HashMap<String, String>();	

			String id_n1 = card7Map.get(PakCData.Card7.I1.toString()).trim();		
			nodeSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.ID_N1, id_n1);

			String id_n2 = card7Map.get(PakCData.Card7.I2.toString()).trim();		
			nodeSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.ID_N2, id_n2);

			String id_n3 = card7Map.get(PakCData.Card7.I3.toString()).trim();		
			nodeSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.ID_N3, id_n3);

			String id_n4 = card7Map.get(PakCData.Card7.I4.toString()).trim();		
			nodeSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.ID_N4, id_n4);

			String id_n5 = card7Map.get(PakCData.Card7.I5.toString()).trim();		
			nodeSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.ID_N5, id_n5);

			String id_n6 = card7Map.get(PakCData.Card7.I6.toString()).trim();		
			nodeSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.ID_N6, id_n6);

			String id_n7 = card7Map.get(PakCData.Card7.I7.toString()).trim();		
			nodeSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.ID_N7, id_n7);

			String id_n8 = card7Map.get(PakCData.Card7.I8.toString()).trim();		
			nodeSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.ID_N8, id_n8);

			addRDFService(builder, nodeSettingsResource, FEMSettingsPAK.Class.nodeSettingsClass, nodeSettingsResourceProps);			

			nodeResourceProps.put(FEMSettingsPAK.Property.ObjectProperty.hasNodeSettings, nodeSettingsResource);			
			//adding nodeResource
			addRDFService(builder, nodeResource, FemOntology.Class.nodeClass, nodeResourceProps);
		}

		//SubDomainGroup Resource
		String subDomainGroupResource = modelResource + "SubDomainGroup" + "1";
		Map<String, String> subDomainGroupResourceProps = new HashMap<String, String>();

		String netip = pakCData1.card8.get(PakCData.Card8.NETIP.toString()).trim();
		subDomainGroupResourceProps.put(FemOntology.Property.DataProperty.hasSpatialDimension, 
				netip);

		addRDFService(builder, subDomainGroupResource, FemOntology.Class.subDomainGroupClass, subDomainGroupResourceProps);

		//ElementGroupSettings Resource 
		String elementGroupSettingsResource  = modelResource + "ElementGoupSettings" + "1";
		Map<String, String> elementGroupSettingsResourceProps = new HashMap<String, String>();

		String indax = pakCData1.card8.get(PakCData.Card8.INDAX.toString()).trim();
		elementGroupSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.INDAX, indax);

		String iatyp  = pakCData1.card8.get(PakCData.Card8.IATYP.toString()).trim();
		elementGroupSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.IAYTP, iatyp);

		String nmodm  = pakCData1.card8.get(PakCData.Card8.NMODM.toString()).trim();
		elementGroupSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.NMODM, nmodm);

		String ipn = pakCData1.card8.get(PakCData.Card8.IPN.toString()).trim();
		elementGroupSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.IPN, ipn);

		//adding elementGroupSettingsResource
		addRDFService(builder, elementGroupSettingsResource, FEMSettingsPAK.Class.elementGroupSettingsClass, elementGroupSettingsResourceProps);

		//Mesh Resource 
		String meshResource  = modelResource + "Mesh";
		Map<String, String> meshResourceProps = new HashMap<String, String>();

		String np2dmx = pakCData1.card8_1_a.get(PakCData.Card8_1_a.NP2DMX.toString()).trim();
		meshResourceProps.put(FemOntology.Property.DataProperty.hasMaximumNumberOfNodesPerElement, np2dmx);

		String iswell = pakCData1.card8_1_a.get(PakCData.Card8_1_a.ISWELL.toString()).trim();

		String meshSettingsResource = modelResource + "MeshSettings";
		Map<String, String> meshSettingsResourceProps = new HashMap<String, String>();

		meshSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.ISWELL, iswell);

		//adding meshSettingsResource
		addRDFService(builder, meshSettingsResource, FEMSettingsPAK.Class.meshSettingsClass, meshSettingsResourceProps);
		//adding meshResource
		addRDFService(builder, meshResource, FemOntology.Class.meshClass, meshResourceProps);

		String youngsModulusDimensionResource = modelResource + "YoungsModulusDimensionResource";		
		Map<String, String> youngsModulusDimensionResourceProps = new HashMap<String, String>();			
		youngsModulusDimensionResourceProps.put(FemOntology.Property.DataProperty.hasTimeDimension, "-2");
		youngsModulusDimensionResourceProps.put(FemOntology.Property.DataProperty.hasMassDimension, "-1");
		youngsModulusDimensionResourceProps.put(FemOntology.Property.DataProperty.hasLengthDimension, "-1");

		addRDFService(builder, youngsModulusDimensionResource, FemOntology.Class.dimensionClass, youngsModulusDimensionResourceProps);

		String dampingCoefficientDimensionResource = modelResource + "DampingCoefficientDimensionResource";		
		Map<String, String> dampingCoefficientDimensionResourceProps = new HashMap<String, String>();			
		dampingCoefficientDimensionResourceProps.put(FemOntology.Property.DataProperty.hasTimeDimension, "-1");
		dampingCoefficientDimensionResourceProps.put(FemOntology.Property.DataProperty.hasMassDimension, "-1");

		addRDFService(builder, dampingCoefficientDimensionResource, FemOntology.Class.dimensionClass, dampingCoefficientDimensionResourceProps);

		//Element Data
		for(String count : pakCData1.card8_1_b.keySet()) {
			Map<String, String> card8_1_bMap = pakCData1.card8_1_b.get(count);
			//element resource
			String elementResource = modelResource + "Element" + count ;
			Map<String, String> elementResourceProps = new HashMap<String, String>();	

			String nn = card8_1_bMap.get(PakCData.Card8_1_b.NN.toString()).trim();
			elementResourceProps.put(FemOntology.Property.DataProperty.hasElementNumber, nn);

			String node1 = card8_1_bMap.get(PakCData.Card8_1_b.NEL1.toString()).trim();
			String nodeResource1 = modelResource + "Node" + node1 ;			
			Map<String, String> node1ResourceProps = new HashMap<String, String>();
			node1ResourceProps.put(FemOntology.Property.ObjectProperty.isNodeOf, elementResource);
			addRDFService(builder, nodeResource1, FemOntology.Class.nodeClass, node1ResourceProps); 
			elementResourceProps.put(FemOntology.Property.ObjectProperty.hasNode, nodeResource1);

			String node2 = card8_1_bMap.get(PakCData.Card8_1_b.NEL2.toString()).trim();
			String nodeResource2 = modelResource + "Node" + node2;
			Map<String, String> node2ResourceProps = new HashMap<String, String>();
			node2ResourceProps.put(FemOntology.Property.ObjectProperty.isNodeOf, elementResource);
			addRDFService(builder, nodeResource2, FemOntology.Class.nodeClass, node2ResourceProps); 
			elementResourceProps.put(FemOntology.Property.ObjectProperty.hasNode, nodeResource2);

			String node3 = card8_1_bMap.get(PakCData.Card8_1_b.NEL3.toString()).trim();
			String nodeResource3 = modelResource + "Node" + node3;
			Map<String, String> node3ResourceProps = new HashMap<String, String>();
			node3ResourceProps.put(FemOntology.Property.ObjectProperty.isNodeOf, elementResource);
			addRDFService(builder, nodeResource3, FemOntology.Class.nodeClass, node3ResourceProps); 
			elementResourceProps.put(FemOntology.Property.ObjectProperty.hasNode, nodeResource3);

			String node4 = card8_1_bMap.get(PakCData.Card8_1_b.NEL4.toString()).trim();
			String nodeResource4 = modelResource + "Node" + node4;
			Map<String, String> node4ResourceProps = new HashMap<String, String>();
			node4ResourceProps.put(FemOntology.Property.ObjectProperty.isNodeOf, elementResource);
			addRDFService(builder, nodeResource4, FemOntology.Class.nodeClass, node4ResourceProps); 
			elementResourceProps.put(FemOntology.Property.ObjectProperty.hasNode, nodeResource4);

			String node5 = card8_1_bMap.get(PakCData.Card8_1_b.NEL5.toString()).trim();
			String nodeResource5 = modelResource + "Node" + node5;
			Map<String, String> node5ResourceProps = new HashMap<String, String>();
			node5ResourceProps.put(FemOntology.Property.ObjectProperty.isNodeOf, elementResource);
			addRDFService(builder, nodeResource5, FemOntology.Class.nodeClass, node5ResourceProps); 
			elementResourceProps.put(FemOntology.Property.ObjectProperty.hasNode, nodeResource5);

			String node6 = card8_1_bMap.get(PakCData.Card8_1_b.NEL6.toString()).trim();
			String nodeResource6 = modelResource + "Node" + node6;
			Map<String, String> node6ResourceProps = new HashMap<String, String>();
			node6ResourceProps.put(FemOntology.Property.ObjectProperty.isNodeOf, elementResource);
			addRDFService(builder, nodeResource6, FemOntology.Class.nodeClass, node6ResourceProps); 
			elementResourceProps.put(FemOntology.Property.ObjectProperty.hasNode, nodeResource6);

			String node7 = card8_1_bMap.get(PakCData.Card8_1_b.NEL7.toString()).trim();
			String nodeResource7 = modelResource + "Node" + node7;
			Map<String, String> node7ResourceProps = new HashMap<String, String>();
			node7ResourceProps.put(FemOntology.Property.ObjectProperty.isNodeOf, elementResource);
			addRDFService(builder, nodeResource7, FemOntology.Class.nodeClass, node7ResourceProps); 
			elementResourceProps.put(FemOntology.Property.ObjectProperty.hasNode, nodeResource7);

			String node8 = card8_1_bMap.get(PakCData.Card8_1_b.NEL8.toString()).trim();
			String nodeResource8 = modelResource + "Node" + node8;
			Map<String, String> node8ResourceProps = new HashMap<String, String>();
			node8ResourceProps.put(FemOntology.Property.ObjectProperty.isNodeOf, elementResource);
			addRDFService(builder, nodeResource8, FemOntology.Class.nodeClass, node8ResourceProps);
			elementResourceProps.put(FemOntology.Property.ObjectProperty.hasNode, nodeResource8);

			String youngsModulusValue = card8_1_bMap.get(PakCData.Card8_1_b.YoungsModulus.toString()).trim();			
			String youngsModulusResource = elementResource + "YoungsModulus";
			Map<String, String> youngsModulusResourceProps = new HashMap<String, String>();
			youngsModulusResourceProps.put(FemOntology.Property.ObjectProperty.hasDimensionObjProp, youngsModulusDimensionResource);
			youngsModulusResourceProps.put(FemOntology.Property.DataProperty.hasMaterialPropertyValue, youngsModulusValue);
			elementResourceProps.put(FemOntology.Property.ObjectProperty.hasMaterialProperty, youngsModulusResource);

			addRDFService(builder, youngsModulusResource, FemOntology.Class.youngsModulusClass, youngsModulusResourceProps);

			String dampingCoefficientValue = card8_1_bMap.get(PakCData.Card8_1_b.DampingCoefficient.toString()).trim();			
			String dampingCoefficientResource = elementResource + "DampingCoefficient";
			Map<String, String> dampingCoefficientResourceProps = new HashMap<String, String>();
			dampingCoefficientResourceProps.put(FemOntology.Property.ObjectProperty.hasDimensionObjProp, dampingCoefficientDimensionResource);
			dampingCoefficientResourceProps.put(FemOntology.Property.DataProperty.hasMaterialPropertyValue, dampingCoefficientValue);
			elementResourceProps.put(FemOntology.Property.ObjectProperty.hasMaterialProperty, dampingCoefficientResource);

			addRDFService(builder, dampingCoefficientResource, FemOntology.Class.dampingCoefficientClass, dampingCoefficientResourceProps);

			String materialId = card8_1_bMap.get(PakCData.Card8_1_b.MaterialID.toString()).trim();			
			//elementGroup resource
			String elementGroupResource = modelResource + "ElementGroup" + materialId;
			Map<String, String> elementGroupResourceProps = new HashMap<String, String>();

			elementResourceProps.put(FemOntology.Property.ObjectProperty.makesUp, elementGroupResource);

			//material resource
			String materialResource = modelResource + "Material" + materialId;// count; //materialId;
			Map<String, String> materialResourceProps = new HashMap<String, String>();

			materialResourceProps.put(FemOntology.Property.DataProperty.hasMaterialNumber, materialId);

			elementGroupResourceProps.put(FemOntology.Property.ObjectProperty.hasMaterial, materialResource);

			//adding materialResource
			addRDFService(builder, materialResource, FemOntology.Class.materialClass, materialResourceProps);

			//adding elementGroupResource
			addRDFService(builder, elementGroupResource, FemOntology.Class.elementGroupClass, elementGroupResourceProps);

			//adding elementResource
			addRDFService(builder, elementResource, FemOntology.Class.elementClass, elementResourceProps);					
		}	

		String p0 = pakCData1.card10.get(PakCData.Card10.p0.toString());	
		String qx0 = pakCData1.card10.get(PakCData.Card10.Qx0.toString());
		String qy0 = pakCData1.card10.get(PakCData.Card10.Qy0.toString());
		String qz0 = pakCData1.card10.get(PakCData.Card10.Qz0.toString());

		String initialConditionResource = modelResource + "InitialConditionResource";
		Map<String, String> initialConditionResourceProps = new HashMap<String, String>();
		initialConditionResourceProps.put(FemOntology.Property.DataProperty.isUniform, "true");	

		String initialVelocityResource = modelResource + "InitialVelocity";
		Map<String,String> initialVelocityResourceProps = new HashMap<String, String>();
		String initialVelocityValueResource = modelResource + "InitialVelocityValue";
		Map<String,String> initialVelocityValueResourceProps = new HashMap<String, String>();
		initialVelocityValueResourceProps.put(SimulationOntology.Property.DataProperty.hasVectorXValueDatProp, "\""+new Util().convertNoNumberToNumber(qx0)+"\"^^xsd:double");
		initialVelocityValueResourceProps.put(SimulationOntology.Property.DataProperty.hasVectorYValueDatProp, "\""+new Util().convertNoNumberToNumber(qy0)+"\"^^xsd:double");
		initialVelocityValueResourceProps.put(SimulationOntology.Property.DataProperty.hasVectorZValueDatProp, "\""+new Util().convertNoNumberToNumber(qz0)+"\"^^xsd:double");
		initialVelocityValueResourceProps.put(SimulationOntology.Property.DataProperty.hasTimeStamp, "0");

		initialVelocityResourceProps.put(SimulationOntology.Property.ObjectProperty.hasVectorValueObjProp, initialVelocityValueResource);

		addRDFService(builder, initialVelocityResource, FemOntology.Class.velocityClass, initialVelocityResourceProps);		
		addRDFService(builder, initialVelocityValueResource, SimulationOntology.Class.vectorValueClass, 
				initialVelocityValueResourceProps);

		String initialPressureResource = modelResource + "InitialPressure";
		Map<String,String> initialPressureResourceProps = new HashMap<String, String>();
		String initialPressureValueResource = modelResource + "InitialPressureValue";
		Map<String,String> initialPressureValueResourceProps = new HashMap<String, String>();
		initialPressureValueResourceProps.put(SimulationOntology.Property.DataProperty.hasScalarDataValueDatProp, p0);
		initialPressureValueResourceProps.put(SimulationOntology.Property.DataProperty.hasTimeStamp, "0");

		addRDFService(builder, initialPressureResource, FemOntology.Class.pressureClass, initialPressureResourceProps);		
		addRDFService(builder, initialPressureValueResource, SimulationOntology.Class.scalarValueClass, 
				initialPressureValueResourceProps);				

		addRDFService(builder, initialConditionResource, FemOntology.Class.initialCondition, initialConditionResourceProps);		

		String maxsil = pakCData1.card11.get(PakCData.Card11.MAXSIL.toString());
		String indjp = pakCData1.card11.get(PakCData.Card11.INDPJ.toString());

		String boundarySettingsResource = modelResource + "BoundarySettings";
		Map<String, String> boundarySettingsResourceProps = new HashMap<String, String>();
		boundarySettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.MAXSIL, maxsil);
		boundarySettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.INDJP, indjp);
                
                System.err.println(" ---- " + boundarySettingsResourceProps);                

		addRDFService(builder, boundarySettingsResource, FEMSettingsPAK.Class.boundarySettingsClass, boundarySettingsResourceProps);

		for(String count : pakCData1.card11_second.keySet()){
			Map<String, String> card11_secondMap = pakCData1.card11_second.get(count);
			String element = card11_secondMap.get(PakCData.Card11_second.Element.toString());
			String node1 = card11_secondMap.get(PakCData.Card11_second.Node1.toString());
			String node2 = card11_secondMap.get(PakCData.Card11_second.Node2.toString());
			String node3 = card11_secondMap.get(PakCData.Card11_second.Node3.toString());
			String node4 = card11_secondMap.get(PakCData.Card11_second.Node4.toString());
			String boundaryResource = modelResource + element + "Boundary";
			String node1Resource = modelResource + "Node" + node1;
			String node2Resource = modelResource + "Node" + node2;
			String node3Resource = modelResource + "Node" + node3;
			String node4Resource = modelResource + "Node" + node4;

			Map<String, String> boundaryResourceProps = new HashMap<String, String>();			
			boundaryResourceProps.put(FemOntology.Property.ObjectProperty.hasBoundaryNode, node1Resource);
			boundaryResourceProps.put(FemOntology.Property.ObjectProperty.hasBoundaryNode, node2Resource);
			boundaryResourceProps.put(FemOntology.Property.ObjectProperty.hasBoundaryNode, node3Resource);
			boundaryResourceProps.put(FemOntology.Property.ObjectProperty.hasBoundaryNode, node4Resource);
			boundaryResourceProps.put(FEMSettingsPAK.Property.ObjectProperty.hasBoundarySettings, boundarySettingsResource);

			addRDFService(builder, boundaryResource, FemOntology.Class.boundaryClass, boundaryResourceProps);		
		}

		for(String count : pakCData1.card12_1_a_materialCommon.keySet()){
			Map<String, String> map = pakCData1.card12_1_a_materialCommon.get(count);
			String mattype = map.get(PakCData.Card12_1_a_materialCommon.Mattype.toString());
			//material resource
			String materialResource = modelResource + "Material" + count;
			Map<String, String> materialResourceProps = new HashMap<String, String>();			
			String materialSettingsResource = materialResource + "Settings";
			Map<String, String> materialSettingsResourceProps = new HashMap<String, String>();			
			materialResourceProps.put(FEMSettingsPAK.Property.ObjectProperty.hasMaterialSettings, materialSettingsResource);
			materialSettingsResourceProps.put(FEMSettingsPAK.Property.DataProperty.MATTYPE, mattype);		
			addRDFService(builder, materialSettingsResource, FEMSettingsPAK.Class.materialSettingsClass, materialSettingsResourceProps);
			addRDFService(builder, materialResource, FemOntology.Class.materialClass, materialResourceProps);			
		}		

		//adding femSettingsPakResource
		addRDFService(builder, femSettingsPakResource, FEMSettingsPAK.Class.finiteElementModelSettingsClass, femSettingsPakResourceProps);
		//adding solverSettingsPakResource
		addRDFService(builder, solverSettingsResource, FEMSettingsPAK.Class.solverSettingsClass, solverSettingsResourceProps);		
		//builder.append("\n } ");
		
		simulationInstanceTO.setPreparedQuery(builder.toString());
		String fileName = "dat_input.ttl";
		simulationInstanceTO.setFileName(fileName);
		InputStream stream = new ByteArrayInputStream(builder.toString().getBytes(StandardCharsets.UTF_8));
		
                //System.out.println(builder.toString());
		DatAndUnvTTLTO datAndUnvTTLTO = new DatAndUnvTTLTO();
		datAndUnvTTLTO.setDatFile(stream);
		datAndUnvTTLTO.setProjectName(simulationInstanceTO.getProjectName());
		datAndUnvTTLTO.setSimulationName(simulationInstanceTO.getSimulationName());
		datAndUnvTTLTO.setInstanceName(simulationInstanceTO.getInstanceName());
                System.err.println(" Filename: " + fileName);
		datAndUnvTTLDAOService.insert(datAndUnvTTLTO,fileName);
		return stream;
	}


	public String getPrefixesFile() {
		return prefixesFile;
	}

	public void setPrefixesFile(String prefixesFile) {
		this.prefixesFile = prefixesFile;
	}

	public String getFemModelName() {
		return femModelName;
	}

	public void setFemModelName(String femModelName) {
		this.femModelName = femModelName;
	}

	public String getFemModelNamePrefix() {
		return femModelNamePrefix;
	}

	public void setFemModelNamePrefix(String femModelNamePrefix) {
		this.femModelNamePrefix = femModelNamePrefix;
	}

        public static void main(String[] args) throws IOException
        {
            SimulationInstanceTO sim = new SimulationInstanceTO();
            PakSparulRDFMapper r = new PakSparulRDFMapper("testA", "testB");
            
            InputStream is = new FileInputStream("/home/panos/Dropbox/Pak.dat");
            try {
                r.datToRDFService(sim, is);
            } catch (Exception ex) {
                Logger.getLogger(PakSparulRDFMapper.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
	
	//TODO Create Test case
//	public static void main(String[] args) throws IOException {
//		File simulationsFile = new File("C:/SifemWindowsResourceFiles/workspace/projectOne/simulationNumber_0/output");
//	
//		for(File file:simulationsFile.listFiles()){
//			
////			if(!StringUtils.startsWithIgnoreCase(simulationFile.getName(), "simulationNumber_")){
////				continue;
////			}
//			
//			System.out.println(file.getCanonicalPath());
//		}
//	}


}


