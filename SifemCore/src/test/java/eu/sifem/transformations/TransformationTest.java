package eu.sifem.transformations;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hp.hpl.jena.rdf.model.RDFNode;

import eu.sifem.dao.jena.JenaModel;
import eu.sifem.model.to.TransformationTO;
import eu.sifem.service.IJenaModelService;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.ITransformationService;
import eu.sifem.service.ITurtleFormatRDFWriterService;
import eu.sifem.utils.CommandLineTools;
import eu.sifem.utils.Util;

@Test(groups= { "default" })
@ContextConfiguration(locations = { "classpath:SifemCore-applicationContext.xml" })
public class TransformationTest extends AbstractTestNGSpringContextTests{

	@Autowired
	private ITransformationService transformationService;

	@Autowired
	private IJenaModelService jenaModel;

	@Autowired
	private IResourceInjectionService resourceInjectionService;

	@Autowired
	private ITurtleFormatRDFWriterService turtleFormatRDFWriter;

	@Test(groups= { "default" })
	public void testServiceIsNotNull() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		Assert.assertNotNull(transformationService);
		Assert.assertNotNull(jenaModel);
		Assert.assertNotNull(resourceInjectionService);
	}

	@Test(groups= { "default" }, enabled=false)
	public void testFindAllBySimulationName() throws Exception{
		TransformationTO transformationTO = null;
		List<TransformationTO> transformationTOList = transformationService.findAllByProjectNameService("projectNameTest");
 
		for(TransformationTO transf:transformationTOList){
			if(transformationTO!=null){
				break;
			}
			if(transf.getIsInUse() && isListContainsTwoSelectedAttributes(transformationTOList,"ids","translationXs")){
				transformationTO = transf;					
			}
		}

	}

	
	private boolean isListContainsTwoSelectedAttributes(List<TransformationTO> transformationTOList, String param1, String param2) {
		//Map<String,String> evaluateParametersToChooseTransformation = new TreeMap<String,String>();
		for(TransformationTO to : transformationTOList){
			String strList = Arrays.deepToString(to.getParameters().toArray());
			if(StringUtils.containsIgnoreCase(strList,param1) &&
			StringUtils.containsIgnoreCase(strList,param2)){
				System.out.println(param1+" "+param2);
				return Boolean.TRUE;
			}
		}
		return false;
	}

	@Test(groups= { "default" }, enabled=true)
	public void testQueryAndScriptWorkingTogether() throws Exception {
		String datTurtle = "src/test/resources/dat_input.ttl";
		String unvTurtle = "src/test/resources/unv_output.ttl";
		JenaModel model = new JenaModel();
		model.importDataService(datTurtle);
		model.importDataService(unvTurtle);

		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> SELECT  "
				+ "?xCoords  "
				+ "?yCoord  "
				+ "?zCoord  "
				+ "?nodes "
				+ "?translationXs  "
				+ "?translationY  "
				+ "?translationZ  "
				+ "?ids  "
				+ "WHERE { ?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?nodes rdf:type fem:Node .   "
				+ "?nodes fem:isNodeOf ?subDomain. ?nodes fem:hasNodeID ?ids. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?nodes fem:hasXCoordinate ?xCoords . ?nodes fem:hasYCoordinate ?yCoord . ?nodes fem:hasZCoordinate ?zCoord . ?nodes fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationXs . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  }";

		List<String> queryVariables = new ArrayList<String>();
		queryVariables.add("xCoords");
		queryVariables.add("yCoord");
		queryVariables.add("zCoord");
		queryVariables.add("nodes");
		queryVariables.add("translationXs");
		queryVariables.add("translationY");
		queryVariables.add("translationZ");
		queryVariables.add("ids");
		
		Map<String, List<RDFNode>> queryModel = model.queryModelService(query,queryVariables);
		Map<String,List<String>> result = new TreeMap<String,List<String>>();
		new Util().getValues(queryModel,query);
		System.out.println(result);
	}

