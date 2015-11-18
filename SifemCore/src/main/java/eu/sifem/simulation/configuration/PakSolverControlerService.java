package eu.sifem.simulation.configuration;




import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.sifem.dao.mongo.DatAndUnvSolverDAO;
import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;
import eu.sifem.model.to.DatAndUnvSolverTO;
import eu.sifem.model.to.ProcessTO;
import eu.sifem.model.to.SimulationInstanceTO;
import eu.sifem.service.IPakRDFMapperService;
import eu.sifem.service.IPakSolverControlerService;
import eu.sifem.service.IResourceInjectionService;
import eu.sifem.service.dao.IConfigFileDAOService;
import eu.sifem.service.dao.IDatAndUnvSolverDAOService;
import eu.sifem.service.dao.IProcessDAOService;


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



}