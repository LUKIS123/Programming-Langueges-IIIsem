package pl.edu.pwr.lgawron.lab06.common.sockets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class SenderSocket {
    // command: playerId, type, coordinates etc
    public void sendMessage(int port, String host, String message) {
        try {
            Socket soc = new Socket(host, port);
            OutputStream out = soc.getOutputStream();
            PrintWriter pw = new PrintWriter(out, false);
            pw.println(message);
            pw.flush();
            pw.close();
            soc.close();
        } catch (SocketException socketException) {
            System.out.println("SenderException");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}