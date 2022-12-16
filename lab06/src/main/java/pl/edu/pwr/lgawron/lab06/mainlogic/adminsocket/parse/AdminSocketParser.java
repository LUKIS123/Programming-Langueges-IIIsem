package pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.parse;

import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.models.PlayerRequest;

public class AdminSocketParser {
    // command
    public static PlayerRequest pareRegisterRequest(String[] split) {
        return new PlayerRequest(split[0], split[1]).withPort(split[2]);
    }

    public static PlayerRequest parseSeeRequest(String[] split) {
        return new PlayerRequest(split[0], split[1]);
    }

    public static PlayerRequest parseMoveRequest(String[] split) {
        return new PlayerRequest(split[0], split[1]).withCoordinates(split[2]);
    }
}
