package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
//MessagePressed
public class MessagePressed extends Message {
    private static final long serialVersionUID = 1L;
    private final int userId;
    private final int key;

    public MessagePressed(int id, int key) {
        super(Message.Type.KEY_PRESSED);
        this.userId = id;
        this.key = key;
    }

    public int getUserId() {
        return userId;
    }

    public int getKey() {
        return key;
    }

}
