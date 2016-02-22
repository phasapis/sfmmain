package eu.sifem.service;

import java.util.List;

import eu.sifem.model.to.ViewTO;


/**
 * 
 * @author jbjares
 * 
 */
public interface IDataAnalyzerService {


	void addDataAnalysisInstancesService(IFeatureExtractorService extractor,
			List<ViewTO> views, String experimentNameUri, String femCase,
			String daoDir, String basePath) throws Exception;


}
