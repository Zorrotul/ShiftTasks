package cft;

import java.text.SimpleDateFormat;
import java.util.*;

public final class Message {
    private final String uniqueId;
    private final String message;
    private final String date;
    private final MessageType messageType;
    private final List<String> participants;

    public Message(){
        this.uniqueId = null;
        this.message = "";
        this.date = getCurrentDate();
        this.messageType = MessageType.ERROR;
        this.participants = new ArrayList<>();
    }

    public Message(String uid, String message, MessageType messageType) {
        this.uniqueId = uid;
        this.message = message;
        this.date = getCurrentDate();
        this.messageType = messageType;
        this.participants = new ArrayList<>();
    }

    public Message(String uid, String message, MessageType messageType, List<String> participants) {
        this.uniqueId = uid;
        this.message = message;
        this.date = getCurrentDate();
        this.messageType = messageType;
        this.participants = participants;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return (date + " " + message);
    }

    public static Message createMessage(String uniqueId, String message, MessageType messageType) {
        String date = getCurrentDate();
        return new Message(uniqueId, message, messageType);
    }

    public static Message createMessage(String uniqueId, String message, MessageType messageType, List<String> connectedNicknames) {
        String date = getCurrentDate();
        return new Message(uniqueId, message, messageType, connectedNicknames);
    }

    private static String getCurrentDate() {
        TimeZone timeZone = TimeZone.getDefault();
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(calendar.getTime());
    }


    public String message() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Message) obj;
        return Objects.equals(this.uniqueId, that.uniqueId) &&
                Objects.equals(this.message, that.message) &&
                Objects.equals(this.date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, message, date);
    }

}
