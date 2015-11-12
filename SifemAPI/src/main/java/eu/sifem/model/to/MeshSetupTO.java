package eu.sifem.model.to;


/**
 * 
 * @author jbjares
 * 
 */
public class MeshSetupTO implements AbstractTO{
	private static final long serialVersionUID = -8378342434305264846L;

        private String name = new String("");
        
        private String commandLineArgument = new String("");
        
	private Double divisionL = new Double("0");
	
	private Double divisionW = new Double("0");
	
	private Double divisionB = new Double("0");
	
	private Double divisionH = new Double("0");
	
	private Double divisionh = new Double("0");
        
        private String description = new String("");

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCommandLineArgument() {
            return commandLineArgument;
        }

        public void setCommandLineArgument(String commandLineArgument) {
            this.commandLineArgument = commandLineArgument;
        }        
        
	public Double getDivisionL() {
		return divisionL;
	}

	public void setDivisionL(Double divisionL) {
		this.divisionL = divisionL;
	}

	public Double getDivisionW() {
		return divisionW;
	}

	public void setDivisionW(Double divisionW) {
		this.divisionW = divisionW;
	}

	public Double getDivisionB() {
		return divisionB;
	}

	public void setDivisionB(Double divisionB) {
		this.divisionB = divisionB;
	}

	public Double getDivisionH() {
		return divisionH;
	}

	public void setDivisionH(Double divisionH) {
		this.divisionH = divisionH;
	}

	public Double getDivisionh() {
		return divisionh;
	}

	public void setDivisionh(Double divisionh) {
		this.divisionh = divisionh;
	}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
}
