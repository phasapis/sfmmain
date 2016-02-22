package eu.sifem.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import au.com.bytecode.opencsv.CSVReader;
import eu.sifem.model.helper.ProjectSimulationPathHelper;
import eu.sifem.model.to.SolverMessageTO;

/**
 * 
 * @author kasooja/jbjares
 */

public class BasicFileTools {

	public static int numberOfLineRead = 0;
	
	public File getImagePath(String workspacePath, String projectName,String simulationName,String transformationName) throws IOException {
		String imagePath = null;
		if(!StringUtils.containsIgnoreCase(workspacePath,projectName)){
			imagePath = workspacePath+"/"+projectName+
			"/"+simulationName+
			"/transformations/img/";
		}else{
			imagePath = workspacePath+
			"/"+simulationName+
			"/transformations/img/";				
		}
		if(imagePath.contains("\\")){
			imagePath = imagePath.replace("\\","/");
		}

		File imageTransformationBasePath = new File(imagePath);
		
		FileUtils.forceMkdir(imageTransformationBasePath);
		String newGraphicFile = imagePath+transformationName+".jpg";
		File generatedFile = new File(newGraphicFile);
		if(generatedFile.exists()){
			generatedFile.delete();
		}
		return generatedFile;
	}

	public static void copyFolder(String src, String dest) {
		File destDir = new File(dest);
		if (!destDir.exists())
			destDir.mkdir();
		try {
			copyFolder(new File(src), destDir);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static void copyFolder(File src, File dest) throws IOException {

		if (src.isDirectory()) {

			// if directory not exists, create it
			if (!dest.exists()) {
				dest.mkdir();
				System.out.println("Directory copied from " + src + "  to "
						+ dest);
			}

			// list all the directory contents
			String files[] = src.list();

			for (String file : files) {
				// construct the src and dest file structure
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// recursive copy
				copyFolder(srcFile, destFile);
			}

		} else {
			// if file, then copy it
			// Use bytes stream to support all file types
			// InputStream in = new FileInputStream(src);
			// OutputStream out = new FileOutputStream(dest);

			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
			System.out.println("File copied from " + src + " to " + dest);
		}
	}

	public static BufferedReader getBufferedReader(FileInputStream inputFile) {
		InputStreamReader streamRead = null;
		BufferedReader read = null;
		try {
			streamRead = new InputStreamReader(inputFile, "UTF8");
			read = new BufferedReader(streamRead);
		} catch (Exception e) {
			numberOfLineRead = 0;
		}
		return read;
	}

	public static BufferedReader getBufferedReader(File file) {
		FileInputStream inputFile = null;
		InputStreamReader streamRead = null;
		BufferedReader read = null;
		try {
			inputFile = new FileInputStream(file.getAbsolutePath());
			streamRead = new InputStreamReader(inputFile, "UTF8");
			read = new BufferedReader(streamRead);
		} catch (Exception e) {
			System.out.println("File Not there probably or yet not created: "
					+ file.getAbsolutePath());
			numberOfLineRead = 0;
		}
		return read;
	}

	public static BufferedReader getBufferedReader(InputStream file) {
		InputStreamReader streamRead = null;
		BufferedReader read = null;
		try {
			// inputFile = new FileInputStream(file.getAbsolutePath());
			streamRead = new InputStreamReader(file, "UTF8");
			read = new BufferedReader(streamRead);
		} catch (Exception e) {
			// System.out.println("File Not there probably or yet not created: "
			// + file.getAbsolutePath());
			numberOfLineRead = 0;
		}
		return read;
	}

	public static BufferedReader getBufferedReaderFile(String filePath)
			throws Exception {
		return getBufferedReader(new FileInputStream(new File(filePath)));
	}

	public static BufferedReader getBufferedReaderFile(FileInputStream fis) {
		return getBufferedReader(fis);
	}

	public static BufferedReader getBufferedReaderFile(InputStream is) {
		return getBufferedReader(is);
	}

	public static String extractTextFromIS(InputStream fileIs) {
		BufferedReader bufferedReader = getBufferedReader(fileIs);
		StringBuffer fileText = new StringBuffer();
		try {
			String line;
			int j = 0;
			while ((line = bufferedReader.readLine()) != null) {
				j++;
				fileText.append(line + "\n");
			}
			numberOfLineRead = j;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		try {
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage(), e);
		}
		return fileText.toString().trim();
	}
	
	public static String extractText(File file) {
		BufferedReader bufferedReader = getBufferedReader(file);
		StringBuffer fileText = new StringBuffer();
		try {
			String line;
			int j = 0;
			while ((line = bufferedReader.readLine()) != null) {
				j++;
				fileText.append(line + "\n");
			}
			numberOfLineRead = j;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		try {
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage(), e);
		}
		return fileText.toString().trim();
	}

	public static String extractText(InputStream file) {
		BufferedReader bufferedReader = getBufferedReader(file);
		StringBuffer fileText = new StringBuffer();
		try {
			String line;
			int j = 0;
			while ((line = bufferedReader.readLine()) != null) {
				j++;
				fileText.append(line + "\n");
			}
			numberOfLineRead = j;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		try {
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage(), e);
		}
		return fileText.toString().trim();
	}

	private static File getFile(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
		} catch (Exception E) {
			System.out.println("Not a File Path or File Not Found");
		}
		return file;
	}

	public static String extractText(String filePath) {
		try {
			if(filePath!=null && !"".equals(filePath) && new File(filePath).isFile()){
				return extractText(getFile(filePath));
			}
			String text = filePath;
			InputStream is = IOUtils.toInputStream(text, "UTF-8");
			return extractText(is);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	public static boolean writeFile(String filePath, String text) {
		File file = new File(filePath);
		Writer output = null;
		boolean written = false;
		try {
			String absolutePath = file.getAbsolutePath();
			output = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(absolutePath), "UTF-8"));
			output.write(text);
			output.close();
			written = true;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return written;
	}

	public static boolean writeFile(File file, String text) {
		Writer output = null;
		boolean written = false;
		try {
			String absolutePath = file.getAbsolutePath();
			output = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(absolutePath), "UTF-8"));
			output.write(text);
			output.close();
			written = true;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return written;
	}

	public static void deleteDirOrFile(String dirOrFilePath) {
		File directory = new File(dirOrFilePath);
		if (!directory.exists()) {
			System.out.println("Directory does not exist.");
			System.exit(0);
		} else {
			try {
				deleteDirOrFile(directory);
			} catch (IOException e) {
				System.err.println(e.getMessage());
				System.exit(0);
			}
		}
	}

	private static void deleteDirOrFile(File fileOrFolder) throws IOException {
		if (fileOrFolder.isDirectory()) {
			// directory is empty, then delete it
			if (fileOrFolder.list().length == 0) {
				fileOrFolder.delete();
				// System.out.println("Directory is deleted : "
				// + fileOrFolder.getAbsolutePath());
			} else {
				// list all the directory contents
				String files[] = fileOrFolder.list();
				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(fileOrFolder, temp);
					// recursive delete
					deleteDirOrFile(fileDelete);
				}
				// check the directory again, if empty then delete it
				if (fileOrFolder.list().length == 0) {
					fileOrFolder.delete();
					// System.out.println("Directory is deleted : "
					// + fileOrFolder.getAbsolutePath());
				}
			}
		} else {
			// if file, then delete it
			fileOrFolder.delete();
			// System.out.println("File is deleted : " +
			// fileOrFolder.getAbsolutePath());
		}
	}

