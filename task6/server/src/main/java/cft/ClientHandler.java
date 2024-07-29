package cft;


import cft.error.ClientDisconnectedException;

import java.net.Socket;

import static cft.Server.*;
import static cft.ServerLogger.getLogger;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final String id;
    private ClientConnect clientConnect;

    public ClientHandler(Socket socket, String id) {
        this.id = id;
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        getLogger().info("Socket: {}, is opened: {}", clientSocket, clientSocket.isConnected());

        try {
            getLogger().info("Client trying to connect");
            clientConnect = new ClientConnect(clientSocket, id);
            getLogger().info("ClientConnect");
            boolean isLogged = clientConnect.tryToLogInNewClient();
            getLogger().info("Client trying to login");
            while (!isLogged) {
                clientConnect.sendMessage(clientConnect.getBusyNicknameError());
                isLogged = clientConnect.tryToLogInNewClient();
            }

            clientHandlers.put(id, this);

            getLogger().info("Client connected");

            clientConnect.sendIdForNewClient(id);
            getLogger().info("Sent id to client");

            sendMessageToAllChat(clientConnect.joinedChatMessage());
            getLogger().info("Sent message to all chat");

            while (!clientConnect.socket.isClosed()) {

                Message messageFromClient = clientConnect.readMessage();
                if (messageFromClient != null) {
                    sendMessageToAllChat(clientConnect.deleteIdAndAddNickname(messageFromClient));
                } else {
                    clientConnect.closeConnect();
                    Thread.currentThread().interrupt();
                }
            }
            clientConnect.closeConnect();
            Thread.currentThread().interrupt();

        } catch (ClientDisconnectedException e) {
            getLogger().info(e.getMessage());
        } catch (RuntimeException e) {
            getLogger().error(e.getMessage());
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void sendMessage(Message message) {
        Message tempMessage = new Message(message.getUniqueId(), message.getMessage(), message.getMessageType(), getConnectedNicknames());
        clientConnect.sendMessage(tempMessage);
    }

    public ClientConnect getClientConnect() {
        return this.clientConnect;
    }
}