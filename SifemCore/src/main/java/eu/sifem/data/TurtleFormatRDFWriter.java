package eu.sifem.data;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import eu.sifem.service.ITurtleFormatRDFWriterService;
import eu.sifem.utils.BasicFileTools;

//TODO rewrite
@Service(value="turtleFormatRDFWriter")
public class TurtleFormatRDFWriter implements ITurtleFormatRDFWriterService{
	
	private StringBuffer buffer = new StringBuffer();
	
	
	public TurtleFormatRDFWriter(){
		if(buffer!=null){
			buffer = new StringBuffer();
		}
		buffer.append("@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n");
	}
	
	@Override
	public void addRDF(String resource, String type, List<String> properties){
		buffer.append("\n\n" + resource + "\n" + "a" + " " + type + ";\n\n");
		for(String property : properties){
			buffer.append(property + "\n\n");
		}
		buffer.append(".\n\n");
	}
	
	@Override
	public void addRDF(String resource, String type, Map<String, String> propsWithValues){
		buffer.append("\n\n" + resource + "\n" + "a" + " " + type + ";\n\n");
		for(String property : propsWithValues.keySet()){
			String value = propsWithValues.get(property);
			if(value.matches("[-+\\d]*\\."))
				value = value + "0";
			buffer.append(property + "\t" + value + ";" + "\n\n");
		}
		buffer.append(".\n\n");
	}	
	
	@Override
	public void addRDF(String resource, String type){
		buffer.append("\n\n" + resource + "\n" + "a" + " " + type + ";\n\n");
		buffer.append(".\n\n");
	}
	
	@Override
	public void write(String filePath){
		BasicFileTools.writeFile(filePath, buffer.toString().trim());
	}
	
	@Override
	public void write(String turtleStr, String filePath){
		BasicFileTools.writeFile(filePath, turtleStr);
	}
	
	@Override
	public void addPrefix(String prefix, String uri){
		buffer.append("@prefix " +  prefix + ":" +  "<" + uri + "> . "  + "\n");					
	}
	
	@Override
	public void addPrefixes(Map<String, String> prefixUriMap){
		for(String prefix : prefixUriMap.keySet()){
			String uri = prefixUriMap.get(prefix);
			buffer.append("@prefix " +  prefix + ":" +  "<" + uri + "> . "  + "\n");			
		}		
	}
	
}
