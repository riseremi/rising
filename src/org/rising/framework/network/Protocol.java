package org.rising.framework.network;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rising.framework.network.messages.Message;
import org.rising.framework.network.messages.MessageChat;
import org.rising.framework.network.messages.MessagePlayer;
import org.rising.game.LobbyScreen;
import org.rising.player.Player;

/**
 *
 * @author Riseremi
 */
public class Protocol {
    private static Random rnd = new Random();

    public static void processMessageOnServerSide(Message message, int id) {
        try {
            Message.Type type = message.getType();

            switch (type) {
                case CONNECTED:
                    LobbyScreen.addToChat("Client connected.");
                    int nextInt = rnd.nextInt(999);
                    //LobbyScreen.sendToClients(new MessagePlayer("Player_" + nextInt));
                    LobbyScreen.getS().sendToOne(new MessagePlayer("Player_" + nextInt), id);
                    System.out.println(nextInt);
                    break;
                case MESSAGE_CHAT:
                    LobbyScreen.sendToClients((MessageChat) message);
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(Protocol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void processMessageOnClientSide(Message message) {
        Message.Type type = message.getType();

        switch (type) {
            case MESSAGE_PLAYER:
                final String nickname = ((MessagePlayer) message).getPlayer();
                System.out.println("nn: " + nickname);
                try {
                    final Player player = new Player(null, 0, 0, 0, 0, 0, nickname, 0);
                    LobbyScreen.addToClients(player);
                    LobbyScreen.setPlayer(player);
                } catch (IOException ex) {
                }
                break;
            case MESSAGE_CHAT:
                LobbyScreen.addToChat(((MessageChat) message).getText());
                break;
        }
    }

}
