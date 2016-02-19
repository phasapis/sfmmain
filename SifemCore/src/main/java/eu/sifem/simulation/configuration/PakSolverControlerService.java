package eu.sifem.simulation.configuration;




import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;
import eu.sifem.model.to.CenterlineTO;
import eu.sifem.model.to.DatAndUnvSolverTO;
import eu.sifem.model.to.PAKCRestServiceTO;
import eu.sifem.model.to.PimagTO;
import eu.sifem.model.to.PrealTO;
import eu.sifem.model.to.ProcessTO;
import eu.sifem.model.to.Simple2DGraphTO;
import eu.sifem.model.to.SimulationInstanceTO;
import eu.sifem.model.to.SolverResultFilesTO;
import eu.sifem.model.to.SolverResultXYGraphTO;
import eu.sifem.model.to.VmagnTO;
import eu.sifem.model.to.VphaseTO;
import eu.sifem.service.IPakRDFMapperService;
import eu.sifem.service.IPakSolverControlerService;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.dao.IConfigFileDAOService;
import eu.sifem.service.dao.IDatAndUnvSolverDAOService;
import eu.sifem.service.dao.IProcessDAOService;
import eu.sifem.service.dao.ISolverResultFilesDAO;
import eu.sifem.utils.BasicFileTools;


/**
 * 
 * @author jbjares
 * 
 */
@Service(value = "pakSolverControlerService")
public class PakSolverControlerService implements IPakSolverControlerService {


	@Autowired
	private IPakRDFMapperService pakRDFMapper;

	@Autowired
	private IResourceInjectionService resourceInjectionService;

	@Autowired
	private IConfigFileDAOService configFileDAO;

	@Autowired
	private IDatAndUnvSolverDAOService datAndUnvSolverDAOService;
	
	@Autowired
	private IProcessDAOService processDAOService;
	
	@Autowired
	private IDatAndUnvSolverDAOService datAndUnvSolverDAO;
	
	@Autowired
	private ISolverResultFilesDAO solverResultFilesDAO;
	
	
	//TODO Deprecating..
//	@Override
//	public void runPakcSolverService(SimulationInstanceTO simulationInstanceTO, SessionIndexTO sessionIndexTO) throws Exception {
//
//		int instanceNumbers = new Util().getInstacesNumberByConfigSessionID(sessionIndexTO.getCfgSessionIdList().get(0));
//	for(int i=0;i<instanceNumbers;i++){
//		String instanceName = "instance_"+i;
//		simulationInstanceTO.setInstanceName(instanceName);
//		configFileDAO.updateInstanceName(simulationInstanceTO.getProjectName(),simulationInstanceTO.getSimulationName(),instanceName);
//		List<InputStream> cfgIs = configFileDAO.findCFGFile(simulationInstanceTO.getProjectName(),simulationInstanceTO.getSimulationName(),instanceName);
//		if(cfgIs==null){
//			cfgIs = configFileDAO.findCFGFile(simulationInstanceTO.getProjectName(),simulationInstanceTO.getSimulationName());
//		}
//		if(cfgIs==null){
//			return;
//		}
//		for(InputStream is: cfgIs){
//			PAKCRestServiceTO result = callService(is);
//			DatAndUnvSolverTO datAndUnvSolverTO = new DatAndUnvSolverTO();
//			datAndUnvSolverTO.setProjectName(simulationInstanceTO.getProjectName());
//			datAndUnvSolverTO.setSimulationName(simulationInstanceTO.getSimulationName());
//			datAndUnvSolverTO.setInstanceName(instanceName);
//
//			InputStream datIs = new ByteArrayInputStream(result.getDatFile());
//			datAndUnvSolverTO.setDatFile(datIs);
//
//			InputStream unvIs = new ByteArrayInputStream(result.getUnvFile());
//			datAndUnvSolverTO.setUnvFile(unvIs);
//			datAndUnvSolverDAOService.insert(datAndUnvSolverTO);
//		}
//	}
	
//			
//
//	}
//
//
//
//	
//	private PAKCRestServiceTO callService(InputStream cfgFileIs) throws Exception {
//		byte[] cfgFileByteArr = IOUtils.toByteArray(cfgFileIs);
//		return callService(cfgFileByteArr);
//	}


