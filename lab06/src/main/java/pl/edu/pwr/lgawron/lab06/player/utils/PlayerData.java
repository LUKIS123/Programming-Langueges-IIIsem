package pl.edu.pwr.lgawron.lab06.player.utils;

import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    private final int receiverPort;
    private final int id;
    private final Point2D point2D;
    // grid of objects?
    private List<List<String>> playerGrid;

    public PlayerData(int receiverPort, int id, int posX, int posY) {
        this.receiverPort = receiverPort;
        this.id = id;
        this.point2D = new Point2D(posX, posY);
        this.playerGrid = new ArrayList<>();
    }

    public void fillGrid(int sizeX, int sizeY) {
        for (int i = 0; i < sizeY; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < sizeX; j++) {
                row.add(j, "_");
            }
            playerGrid.add(i, row);
        }
    }

    public void replaceTile(int x, int y, String s) {
        List<String> list = playerGrid.get(y);
        list.set(x, s);
    }

    public void setPlayerGrid(List<List<String>> playerGrid) {
        this.playerGrid = playerGrid;
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

    public List<List<String>> getPlayerGrid() {
        return playerGrid;
    }

    public void setNewPosition(int x, int y) {
        point2D.setPositionX(x);
        point2D.setPositionY(y);
    }
}
