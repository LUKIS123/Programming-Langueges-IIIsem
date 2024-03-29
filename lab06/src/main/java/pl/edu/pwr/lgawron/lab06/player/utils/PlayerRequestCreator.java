package pl.edu.pwr.lgawron.lab06.player.utils;

import pl.edu.pwr.lgawron.lab06.common.game.geometry.Point2D;

import java.net.InetAddress;

public class PlayerRequestCreator {
    //command: playerId, type, coordinates itd
    public static String registerRequest(int port, InetAddress inetAddress) {
        return 0 + ";register;" + port + ";" + inetAddress + "\n";
    }

    public static String seeRequest(int id) {
        return id + ";see\n";
    }

    public static String moveUpRequest(int id, int direction) {
        return id + ";move;" + direction + "," + "-1\n";
    }

    public static String moveLeftRequest(int id) {
        return id + ";move;-1," + "0\n";
    }

    public static String moveRightRequest(int id) {
        return id + ";move;1," + "0\n";
    }

    public static String moveDownRequest(int id, int direction) {
        return id + ";move;" + direction + "," + "1\n";
    }

    public static String artificialMoveRequest(int id, int x, int y) {
        return id + ";move;" + x + "," + y + "\n";
    }

    public static String takeTreasureRequest(int id, Point2D point2D) {
        return id + ";take;" + point2D.getPositionX() + "," + point2D.getPositionY() + "\n";
    }

    public static String leaveGameRequest(int id) {
        return id + ";leave";
    }
}
