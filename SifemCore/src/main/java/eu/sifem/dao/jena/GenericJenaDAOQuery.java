package eu.sifem.dao.jena;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import virtuoso.jena.driver.VirtDataset;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

import eu.sifem.model.to.TransformationSimulationTO;
import eu.sifem.model.to.TransformationTO;
import eu.sifem.service.IJenaModelService;
import eu.sifem.service.ITransformationService;
import eu.sifem.service.dao.IDatAndUnvSolverDAOService;
import eu.sifem.service.dao.IGenericJenaQueryDAOService;
import eu.sifem.utils.BasicFileTools;
import eu.sifem.utils.Util;

@Service("genericJenaQuery")
public class GenericJenaDAOQuery implements IGenericJenaQueryDAOService{
	
	@Autowired
	private IJenaModelService jenaModel;
	
	@Autowired
	private IDatAndUnvSolverDAOService datAndUnvSolverDAO;
	
	@Autowired
	private VirtDataset virtDataset;
	
	@Autowired
	private ITransformationService transformationService;
	
	@Override
	public List<String> selectAllNamedGraphs(){
		List<String> namedGraphs = new ArrayList<String>();
		String query = "SELECT DISTINCT(?g) WHERE { GRAPH ?g { ?s ?p ?o } }";
		ResultSet results = Util.executeQuery(query, virtDataset);
		while(results.hasNext()) {		
			QuerySolution sol = results.next();		
				RDFNode nodeVar = sol.get("g");
				namedGraphs.add(nodeVar.toString());
		}
		return namedGraphs;
	}
	
	@Override
	public Boolean askNamedGraphExistence(String namedgraph){
		String query = "ASK WHERE {GRAPH <"+namedgraph+"> {?s ?p ?o.}}";
		VirtGraph vg = new VirtGraph(virtDataset.getGraphUrl(),
				virtDataset.getGraphUser(), virtDataset.getGraphPassword());
		VirtuosoQueryExecution vur = VirtuosoQueryExecutionFactory.create(query, vg);
		Boolean result = vur.execAsk();
		return result;
		
	}
	
