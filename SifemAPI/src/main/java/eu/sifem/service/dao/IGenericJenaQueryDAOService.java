package eu.sifem.service.dao;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.RDFNode;

import eu.sifem.model.to.TransformationSimulationTO;
import eu.sifem.model.to.TransformationTO;

public interface IGenericJenaQueryDAOService {

	List<String> selectAllNamedGraphs();

	Map<String, List<RDFNode>> findGenericGraph(
			TransformationSimulationTO transformationSimulationTO,
			Map<String, String> attributes, List<File> semantificationFiles,
			String projectName, String simulationName, TransformationTO transformationTO) throws Exception;

	Boolean askNamedGraphExistence(String namedgraph);

	Boolean askNamedGraphExistence(List<String> namedGraphs);

}
