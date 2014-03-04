package org.rising.framework.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Riseremi
 */
public class Client {
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Client(int port, String ip) throws IOException {
        Socket s = new Socket(ip, port);

        out = new ObjectOutputStream(s.getOutputStream());
        out.flush();
        in = new ObjectInputStream(s.getInputStream());

        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Object s = in.readObject();
                        System.out.println("Server says: " + s.toString());
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
