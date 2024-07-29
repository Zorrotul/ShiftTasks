package cft;

public class ClientMain {

    public static void main(String[] args) {
        ClientConnection connection = new ClientConnection();
        connection.getResponse();
        connection.onDisconnected();
    }
}
