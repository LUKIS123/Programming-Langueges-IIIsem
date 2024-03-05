package pl.edu.pwr.lgawron.lab06.administrator.utils;

import pl.edu.pwr.lgawron.lab06.common.game.geometry.Point2D;
import pl.edu.pwr.lgawron.lab06.common.game.objects.GameInstance;

import java.util.List;
import java.util.Optional;

public class AdminResponseCreator {
    // command: playerId, type, coordinates etc
    public static String createRegisterMessage(int newPlayerId, int newServerPort, int sizeX, int sizeY, Point2D position) {
        return newPlayerId + ";register;" + newServerPort + ";" + sizeX + "," + sizeY + ";" + position.getPositionX() + "," + position.getPositionY() + "\n";
    }

    public static String createSeeMessage(int playerId, List<String> mapFragment) {
        return playerId + ";see;" + mapFragment.get(0) + ":" + mapFragment.get(1) + ":" + mapFragment.get(2) + "\n";
    }

    public static String createMoveMessage(int playerId, int positionX, int positionY, Optional<GameInstance> instanceOptional) {
        if (instanceOptional.isEmpty()) {
            return playerId + ";move;" + positionX + "," + positionY + ";" + 0 + "\n";
        } else {
            int waitingTime = instanceOptional.get().getWaitingTime();
            return playerId + ";move;" + positionX + "," + positionY + ";" + waitingTime + "\n";
        }
    }

    public static String createTakeMessage(int id, boolean takeAttempt, int currentWaitingTime, int howManyTreasuresPicked) {
        return id + ";take;" + takeAttempt + ";" + currentWaitingTime + ";" + howManyTreasuresPicked + "\n";
    }

    public static String createGameOverMessage(int id, int mostTreasuresPicked) {
        return id + ";over;lose;" + mostTreasuresPicked + "\n";
    }

    public static String createYouWonMessage(int id, int mostTreasuresPicked) {
        return id + ";over;win;" + mostTreasuresPicked + "\n";
    }

}
