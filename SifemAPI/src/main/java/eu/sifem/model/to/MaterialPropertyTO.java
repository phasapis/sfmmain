package eu.sifem.model.to;

import java.io.File;


/**
 * 
 * @author jbjares
 * 
 */
public class MaterialPropertyTO implements AbstractTO{
	private static final long serialVersionUID = -5033208917141944387L;
	
	private KinematicViscosityTO kinematicViscosity = new KinematicViscosityTO();
	
	private FluidTO fluid = new FluidTO();
	
	private Boolean isUsingYoungModulus;
	
	private Boolean isUsingDamping;
	
	private File uploadedFile;

	public KinematicViscosityTO getKinematicViscosity() {
		return kinematicViscosity;
	}

	public void setKinematicViscosity(KinematicViscosityTO kinematicViscosity) {
		this.kinematicViscosity = kinematicViscosity;
	}

	public FluidTO getFluid() {
		return fluid;
	}

	public void setFluid(FluidTO fluid) {
		this.fluid = fluid;
	}

	public Boolean getIsUsingYoungModulus() {
		return isUsingYoungModulus;
	}

	public void setIsUsingYoungModulus(Boolean isUsingYoungModulus) {
		this.isUsingYoungModulus = isUsingYoungModulus;
	}

	public Boolean getIsUsingDamping() {
		return isUsingDamping;
	}

	public void setIsUsingDamping(Boolean isUsingDamping) {
		this.isUsingDamping = isUsingDamping;
	}

	public File getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(File uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	

}
