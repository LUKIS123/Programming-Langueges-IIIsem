package pl.edu.pwr.lgawron.lab06.administrator.models;

public class PlayerRequest {
    private final int playerId;
    private String proxyAddress;
    private final RequestType type;
    private int clientServerPort;
    private int moveX = 0;
    private int moveY = 0;
    private int treasureX;
    private int treasureY;

    public PlayerRequest(String playerId, String type) {
        this.playerId = Integer.parseInt(playerId);
        this.type = this.parseType(type);
    }

    public PlayerRequest(PlayerRequestBuilder builder) {
        this.playerId = builder.getPlayerId();
        this.type = builder.getType();
        if (builder.isRegisterRequest()) {
            proxyAddress = builder.getProxyAddress();
            clientServerPort = builder.getClientServerPort();
        } else if (builder.isMoveRequest()) {
            moveX = builder.getMoveX();
            moveY = builder.getMoveY();
        } else if (builder.isTakeRequest()) {
            treasureX = builder.getTreasureX();
            treasureY = builder.getTreasureY();
        }
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

    public int getPlayerId() {
        return playerId;
    }

    public RequestType getType() {
        return type;
    }

    public int getClientServerPort() {
        return clientServerPort;
    }

    public String getProxyAddress() {
        return proxyAddress;
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
