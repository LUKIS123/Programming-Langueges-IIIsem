package pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.models;

public class PlayerRequest {
    private final int playerId;
    private final RequestType type;
    private int serverPort;

    public PlayerRequest(String playerId, String type) {
        this.playerId = this.getIntValue(playerId);
        this.type = this.checkType(type);
    }

    private RequestType checkType(String type) {
        if (type.equals("register")) {
            return RequestType.REGISTER;
        }
        return RequestType.UNKNOWN;
    }

    private int getIntValue(String s) {
        return Integer.parseInt(s);
    }

    public PlayerRequest withPort(int port) {
        serverPort = port;
        return this;
    }

    public int getPlayerId() {
        return playerId;
    }

    public RequestType getType() {
        return type;
    }

    public int getServerPort() {
        return serverPort;
    }
}
