package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class MessagePlayer extends Message {
    private static final long serialVersionUID = 1L;
    private final String nickname;

    public MessagePlayer(String nickname) {
        super(Message.Type.MESSAGE_PLAYER);
        this.nickname = nickname;
    }

    public String getPlayer() {
        return nickname;
    }

}
