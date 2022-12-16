package pl.edu.pwr.lgawron.lab06.player.utils;

import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry.Point2D;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.EnvironmentInstance;

import java.util.List;

public class PlayerData {
    private final int receiverPort;
    private final int id;

    private final Point2D point2D;
    // grid of objects?
    private List<List<String>> surroundingGrid;

    public PlayerData(int receiverPort, int id, int posX, int posY) {
        this.receiverPort = receiverPort;
        this.id = id;
        this.point2D = new Point2D(posX, posY);
    }

    public void setSurroundingGrid(List<List<String>> surroundingGrid) {
        this.surroundingGrid = surroundingGrid;
    }

    public int getReceiverPort() {
        return receiverPort;
    }

    public int getId() {
        return id;
    }

    public Point2D getPoint2D() {
        return point2D;
    }

    public List<List<String>> getSurroundingGrid() {
        return surroundingGrid;
    }
}
