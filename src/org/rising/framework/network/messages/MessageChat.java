package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class MessageChat extends Message {
    private static final long serialVersionUID = 1L;
    private final String text;

    public MessageChat(String text) {
        super(Message.Type.MESSAGE_CHAT);
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
