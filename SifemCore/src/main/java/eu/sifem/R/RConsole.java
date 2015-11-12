package eu.sifem.R;

import org.rosuda.JRI.RMainLoopCallbacks;

/**
 * Callback class for R interface.
 * @param out Stream to output R messages to, e.g. StdOut: System.out
 */
//TODO: JGR manages to have for example the R "demo()" window functioning, I don't.
public class RConsole {//implements RMainLoopCallbacks {

//	PrintStream out = new PrintStream(System.out);
//
//	public void rWriteConsole( Rengine re, String text, int  oType) {
//		out.println( text );
//	}
//
//	public void rBusy( Rengine re, int which) {
//	}
//
//	public String rReadConsole( Rengine re, String prompt, int addToHistory) {
//		out.print(prompt);
//		try {
//			BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
//			String s = br.readLine();
//			if ( s == null || s.length() == 0 ) return s; else return s + "\n";
//		} catch(Exception e) {
//			out.println( "jriReadConsole exception: " + e.getMessage() );
//		}
//		return null;
//	}
//
//	public void rShowMessage( Rengine re, String message) {
//		out.println( "Error: \"" + message + "\"" );
//	}
//
//	public String rChooseFile( Rengine re, int newFile){
//		String dia1 = null;	
//		int load = -1;
//		if ( newFile == 0 ) dia1 = "Select a file"; else dia1 = "Select a new file";
//		if ( newFile == 0 ) load = FileDialog.LOAD; else load = FileDialog.SAVE ;
//		FileDialog fd = new FileDialog( new Frame(), dia1,load);			
//		fd.setVisible( true );
//		String res = "";
//		if ( fd.getDirectory() != null )
//			res = fd.getDirectory();
//			if ( fd.getFile() != null )
//				if ( res == null ) res = fd.getFile(); else res = ( res + fd.getFile() );
//		return res;
//	}
//
//	public  void rFlushConsole(Rengine re) {
//		out.flush();
//	}
//
//	public void rLoadHistory(Rengine re, String filename) {
//	}
//
//	public void rSaveHistory(Rengine re, String filename ) {
//	}
}