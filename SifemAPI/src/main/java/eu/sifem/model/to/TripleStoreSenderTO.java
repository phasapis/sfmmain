package eu.sifem.model.to;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TripleStoreSenderTO implements AbstractTO{
	private static final long serialVersionUID = 3279994744899312169L;
	
	private List<MessageFileTO> semantificatedFiles = new LinkedList<MessageFileTO>();
	
	private DataSetTO dataSetTO = new DataSetTO();


	public List<MessageFileTO> getSemantificatedFiles() {
		return semantificatedFiles;
	}

	public void setSemantificatedFiles(List<MessageFileTO> semantificatedFiles) {
		this.semantificatedFiles = semantificatedFiles;
	}

	public DataSetTO getDataSetTO() {
		return dataSetTO;
	}

	public void setDataSetTO(DataSetTO dataSetTO) {
		this.dataSetTO = dataSetTO;
	}
	
}
