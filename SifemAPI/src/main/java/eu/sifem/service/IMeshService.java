/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.sifem.service;

import eu.sifem.model.to.GeometrySetupTO;
import eu.sifem.model.to.MeshSetupTO;
import java.util.List;

/**
 *
 * @author panos
 */
public interface IMeshService {

    List<MeshSetupTO> getSetupList();
    
    List<GeometrySetupTO> getGeometrySetupList();
}
