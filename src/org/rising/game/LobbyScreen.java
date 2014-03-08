package org.rising.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import org.rising.framework.Utility;
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
//    private static JTextArea chatArea;
//    private static ArrayList<AbstractPlayer> players;
    //
    private static Player player;
    //
    static boolean isServer;
    static Server s;
    static Client c1;
    //
    private static JTextPane tPane;
    //colors
    private final static Color MESSAGE = new Color(53, 11, 53);
    private final static Color YOUR_NICK = new Color(209, 85, 119);
    private final static Color OTHER_NICK = new Color(126, 126, 176);
    private final static Color CONNECTED = new Color(111, 116, 166);
    private final static Color SERVER_INFO = new Color(145, 169, 81);
    private final static Color USED = new Color(0, 0, 0);

    public enum Type {

        MESSAGE, YOUR_MESSAGE, ENEMY_MESSAGE, CONNECTED, SERVER_INFO, USED
    }

    public LobbyScreen() {
//        setPreferredSize(new Dimension(16 * 2 + 192, 380));
        setPreferredSize(new Dimension(640, 480));
        nickname = new JTextField(Utility.getRandomName());
        ip = new JTextField("localhost");
        ip.setEditable(false);
        message = new JTextField("message");
        message.setVisible(false);

        setLayout(null);

        server = getButton("Server", "START_SERVER", 16, 16 + 26 + 26 + 16, 96, 26);
        client = getButton("Client", "START_CLIENT", 16 + 96, 16 + 26 + 26 + 16, 96, 26);
        send = getButton("send", "SEND_MESSAGE", 16 + 192 - 64, 16 + 8 + 256, 64, 26, false);

        tPane = new JTextPane();
//        EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
//        tPane.setBorder(eb);
//        tPane.setMargin(new Insets(5, 5, 5, 5));
        tPane.setBounds(16, 16, 192, 256);
        tPane.setVisible(false);
        tPane.setEditable(false);
        add(tPane);

//        appendToPane(tPane, "TEST", Color.red);
//        chatArea = new JTextArea(16, 16);
//        chatArea.setVisible(false);
//        chatArea.setBounds(16, 16, 192, 256);
//        chatArea.setLineWrap(true);
//        chatArea.setWrapStyleWord(true);
        //add(chatArea);
        clients = new JList<>();
//        clients.setPreferredSize(new Dimension(192, 256));
        clients.setBounds(256, 16, 192, 256);
        final ArrayList<AbstractPlayer> players = Server.getPlayers();
        clients.setListData(players.toArray(new AbstractPlayer[players.size()]));
        //clients.setVisible(false);

        nickname.setBounds(16, 16, 192, 26);
        add(nickname);
        ip.setBounds(16, 16 + 26 + 8, 192, 26);
        add(ip);

        add(clients);

        message.setBounds(16, 16 + 8 + 256, 192 - 64, 26);
        add(message);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(server.getActionCommand())) {
            server.setVisible(false);
            client.setVisible(false);
            ip.setVisible(false);
            nickname.setVisible(false);
            //
//            chatArea.setVisible(true);
            tPane.setVisible(true);
            message.setVisible(true);
            send.setVisible(true);
            try {
                s = Server.getInstance();
                c1 = Client.getInstance();
                c1.send(new MessageConnected(nickname.getText()));
            } catch (IOException ex) {
            }
        }
        if (e.getActionCommand().equals(client.getActionCommand())) {
            server.setVisible(false);
            client.setVisible(false);
            ip.setVisible(false);
            nickname.setVisible(false);
            //
            //chatArea.setVisible(true);
            tPane.setVisible(true);
            message.setVisible(true);
            send.setVisible(true);
            try {
                c1 = Client.getInstance();
                c1.send(new MessageConnected(nickname.getText()));
            } catch (IOException ex) {
            }

        }

        if (e.getActionCommand().equals(send.getActionCommand())) {
            try {
                c1.send(new MessageChat(player.getName(), message.getText(), player.getId()));
                message.setText("");
            } catch (IOException ex) {
            }
        }
    }

    public static Server getS() {
        return s;
    }

    public static Player getPlayer() {
        return player;
    }
    
    

    public static void addToChat(String str, boolean full, Type type) {
        Color color = Color.MAGENTA;

        switch (type) {
            case MESSAGE:
                color = MESSAGE;
                break;
            case YOUR_MESSAGE:
                color = YOUR_NICK;
                break;
            case ENEMY_MESSAGE:
                color = OTHER_NICK;
                break;
            case CONNECTED:
                color = CONNECTED;
                break;
        }
        String next = full ? "\r\n" : "";
        appendToPane(tPane, str + next, color);

    }

    public static void sendToClients(Message m) {
        try {
            s.sendToAll(m);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    public static void addToClients(AbstractPlayer player) {
        final ArrayList<AbstractPlayer> players = Server.getPlayers();
        players.add(player);
        clients.setListData(players.toArray(new AbstractPlayer[players.size()]));
    }

    public static void setPlayer(Player p) {
        player = p;
    }

    private JButton getButton(String title, String action, int x, int y, int w, int h) {
        return getButton(title, action, x, y, w, h, true);
    }

    private JButton getButton(String title, String action, int x, int y, int w, int h, boolean visible) {
        JButton button = new JButton(title);
        button.setBounds(x, y, w, h);
        button.setActionCommand(action);
        button.addActionListener(this);
        button.setVisible(visible);
        add(button);
        return button;
    }

    private static void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        try {
            StyledDocument doc = tp.getStyledDocument();
            doc.insertString(doc.getLength(), msg, aset);
        } catch (BadLocationException ex) {
        }

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}
