package pl.edu.pwr.lgawron.lab06.administrator.utils;

import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry.Point2D;

import java.util.List;

public class MessageParser {
    // command: playerId, type, coordinates itd
    public static String createRegisterMessage(int newPlayerId, int newServerPort, int sizeX, int sizeY, Point2D position) {
        return newPlayerId + ";register;" + newServerPort + ";" + sizeX + "," + sizeY + ";" + position.getPositionX() + "," + position.getPositionY() + "\n";
    }

    public static String createSeeMessage(int playerId, List<String> mapFragment) {
        return playerId + ";see;" + mapFragment.get(0) + ":" + mapFragment.get(1) + ":" + mapFragment.get(2) + "\n";
    }

    public static String createMoveMessage(int playerId, int positionX, int positionY) {
        return playerId + ";move;" + positionX + "," + positionY + "\n";
    }
}
