package eu.sifem.model.to;



/**
 * 
 * @author jbjares
 * 
 */
public class GeometrySetupTO implements AbstractTO{

	private static final long serialVersionUID = 3273889646700950247L;
	
	private String name = new String("");
        
	private Double lengthL = new Double("0");
	
	private Double heigthH = new Double("0");
	
	private Double widthW = new Double("0");
	
	private Double widthBM = new Double("0");
	
	private Double thicknessh = new Double("0");
        
        private Double thicknesshstart = new Double("0");
        private Double thicknesshend   = new Double("0");
        private Double widthBMstart    = new Double("0");
        private Double widthBMend = new Double("0");
        private Double helicotrema = new Double("0");

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    public Double getHelicotrema() {
        return helicotrema;
    }

    public void setHelicotrema(Double helicotrema) {
        this.helicotrema = helicotrema;
    }


        public Double getThicknesshstart() {
            return thicknesshstart;
        }

        public void setThicknesshstart(Double thicknesshstart) {
            this.thicknesshstart = thicknesshstart;
        }

        public Double getThicknesshend() {
            return thicknesshend;
        }

        public void setThicknesshend(Double thicknesshend) {
            this.thicknesshend = thicknesshend;
        }

        public Double getWidthBMstart() {
            return widthBMstart;
        }

        public void setWidthBMstart(Double widthBMstart) {
            this.widthBMstart = widthBMstart;
        }

        public Double getWidthBMend() {
            return widthBMend;
        }

        public void setWidthBMend(Double widthBMend) {
            this.widthBMend = widthBMend;
        }

        public Double getLengthL() {
            return lengthL;
        }

        public void setLengthL(Double lengthL) {
            this.lengthL = lengthL;
        }

        public Double getHeigthH() {
            return heigthH;
        }

        public void setHeigthH(Double heigthH) {
            this.heigthH = heigthH;
        }

        public Double getWidthW() {
            return widthW;
        }

        public void setWidthW(Double widthW) {
            this.widthW = widthW;
        }

        public Double getWidthBM() {
            return widthBM;
        }

        public void setWidthBM(Double widthBM) {
            this.widthBM = widthBM;
        }

        public Double getThicknessh() {
            return thicknessh;
        }

        public void setThicknessh(Double thicknessh) {
            this.thicknessh = thicknessh;
        }
}
