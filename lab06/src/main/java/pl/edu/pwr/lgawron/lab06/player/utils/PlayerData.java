package pl.edu.pwr.lgawron.lab06.player.utils;

import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    private final int receiverPort;
    private final int id;
    private final Point2D point2D;
    private List<List<String>> playerGrid;
    private boolean possibleCurrentSpotTreasure;
    private int possibleTreasureWaitTime;
    private int treasuresPicked;
    private int treasurePickedWaitTime;

    public PlayerData(int receiverPort, int id, int posX, int posY) {
        this.receiverPort = receiverPort;
        this.id = id;
        this.point2D = new Point2D(posX, posY);
        this.playerGrid = new ArrayList<>();
        this.possibleCurrentSpotTreasure = false;
        this.possibleTreasureWaitTime = 0;
        this.treasuresPicked = 0;
        this.treasurePickedWaitTime = 0;
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

    // AI utility
    public void setPossibleCurrentSpotTreasure(int waitTime) {
        if (waitTime == 0) {
            possibleCurrentSpotTreasure = false;
            possibleTreasureWaitTime = 0;
        } else {
            possibleCurrentSpotTreasure = true;
            possibleTreasureWaitTime = waitTime;
        }
    }

    // AI utility
    public boolean checkIfPositionPossibleToMove(int x, int y) {
        try {
            String gameObject = playerGrid.get(point2D.getPositionY() + y).get(point2D.getPositionX() + x);
            if (gameObject.equals("*") || gameObject.equals("T")) {
                return true;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
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

    public boolean isPossibleCurrentSpotTreasure() {
        return possibleCurrentSpotTreasure;
    }

    public int getPossibleTreasureWaitTime() {
        return possibleTreasureWaitTime;
    }

    public void setPossibleCurrentSpotTreasure(boolean possibleCurrentSpotTreasure) {
        this.possibleCurrentSpotTreasure = possibleCurrentSpotTreasure;
    }

    public void setPossibleTreasureWaitTime(int possibleTreasureWaitTime) {
        this.possibleTreasureWaitTime = possibleTreasureWaitTime;
    }

    public int getTreasuresPicked() {
        return treasuresPicked;
    }

    public int getTreasurePickedWaitTime() {
        return treasurePickedWaitTime;
    }

    public void setTreasuresPicked(int treasuresPicked) {
        this.treasuresPicked = treasuresPicked;
    }

    public void setTreasurePickedWaitTime(int treasurePickedWaitTime) {
        this.treasurePickedWaitTime = treasurePickedWaitTime;
    }
}
