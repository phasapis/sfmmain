package eu.sifem.dao;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.Quad;
import com.hp.hpl.jena.update.GraphStore;
import com.hp.hpl.jena.update.GraphStoreFactory;
import com.hp.hpl.jena.update.UpdateAction;

import eu.sifem.data.TurtleFormatRDFWriter;
import eu.sifem.ontologies.FEMSettingsPAK;
import eu.sifem.ontologies.FemOntology;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.utils.BasicFileTools;



@ContextConfiguration(locations={"classpath:SifemCore-applicationContext.xml"})
public class SparulTest extends AbstractTestNGSpringContextTests{

	@Autowired
	private IResourceInjectionService resourceInjectionService;
	
	public void testInsertUNVThroughSPARUL() throws Exception{
		
		/**
		 * bw:BoxModel1Node1
a fem:Node;

fem:hasNumberOfUnusedBlock15Values	2;

fem:hasZCoordinate	0.10000E-02;

fem:hasYCoordinate	0.00000E+00;

fem:hasXCoordinate	0.00000E+00;

fem:hasColour	8;

.
		 */
        String insertString = 
                "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#>"+
                "INSERT DATA { " +
                        "<http://www.sifemontologies.com/ontologies/BoxModel#BoxModel_?0> "+
                        "fem:hasNumberOfUnusedBlock15Values ?1 ; "+
                        "fem:hasZCoordinate ?2 ; "+
                        "fem:hasYCoordinate ?3 ; "+
                        "fem:hasXCoordinate ?4 ; "+
                        "fem:hasColour ?5 ."+
                "} ";
        
		SimplePreparedStatementForSPARQL sps = new SimplePreparedStatementForSPARQL(insertString);
//		sps.setString(0,sps.addQuotationMarks("nome"));
//		sps.setString(1,sps.addQuotationMarks("query"));
//		sps.setString(2,sps.addQuotationMarks("script"));

        GraphStore graphstore = null;
        File graphStoreFile = new File("C:/SifemWindowsResourceFiles/workspace/output/transformations/TransformationsATeste.nt");
        if(graphStoreFile.length()==0){
        	graphstore = GraphStoreFactory.create();
        }else{
    		Path input = Paths.get("C:/SifemWindowsResourceFiles/workspace/output/transformations/", "TransformationsATeste.nt"); 
    		DatasetGraph dataset = RDFDataMgr.loadDataset(input.toUri().toString(), Lang.NQ).asDatasetGraph();
    		graphstore = GraphStoreFactory.create(dataset);
        }
		

		UpdateAction.parseExecute(sps.toString(), graphstore);
		RDFDataMgr.write(new FileOutputStream("C:/SifemWindowsResourceFiles/workspace/output/transformations/TransformationsATeste.nt"), graphstore, Lang.NQ); 

	}
	
