package eu.sifem.service.dao;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.RDFNode;

import eu.sifem.model.to.TransformationSimulationTO;
import eu.sifem.model.to.TransformationTO;

public interface IVisualizationDAOService {

	Map<String, List<RDFNode>> findTransformationGraphMapGeneric(
			TransformationSimulationTO transformationSimulationTO,
			Map<String, String> attributes) throws Exception;

	InputStream findQueryByProjectAndSimulationName(String transformationName,
			String projectName, String simulationName) throws Exception;

	InputStream findScriptByProjectAndSimulationName(String transformationName,
			String projectName, String simulationName) throws Exception;

	void insert(TransformationTO transformationTO) throws Exception;

}
