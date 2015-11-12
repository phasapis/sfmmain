package eu.sifem.exception;

public class SifemException extends Exception{

	private static final long serialVersionUID = -7684332830825337845L;

	public SifemException() {
		super();
	}

	public SifemException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SifemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SifemException(String message) {
		super(message);
	}

	public SifemException(Throwable cause) {
		super(cause);
	}
	
	

}
