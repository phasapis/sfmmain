package eu.sifem.mb.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import eu.sifem.mb.entitybean.ProjectSimulationEB;
import eu.sifem.model.to.SolverResultXYGraphTO;
import eu.sifem.service.IPakSolverControlerService;


/**
 * 
 * @author Yasar Khan/ jbjares
 *
 */

@ManagedBean(name = "chartViewController")
@ViewScoped
public class ChartViewController extends GenericMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3759949443801265128L;

	private LineChartModel lineModel;
	
	@ManagedProperty(value = "#{pakSolverControlerService}")
	private IPakSolverControlerService pakSolverControlerService;
	
	@ManagedProperty(value = "#{projectSimulationEB}")
	private ProjectSimulationEB projectSimulationEB;
	
	private String projectID;
	
	private String simulationID;
	
	private SolverResultXYGraphTO solverResultXYGraphTO;

	@PostConstruct
    public void init() {
        try {
			createLineModels();
		} catch (Exception e) {
			addErrorMessage("Error!",e.getMessage());
		}
    }

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}
	
	
	private void createLineModels() {
        try {
			lineModel = initLinearModel();
			lineModel.setTitle("Linear Chart");
			lineModel.setLegendPosition("e");
			Axis yAxis = lineModel.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax(10);
			
//			lineModel.setTitle("Pesquisa de Opinião"); 
//			lineModel.setLegendPosition("e"); 
//			lineModel.setShowPointLabels(true); 
//			lineModel.getAxes().put(AxisType.X, new CategoryAxis("Pesquisas")); 
//			lineModel.setZoom(true); 
//			Axis yAxis = lineModel.getAxis(AxisType.Y); 
//			yAxis.setLabel("% de votos"); 
//			yAxis.setMin(0); 
//			yAxis.setMax(100);
			
		} catch (Exception e) {
			addErrorMessage("Error!",e.getMessage());
		}
         
    }
	
	
	private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
         
        return model;
    }

	private LineChartModel initBVMLinearModel(HashMap<Double, Double> xyMap) {
        LineChartModel model = new LineChartModel();
        try {
			model.setTitle("Modal BM Velocity Magnitude vs. X Coord: f = 1000.00 Hz");
			model.setLegendPosition("e");
			model.setShowPointLabels(true);	
			LineChartSeries series1 = new LineChartSeries();
			series1.setLabel("Modal BM Velocity Magnitude vs. X Coord");
			
			model.getAxes().put(AxisType.X, new CategoryAxis("Coord[mm]"));
			model.setZoom(true);
			
			Axis yAxis = model.getAxis(AxisType.Y); 
			yAxis.setLabel("V_bm[dB]"); 
			//yAxis.setMin(0); 
			//yAxis.setMax(100);

 
			Set<Double> keySet = xyMap.keySet();
			Iterator<Double> keyIter = keySet.iterator();
			while(keyIter.hasNext()) {
				Double x = keyIter.next();
				Double y = xyMap.get(x);
				series1.set(x, y);
			}
 
			model.addSeries(series1);
		} catch (Exception e) {
			addErrorMessage("Error!",e.getMessage());
		}
         
        return model;
    }

	private LineChartModel initBVPLinearModel(HashMap<Double, Double> xyMap) {
        LineChartModel model = new LineChartModel();
        try {
			model.setTitle("Modal BM Velocity Phase vs. X Coord: f = 1000.00 Hz");
			model.setLegendPosition("e");
			model.setShowPointLabels(true);	
			LineChartSeries series1 = new LineChartSeries();
			series1.setLabel("Modal BM Velocity Phase vs. X Coord");
			
			model.getAxes().put(AxisType.X, new CategoryAxis("Coord[mm]"));
			model.setZoom(true);
			
			Axis yAxis = model.getAxis(AxisType.Y); 
			yAxis.setLabel("V_bm[Cycles]"); 
			//yAxis.setMin(0); 
			//yAxis.setMax(100);

 
			Set<Double> keySet = xyMap.keySet();
			Iterator<Double> keyIter = keySet.iterator();
			while(keyIter.hasNext()) {
				Double x = keyIter.next();
				Double y = xyMap.get(x);
				series1.set(x, y);
			}
 
			model.addSeries(series1);
		} catch (Exception e) {
			addErrorMessage("Error!",e.getMessage());
		}
         
        return model;
    }	
	
	private LineChartModel initPRPLinearModel(HashMap<Double, Double> xyMap) {
        LineChartModel model = new LineChartModel();
        try {
			model.setTitle("Real part of pressure vs. X Coord: f = 1000.00 Hz");
			model.setLegendPosition("e");
			model.setShowPointLabels(true);	
			LineChartSeries series1 = new LineChartSeries();
			series1.setLabel("Pressure vs. X Coord");
			
			model.getAxes().put(AxisType.X, new CategoryAxis("Coord[mm]"));
			model.setZoom(true);
			
			Axis yAxis = model.getAxis(AxisType.Y); 
			yAxis.setLabel("Pressure [Pa]"); 
			//yAxis.setMin(0); 
			//yAxis.setMax(100);

 
			Set<Double> keySet = xyMap.keySet();
			Iterator<Double> keyIter = keySet.iterator();
			while(keyIter.hasNext()) {
				Double x = keyIter.next();
				Double y = xyMap.get(x);
				series1.set(x, y);
			}
 
			model.addSeries(series1);
		} catch (Exception e) {
			addErrorMessage("Error!",e.getMessage());
		}
         
        return model;
    }

	
	private LineChartModel initPIPLinearModel(HashMap<Double, Double> xyMap) {
        LineChartModel model = new LineChartModel();
        try {
			model.setTitle("Imaginary part of pressure vs. X Coord: f = 1000.00 Hz");
			model.setLegendPosition("e");
			model.setShowPointLabels(true);	
			LineChartSeries series1 = new LineChartSeries();
			series1.setLabel("Pressure vs. X Coord");
			
			model.getAxes().put(AxisType.X, new CategoryAxis("Coord[mm]"));
			model.setZoom(true);
			
			Axis yAxis = model.getAxis(AxisType.Y); 
			yAxis.setLabel("Pressure[Pa]"); 
			//yAxis.setMin(0); 
			//yAxis.setMax(100);

 
			Set<Double> keySet = xyMap.keySet();
			Iterator<Double> keyIter = keySet.iterator();
			while(keyIter.hasNext()) {
				Double x = keyIter.next();
				Double y = xyMap.get(x);
				series1.set(x, y);
			}
 
			model.addSeries(series1);
		} catch (Exception e) {
			addErrorMessage("Error!",e.getMessage());
		}
         
        return model;
    }
	
	private LineChartModel initCLLinearModel(HashMap<Double, Double> xyMap) {
        LineChartModel model = new LineChartModel();
        try {
			model.setTitle("Centerline position: f = 1000.00 Hz");
			model.setLegendPosition("e");
			model.setShowPointLabels(true);	
			LineChartSeries series1 = new LineChartSeries();
			series1.setLabel("Magnitude");
			
			model.getAxes().put(AxisType.X, new CategoryAxis("Coord[mm]"));
			model.setZoom(true);
			
			Axis yAxis = model.getAxis(AxisType.Y); 
			yAxis.setLabel("Magnitude Max [e1 mm]"); 
			//yAxis.setMin(0); 
			//yAxis.setMax(100);

 
			Set<Double> keySet = xyMap.keySet();
			Iterator<Double> keyIter = keySet.iterator();
			while(keyIter.hasNext()) {
				Double x = keyIter.next();
				Double y = xyMap.get(x);
				series1.set(x, y);
			}
 
			model.addSeries(series1);
		} catch (Exception e) {
			addErrorMessage("Error!",e.getMessage());
		}
         
        return model;
    }
	
	private LineChartModel initLinearTestModel(HashMap<Double, Double> xyMap) {
        LineChartModel model = new LineChartModel();
        try {
			model.setTitle("Linear Chart");
			model.setLegendPosition("e");
 
			LineChartSeries series1 = new LineChartSeries();
			series1.setLabel("Series 1");
 
			Set<Double> keySet = xyMap.keySet();
			Iterator<Double> keyIter = keySet.iterator();
			while(keyIter.hasNext()) {
				Double x = keyIter.next();
				Double y = xyMap.get(x);
				series1.set(x, y);
			}
			
			
 
			model.addSeries(series1);
		} catch (Exception e) {
			addErrorMessage("Error!",e.getMessage());
		}
         
        return model;
    }
	
	public void plotChartActionListner(AjaxBehaviorEvent event) throws Exception {
		String buttonID = event.getComponent().getId();
		HashMap<Double, Double> xyMap = new HashMap<Double, Double>();
		
		this.setProjectID(projectSimulationEB.getProjectSimulationTO().getProjectSimulationID());
		this.setSimulationID(projectSimulationEB.getProjectSimulationTO().getSimulationID());
		solverResultXYGraphTO = pakSolverControlerService.showResultGraphs(this.getProjectID(),this.getSimulationID(),Boolean.TRUE);
		
		try {
				HashMap<Double, Double> xyMapTest = new HashMap<Double, Double>();
				
				if(buttonID.equals(new String("test"))) {
					
					xyMapTest.put(1.0, 8.0);
					xyMapTest.put(2.0, 9.5);
					xyMapTest.put(3.2, 3.2);
					xyMapTest.put(4.0, 10.0);
					xyMapTest.put(5.0, 1.0);
					lineModel = initLinearTestModel(xyMapTest);
				return;
				
			} 
			if(buttonID.equals(new String("bvm"))) {
			
				for (Map.Entry<Double, Double> entry : solverResultXYGraphTO.getVmagnTO().getXyMap().entrySet()) {
					xyMap.put( entry.getKey(),entry.getValue());
				}
				lineModel = initBVMLinearModel(xyMap);
				return;
				
			} 
			if(buttonID.equals(new String("bvp"))) {
				
				for (Map.Entry<Double, Double> entry : solverResultXYGraphTO.getVphaseTO().getXyMap().entrySet()) {
					xyMap.put( entry.getKey(),entry.getValue());
				}
				lineModel = initBVPLinearModel(xyMap);
				return;	
			} 
			if(buttonID.equals(new String("prp"))) {
				
				for (Map.Entry<Double, Double> entry : solverResultXYGraphTO.getPrealTO().getXyMap().entrySet()) {
					xyMap.put( entry.getKey(),entry.getValue());
				}
				lineModel = initPRPLinearModel(xyMap);
				return;
			} if(buttonID.equals(new String("pip"))) {
				for (Map.Entry<Double, Double> entry : solverResultXYGraphTO.getPimagTO().getXyMap().entrySet()) {
					xyMap.put( entry.getKey(),entry.getValue());
				}
				lineModel = initPIPLinearModel(xyMap);
				return;
			}if(buttonID.equals(new String("cl"))) {
				for (Map.Entry<Double, Double> entry : solverResultXYGraphTO.getCenterlineTO().getXyMap().entrySet()) {
					xyMap.put( entry.getKey(),entry.getValue());
				}
				
				lineModel = initCLLinearModel(xyMap);
				return;
			}
		} catch (Exception e) {
			addErrorMessage("Error!",e.getMessage());
		}
	}

	public IPakSolverControlerService getPakSolverControlerService() {
		return pakSolverControlerService;
	}

	public void setPakSolverControlerService(
			IPakSolverControlerService pakSolverControlerService) {
		this.pakSolverControlerService = pakSolverControlerService;
	}

	public SolverResultXYGraphTO getSolverResultXYGraphTO() {
		return solverResultXYGraphTO;
	}

	public void setSolverResultXYGraphTO(SolverResultXYGraphTO solverResultXYGraphTO) {
		this.solverResultXYGraphTO = solverResultXYGraphTO;
	}

	public ProjectSimulationEB getProjectSimulationEB() {
		return projectSimulationEB;
	}

	public void setProjectSimulationEB(ProjectSimulationEB projectSimulationEB) {
		this.projectSimulationEB = projectSimulationEB;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getSimulationID() {
		return simulationID;
	}

	public void setSimulationID(String simulationID) {
		this.simulationID = simulationID;
	}

	
	
}
