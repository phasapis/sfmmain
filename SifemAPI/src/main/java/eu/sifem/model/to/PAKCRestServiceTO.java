
package eu.sifem.model.to;

public class PAKCRestServiceTO implements AbstractTO{

        private static final long serialVersionUID = -7622290983547686018L;

        private byte[] cfgFile;

        private byte[] datFile;

        private byte[] unvFile;

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