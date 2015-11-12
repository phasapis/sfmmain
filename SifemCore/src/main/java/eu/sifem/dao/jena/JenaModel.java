package eu.sifem.dao.jena;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.springframework.stereotype.Service;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.RDFReader;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.DatasetGraphFactory;
import com.hp.hpl.jena.sparql.core.Quad;
import com.hp.hpl.jena.update.GraphStore;
import com.hp.hpl.jena.update.GraphStoreFactory;
import com.hp.hpl.jena.update.UpdateAction;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import eu.sifem.service.IJenaModelService;
import eu.sifem.utils.Util;

@Service(value="jenaModel")
public class JenaModel implements IJenaModelService{

	private OntModel ontModel;

	private PrintWriter log;

	private static final String QUERY = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
			+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
			+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
			+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
			+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
			+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?subDomain ?id  "
			//+ "FROM NAMED <http://www.sifemontologies.com/ontologies/test>  "
			+ "WHERE {  "
			//+ "GRAPH ?g {  "
			+ "?material rdf:type fem:Material . ?material fem:hasMaterialNumber ?y .    ?material pak:hasMaterialSettings ?z .  ?z pak:MATTYPE ?materialType. FILTER (?y=1) . ?node rdf:type fem:Node .   "
			+ "?node fem:isNodeOf ?subDomain. ?node fem:hasNodeID ?id. ?subDomain fem:makesUp ?subDomainGroup. ?subDomainGroup fem:hasMaterial ?material. ?node fem:hasXCoordinate ?xCoord . ?node fem:hasYCoordinate ?yCoord . ?node fem:hasZCoordinate ?zCoord . ?node fem:holdsValueFor ?b . ?b rdf:type fem:Translation. ?b sim:hasVectorValue ?a . ?a sim:isReal true . ?a sim:hasVectorXValue ?translationX . ?a sim:hasVectorYValue ?translationY . ?a sim:hasVectorZValue ?translationZ .  "
			//+ "} "
			+ " }";        

	public JenaModel(){
		ontModel = ModelFactory.createOntologyModel();
		openLog("log.txt");
	}

	@Override
	public OntModel getOntModel(){
		return ontModel;
	}

