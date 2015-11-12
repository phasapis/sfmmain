package eu.sifem.featurextraction;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.sifem.R.RInterface;
import eu.sifem.service.IFeatureExtractorService;

@Service(value="featureExtractor")
public class FeatureExtractor implements IFeatureExtractorService{

	private RInterface r;

	public FeatureExtractor(){
		//TODO Deprecated
		//r = new RInterface();
		//r.startR();
		//r.openJavaGD();		
	}	

	@Override
	public String convRDataArrayCmdService(List<String> data, String varNam){
		StringBuffer dataCmdBuf = new StringBuffer();
		dataCmdBuf.append(varNam + " <- c(");
		int i = 1;		
		for(String datum : data){
			String toAppend = null;
			if(i<data.size()) 
				toAppend = datum + ", ";
			else toAppend = datum +"";
			dataCmdBuf.append(toAppend);
			i++;
		}
		dataCmdBuf.append(")");
		return dataCmdBuf.toString().trim();
	}
	
	private String convRDataArrayDoubleCmd(List<Double> data, String varNam){
		StringBuffer dataCmdBuf = new StringBuffer();
		dataCmdBuf.append(varNam + " <- c(");
		int i = 1;		
		for(Double d : data){
			String toAppend = null;
			if(i<data.size()) 
				toAppend = d.toString() + ", ";
			else toAppend = d.toString() +"";
			dataCmdBuf.append(toAppend);
			i++;
		}
		dataCmdBuf.append(")");
		return dataCmdBuf.toString().trim();
	}


	@Override
	public Double[] computeSlopesService(String varNamx, String varNamy, int size) throws Exception{
		Double[] slopes = new Double[size];
		for(int i = 1; i<=size; i++){
			String currentIndex = i + "";
			String nextIndex = i+1 + "";
			if(i==size){
				nextIndex = i-1 + "";
			}
			String slopeCmd = "("+varNamy + "[" + nextIndex +"]-" + varNamy+"[" + currentIndex + "])/(" + varNamx + "[" + nextIndex + "]-" + varNamx + "[" + currentIndex + "])";
			System.out.println(slopeCmd);
			//TODO Deprecated
			//slopes[i-1] = RInterface.engine.eval(slopeCmd).asDouble();
			System.out.println("varx:  " +  slopes[i-1]);
		}
		return slopes;
	}


	@Override
	public int findGlobalMaximaIndexService(List<Double> datax, List<Double> datay){
		int counter = 0;
		int indexMax = 0;
		double max = Double.NEGATIVE_INFINITY;
		for(double y : datay){
			if(y>max){
				max = y;
				indexMax = counter;
			}
			counter++;
		}
		return indexMax;
	}


	@Override
	public int findGlobalMinimaIndexService(List<Double> datax, List<Double> datay){
		int counter = 0;
		int indexMin = 0;
		double min = Double.POSITIVE_INFINITY;
		for(double y : datay){
			if(y<min){
				min = y;
				indexMin = counter;
			}
			counter++;
		}
		return indexMin;
	}

	@Override
	public String plot2dGFeatService(List datax, String varNamx, List datay, String varNamy, String plotName,String basePath){
		String dataType = "";
		if(datax!=null && !datax.isEmpty() && datax.size()>=1){
			dataType = datax.get(0).getClass().toString();
		}
		String dataxCmd = "";
		String datayCmd = "";
		if(dataType.contains("java.lang.String")){
			dataxCmd = convRDataArrayCmdService(datax , varNamx);
			datayCmd = convRDataArrayCmdService(datay, varNamy);			
		}
		if(dataType.contains("java.lang.Double")){
			dataxCmd = convRDataArrayDoubleCmd(datax , varNamx);
			datayCmd = convRDataArrayDoubleCmd(datay, varNamy);			
		}

		String plotCmd = "plot("+ varNamx + "," + varNamy + "," +  "ylab=" + "\""+ varNamy + "\""+ "," + "xlab=" + "\""+ varNamx +"\""+ "," +  "bty=\"n\"," +
				" type=\"o\", main=\"ModelGraph\")"; 
		if(basePath.contains("\\")){
			basePath = basePath.replace("\\","/");
		}
		if(basePath.contains(".jpg")){
			basePath = basePath.replace(".jpg","");
		}
		if(basePath.contains(".jpeg")){
			basePath = basePath.replace(".jpeg","");
		}
		basePath = basePath+"_"+varNamx+"_"+varNamy+".jpg";
		String jpegCmd = "jpeg('" + basePath +"')";
		String devCmd = "dev.off()";		
		
		//TODO Deprecated
//		r.engine.eval(dataxCmd);
//		r.engine.eval(datayCmd);
//		r.engine.eval(jpegCmd);
//		r.engine.eval(plotCmd);
//		r.engine.eval(devCmd);
		return basePath;
//				System.out.println(dataxCmd);
//				System.out.println(datayCmd);
//				System.out.println(jpegCmd);
//				System.out.println(plotCmd);
//				System.out.println(devCmd);
	}
	



	@Override
	public void endEngineService(){
		//TODO Deprecated
		//RInterface.engine.end();
		//System.out.println(RInterface.engine.isAlive());
		//RInterface.engine.end();
	}

	//	public static void main(String[] args) throws Exception {
	//		FeatureExtractor f = new FeatureExtractor();
	//		List<Double> datax = new ArrayList<Double>();
	//		datax.add(0.0);
	//		datax.add(144.321);
	//		datax.add(159.407);
	//		datax.add(178.413);
	//		datax.add(202.557);
	//		List<Double> datay = new ArrayList<Double>();
	//		datay.add(0.0);
	//		datay.add(0.84);
	//		datay.add(0.8925);
	//		datay.add(0.945);
	//		datay.add(0.9975);
	//		int maximaIndex = f.findGlobalMaximaIndex(datax, datay);
	//		System.out.println(maximaIndex);
	//		
	//		
	//		f.plot2dGFeat(datax, "velocity", datay, "distance", "name","C:/SifemWindowsResourceFiles/workspace/output/");
	//	}


}