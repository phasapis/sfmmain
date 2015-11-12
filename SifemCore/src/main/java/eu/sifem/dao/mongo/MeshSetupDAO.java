package eu.sifem.dao.mongo;

import eu.sifem.model.to.GeometrySetupTO;
import eu.sifem.model.to.MeshSetupTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;
import eu.sifem.service.dao.IMeshSetupDAOService;

/**
 *
 * @author panos
 */
@Repository
public class MeshSetupDAO implements IMeshSetupDAOService
{
    
    @Autowired
    private MongoOperations mongoOperations;
    
    @Override
    public List<MeshSetupTO> getSetupList()
    {
        return mongoOperations.findAll(MeshSetupTO.class,MeshSetupTO.class.getSimpleName());
    }

    @Override
    public List<GeometrySetupTO> getGeometrySetupList() {
        return mongoOperations.findAll(GeometrySetupTO.class,GeometrySetupTO.class.getSimpleName());
    }
    
}