	public static boolean createNewDirectory(String dirPath) {
		boolean success = (new File(dirPath)).mkdir();
		return success;
	}

	public static List<String> listSubDir(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.isDirectory()) {
			System.err.println(dirPath + " is not a directory");
			return null;
		}
		String[] files = dir.list();
		ArrayList<String> filePathList = new ArrayList<String>();

		for (String file : files) {
			if (!file.startsWith("."))
				filePathList.add(dirPath + File.separatorChar + file);
		}

		return filePathList;

	}

	public static String verifyAndConvertFileToString(String str)
			throws Exception {
		File queryFileContent = null;
		if (str == null || "".equals(str)) {
			throw new RuntimeException("Empty or invalid query!");
		}
		if (StringUtils.startsWithIgnoreCase(str, "file")) {
			URI uri = new URI(str);
			queryFileContent = new File(uri);
		}
		if (StringUtils.startsWithIgnoreCase(str, "/C:")) {
			str = StringUtils.removeStartIgnoreCase(str, "/"); // originalQuery.substring(1,originalQuery.indexOf(originalQuery.length()));
			queryFileContent = new File(str);
		}
		if (queryFileContent == null) {
			queryFileContent = new File(str);
		}

		if (queryFileContent.isFile()) {
			return FileUtils.readFileToString(queryFileContent);
		}
		return str;
	}

	public static Boolean isAnEmptyInputStream(InputStream inputStream)
			throws Exception {
		InputStreamReader reader = new InputStreamReader(inputStream);
		Boolean result = Boolean.FALSE;
		if (reader.ready()) {
			result = Boolean.TRUE;
		}
		return result;
	}

	@Deprecated
	public List<String[]> csvReader(InputStream fileName) {
		try {
			List<String[]> data = new ArrayList<String[]>();
			au.com.bytecode.opencsv.CSVReader reader = new au.com.bytecode.opencsv.CSVReader(
					new InputStreamReader(fileName));
			data = reader.readAll();
			return data;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public List<String[]> csvReader(CSVReader reader) {
		try {
			List<String[]> data = new ArrayList<String[]>();
			data = reader.readAll();
			return data;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static File getFileByName(SolverMessageTO solverMessageTO,
			String name) {
		List<ProjectSimulationPathHelper> projectSimulationPathHelperList = new ArrayList<ProjectSimulationPathHelper>(
				solverMessageTO.getProjectSimulationPaths());
		for (ProjectSimulationPathHelper projectSimulationPathHelper : projectSimulationPathHelperList) {
			if (name.equals(projectSimulationPathHelper.getName())) {
				return new File(projectSimulationPathHelper.getCanonicaPath());
			}

		}

		return null;
	}

	public static FileOutputStream readByteStreamToFile(String filePath,
			byte[] bytes) throws Exception {
		FileOutputStream stream = new FileOutputStream(filePath);
		try {
			stream.write(bytes);
		} finally {
			stream.close();
		}
		return stream;
	}

	public static List<String> locateDatasourceTempPath(String workspace,String searchFolder) throws Exception {
		if(workspace==null || "".equals(workspace)){
			return Collections.EMPTY_LIST;
		}
		if(searchFolder==null || "".equals(searchFolder)){
			return Collections.EMPTY_LIST;
		}
		File workspaceFile = new File(workspace);
		if(!workspaceFile.exists()){
			throw new RuntimeException("[ERROR] The workspace was not created for this project. Try again.");
		}
		List<String> dataSourceTempPaths = BasicFileTools.searchDatasourceFolderCanonicalPath(workspaceFile+"\\"+searchFolder);
		return dataSourceTempPaths;
	}
	


	public static List<String> searchForFolderCanonicalPath(File root, String folderSearchName) throws Exception {
		List<String> result = new ArrayList<String>();
		if (root == null || "".equals(root)){
			return Collections.EMPTY_LIST; // just for safety			
		}

		if (root.isDirectory()) {
			for (File file : root.listFiles()){
				searchForFolderCanonicalPath(file,folderSearchName);				
			}
		} else if (root.isDirectory() && StringUtils.equalsIgnoreCase(root.getName(),folderSearchName)) {
			result.add(root.getCanonicalPath());
		}
		
		return result;
	}
	
	private static List<String> searchDatasourceFolderCanonicalPath(String mainPath) throws Exception {
		List<String> result = new ArrayList<String>();
		File mainFile = new File(mainPath);
		for(int i=0;i<1000;i++){
			File subFile = new File(mainFile+"\\instance_"+i);
			if(!subFile.exists() || !subFile.isDirectory()){
				continue;
			}
			for(File ttlFolder:subFile.listFiles()){
				if(!ttlFolder.exists() || !ttlFolder.isDirectory()){
					continue;
				}
				for(File datunvfiles:ttlFolder.listFiles()){
					if(datunvfiles.exists() && datunvfiles.isFile() && datunvfiles.getName().endsWith(".ttl")){
						result.add(datunvfiles.getCanonicalPath());
					}
				}
			}
		}
		return result;
	}
	
	public static InputStream getFileAsMock(byte[] byteArr, String tempoeraryFileSystemLocation) throws Exception{
        if(byteArr!=null ){
        	InputStream file = new ByteArrayInputStream(byteArr);
        	if(!isAnEmptyInputStream(file)){
        		return file;        		
        	}
        }
        
        File fileFSystem = new File(tempoeraryFileSystemLocation);
        if(fileFSystem!=null){
        	InputStream fileIS = new FileInputStream(fileFSystem);        		
	        	if(!isAnEmptyInputStream(fileIS)){
	            	return fileIS;
	        	}
        }
        
        
        
		throw new RuntimeException("File Not found Exception: "+tempoeraryFileSystemLocation); 
	}
	
	public static InputStream getFileAsMock(byte[] byteArr, InputStream tempoeraryFileSystemLocation) throws Exception{
        if(byteArr!=null ){
        	InputStream file = new ByteArrayInputStream(byteArr);
        	if(!isAnEmptyInputStream(file)){
        		return file;        		
        	}
        }
        
        if(tempoeraryFileSystemLocation!=null){
        	return tempoeraryFileSystemLocation;
        }
        
        
        
		throw new RuntimeException("File Not found Exception: "+tempoeraryFileSystemLocation); 
	}
}


