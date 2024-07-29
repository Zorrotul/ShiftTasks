package cft;

import cft.error.ClientDisconnectedException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.List;

import static cft.Server.*;
import static cft.ServerLogger.getLogger;

public final class ClientConnect {
    final Socket socket;
    private String nickname;
    private final ObjectMapper objectMapper;
    private final String id;
    private final BufferedReader in;
    private final PrintWriter out;

    public ClientConnect(Socket socket, String id) {
        this.socket = socket;
        this.id = id;
        this.objectMapper = new ObjectMapper();
        in = createBufferedReader(socket);
        out = createPrintWriter(socket);
    }

    public boolean tryToLogInNewClient() {
        getLogger().info("Try to add Client");
        if (!socket.isClosed()) {

            Message message = readFirstMessage();

            if (message != null && message.getMessageType().equals(MessageType.CONNECTION_MESSAGE)) {
                nickname = message.getMessage();
                List<String> participants = getConnectedNicknames();
                if (participants.contains(nickname)) {
                    nickname = null;
                    return false;
                }
                getLogger().info("Added client");

                return true;
            }
        } else {
            closeConnect();
        }
        return false;
    }

    Message readFirstMessage() {
        getLogger().info("Try to read message");

        Message message = null;
        if (!socket.isClosed()) {
            try {
                String line = in.readLine();

                message = objectMapper.readValue(line, Message.class);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            getLogger().info("Read message: {}", message);
        } else {
            closeConnect();
        }
        return message;
    }

    void sendIdForNewClient(String id) {
        getLogger().info("Try to send id: {} to client", id);

        if (socket.isClosed()) {
            throw new ClientDisconnectedException("Socket is closed sendIdForNewClient");
        }
        Message message = Message.createMessage(id,
                String.format("This is your id: %s", id),
                MessageType.GOOD_RESPONSE,
                getConnectedNicknames());
        sendMessage(message);
        getLogger().info("Send id: {} to client", id);

    }

    Message joinedChatMessage() {
        getLogger().info("creating joined message");
        String message = nickname + ": joined chat";
        return Message.createMessage(null, message, MessageType.MESSAGE, getConnectedNicknames());
    }

    Message readMessage() {
        getLogger().info("Try to read message");

        Message message;
        if (!socket.isClosed()) {
            try {
                getLogger().info("is socket closed?: {}", socket.isClosed());

                String line = in.readLine();
                if (line != null) {
                    message = objectMapper.readValue(line, Message.class);
                    if (message.getUniqueId().equals(id)) {
                        getLogger().info("Read message: {}", message);
                        return message;
                    } else {
                        sendMessage(wrongIdMessage());
                        getLogger().info("Wrong id message: {}", wrongIdMessage());
                        return null;
                    }
                } else {
                    return null;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            getLogger().info("closing connection");
            closeConnect();
            return null;
        }
    }

    void sendMessage(Message message) {
        getLogger().info("Try to send message{}", message);

        if (socket.isClosed()) {
            throw new RuntimeException("Socket is closed");
        }
        try {
            String messageStr = objectMapper.writeValueAsString(message);
            out.println(messageStr);
            getLogger().info("Send message: {}", message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Message getBusyNicknameError() {
        String message = "this nickname is busy";
        return Message.createMessage(null,
                message,
                MessageType.ERROR,
                getConnectedNicknames());
    }

    Message wrongIdMessage() {
        return Message.createMessage(null, "wrong auth", MessageType.ERROR);
    }

    void closeConnect() throws ClientDisconnectedException {
        try {
            in.close();
            out.close();
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clientHandlers.remove(id);
        sendMessageToAllChat(leftChatMessage());
        throw new ClientDisconnectedException(String.format("Client %s disconnected ", nickname), id);
    }

    Message leftChatMessage() {
        getLogger().info("creating disconnected message");

        String message = nickname + ": left chat";
        return Message.createMessage(null, message, MessageType.MESSAGE, getConnectedNicknames());
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

    public Socket socket() {
        return socket;
    }

    public String nickname() {
        return nickname;
    }

    Message deleteIdAndAddNickname(Message message) {
        String messageStr = nickname + ": " + message.getMessage();
        return new Message(null, messageStr, message.getMessageType());
    }
}
