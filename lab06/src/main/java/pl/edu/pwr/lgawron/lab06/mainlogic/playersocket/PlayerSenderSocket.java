package pl.edu.pwr.lgawron.lab06.mainlogic.playersocket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class PlayerSenderSocket {
    public void sendRequest(int port, String command) {
        try {
            Socket soc = new Socket("localhost", port);
            // while (!exit) {

            DataOutputStream outputStream = new DataOutputStream(
                    soc.getOutputStream()
            );
            outputStream.writeUTF("Hello test 123! \n newline");
            outputStream.flush();

            //}
        } catch (SocketException e) {
            // jakos obsluzyc podczas zamyknia socketa
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
