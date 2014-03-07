package org.rising.framework.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rising.framework.network.messages.Message;
import org.rising.framework.network.messages.MessageChat;
import org.rising.framework.network.messages.MessagePlayer;
import org.rising.game.LobbyScreen;
import org.rising.player.AbstractPlayer;
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

                    int nextInt = rnd.nextInt(99);
                    final MessagePlayer messagePlayer = new MessagePlayer("player_" + nextInt);
                    final ArrayList<AbstractPlayer> players = Server.getPlayers();
                    
                    for (int i = 0; i < players.size(); i++) {
                        Server.getInstance().sendToOne(new MessagePlayer(players.get(i).getName()), id);
                    }
                    
                    Server.getInstance().sendToAllExcludingOne(new MessagePlayer("player_" + nextInt), id);

                    Server.getInstance().sendToOne(messagePlayer, id);
                    System.out.println(nextInt);
                    break;
                case MESSAGE_CHAT:
                    Server.getInstance().sendToAll((MessageChat) message);
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
                try {
                    final Player player = new Player(null, nickname);
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
