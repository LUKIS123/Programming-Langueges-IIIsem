package pl.edu.pwr.lgawron.lab06.administrator.adminsocket.registration;

import pl.edu.pwr.lgawron.lab06.administrator.parse.ClientCommandParser;
import pl.edu.pwr.lgawron.lab06.administrator.queue.RequestQueue;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class RegistrationReceiverSocket {
    private Thread thread;
    private final int port;
    private ServerSocket serverSocket;
    private boolean exit;
    private final RequestQueue requestQueue;

    public RegistrationReceiverSocket(int port, RequestQueue requestQueue) {
        this.port = port;
        this.requestQueue = requestQueue;
        this.exit = false;
    }

    public void start() {
        thread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("REGISTRATION-SocketSetup");

                while (!exit) {
                    Socket pSocket = serverSocket.accept();
                    DataInputStream ins = new DataInputStream(pSocket.getInputStream());
                    InputStreamReader isr = new InputStreamReader(ins);
                    BufferedReader br = new BufferedReader(isr);
                    String theLine = br.readLine();

                    System.out.println(theLine);

                    // command: playerId, type, coordinates itd
                    String s = removeNewLineSignRemove(theLine);
                    requestQueue.addElement(ClientCommandParser.parseRequest(s));

                    // closing the non-server socket
                    pSocket.close();
                }

            } catch (SocketException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    private String removeNewLineSignRemove(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '\n') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public void kilThread() {
        thread.interrupt();
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException ignored) {
        }
        System.out.println("RegisterSocket closed");
    }

}
