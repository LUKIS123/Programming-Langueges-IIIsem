package pl.edu.pwr.lgawron.lab06.player.playersocket;

import pl.edu.pwr.lgawron.lab06.player.ai.TaskRepository;
import pl.edu.pwr.lgawron.lab06.player.flow.PlayerWorker;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerRequestCreator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class PlayerReceiverSocket {
    private Thread thread;
    private final int port;
    private final String proxy;
    private ServerSocket serverSocket;
    private final PlayerSenderSocket senderSocket;
    private final PlayerWorker worker;
    private final TaskRepository taskRepository;
    private boolean exit;

    public PlayerReceiverSocket(int port, String proxy, PlayerSenderSocket senderSocket, PlayerWorker worker, TaskRepository taskRepository) {
        this.port = port;
        this.proxy = proxy;
        this.senderSocket = senderSocket;
        this.worker = worker;
        this.taskRepository = taskRepository;
        this.exit = false;
    }

    public void start() {
        thread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(0);
                System.out.println("PlayerServerSocketSetup");
                senderSocket.sendRequest(
                        port, proxy, PlayerRequestCreator.registerRequest(serverSocket.getLocalPort(), serverSocket.getInetAddress())
                );

                while (!exit) {
                    Socket sc = serverSocket.accept();
                    InputStream is = sc.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String theLine = br.readLine();

                    System.out.println(theLine);

                    String s = newLineSignRemover(theLine);
                    this.handleResponse(s);

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

    private void handleResponse(String s) {
        String[] split = s.split(";");
        String type = split[1];
        if (type.equals("register")) {
            worker.handleRegistrationResponse(Integer.parseInt(split[2]), Integer.parseInt(split[0]), split[3], split[4]);
        } else if (type.equals("over")) {
            worker.handleGameOverResponse(split);
        } else {
            taskRepository.addTask(split);
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
        try {
            serverSocket.close();
        } catch (IOException ignored) {
        }
        System.out.println("Player Socket Closed");
    }
}
