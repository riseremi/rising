package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class MessageTerminate extends Message {

    private static final long serialVersionUID = 1L;

    public MessageTerminate() {
        super(Message.Type.TERMINATE);
    }

}
