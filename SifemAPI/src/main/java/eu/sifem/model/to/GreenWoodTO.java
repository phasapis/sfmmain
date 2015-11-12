package eu.sifem.model.to;

public class GreenWoodTO implements AbstractTO{

	private static final long serialVersionUID = 3273889646700950247L;
	
	private String material;
	private String materialType;
	private String node;
	private String xCoord;
	private String yCoordl;
	private String zCoord;
	private String translationX;
	private String translationY;
	private String translationZ;
	private String subDomain;
	private String id;
	private String excitationFreqVal;
	
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public String getxCoord() {
		return xCoord;
	}
	public void setxCoord(String xCoord) {
		this.xCoord = xCoord;
	}
	public String getyCoordl() {
		return yCoordl;
	}
	public void setyCoordl(String yCoordl) {
		this.yCoordl = yCoordl;
	}
	public String getzCoord() {
		return zCoord;
	}
	public void setzCoord(String zCoord) {
		this.zCoord = zCoord;
	}
	public String getTranslationX() {
		return translationX;
	}
	public void setTranslationX(String translationX) {
		this.translationX = translationX;
	}
	public String getTranslationY() {
		return translationY;
	}
	public void setTranslationY(String translationY) {
		this.translationY = translationY;
	}
	public String getTranslationZ() {
		return translationZ;
	}
	public void setTranslationZ(String translationZ) {
		this.translationZ = translationZ;
	}
	public String getSubDomain() {
		return subDomain;
	}
	public void setSubDomain(String subDomain) {
		this.subDomain = subDomain;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExcitationFreqVal() {
		return excitationFreqVal;
	}
	public void setExcitationFreqVal(String excitationFreqVal) {
		this.excitationFreqVal = excitationFreqVal;
	}

	
	
}
