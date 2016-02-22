package eu.sifem.transformations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import virtuoso.jena.driver.VirtDataset;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

import eu.sifem.dao.jena.JenaModel;
import eu.sifem.featurextraction.FeatureExtractor;
import eu.sifem.utils.Util;

@ContextConfiguration(locations = { "classpath:SifemCore-applicationContext.xml" })
public class DisplacementGraphicGeneration extends AbstractTestNGSpringContextTests{
	
	@Autowired
	private VirtDataset virtDataset;
	
	@Test(enabled=true)
	public void selectAllNamedGraphs(){
		List<String> namedGraphs = new ArrayList<String>();
		String query = "SELECT DISTINCT(?g) WHERE { GRAPH ?g { ?s ?p ?o } }";
		ResultSet results = Util.executeQuery(query, virtDataset);
		while(results.hasNext()) {		
			QuerySolution sol = results.next();		
				RDFNode nodeVar = sol.get("g");
				namedGraphs.add(nodeVar.toString());
		}
		
		System.out.println( namedGraphs);
	}
	
	
	@Test(enabled=false)
	public void countTriplesFromVirtuoso() throws Exception {

		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ");
		sb.append("PREFIX owl: <http://www.w3.org/2002/07/owl#> ");
		sb.append("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> ");
		sb.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ");
		sb.append("PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> ");
		sb.append("PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> ");
		sb.append("PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> ");
		sb.append("SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id "); 
		sb.append("FROM NAMED <http://www.sifemontologies.com/ontologies/Simulation/DisplacementInstance_0>  ");
		sb.append("WHERE { "); 
		sb.append("GRAPH ?g {  ");
		sb.append("?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node . ");  
		sb.append("?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ . ");  
		sb.append("} "); 
		sb.append("}");

		try {
				List<String> queryVariables = new ArrayList<String>();
				queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
				queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
				queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
				queryVariables.add("subDomain");queryVariables.add("id");

				Map<String, List<RDFNode>> queryModel = queryModel(sb.toString(), queryVariables,virtDataset);		

				//List<RDFNode> materials = queryModel.get(queryVariables.get(0));
				//List<RDFNode> materialTypes = queryModel.get(queryVariables.get(1));		
				List<RDFNode> nodes = queryModel.get(queryVariables.get(2));
				List<RDFNode> xCoords = queryModel.get(queryVariables.get(3));
				//List<RDFNode> yCoords = queryModel.get(queryVariables.get(4));
				//List<RDFNode> zCoords = queryModel.get(queryVariables.get(5));
				List<RDFNode> translationXs = queryModel.get(queryVariables.get(6));
				//List<RDFNode> translationYs = queryModel.get(queryVariables.get(7));
				//List<RDFNode> translationZs = queryModel.get(queryVariables.get(8));
				List<RDFNode> subDomains = queryModel.get(queryVariables.get(9));
				List<RDFNode> ids = queryModel.get(queryVariables.get(10));

				System.out.println("Nodes number: " + nodes.size());

				//Set<RDFNode> nodesCovered = new HashSet<RDFNode>();
				Map<RDFNode, Float> nodeXDisplacement = new HashMap<RDFNode, Float>();
				Map<Integer, RDFNode> idNodeMap = new HashMap<Integer, RDFNode>();
				Map<RDFNode, Float> nodeXCoord = new HashMap<RDFNode, Float>();

				int size = subDomains.size();
				for(int i=0; i<size; i++){			
					Float displacementX =  translationXs.get(i).asLiteral().getFloat();
					Float currentXCoord = xCoords.get(i).asLiteral().getFloat();
					System.out.println(currentXCoord);
					RDFNode node = nodes.get(i);
					RDFNode id = ids.get(i);
					int nodeNumber = id.asLiteral().getInt();
					nodeXDisplacement.put(node, displacementX);
					nodeXCoord.put(node, currentXCoord);
					//nodesCovered.add(node);
					idNodeMap.put(nodeNumber, node);
				}
				
				System.out.println("Final Nodes Remaining: " + idNodeMap.size());
				List<Double> datax = new ArrayList<Double>();
				List<Double> datay = new ArrayList<Double>();
				List<Integer> nodeSortedIds = new ArrayList<Integer>();
				for(int i : idNodeMap.keySet()) {
					nodeSortedIds.add(i);
				}
				Collections.sort(nodeSortedIds);
				Float previousXCoord = Float.MIN_VALUE;		
				for(Integer id : nodeSortedIds){
					RDFNode node = idNodeMap.get(id);
					Float displacement = nodeXDisplacement.get(node);
					Float currentXCoord = nodeXCoord.get(node);
					if(currentXCoord > previousXCoord){
						datax.add(currentXCoord.doubleValue());
						datay.add(displacement.doubleValue());
						previousXCoord = currentXCoord;				
					}
				}
				System.out.println(nodeSortedIds.size());
				System.out.println(datax);
				System.out.println(datax.size());		
				System.out.println(datay);
				System.out.println(datay.size());		
				
				FeatureExtractor f = new FeatureExtractor();
				//f.plot2dGFeat(datax, "NodesInIncreasingOrder", datay, "XDisplacement", "DisplacementGraph","C:/Users/JoaoBoscoJares/Desktop/tmp/sifem2bkp/SifemCore/src/test/resources/" );
				
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			sb = new StringBuilder();
		}
	}
	
