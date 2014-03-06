package org.rising.framework.network;

import java.io.IOException;
import org.rising.framework.network.messages.Message;
import org.rising.framework.network.messages.MessageChatToClient;
import org.rising.framework.network.messages.MessageChatToServer;
import org.rising.game.LobbyScreen;
import org.rising.player.Player;

/**
 *
 * @author Riseremi
 */
public class Protocol {

    public static void processMessageOnServerSide(Message message) {
        Message.Type type = message.getType();

        switch (type) {
            case CONNECTED:
                LobbyScreen.addToChat("Client connected.");
                try {
                    LobbyScreen.addToClients(new Player(null, 0, 0, 0, 0, 0, null, 0));
                } catch (IOException ex) {
                }
                break;

            case MESSAGE_FROM_CLIENT:
                String text = ((MessageChatToServer) message).getText();
                LobbyScreen.sendToClients(new MessageChatToClient(text));
                break;
            case MESSAGE_FROM_SERVER:
                LobbyScreen.addToChat(((MessageChatToClient) message).getText());
                break;
        }
    }

}
