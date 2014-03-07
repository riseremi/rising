package org.rising.framework.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.rising.framework.network.messages.Message;

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
                        Message s = (Message) in.readObject();
                        System.out.println("CLIENT RECIEVED: " + s.getType().name());
                        Protocol.processMessageOnClientSide(s);
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