	public Map<String, List<RDFNode>> queryModel(String queryString, List<String> queryVariables,VirtDataset virtDataset) {
		Map<String, List<RDFNode>> solution = new HashMap<String, List<RDFNode>>();
		QueryExecution qexec = null;
		try{
			qexec = QueryExecutionFactory.create(queryString, virtDataset);
			//ResultSet rs = executeQuery(sb.toString(), virtDataset);
		}catch(com.hp.hpl.jena.query.QueryParseException e){
			System.err.println(ExceptionUtils.getStackTrace(e)+"\n Will return an empty map...");
			return Collections.EMPTY_MAP;
		}

		ResultSet results = qexec.execSelect();
		while(results.hasNext()) {		
			QuerySolution sol = results.next();		
			for(String variable : queryVariables) {	

				RDFNode nodeVar = sol.get(variable);
				if(nodeVar!=null){
					if(solution.get(variable)==null){
						solution.put(variable, new ArrayList<RDFNode>());											
					}
					solution.get(variable).add(nodeVar);
				}
			}				
		}

		return solution;
	}	
	
	public ResultSet executeQuery(String query, VirtDataset virtDataset) {
		VirtGraph vg = new VirtGraph(virtDataset.getGraphUrl(),
				virtDataset.getGraphUser(), virtDataset.getGraphPassword());
		VirtuosoQueryExecution vur = VirtuosoQueryExecutionFactory.create(
				query, vg);
		ResultSet rs = vur.execSelect();
		return rs;
	}
	
	
	@Test(enabled=false)
	public void countTriplesFromTTL() throws Exception {
		String datTurtle = "src/test/resources/dat_input.ttl";
		String unvTurtle = "src/test/resources/unv_output.ttl";
		JenaModel model = new JenaModel();
		model.importDataService(datTurtle);
		model.importDataService(unvTurtle);
		String query = "SELECT (COUNT(*) AS ?x) "
				+ "WHERE "
				+ "{ "
				+ "?s ?p ?o  . "
				+ "} ";
		
		List<String> queryVariables = new ArrayList<String>();
		queryVariables.add("x");
		
		Map<String, List<RDFNode>> queryModel = model.queryModelService(query, queryVariables);		
		System.out.println(queryModel);
		List<RDFNode> s = queryModel.get("x");		
		
		System.out.println(s);
	}

	
	


