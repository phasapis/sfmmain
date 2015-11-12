package eu.sifem.simulation.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.resultset.ResultSetMem;

import eu.sifem.dao.jena.JenaModel;
import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;
import eu.sifem.model.to.DataSetHashCacheTO;
import eu.sifem.model.to.TransformationSimulationTO;
import eu.sifem.service.IIndexingService;
import eu.sifem.service.dao.IDatAndUnvTTLDAOService;
import eu.sifem.service.dao.IDataSetHashCacheDAOService;
import eu.sifem.service.dao.IGenericJenaQueryDAOService;
import eu.sifem.utils.Util;
import java.io.File;

@Configuration
@EnableScheduling
@Service("indexingService")
public class IndexingService implements IIndexingService {

	private static final String SYNC_QUERY = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
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
			+ " } ";

	private Map<String, List<String>> values = new TreeMap<String, List<String>>();
	
	@Autowired
	private IGenericJenaQueryDAOService genericJenaDAOQueryService;
	
	@Autowired
	private IDataSetHashCacheDAOService dataSetHashCacheDAO;
	
	@Autowired
	private IDatAndUnvTTLDAOService datAndUnvTTLDAO;
	
	@Override
	public void indexStreamService(List<AsyncTripleStoreInsertMessageTO> asyncTripleStoreInsertMessageTOList,
			String projectName, String simulationName) throws Exception {

		if(asyncTripleStoreInsertMessageTOList==null || asyncTripleStoreInsertMessageTOList.isEmpty() || 
				"".equals(projectName) || "".equals(simulationName)){
			return;
		}
		int i = 0;
                System.err.println(" --- asyncTripleStoreInsertMessageTOList.size()=" + asyncTripleStoreInsertMessageTOList.size());
                AsyncTripleStoreInsertMessageTO asyncTripleStoreInsertMessageTO = asyncTripleStoreInsertMessageTOList.get(0);
                
		/*for(AsyncTripleStoreInsertMessageTO asyncTripleStoreInsertMessageTO: asyncTripleStoreInsertMessageTOList){
		
			if(asyncTripleStoreInsertMessageTO==null){
				continue;
			}
		*/
			String instanceName = "instance_" + i;
			
			InputStream datIs = datAndUnvTTLDAO.findDatFileByProjectName(projectName,simulationName,instanceName);
			InputStream unvIs = datAndUnvTTLDAO.findUnvFileByProjectName(projectName,simulationName,instanceName);
			
                        /*
                        BufferedReader reader = new BufferedReader(
                                            new InputStreamReader(datIs));
                                    String line = null;
                                    while ((line = reader.readLine()) != null) {
                                        System.out.println(line);
                                    }
                                    reader.close();                        
                        */
                        
                        System.err.println(instanceName + " " + projectName + " " + simulationName);
                        System.err.println(" First Exec " + i);
			JenaModel model = new JenaModel();
			model.importDataService(datIs);
                        System.err.println(" Second Exec " + i);                        
			model.importDataService(unvIs);
			queryIntoTtlFiles(model.getOntModel());
			i++;
		//}
                        
		insertRawIndexedDataIntoCacheDB(projectName, simulationName, instanceName);
		
	}

	private void queryIntoTtlFiles(Model model) throws Exception{
		
		QueryExecution qexec = QueryExecutionFactory.create(SYNC_QUERY, model);	
		ResultSet results = qexec.execSelect();
                
                
		ResultSetMem inMem = new ResultSetMem(results);
                
                System.err.println(" ResultVars=" + inMem.getResultVars());
                
                
		Map<String,String> attributes = new TreeMap<String,String>();
		new Util().fillQueryAttributes(SYNC_QUERY, attributes);
                
                System.err.println(" Sync Query=" + SYNC_QUERY + attributes);
                
		Map<String, List<String>> values = new TreeMap<String, List<String>>();
		
                System.err.println(" ResultVars=" + inMem.getResultVars());                
                
		while(inMem.hasNext()){
			QuerySolution qSolution = inMem.next();
			for(Map.Entry<String,String> entry: attributes.entrySet()){
				if(qSolution.contains(entry.getKey())){
                                    
                                        if(entry.getKey().equals("material") || entry.getKey().equals("node") || entry.getKey().equals("subDomain"))
                                        {
                                            //System.err.println(" --- Key:" + entry.getKey());                                            
                                            //System.err.println(" --- Key:" + entry.getKey() + " - Val:" + qSolution.getResource(entry.getKey()).toString());
                                            new Util().addConvertedValuesToList(entry.getKey(),qSolution.getResource(entry.getKey()).toString(),values);                                            
                                        }
                                        else if(entry.getKey().equals("id") || entry.getKey().equals("xCoord") || entry.getKey().equals("yCoord") || entry.getKey().equals("zCoord") || entry.getKey().equals("xTranslation") || entry.getKey().equals("yTranslation") || entry.getKey().equals("zTranslation") || entry.getKey().equals("materialType"))
                                        {
                                            //System.err.println(" --- Key:" + entry.getKey());
                                            //System.err.println(" - Val:" + qSolution.getLiteral(entry.getKey()).getString());
                                            new Util().addConvertedValuesToList(entry.getKey(),qSolution.getLiteral(entry.getKey()).toString(),values);                                                                                        
                                        }
                                            
				}
			}
		}
                System.err.println(" values.size=" + values.size() + " | " + values.keySet());

		setValues(values);

		
	}
	
	public void insertRawIndexedDataIntoCacheDB(String projectName, String simulationName, String instanceName) throws Exception{
		Map<String, List<String>> values = getValues();
                
                System.err.println(" values.size=" + values.size() + " | " + values.keySet());
                
		DataSetHashCacheTO dataSetHashCacheTO = dataSetHashCacheDAO.insert(projectName,simulationName,instanceName,values);
		Assert.assertNotNull(dataSetHashCacheTO);
		System.out.println(dataSetHashCacheTO.get_id().toString());
	}

	public Map<String, List<String>> getValues() {
		return values;
	}

	public void setValues(Map<String, List<String>> values) {
		this.values = values;
	}
	

}