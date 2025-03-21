package dungeon.Messages.MTV;

public class AddMessage implements ModelToViewMessage {
    public String message;
    public AddMessage(final String theMessage) {
        message = theMessage;
    }
}
