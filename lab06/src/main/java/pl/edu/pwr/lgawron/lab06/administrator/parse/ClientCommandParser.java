package pl.edu.pwr.lgawron.lab06.administrator.parse;

import pl.edu.pwr.lgawron.lab06.administrator.models.PlayerRequest;
import pl.edu.pwr.lgawron.lab06.administrator.models.PlayerRequestBuilder;

public class ClientCommandParser {
    // commands
    public static PlayerRequest parseRequest(String requestString) {
        String[] split = requestString.split(";");
        return switch (split[1]) {
            case "register" ->
                    new PlayerRequestBuilder(split[0], split[1]).withPort(split[2]).withProxy(split[3]).buildPayerRequest();

            case "see", "leave" -> new PlayerRequestBuilder(split[0], split[1]).buildPayerRequest();

            case "move" -> new PlayerRequestBuilder(split[0], split[1]).withCoordinates(split[2]).buildPayerRequest();

            case "take" ->
                    new PlayerRequestBuilder(split[0], split[1]).withTreasureLocation(split[2]).buildPayerRequest();

            default -> new PlayerRequest("0", "unknown");
        };
    }

}
