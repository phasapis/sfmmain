package eu.sifem.dao.jena;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hp.hpl.jena.rdf.model.RDFNode;

import eu.sifem.model.to.TransformationSimulationTO;
import eu.sifem.model.to.TransformationTO;
import eu.sifem.service.dao.IGenericJenaQueryDAOService;
import eu.sifem.utils.Util;


@Test(groups= { "default" })
@ContextConfiguration(locations={"classpath:SifemCore-applicationContext_test.xml"})
public class GenericJenaDAOQueryTest extends AbstractTestNGSpringContextTests{

	private static final String QUERY = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
			+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
			+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
			+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
			+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
			+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id  "
			+ "FROM NAMED <http://www.sifemontologies.com/ontologies/test>  "
			+ "WHERE {  "
			+ "GRAPH ?g {  "
			+ "?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .   "
			+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  "
			+ "} "
			+ " }";
	
	@Autowired
	private IGenericJenaQueryDAOService genericJenaDAOQueryService;
	
	@Test(groups= { "default" })
	public void testServiceIsNotNull() throws Exception{
		Assert.assertNotNull(genericJenaDAOQueryService);
	}
	
	//public Map<String, List<RDFNode>> findGenericGraph(TransformationSimulationTO transformationSimulationTO, Map<String, String> attributes, List<File> semantificationFiles, String projectName, String simulationName,TransformationTO transformationTO) throws Exception{
	@Test(groups= { "default" })
	public void testFindGenericGraph() throws Exception{
		TransformationSimulationTO transformationSimulationTO = new TransformationSimulationTO();
		transformationSimulationTO.setQueryStr(QUERY);
		Map<String,String> attributes = new TreeMap<String,String>();
		new Util().fillQueryAttributes(QUERY, attributes);
		Map<String, List<RDFNode>> result = genericJenaDAOQueryService.findGenericGraph(transformationSimulationTO, attributes, null, null, null, null);
		Assert.assertNotNull(result);
		Assert.assertNotSame(Collections.EMPTY_MAP, result);
		
//		StringBuilder sb = new StringBuilder();
//		for(Map.Entry<String, List<RDFNode>> entry:result.entrySet()){
//			sb.append("Key: "+entry.getKey()+" -- Value: "+entry.getValue());
//		}
//		System.out.println(sb.toString());
	}
	
}
