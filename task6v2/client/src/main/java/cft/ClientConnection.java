package cft;


import cft.error.ResponseException;
import cft.listeners.ClientConnectionListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ClientConnection implements ClientConnectionListener {
    private Socket socket;
    private String id;
    private final ChatField chatField;
    private final NewSessionWindow sessionWindow;
    private final ObjectMapper objectMapper;
    private BufferedReader in;
    private PrintWriter out;
    private static final Logger logger = LoggerFactory.getLogger(ClientConnection.class);


    public ClientConnection() {
        objectMapper = new ObjectMapper();
        sessionWindow = new NewSessionWindow(this);
        chatField = new ChatField(this);
        sessionWindow.showWindow();
    }

    public void getResponse() {
        logger.info("Try to read message");

        Message message;
        while (socket.isConnected()) {

            message = readMessage();
            logger.info("Red message: {}", message.toString());

            if (message.getMessageType().equals(MessageType.MESSAGE)) {
                chatField.printChatMessage(message);
            }

            try {
                String line;
                while ((line = in.readLine()) != null) {
                    message = objectMapper.readValue(line, Message.class);
                    chatField.printChatMessage(message);
                }
            } catch (IOException e) {
                System.out.println("IO Ex");
                break;
            }
        }
    }

    public boolean tryToConnect(int serverPort, String nickname) {

        logger.info("Trying to connect: {} nick: {}", serverPort, nickname);

        Message connectResponse;
        try {
            this.socket = new Socket("localhost", serverPort);
        } catch (IllegalArgumentException e) {
            logger.info("Out of bounds host port: {}", serverPort);
            InformationWindow informationWindow = new InformationWindow("Wrong host port");
            informationWindow.showChat();
            return false;
        } catch (UnknownHostException e) {
            logger.info("Wrong host port: {}", serverPort);
            InformationWindow informationWindow = new InformationWindow("Wrong host port");
            informationWindow.showChat();
            return false;
        } catch (IOException e) {
            logger.info("Failed to connect to the server with port: {}", serverPort);
            InformationWindow informationWindow = new InformationWindow("Wrong host port");
            informationWindow.showChat();
            return false;
        }

        out = createPrintWriter(socket);
        in = createBufferedReader(socket);
        sendLogInMessage(Message.createMessage(id, nickname, MessageType.CONNECTION_MESSAGE));

        connectResponse = readMessage();
        if (connectResponse.getMessageType().equals(MessageType.GOOD_RESPONSE)) {
            id = connectResponse.getUniqueId();
            chatField.printParticipantList(connectResponse.getParticipants());
            logger.info("Try to connecting id: {} nick: {}", id, nickname);
            return true;
        } else if (connectResponse.getMessageType().equals(MessageType.ERROR)) {
            logger.info("Nickname {} is busy, try another", nickname);
            InformationWindow informationWindow = new InformationWindow("Nickname is busy, try another");
            informationWindow.showChat();
            return false;
        } else {
            throw new ResponseException(
                    String.format("Wrong message type from connection message: %s", connectResponse.getMessageType()));
        }
    }

    void sendLogInMessage(Message message) {
        logger.info("Sending message: {}", message.toString());

        try {
            String messageStr = objectMapper.writeValueAsString(message);
            out.println(messageStr);
        } catch (IOException e) {
            logger.error("cant send log in message: {}", message.toString(), e);
        }
        logger.info("Send message: {}", message.toString());

    }

    void sendChatMessage(String messageFromChat) {
        logger.info("Sending message: {}", messageFromChat.toString());

        try {
            Message message = Message.createMessage(id, messageFromChat, MessageType.MESSAGE);
            String messageStr = objectMapper.writeValueAsString(message);
            out.println(messageStr);
        } catch (IOException e) {
            logger.error("cant send chat message: {}", messageFromChat.toString(), e);
        }
        logger.info("Send message: {}", messageFromChat.toString());

    }

    Message readMessage() {
        logger.info("Try to Read message");

        Message message = null;
        try {
            String line = in.readLine();
            if (line != null) {
                message = objectMapper.readValue(line, Message.class);
            } else {
                logger.error("Socket is closed");
                onDisconnected();
            }
        } catch (SocketException e) {
            logger.error("Socket is closed", e);
            onDisconnected();
        } catch (IOException e) {
            logger.error("cant read message",
                    e);
        }
        logger.info("Read message: {}", message.toString());
        return message;
    }

    private BufferedReader createBufferedReader(Socket socket) {
        InputStream inputStream;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return new BufferedReader(inputStreamReader);
    }

    private PrintWriter createPrintWriter(Socket socket) {
        OutputStream outputStream;
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new PrintWriter(outputStream, true);
    }

    @Override
    public void onConnected(String nickname) {
        logger.info("Client {} connected", nickname);
        this.chatField.showChat();
        chatField.setNickname(nickname);
    }

    @Override
    public void onDisconnected() {
        logger.info("Closing socket");

        try {
            if (socket != null) {
                socket.close();
                logger.info("socket.close");

            }
            System.exit(0);
        } catch (IOException e) {
            logger.error("Failed to close connection");

        }
    }
}