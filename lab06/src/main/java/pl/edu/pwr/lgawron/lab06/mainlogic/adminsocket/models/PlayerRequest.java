package pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.models;

public class PlayerRequest {
    private final int playerId;
    private final RequestType type;
    private int clientServerPort;
    private int moveX = 0;
    private int moveY = 0;
    private int treasureX;
    private int treasureY;

    public PlayerRequest(String playerId, String type) {
        this.playerId = this.getIntValue(playerId);
        this.type = this.checkType(type);
    }

    private RequestType checkType(String type) {
        if (type.equals("register")) {
            return RequestType.REGISTER;
        }
        if (type.equals("see")) {
            return RequestType.SEE;
        }
        if (type.equals("move")) {
            return RequestType.MOVE;
        }
        if (type.equals("take")) {
            return RequestType.TAKE;
        }
        if (type.equals("logout")) {
            return RequestType.LOGOUT;
        }
        return RequestType.UNKNOWN;
    }

    private int getIntValue(String s) {
        return Integer.parseInt(s);
    }

    public PlayerRequest withPort(String port) {
        clientServerPort = this.getIntValue(port);
        return this;
    }

    public PlayerRequest withCoordinates(String coordinates) {
        String[] split = coordinates.split(",");
        moveX = Integer.parseInt(split[0]);
        moveY = Integer.parseInt(split[1]);
        return this;
    }

    public PlayerRequest withTreasureLocation(String coordinates) {
        String[] split = coordinates.split(",");
        treasureX = Integer.parseInt(split[0]);
        treasureY = Integer.parseInt(split[1]);
        return this;
    }

    public int getPlayerId() {
        return playerId;
    }

    public RequestType getType() {
        return type;
    }

    public int getClientServerPort() {
        return clientServerPort;
    }

    public int getMoveX() {
        return moveX;
    }

    public int getMoveY() {
        return moveY;
    }

    public int getTreasureX() {
        return treasureX;
    }

    public int getTreasureY() {
        return treasureY;
    }
}