	//ASK WHERE {GRAPH <http://www.sifemontologies.com/ontologies/projectThree_simi1_instance_0> {?s ?p ?o.}}
	@Override
	public Map<String, List<RDFNode>> findGenericGraph(TransformationSimulationTO transformationSimulationTO, Map<String, String> attributes, List<File> semantificationFiles, String projectName, String simulationName,TransformationTO transformationTO) throws Exception{
		
		Map<String, List<RDFNode>> result = new TreeMap<String,List<RDFNode>>();
		List<String> filePaths = new ArrayList<String>();
		if(semantificationFiles!=null && !semantificationFiles.isEmpty()){
			filePaths.addAll(getFilePathNames(semantificationFiles));
						
		}
		if((filePaths==null || filePaths.isEmpty())){
			if(transformationSimulationTO!=null && transformationSimulationTO.getDatasourceTempPathBySimulationNameList()!=null
					&& !transformationSimulationTO.getDatasourceTempPathBySimulationNameList().isEmpty()){
				filePaths.addAll(transformationSimulationTO.getDatasourceTempPathBySimulationNameList());				
			}
		
			if((filePaths==null || filePaths.isEmpty())){
				List<String> pathList = BasicFileTools.locateDatasourceTempPath(transformationSimulationTO.getWorkspace(), simulationName);
				if(pathList!=null && !pathList.isEmpty()){
					filePaths.addAll(pathList);									
				}
			}
		}

		String query = transformationSimulationTO.getQueryStr();

		
		List<String> queryVariables = new ArrayList<String>();
		for (Map.Entry<String, String> entry : attributes.entrySet()) {
			if( StringUtils.equalsIgnoreCase(entry.getKey(),entry.getValue())){
				queryVariables.add(entry.getValue().trim());				
			}
		}
		
		Map<String, List<RDFNode>> queryModel = null;
		if(transformationTO==null && !filePaths.isEmpty()){
			importData(jenaModel,filePaths);
			queryModel = jenaModel.queryModelService(query, queryVariables);		
		}else{
			String queryStr = "";
			if(transformationTO!=null){
				InputStream queryIS = transformationService.findQueryByTransformationNameService(transformationTO.getName());
				queryStr = IOUtils.toString(queryIS,"UTF-8");
				if(transformationTO.getNamedGraphs()!=null && !transformationTO.getNamedGraphs().isEmpty()){
					queryStr = new Util().addNamedGraphsToQuery(queryStr,transformationTO.getNamedGraphs());				
				}
				if(queryIS==null){
					queryStr = transformationSimulationTO.getQueryStr();
				}
			}else{
				queryStr = transformationSimulationTO.getQueryStr();
			}


			ResultSet results = Util.executeQuery(queryStr, virtDataset);
			queryModel = jenaModel.queryModelWithResultService(results,queryVariables);		
		}
		if(queryModel==null){
			//TODO review
			//throw new RuntimeException("ERROR: queryModel is empty!");
			return Collections.EMPTY_MAP;
		}

		int count = 0;
		for (String str : queryVariables) {
			result.put(str, queryModel.get(queryVariables.get(count)));
			count++;
		}
		return result;
	}
	
	
	private List<String> getFilePathNames(List<File> semantificationFiles) throws Exception {
		List<String> canonicalPathFileNameList = new ArrayList<String>();
		for(File canonicalPathFile:semantificationFiles){
			canonicalPathFileNameList.add(canonicalPathFile.getCanonicalPath());
		}
		
		//TODO validate existent files
		return canonicalPathFileNameList;
	}

	
	//TODO create test case
//	@Override
//	public Map<String, List<RDFNode>> findDisplacementGraph(TransformationSimulationTO transformationSimulationTO) throws Exception{
//		
//		Map<String, List<RDFNode>> result = new TreeMap<String,List<RDFNode>>();
////		String datTurtle = "src/test/resources/dat_input.ttl";
////		String unvTurtle = "src/test/resources/unv_output.ttl";
//		importData(jenaModel,transformationSimulationTO.getDatasourceTempPathBySimulationNameList());
//
////		jenaModel.importData(datTurtle);
////		jenaModel.importData(unvTurtle);
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
//		List<String> queryVariables = new ArrayList<String>();
//		queryVariables.add("material");queryVariables.add("materialType");queryVariables.add("node");
//		queryVariables.add("xCoord");queryVariables.add("yCoord");queryVariables.add("zCoord");
//		queryVariables.add("translationX");queryVariables.add("translationY");queryVariables.add("translationZ");
//		queryVariables.add("subDomain");queryVariables.add("id");
//
//		Map<String, List<RDFNode>> queryModel = jenaModel.queryModel(query, queryVariables);		
//
//		//List<RDFNode> materials = queryModel.get(queryVariables.get(0));
//		//List<RDFNode> materialTypes = queryModel.get(queryVariables.get(1));		
//		List<RDFNode> nodes = queryModel.get(queryVariables.get(2));
//		List<RDFNode> xCoords = queryModel.get(queryVariables.get(3));
//		//List<RDFNode> yCoords = queryModel.get(queryVariables.get(4));
//		//List<RDFNode> zCoords = queryModel.get(queryVariables.get(5));
//		List<RDFNode> translationXs = queryModel.get(queryVariables.get(6));
//		//List<RDFNode> translationYs = queryModel.get(queryVariables.get(7));
//		//List<RDFNode> translationZs = queryModel.get(queryVariables.get(8));
//		List<RDFNode> subDomains = queryModel.get(queryVariables.get(9));
//		List<RDFNode> ids = queryModel.get(queryVariables.get(10));
//
//		result.put("node", nodes);
//		result.put("xCoord", xCoords);
//		result.put("translationX", translationXs);
//		result.put("subDomain", subDomains);
//		result.put("id", ids);
//		return result;
//	}

	private void importData(IJenaModelService model,List<String> datasourceTempPathBySimulationNameList) throws Exception {
		if(datasourceTempPathBySimulationNameList==null || datasourceTempPathBySimulationNameList.isEmpty()){
			throw new RuntimeException("The datasourceTempPathBySimulationNameList argument is null or empty.");
		}
		for(String dataStr:datasourceTempPathBySimulationNameList){
			model.importDataService(dataStr);
		}
	}

	@Override
	public Boolean askNamedGraphExistence(List<String> namedGraphs) {
		Boolean result = Boolean.FALSE;
		for(String name:namedGraphs){
			String query = "ASK WHERE {GRAPH <"+name+"> {?s ?p ?o.}}";
			VirtGraph vg = new VirtGraph(virtDataset.getGraphUrl(),
					virtDataset.getGraphUser(), virtDataset.getGraphPassword());
			VirtuosoQueryExecution vur = VirtuosoQueryExecutionFactory.create(query, vg);
			result = vur.execAsk();
			if(result){
				return result;
			}
			
		}
		return result;
	}


}
