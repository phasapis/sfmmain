package eu.sifem.service.dao;

import java.util.List;

import eu.sifem.model.to.SimulationProcessManagerTO;

public interface ISimulationProcessManagerDAO {
	
	List<SimulationProcessManagerTO> findAll() throws Exception;

	void delete(SimulationProcessManagerTO simulationProcessManagerTO)
			throws Exception;

	void update(SimulationProcessManagerTO simulationProcessManagerTO)
			throws Exception;

	void insert(SimulationProcessManagerTO simulationProcessManagerTO)
			throws Exception;


	SimulationProcessManagerTO findBySimulationName(
			SimulationProcessManagerTO simulationProcessManagerTO)
			throws Exception;


}