	 @Test(groups= { "default" })
	public void testMarggieTranformationSPARQLOverTTL() {
		
		Path input = Paths.get("C:/SifemWindowsResourceFiles/ConceptualModel/PAK/Turtle/test", "turtle_test.ttl");
		Model model = ModelFactory.createDefaultModel();
		if(input.toFile().length()!=0){
			model = RDFDataMgr.loadModel(input.toUri().toString()) ;			
		}
		importData("C:/SifemWindowsResourceFiles/ConceptualModel/PAK/Turtle/test/unv_output_test.ttl", model);
			String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
					+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
					+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
					+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
					+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
					+ "SELECT ?material ?materialType ?node ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ WHERE "
					+ "{ ?material rdf:type fem:Material . "
					+ "?material fem:hasMaterialNumber ?y .    "
					+ "?material pak:hasMaterialSettings ?z .  "
					+ "?z pak:MATTYPE ?materialType. FILTER (?y=1) . "
					+ "?node rdf:type fem:Node .  "
					+ "?node fem:isNodeOf ?subDomain. "
					+ "?subDomain fem:makesUp ?subDomainGroup. "
					+ "?subDomainGroup fem:hasMaterial ?material. "
					+ "?node fem:hasXCoordinate ?xCoord . "
					+ "?node fem:hasYCoordinate ?yCoord . "
					+ "?node fem:hasZCoordinate ?zCoord . "
					+ "?node fem:holdsValueFor ?b . "
					+ "?b rdf:type fem:Translation. "
					+ "?b sim:hasVectorValue ?a . "
					+ "?a sim:isReal true . "
					+ "?a sim:hasVectorXValue "
					+ "?translationX . "
					+ "?a sim:hasVectorYValue ?translationY . "
					+ "?a sim:hasVectorZValue ?translationZ .  } "
					+ "LIMIT 10";
			QueryExecution queryExec= QueryExecutionFactory.create(query,model);
			ResultSet rs=queryExec.execSelect();
			List<QuerySolution> lstOfQuerySol=ResultSetFormatter.toList(rs);
			Assert.assertFalse(lstOfQuerySol.isEmpty());
			
			int i = 0;
			for(QuerySolution qs:lstOfQuerySol){
				if(i==0){
					Assert.assertEquals(qs.toString(), "( ?material = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Material1> ) ( ?materialType = 1 ) ( ?node = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Node923> ) ( ?xCoord = 0.52500E-02 ) ( ?yCoord = 1 ) ( ?zCoord = 1 ) ( ?translationX = -4.07649E-07 ) ( ?translationY = -1.06253E-05 ) ( ?translationZ = 1.09246E-04 )");
				}
				if(i==1){
					Assert.assertEquals(qs.toString(), "( ?material = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Material1> ) ( ?materialType = 1 ) ( ?node = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Node923> ) ( ?xCoord = 0.52500E-02 ) ( ?yCoord = 1 ) ( ?zCoord = -0.66667E-04 ) ( ?translationX = -4.07649E-07 ) ( ?translationY = -1.06253E-05 ) ( ?translationZ = 1.09246E-04 )");
				}
				if(i==2){
					Assert.assertEquals(qs.toString(), "( ?material = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Material1> ) ( ?materialType = 1 ) ( ?node = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Node923> ) ( ?xCoord = 0.52500E-02 ) ( ?yCoord = 0.15000E-03 ) ( ?zCoord = 1 ) ( ?translationX = -4.07649E-07 ) ( ?translationY = -1.06253E-05 ) ( ?translationZ = 1.09246E-04 )");
					
				}
				if(i==3){
					Assert.assertEquals(qs.toString(), "( ?material = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Material1> ) ( ?materialType = 1 ) ( ?node = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Node923> ) ( ?xCoord = 0.52500E-02 ) ( ?yCoord = 0.15000E-03 ) ( ?zCoord = -0.66667E-04 ) ( ?translationX = -4.07649E-07 ) ( ?translationY = -1.06253E-05 ) ( ?translationZ = 1.09246E-04 )");

				}
				if(i==4){
					Assert.assertEquals(qs.toString(), "( ?material = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Material1> ) ( ?materialType = 1 ) ( ?node = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Node923> ) ( ?xCoord = 1 ) ( ?yCoord = 1 ) ( ?zCoord = 1 ) ( ?translationX = -4.07649E-07 ) ( ?translationY = -1.06253E-05 ) ( ?translationZ = 1.09246E-04 )");

				}
				if(i==5){
					Assert.assertEquals(qs.toString(), "( ?material = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Material1> ) ( ?materialType = 1 ) ( ?node = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Node923> ) ( ?xCoord = 1 ) ( ?yCoord = 1 ) ( ?zCoord = -0.66667E-04 ) ( ?translationX = -4.07649E-07 ) ( ?translationY = -1.06253E-05 ) ( ?translationZ = 1.09246E-04 )");
					
				}
				if(i==6){
					Assert.assertEquals(qs.toString(), "( ?material = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Material1> ) ( ?materialType = 1 ) ( ?node = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Node923> ) ( ?xCoord = 1 ) ( ?yCoord = 0.15000E-03 ) ( ?zCoord = 1 ) ( ?translationX = -4.07649E-07 ) ( ?translationY = -1.06253E-05 ) ( ?translationZ = 1.09246E-04 )");
					
				}
				if(i==7){
					Assert.assertEquals(qs.toString(), "( ?material = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Material1> ) ( ?materialType = 1 ) ( ?node = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Node923> ) ( ?xCoord = 1 ) ( ?yCoord = 0.15000E-03 ) ( ?zCoord = -0.66667E-04 ) ( ?translationX = -4.07649E-07 ) ( ?translationY = -1.06253E-05 ) ( ?translationZ = 1.09246E-04 )");
					
				}
				if(i==8){
					Assert.assertEquals(qs.toString(), "( ?material = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Material1> ) ( ?materialType = 1 ) ( ?node = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Node923> ) ( ?xCoord = 0.52500E-02 ) ( ?yCoord = 1 ) ( ?zCoord = 1 ) ( ?translationX = -4.07649E-07 ) ( ?translationY = -1.06253E-05 ) ( ?translationZ = 1.09246E-04 )");
				}
				if(i==9){
					Assert.assertEquals(qs.toString(), "( ?material = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Material1> ) ( ?materialType = 1 ) ( ?node = <http://www.sifemontologies.com/ontologies/BoxModel#BoxModel1Node923> ) ( ?xCoord = 0.52500E-02 ) ( ?yCoord = 1 ) ( ?zCoord = -0.66667E-04 ) ( ?translationX = -4.07649E-07 ) ( ?translationY = -1.06253E-05 ) ( ?translationZ = 1.09246E-04 )");
				}
				System.out.println(qs.toString());
				i++;
			}
			

	}
	
