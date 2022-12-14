package pl.edu.pwr.lgawron.lab06.player.utils;

public class PlayerData {
    private final int receiverPort;
    private final int id;

    public PlayerData(int receiverPort, int id) {
        this.receiverPort = receiverPort;
        this.id = id;
    }

    public int getReceiverPort() {
        return receiverPort;
    }

    public int getId() {
        return id;
    }
}