	private void openLog(String filePath) {
		try {
			log = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)), true);	
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	@Override
	public PrintWriter getLogWriter() {
		return log;
	}


	@Override
	public void importOntologyService(String ontologyFile, String base){		
		try {		
			if (ontologyFile.contains(".")==false){
				ontologyFile=ontologyFile+".owl";	
			}
			FileInputStream file = new FileInputStream(ontologyFile);	
			ontModel.read(file, base);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	@Override
	public void importDataService(String rdfFile) {
		System.out.println(rdfFile);
		DatasetGraph dataset = RDFDataMgr.loadDataset(rdfFile, Lang.TTL).asDatasetGraph();		
		Iterator<Quad> quads = dataset.find();
		while ( quads.hasNext() ) {
			Quad quad = quads.next();
			Triple triple = quad.asTriple();
			Statement statement = ontModel.asStatement(triple);
			ontModel.add(statement);
		}	
	}
	
	
	@Override
	public void importDataService(InputStream inputStream) throws IOException {
		DatasetGraph datasetGraph = DatasetGraphFactory.createMem();
//		RDFDataMgr.read(datasetGraph, inputStream, Lang.TTL);
//		Iterator<Quad> quads = datasetGraph.find();
//		while ( quads.hasNext() ) {
//			Quad quad = quads.next();
//			Triple triple = quad.asTriple();
//			Statement statement = ontModel.asStatement(triple);
//			ontModel.add(statement);
//		}	
		RDFReader r = ontModel.getReader("TTL");

//		r.setProperty("iri-rules", "strict") ;
//		r.setProperty("error-mode", "strict") ; // Warning will be errors.

		// Alternative to the above "error-mode": set specific warning to be an error.
		//r.setProperty( "WARN_MALFORMED_URI", ARPErrorNumbers.EM_ERROR) ;
                System.err.println(" ont=" + (ontModel==null) + " inputS=" + (inputStream==null));
                
                String str = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> select * where {?a <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?c. } ";
                
		r.read(ontModel, inputStream, null);
                this.queryModelService(str);
		inputStream.close();
	}
	

	@Override
	public void queryModelService(String queryString) {
		QueryExecution qexec = QueryExecutionFactory.create(queryString, ontModel);
		ResultSet results = qexec.execSelect();
                System.err.println("Printing solution");
		while(results.hasNext()) {
			QuerySolution solution = results.next();
                        if(solution.getResource("c").getLocalName().equals("Material"))
                            System.err.println(solution.getResource("c").getLocalName());
			//log.println(solution.toString() +"\n");				
		}
	}	

	@Override
	public Map<String, List<RDFNode>> queryModelService(String queryString, List<String> queryVariables) {
		Map<String, List<RDFNode>> solution = new HashMap<String, List<RDFNode>>();
		QueryExecution qexec = QueryExecutionFactory.create(queryString, ontModel);	
		ResultSet results = qexec.execSelect();
		while(results.hasNext()) {		
			QuerySolution sol = results.next();		
			for(String variable : queryVariables) {	

				RDFNode nodeVar = sol.get(variable);
				if(nodeVar!=null){
					if(solution.get(variable)==null)
						solution.put(variable, new ArrayList<RDFNode>());					
					solution.get(variable).add(nodeVar);
				}
			}
			log.println(solution.toString() +"\n");				
		}
		if(solution.isEmpty())
			solution = null;
		return solution;
	}	


	
	
	@Override
	public Map<String, List<RDFNode>> queryModelService(Map<String, String> prefixes, String queryString, List<String> queryVariables) {
		queryString = getPrefixStringService(prefixes) + queryString;
		Map<String, List<RDFNode>> solution = new HashMap<String, List<RDFNode>>();
		QueryExecution qexec = QueryExecutionFactory.create(queryString, ontModel);	
		ResultSet results = qexec.execSelect();
		while(results.hasNext()) {		
			QuerySolution sol = results.next();		
			for(String variable : queryVariables) {	

				RDFNode nodeVar = sol.get(variable);
				if(nodeVar!=null){
					if(solution.get(variable)==null)
						solution.put(variable, new ArrayList<RDFNode>());					
					solution.get(variable).add(nodeVar);
				}
			}
			log.println(solution.toString() +"\n");				
		}
		if(solution.isEmpty())
			solution = null;
		return solution;
	}	


	@Override
	public String getPrefixStringService(Map<String, String> prefixUriMap){
		StringBuffer buffer = new StringBuffer();
		for(String prefix : prefixUriMap.keySet()){
			String uri = prefixUriMap.get(prefix);
			buffer.append("Prefix " +  prefix + ":" +  "<" + uri + "#> "  + "\n");			
		}		
		return buffer.toString().trim() + "\n";
	}

	@Override
	public StmtIterator constructQueryService(String queryString) {
		QueryExecution qexec = QueryExecutionFactory.create(queryString, ontModel);	
		Model construct = qexec.execConstruct();
		StmtIterator statements = construct.listStatements();
		return statements;
	}



	@Override
	public void createIndividiualService(String ontology, String query)
			throws FileNotFoundException {
		DatasetGraph dataset = RDFDataMgr.loadDataset(ontology, Lang.RDFXML)
				.asDatasetGraph();
		GraphStore graphstore = GraphStoreFactory.create(dataset);
		graphstore.setDefaultGraph(ontModel.getGraph());
		FileOutputStream out = new FileOutputStream(ontology);
		UpdateAction.parseExecute(query.toString(), graphstore);
		ontModel.write(out);
	}

	/**
	 * It returns the name of the subclass of any Class from ontology
	 * @param className
	 * @return
	 */
	@Override
	public List<String> getSubclassOfService(String ontologyPath,String className) {
		List<String> lstOfSubclasses=new ArrayList<String>();
		OntClass classObj=this.ontModel.getOntClass(ontologyPath+"#"+className);
		ExtendedIterator<OntClass> iteratorSubClass=classObj.listSubClasses();
		while(iteratorSubClass.hasNext()){
			lstOfSubclasses.add(iteratorSubClass.next().getLocalName().trim());
		}
		return lstOfSubclasses;
	}
	
	/**
	 * It returns the data properties of a class from  ontology
	 * @param className
	 * @return
	 */
	@Override
	public List<String> getPropertiesOfService(String ontologyPath, String className) {
		List<String> lstOfProperties = new ArrayList<String>();
		OntClass classObj = this.ontModel.getOntClass(ontologyPath + "#"
				+ className);
		ExtendedIterator<OntProperty> lstOfPro = classObj.listDeclaredProperties(true);
		while (lstOfPro.hasNext()) {
			lstOfProperties.add(lstOfPro.next().getLocalName().trim());
		}
		return lstOfProperties;
	}
	
	@Override
	public Object executeQueryService(String query){
		String errorMsg;
		try{
			QueryExecution queryExec= QueryExecutionFactory.create(query,this.ontModel);
			ResultSet rs=queryExec.execSelect();
			List<QuerySolution> lstOfQuerySol=ResultSetFormatter.toList(rs);
			return lstOfQuerySol;
		}catch(Exception e){
			errorMsg=e.getMessage();
			return errorMsg;
		}
	}


	@Override
	public Map<String, List<RDFNode>> queryModelWithResultService(ResultSet results,
			List<String> queryVariables) {
		Map<String, List<RDFNode>> solution = new HashMap<String, List<RDFNode>>();
		while(results.hasNext()) {		
			QuerySolution sol = results.next();		
			for(String variable : queryVariables) {	

				RDFNode nodeVar = sol.get(variable);
				if(nodeVar!=null){
					if(solution.get(variable)==null)
						solution.put(variable, new ArrayList<RDFNode>());					
					solution.get(variable).add(nodeVar);
				}
			}
			log.println(solution.toString() +"\n");				
		}
		if(solution.isEmpty())
			solution = null;
		return solution;
	}


}
