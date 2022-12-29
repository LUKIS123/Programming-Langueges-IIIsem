package pl.edu.pwr.lgawron.lab06.player.utils;

import javafx.util.Pair;
import pl.edu.pwr.lgawron.lab06.common.game.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    private final int id;
    private final Point2D location2DPoint;
    private final List<List<String>> playerGrid;
    private boolean possibleCurrentSpotTreasure;
    private int treasuresPicked;
    private int treasurePickedWaitTime;
    private int movingDirectionX;
    private int movingDirectionY;

    public PlayerData(int id, int posX, int posY) {
        this.id = id;
        this.location2DPoint = new Point2D(posX, posY);
        this.playerGrid = new ArrayList<>();
        this.possibleCurrentSpotTreasure = false;
        this.treasuresPicked = 0;
        this.treasurePickedWaitTime = 0;
        this.movingDirectionX = 0;
        this.movingDirectionY = 0;
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

    public void replaceGridTile(int x, int y, String s) {
        playerGrid.get(y).set(x, s);
    }

    // AI utility
    public void setPossibleCurrentSpotTreasure(int waitTime) {
        if (waitTime != 0) possibleCurrentSpotTreasure = true;
        else possibleCurrentSpotTreasure = false;
    }

    // AI utility
    public boolean checkIfPositionPossibleToMove(int x, int y, Pair<Integer, Integer> dimensions) {
        if (location2DPoint.getPositionY() + y < 0 || location2DPoint.getPositionY() + y >= dimensions.getValue()
                || location2DPoint.getPositionX() + x < 0 || location2DPoint.getPositionX() >= dimensions.getKey()) {
            return false;
        }
        if ((x == 0 && y == 0)) {
            return false;
        }

        try {
            String gameObject = playerGrid.get(location2DPoint.getPositionY() + y).get(location2DPoint.getPositionX() + x);
            if (gameObject.equals("*") || gameObject.equals("T")) {
                return true;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return false;
    }

    public int getId() {
        return id;
    }

    public Point2D getLocation2DPoint() {
        return location2DPoint;
    }

    public List<List<String>> getPlayerGrid() {
        return playerGrid;
    }

    public void setNewPosition(int x, int y) {
        location2DPoint.setPositionX(x);
        location2DPoint.setPositionY(y);
    }

    public boolean isPossibleCurrentSpotTreasure() {
        return possibleCurrentSpotTreasure;
    }

    public void setPossibleCurrentSpotTreasure(boolean possibleCurrentSpotTreasure) {
        this.possibleCurrentSpotTreasure = possibleCurrentSpotTreasure;
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

    public int getMovingDirectionX() {
        return movingDirectionX;
    }

    public int getMovingDirectionY() {
        return movingDirectionY;
    }

    public void setMovingDirectionX(int movingDirectionX) {
        this.movingDirectionX = movingDirectionX;
    }

    public void setMovingDirectionY(int movingDirectionY) {
        this.movingDirectionY = movingDirectionY;
    }
}
