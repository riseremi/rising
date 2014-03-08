package org.rising.framework.network.messages;

/**
 *
 * @author Remi
 */
public class MessageServerInfo extends Message {

    private final String text;

    public MessageServerInfo(String text) {
        super(Message.Type.SERVER_MESSAGE);
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
