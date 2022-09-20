package in.co.sunrays.project4.exception;

/**
 * ApplicationException is propagated from Service classes when a business logic
 * exception occurred.
 * 
 * @author GARIMA
 *
 */
public class ApplicationException extends Exception {

	/**
	 * Error message
	 */
	public ApplicationException(String msg) {
		super(msg);
	}
}
