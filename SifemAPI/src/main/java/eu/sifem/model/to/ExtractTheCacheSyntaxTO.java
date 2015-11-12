package eu.sifem.model.to;

import java.util.LinkedList;
import java.util.List;

public class ExtractTheCacheSyntaxTO implements AbstractTO{
	private static final long serialVersionUID = 3279994744899312169L;
	
	private List<MessageFileTO> semantificatedFilesID = new LinkedList<MessageFileTO>();
	
	private DataSetTO dataSetTO = new DataSetTO();
	
	private TransformationTO transformationTO = new TransformationTO();
	
	public List<MessageFileTO> getSemantificatedFilesID() {
		return semantificatedFilesID;
	}

	public void setSemantificatedFilesID(List<MessageFileTO> semantificatedFilesID) {
		this.semantificatedFilesID = semantificatedFilesID;
	}

	public DataSetTO getDataSetTO() {
		return dataSetTO;
	}

	public void setDataSetTO(DataSetTO dataSetTO) {
		this.dataSetTO = dataSetTO;
	}

	public TransformationTO getTransformationTO() {
		return transformationTO;
	}

	public void setTransformationTO(TransformationTO transformationTO) {
		this.transformationTO = transformationTO;
	}
	
	
}
