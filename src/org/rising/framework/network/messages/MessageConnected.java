package org.rising.framework.network.messages;

/**
 *
 * @author Riseremi
 */
public class MessageConnected extends Message {
    private static final long serialVersionUID = 1L;
 
   public MessageConnected() {
        super(Message.Type.CONNECTED);
    }

}
