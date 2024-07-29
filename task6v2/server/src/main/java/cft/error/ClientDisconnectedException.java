package cft.error;

public class ClientDisconnectedException extends RuntimeException{
    public String id;

    public ClientDisconnectedException(String message) {
        super(message);
    }

    public ClientDisconnectedException(String message, String id) {
        super(message);
        this.id=id;
    }

    public ClientDisconnectedException(String message, Throwable cause) {
        super(message, cause);
    }


}
