package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class MessageInit extends Message {

    private static final long serialVersionUID = 1L;
    private final int id;

    public MessageInit(int sender) {
        super(Message.Type.MESSAGE_INIT);
        this.id = sender;
    }

    public int getId() {
        return id;
    }
}
