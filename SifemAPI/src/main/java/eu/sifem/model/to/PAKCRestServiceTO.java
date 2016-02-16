
package eu.sifem.model.to;

public class PAKCRestServiceTO implements AbstractTO{

        private static final long serialVersionUID = -7622290983547686018L;

        private byte[] cfgFile;

        private byte[] datFile;

        private byte[] unvFile;
        
        private byte[] pImagFile;
        
        private byte[] dCenterLineFile;
        
        private byte[] pRealFile;
        
        private byte[] vMagnFile;
        
        private byte[] vPhaseFile;
        
        
        
        

        public byte[] getpImagFile() {
			return pImagFile;
		}

		public void setpImagFile(byte[] pImagFile) {
			this.pImagFile = pImagFile;
		}

		public byte[] getdCenterLineFile() {
			return dCenterLineFile;
		}

		public void setdCenterLineFile(byte[] dCenterLineFile) {
			this.dCenterLineFile = dCenterLineFile;
		}

		public byte[] getpRealFile() {
			return pRealFile;
		}

		public void setpRealFile(byte[] pRealFile) {
			this.pRealFile = pRealFile;
		}

		public byte[] getvMagnFile() {
			return vMagnFile;
		}

		public void setvMagnFile(byte[] vMagnFile) {
			this.vMagnFile = vMagnFile;
		}

		public byte[] getvPhaseFile() {
			return vPhaseFile;
		}

		public void setvPhaseFile(byte[] vPhaseFile) {
			this.vPhaseFile = vPhaseFile;
		}

		public byte[] getCfgFile() {
                return cfgFile;
        }

        public void setCfgFile(byte[] cfgFile) {
                this.cfgFile = cfgFile;
        }

        public byte[] getDatFile() {
                return datFile;
        }

        public void setDatFile(byte[] datFile) {
                this.datFile = datFile;
        }

        public byte[] getUnvFile() {
                return unvFile;
        }

        public void setUnvFile(byte[] unvFile) {
                this.unvFile = unvFile;
        }
}