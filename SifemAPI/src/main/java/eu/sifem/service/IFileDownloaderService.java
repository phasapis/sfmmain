package eu.sifem.service;

import java.util.List;

import eu.sifem.model.to.ImageLocationTO;


/**
 * 
 * @author jbjares
 * 
 */
public interface IFileDownloaderService {



	List<ImageLocationTO> setPAKOutputLocationPathNameAndExtService(
			String imageLocation, List<ImageLocationTO> lstOfImgLoc)
			throws Exception;


}
