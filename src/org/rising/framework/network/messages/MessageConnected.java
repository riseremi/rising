package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class MessageConnected extends Message {

    private static final long serialVersionUID = 1L;
    private final String nickname;

    public MessageConnected(String nickname) {
        super(Message.Type.CONNECTED);
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
