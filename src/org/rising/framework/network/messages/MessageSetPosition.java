package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class MessageSetPosition extends Message {

    private final int x, y;

    public MessageSetPosition(int x, int y) {
        super(Message.Type.SET_POSITION);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
