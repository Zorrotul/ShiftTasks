package cft.model.error;

public class CellException extends RuntimeException {

    public CellException(String message) {
        super(message);
    }

    public CellException(String message, Throwable cause) {
        super(message, cause);
    }

}