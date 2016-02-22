/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.sifem.data;

import eu.sifem.model.to.GeometrySetupTO;
import eu.sifem.model.to.MeshSetupTO;
import eu.sifem.service.IMeshService;
import eu.sifem.service.dao.IMeshSetupDAOService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author panos
 */

@Service(value="meshService")
public class MeshService implements IMeshService
{
	@Autowired
	private IMeshSetupDAOService meshSetupDAO;

        @Override
        public List<MeshSetupTO> getSetupList() {
            return meshSetupDAO.getSetupList();
        }

        @Override
        public List<GeometrySetupTO> getGeometrySetupList() {
            return meshSetupDAO.getGeometrySetupList();
        }
        
}
