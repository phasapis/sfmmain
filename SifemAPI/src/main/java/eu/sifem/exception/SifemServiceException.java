package eu.sifem.exception;

public class SifemServiceException extends SifemException{

	private static final long serialVersionUID = -334040711867610457L;

	public SifemServiceException() {
		super();
	}

	public SifemServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SifemServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public SifemServiceException(String message) {
		super(message);
	}

	public SifemServiceException(Throwable cause) {
		super(cause);
	}
	
	

}
