package pl.edu.pwr.lgawron.lab06.mainlogic.playersocket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class PlayerReceiverSocket {
    private Thread thread;
    private int port;
    private ServerSocket serverSocket;
    private boolean exit;

    public PlayerReceiverSocket(int port) {
        this.port = port;
        this.exit = false;
    }

    public void start() {
        thread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("ServerSocketSetup");

                while (!exit) {

                    Socket pSocket = serverSocket.accept();
                    DataInputStream dataInputStream = new DataInputStream(pSocket.getInputStream());
                    String message = dataInputStream.readUTF();


                }

            } catch (SocketException e) {
                // jakos obsluzyc podczas zamyknia socketa
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
