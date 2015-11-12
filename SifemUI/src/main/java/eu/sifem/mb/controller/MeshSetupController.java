package eu.sifem.mb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.UploadedFile;

import eu.sifem.mb.entitybean.GeometrySetupEB;
import eu.sifem.mb.entitybean.MeshSetupEB;
import eu.sifem.model.to.GeometrySetupTO;
import eu.sifem.model.to.MeshSetupTO;
import eu.sifem.service.IMeshService;
import eu.sifem.service.dao.IMeshSetupDAOService;
import javax.faces.bean.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * 
 * @author jbjares
 * 
 */
@ManagedBean(name = "meshSetupController") 
@ViewScoped
public class MeshSetupController extends GenericMB
{
	

	private static final long serialVersionUID = 3460104431041978681L;


	private static final String MESH_UPLOADER = "Mesh Uploader";


	private static final String BIO_IRC_BOX_MODEL = "BioIRC Box Model";

        
	@ManagedProperty(value="#{meshSetupEB}")
	private MeshSetupEB meshSetupEB;
	
	
	@ManagedProperty(value="#{geometrySetupEB}")
	private GeometrySetupEB geometrySetupEB;

	@ManagedProperty(value="#{meshService}")
	private IMeshService meshService;
	        
        
	private Map<String,String> meshDefaults = new HashMap<String,String>();

	private Map<String,MeshSetupTO> meshDefaultsValues = new HashMap<String,MeshSetupTO>();
	private Map<String,GeometrySetupTO> geometryDefaultsValues = new HashMap<String,GeometrySetupTO>();
                
	private String meshSelectedDefault;
	
	private UploadedFile meshUploadedFile;
	
	private Boolean uploadMesh = Boolean.FALSE;
	


	public void handleMeshSelectedDefaultChange() {
		
                populateMeshWithBIORCDefault();
                /*
                try {
			if(BIO_IRC_BOX_MODEL.equalsIgnoreCase(meshSelectedDefault)){
	    		populateMeshWithBIORCDefault();
	    		uploadMesh = Boolean.FALSE;
	    		return;
	    	}
	    	
	    	if(MESH_UPLOADER.equalsIgnoreCase(meshSelectedDefault)){
	    		uploadMesh = Boolean.TRUE;
	    		clear();
	    		return;
	    	}

		} catch (Exception e) {
			addExceptionMessage(e);
		}
                */
    	
    }


    public IMeshService getMeshService() {
        return meshService;
    }

    public void setMeshService(IMeshService meshService) {
        this.meshService = meshService;
    }


	private void populateMeshWithBIORCDefault() {
            
            System.err.println("--- " + this.meshSelectedDefault);
            System.err.println("--- " + this.geometryDefaultsValues.size());
            //System.err.println("--- " + this.geometryDefaultsValues.get(this.meshSelectedDefault).getLengthL());
            
            meshSetupEB.setMeshSetupTO(this.meshDefaultsValues.get(this.meshSelectedDefault));
            geometrySetupEB.setGeometrySetupTO(this.geometryDefaultsValues.get(this.meshSelectedDefault));
            
            /*
		MeshSetupTO meshSetupTO = new MeshSetupTO();
		meshSetupTO.setDivisionB(4.0);
		meshSetupTO.setDivisionh(3.0);
		meshSetupTO.setDivisionH(4.0);
		meshSetupTO.setDivisionL(100.00);
		meshSetupTO.setDivisionW(4.0);
		meshSetupEB.setMeshSetupTO(meshSetupTO);
		
		GeometrySetupTO geometrySetupTO = new GeometrySetupTO();
		geometrySetupTO.setHeigthH(0.001);
		geometrySetupTO.setLengthL(0.035);
		geometrySetupTO.setThicknessh(5e-005);
		geometrySetupTO.setWidthBM(0.0003);
		geometrySetupTO.setWidthW(0.001);
		geometrySetupEB.setGeometrySetupTO(geometrySetupTO);
            */
	}


	private void clear() {
		MeshSetupTO meshSetupTO = new MeshSetupTO();
		meshSetupTO.setDivisionB(0.0);
		meshSetupTO.setDivisionh(0.0);
		meshSetupTO.setDivisionH(0.0);
		meshSetupTO.setDivisionL(0.0);
		meshSetupTO.setDivisionW(0.0);
		meshSetupEB.setMeshSetupTO(meshSetupTO);
		
		GeometrySetupTO geometrySetupTO = new GeometrySetupTO();
		geometrySetupTO.setHeigthH(0.0);
		geometrySetupTO.setLengthL(0.0);
		geometrySetupTO.setThicknessh(0.0);
		geometrySetupTO.setWidthBM(0.0);
		geometrySetupTO.setWidthW(0.0);
		geometrySetupEB.setGeometrySetupTO(geometrySetupTO);
	}


	public MeshSetupEB getMeshSetupEB() {
		return meshSetupEB;
	}


	public void setMeshSetupEB(MeshSetupEB meshSetupEB) {
		this.meshSetupEB = meshSetupEB;
	}


	public GeometrySetupEB getGeometrySetupEB() {
		return geometrySetupEB;
	}


	public void setGeometrySetupEB(GeometrySetupEB geometrySetupEB) {
		this.geometrySetupEB = geometrySetupEB;
	}


	public String getMeshSelectedDefault() {
		return meshSelectedDefault;
	}


	public void setMeshSelectedDefault(String meshSelectedDefault) {
		this.meshSelectedDefault = meshSelectedDefault;
	}


	public Map<String, String> getMeshDefaults() {
		List<String> lstOfParams = new ArrayList<String>();
		try {
			//TODO Remove this hardcode
                    /*
			lstOfParams.add(BIO_IRC_BOX_MODEL);
			lstOfParams.add(MESH_UPLOADER);
			for (String eachObject : lstOfParams) {
				this.meshDefaults.put(eachObject, eachObject);
			}
                            */
			//TODO insertcode here                  

                        List<MeshSetupTO> meshSetupList = meshService.getSetupList();
                        List<GeometrySetupTO> geometrySetupList = meshService.getGeometrySetupList();
                        
                        for (MeshSetupTO meshSetupItem : meshSetupList)
                        {
                            this.meshDefaults.put(meshSetupItem.getName(), meshSetupItem.getName());
                            this.meshDefaultsValues.put(meshSetupItem.getName(), meshSetupItem);
                        }

                        for (GeometrySetupTO geometrySetupItem : geometrySetupList)
                        {
                            this.geometryDefaultsValues.put(geometrySetupItem.getName(), geometrySetupItem);
                        }                        
                        
		} catch (Exception e) {
			addExceptionMessage(e);
		}
		return meshDefaults;
	}


	public void setMeshDefaults(Map<String, String> meshDefaults) {
		this.meshDefaults = meshDefaults;
	}


	public UploadedFile getMeshUploadedFile() {
		return meshUploadedFile;
	}


	public void setMeshUploadedFile(UploadedFile meshUploadedFile) {
		this.meshUploadedFile = meshUploadedFile;
	}


	public Boolean getUploadMesh() {
		return uploadMesh;
	}


	public void setUploadMesh(Boolean uploadMesh) {
		this.uploadMesh = uploadMesh;
	}



	
}
