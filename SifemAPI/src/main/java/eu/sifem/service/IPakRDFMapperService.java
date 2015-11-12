package eu.sifem.service;

import java.io.InputStream;

import eu.sifem.model.to.SimulationInstanceTO;



/**
 * 
 * @author jbjares
 * 
 */
public interface IPakRDFMapperService {

	InputStream unvToRDFService(SimulationInstanceTO simulationInstanceTO,InputStream simulationFile) throws Exception;

	InputStream datToRDFService(SimulationInstanceTO simulationInstanceTO,
			InputStream simulationFile) throws Exception;


}
