package eu.sifem.model.to;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author jbjares
 * 
 */
public class VisualisationOutputTO implements AbstractTO{
	private static final long serialVersionUID = 6921770574437995190L;
	
	private String graphType;
	
	private String plot2DX;
	
	private String plot2DY;
	
	private String plot3DX;
	
	private String plot3DY;
	
	private String plot3DZ;
		
	private String vectorFieldVelocity;
	
	private StreamLine streamLine= new StreamLine();
	
	private List<ImageLocationTO> lstOfImageLoc = new ArrayList<ImageLocationTO>();
	

	public String getPlot3DX() {
		return plot3DX;
	}

	public void setPlot3DX(String plot3dx) {
		plot3DX = plot3dx;
	}

	public String getPlot3DY() {
		return plot3DY;
	}

	public void setPlot3DY(String plot3dy) {
		plot3DY = plot3dy;
	}

	public String getPlot3DZ() {
		return plot3DZ;
	}

	public void setPlot3DZ(String plot3dz) {
		plot3DZ = plot3dz;
	}

	public String getGraphType() {
		return graphType;
	}

	public void setGraphType(String graphType) {
		this.graphType = graphType;
	}


	public String getPlot2DY() {
		return plot2DY;
	}

	public void setPlot2DY(String plot2dy) {
		plot2DY = plot2dy;
	}

	public String getPlot2DX() {
		return plot2DX;
	}

	public void setPlot2DX(String plot2dx) {
		plot2DX = plot2dx;
	}

	public String getVectorFieldVelocity() {
		return vectorFieldVelocity;
	}

	public void setVectorFieldVelocity(String vectorFieldVelocity) {
		this.vectorFieldVelocity = vectorFieldVelocity;
	}

	public StreamLine getStreamLine() {
		return streamLine;
	}

	public void setStreamLine(StreamLine streamLine) {
		this.streamLine = streamLine;
	}

	public List<ImageLocationTO> getLstOfImageLoc() {
		return lstOfImageLoc;
	}

	public void setLstOfImageLoc(List<ImageLocationTO> lstOfImageLoc) {
		this.lstOfImageLoc = lstOfImageLoc;
	}
	
	

}
