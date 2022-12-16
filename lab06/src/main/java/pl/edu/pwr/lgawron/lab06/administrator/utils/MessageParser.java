package pl.edu.pwr.lgawron.lab06.administrator.utils;

import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry.Point2D;

import java.util.List;

public class MessageParser {
    //komenda: playerId, type, coordinates itd
    public static String createRegisterMessage(int newPlayerId, int newServerPort, Point2D position) {
        return newPlayerId + ";register;" + newServerPort + ";" + position.getPositionX() + "," + position.getPositionY() + "\n";
    }

    public static String createSeeMessage(int playerId, List<String> mapFragment) {
        return playerId + ";see;" + mapFragment.get(0) + ":" + mapFragment.get(1) + ":" + mapFragment.get(2) + "\n";
    }

    public static String createMoveMessage(int playerId) {
        return playerId + ";move;";
    }
}
