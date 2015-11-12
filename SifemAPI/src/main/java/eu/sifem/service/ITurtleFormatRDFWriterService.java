package eu.sifem.service;

import java.util.List;
import java.util.Map;



/**
 * 
 * @author jbjares
 * 
 */

public interface ITurtleFormatRDFWriterService {

	void addRDF(String resource, String type, List<String> properties);

	void addRDF(String resource, String type,
			Map<String, String> propsWithValues);

	void addRDF(String resource, String type);

	void write(String filePath);

	void addPrefix(String prefix, String uri);

	void addPrefixes(Map<String, String> prefixUriMap);

	void write(String turtleStr, String filePath);


}