	public IPakRDFMapperService getPakRDFMapper() {
		return pakRDFMapper;
	}

	public void setPakRDFMapper(IPakRDFMapperService pakRDFMapper) {
		this.pakRDFMapper = pakRDFMapper;
	}







	@Override
	public List<AsyncTripleStoreInsertMessageTO> semantifyOutputService(SimulationInstanceTO simulationInstanceTO,List<DatAndUnvSolverTO> datAndUnvSolverTOList) throws Exception {
			List<AsyncTripleStoreInsertMessageTO> asyncTripleStoreInsertMessageTOList = new ArrayList<AsyncTripleStoreInsertMessageTO>();
			AsyncTripleStoreInsertMessageTO asyncTripleStoreInsertMessageTO = new AsyncTripleStoreInsertMessageTO();
			List<InputStream> semantificationFiles = new ArrayList<InputStream>();
			
			for(DatAndUnvSolverTO datAndUnvSolverTO:datAndUnvSolverTOList){
				InputStream datIs = datAndUnvSolverDAO.findDatFileByProjectName(datAndUnvSolverTO.getProjectName(),datAndUnvSolverTO.getSimulationName(),datAndUnvSolverTO.getInstanceName());
				InputStream unvIs = datAndUnvSolverDAO.findUnvFileByProjectName(datAndUnvSolverTO.getProjectName(),datAndUnvSolverTO.getSimulationName(),datAndUnvSolverTO.getInstanceName());
					
                                System.err.println(" --- " + datAndUnvSolverTO.getProjectName() + " " + datAndUnvSolverTO.getSimulationName() + " " + datAndUnvSolverTO.getInstanceName());
				//dat
				InputStream datTTLFile = pakRDFMapper.datToRDFService(simulationInstanceTO,datIs);
				semantificationFiles.add(datTTLFile);
				asyncTripleStoreInsertMessageTO.getTripleFiles().put("dat", datTTLFile);
					
				//unv
				InputStream unvTTLFile = pakRDFMapper.unvToRDFService(simulationInstanceTO,unvIs);
				semantificationFiles.add(unvTTLFile);
				asyncTripleStoreInsertMessageTO.getTripleFiles().put("unv", unvTTLFile);
			

				asyncTripleStoreInsertMessageTO.getSemantificationFiles().addAll(semantificationFiles);
				asyncTripleStoreInsertMessageTOList.add(asyncTripleStoreInsertMessageTO);
			}
			
			return asyncTripleStoreInsertMessageTOList;
	}




	@Override
	public void saveOrUpdateProcessStatus(ProcessTO sifemProcess) {
		processDAOService.saveOrUpdate(sifemProcess);
	}
	
	@Override
	public SolverResultXYGraphTO showResultGraphs(String projectID) throws Exception {
		SolverResultFilesTO solverResultFilesTO = solverResultFilesDAO.findByProjectID(projectID);


		
		SolverResultXYGraphTO  solverResultXYGraphTO = parseStreamsToWrapperObjects(solverResultFilesTO);

		return solverResultXYGraphTO;
	}
	
