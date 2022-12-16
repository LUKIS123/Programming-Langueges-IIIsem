package pl.edu.pwr.lgawron.lab06.player.utils;

public class PlayerRequestParser {
    //command: playerId, type, coordinates itd
    public static String registerRequest(int port) {
        return 0 + ";register;" + port + "\n";
    }

    public static String seeRequest(int id) {
        return id + ";see\n";
    }

    public static String moveUpRequest(int id) {
        return id + ";move;0," + "-1\n";
    }

    public static String moveLeftRequest(int id) {
        return id + ";move;-1," + "0\n";
    }

    public static String moveRightRequest(int id) {
        return id + ";move;1," + "0\n";
    }

    public static String moveDownRequest(int id) {
        return id + ";move;0," + "1\n";
    }
}
