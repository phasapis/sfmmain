package eu.deri.dataanalysis.vo;

import eu.deri.dataanalysis.composer.OperationComposer;
import eu.deri.dataanalysis.util.ProjectConstant;

public class PairFeature implements Comparable , Cloneable{
	private Feature featureX;
	private Feature featureY;
	private OperationComposer composer;
	private String minimaGlobal;
	private String maximaGlobal;
	private String[] characteristics;
	
	public Feature getFeatureX() {
		return featureX;
	}
	public void setFeatureX(Feature featureX) {
		this.featureX = featureX;
		this.featureX.setAxis(ProjectConstant.X);
	}
	public Feature getFeatureY() {
		return featureY;
	}
	public void setFeatureY(Feature featureY) {
		this.featureY = featureY;
		this.featureY.setAxis(ProjectConstant.Y);
	}
	public OperationComposer getComposer() {
		return composer;
	}
	public void setComposer(OperationComposer composer) {
		this.composer = composer;
	}
	 public String getMinimaGlobal() {
		return minimaGlobal;
	}
	public void setMinimaGlobal(String minimaGlobal) {
		this.minimaGlobal = minimaGlobal;
	}
	public String getMaximaGlobal() {
		return maximaGlobal;
	}
	public void setMaximaGlobal(String maximaGlobal) {
		this.maximaGlobal = maximaGlobal;
	}
	public String[] getCharacteristics() {
		return characteristics;
	}
	public void setCharacteristics(String[] characteristics) {
		this.characteristics = characteristics;
	}
	@Override
	public boolean equals(Object obj) {
		 PairFeature feature=(PairFeature)obj;
			if(this.getFeatureX()!=null && this.getFeatureY()!=null && feature.getFeatureX()!=null && feature.getFeatureY()!=null){
				if(this.getFeatureX().getAttrName().equalsIgnoreCase(feature.getFeatureY().getAttrName()) && 
						this.getFeatureY().getAttrName().equalsIgnoreCase(feature.getFeatureX().getAttrName())){
					return true;
				}else {
					return false;
				}
			}
			return false;
	}
	public int compareTo(Object obj) {
		PairFeature feature=(PairFeature)obj;
		if(this.getFeatureX()!=null && this.getFeatureY()!=null && feature.getFeatureX()!=null && feature.getFeatureY()!=null){
			if(this.getFeatureX().getAttrName().equalsIgnoreCase(feature.getFeatureY().getAttrName()) && 
					this.getFeatureY().getAttrName().equalsIgnoreCase(feature.getFeatureX().getAttrName())){
				return 0;
			}else {
				return -1;
			}
		}
		return -1;
	}
	public PairFeature clone() throws CloneNotSupportedException {
		 return (PairFeature)super.clone();
	}
}
