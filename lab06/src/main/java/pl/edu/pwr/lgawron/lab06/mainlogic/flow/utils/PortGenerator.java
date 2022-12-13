package pl.edu.pwr.lgawron.lab06.mainlogic.flow.utils;

public class PortGenerator {
    private final int serverPort;
    private int sequence;

    public PortGenerator(int serverPort) {
        this.serverPort = serverPort;
        this.sequence = 0;
    }

    public int generateNextPort() {
        sequence++;
        return serverPort + sequence;
    }
}
