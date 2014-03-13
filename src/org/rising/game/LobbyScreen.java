package org.rising.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
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
import org.rising.framework.network.messages.MessageChatPrivate;
import org.rising.framework.network.messages.MessageConnected;
import org.rising.framework.network.messages.MessageStartGame;
import org.rising.player.AbstractPlayer;
import org.rising.player.Player;

/**
 *
 * @author Riseremi
 */
public class LobbyScreen extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private static JList<AbstractPlayer> clientsList;
    private JTextField nickname, ip, message;
    private JButton server, client, start, off;
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
    private final JScrollPane jsp;

    public enum Type {

        MESSAGE, YOUR_MESSAGE, ENEMY_MESSAGE, CONNECTED, SERVER_INFO, USED
    }

    public LobbyScreen() {
//        setPreferredSize(new Dimension(16 * 2 + 192, 380));
        setPreferredSize(new Dimension(640, 480));
        nickname = new JTextField(Utility.getRandomName());
        ip = new JTextField("localhost");
        //ip.setEditable(false);
        message = new JTextField("message");
        message.setVisible(false);

        message.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = message.getText().trim();
                    if (!text.isEmpty()) {
                        c1.send(new MessageChat(player.getName(), text, player.getId()));
                        message.setText("");
                    }
                } catch (IOException ex) {
                }
            }
        });

        setLayout(null);

        server = getButton("Server", "START_SERVER", 16, 16 + 26 + 26 + 16, 96, 26);
        client = getButton("Client", "START_CLIENT", 16 + 96, 16 + 26 + 26 + 16, 96, 26);
        off = getButton("RAGEQUIT", "TERMINATE", 16, 16 + 8 + 256 + 26 + 16, 192, 26, false);

        start = getButton("Start", "START", 16, 16 + 8 + 256 + 26 + 16 + 26 + 16, 192, 26, false);

        tPane = new JTextPane();
        jsp = new JScrollPane(tPane);
        tPane.setBounds(16, 16, 192, 256);
        jsp.setBounds(16, 16, 192, 256);
        jsp.setVisible(false);
        tPane.setEditable(false);
        add(jsp);
        
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        clientsList = new JList<>();
        clientsList.setBounds(256, 16, 192, 256);
        final ArrayList<AbstractPlayer> players = Server.getPlayers();
        clientsList.setListData(players.toArray(new AbstractPlayer[players.size()]));

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    AbstractPlayer selectedPlayer = clientsList.getSelectedValue();
                    String text = message.getText().trim();
                    MessageChatPrivate mcp = new MessageChatPrivate(player.getName(), text, player.getId(), selectedPlayer.getId());

                    try {
                        if (!text.isEmpty()) {
                            c1.send(mcp);
                            message.setText("");
                        }
                    } catch (IOException ex) {
                    }

                }
            }
        };
        clientsList.addMouseListener(mouseListener);

        nickname.setBounds(16, 16, 192, 26);
        add(nickname);
        ip.setBounds(16, 16 + 26 + 8, 192, 26);
        add(ip);

        add(clientsList);

        message.setBounds(16, 16 + 8 + 256, 192, 26);
        add(message);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(server.getActionCommand())) {
            off.setVisible(true);
            start.setVisible(true);
            
            server.setVisible(false);
            client.setVisible(false);
            ip.setVisible(false);
            nickname.setVisible(false);
            jsp.setVisible(true);
            message.setVisible(true);
            try {
                Server.SERVER_IP = ip.getText();
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
            jsp.setVisible(true);
            message.setVisible(true);
            try {
                Server.SERVER_IP = ip.getText();
                c1 = Client.getInstance();
                c1.send(new MessageConnected(nickname.getText()));
            } catch (IOException ex) {
            }

        }
        if (e.getActionCommand().equals(off.getActionCommand())) {
            s.terminate();
        }

        if (e.getActionCommand().equals(start.getActionCommand())) {
            System.out.println("start game s");
            try {
                s.sendToAll(new MessageStartGame());
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
            case SERVER_INFO:
                color = SERVER_INFO;
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
        clientsList.setListData(players.toArray(new AbstractPlayer[players.size()]));
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
