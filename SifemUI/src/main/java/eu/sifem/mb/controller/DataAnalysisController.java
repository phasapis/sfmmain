package eu.sifem.mb.controller;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import eu.sifem.mb.entitybean.DataAnalysisEB;
import eu.sifem.mb.entitybean.ProjectSimulationEB;
import eu.sifem.mb.entitybean.VisualisationOutputEB;
import eu.sifem.model.to.DataSetHashCacheTO;
import eu.sifem.model.to.DataSetTO;
import eu.sifem.model.to.ProjectSimulationTO;
import eu.sifem.model.to.ViewTO;
import eu.sifem.service.IDataAnalysisService;
import eu.sifem.service.IVisualizationService;
import eu.sifem.utils.Util;

/**
 * 
 * @author jbjares/yaskha
 *
 */

@ManagedBean(name = "dataAnalysisController")
@ViewScoped
public class DataAnalysisController extends GenericMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9024393986708730226L;
	
	@ManagedProperty(value="#{dataAnalysisEB}")
	private DataAnalysisEB dataAnalysisEB;
	
	@ManagedProperty(value="#{dataAnalysisService}")
	private IDataAnalysisService dataAnalysisService;
	
	@ManagedProperty(value="#{visualizationService}")
	private IVisualizationService visualizationService;
	
	@ManagedProperty(value="#{visualisationOutputEB}")
	private VisualisationOutputEB visualisationOutputEB;
	
	@ManagedProperty(value="#{projectSimulationEB}")
	private ProjectSimulationEB projectSimulationEB;
	
	private Boolean renderSemanticIntepretation = Boolean.FALSE;

	public DataAnalysisEB getDataAnalysisEB() {
		return dataAnalysisEB;
	}

	public void setDataAnalysisEB(DataAnalysisEB dataAnalysisEB) {
		this.dataAnalysisEB = dataAnalysisEB;
	}

	public IDataAnalysisService getDataAnalysisService() {
		return dataAnalysisService;
	}

	public void setDataAnalysisService(IDataAnalysisService dataAnalysisService) {
		this.dataAnalysisService = dataAnalysisService;
	}
	
	public Boolean getRenderSemanticIntepretation() {
		return renderSemanticIntepretation;
	}

	public void setRenderSemanticIntepretation(Boolean renderSemanticIntepretation) {
		this.renderSemanticIntepretation = renderSemanticIntepretation;
	}
		
	public IVisualizationService getVisualizationService() {
		return visualizationService;
	}

	public void setVisualizationService(IVisualizationService visualizationService) {
		this.visualizationService = visualizationService;
	}

	public VisualisationOutputEB getVisualisationOutputEB() {
		return visualisationOutputEB;
	}

	public void setVisualisationOutputEB(VisualisationOutputEB visualisationOutputEB) {
		this.visualisationOutputEB = visualisationOutputEB;
	}

	public ProjectSimulationEB getProjectSimulationEB() {
		return projectSimulationEB;
	}

	public void setProjectSimulationEB(ProjectSimulationEB projectSimulationEB) {
		this.projectSimulationEB = projectSimulationEB;
	}

	public void showSemanticInterpretation() {
		
		try{
			Map<String, List<String>> xyMap = new LinkedHashMap<String, List<String>>();
			
//			List<String> xValues = Arrays
//					.asList(new String[] { "3.499999875202775E-4", "6.99999975040555E-4", "0.0010499999625608325", "0.00139999995008111", "0.0017500000540167093", "0.002099999925121665", "0.0024500000290572643", "0.00279999990016222", "0.0031500000040978193", "0.0035000001080334187", "0.0038499999791383743", "0.00419999985024333", "0.004550000187009573", "0.004900000058114529", "0.005249999929219484", "0.00559999980032444", "0.005950000137090683", "0.006300000008195639", "0.006649999879300594", "0.007000000216066837", "0.007350000087171793", "0.007699999958276749", "0.008050000295042992", "0.00839999970048666", "0.008750000037252903", "0.009100000374019146", "0.009449999779462814", "0.009800000116229057", "0.0101500004529953", "0.010499999858438969", "0.010850000195205212", "0.01119999960064888", "0.011549999937415123", "0.011900000274181366", "0.012249999679625034", "0.012600000016391277", "0.01295000035315752", "0.013299999758601189", "0.013650000095367432", "0.014000000432133675", "0.014349999837577343", "0.014700000174343586", "0.015049999579787254", "0.015399999916553497", "0.01575000025331974", "0.016100000590085983", "0.016450000926852226", "0.01679999940097332", "0.017149999737739563", "0.017500000074505806", "0.01785000041127205", "0.018200000748038292", "0.018549999222159386", "0.01889999955892563", "0.01924999989569187", "0.019600000232458115", "0.019950000569224358", "0.0203000009059906", "0.020649999380111694", "0.020999999716877937", "0.02135000005364418", "0.021700000390410423", "0.022050000727176666", "0.02239999920129776", "0.022749999538064003", "0.023099999874830246", "0.02345000021159649", "0.023800000548362732", "0.024150000885128975", "0.02449999935925007", "0.02484999969601631", "0.025200000032782555", "0.025550000369548798", "0.02590000070631504", "0.026249999180436134", "0.026599999517202377", "0.02694999985396862", "0.027300000190734863", "0.027650000527501106", "0.02800000086426735", "0.028349999338388443", "0.028699999675154686", "0.02905000001192093", "0.029400000348687172", "0.029750000685453415", "0.03009999915957451", "0.03044999949634075", "0.030799999833106995", "0.031150000169873238", "0.03150000050663948", "0.031849998980760574", "0.03220000118017197", "0.03254999965429306", "0.03290000185370445", "0.033250000327825546", "0.03359999880194664", "0.03395000100135803", "0.034299999475479126", "0.03465000167489052", "0.03500000014901161" });
//			List<String> yValues = Arrays
//					.asList(new String[] { "-1.1335900126141496E-5", "-2.81479992736422E-6", "-2.1090299640036392E-7", "-8.018120070119039E-7", "-1.13827002223843E-6", "-1.1322799764457159E-6", "-1.0813299695655587E-6", "-1.0400999599369243E-6", "-9.838649930316024E-7", "-8.997449754133413E-7", "-7.834950110918726E-7", "-6.301700068434002E-7", "-4.33046011494298E-7", "-1.8425200210003823E-7", "1.251169976512756E-7", "5.051670086686499E-7", "9.673469776316779E-7", "1.52454003909952E-6", "2.1911801013629884E-6", "2.983269951073453E-6", "3.918440143024782E-6", "5.0159101192548405E-6", "6.296319952525664E-6", "7.781480235280469E-6", "9.4939596237964E-6", "1.1456299944256898E-5", "1.3690299965674058E-5", "1.6214900824707E-5", "1.9045099179493263E-5", "2.2188200091477484E-5", "2.5641000320320018E-5", "2.9384000299614854E-5", "3.337550151627511E-5", "3.754290082724765E-5", "4.177189839538187E-5", "4.589280069922097E-5", "4.9663598474580795E-5", "5.274869909044355E-5", "5.46947012480814E-5", "5.490050170919858E-5", "5.2586401579901576E-5", "4.676080061472021E-5", "3.619339986471459E-5", "1.9402699763304554E-5", "-5.321689968695864E-6", "-3.9825899875722826E-5", "-8.586829790147021E-5", "-1.4465099957305938E-4", "-2.1591500262729824E-4", "-2.96270998660475E-4", "-3.7631799932569265E-4", "-4.3621400254778564E-4", "-4.4062899542041123E-4", "-3.3780900412239134E-4", "-7.39363968023099E-5", "3.629059938248247E-4", "8.71840980835259E-4", "0.0011949100298807025", "0.0010070799617096782", "1.9946599786635488E-4", "-8.231030078604817E-4", "-0.001258690026588738", "-6.430490175262094E-4", "4.416519950609654E-4", "8.006020216271281E-4", "1.0627500159898773E-4", "-5.48785028513521E-4", "-2.3142099962569773E-4", "3.655680047813803E-4", "1.9294300000183284E-4", "-2.692860143724829E-4", "-1.0017000022344291E-4", "2.1276299958117306E-4", "4.532999810180627E-6", "-1.4635399566031992E-4", "6.521410250570625E-5", "6.179379852255806E-5", "-7.862850179662928E-5", "1.3176900210964959E-5", "3.8941499951761216E-5", "-3.7447898648679256E-5", "7.882300451456103E-6", "1.4364200069394428E-5", "-1.6889700418687426E-5", "7.968040335981641E-6", "1.035979948937893E-6", "-4.80898006571806E-6", "4.207509846310131E-6", "-2.001770099013811E-6", "2.1336799704840814E-7", "5.957489861430076E-7", "-6.666539889010892E-7", "4.427469946222118E-7", "-2.0115500376505224E-7", "5.173010109160714E-8", "1.3835699697040127E-8", "-2.7390800738658072E-8", "2.193950088269503E-8", "-1.1354599926960418E-8", "0.0" });
			
			
			
			
			
			
//			String sparqlView = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
//					+ "PREFIX owl: <http://www.w3.org/2002/07/owl#> "
//					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
//					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
//					+ "PREFIX fem: <http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#> "
//					+ "PREFIX pak: <http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#> "
//					+ "PREFIX sim: <http://www.sifemontologies.com/ontologies/Simulation.owl#> "
//					+ "SELECT ?xCoord ?yCoord ?zCoord ?translationX ?translationY ?translationZ ?ids "
//					+ "WHERE { "
//					+ "?material rdf:type fem:Material . "
//					+ "?material fem:hasMaterialNumber ?y . "
//					+ "?material pak:hasMaterialSettings ?z ."
//					+ " ?z pak:MATTYPE ?materialType. FILTER (?y=1) . "
//					+ "?node rdf:type fem:Node . "
//					+ "?node fem:isNodeOf ?subDomain. "
//					+ "?node fem:hasNodeID ?ids. "
//					+ "?subDomain fem:makesUp ?subDomainGroup. "
//					+ "?subDomainGroup fem:hasMaterial ?material. "
//					+ "?node fem:hasXCoordinate ?xCoord . "
//					+ "?node fem:hasYCoordinate ?yCoord . "
//					+ "?node fem:hasZCoordinate ?zCoord . "
//					+ "?node fem:holdsValueFor ?b . "
//					+ "?b rdf:type fem:Translation. "
//					+ "?b sim:hasVectorValue ?a . "
//					+ "?a sim:isReal true . "
//					+ "?a sim:hasVectorXValue ?translationX . "
//					+ "?a sim:hasVectorYValue ?translationY . "
//					+ "?a sim:hasVectorZValue ?translationZ .  "
//					+ "}";
			
			String xName = visualisationOutputEB.getVisualisationOutputTO().getPlot2DX();
			String yName = visualisationOutputEB.getVisualisationOutputTO().getPlot2DY();
			
			System.out.println("xName: " + xName + " --- yName: " + yName);
			
			//TODO Deprecating..
			//String workspacePath = projectSimulationEB.getProjectSimulationTO().getWorkspace();
			
			String projectName = projectSimulationEB.getProjectSimulationTO().getProjectName();
			
			ProjectSimulationTO projectSimulation = projectSimulationEB.getProjectSimulationTO();
			String simulationName = projectSimulationEB.getProjectSimulationTO().getSimulationName();
			
			//fillDataSetTO(dataSetTO);
			DataSetTO dataSetHashCacheTO =  new DataSetTO(new DataSetHashCacheTO(projectName,simulationName,xName,yName,""),"");
			
			ViewTO viewTO = visualizationService.retrieveDataViewService(dataSetHashCacheTO,null,Collections.EMPTY_LIST,null,projectName,simulationName);
//			if(dataView==null){
//				dataView = transformationService.retrieveDataViewService(transformationTO,projectSimulation.getWorkspace(),projectName,simulationName,xName,yName,null);
//			}
//			
//			ViewTO viewTO = visualizationService.retrieveDataViewService(dataSetTO, null, null, workspacePath, projectName, simulationName);
			
			if(viewTO==null){
				addErrorMessage("Error!","Data not found for the given attributes.");
				return;
			}
			
			if(viewTO.getxView()!=null && !viewTO.getxView().isEmpty() && viewTO.getyView()!=null && !viewTO.getyView().isEmpty()){
				xyMap.put(xName, viewTO.getxView());
				xyMap.put(yName, viewTO.getyView());
			}
			if(viewTO.getDimValMap()!=null && !viewTO.getDimValMap().isEmpty()){
				Map<String,List<String>> xAndYMap = viewTO.getDimValMap();		
				List<String> xValues = xAndYMap.get(xName);
				List<String> yValues = xAndYMap.get(yName);
				
				xyMap.put(xName, xValues);
				xyMap.put(yName, yValues);				
			}
			if(xyMap==null || xyMap.isEmpty()){
				addErrorMessage("Error!","Data not found for the given attributes.");
			}
			
//			System.out.println("***********************************************");
//			System.out.println(xyMap);
//			System.out.println("***********************************************");
			
//			WriteToExcel excelWriter = new WriteToExcel();
//			excelWriter.writeMapToExcel(xyMap);
			
			String outputRDF = dataAnalysisService.extractGraphFeaturesService(xyMap);
//			System.out.println("**************"+outputRDF);
			dataAnalysisEB.getDataAnalysisTO().setOutputRDF(outputRDF);
			renderSemanticIntepretation = Boolean.TRUE;
			
//			Dialog semanticInterpretationDialog = (Dialog) findComponent("semanticInterpretationDisplacementDialogID");
//			semanticInterpretationDialog.setVisible(Boolean.TRUE);
//			RequestContext context = RequestContext.getCurrentInstance();
//			context.update("panel1");
//			PanelGrid panelInterpretationDialog = (PanelGrid) findComponent("panelbuttons");
//			panelInterpretationDialog.
					
		}catch(Exception e){
			addExceptionMessage(e);
		}
		
	}
	
	
	private void fillDataSetTO(DataSetTO dataSetTO)  {
		try {
			DataSetHashCacheTO cache = new DataSetHashCacheTO();
			cache.setProjectName("projectOne");
			cache.setSimulationName("displacement");
			cache.setxName("ids");
			cache.setyName("translationXs");
			cache.setHash(Util.generateHashCacheID(cache));
			dataSetTO.setNamedGraph("http://www.sifemontologies.com/ontologies/BoxModel#"+cache.getProjectName()+"_"+cache.getSimulationName());
			dataSetTO.setCache(cache);
			return;
		} catch (Exception e) {
			addExceptionMessage(e);
		}
	}

	
	

}