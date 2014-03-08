package org.rising.framework.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rising.framework.network.messages.Message;
import org.rising.framework.network.messages.MessageChat;
import org.rising.framework.network.messages.MessageConnected;
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

    public static void processMessageOnServerSide(final Message message, int id) {
        try {
            Message.Type type = message.getType();

            switch (type) {
                case CONNECTED:
                    MessageConnected messageConnected = ((MessageConnected) message);
                    String nickname = messageConnected.getNickname();

                    LobbyScreen.addToChat("Client connected.", true, LobbyScreen.Type.CONNECTED);

                    final MessagePlayer messagePlayer = new MessagePlayer(nickname, id);
                    final ArrayList<AbstractPlayer> players = Server.getPlayers();

                    //send newly created player to connected client firstly
                    Server.getInstance().sendToOne(messagePlayer, id);

                    //send all existing players to connected client
                    for (int i = 0; i < players.size(); i++) {
                        final AbstractPlayer player = players.get(i);
                        Server.getInstance().sendToOne(new MessagePlayer(player.getName(), player.getId()), id);
                    }

                    //send our newly connected client to all existing clients 
                    Server.getInstance().sendToAllExcludingOne(new MessagePlayer(nickname, id), id);

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
                final MessagePlayer playerMsg = ((MessagePlayer) message);
                final String nickname = playerMsg.getNickname();
                final int id = playerMsg.getId();
                final ArrayList<AbstractPlayer> players = Server.getPlayers();
                try {
                    final Player player = new Player(null, nickname, id);
                    LobbyScreen.addToClients(player);
                    if (players.size() <= 1) {
                        LobbyScreen.setPlayer(player);
                    }
                } catch (IOException ex) {
                }
                break;
            case MESSAGE_CHAT:
                MessageChat msg = ((MessageChat) message);
                String nick = msg.getNickname();
                String text = msg.getText();
                int sender = msg.getSender();

                LobbyScreen.Type type1 = (LobbyScreen.getPlayer().getId() == sender) ? LobbyScreen.Type.YOUR_MESSAGE : LobbyScreen.Type.ENEMY_MESSAGE;

                LobbyScreen.addToChat(nick + ": ", false, type1);
                LobbyScreen.addToChat(text, true, LobbyScreen.Type.MESSAGE);
                break;
        }
    }

}
