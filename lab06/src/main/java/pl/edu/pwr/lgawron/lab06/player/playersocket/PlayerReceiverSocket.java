package pl.edu.pwr.lgawron.lab06.player.playersocket;

import pl.edu.pwr.lgawron.lab06.common.sockets.SenderSocket;
import pl.edu.pwr.lgawron.lab06.player.executor.ServerResponseQueue;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerRequestCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class PlayerReceiverSocket {
    private Thread thread;
    private final int port;
    private final String proxy;
    private ServerSocket serverSocket;
    private final SenderSocket senderSocket;
    private final ServerResponseQueue serverResponseQueue;
    private boolean exit;

    public PlayerReceiverSocket(int port, String proxy, SenderSocket senderSocket, ServerResponseQueue serverResponseQueue) {
        this.port = port;
        this.proxy = proxy;
        this.senderSocket = senderSocket;
        this.serverResponseQueue = serverResponseQueue;
        this.exit = false;
    }

    public void start() {
        thread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(0);
                System.out.println("PlayerServerSocketSetup");
                senderSocket.sendMessage(
                        port, proxy, PlayerRequestCreator.registerRequest(serverSocket.getLocalPort(), serverSocket.getInetAddress())
                );

                while (!exit) {
                    Socket sc = serverSocket.accept();
                    InputStream is = sc.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String theLine = br.readLine();

                    System.out.println(theLine);

                    String s = removeNewLineSign(theLine);
                    serverResponseQueue.addTask(s.split(";"));

                    sc.close();
                }
            } catch (SocketException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    private String removeNewLineSign(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '\n') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void exit(boolean exit) {
        this.exit = exit;
        thread.interrupt();
        try {
            serverSocket.close();
        } catch (IOException ignored) {
        }
        System.out.println("Player Socket Closed");
    }
}
