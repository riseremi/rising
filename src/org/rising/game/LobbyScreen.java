package org.rising.game;

import java.awt.Dimension;
import java.awt.FlowLayout;
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
import org.rising.framework.network.messages.MessageChatToClient;
import org.rising.framework.network.messages.MessageChatToServer;
import org.rising.framework.network.messages.MessageConnected;
import org.rising.player.AbstractPlayer;

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
    static boolean isServer;
    static Server s;
    static Client c1;

    public LobbyScreen() {
        nickname = new JTextField("nickname");
        nickname.setPreferredSize(new Dimension(128, 26));
        ip = new JTextField("ip");
        ip.setPreferredSize(new Dimension(128, 26));
        message = new JTextField("message");
        message.setPreferredSize(new Dimension(128, 26));

        setLayout(new FlowLayout());

        server = new JButton("server");
        client = new JButton("client");
        start = new JButton("start");
        send = new JButton("send");

        chatArea = new JTextArea("CHAT KEK KEK KEK\r\n", 16, 16);

        pl = new ArrayList<>();
//        try {
//            pl.add(new Player(null, 0, 0, 0, WIDTH, 0, TOOL_TIP_TEXT_KEY, 0));
//        } catch (IOException ex) {
//        }

        clients = new JList<>();
        clients.setPreferredSize(new Dimension(192, 256));
        clients.setListData(pl.toArray(new AbstractPlayer[pl.size()]));

        add(nickname);
        add(ip);

        add(server);
        add(client);
        add(send);

        add(clients);

        add(chatArea);
        add(message);
        add(start);

        server.addActionListener(this);
        client.addActionListener(this);
        send.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(server.getActionCommand())) {
            try {
                s = new Server(7777);
                c1 = new Client(7777, "localhost");

                s.sendToAll(new MessageChatToClient("Sieg heil, clients.\r\n"));

            } catch (IOException ex) {
            }
        }
        if (e.getActionCommand().equals(client.getActionCommand())) {
            try {
                c1 = new Client(7777, "localhost");
                c1.send(new MessageConnected());
                c1.send(new MessageChatToServer("Sieg heil, server.\r\n"));
            } catch (IOException ex) {
            }

        }

        if (e.getActionCommand().equals(send.getActionCommand())) {
            try {
                c1.send(new MessageChatToServer(message.getText()));
                message.setText("");
            } catch (IOException ex) {
            }
        }

    }

    public static void addToChat(String str) {
        chatArea.append(str + "\r\n");
    }

    public static void sendToClients(Message m) {
        try {
            s.sendToAll(m);
        } catch (IOException ex) {
        }
    }

    public static void addToClients(AbstractPlayer player) {
        pl.add(player);
        clients.setListData(pl.toArray(new AbstractPlayer[pl.size()]));
    }

}