	@Test(enabled=false)
	public void generateWithTTLFromSource() {
		String datTurtle = "src/test/resources/dat_input.ttl";
		String unvTurtle = "src/test/resources/unv_output.ttl";
		JenaModel model = new JenaModel();
		model.importDataService(datTurtle);
		model.importDataService(unvTurtle);
//		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
//				"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"+
//				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"+
//				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
//				"PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#>\n"+
//				"PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#>\n"+
//				"PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#>\n"+
//				"SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id WHERE { ?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .  " +
//				"?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  } " +
//				"";
		
		String query2 = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
				+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id  "
				+ "WHERE {  "
				+ "?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .   "
				+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .   "
				+ "} ";
		List<String> queryVariables = new ArrayList<String>();
		queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
		queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
		queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
		queryVariables.add("subDomain");queryVariables.add("id");

		Map<String, List<RDFNode>> queryModel = model.queryModelService(query2, queryVariables);		

		//List<RDFNode> materials = queryModel.get(queryVariables.get(0));
		//List<RDFNode> materialTypes = queryModel.get(queryVariables.get(1));		
		List<RDFNode> nodes = queryModel.get(queryVariables.get(2));
		List<RDFNode> xCoords = queryModel.get(queryVariables.get(3));
		//List<RDFNode> yCoords = queryModel.get(queryVariables.get(4));
		//List<RDFNode> zCoords = queryModel.get(queryVariables.get(5));
		List<RDFNode> translationXs = queryModel.get(queryVariables.get(6));
		//List<RDFNode> translationYs = queryModel.get(queryVariables.get(7));
		//List<RDFNode> translationZs = queryModel.get(queryVariables.get(8));
		List<RDFNode> subDomains = queryModel.get(queryVariables.get(9));
		List<RDFNode> ids = queryModel.get(queryVariables.get(10));

		System.out.println("Nodes number: " + nodes.size());

		//Set<RDFNode> nodesCovered = new HashSet<RDFNode>();
		Map<RDFNode, Float> nodeXDisplacement = new HashMap<RDFNode, Float>();
		Map<Integer, RDFNode> idNodeMap = new HashMap<Integer, RDFNode>();
		Map<RDFNode, Float> nodeXCoord = new HashMap<RDFNode, Float>();

		int size = subDomains.size();
		for(int i=0; i<size; i++){			
			Float displacementX =  translationXs.get(i).asLiteral().getFloat();
			Float currentXCoord = xCoords.get(i).asLiteral().getFloat();
			System.out.println(currentXCoord);
			RDFNode node = nodes.get(i);
			RDFNode id = ids.get(i);
			int nodeNumber = id.asLiteral().getInt();
			nodeXDisplacement.put(node, displacementX);
			nodeXCoord.put(node, currentXCoord);
			//nodesCovered.add(node);
			idNodeMap.put(nodeNumber, node);
		}
		
		System.out.println("Final Nodes Remaining: " + idNodeMap.size());
		List<Double> datax = new ArrayList<Double>();
		List<Double> datay = new ArrayList<Double>();
		List<Integer> nodeSortedIds = new ArrayList<Integer>();
		for(int i : idNodeMap.keySet()) 
			nodeSortedIds.add(i);
		Collections.sort(nodeSortedIds);
		Float previousXCoord = Float.MIN_VALUE;		
		for(Integer id : nodeSortedIds){
			RDFNode node = idNodeMap.get(id);
			Float displacement = nodeXDisplacement.get(node);
			Float currentXCoord = nodeXCoord.get(node);
			if(currentXCoord > previousXCoord){
				datax.add(currentXCoord.doubleValue());
				datay.add(displacement.doubleValue());
				previousXCoord = currentXCoord;				
			}
		}
		System.out.println(nodeSortedIds.size());
		System.out.println(datax);
		System.out.println(datax.size());		
		System.out.println(datay);
		System.out.println(datay.size());		
		
		FeatureExtractor f = new FeatureExtractor();
		//f.plot2dGFeat(datax, "NodesInIncreasingOrder", datay, "XDisplacement", "DisplacementGraph","C:/Users/JoaoBoscoJares/Desktop/tmp/sifem2bkp/SifemCore/src/test/resources/" );
	}
	
	
	
	/**
	 * 
	 * 
	 * 		StringBuilder sb = new StringBuilder();
		List<SimulationProcessManagerTO> simulationProcessManagerTOList = simulationProcessManagerDAO.findAll();
		sb.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
				+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
				+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> ");
		sb.append(" SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id ?excitationFreqVal ");
		
		//
		sb.append("FROM NAMED ");
		String namedGraph = simulationProcessManagerTOList.get(0).getSimulationInstanceName().get(0);
		System.out.println(namedGraph);
		sb.append(namedGraph);
		sb.append(" ");
		//

		sb.append("WHERE { GRAPH ?g { ");
		sb.append(" ?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType.  ?node rdf:type fem:Node .   "
				+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .   "
				+ "?loadResource rdf:type fem:Load. ?loadResource fem:holdsValueFor ?excitationFreq. ?excitationFreq rdf:type fem:Frequency.  "
				+ "?excitationFreq sim:hasScalarValue ?excitationFreqValObj. ?excitationFreqValObj sim:hasScalarDataValue ?excitationFreqVal. ");
		sb.append(" } }");

		try {
			virtDataset.begin(ReadWrite.READ);
			ResultSet rs = executeQuery(sb.toString(), virtDataset);
			while(rs.hasNext()){
				QuerySolution qs = rs.next();
				String material = qs.get("?material").toString();
				String materialType = qs.get("?materialType").toString();
				String node = qs.get("?node").toString();
				String xCoord = qs.get("?xCoord").toString();
				String yCoord = qs.get("?yCoord").toString();
				String zCoord = qs.get("?zCoord").toString();
				String translationX = qs.get("?translationX").toString();
				String translationY = qs.get("?translationY").toString();
				String translationZ = qs.get("?translationZ").toString();
				String subDomain = qs.get("?subDomain").toString();
				String id = qs.get("?id").toString();
				String excitationFreqVal = qs.get("?excitationFreqVal").toString();
				
				System.out.println(material);
				System.out.println(materialType);
				System.out.println(node);
				System.out.println(xCoord);
				System.out.println(yCoord);
				System.out.println(zCoord);
				System.out.println(translationX);
				System.out.println(translationY);
				System.out.println(translationZ);
				System.out.println(subDomain);
				System.out.println(id);
				System.out.println(excitationFreqVal);

			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			sb = new StringBuilder();
		}
	 * 
	 * 
	 */
}
