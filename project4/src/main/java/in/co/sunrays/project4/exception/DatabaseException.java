package in.co.sunrays.project4.exception;

/**
 * DatabaseException is propagated by DAO classes when an unhandled Database
 * exception occurred.
 * @author GARIMA
 *
 */
public class DatabaseException extends Exception {

    /**
     * Error message
     */
    public DatabaseException(String msg) {
        super(msg);
    }
}

