package cft.errors;

public class NotWithinRangeException extends RuntimeException{

    public NotWithinRangeException(String message) {
        super(message);
    }

    public NotWithinRangeException(String message, Throwable cause) {
        super(message, cause);
    }

}

