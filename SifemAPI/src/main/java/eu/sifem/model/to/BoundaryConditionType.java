package eu.sifem.model.to;

/**
 * @author swapnil/jbjares
 *  Boundary condition Type
 */
public enum BoundaryConditionType{
	MOVINGWALL("movingWall"), BACKEMPTY("backEmpty"), FIXEDWALL1("fixedWall1"), FIXEDWALL2(
			"fixedWall2"), FRONTEMPTY("frontEmpty"), FIXEDWALL3("fixedWall3");
	
	public String type;

	private BoundaryConditionType(String type) {
		this.type = type;
	}
}
