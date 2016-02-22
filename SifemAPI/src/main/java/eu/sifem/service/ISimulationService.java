package eu.sifem.service;

import java.io.File;
import java.util.List;

import eu.sifem.model.to.AsyncTripleStoreInsertMessageTO;
import eu.sifem.model.to.ParameterTO;
import eu.sifem.model.to.ProjectSimulationTO;
import eu.sifem.model.to.SessionIndexTO;
import eu.sifem.model.to.SimulationInstanceTO;
import eu.sifem.model.to.TransformationTO;


/**
 * 
 * @author jbjares
 * 
 */
public interface ISimulationService {

	void insertService(ProjectSimulationTO projectSimulationTO) throws Exception;
	
	List<String> getGraphTypesService() throws Exception;

	List<String> getSolverListService() throws Exception;

	List<String> getNumericalMethodPropService() throws Exception;

	//void executeParaViewOutput(String parameter, String basePath) throws Exception;

	List<ParameterTO> findAllParametersService() throws  Exception;

	List<String> getSolverTypelistService(String solverName) throws Exception;

	List<String> getNumMethodAttributesService(String solverName, String solverType)
			throws Exception;

	List<String> getSolverMethodsService(String solverName, String string) throws Exception;

	List<String> findAllParametersByTransformationNameService(String selectedTransformationName)throws Exception;

	List<TransformationTO> findAllTransformationsBySimulationNameService(String simultionName) throws  Exception;

	void updateService(ProjectSimulationTO projectSimulationTO) throws Exception;

	ProjectSimulationTO findByNameService(String name) throws Exception;

	List<String> getParameterNamesService() throws Exception;

	Boolean executeSimulationService(List<File> semantificationFiles,
			SimulationInstanceTO simulationInstanceTO,
			TransformationTO transformationTO) throws Exception;

	void cleanCacheEnvironmentService(SimulationInstanceTO simulationInstanceTO);

	List<String> findAllTempParameters() throws Exception;

//	void run(SimulationInstanceTO simulationInstanceTO,
//			SessionIndexTO sessionIndexTO) throws Exception;

	void runAsync(SimulationInstanceTO simulationInstanceTO,
			SessionIndexTO sessionIndexTO) throws Exception;

	List<AsyncTripleStoreInsertMessageTO> runSync(
			SimulationInstanceTO simulationInstanceTO,
			SessionIndexTO sessionIndexT,
                        String commandLineArgument) throws Exception;

}
