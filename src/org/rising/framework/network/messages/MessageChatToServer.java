package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class MessageChatToServer extends Message {
    private static final long serialVersionUID = 1L;
    private final String text;

    public MessageChatToServer(String text) {
        super(Message.Type.MESSAGE_FROM_CLIENT);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
