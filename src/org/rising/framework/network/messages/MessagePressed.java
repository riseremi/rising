package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class MessagePressed extends Message {
    private static final long serialVersionUID = 1L;
    private final int userId;
    private final int key;

    public MessagePressed(Type type, int id, int key) {
        super(type);
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
