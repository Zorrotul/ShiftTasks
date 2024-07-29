package cft;

public class MessageFormat {
    public static String format(Message message) {
        return String.format("%s: %s", message.getUniqueId(), message.message());
    }
}
