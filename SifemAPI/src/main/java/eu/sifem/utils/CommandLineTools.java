package eu.sifem.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;
/**
 * 
 * @author kartik/jbjares
 * 
 */
public class CommandLineTools {
	
	public synchronized static void runBatch(String command) throws IOException, InterruptedException{
		Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = null;
			
			int exitVal = pr.waitFor();
			
			Integer count = 0;
			while((line=input.readLine()) != null) {
				System.out.println("Console started: \n");
				System.out.println("Console line"+count+": "+ line+ "\n");
				System.out.println("Console finished: \n");
				count++;
			}
			if(count.intValue()==0){
				InputStream stderr = pr.getErrorStream();
				InputStreamReader isr = new InputStreamReader(stderr);
				BufferedReader br = new BufferedReader(isr);
				String lineError = null;
				System.out.println("<ERROR>");
				while ( (lineError = br.readLine()) != null){
					System.out.println("Console error started: \n");
					System.out.println("Console error line"+count+": "+ lineError+ "\n");
					System.out.println("Console error finished: \n");
					count++;          	
				}
				System.out.println("</ERROR>");
				exitVal = pr.waitFor();
				System.out.println("Process exitValue: " + exitVal);				
			}

	}

	public synchronized static void runCommand(String command) throws IOException, InterruptedException{
		Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = null;
			
			int exitVal = pr.waitFor();
			
			Integer count = 0;
			while((line=input.readLine()) != null) {
				System.out.println("Console started: \n");
				System.out.println("Console line"+count+": "+ line+ "\n");
				System.out.println("Console finished: \n");
				count++;
			}
			if(count.intValue()==0){
				InputStream stderr = pr.getErrorStream();
				InputStreamReader isr = new InputStreamReader(stderr);
				BufferedReader br = new BufferedReader(isr);
				String lineError = null;
				System.out.println("<ERROR>");
				while ( (lineError = br.readLine()) != null){
					System.out.println("Console error started: \n");
					System.out.println("Console error line"+count+": "+ lineError+ "\n");
					System.out.println("Console error finished: \n");
					count++;          	
				}
				System.out.println("</ERROR>");
				exitVal = pr.waitFor();
				System.out.println("Process exitValue: " + exitVal);				
			}

	}	
	
	public static Map<String,String> runCommandAndGetMap(String command) throws Exception{
		Map<String,String> lineMap = new TreeMap<String,String>();
		Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = null;
			Integer count = 0;
			while((line=input.readLine()) != null) {
				System.out.println("Console started: \n");
				System.out.println("Console line"+count+": "+ line+ "\n");
				lineMap.put(count.toString(),line);
				System.out.println("Console finished: \n");
				count++;
			}
			if(count.intValue()==0){
				InputStream stderr = pr.getErrorStream();
				InputStreamReader isr = new InputStreamReader(stderr);
				BufferedReader br = new BufferedReader(isr);
				String lineError = null;
				System.out.println("<ERROR>");
				while ( (lineError = br.readLine()) != null){
					System.out.println("Console error started: \n");
					System.out.println("Console error line"+count+": "+ lineError+ "\n");
					lineMap.put(count.toString(),lineError);
					System.out.println("Console error finished: \n");
					count++;          	
				}
				System.out.println("</ERROR>");
				int exitVal = pr.waitFor();
				System.out.println("Process exitValue: " + exitVal);				
			}

		return lineMap;
	}	
		
}
