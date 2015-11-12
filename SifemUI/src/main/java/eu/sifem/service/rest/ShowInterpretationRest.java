package eu.sifem.service.rest;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.sifem.model.to.DataSetHashCacheTO;
import eu.sifem.model.to.DataSetTO;
import eu.sifem.model.to.ViewTO;
import eu.sifem.service.IDataAnalysisService;
import eu.sifem.service.IVisualizationService;

@RestController
@RequestMapping("/semanticInterpretation")
public class ShowInterpretationRest {

	
	@Autowired
	private IVisualizationService visualizationService;
	
	@Autowired
	private IDataAnalysisService dataAnalysisService;

    @RequestMapping(value="/show", method = RequestMethod.GET)
    public String greeting(@RequestParam(value="workspacePath", defaultValue="NONE") String workspacePath,
    						@RequestParam(value="projectName", defaultValue="PROJECTTEST") String projectName,
    						@RequestParam(value="simulationName", defaultValue="SIMULATIONUNITTEST") String simulationName,
    						@RequestParam(value="xName", defaultValue="DISTANCEFROMTHECOCHLEAAPEX") String xName,
    						@RequestParam(value="yName", defaultValue="FREQUENCYATSTAPLES") String yName) throws Exception {
    	String result = showSemanticInterpretation(workspacePath, projectName, simulationName, xName, yName);
    	return result;
    }
    
	public String showSemanticInterpretation(String workspacePath,String projectName,String simulationName,String xName,String yName) {
		String outputRDF = null;
		try{
			Map<String, List<String>> xyMap = new LinkedHashMap<String, List<String>>();
			
			DataSetTO dataSetHashCacheTO =  new DataSetTO(new DataSetHashCacheTO(projectName,simulationName,xName,yName,""),"");
			ViewTO viewTO = visualizationService.retrieveDataViewService(dataSetHashCacheTO,null,Collections.EMPTY_LIST,workspacePath,projectName,simulationName);
			
			if(viewTO==null){
				throw new RuntimeException("Data not found for the given attributes.");
			}
			
			if(viewTO.getxView()!=null && !viewTO.getxView().isEmpty() && viewTO.getyView()!=null && !viewTO.getyView().isEmpty()){
				xyMap.put(xName, viewTO.getxView());
				xyMap.put(yName, viewTO.getyView());
			}
			if(viewTO.getDimValMap()!=null && !viewTO.getDimValMap().isEmpty()){
				Map<String,List<String>> xAndYMap = viewTO.getDimValMap();		

				if(xAndYMap.containsKey(xName) && xAndYMap.containsKey(yName)){
					List<String> xValues = xAndYMap.get(xName);
					List<String> yValues = xAndYMap.get(yName);
					
					xyMap.put(xName, xValues);
					xyMap.put(yName, yValues);
				}
			}
//			if(xyMap==null || xyMap.isEmpty()){
//				throw new RuntimeException("Data not found for the given attributes.");
//			}
			
			if(xyMap.get(xName)==null || "".equals(xyMap.get(xName)) || xyMap.get(yName)==null || "".equals(xyMap.get(yName))){
				viewTO = visualizationService.retrieveDataXandYViewService(dataSetHashCacheTO,Collections.EMPTY_LIST,workspacePath,projectName,simulationName);
			}
			if(xyMap.size()==0 && viewTO!=null && viewTO.getxView()!=null && viewTO.getyView()!=null){
				xyMap.put(xName, viewTO.getxView());
				xyMap.put(yName, viewTO.getyView());
			}
			outputRDF = dataAnalysisService.extractGraphFeaturesService(xyMap);
			outputRDF = outputRDF.replace("&#10;", "</p>");
			outputRDF = "{\"content\": \""+outputRDF+"\"}";
			
		}catch(Exception e){
			outputRDF = "{\"content\": \""+e.getMessage()+"\"}";
		}

		return outputRDF;
		
	}
	    
	
}
