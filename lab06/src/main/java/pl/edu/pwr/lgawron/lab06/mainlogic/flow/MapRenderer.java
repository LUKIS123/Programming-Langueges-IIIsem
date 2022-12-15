package pl.edu.pwr.lgawron.lab06.mainlogic.flow;

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
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.EnvironmentInstance;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.PlayerInstance;

import java.io.File;
import java.util.List;

public class MapRenderer {
    private final List<List<EnvironmentInstance>> gameGrid;
    private final Pair<Integer, Integer> dimensions;
    private final GridPane mapPane;
    private final GridPane playerPane;
    private final Node[][] backgroundTiles;
    private final Node[][] frontTiles;
    private final Image treasureImage = new Image((new File("treasure.png").toURI().toString()));


    public MapRenderer(GridPane mapPane, GridPane playerPane, List<List<EnvironmentInstance>> gameGrid, Pair<Integer, Integer> dimensions) {
        this.gameGrid = gameGrid;
        this.dimensions = dimensions;
        this.mapPane = mapPane;
        this.playerPane = playerPane;

        this.fillPlayerPane();

        // table of tiles on position x,y
        this.backgroundTiles = new Node[dimensions.getKey()][dimensions.getValue()];
        this.frontTiles = new Node[dimensions.getKey()][dimensions.getValue()];
    }

    public void renderMap() {
        for (List<EnvironmentInstance> environmentInstances : gameGrid) {
            for (EnvironmentInstance environmentInstance : environmentInstances) {
                Rectangle rectangle = new Rectangle(50, 50);
                int positionX = environmentInstance.getPosition().getPositionX();
                int positionY = environmentInstance.getPosition().getPositionY();

                if (environmentInstance.getType().equals("obstacle")) {
                    rectangle.setStyle("-fx-fill: grey");
                }
                if (environmentInstance.getType().equals("path")) {
                    rectangle.setStyle("-fx-fill: gold");
                }
                if (environmentInstance.getType().equals("treasure")) {
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
            }
        }
    }

    public void renderPlayers(PlayerInstance playerInstance) {
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

    public Node[][] getBackgroundTiles() {
        return backgroundTiles;
    }

    public Node[][] getFrontTiles() {
        return frontTiles;
    }
}
