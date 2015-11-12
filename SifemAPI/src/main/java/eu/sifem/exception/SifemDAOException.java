package eu.sifem.exception;

public class SifemDAOException extends SifemException{

	private static final long serialVersionUID = -334040711867610457L;

	public SifemDAOException() {
		super();
	}

	public SifemDAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SifemDAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public SifemDAOException(String message) {
		super(message);
	}

	public SifemDAOException(Throwable cause) {
		super(cause);
	}
	
	

}
