package cft.errors;

public class NumberParserException extends RuntimeException {

    public NumberParserException(String message) {
        super(message);
    }

    public NumberParserException(String message, Throwable cause) {
        super(message, cause);
    }

}