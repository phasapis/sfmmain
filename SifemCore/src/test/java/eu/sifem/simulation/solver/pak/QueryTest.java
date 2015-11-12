package eu.sifem.simulation.solver.pak;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.RDFNode;

import eu.sifem.dao.jena.JenaModel;
import eu.sifem.featurextraction.FeatureExtractor;

public class QueryTest {

	public static void main(String[] args) throws IOException {
		
		
		String datTurtle = "src/test/resources/dat_input.ttl";
		String unvTurtle = "src/test/resources/unv_output.ttl";
		JenaModel model = new JenaModel();
		model.importDataService(datTurtle);
		model.importDataService(unvTurtle);
		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
				"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"+
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"+
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
				"PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#>\n"+
				"PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#>\n"+
				"PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#>\n"+
				"SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id WHERE { ?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .  " +
				"?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  } " +
				"";
		List<String> queryVariables = new ArrayList<String>();
		queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
		queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
		queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
		queryVariables.add("subDomain");queryVariables.add("id");

		Map<String, List<RDFNode>> queryModel = model.queryModelService(query, queryVariables);		

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
		List<String> datax = new ArrayList<String>();
		List<String> datay = new ArrayList<String>();
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
				datax.add(currentXCoord.toString());
				datay.add(displacement.toString());
				previousXCoord = currentXCoord;				
			}
		}
		
		
		
		System.out.println(nodeSortedIds.size());
		System.out.println(datax);
		System.out.println(datax.size());		
		System.out.println(datay);
		System.out.println(datay.size());	
		
		
		
		File file = new File("src/test/resources/eu_sifem_test_unit_workflow_stepbystep/WorkflowUnit/img/");
		file.mkdir();
		
		FeatureExtractor f = new FeatureExtractor();
		f.plot2dGFeatService(datax, "NodesInIncreasingOrder", datay, "XDisplacement", "DisplacementGraph",file.getCanonicalPath()+"/");
		System.exit(0);
	}
	

}