//	private void getValues(Map<String, List<RDFNode>> queryModel,String query) throws Exception {
//		
//		//
//		Map<String,List<String>> result = new TreeMap<String,List<String>>();
//		
//		//
//		
//		Map<String, String> queryAttributes = new TreeMap<String, String>();
//		Set<String> checkAttributes = new TreeSet<String>();
//		new Util().fillQueryAttributes(query, queryAttributes);
//		
//		File scriptFile = new File("src/test/resources/transformationScript/scriptBase.py");
//		String script = FileUtils.readFileToString(scriptFile);
//
//		Set<String> usedKeys = new TreeSet<String>();
//		List<RDFNode> usedResources = new ArrayList<RDFNode>();
//
//		for (Map.Entry<String, String> entry : queryAttributes.entrySet()) {
//
//				for (Map.Entry entryQueryModel : queryModel.entrySet()) {
//					if (usedKeys.contains(entryQueryModel.getKey())) {
//						continue;
//					}
//					String mainkey = (String) entryQueryModel.getKey();
//					usedKeys.add(mainkey);
//
//					if ("java.util.ArrayList".equals(entryQueryModel.getValue().getClass().getName())) {
//						@SuppressWarnings("unchecked")
//						List<RDFNode> rdfnodeList = (List<RDFNode>) entryQueryModel.getValue();
//
//						List pyList = new ArrayList(new ArrayList<String>());
//						int count = 0;
//						for (RDFNode node : rdfnodeList) {
//							if (node.isLiteral()) {
//								String literalNodeAsString = node.asLiteral().toString();
//								literalNodeAsString = literalNodeAsString.substring(0,literalNodeAsString.indexOf("^^"));
//								pyList.add("'"+literalNodeAsString+"'");
//								count++;
//							}
//							if (node.isResource()) {
//								if (!usedResources.toString().toString().contains(node.asResource().toString().toString())) {
//									usedResources.add(node);									
//								}
//
//								if (!node.asResource().getURI().contains("http://www.sifemontologies.com/ontologies")) {
//									continue;
//								}
//								pyList.add("'"+node.asResource().getURI()+"'");
//								count++;
//							}
//							
//							
//							if (rdfnodeList.size() == count) {
//								String key = (String) entryQueryModel.getKey();
//								result.put(new String(key),pyList);
//								checkAttributes.add(key);
//								count = 0;
//								continue;
//							}
//							
//						}
//
//					}
//
//				}
//
//			
//
//		}
//
//		StringBuilder sb = new StringBuilder();
//		List<String> usedKey = new ArrayList<String>();
//		for (Map.Entry<String, List<String>> entry : result.entrySet()) {
//			if(usedKey.contains(entry.getKey())){
//				continue;
//			}
//			usedKey.add(entry.getKey());
//			sb.append(entry.getKey()+"="+entry.getValue()+"\n");
//		}
//
//
//		sb.append(sb.toString()+"\n");
//		System.out.println(sb.toString());
//		
//		sb.append(script);
//		
//
//		
//		String filePathStr = "C:\\ProjetosWS\\sifem\\Sifem\\SifemCore\\src\\test\\resources\\transformationScript\\transformationScript.py";
//		File file = new File(filePathStr);
//		file.createNewFile();
//		FileUtils.writeStringToFile(file, sb.toString(), Boolean.FALSE);
//		Map<String,String> map = new CommandLineTools().runCommandAndGetMap("C:\\Python34\\python.exe "+filePathStr);
//
//
//	}


	
	@Test(groups= { "default" },enabled=false)
	public void testFillQueryAttributesWithOneAttribute() throws Exception {
		Map<String, String> result = new TreeMap<String, String>();
		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> \n"
				+ "PREFIX femsettings: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> \n"
				+ "PREFIX da:  <http://www.sifemontologies.com/ontologies/DataAnalysis.owl#>  \n"
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#>  \n"
				+ "PREFIX omlprop: <http://codata.jp/OML-Property.owl#> \n"
				+ " SELECT ?material  \n"
				+ " WHERE { \n"
				+ "?material rdf:type fem:Material . \n"
				+ "?material fem:hasMaterialNumber ?materialNumber . \n"
				+ "?material fem:hasMaterialProperty ?materialProperty.  \n"
				+ "?materialProperty fem:hasMaterialPropertyValue ?materialPropertyValue .  }           ";
		new Util().fillQueryAttributes(query, result);
		Assert.assertFalse(result.isEmpty());
		Assert.assertEquals(result.get("material"),"material");
	}
	
	
	@Test(groups= { "default" },enabled=false)
	public void testFillQueryAttributes() throws Exception {
		Map<String, String> result = new TreeMap<String, String>();
		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
				+ "SELECT  "
				+ "?xCoord  "
				+ "?yCoord  "
				+ "?zCoord  "
				+ "?translationX  "
				+ "?translationY  "
				+ "?translationZ  "
				+ "?ids  "
				+ "WHERE { ?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    "
				+ "?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .   "
				+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?ids. ?subDomain fem:makesUp ?subDomainGroup. "
				+ "?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . "
				+ "?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . "
				+ "?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  }";
		new Util().fillQueryAttributes(query, result);
		Assert.assertFalse(result.isEmpty());
		Assert.assertEquals("xCoord".toUpperCase(),result.get("xCoord".toUpperCase()));
		Assert.assertEquals("yCoord".toUpperCase(),result.get("yCoord".toUpperCase()));
		Assert.assertEquals("translationX".toUpperCase(),result.get("translationX".toUpperCase()));
		Assert.assertEquals("translationY".toUpperCase(),result.get("translationY".toUpperCase()));
		Assert.assertEquals("translationZ".toUpperCase(),result.get("translationZ".toUpperCase()));
		Assert.assertEquals("ids".toUpperCase(),result.get("ids".toUpperCase()));
	}


	@Test(groups= { "default" },enabled=false)
	public void testFillQueryAttributesWithManyAttributes() throws Exception {
		Map<String, String> result = new TreeMap<String, String>();
		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> \n"
				+ "PREFIX femsettings: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> \n"
				+ "PREFIX da:  <http://www.sifemontologies.com/ontologies/DataAnalysis.owl#>  \n"
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#>  \n"
				+ "PREFIX omlprop: <http://codata.jp/OML-Property.owl#> \n"
				+ " SELECT ?material ?materialNumber ?materialProperty ?materialPropertyValue \n"
				+ " WHERE { \n"
				+ "?material rdf:type fem:Material . \n"
				+ "?material fem:hasMaterialNumber ?materialNumber . \n"
				+ "?material fem:hasMaterialProperty ?materialProperty.  \n"
				+ "?materialProperty fem:hasMaterialPropertyValue ?materialPropertyValue .  }           ";
		new Util().fillQueryAttributes(query, result);
		Assert.assertFalse(result.isEmpty());
		Assert.assertEquals(4, result.size());
		Assert.assertEquals("material".toUpperCase(),
				result.get("material".toUpperCase()));
		Assert.assertEquals("materialNumber".toUpperCase(),
				result.get("materialNumber".toUpperCase()));
		Assert.assertEquals("materialProperty".toUpperCase(),
				result.get("materialProperty".toUpperCase()));
		Assert.assertEquals("materialPropertyValue".toUpperCase(),
				result.get("materialPropertyValue".toUpperCase()));
	}

	
	


}
