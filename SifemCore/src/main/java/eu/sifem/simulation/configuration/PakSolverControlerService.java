package eu.sifem.simulation.configuration;




import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
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
	public SolverResultXYGraphTO showResultGraphs(String projectID,String simulationID,Boolean isInsert) throws Exception {
		//SolverResultFilesTO solverResultFilesTO = solverResultFilesDAO.findByProjectID(projectID);
		SolverResultFilesTO solverResultFilesTO = solverResultFilesDAO.findByProjectAndSimulationID(projectID,simulationID);
		SolverResultXYGraphTO  solverResultXYGraphTO = parseStreamsToWrapperObjects(solverResultFilesTO,isInsert,projectID,simulationID);
		return solverResultXYGraphTO;
	}
	
	@Override
	public SolverResultXYGraphTO parseStreamsToWrapperObjects(SolverResultFilesTO solverResultFilesTO,Boolean isInsert, String projectID, String simulationID) throws Exception {
		
		solverResultFilesTO = solverResultFilesDAO.findByProjectAndSimulationID(projectID, simulationID);
//		if(solverResultFilesTO==null || solverResultFilesTO.get_id()==null || 
//				solverResultFilesTO.getDcenterlineFile()==null ||
//				solverResultFilesTO.getPimagFile()==null || 
//				solverResultFilesTO.getPrealFile()==null || 
//				solverResultFilesTO.getVmagnFile()==null || 
//				solverResultFilesTO.getVphaseFile()==null){
//			solverResultFilesTO = new SolverResultFilesTO();
//			
//			InputStream dcenterlineIS = dCenterLineFile(null);
//			InputStream pImagIS = pImagFile(null);
//			InputStream pRealIS = pRealFile(null);
//			InputStream vMagnIS = vMagnFile(null);
//			InputStream vPhaseIS = vPhaseFile(null);
//			
//			solverResultFilesTO.setDcenterlineFile(dcenterlineIS);
//			solverResultFilesTO.setPimagFile(pImagIS);
//			solverResultFilesTO.setPrealFile(pRealIS);
//			solverResultFilesTO.setVmagnFile(vMagnIS);
//			solverResultFilesTO.setVphaseFile(vPhaseIS);
//			
//			if(isInsert){
//				solverResultFilesTO.setProjectID(projectID);
//				solverResultFilesTO.setSimulationID(simulationID);
//				solverResultFilesDAO.insert(solverResultFilesTO);				
//			}
//		}
		//solverResultFilesTO = solverResultFilesDAO.findByProjectID(projectID);
		//String dcenterStr = IOUtils.toString(solverResultFilesTO.getDcenterlineFile(),"UTF-8");
		String dcenterStr = BasicFileTools.extractText(solverResultFilesTO.getDcenterlineFile());
		String pimagStr = BasicFileTools.extractText(solverResultFilesTO.getPimagFile());
		String prealStr = BasicFileTools.extractText(solverResultFilesTO.getPrealFile());
		String vmagnStr = BasicFileTools.extractText(solverResultFilesTO.getVmagnFile());
		String vphaseStr = BasicFileTools.extractText(solverResultFilesTO.getVphaseFile());
		
		CenterlineTO centerlineTO = (CenterlineTO) graphWrapper(dcenterStr.trim(),CenterlineTO.class.getCanonicalName());
		PimagTO pimagTO = (PimagTO) graphWrapper(pimagStr.trim(),PimagTO.class.getCanonicalName());
		PrealTO prealTO = (PrealTO) graphWrapper(prealStr.trim(),PrealTO.class.getCanonicalName());
		VmagnTO vmagnTO = (VmagnTO) graphWrapper(vmagnStr.trim(),VmagnTO.class.getCanonicalName());
		VphaseTO vphaseTO = (VphaseTO) graphWrapper(vphaseStr.trim(),VphaseTO.class.getCanonicalName());

		SolverResultXYGraphTO solverResultXYGraphTO = new SolverResultXYGraphTO();
		solverResultXYGraphTO.setProjectID(solverResultFilesTO.getProjectID());
		
		solverResultXYGraphTO.setCenterlineTO(centerlineTO);
		solverResultXYGraphTO.setPimagTO(pimagTO);
		solverResultXYGraphTO.setPrealTO(prealTO);
		solverResultXYGraphTO.setVmagnTO(vmagnTO);
		solverResultXYGraphTO.setVphaseTO(vphaseTO);
		
		
		return solverResultXYGraphTO;
	}
	
	private Object graphWrapper(String graphStr,String clazz) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Object obj = Class.forName(clazz).newInstance();
		graphStr = graphStr.trim();
		int count = 1;
		List<String> items = Arrays.asList(graphStr.split("\\s*,\\s*"));
		HashMap<Double, Double> xyMap = new HashMap<Double, Double>();
		Double previousKey = null;
		for(String value:items){
			if(!isNum(value)){
				continue;
			}
			Boolean isX = (count%2)==0;
			if(!isX){
				previousKey = Double.parseDouble(value);
				xyMap.put(Double.parseDouble(value),null);
			}else{
				Double previousValue = xyMap.get(previousKey);
				if(previousKey!=null || previousValue==null){
					xyMap.put(previousKey,Double.parseDouble(value));
				}
			}

			count++;
		}
		((Simple2DGraphTO)obj).getXyMap().putAll(xyMap);

		return obj;
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