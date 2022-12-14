package pl.edu.pwr.lgawron.lab06.mainlogic.playersocket;

import pl.edu.pwr.lgawron.lab06.player.PlayerRequestParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class PlayerReceiverSocket {
    private Thread thread;
    private int port;
    String proxy;
    private ServerSocket serverSocket;
    private final PlayerSenderSocket senderSocket;
    private boolean exit;

    public PlayerReceiverSocket(int port, String proxy, PlayerSenderSocket senderSocket) {
        this.port = port;
        this.proxy = proxy;
        this.senderSocket = senderSocket;
        this.exit = false;
    }

    public void start() {
        thread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(0);
                System.out.println("PlayerServerSocketSetup");
                senderSocket.sendRequest(port, proxy, PlayerRequestParser.registerRequest(serverSocket.getLocalPort()));


                while (!exit) {
                    Socket sc = serverSocket.accept();
                    InputStream is = sc.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String theLine = br.readLine();

                    System.out.println(theLine);


                    sc.close();
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
