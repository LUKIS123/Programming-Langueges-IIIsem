package pl.edu.pwr.lgawron.lab06.player.parse;

public class ValuesHolder {

    private String server;
    private int port;

    public ValuesHolder() {
    }

    public void setApplicationArguments(String server, String port) throws InputDataException {
        if (port.length() != 4) {
            throw new InputDataException("Wrong port");
        }
        this.server = server;
        try {
            this.port = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            throw new InputDataException("Port is not a String");
        }
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }
}
