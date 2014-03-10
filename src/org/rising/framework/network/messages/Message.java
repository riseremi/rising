package org.rising.framework.network.messages;

import java.io.Serializable;

/**
 *
 * @author Riseremi
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Type type;

    public Message(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        EMPTY, CONNECTED, KEY_PRESSED, MESSAGE_FROM_SERVER, MESSAGE_FROM_CLIENT,
        CREATE_PLAYER, MESSAGE_CHAT, MESSAGE_PLAYER, MESSAGE_INIT, SERVER_MESSAGE,
        MESSAGE_CHAT_PRIVATE, TERMINATE, START_GAME, SET_POSITION
    }
}
