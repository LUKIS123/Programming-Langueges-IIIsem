package pl.edu.pwr.lgawron.lab06.administrator.flow;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import pl.edu.pwr.lgawron.lab06.common.game.objects.GameInstance;
import pl.edu.pwr.lgawron.lab06.administrator.models.PlayerInstance;

import java.io.File;
import java.util.List;

public class MapRenderer {
    private final List<List<GameInstance>> gameGrid;
    private final Pair<Integer, Integer> dimensions;
    private final GridPane mapPane;
    private final GridPane playerPane;
    private final Node[][] backgroundTiles;
    private final Node[][] frontTiles;
    private final Image treasureImage = new Image((new File("treasure.png").toURI().toString()));


    public MapRenderer(GridPane mapPane, GridPane playerPane, List<List<GameInstance>> gameGrid, Pair<Integer, Integer> dimensions) {
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

    public void renderMap() {
        // gameMap
        for (List<GameInstance> gameInstances : gameGrid) {
            for (GameInstance gameInstance : gameInstances) {
                Rectangle rectangle = new Rectangle(50, 50);
                int positionX = gameInstance.position().getPositionX();
                int positionY = gameInstance.position().getPositionY();

                if (gameInstance.getType().equals("obstacle")) {
                    rectangle.setStyle("-fx-fill: grey");
                }
                if (gameInstance.getType().equals("path")) {
                    rectangle.setStyle("-fx-fill: gold");
                }
                if (gameInstance.getType().equals("treasure")) {
                    rectangle.setFill(new ImagePattern(treasureImage));
                }
                mapPane.add(rectangle, positionX, positionY);
                backgroundTiles[positionX][positionY] = rectangle;
            }
        }
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

    public void renderPlayerSpawned(PlayerInstance playerInstance) {
        int x = playerInstance.getPosition().getPositionX();
        int y = playerInstance.getPosition().getPositionY();
        Platform.runLater(() -> {
            playerPane.getChildren().remove(frontTiles[x][y]);

            Label playerLabel = new Label("Player " + playerInstance.getId());
            playerLabel.setAlignment(Pos.CENTER);
            playerLabel.setPrefSize(50, 50);
            playerLabel.setStyle("-fx-background-color: magenta");
            playerPane.add(playerLabel, x, y);

            frontTiles[x][y] = playerLabel;
        });
    }

    public void renderMove(int x, int y, int newX, int newY, PlayerInstance playerInstance) {
        Platform.runLater(() -> {
            playerPane.getChildren().remove(frontTiles[x][y]);

            Rectangle rect = new Rectangle(50, 50);
            rect.setFill(Color.TRANSPARENT);
            playerPane.add(rect, x, y);
            frontTiles[x][y] = rect;

            playerPane.getChildren().remove(frontTiles[newX][newY]);

            Label playerLabel = new Label("Player " + playerInstance.getId());
            playerLabel.setAlignment(Pos.CENTER);
            playerLabel.setPrefSize(50, 50);
            playerLabel.setStyle("-fx-background-color: magenta");
            playerPane.add(playerLabel, newX, newY);
            frontTiles[newX][newY] = playerLabel;
        });
    }

    public void renderAfterTreasureTaken(int treasureX, int treasureY) {
        Platform.runLater(() -> {
            mapPane.getChildren().remove(backgroundTiles[treasureX][treasureY]);

            Rectangle rect = new Rectangle(50, 50);
            rect.setStyle("-fx-fill: gold");
            mapPane.add(rect, treasureX, treasureY);

            backgroundTiles[treasureX][treasureY] = rect;
        });
    }

}
