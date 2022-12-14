package pl.edu.pwr.lgawron.lab06.player.utils;

public class PlayerRequestParser {
    //komenda: playerId, type, coordinates itd
    public static String registerRequest(int port) {
        return 0 + ";register;" + port + "\n";
    }
}
