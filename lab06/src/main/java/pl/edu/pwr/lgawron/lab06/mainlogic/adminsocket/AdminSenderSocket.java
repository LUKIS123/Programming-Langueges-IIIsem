package pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class AdminSenderSocket {
    // command: playerId, type, coordinates etc
    public void sendResponse(int port, String host, String message) {
        try {

            Socket soc = new Socket(host, port);
            OutputStream out = soc.getOutputStream();
            PrintWriter pw = new PrintWriter(out, false);
            pw.println(message);
            pw.flush();
            pw.close();
            soc.close();

        } catch (SocketException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}