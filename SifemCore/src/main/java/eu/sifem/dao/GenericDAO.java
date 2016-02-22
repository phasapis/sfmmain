package eu.sifem.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.update.GraphStore;
import com.hp.hpl.jena.update.GraphStoreFactory;
import com.hp.hpl.jena.update.UpdateAction;

import eu.sifem.service.IResourceInjectionService;
/**
 * 
 * @author jbjares
 * 
 */

//TODO rewrite or remove
@Service(value="DAOService")
public class GenericDAO{
	
	
	@Autowired
	protected IResourceInjectionService resourceInjectionService;
	

	public String removeLine(String str, int i) {
		try{
			StringBuilder result = new StringBuilder(); 
			BufferedReader reader = new BufferedReader(new StringReader(str));
			String line = null;
			while ((line = reader.readLine()) != null)  {  
				if(!line.contains("?"+i)){
					result.append(line);
				}
				
			} 
			return result.toString();
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}
		
	}


	
	protected void execute(String insertSparul) throws Exception{
        GraphStore graphstore = null;
        File graphStoreFile = resourceInjectionService.getDatasetPathCompletePathAndFile().getFile();
        if(graphStoreFile.length()==0){
        	graphstore = GraphStoreFactory.create();
        }else{
    		Path input = Paths.get(resourceInjectionService.getDatasetBasePath().getFile().getPath(), resourceInjectionService.getDatasetPathCompletePathAndFile().getFile().getName()); 
    		DatasetGraph dataset = RDFDataMgr.loadDataset(input.toUri().toString(), Lang.NQ).asDatasetGraph();
    		graphstore = GraphStoreFactory.create(dataset);
        }
		UpdateAction.parseExecute(insertSparul, graphstore);
		RDFDataMgr.write(new FileOutputStream(resourceInjectionService.getDatasetPathCompletePathAndFile().getFile()), graphstore, Lang.NQ);
	}
	
	
	protected boolean executeASK(String ASKSparql) throws Exception{
        GraphStore graphstore = null;
        File graphStoreFile = resourceInjectionService.getDatasetPathCompletePathAndFile().getFile();
        if(graphStoreFile.length()==0){
        	graphstore = GraphStoreFactory.create();
        }else{
    		Path input = Paths.get(resourceInjectionService.getDatasetBasePath().getFile().getPath(), resourceInjectionService.getDatasetPathCompletePathAndFile().getFile().getName()); 
    		DatasetGraph dataset = RDFDataMgr.loadDataset(input.toUri().toString(), Lang.NQ).asDatasetGraph();
    		graphstore = GraphStoreFactory.create(dataset);
        }
        
        Query query = QueryFactory.create(ASKSparql) ;
        QueryExecution qexec = QueryExecutionFactory.create(query, graphstore.toDataset()) ;
            
        boolean b = qexec.execAsk();
            
        // Close objects
        qexec.close();
        
        return b;
	}
	


	protected Model getModel(){
		Path input = Paths.get(resourceInjectionService.getDatasetBasePath().getFile().getPath(), resourceInjectionService.getDatasetPathCompletePathAndFile().getFile().getName());
		Model model = ModelFactory.createDefaultModel();
		if(input.toFile().length()!=0){
			model = RDFDataMgr.loadModel(input.toUri().toString()) ;			
		}
		return model;
	}
	
	
	protected String removeDisnecessaryDots(String string) {
		if(string.startsWith(";")){
			string = string.replace(";","");			
		}
		if(string.endsWith(";")){
			string = string.substring(0,string.length()-1);
		}
		return string;
	}
	
	
	
	public IResourceInjectionService getResourceInjectionService() {
		return resourceInjectionService;
	}



	public void setResourceInjectionService(
			IResourceInjectionService resourceInjectionService) {
		this.resourceInjectionService = resourceInjectionService;
	}


}
