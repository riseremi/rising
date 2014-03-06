package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class MessageChatToClient extends Message {
    private static final long serialVersionUID = 1L;
    private final String text;

    public MessageChatToClient(String text) {
        super(Message.Type.MESSAGE_FROM_SERVER);
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
