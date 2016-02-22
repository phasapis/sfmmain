package eu.sifem.service.dao;

import java.io.InputStream;
import java.util.List;

import eu.sifem.model.to.AcessoryFileTO;
import eu.sifem.model.to.SolverConfigCreatorTO;


public interface IAcessoryFileDAOService {

	String insert(AcessoryFileTO acessoryFileTO) throws Exception;

	InputStream findAcessoryFile(String projectName,
			String simulationName, String acessoryFileName) throws Exception;


	String updateAcessoryFile(InputStream acessoryFileIS,
			SolverConfigCreatorTO solverConfigCreatorTO, String parameterName, String csv) throws Exception;

	List<AcessoryFileTO> findAcessoryFileTO(String projectName,
			String simulationName, String acessoryFileName) throws Exception;

}
