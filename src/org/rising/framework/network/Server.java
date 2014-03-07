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
    private AbstractPlayer player;
    private int i;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                        clients.add(new Connection(socket, i++));
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

    public void sendToOne(Object message, int index) throws IOException {
        clients.get(index).send(message);
    }

    private class Connection {
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

        public void send(Object message) throws IOException {
            out.writeObject(message);
        }
    }
}
