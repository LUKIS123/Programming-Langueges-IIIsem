package pl.edu.pwr.lgawron.lab07.common.input;

public class ValuesHolder {
    private String server;
    private int port;

    public ValuesHolder() {
    }

    public void setApplicationArguments(String server, String port) throws InvalidInputException {
        if (port.length() != 4) {
            throw new InvalidInputException("Wrong port");
        }
        this.server = server;
        try {
            this.port = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Port is not a String");
        }
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }
}