	@Override
	public SolverResultXYGraphTO parseStreamsToWrapperObjects(SolverResultFilesTO solverResultFilesTO) throws Exception {
		if(solverResultFilesTO==null || solverResultFilesTO.get_id()==null || 
				solverResultFilesTO.getDcenterlineFile()==null ||
				solverResultFilesTO.getPimagFile()==null || 
				solverResultFilesTO.getPrealFile()==null || 
				solverResultFilesTO.getVmagnFile()==null || 
				solverResultFilesTO.getVphaseFile()==null){
			solverResultFilesTO = new SolverResultFilesTO();
			solverResultFilesTO.setDcenterlineFile(dCenterLineFile(null));
			solverResultFilesTO.setPimagFile(pImagFile(null));
			solverResultFilesTO.setPrealFile(pRealFile(null));
			solverResultFilesTO.setVmagnFile(vMagnFile(null));
			solverResultFilesTO.setVphaseFile(vPhaseFile(null));
		}
		
		//String dcenterStr = IOUtils.toString(solverResultFilesTO.getDcenterlineFile(),"UTF-8");
		String dcenterStr = BasicFileTools.extractText(solverResultFilesTO.getDcenterlineFile());
		String pimagStr = BasicFileTools.extractText(solverResultFilesTO.getPimagFile());
		String prealStr = BasicFileTools.extractText(solverResultFilesTO.getPrealFile());
		String vmagnStr = BasicFileTools.extractText(solverResultFilesTO.getVmagnFile());
		String vphaseStr = BasicFileTools.extractText(solverResultFilesTO.getVphaseFile());
		
		Simple2DGraphTO centerlineTO = graphWrapper(dcenterStr.trim());
		Simple2DGraphTO pimagTO = graphWrapper(pimagStr.trim());
		Simple2DGraphTO prealTO = graphWrapper(prealStr.trim());
		Simple2DGraphTO vmagnTO = graphWrapper(vmagnStr.trim());
		Simple2DGraphTO vphaseTO = graphWrapper(vphaseStr.trim());

		SolverResultXYGraphTO solverResultXYGraphTO = new SolverResultXYGraphTO();
		solverResultXYGraphTO.setProjectID(solverResultFilesTO.getProjectID());
		
		solverResultXYGraphTO.setCenterlineTO(new CenterlineTO(centerlineTO));
		solverResultXYGraphTO.setPimagTO(new PimagTO(pimagTO));
		solverResultXYGraphTO.setPrealTO(new PrealTO(prealTO));
		solverResultXYGraphTO.setVmagnTO(new VmagnTO(vmagnTO));
		solverResultXYGraphTO.setVphaseTO(new VphaseTO(vphaseTO));
		
		
		return solverResultXYGraphTO;
	}
	
	private Simple2DGraphTO graphWrapper(String graphStr) {
		Simple2DGraphTO simple2DGraphTO = new Simple2DGraphTO();
		graphStr = graphStr.trim();
		int count = 1;
		List<String> items = Arrays.asList(graphStr.split("\\s*,\\s*"));
		
		for(String value:items){
			if(!isNum(value)){
				continue;
			}
			if((count%2)==0){
				simple2DGraphTO.getxView().add(Double.parseDouble(value));
			}else{
				simple2DGraphTO.getyView().add(Double.parseDouble(value));
			}
			
			count++;
		}

		return simple2DGraphTO;
	}

	public static boolean isNum(String strNum) {
	    boolean ret = true;
	    try {

	        Double.parseDouble(strNum);

	    }catch (NumberFormatException e) {
	        ret = false;
	    }
	    return ret;
	}
	
	public static void main(String[] args) throws Exception {
		new PakSolverControlerService().parseStreamsToWrapperObjects(null);
	}

	//TODO could be removed after Panos' service is ready
	public InputStream pImagFile(PAKCRestServiceTO simulationInstance) throws Exception{
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(PIMAG_LOCAL_FILE);
		return BasicFileTools.getFileAsMock(null,in);
	}
	public InputStream dCenterLineFile(PAKCRestServiceTO simulationInstance) throws Exception{
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(DCENTERLINE_LOCAL_FILE);
		return BasicFileTools.getFileAsMock(null,in);
	}
	public InputStream pRealFile(PAKCRestServiceTO simulationInstance) throws Exception{
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(PREAL_LOCAL_FILE);
		return BasicFileTools.getFileAsMock(null,in);
	}
	public InputStream vMagnFile(PAKCRestServiceTO simulationInstance) throws Exception{
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(VMAGN_LOCAL_FILE);
		return BasicFileTools.getFileAsMock(null,in);
	}
	public InputStream vPhaseFile(PAKCRestServiceTO simulationInstance) throws Exception{
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(VPHASE_LOCAL_FILE);
		return BasicFileTools.getFileAsMock(null,in);
	}

	



}