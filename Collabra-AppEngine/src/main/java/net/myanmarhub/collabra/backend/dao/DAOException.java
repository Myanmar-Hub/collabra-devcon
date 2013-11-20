package net.myanmarhub.collabra.backend.dao;

/**
 * Tin Htoo Aung (Myanmar Hub) on 20/11/13.
 */
public class DAOException extends Exception {

    public static final String ID_NOT_FOUND = "Object Id is required.";

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
