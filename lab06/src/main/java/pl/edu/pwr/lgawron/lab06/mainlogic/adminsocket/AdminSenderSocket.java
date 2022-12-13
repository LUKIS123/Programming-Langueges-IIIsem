package pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class AdminSenderSocket {

    public void sendResponse(int port, String host, String command) {
        try {
            Socket soc = new Socket("localhost", port);


            DataOutputStream outputStream = new DataOutputStream(soc.getOutputStream());
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


//https://stackoverflow.com/questions/2675362/how-to-find-an-available-port -> wanze
