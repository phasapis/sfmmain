package eu.sifem.service;

import org.springframework.core.io.FileSystemResource;


/**
 * 
 * @author jbjares
 * 
 */
public interface IResourceInjectionService{
	

	//

	FileSystemResource getPythonExe();

	void setPythonExe(FileSystemResource pythonExe);
	
	//

	FileSystemResource getDatasetBasePath();

	void setDatasetBasePath(FileSystemResource datasetBasePath);

	
	//
	
	
	void setDatasetPathCompletePathAndFile(FileSystemResource datasetPathCompletePathAndFile);

	FileSystemResource getDatasetPathCompletePathAndFile();
	
	//

	void setOntologyPath(FileSystemResource ontologyPath);

	FileSystemResource getOntologyPath();
	
	//

	FileSystemResource getBlockMeshLocation();

	void setBlockMeshLocation(FileSystemResource blockMeshLocation);

	FileSystemResource getCaseRdfPathContigency();

	void setCaseRdfPathContigency(FileSystemResource caseRdfPathContigency);

	FileSystemResource getCaseRdfPath();

	void setCaseRdfPath(FileSystemResource caseRdfPath);

	void setSimOntologyPath(FileSystemResource simOntologyPath);

	FileSystemResource getSimOntologyPath();

	void setFemOntologyPath(FileSystemResource femOntologyPath);

	FileSystemResource getFemOntologyPath();

	String getShinyVisualizationAppHostName();

	void setShinyVisualizationAppHostName(
			String shinyVisualizationAppHostName);


	String getApplicationServerURL();

	void setApplicationServerURL(
			String applicationServerURL);        
        
}
