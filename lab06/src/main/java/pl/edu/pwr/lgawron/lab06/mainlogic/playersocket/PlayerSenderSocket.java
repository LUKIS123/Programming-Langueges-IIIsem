package pl.edu.pwr.lgawron.lab06.mainlogic.playersocket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class PlayerSenderSocket {
    public void sendRequest(int port, String host, String command) {
        try {
            Socket soc = new Socket(host, port);

            DataOutputStream outputStream = new DataOutputStream(
                    soc.getOutputStream()
            );
            PrintWriter pw = new PrintWriter(outputStream, false);
            pw.println(command);
            pw.flush();
            pw.close();
            soc.close();

        } catch (SocketException e) {
            // jakos obsluzyc podczas zamyknia socketa
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
