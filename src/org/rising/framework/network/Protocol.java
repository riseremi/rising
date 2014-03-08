package org.rising.framework.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rising.framework.network.messages.Message;
import org.rising.framework.network.messages.MessageChat;
import org.rising.framework.network.messages.MessageChatPrivate;
import org.rising.framework.network.messages.MessageConnected;
import org.rising.framework.network.messages.MessagePlayer;
import org.rising.framework.network.messages.MessageServerInfo;
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

                    //LobbyScreen.addToChat("New client connected to the server.", true, LobbyScreen.Type.SERVER_INFO);
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

                    Server.getInstance().sendToAllExcludingOne(new MessageServerInfo(nickname + " joined the game."), id);
                    Server.getInstance().sendToOne(new MessageServerInfo("Welcome to the game."), id);
                    break;
                case MESSAGE_CHAT:
                    Server.getInstance().sendToAll((MessageChat) message);
                    break;
                case MESSAGE_CHAT_PRIVATE:
                    final MessageChatPrivate message1 = ((MessageChatPrivate) message);
                    Server.getInstance().sendToOne(message1.getMessageChat(), message1.getTarget());
                    break;
                default:
                    throw new Exception("Not supported yet.");
            }
        } catch (IOException ex) {
        } catch (Exception ex) {
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
                String nick = msg.getNickname() + ": ";
                String text = msg.getText();
                int sender = msg.getSender();

                LobbyScreen.Type type1 = (LobbyScreen.getPlayer().getId() == sender) ? LobbyScreen.Type.YOUR_MESSAGE : LobbyScreen.Type.ENEMY_MESSAGE;

                LobbyScreen.addToChat(nick, false, type1);
                LobbyScreen.addToChat(text, true, LobbyScreen.Type.MESSAGE);
                break;

            case SERVER_MESSAGE:
                LobbyScreen.addToChat(((MessageServerInfo) message).getText(), true, LobbyScreen.Type.SERVER_INFO);
                break;
            case TERMINATE:
                LobbyScreen.addToChat("You will be disconnected and turned off after 10 seconds.", true, LobbyScreen.Type.SERVER_INFO);
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException ex) {
                }
                System.exit(0);
                break;
            default:
                try {
                    throw new Exception("Not supported yet.");
                } catch (Exception ex) {
                }
        }
    }

}
