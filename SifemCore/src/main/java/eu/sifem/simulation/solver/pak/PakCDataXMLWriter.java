package eu.sifem.simulation.solver.pak;


public class PakCDataXMLWriter {

	
	//TODO Create a test case
//	public static void main(String[] args) {
//		PakCDatReader pakCReader = new PakCDatReader();
//		String pakCfilePath = "src/main/resources/PAK/caseDir/PAKC/Pak.dat";
//		PakCData pakCData1 = pakCReader.read(pakCfilePath);
//		StringBuffer buffer = new StringBuffer();
//		StringBuffer meshBuffer = new StringBuffer();
//		Map<String, String> descriptorMap = new HashMap<String, String>();
//		descriptorMap.put("I1", "Translation Direction=\"x\"");
//		descriptorMap.put("I2", "Translation Direction=\"y\"");
//		descriptorMap.put("I3", "Translation Direction=\"z\"");
//		descriptorMap.put("I4", "Pressure");
//		descriptorMap.put("I5", "Velocity Direction=\"x\"");
//		descriptorMap.put("I6", "Velocity Direction=\"y\"");
//		descriptorMap.put("I7", "Velocity Direction=\"z\"");
//		descriptorMap.put("I8", "Potential ");
//		descriptorMap.put("I9", "Rotation Direction=\"x\"");
//		descriptorMap.put("I10", "Rotation Direction=\"y\"");
//		descriptorMap.put("I11", "Rotation Direction=\"z\"");
//
//		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
//		meshBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
//		meshBuffer.append("<MeshData>\n");
//
//		buffer.append("<FemInputDat>\n");
//
//		buffer.append("\n<" + "HeadingCard" + ">");
//		for(String key : pakCData1.card1.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card1.get(key));
//			buffer.append("</" + key + ">\n");			
//		}		
//		buffer.append("</" + "HeadingCard" + ">\n");			
//
//
//		buffer.append("\n<" + "InputDataFormatCard" + ">");		
//		for(String key : pakCData1.card2.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card2.get(key));
//			buffer.append("</" + key + ">\n");			
//		}		
//		buffer.append("</" + "InputDataFormatCard" + ">\n");			
//
//
//		buffer.append("\n<" + "BasicDataCard" + ">");		
//		for(String key : pakCData1.card3.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card3.get(key));
//			buffer.append("</" + key + ">\n");			
//		}		
//
//		buffer.append("<MESHFILE> file:///C:/files/projects/sifm/DatAndMesh/pakDatWithoutMesh.xml"
//				+ "</MESHFILE>");
//		buffer.append("</" + "BasicDataCard" + ">\n");			
//
//
//		buffer.append("\n<" + "ProblemBasicDataCard" + ">");		
//		for(String key : pakCData1.card4.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card4.get(key));
//			buffer.append("</" + key + ">\n");			
//		}
//		buffer.append("</" + "ProblemBasicDataCard" + ">\n");			
//
//		buffer.append("\n<" + "RestartDataCard" + ">");	
//		for(String key : pakCData1.card5.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card5.get(key));
//			buffer.append("</" + key + ">\n");			
//		}
//		buffer.append("</" + "RestartDataCard" + ">\n");			
//
//		buffer.append("\n<" + "TimeStepsDataCard" + ">");		
//		for(String key : pakCData1.card6.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card6.get(key));
//			buffer.append("</" + key + ">\n");			
//		}		
//		buffer.append("</" + "TimeStepsDataCard" + ">\n");			
//
//		int max = 10;
//		buffer.append("\n<" + "NodalPointDataCard" + ">");
//		meshBuffer.append("\n<" + "Nodes" + ">");		
//
//
//
//		for(String count : pakCData1.card7.keySet()){
//			if(Integer.parseInt(count) < max){
//				Map<String, String> map = pakCData1.card7.get(count);
//				buffer.append("\n<" + "Node" + ">");			
//				meshBuffer.append("\n<" + "Node" + ">");
//				for(String key : map.keySet()){			
//					if(key.equalsIgnoreCase(PakCData.Card7.CORD1NX.toString())
//							|| key.equalsIgnoreCase(PakCData.Card7.CORD2NY.toString()) 
//							|| key.equalsIgnoreCase(PakCData.Card7.CORD3NZ.toString()	)){
//						meshBuffer.append("\n<" + key + ">");
//						meshBuffer.append(map.get(key));
//						meshBuffer.append("</" + key + ">\n");			
//					} else {
//						String tag = null;
//						if(descriptorMap.containsKey(key)){
//							tag = descriptorMap.get(key);
//							tag = tag + " " + "Constrained=\""  + map.get(key) + "\"";
//							buffer.append("\n<" + tag + "/>\n");						
//						}
//					}
//					if(key.equalsIgnoreCase(PakCData.Card7.N.toString())){
//						meshBuffer.append("\n<" + key + ">");
//						meshBuffer.append(map.get(key));
//						meshBuffer.append("</" + key + ">\n");					
//					}
//				}
//				buffer.append("</" + "Node" + ">\n");
//				meshBuffer.append("</" + "Node" + ">\n");
//
//			}
//		}
//		buffer.append("</" + "NodalPointDataCard" + ">\n");
//		meshBuffer.append("</" + "Nodes" + ">\n");		
//
//
//		buffer.append("\n<" + "ElementGroupDataCard" + ">");		
//		for(String key : pakCData1.card8.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card8.get(key));
//			buffer.append("</" + key + ">\n");			
//		}		
//		buffer.append("</" + "ElementGroupDataCard" + ">\n");		
//
//		buffer.append("\n<" + "ElementsCommonDataCard" + ">");		
//		for(String key : pakCData1.card8_1_a.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card8_1_a.get(key));
//			buffer.append("</" + key + ">\n");			
//		}
//		buffer.append("</" + "ElementsCommonDataCard" + ">\n");		
//
//
//		max = 10;
//		buffer.append("\n<" + "ElementNodalDataCard" + ">");
//		meshBuffer.append("\n<" + "Elements" + ">");				
//
//		for(String count : pakCData1.card8_1_b.keySet()){
//			if(Integer.parseInt(count) < max){
//				Map<String, String> map = pakCData1.card8_1_b.get(count);
//				buffer.append("\n<" + "Element" + ">");
//				meshBuffer.append("\n<" + "Element" + ">");				
//				for(String key : map.keySet()){				
//					if(!key.equalsIgnoreCase(PakCData.Card8_1_b.MaterialID.toString())
//							&& !key.equalsIgnoreCase(PakCData.Card8_1_b.DampingCoefficient.toString()) 
//							&& !key.equalsIgnoreCase(PakCData.Card8_1_b.YoungsModulus.toString())){
//						meshBuffer.append("\n<" + key + ">");
//						meshBuffer.append(map.get(key));
//						meshBuffer.append("</" + key + ">\n");			
//					} else {					
//						buffer.append("\n<" + key + ">");
//						buffer.append(map.get(key));
//						buffer.append("</" + key + ">\n");
//					}
//					if(key.equalsIgnoreCase(PakCData.Card8_1_b.NN.toString())){
//						buffer.append("\n<" + key + ">");
//						buffer.append(map.get(key));
//						buffer.append("</" + key + ">\n");					
//					}					
//				}
//				meshBuffer.append("</" + "Element" + ">\n");				
//				buffer.append("</" + "Element" + ">\n");
//			}
//		}		
//		buffer.append("</" + "ElementNodalDataCard" + ">\n");		
//		meshBuffer.append("</" + "Elements" + ">\n");		
//
//
//		buffer.append("\n<" + "PrescribedValuesNumberCard" + ">");		
//		for(String key : pakCData1.card9.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card9.get(key));
//			buffer.append("</" + key + ">\n");			
//		}
//		buffer.append("</" + "PrescribedValuesNumberCard" + ">\n");		
//
//		buffer.append("\n<" + "InitialValuesCard" + ">");		
//		for(String key : pakCData1.card10.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card10.get(key));
//			buffer.append("</" + key + ">\n");			
//		}
//		buffer.append("</" + "InitialValuesCard" + ">\n");		
//
//		buffer.append("\n<" + "SurfaceBoundaryConditionsDataCard" + ">");		
//		for(String key : pakCData1.card11.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card11.get(key));
//			buffer.append("</" + key + ">\n");			
//		}	
//		max = 10;		
//		for(String count : pakCData1.card11_second.keySet()){
//			//System.out.println("********** " + pakCData1.card11_second.size());
//			if(Integer.parseInt(count) < max){
//				Map<String, String> map = pakCData1.card11_second.get(count);
//				buffer.append("\n<" + "ElementSurface" + ">");			
//				for(String key : map.keySet()){				
//					buffer.append("\n<" + key + ">");
//					buffer.append(map.get(key));
//					buffer.append("</" + key + ">\n");				
//				}
//				buffer.append("</" + "ElementSurface" + ">\n");
//			}
//		}		
//		buffer.append("</" + "SurfaceBoundaryConditionsDataCard" + ">\n");	
//
//		buffer.append("\n<" + "MaterialsDataCard" + ">");		
//		for(String key : pakCData1.card12_1_number.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card12_1_number.get(key));
//			buffer.append("</" + key + ">\n");			
//		}		
//		buffer.append("\n<" + "Material1" + ">");
//		Map<String, String> materialMap1 = pakCData1.card12_1_a_materialCommon.get("1");
//		for(String key : materialMap1.keySet()){				
//			buffer.append("\n<" + key + ">");
//			buffer.append(materialMap1.get(key));
//			buffer.append("</" + key + ">\n");				
//		}
//		for(String key : pakCData1.card12_1_a_materialType1_1.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card12_1_a_materialType1_1.get(key));
//			buffer.append("</" + key + ">\n");			
//		}
//		for(String key : pakCData1.card12_1_a_materialType1_2.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card12_1_a_materialType1_2.get(key));
//			buffer.append("</" + key + ">\n");			
//		}
//		buffer.append("</" + "Material1" + ">\n");				
//
//		buffer.append("\n<" + "Material2" + ">");
//		Map<String, String> materialMap2 = pakCData1.card12_1_a_materialCommon.get("2");
//		for(String key : materialMap2.keySet()){				
//			buffer.append("\n<" + key + ">");
//			buffer.append(materialMap2.get(key));
//			buffer.append("</" + key + ">\n");				
//		}	
//		for(String key : pakCData1.card12_1_a_materialType2_1.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card12_1_a_materialType2_1.get(key));
//			buffer.append("</" + key + ">\n");			
//		}
//		for(String key : pakCData1.card12_1_a_materialType2_2.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card12_1_a_materialType2_2.get(key));
//			buffer.append("</" + key + ">\n");			
//		}	
//		buffer.append("</" + "Material2" + ">\n");	
//		buffer.append("</" + "MaterialsDataCard" + ">\n");
//
//		buffer.append("\n<" + "TimeFunctionsDataCard" + ">");		
//		for(String key : pakCData1.card13.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card13.get(key));
//			buffer.append("</" + key + ">\n");			
//		}
//		for(String key : pakCData1.card13_1_a.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card13_1_a.get(key));
//			buffer.append("</" + key + ">\n");			
//		}
//		buffer.append("\n<" + "ArgumentFunctionValues" + ">");
//		for(String count : pakCData1.card13_1_b.keySet()){
//			Map<String, String> map = pakCData1.card13_1_b.get(count);
//			buffer.append("\n<" + "ArgFuncValue" + count  + ">");			
//			for(String key : map.keySet()){				
//				buffer.append("\n<" + key + ">");
//				buffer.append(map.get(key));
//				buffer.append("</" + key + ">\n");				
//			}
//			buffer.append("</" + "ArgFuncValue" + count   + ">\n");
//		}		
//		buffer.append("</" + "ArgumentFunctionValues" + ">\n");
//		buffer.append("</" + "TimeFunctionsDataCard" + ">\n");
//
//		
//		buffer.append("\n<" + "ForceAndCurrentPrescribedValuesCard" + ">");		
//		for(String key : pakCData1.card14.keySet()){
//			buffer.append("\n<" + key + ">");
//			buffer.append(pakCData1.card14.get(key));
//			buffer.append("</" + key + ">\n");			
//		}
//		buffer.append("\n<" + "NodalValues" + ">");
//		for(String count : pakCData1.card14_1.keySet()){
//			Map<String, String> map = pakCData1.card14_1.get(count);
//			buffer.append("\n<" + "NodeValue" + count  + ">");			
//			for(String key : map.keySet()){				
//				buffer.append("\n<" + key + ">");
//				buffer.append(map.get(key));
//				buffer.append("</" + key + ">\n");				
//			}
//			buffer.append("</" + "NodeValue" + count   + ">\n");
//		}		
//		buffer.append("</" + "NodalValues" + ">\n");			
//		buffer.append("</" + "ForceAndCurrentPrescribedValuesCard" + ">\n");		
//			
//		buffer.append("</FemInputDat>\n");		
//
//		meshBuffer.append("</MeshData>\n");		
//
//		BasicFileTools.writeFile("src/main/resources/pakDat_1.xml", buffer.toString());
//		BasicFileTools.writeFile("src/main/resources/pakDatMesh.xml", meshBuffer.toString());
//	}

}
