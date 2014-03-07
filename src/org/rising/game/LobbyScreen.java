package org.rising.game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.rising.framework.network.Client;
import org.rising.framework.network.Server;
import org.rising.framework.network.messages.Message;
import org.rising.framework.network.messages.MessageChat;
import org.rising.framework.network.messages.MessageConnected;
import org.rising.player.AbstractPlayer;
import org.rising.player.Player;

/**
 *
 * @author Riseremi
 */
public class LobbyScreen extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static JList<AbstractPlayer> clients;
    private JTextField nickname, ip, message;
    private JButton server, client, start, send;
    private static JTextArea chatArea;
    private static ArrayList<AbstractPlayer> pl;
    //
    private static Player player;
    //
    static boolean isServer;
    static Server s;
    static Client c1;

    public LobbyScreen() {
//        setPreferredSize(new Dimension(16 * 2 + 192, 380));
        setPreferredSize(new Dimension(620, 480));
        nickname = new JTextField("nickname");
        ip = new JTextField("ip");
        message = new JTextField("message");
        message.setVisible(false);

        setLayout(null);

        server = new JButton("server");
        client = new JButton("client");
        start = new JButton("start");
        send = new JButton("send");
        send.setVisible(false);

        chatArea = new JTextArea(16, 16);
        chatArea.setVisible(false);

        pl = new ArrayList<>();

        clients = new JList<>();
//        clients.setPreferredSize(new Dimension(192, 256));
        clients.setBounds(256, 16 + 26 + 8, 192, 256);
        clients.setListData(pl.toArray(new AbstractPlayer[pl.size()]));
        //clients.setVisible(false);

        nickname.setBounds(16, 16, 192, 26);
        add(nickname);
        ip.setBounds(16, 16 + 26 + 8, 192, 26);
        add(ip);

        server.setBounds(16, 16 + 26 + 26 + 16, 96, 26);
        add(server);
        client.setBounds(16 + 96, 16 + 26 + 26 + 16, 96, 26);
        add(client);

        add(clients);
        chatArea.setBounds(16, 16, 192, 256);
        add(chatArea);
        message.setBounds(16, 16 + 8 + 256, 192 - 64, 26);
        add(message);
        send.setBounds(16 + 192 - 64, 16 + 8 + 256, 64, 26);
        add(send);
        //add(start);
        server.addActionListener(this);
        client.addActionListener(this);
        send.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(server.getActionCommand())) {
            server.setVisible(false);
            client.setVisible(false);
            ip.setVisible(false);
            nickname.setVisible(false);
            //
            chatArea.setVisible(true);
            message.setVisible(true);
            send.setVisible(true);
            try {
                s = new Server(7777);
                c1 = new Client(7777, "localhost");
                c1.send(new MessageConnected());

                s.sendToAll(new MessageChat("INIT: Sieg heil, clients."));
            } catch (IOException ex) {
            }
        }
        if (e.getActionCommand().equals(client.getActionCommand())) {
            server.setVisible(false);
            client.setVisible(false);
            ip.setVisible(false);
            nickname.setVisible(false);
            //
            chatArea.setVisible(true);
            message.setVisible(true);
            send.setVisible(true);
            try {
                c1 = new Client(7777, "localhost");
                c1.send(new MessageConnected());
                c1.send(new MessageChat("INIT: Sieg heil, server."));
            } catch (IOException ex) {
            }

        }

        if (e.getActionCommand().equals(send.getActionCommand())) {
            try {
                c1.send(new MessageChat(player.getName() + ": " + message.getText()));
                message.setText("");
            } catch (IOException ex) {
            }
        }
    }

    public static Server getS() {
        return s;
    }

    public static void addToChat(String str) {
        chatArea.append(str + "\r\n");
    }

    public static void sendToClients(Message m) {
        try {
            s.sendToAll(m);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    public static void addToClients(AbstractPlayer player) {
        pl.add(player);
        clients.setListData(pl.toArray(new AbstractPlayer[pl.size()]));
    }

    public static void setPlayer(Player p) {
        player = p;
    }
}