	public void importData(String rdfFile,Model model) {
		System.out.println(rdfFile);
		DatasetGraph dataset = RDFDataMgr.loadDataset(rdfFile, Lang.TURTLE).asDatasetGraph();		
		Iterator<Quad> quads = dataset.find();
		while ( quads.hasNext() ) {
			Quad quad = quads.next();
			Triple triple = quad.asTriple();
			Statement statement = model.asStatement(triple);
			model.add(statement);
		}	
	}

	 @Test(groups= { "default" })
	public void testSimpleSPARUL() throws FileNotFoundException, SQLException {
		
        String insertString = 
                "PREFIX trans: <http://www.semanticweb.org/ontologies/Transformations#> " +
                "INSERT DATA { " +
                        "<http://www.semanticweb.org/ontologies/Transformations#Transformations> trans:name ?0 ; " +
                        "                       trans:query ?1 ; trans:script ?2 . " +
                "} ";
        
		SimplePreparedStatementForSPARQL sps = new SimplePreparedStatementForSPARQL(insertString);
		sps.setString(0,sps.addQuotationMarks("nome"));
		sps.setString(1,sps.addQuotationMarks("query"));
		sps.setString(2,sps.addQuotationMarks("script"));

        GraphStore graphstore = null;
        File graphStoreFile = new File("C:/SifemWindowsResourceFiles/workspace/output/transformations/TransformationsATeste.nt");
        if(graphStoreFile.length()==0){
        	graphstore = GraphStoreFactory.create();
        }else{
    		Path input = Paths.get("C:/SifemWindowsResourceFiles/workspace/output/transformations/", "TransformationsATeste.nt"); 
    		DatasetGraph dataset = RDFDataMgr.loadDataset(input.toUri().toString(), Lang.NQ).asDatasetGraph();
    		graphstore = GraphStoreFactory.create(dataset);
        }
		

		UpdateAction.parseExecute(sps.toString(), graphstore);
		RDFDataMgr.write(new FileOutputStream("C:/SifemWindowsResourceFiles/workspace/output/transformations/TransformationsATeste.nt"), graphstore, Lang.NQ); 


	}
	

	 @Test(groups= { "default" })
	public void testSimpleSPARQLOverNQ() {
		Path input = Paths.get("C:/SifemWindowsResourceFiles/workspace/output/transformations/", "TransformationsATeste.nt");
		Model model = ModelFactory.createDefaultModel();
		if(input.toFile().length()!=0){
			model = RDFDataMgr.loadModel(input.toUri().toString()) ;			
		}
		
			String query = "SELECT ?s WHERE {?s ?p ?o }";
			QueryExecution queryExec= QueryExecutionFactory.create(query,model);
			ResultSet rs=queryExec.execSelect();
			List<QuerySolution> lstOfQuerySol=ResultSetFormatter.toList(rs);
			
			Assert.assertFalse(lstOfQuerySol.isEmpty());

	}

	
	
