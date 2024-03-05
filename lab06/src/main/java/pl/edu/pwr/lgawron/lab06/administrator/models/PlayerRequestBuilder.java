package pl.edu.pwr.lgawron.lab06.administrator.models;

public class PlayerRequestBuilder {
    private final int playerId;
    private final RequestType type;
    private String proxyAddress;
    private int clientServerPort;
    private boolean isRegisterRequest = false;
    private int moveX = 0;
    private int moveY = 0;
    private boolean isMoveRequest = false;
    private int treasureX;
    private int treasureY;
    private boolean isTakeRequest = false;

    public PlayerRequestBuilder(String playerId, String typeName) {
        this.playerId = Integer.parseInt(playerId);
        this.type = this.parseType(typeName);
    }

    private RequestType parseType(String type) {
        return switch (type) {
            case "register" -> RequestType.REGISTER;
            case "see" -> RequestType.SEE;
            case "move" -> RequestType.MOVE;
            case "take" -> RequestType.TAKE;
            case "logout" -> RequestType.GAME_OVER;
            case "exit" -> RequestType.EXIT;
            case "finish_registration" -> RequestType.FINISH_REGISTRATION;
            case "leave" -> RequestType.LEAVE;
            default -> RequestType.UNKNOWN;
        };
    }

    public PlayerRequestBuilder withPort(String port) {
        clientServerPort = Integer.parseInt(port);
        isRegisterRequest = true;
        return this;
    }

    public PlayerRequestBuilder withProxy(String proxy) {
        if (proxy.equals("0.0.0.0/0.0.0.0")) {
            proxyAddress = ("localhost");
        } else {
            proxyAddress = proxy;
        }
        isRegisterRequest = true;
        return this;
    }

    public PlayerRequestBuilder withCoordinates(String coordinates) {
        String[] split = coordinates.split(",");
        moveX = Integer.parseInt(split[0]);
        moveY = Integer.parseInt(split[1]);
        isMoveRequest = true;
        return this;
    }

    public PlayerRequestBuilder withTreasureLocation(String coordinates) {
        String[] split = coordinates.split(",");
        treasureX = Integer.parseInt(split[0]);
        treasureY = Integer.parseInt(split[1]);
        isTakeRequest = true;
        return this;
    }

    public PlayerRequest buildPayerRequest() {
        return new PlayerRequest(this);
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getProxyAddress() {
        return proxyAddress;
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

    public boolean isRegisterRequest() {
        return isRegisterRequest;
    }

    public boolean isMoveRequest() {
        return isMoveRequest;
    }

    public boolean isTakeRequest() {
        return isTakeRequest;
    }

}
