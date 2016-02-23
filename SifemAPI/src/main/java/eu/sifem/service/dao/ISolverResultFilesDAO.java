package eu.sifem.service.dao;

import eu.sifem.model.to.SolverResultFilesTO;

public interface ISolverResultFilesDAO {

	void insert(SolverResultFilesTO solverResultFilesTO) throws Exception;

	SolverResultFilesTO findByProjectID(String projectID);

	SolverResultFilesTO findByProjectAndSimulationID(String projectID,String simulationID);

}
