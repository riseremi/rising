package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class MessagePlayer extends Message {

    private static final long serialVersionUID = 1L;
    private final String nickname;
    private final int id;

    public MessagePlayer(String nickname, int id) {
        super(Message.Type.MESSAGE_PLAYER);
        this.nickname = nickname;
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public int getId() {
        return id;
    }

}
