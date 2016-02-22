package eu.sifem.test.unit.workflow.stepbystep;

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

import eu.sifem.model.to.DataSetHashCacheTO;
import eu.sifem.model.to.TransformationSimulationTO;
import eu.sifem.service.dao.IDataSetHashCacheDAOService;
import eu.sifem.service.dao.IGenericJenaQueryDAOService;
import eu.sifem.utils.Util;

@Test(groups= { "default" })
@ContextConfiguration(locations={"classpath:SifemCore-applicationContext_test.xml"})
public class WorkflowIndexRawDataTestUnit extends AbstractTestNGSpringContextTests{
	
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
	
		private Map<String, List<String>> values = new TreeMap<String, List<String>>();
		
		@Autowired
		private IGenericJenaQueryDAOService genericJenaDAOQueryService;
		
		@Autowired
		private IDataSetHashCacheDAOService dataSetHashCacheDAO;
		
		
		
		@Test(groups= { "default" },priority=1)
		public void testServiceIsNotNull() throws Exception{
			Assert.assertNotNull(genericJenaDAOQueryService);
			Assert.assertNotNull(dataSetHashCacheDAO);
		}
		
		@Test(groups= { "default" },priority=2,enabled=false)
		public void testFindGenericGraph() throws Exception{
			TransformationSimulationTO transformationSimulationTO = new TransformationSimulationTO();
			transformationSimulationTO.setQueryStr(QUERY);
			Map<String,String> attributes = new TreeMap<String,String>();
			new Util().fillQueryAttributes(QUERY, attributes);
			Map<String, List<RDFNode>> result = genericJenaDAOQueryService.findGenericGraph(transformationSimulationTO, attributes, null, null, null, null);
			Assert.assertNotNull(result);
			Assert.assertNotSame(Collections.EMPTY_MAP, result);
			
			Map<String, List<String>> values = new Util().getValues(result,attributes);
			setValues(values);

		}
		
		@Test(groups= { "default" },priority=3,enabled=false)
		public void insertRawIndexedDataIntoCacheDB() throws Exception{
			Map<String, List<String>> values = getValues();
			DataSetHashCacheTO dataSetHashCacheTO = dataSetHashCacheDAO.insert("PROJECTTEST","SIMULATIONUNITTEST",values);
			Assert.assertNotNull(dataSetHashCacheTO);
			System.out.println(dataSetHashCacheTO.get_id().toString());
		}

		@Test(enabled=false)
		public Map<String, List<String>> getValues() {
			return values;
		}

		@Test(enabled=false)
		public void setValues(Map<String, List<String>> values) {
			this.values = values;
		}
		
		
		

}
