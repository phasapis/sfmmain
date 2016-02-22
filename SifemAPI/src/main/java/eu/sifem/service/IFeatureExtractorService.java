package eu.sifem.service;

import java.util.List;


/**
 * 
 * @author jbjares
 * 
 */
public interface IFeatureExtractorService {

	void endEngineService();

	String plot2dGFeatService(List<String> datax, String varNamx, List<String> datay,
			String varNamy, String plotName, String basePath) throws Exception;

	int findGlobalMinimaIndexService(List<Double> datax, List<Double> datay);

	int findGlobalMaximaIndexService(List<Double> datax, List<Double> datay);

	Double[] computeSlopesService(String varNamx, String varNamy, int size) throws Exception;

	String convRDataArrayCmdService(List<String> data, String varNam);

}