	 @Test(groups= { "default" })
	public void testSimpleSPARUL2() throws FileNotFoundException, SQLException {
		
        String insertString = 
                "PREFIX trans: <http://www.semanticweb.org/ontologies/Transformations#> " +
                "INSERT DATA { " +
                        "<http://www.semanticweb.org/ontologies/Transformations#Transformations_2> trans:name ?0 ; " +
                        "                       trans:query ?1 ; trans:script ?2 . " +
                "} ";
        
		SimplePreparedStatementForSPARQL sps = new SimplePreparedStatementForSPARQL(insertString);
		sps.setString(0,sps.addQuotationMarks("nome"));
		sps.setString(1,sps.addQuotationMarks("query"));
		sps.setString(2,sps.addQuotationMarks("script"));

        GraphStore graphstore = null;
        File graphStoreFile = new File("C:/SifemWindowsResourceFiles/workspace/output/transformations/TransformationsATeste2.nt");
        if(graphStoreFile.length()==0){
        	graphstore = GraphStoreFactory.create();
        }else{
    		Path input = Paths.get("C:/SifemWindowsResourceFiles/workspace/output/transformations/", "TransformationsATeste2.nt"); 
    		DatasetGraph dataset = RDFDataMgr.loadDataset(input.toUri().toString(), Lang.NQ).asDatasetGraph();
    		graphstore = GraphStoreFactory.create(dataset);
        }
		

		UpdateAction.parseExecute(sps.toString(), graphstore);
		RDFDataMgr.write(new FileOutputStream("C:/SifemWindowsResourceFiles/workspace/output/transformations/TransformationsATeste2.nt"), graphstore, Lang.NQ); 


	}
	
	
	 @Test(groups= { "default" })
	public void testSimpleSPARQLOverNQ2() {
		Path input = Paths.get("C:/SifemWindowsResourceFiles/workspace/output/transformations/", "TransformationsATeste2.nt");
		Model model = ModelFactory.createDefaultModel();
		if(input.toFile().length()!=0){
			model = RDFDataMgr.loadModel(input.toUri().toString()) ;			
		}
		
			String query = "SELECT ?s WHERE {?s ?p ?o }";
			QueryExecution queryExec= QueryExecutionFactory.create(query,model);
			ResultSet rs=queryExec.execSelect();
			List<QuerySolution> lstOfQuerySol=ResultSetFormatter.toList(rs);
			
			for(QuerySolution qSoluction:lstOfQuerySol){
				Assert.assertEquals("( ?s = <http://www.semanticweb.org/ontologies/Transformations#Transformations_2> )", qSoluction.toString());
			}

	}
	
	
	 @Test(groups= { "default" })
	public void testSimpleSPARUL3() throws FileNotFoundException, SQLException {
		
        String insertString = 
                "PREFIX trans: <http://www.semanticweb.org/ontologies/Transformations#> " +
                "INSERT DATA { " +
                        "<http://www.semanticweb.org/ontologies/Transformations#Transformations> trans:name ?0 ; " +
                        "                       trans:query ?1 ; trans:script ?2 . " +
                "} ";
        
		SimplePreparedStatementForSPARQL sps = new SimplePreparedStatementForSPARQL(insertString);
		sps.setString(0,sps.addQuotationMarks("nome"));
		sps.setString(1,sps.addQuotationMarks("query"));
		sps.setString(2,sps.addQuotationMarks("script"));

        GraphStore graphstore = null;
        File graphStoreFile = new File("C:/SifemWindowsResourceFiles/workspace/output/transformations/TransformationsATeste3.nt");
        if(graphStoreFile.length()==0){
        	graphstore = GraphStoreFactory.create();
        }else{
    		Path input = Paths.get("C:/SifemWindowsResourceFiles/workspace/output/transformations/", "TransformationsATeste3.nt"); 
    		DatasetGraph dataset = RDFDataMgr.loadDataset(input.toUri().toString(), Lang.NQ).asDatasetGraph();
    		graphstore = GraphStoreFactory.create(dataset);
        }
		

		UpdateAction.parseExecute(sps.toString(), graphstore);
		RDFDataMgr.write(new FileOutputStream("C:/SifemWindowsResourceFiles/workspace/output/transformations/TransformationsATeste3.nt"), graphstore, Lang.NQ); 

		
        insertString = 
                "PREFIX trans: <http://www.semanticweb.org/ontologies/Transformations#> " +
                "INSERT DATA { " +
                        "<http://www.semanticweb.org/ontologies/Transformations#Transformations_2> trans:name ?0 ; " +
                        "                       trans:query ?1 ; trans:script ?2 . " +
                "} ";
        
		sps = new SimplePreparedStatementForSPARQL(insertString);
		sps.setString(0,sps.addQuotationMarks("nome"));
		sps.setString(1,sps.addQuotationMarks("query"));
		sps.setString(2,sps.addQuotationMarks("script"));

        graphstore = null;
        graphStoreFile = new File("C:/SifemWindowsResourceFiles/workspace/output/transformations/TransformationsATeste3.nt");
        if(graphStoreFile.length()==0){
        	graphstore = GraphStoreFactory.create();
        }else{
    		Path input = Paths.get("C:/SifemWindowsResourceFiles/workspace/output/transformations/", "TransformationsATeste3.nt"); 
    		DatasetGraph dataset = RDFDataMgr.loadDataset(input.toUri().toString(), Lang.NQ).asDatasetGraph();
    		graphstore = GraphStoreFactory.create(dataset);
        }
		

		UpdateAction.parseExecute(sps.toString(), graphstore);
		RDFDataMgr.write(new FileOutputStream("C:/SifemWindowsResourceFiles/workspace/output/transformations/TransformationsATeste3.nt"), graphstore, Lang.NQ); 


	}

	
	 @Test(groups= { "default" })
	public void testSimpleSPARQLOverNQ3() {
		Path input = Paths.get("C:/SifemWindowsResourceFiles/workspace/output/transformations/", "TransformationsATeste2.nt");
		Model model = ModelFactory.createDefaultModel();
		if(input.toFile().length()!=0){
			model = RDFDataMgr.loadModel(input.toUri().toString()) ;			
		}
		
			String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
					+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
					+ "PREFIX trans: <http://www.semanticweb.org/ontologies/Transformations> "
					+ "SELECT DISTINCT ?name ?script ?query "
					+ "WHERE { "
					+ "<http://www.semanticweb.org/ontologies/Transformations#Transformations_2> ?p ?o . "
					+ "<http://www.semanticweb.org/ontologies/Transformations#Transformations_2> <http://www.semanticweb.org/ontologies/Transformations#name> ?name . "
					+ "<http://www.semanticweb.org/ontologies/Transformations#Transformations_2> <http://www.semanticweb.org/ontologies/Transformations#script> ?script . "
					+ "<http://www.semanticweb.org/ontologies/Transformations#Transformations_2> <http://www.semanticweb.org/ontologies/Transformations#query> ?query . "
					+ "} "
					+ " ORDER BY ?name";
			
			QueryExecution queryExec= QueryExecutionFactory.create(query,model);
			ResultSet rs=queryExec.execSelect();
			List<QuerySolution> lstOfQuerySol=ResultSetFormatter.toList(rs);
			
			for(QuerySolution qSoluction:lstOfQuerySol){
				Assert.assertEquals("nome",qSoluction.get("name").toString());
				Assert.assertEquals("query",qSoluction.get("query").toString());
				Assert.assertEquals("script",qSoluction.get("script").toString());
			}

	}
	
	
	@Test(groups= { "default" })
	public void getUnvBlock15SparulQueryTest() throws Exception{
		for(String query : getUnvBlock15SparulQuery()){
			System.out.println(query);
		}

	}
	
