package pl.edu.pwr.lgawron.lab06.administrator.parse;

import pl.edu.pwr.lgawron.lab06.administrator.models.PlayerRequest;

public class ClientCommandParser {
    // commands
    public static PlayerRequest pareRegisterRequest(String[] split) {
        return new PlayerRequest(split[0], split[1]).withPort(split[2]).withProxy(split[3]);
    }

    public static PlayerRequest parseSeeRequest(String[] split) {
        return new PlayerRequest(split[0], split[1]);
    }

    public static PlayerRequest parseMoveRequest(String[] split) {
        return new PlayerRequest(split[0], split[1]).withCoordinates(split[2]);
    }

    public static PlayerRequest parseTakeRequest(String[] split) {
        return new PlayerRequest(split[0], split[1]).withTreasureLocation(split[2]);
    }
}
