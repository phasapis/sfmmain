package eu.deri.dataanalysis.analyser;

import java.util.List;

import eu.deri.dataanalysis.util.DataType;
import eu.deri.dataanalysis.vo.Feature;
import eu.deri.dataanalysis.vo.QualitativeDT;
import eu.deri.dataanalysis.vo.QuantativeDT;

/**
 * @author yaskha/swapnil
 *
 */
public class DataTypeAnalyser implements Analyser{
	 
	DataTypeAnalyser() {
		
	}

	public Object analyse() {
		return null;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Object analyse(Object object) {
		/*It is temporary logic to assigned the data type of each feature
		 * TODO: implement  business logic to classify each feature*/
		
		List<Feature> lstOfFeature=(List<Feature>)object;
//		for(Integer i=0;i<lstOfFeature.size();i++){
//			Feature feature=lstOfFeature.get(i);
//			if(feature.getAttrName().equalsIgnoreCase("Employee Number")){
//				QuantativeDT quant=new QuantativeDT();
//				quant.setInterval(true);
//				quant.setRealValAddptive(true);
//				quant.setUnique(true);
//				feature.setStatDataType(quant);
//			}else if(feature.getAttrName().equalsIgnoreCase("Name")){
//				QualitativeDT qualitative=new QualitativeDT();
//				feature.setStatDataType(qualitative);
//			}else if(feature.getAttrName().equalsIgnoreCase("Department")){
//				QualitativeDT qualitative=new QualitativeDT();
//				qualitative.setNominal(true);
//				qualitative.setCategorical(true);
//				feature.setStatDataType(qualitative);
//			}else if(feature.getAttrName().equalsIgnoreCase("Rank")){
//				QualitativeDT qualitative=new QualitativeDT();
//				qualitative.setOrdinal(true);
//				qualitative.setRealNo(true);
//				feature.setStatDataType(qualitative);
//			}else if(feature.getAttrName().equalsIgnoreCase("Salary")){
//				QuantativeDT quant=new QuantativeDT();
//				quant.setContinious(true);
//				quant.setRatio(true);
//				feature.setStatDataType(quant);
//			}else if(feature.getAttrName().equalsIgnoreCase("Age")){
//				QuantativeDT quant=new QuantativeDT();
//				quant.setRatio(true);
//				quant.setContinious(true);
//				feature.setStatDataType(quant);
//			}else if(feature.getAttrName().equalsIgnoreCase("NumberOf Years in work")){
//				QuantativeDT quant=new QuantativeDT();
//				quant.setContinious(true);
//				quant.setRatio(true);
//				quant.setCount(true);
//				feature.setStatDataType(quant);
//			}else if(feature.getAttrName().equalsIgnoreCase("sequence")){
//				QuantativeDT quant=new QuantativeDT();
//				quant.setContinious(true);
//				quant.setUnique(true);
//				quant.setCount(true);
//				feature.setStatDataType(quant);
//			}else if(feature.getAttrName().equalsIgnoreCase("function")){
//				QuantativeDT quant=new QuantativeDT();
//				quant.setContinious(true);
//				feature.setStatDataType(quant);
//			}else if(feature.getAttrName().equalsIgnoreCase("position")){
//				QuantativeDT quant=new QuantativeDT();
//				quant.setContinious(true);
//				quant.setCount(true);
//				quant.setUnique(true);
//				feature.setStatDataType(quant);
//			}else if(feature.getAttrName().equalsIgnoreCase("frequency")){
//				QuantativeDT quant=new QuantativeDT();
//				quant.setContinious(true);
//				feature.setStatDataType(quant);
//			}else if(feature.getAttrName().equalsIgnoreCase("velocity")){
//				QuantativeDT quant=new QuantativeDT();
//				quant.setContinious(true);
//				feature.setStatDataType(quant);
//			}else if(feature.getAttrName().equalsIgnoreCase("cavity")){
//				QuantativeDT quant=new QuantativeDT();
//				quant.setContinious(true);
//				feature.setStatDataType(quant);
//			} else if(feature.getAttrName().equalsIgnoreCase("x")) {
//				QuantativeDT quant=new QuantativeDT();
//				quant.setContinious(true);
//				quant.setUnique(true);
//				quant.setCount(true);
//			} else if(feature.getAttrName().equalsIgnoreCase("y")) {
//				QuantativeDT quant=new QuantativeDT();
//				quant.setContinious(true);
//				feature.setStatDataType(quant);
//			}
//		}
		
		
		// New business logic to dynamically classify attributes based on their data types 
		for(Integer i=0;i<lstOfFeature.size();i++) {
			Feature feature=lstOfFeature.get(i);
			List<Object> listData = feature.getLstOfData();
			if(DataType.isQuantitative(listData)) {
				QuantativeDT quant=new QuantativeDT();
				quant.setContinuous(DataType.isContinuous());
				quant.setInterval(DataType.isInterval());
				if(i==0 || DataType.isDiscrete()) {
					quant.setCount(true);
				}
				System.out.println("[DA][DT] " + feature.getAttrName() + " Data Type is Quatitative");
				feature.setStatDataType(quant);
			} else {
				QualitativeDT qualitative=new QualitativeDT();
				System.out.println("[DA][DT] " + feature.getAttrName() + " Data Type is Qualitative");
				feature.setStatDataType(qualitative);
			}
		}
		
		return lstOfFeature;
	}
	 
}