	@Test(groups= { "default" })
	public void separateUnvFileTest() throws Exception{
		File unvFile = new File("C:/ArquivosProgramasProjetos/PAK_Latest/PAKC/Pak.unv");
		Map<String,StringBuilder> map = separateUnvFileIntoListString(unvFile);
		for (Map.Entry<String, StringBuilder> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : "
				+ entry.getValue().toString());
			
		}
		
	}
	
	public Map<String,StringBuilder> separateUnvFileIntoListString(File unvFile) throws IOException{
		Map<String,StringBuilder> groups = new TreeMap<String, StringBuilder>();
		StringBuilder sb = new StringBuilder();
		String unvFileAsStr = FileUtils.readFileToString(unvFile);
		List<String> lines = IOUtils.readLines(new StringReader(unvFileAsStr));
		lines.set(0,"");
		lines.set(lines.size()-1,"END");
		
		int minusOneCount = 0;
		int count = 1;
		String firstLineName = "";
		for(String line:lines){
			if("-1".equals(line.trim()) || "END".equals(line)){

				if(minusOneCount==2){
					groups.put(new Integer(count).toString(),sb);
					minusOneCount = 0;
					sb = new StringBuilder();
				}
				minusOneCount++;				
			}else{
				sb.append(line);
				String newline = System.getProperty("line.separator");
				sb.append(newline);
			}
			count++;
		}
		
		//StringBuilder sb = new StringBuilder();
		return groups;
	}
	
	
	@Deprecated
	public void separateUnvFile() throws Exception{
		BufferedReader reader = BasicFileTools.getBufferedReaderFile("C:/ArquivosProgramasProjetos/PAK_Latest/PAKC/Pak.unv");
		StringBuilder sb = new StringBuilder();
		String line = null;
		Boolean closeFile = Boolean.FALSE;
		Boolean isFirstFile = Boolean.TRUE;
		String code = "";
			while((line = reader.readLine()) != null){
				if(reader==null || reader.readLine()==null){
					continue;
				}
				line = line.trim();				
				if(line.equals("-1")){
					if(!isFirstFile){
						line = reader.readLine().trim();
						if(line.equals("-1")){	
							closeFile = Boolean.TRUE;
							line = reader.readLine();
							code = line.trim();
							if("".equals(reader.readLine().trim())){
								closeFile = Boolean.TRUE;
							}
						}						
					}
				}
				File file = null;
				if(!closeFile){
					line = reader.readLine().trim();						
					sb.append(line);
					String newline = System.getProperty("line.separator");
					sb.append(newline);
				}
				if(isFirstFile){
					while("-1".equals(line)){
						line = reader.readLine().trim();
					}
					String[] split = line.split("\\s+");
					if(split.length<7){
						closeFile = Boolean.TRUE;
						isFirstFile = Boolean.FALSE;
					}					
				}
				
				if(closeFile){
					file = new File("C:/ArquivosProgramasProjetos/PAK_Latest/PAKC/Pak_group"+code+".unv");												
					FileUtils.writeStringToFile(file,sb.toString(),Boolean.TRUE);
					sb = new StringBuilder();
					closeFile = Boolean.FALSE;
					continue;
				}
			}
	}
	
	public Set<String> getUnvBlock15SparulQuery() throws Exception{
		Set<String> sparulQueries = new TreeSet<String>();
		BufferedReader reader = BasicFileTools.getBufferedReaderFile("C:/ArquivosProgramasProjetos/PAK_Latest/PAKC/Pak.unv");
		String line = null;

		//int numOf55Blocks = 1;
			while((line = reader.readLine()) != null){
				line = line.trim();				
				if(line.equals("-1")){
					while((line = reader.readLine()) != null){
						line = line.trim();						
						if(line.equals("15")){
							Map<String, String> block15ResourceProps = new HashMap<String, String>();
							block15ResourceProps.put(FEMSettingsPAK.Property.DataProperty.hasUNVBlockIdPartOne,"15");
							while((line = reader.readLine()) != null){
								line = line.trim();
								if(line.equals("-1")){
									break;									
								}
								//System.out.println(line);
								String[] split = line.split("\\s+");
								String count = split[0].trim();
								//String unusedValue1 = split[1].trim();
								//String unusedValue2 = split[2].trim();
								StringBuilder sb = new StringBuilder();
								sb.append("PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> \n");
								sb.append("INSERT DATA { \n");
								sb.append("<http://www.sifemontologies.com/ontologies/BoxModel#BoxModel_Node"+count+"> \n");
								if(split.length<7){
									break;
								}
								String color = split[3].trim();
								String nodeCoordinateX = split[4].trim();
								String nodeCoordinateY = split[5].trim();
								String nodeCoordinateZ = split[6].trim();
	
								sb.append(FemOntology.Property.DataProperty.hasColour.trim()+" "+color+" ;\n");
								sb.append(FemOntology.Property.DataProperty.hasXCoordinate+" "+nodeCoordinateX+" ; \n");
								sb.append(FemOntology.Property.DataProperty.hasYCoordinate+" "+nodeCoordinateY+" ; \n");
								sb.append(FemOntology.Property.DataProperty.hasZCoordinate+" "+nodeCoordinateZ+" ; \n");
								sb.append(FemOntology.Property.DataProperty.hasNumberOfUnusedBlock15Values+" "+String.valueOf(2));
								sb.append(" . } \n\n");
								sparulQueries.add(sb.toString());
								//numOf55Blocks++;
							}

						}
					}
				}
				break;
			}
			System.out.println(line);
			return sparulQueries;
		}


	



}


