package eu.sifem.data;

import org.springframework.core.io.FileSystemResource;

import eu.sifem.service.IResourceInjectionService;



/**
 * 
 * @author jbjares
 * 
 */
public  class ResourceInjectionService implements IResourceInjectionService{
    
    private FileSystemResource  femOntologyPath;
    
    private FileSystemResource  simOntologyPath;
    
    private FileSystemResource  caseRdfPath;
    
    private FileSystemResource  caseRdfPathContigency;
    
    private FileSystemResource  blockMeshLocation;

    private FileSystemResource  ontologyPath;
       
    private FileSystemResource datasetPathCompletePathAndFile;
    
    private FileSystemResource datasetBasePath;
    
    private FileSystemResource pythonExe;
    
    private String shinyVisualizationAppHostName;
    
    private String applicationServerURL;
    
    private FileSystemResource legacyWorkspacePath;
    
    
    public FileSystemResource getLegacyWorkspacePath() {
		return legacyWorkspacePath;
	}
	public void setLegacyWorkspacePath(FileSystemResource legacyWorkspacePath) {
		this.legacyWorkspacePath = legacyWorkspacePath;
	}
	@Override
	public String getShinyVisualizationAppHostName() {
		return shinyVisualizationAppHostName;
	}
	@Override
	public void setShinyVisualizationAppHostName(
			String shinyVisualizationAppHostName) {
		this.shinyVisualizationAppHostName = shinyVisualizationAppHostName;
	}
	@Override
    public FileSystemResource getFemOntologyPath() {
		return femOntologyPath;
	}
    @Override
	public void setFemOntologyPath(FileSystemResource femOntologyPath) {
		this.femOntologyPath = femOntologyPath;
	}
	@Override
    public FileSystemResource getSimOntologyPath() {
		return simOntologyPath;
	}
    @Override
	public void setSimOntologyPath(FileSystemResource simOntologyPath) {
		this.simOntologyPath = simOntologyPath;
	}
	@Override
    public FileSystemResource getCaseRdfPath() {
		return caseRdfPath;
	}
    @Override
	public void setCaseRdfPath(FileSystemResource caseRdfPath) {
		this.caseRdfPath = caseRdfPath;
	}
	@Override
    public FileSystemResource getCaseRdfPathContigency() {
		return caseRdfPathContigency;
	}
    @Override
	public void setCaseRdfPathContigency(FileSystemResource caseRdfPathContigency) {
		this.caseRdfPathContigency = caseRdfPathContigency;
	}
	@Override
    public FileSystemResource getBlockMeshLocation() {
		return blockMeshLocation;
	}
    @Override
	public void setBlockMeshLocation(FileSystemResource blockMeshLocation) {
		this.blockMeshLocation = blockMeshLocation;
	}
	@Override
    public FileSystemResource getOntologyPath() {
		return ontologyPath;
	}
    @Override
	public void setOntologyPath(FileSystemResource ontologyPath) {
		this.ontologyPath = ontologyPath;
	}
	@Override
    public FileSystemResource getDatasetPathCompletePathAndFile() {
		return datasetPathCompletePathAndFile;
	}
    @Override
	public void setDatasetPathCompletePathAndFile(
			FileSystemResource datasetPathCompletePathAndFile) {
		this.datasetPathCompletePathAndFile = datasetPathCompletePathAndFile;
	}
	@Override
    public FileSystemResource getPythonExe() {
		return pythonExe;
	}
    @Override
	public void setPythonExe(FileSystemResource pythonExe) {
		this.pythonExe = pythonExe;
	}

    @Override 
	public FileSystemResource getDatasetBasePath() {
		return datasetBasePath;
	}
    @Override 
	public void setDatasetBasePath(FileSystemResource datasetBasePath) {
		this.datasetBasePath = datasetBasePath;
	}

	@Override
	public String getApplicationServerURL() {
		return applicationServerURL;
	}
	@Override
	public void setApplicationServerURL(
			String applicationServerURL) {
		this.applicationServerURL = applicationServerURL;
	}
        

}
