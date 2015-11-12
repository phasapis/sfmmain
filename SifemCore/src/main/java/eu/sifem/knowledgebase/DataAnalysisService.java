package eu.sifem.knowledgebase;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.sifem.service.IDataAnalysisService;
import eu.sifem.service.dao.IDataAnalysisDAOService;
import eu.sifem.service.dao.IRulesDAOService;

/**
 * 
 * @author yaskha
 *
 */

@Service(value="dataAnalysisService")
public class DataAnalysisService implements IDataAnalysisService {

	@Autowired
	private IDataAnalysisDAOService dataAnalysisDAO;
	

	@Override
	public String extractGraphFeaturesService(Map<String, List<String>> xyMap) {
		return dataAnalysisDAO.extractGraphFeaturesService(xyMap);
	}



}
