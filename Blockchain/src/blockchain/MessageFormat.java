package blockchain;

public class MessageFormat implements IFormat {
    public String format(Message message) {
        return String.format("%s: %s", message.getAuthor(), message.getMessage());
    }
}
