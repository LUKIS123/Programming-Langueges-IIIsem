package pl.edu.pwr.lgawron.lab06.player.utils;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.EnvironmentInstance;

import java.io.File;
import java.util.List;

public class PlayerMapRenderer {
    private final List<List<EnvironmentInstance>> gameGrid;
    private final Pair<Integer, Integer> dimensions;
    private final GridPane mapPane;
    private final GridPane playerPane;
    private final Node[][] backgroundTiles;
    private final Node[][] frontTiles;
    private final Image treasureImage = new Image((new File("treasure.png").toURI().toString()));

    public PlayerMapRenderer(GridPane mapPane, GridPane playerPane, List<List<EnvironmentInstance>> gameGrid, Pair<Integer, Integer> dimensions) {
        this.gameGrid = gameGrid;
        this.dimensions = dimensions;
        this.mapPane = mapPane;
        this.playerPane = playerPane;

        // table of tiles on position x,y
        this.backgroundTiles = new Node[dimensions.getKey()][dimensions.getValue()];
        this.frontTiles = new Node[dimensions.getKey()][dimensions.getValue()];
        // fill playerPane
        this.fillPlayerPane();
    }

    public void firstRender() {
        Platform.runLater(() -> {
            // gameMap
            for (List<EnvironmentInstance> environmentInstances : gameGrid) {
                for (EnvironmentInstance environmentInstance : environmentInstances) {
                    Rectangle rect = new Rectangle(50, 50);
                    rect.setFill(Color.TRANSPARENT);

                    int positionX = environmentInstance.getPosition().getPositionX();
                    int positionY = environmentInstance.getPosition().getPositionY();

                    mapPane.add(rect, positionX, positionY);
                    backgroundTiles[positionX][positionY] = rect;
                }
            }
        });
    }

    private void fillPlayerPane() {
        for (int i = 0; i < dimensions.getValue(); i++) {
            for (int j = 0; j < dimensions.getKey(); j++) {
                Rectangle rect = new Rectangle(50, 50);
                rect.setFill(Color.TRANSPARENT);
                playerPane.add(rect, j, i);
                frontTiles[j][i] = rect;
            }
        }
    }

    public void renderPlayerSpawned(int x, int y, PlayerData playerData) {
        Platform.runLater(() -> {
            playerPane.getChildren().remove(frontTiles[x][y]);

            Label playerLabel = new Label("Player " + playerData.getId());
            playerLabel.setAlignment(Pos.CENTER);
            playerLabel.setPrefSize(50, 50);
            playerLabel.setStyle("-fx-background-color: magenta");
            playerPane.add(playerLabel, x, y);

            frontTiles[x][y] = playerLabel;
        });
    }

    public void renderSee(int x, int y, String s) {
        Platform.runLater(() -> {
            Rectangle rectangle = new Rectangle(50, 50);
            if (s.equals("-")) {
                return;
            }
            if (s.equals("*")) {
                rectangle.setStyle("-fx-fill: gold");
            }
            if (s.equals("#")) {
                rectangle.setStyle("-fx-fill: grey");
            }
            if (s.equals("T")) {
                rectangle.setFill(new ImagePattern(treasureImage));
            }
            if (s.equals("P")) {
                rectangle.setStyle("-fx-fill: red");
            }
            mapPane.getChildren().remove(backgroundTiles[x][y]);
            mapPane.add(rectangle, x, y);
            backgroundTiles[x][y] = rectangle;
        });
    }

    public void renderMove(int x, int y, int newX, int newY, PlayerData playerData) {
        Platform.runLater(() -> {
            playerPane.getChildren().remove(frontTiles[x][y]);

            Rectangle rect = new Rectangle(50, 50);
            rect.setFill(Color.TRANSPARENT);
            playerPane.add(rect, x, y);
            frontTiles[x][y] = rect;

            playerPane.getChildren().remove(frontTiles[newX][newY]);

            Label playerLabel = new Label("Player " + playerData.getId());
            playerLabel.setAlignment(Pos.CENTER);
            playerLabel.setPrefSize(50, 50);
            playerLabel.setStyle("-fx-background-color: magenta");
            playerPane.add(playerLabel, newX, newY);
            frontTiles[newX][newY] = playerLabel;
        });
    }

    public void renderAfterTreasurePicked(int positionX, int positionY) {
        Platform.runLater(() -> {
            mapPane.getChildren().remove(backgroundTiles[positionX][positionY]);

            Rectangle rect = new Rectangle(50, 50);
            rect.setStyle("-fx-fill: gold");
            mapPane.add(rect, positionX, positionY);

            backgroundTiles[positionX][positionY] = rect;
        });
    }

    public Node[][] getBackgroundTiles() {
        return backgroundTiles;
    }

    public Node[][] getFrontTiles() {
        return frontTiles;
    }

}
