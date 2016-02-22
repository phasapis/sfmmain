package eu.sifem.simulation.configuration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import eu.sifem.model.to.ImageLocationTO;
import eu.sifem.model.to.StreamedContentTO;
import eu.sifem.service.IFileDownloaderService;

@Service(value = "fileDownloader")
public class FileDownloader implements IFileDownloaderService {
	
	@Override
	public List<ImageLocationTO> setPAKOutputLocationPathNameAndExtService(String imageLocation, List<ImageLocationTO> lstOfImgLoc) throws Exception {
		ImageLocationTO imageLoc = new ImageLocationTO();
		imageLoc.setLocation(imageLocation);
		imageLoc.setN(0);
		imageLoc.setContent(getStream(imageLoc.getLocation()));
		lstOfImgLoc.add(imageLoc);
		return lstOfImgLoc;
	}
	
	private  StreamedContentTO getStream(String absPath)throws Exception {
		
		File absPathFile = new File(absPath);
		if(!absPathFile.exists() || !absPathFile.isDirectory()){
			absPathFile.createNewFile();
		}
		if (absPath == null || ("".equalsIgnoreCase(absPath))) {
			throw new RuntimeException("Error: Image's absolut path was not defined.");
		}
		FileInputStream fis;
		fis = new FileInputStream(absPathFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		StreamedContentTO content = new StreamedContentTO(bis);
		return content;
	}
	

//	@Override
//	public List<ImageLocationTO> setOutputLocation(String basPath, int len,
//			List<ImageLocationTO> lstOfImgLoc, String extn) throws Exception {
//
//		for (int counter = 0; counter <= len; counter++) {
//			ImageLocationTO imageLoc = new ImageLocationTO();
//			imageLoc.setLocation(basPath + "/" + "Case" + counter + "." + extn);
//			imageLoc.setN(counter);
//			imageLoc.setContent(getStream(imageLoc.getLocation()));
//			lstOfImgLoc.add(imageLoc);
//		}
//		System.out.println(lstOfImgLoc);
//		return lstOfImgLoc;
//	}
//
//	
//	@Override
//	public List<ImageLocationTO> setOutputLocationPath(String basePath,List<ImageLocationTO> lstOfImgLoc, String extn) throws Exception {
//		for (int counter = 0; counter <= lstOfImgLoc.size(); counter++) {
//			ImageLocationTO imageLoc = new ImageLocationTO();
//			imageLoc.setLocation(basePath + "/" + "Case" + counter + "." + extn);
//			imageLoc.setN(counter);
//			imageLoc.setContent(getStream(imageLoc.getLocation()));
//			lstOfImgLoc.add(imageLoc);
//		}
//		System.out.println(lstOfImgLoc);
//		return lstOfImgLoc;
//	}
//	
//	
//	@Override
//	public List<ImageLocationTO> setPAKOutputLocationPathNameAndExt(
//			String basPath, List<ImageLocationTO> lstOfImgLoc,
//			String... fileNameAndExt) throws Exception {
//
//		for (String img:fileNameAndExt) {
//			ImageLocationTO imageLoc = new ImageLocationTO();
//			imageLoc.setLocation(basPath + "\\" + img);
//			imageLoc.setN(0);
//			imageLoc.setContent(getStream(imageLoc.getLocation()));
//			lstOfImgLoc.add(imageLoc);
//		}
//		return lstOfImgLoc;
//	}


//	@Override
//	public List<ImageLocationTO> setPAKOutputLocation(String basPath,
//			List<ImageLocationTO> lstOfImgLoc, String extn) throws Exception {
//
//		// for(int counter=0;counter<=len;counter++){
//		ImageLocationTO imageLoc = new ImageLocationTO();
//		imageLoc.setLocation(basPath + "/" + "pak." + extn);
//		imageLoc.setN(0);
//		imageLoc.setContent(getStream(imageLoc.getLocation()));
//		lstOfImgLoc.add(imageLoc);
//		// }
//		System.out.println(lstOfImgLoc);
//		return lstOfImgLoc;
//	}
//
//	@Override
//	public StreamedContentTO getStream(String absPath)
//			throws Exception {
//		
//		File absPathFile = new File(absPath);
//		if(!absPathFile.exists() || !absPathFile.isDirectory()){
//			absPathFile.createNewFile();
//		}
//		if (absPath == null || ("".equalsIgnoreCase(absPath))) {
//			throw new RuntimeException("Error: Image's absolut path was not defined.");
//		}
//		FileInputStream fis;
//		fis = new FileInputStream(absPathFile);
//		BufferedInputStream bis = new BufferedInputStream(fis);
//		StreamedContentTO content = new StreamedContentTO(bis);
//		return content;
//	}
//	

}