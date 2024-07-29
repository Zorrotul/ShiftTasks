package cft;


import cft.error.ClientDisconnectedException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static cft.ServerLogger.getLogger;

public class Server {

    private final ServerSocket serverSocket;
    public static final Map<String, ClientHandler> clientHandlers = new HashMap<>();

    public Server() {

        try {
            this.serverSocket = new ServerSocket(1);
            getLogger().info("Server started, wait connecting...");

            ExecutorService executorService = Executors.newCachedThreadPool();


            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    String id = generateUniqueId();
                    ClientHandler clientHandler = new ClientHandler(clientSocket, id);
                    executorService.submit(clientHandler);

                } catch (ClientDisconnectedException e) {
                    getLogger().info(e.getMessage());
                } catch (IOException e) {
                    getLogger().error("Cant connect client");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().error("Cant start server");
            throw new RuntimeException();
        }
    }

    static void sendMessageToAllChat(Message message) {
        getLogger().info("Try to sent message to all");
        List<String> deadClients = new ArrayList<>();
        for (Map.Entry<String, ClientHandler> clientHandlerEntry : clientHandlers.entrySet()) {
            if (clientHandlerEntry.getValue().getClientSocket().isConnected()) {
                clientHandlerEntry.getValue().sendMessage(message);
            } else {
                deadClients.add(clientHandlerEntry.getKey());
            }
        }
        for (String id : deadClients) {
            clientHandlers.get(id).getClientConnect().closeConnect();
        }
        getLogger().info("Sent message to all");
    }

    private String generateUniqueId() {
        getLogger().info("generate id");
        return UUID.randomUUID().toString();
    }

    static List<String> getConnectedNicknames() {
        List<String> connectedNicknames = new ArrayList<>();
        for (Map.Entry<String, ClientHandler> clientConnectEntry : clientHandlers.entrySet()) {
            connectedNicknames.add(clientConnectEntry.getValue().getClientConnect().nickname());
        }
        return connectedNicknames;
    }

}

