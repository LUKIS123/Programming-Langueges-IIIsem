package pl.edu.pwr.lgawron.lab06.mainlogic.playersocket;

import pl.edu.pwr.lgawron.lab06.player.flow.PlayerWorker;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerRequestParser;

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
    private final PlayerWorker worker;
    private boolean exit;

    public PlayerReceiverSocket(int port, String proxy, PlayerSenderSocket senderSocket, PlayerWorker worker) {
        this.port = port;
        this.proxy = proxy;
        this.senderSocket = senderSocket;
        this.worker = worker;
        this.exit = false;
    }

    public void start() {
        thread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(0);
                System.out.println("PlayerServerSocketSetup");
                senderSocket.sendRequest(
                        port, proxy, PlayerRequestParser.registerRequest(serverSocket.getLocalPort())
                );


                while (!exit) {
                    Socket sc = serverSocket.accept();
                    InputStream is = sc.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String theLine = br.readLine();

                    System.out.println(theLine);
                    String s = newLineSignRemover(theLine);
                    String[] split = s.split(";");
                    this.handleResponse(split);


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

    private void handleResponse(String[] split) {
        String type = split[1];
        if (type.equals("register")) {
            // 1;register;61595

            worker.init(Integer.parseInt(split[2]), Integer.parseInt(split[0]));
        }

    }

    private String newLineSignRemover(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '\n') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
