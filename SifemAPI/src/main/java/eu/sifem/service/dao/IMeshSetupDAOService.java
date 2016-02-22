/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.sifem.service.dao;

import eu.sifem.model.to.GeometrySetupTO;
import eu.sifem.model.to.MeshSetupTO;
import java.util.List;

/**
 *
 * @author panos
 */
public interface IMeshSetupDAOService {

    List<MeshSetupTO> getSetupList();    
    List<GeometrySetupTO> getGeometrySetupList();

}
