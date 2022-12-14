package pl.edu.pwr.lgawron.lab06.administrator;

public class MessageParser {
    //komenda: playerId, type, coordinates itd
    public static String createRegisterMessage(int newPlayerId, int newServerPort) {
        return newPlayerId + ";register;" + newServerPort + "\n";
    }
}
