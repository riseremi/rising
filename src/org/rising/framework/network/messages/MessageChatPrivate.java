package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class MessageChatPrivate extends Message {

    private static final long serialVersionUID = 1L;
    private final String nickname, text;
    private final int sender, target;

    public MessageChatPrivate(String nickname, String text, int sender, int target) {
        super(Message.Type.MESSAGE_CHAT_PRIVATE);
        this.nickname = nickname;
        this.text = text;
        this.sender = sender;
        this.target = target;
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

    public int getTarget() {
        return target;
    }

    public MessageChat getMessageChat() {
        return new MessageChat("[PM] " + nickname, text, sender);
    }

}
