package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class MessageChat extends Message {

    private static final long serialVersionUID = 1L;
    private final String nickname, text;
    private final int sender;

    public MessageChat(String nickname, String text, int sender) {
        super(Message.Type.MESSAGE_CHAT);
        this.nickname = nickname;
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public String getNickname() {
        return nickname;
    }

    public int getSender() {
        return sender;
    }
}
