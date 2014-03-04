package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class Message {
    private final Type type;
    private final String body;

    public Type getType() {
        return type;
    }

    public Message(Type type, String body) {
        this.type = type;
        this.body = body;
    }

    public enum Type {
        BLANK, CONNECTED
    }
}
