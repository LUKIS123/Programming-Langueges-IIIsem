package pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances;

public class PlayerInstance {
    private final int id;
    private final int serverPort;

    public PlayerInstance(int id, int serverPort) {
        this.id = id;
        this.serverPort = serverPort;
    }

    public int getId() {
        return id;
    }

    public int getServerPort() {
        return serverPort;
    }
}
