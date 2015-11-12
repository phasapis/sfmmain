package eu.sifem.R;




import org.rosuda.JRI.Rengine;


/**
 * Create link to R
 * @param out Stream to output R messages to, e.g. StdOut: System.out
 */
public class RInterface {

//	
//	public static Rengine engine = null;
//
//	public boolean startR(){	
//		if(engine!=null && engine.isAlive()){
//			return Boolean.TRUE;
//		}
//		
//		if (!engine.versionCheck()) {
//			System.err.println( "** Version mismatch - Java files don't match library version." );
//			return false;
//		} 
//
//
//		System.out.println( "Creating Rengine" );
//		engine = new Rengine( new String[]{"--vanilla"}, false, null);
//		System.out.println( "Rengine created." );
//		
//		System.out.println("Waiting for R");
//		if ( !engine.waitForR() ) {
//			System.out.println( "Cannot load R" );
//			return false;
//		} else {
//			return true;
//		}
//		
//	}
//
//	public void openJavaGD(){
//		engine.eval("Sys.setenv(\"JAVAGD_CLASS_NAME\"=\"eu/sifem/R/RWindow\"");
//		
//		
//		engine.eval( "library(JavaGD)" );
//		engine.eval( "JavaGD(width=1000, height=600, ps=12)" );
//	}
//
//	public void finalize() {
//		if ( engine != null ) {
//			if ( engine.isAlive() ) {
//				engine.idleEval( "q(\"no\")" );
//			}
//			engine.end();
//			engine = null;
//		}
//	}
}
