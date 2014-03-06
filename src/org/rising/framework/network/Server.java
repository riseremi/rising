package org.rising.framework.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.rising.player.AbstractPlayer;

/**
 *
 * @author Riseremi
 */
public class Server {
    private ServerSocket serverSocket;
    private ArrayList<Connection> clients = new ArrayList<>();
    //
    private AbstractPlayer players;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                        clients.add(new Connection(socket));
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

    private class Connection {
        private ObjectInputStream in;
        private ObjectOutputStream out;

        public Connection(Socket socket) throws IOException {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            Thread t = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Object s = in.readObject();
                            System.out.println("Client says: " + s.toString());
                            send("message that should be returned back to the client" + s.toString());
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
