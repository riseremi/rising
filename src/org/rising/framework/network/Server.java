package org.rising.framework.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.rising.framework.network.messages.Message;
import org.rising.player.AbstractPlayer;

/**
 *
 * @author Riseremi
 */
public class Server {

    private ServerSocket serverSocket;
    private ArrayList<Connection> clients = new ArrayList<>();
    //
    private static final ArrayList<AbstractPlayer> players = new ArrayList<>();
    //

    //
    private int i;
    private static Server instance;

    public static Server getInstance() {
        if (instance == null) {
            try {
                instance = new Server(7777);
                return instance;
            } catch (IOException ex) {
            }
        }
        return instance;
    }

    private Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                        final Connection connection = new Connection(socket, i++);
                        clients.add(connection);
                        //connection.send(connection.getId());
                    } catch (IOException ex) {
                    }
                }
            }
        };
        t.start();
    }

    public void sendToAll(Object message) throws IOException {
        for (Connection connection : clients) {
            connection.send(message);
        }
    }

    public void sendToAllExcludingOne(Object message, int id) throws IOException {
        for (Connection connection : clients) {
            if (connection.getId() != id) {
                connection.send(message);
            }
        }
    }

    public void sendToOne(Object message, int index) throws IOException {
        clients.get(index).send(message);
    }

    static class Connection {

        private ObjectInputStream in;
        private ObjectOutputStream out;
        private final int id;

        public Connection(Socket socket, final int id) throws IOException {
            this.id = id;
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            Thread t = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Message s = (Message) in.readObject();
                            System.out.println("SERVER RECIEVED: " + s.getType().name());
                            Protocol.processMessageOnServerSide(s, id);
                        } catch (IOException | ClassNotFoundException ex) {
                        }
                    }
                }
            };
            t.start();
        }

        public int getId() {
            return id;
        }
        
        

        public void send(Object message) throws IOException {
            out.writeObject(message);
        }

    }

    public static ArrayList<AbstractPlayer> getPlayers() {
        return players;
    }

}
