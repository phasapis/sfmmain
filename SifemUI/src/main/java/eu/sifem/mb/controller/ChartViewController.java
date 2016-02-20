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
		} catch (Exception e) {
			addErrorMessage("Error!",e.getMessage());
		}
         
    }
	
	
	private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
         
        return model;
    }
	
	
	private LineChartModel initLinearModel(HashMap<Double, Double> xyMap) {
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
		solverResultXYGraphTO = pakSolverControlerService.showResultGraphs(this.getProjectID(),Boolean.TRUE);
		
		try {
			if(buttonID.equals(new String("bvm"))) {
			
				for (Map.Entry<Double, Double> entry : solverResultXYGraphTO.getVmagnTO().getXyMap().entrySet()) {
					xyMap.put( entry.getKey(),entry.getValue());
				}
				lineModel = initLinearModel(xyMap);
				
			} else if(buttonID.equals(new String("bvp"))) {
				
				for (Map.Entry<Double, Double> entry : solverResultXYGraphTO.getVphaseTO().getXyMap().entrySet()) {
					xyMap.put( entry.getKey(),entry.getValue());
				}
				lineModel = initLinearModel(xyMap);
				
			} else if(buttonID.equals(new String("prp"))) {
				
				for (Map.Entry<Double, Double> entry : solverResultXYGraphTO.getPrealTO().getXyMap().entrySet()) {
					xyMap.put( entry.getKey(),entry.getValue());
				}
				lineModel = initLinearModel(xyMap);
				
			} else if(buttonID.equals(new String("pip"))) {
				for (Map.Entry<Double, Double> entry : solverResultXYGraphTO.getPimagTO().getXyMap().entrySet()) {
					xyMap.put( entry.getKey(),entry.getValue());
				}
				lineModel = initLinearModel(xyMap);
				
			} else if(buttonID.equals(new String("cl"))) {
				for (Map.Entry<Double, Double> entry : solverResultXYGraphTO.getCenterlineTO().getXyMap().entrySet()) {
					xyMap.put( entry.getKey(),entry.getValue());
				}
				
				lineModel = initLinearModel(xyMap);
				
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

	
}
