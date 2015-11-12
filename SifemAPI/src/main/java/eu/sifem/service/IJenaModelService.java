package eu.sifem.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.StmtIterator;



/**
 * 
 * @author jbjares
 * 
 */
public interface IJenaModelService {

	OntModel getOntModel();

	PrintWriter getLogWriter();

	void importOntologyService(String ontologyFile, String base);

	void importDataService(String rdfFile);

	void queryModelService(String queryString);

	Map<String, List<RDFNode>> queryModelService(String query,
			List<String> queryVariables);

	Map<String, List<RDFNode>> queryModelService(Map<String, String> prefixes,
			String queryString, List<String> queryVariables);

	String getPrefixStringService(Map<String, String> prefixUriMap);

	StmtIterator constructQueryService(String queryString);

	List<String> getSubclassOfService(String simNameSpace, String graphTypeClass);

	List<String> getPropertiesOfService(String femNameSpace, String nummethodTypeClass);

	Object executeQueryService(String query);

	void createIndividiualService(String ontology, String query)
			throws FileNotFoundException;

	void importDataService(InputStream inputStream) throws IOException;

	Map<String, List<RDFNode>> queryModelWithResultService(ResultSet results,
			List<String> queryVariables);


}
