package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class MessageMove extends Message {

    private static final long serialVersionUID = 1L;
    private final int direction;

    public MessageMove(final int direction) {
        super(Message.Type.MOVE);
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
