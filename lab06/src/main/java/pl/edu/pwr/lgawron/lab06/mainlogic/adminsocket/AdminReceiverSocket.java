package pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket;

import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.models.PlayerRequest;
import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.parse.ClientCommandParser;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.queue.RequestQueue;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class AdminReceiverSocket {
    private Thread thread;
    private int port;
    private ServerSocket serverSocket;
    private boolean exit;
    private final RequestQueue requestQueue;

    public AdminReceiverSocket(int port, RequestQueue requestQueue) {
        this.port = port;
        this.requestQueue = requestQueue;
        this.exit = false;

        this.startListening();
    }

    public void startListening() {
        thread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("ServerSocketSetup");

                while (!exit) {

                    Socket pSocket = serverSocket.accept();
                    DataInputStream ins = new DataInputStream(pSocket.getInputStream());
                    InputStreamReader isr = new InputStreamReader(ins);
                    BufferedReader br = new BufferedReader(isr);
                    String theLine = br.readLine();

                    // do wywalenia
                    System.out.println(theLine);

                    // command: playerId, type, coordinates itd
                    String s = newLineSignRemover(theLine);
                    requestQueue.addElement(this.parseRequest(s));

                    // closing the non-server socket
                    pSocket.close();
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

    private PlayerRequest parseRequest(String s) {
        String[] split = s.split(";");
        if (split[1].equals("register")) {
            return ClientCommandParser.pareRegisterRequest(split);
        }
        if (split[1].equals("see")) {
            return ClientCommandParser.parseSeeRequest(split);
        }
        if (split[1].equals("move")) {
            return ClientCommandParser.parseMoveRequest(split);
        }
        if (split[1].equals("take")) {
            return ClientCommandParser.parseTakeRequest(split);
        }
        return new PlayerRequest("0", "unknown");
    }

    private String newLineSignRemover(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '\n') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    // server port on host
    public int getServerPort() {
        return serverSocket.getLocalPort();
    }

    public boolean checkIfConnected() {
        if (serverSocket == null) {
            return false;
        }
        return serverSocket.isBound();
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

}