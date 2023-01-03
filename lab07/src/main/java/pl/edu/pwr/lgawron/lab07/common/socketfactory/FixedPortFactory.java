package pl.edu.pwr.lgawron.lab07.common.socketfactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

public class FixedPortFactory extends RMISocketFactory {
    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return null;
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        return null;
    }
}
