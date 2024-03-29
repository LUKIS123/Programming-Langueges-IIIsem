package pl.edu.pwr.lgawron.lab06.player.flow;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import pl.edu.pwr.lgawron.lab06.common.sockets.SenderSocket;
import pl.edu.pwr.lgawron.lab06.common.input.ValuesHolder;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerEnvironmentData;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerMapRenderer;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerRequestCreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlayerClientService {
    private PlayerEnvironmentData playerEnvironmentData;
    private final VBox controlBox;
    private SenderSocket senderSocket;
    private final ValuesHolder valuesHolder;
    private Pair<Integer, Integer> dimensions;
    private int newReceiverPort;
    private PlayerMapRenderer playerMapRenderer;
    private final PlayerAppFlow appFlow;
    private final Random random;
    private final Label gameInfo;

    public PlayerClientService(VBox controlBox, ValuesHolder valuesHolder, PlayerAppFlow appFlow) {
        this.appFlow = appFlow;
        this.valuesHolder = valuesHolder;

        this.controlBox = controlBox;
        this.gameInfo = new Label();
        this.random = new Random();
    }

    public void handleRegistrationResponse(int receiverPort, int id, String boardSize, String positions, int playerServerPort) {
        newReceiverPort = receiverPort;
        // getting game info
        String[] sizes = boardSize.split(",");
        String[] coordinates = positions.split(",");

        playerEnvironmentData = new PlayerEnvironmentData(id, Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
        this.displayBasicInfo(receiverPort, playerServerPort, id);

        // init grid
        dimensions = appFlow.setDimensionsAndInitGrid(Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1]));
        playerEnvironmentData.fillGrid(Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1]));

        // first render
        playerMapRenderer = appFlow.initMapRenderer();
        playerMapRenderer.firstRender();
        playerMapRenderer.renderPlayerSpawned(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), playerEnvironmentData);

        // setting starting moving directions
        while (true) {
            int x = random.nextInt(-1, 2);
            int y = random.nextInt(-1, 2);
            if (x != 0 || y != 0) {
                playerEnvironmentData.setMovingDirectionX(x);
                playerEnvironmentData.setMovingDirectionY(y);
                break;
            }
        }
    }

    public void handleSeeResponse(String[] splitData) {
        String surr = splitData[2];
        String[] rows = surr.split(":");

        List<List<String>> stringGrid = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<String> strings = Arrays.asList(rows[i].split(","));
            stringGrid.add(strings);
        }

        // loop for replacement in grid & render
        int currentX = playerEnvironmentData.getLocation2DPoint().getPositionX();
        int currentY = playerEnvironmentData.getLocation2DPoint().getPositionY();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                // replacement
                if (stringGrid.get(1 + i).get(1 + j).equals("-")) {
                    continue;
                }
                playerEnvironmentData.replaceGridTile(currentX + j, currentY + i, stringGrid.get(1 + i).get(1 + j));
                // render
                playerMapRenderer.renderSee(currentX + j, currentY + i, stringGrid.get(1 + i).get(1 + j));
            }
        }
    }

    public void handleMoveResponse(String[] splitData) {
        String newPosition = splitData[2];
        String[] coordinates = newPosition.split(",");
        int newX = Integer.parseInt(coordinates[0]);
        int newY = Integer.parseInt(coordinates[1]);
        int oldX = playerEnvironmentData.getLocation2DPoint().getPositionX();
        int oldY = playerEnvironmentData.getLocation2DPoint().getPositionY();
        int treasureWaitingTime = Integer.parseInt(splitData[3]);

        if (oldX == newX && oldY == newY) {
            return;
        }
        // render
        playerMapRenderer.renderMove(oldX, oldY, newX, newY, playerEnvironmentData);
        // save data
        playerEnvironmentData.setNewPosition(newX, newY);
        // possible treasure
        playerEnvironmentData.setPossibleCurrentSpotTreasure(treasureWaitingTime);
    }

    public void handleTakeResponse(String[] split) {
        boolean result = Boolean.parseBoolean(split[2]);
        if (result) {
            int waitTime = Integer.parseInt(split[3]);
            int treasures = Integer.parseInt(split[4]);

            playerEnvironmentData.setTreasuresPicked(treasures);
            playerEnvironmentData.setTreasurePickedWaitTime(waitTime);
            playerEnvironmentData.replaceGridTile(playerEnvironmentData.getLocation2DPoint().getPositionX(), playerEnvironmentData.getLocation2DPoint().getPositionY(), "*");
            this.displayTreasureInfo(treasures);
            // render treasure taken
            playerMapRenderer.renderAfterTreasurePicked(playerEnvironmentData.getLocation2DPoint().getPositionX(), playerEnvironmentData.getLocation2DPoint().getPositionY());
        }
    }

    // getting coordinates of treasure in field of view, if not -> random point
    public int[] getNextPointToMove() {
        for (int i = -1; i < 2; i++) {
            if (playerEnvironmentData.getLocation2DPoint().getPositionY() + i < 0 || playerEnvironmentData.getLocation2DPoint().getPositionY() + i >= dimensions.getValue()) {
                continue;
            }
            List<String> currentRow = playerEnvironmentData.getPlayerGrid().get(playerEnvironmentData.getLocation2DPoint().getPositionY() + i);
            for (int j = -1; j < 2; j++) {
                if (playerEnvironmentData.getLocation2DPoint().getPositionX() + j < 0 || playerEnvironmentData.getLocation2DPoint().getPositionX() + j >= dimensions.getKey()) {
                    continue;
                }
                if (currentRow.get(playerEnvironmentData.getLocation2DPoint().getPositionX() + j).equals("T")) {
                    return new int[]{j, i};
                }
            }
        }
        return this.setRandomAvailableMovingDirections();
    }

    // no treasures nearby -> getting next direction
    private int[] setRandomAvailableMovingDirections() {
        int x;
        int y;

        while (!playerEnvironmentData.checkIfPositionPossibleToMove(playerEnvironmentData.getMovingDirectionX(), playerEnvironmentData.getMovingDirectionY(), dimensions)) {
            x = random.nextInt(-1, 2);
            y = random.nextInt(-1, 2);
            if (x != 0 || y != 0) {
                playerEnvironmentData.setMovingDirectionX(x);
                playerEnvironmentData.setMovingDirectionY(y);
            }
        }

        return new int[]{playerEnvironmentData.getMovingDirectionX(), playerEnvironmentData.getMovingDirectionY()};
    }

    public void handleGameOverResponse(String[] split) {
        String status = split[2];
        if (status.equals("win")) {
            this.displayWonInfo(Integer.parseInt(split[3]));
        }
        if (status.equals("lose")) {
            this.displayLostInfo(Integer.parseInt(split[3]));
        }
        appFlow.killApp();
    }

    public void sendSeeRequest() {
        senderSocket.sendMessage(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.seeRequest(playerEnvironmentData.getId()));
    }

    public void sendLeaveGameRequest() {
        try {
            senderSocket.sendMessage(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.leaveGameRequest(playerEnvironmentData.getId()));
        } catch (NullPointerException ignored) {
        }
    }

    // manual controls

    public void sendMoveUpRequest(int direction) {
        senderSocket.sendMessage(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.moveUpRequest(playerEnvironmentData.getId(), direction));
    }

    public void sendMoveLeftRequest() {
        senderSocket.sendMessage(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.moveLeftRequest(playerEnvironmentData.getId()));
    }

    public void sendMoveRightRequest() {
        senderSocket.sendMessage(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.moveRightRequest(playerEnvironmentData.getId()));
    }

    public void sendArtificialMoveRequest(int x, int y) {
        senderSocket.sendMessage(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.artificialMoveRequest(playerEnvironmentData.getId(), x, y));
    }

    public void sendMoveDownRequest(int direction) {
        senderSocket.sendMessage(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.moveDownRequest(playerEnvironmentData.getId(), direction));
    }

    public void sendTakeRequest() {
        senderSocket.sendMessage(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.takeTreasureRequest(playerEnvironmentData.getId(), playerEnvironmentData.getLocation2DPoint()));
    }
    // manual controls

    public void setSenderSocket(SenderSocket senderSocket) {
        this.senderSocket = senderSocket;
    }

    @FXML
    private void displayBasicInfo(int receiverPort, int playerServerPort, int id) {
        Platform.runLater(() -> {
            controlBox.getChildren().add(new Label("Connected, id=" + id + ", sending requests to server on=" + receiverPort + ", receiving answers on=" + playerServerPort));
            controlBox.getChildren().add(gameInfo);
        });
    }

    @FXML
    private void displayTreasureInfo(int treasures) {
        Platform.runLater(() ->
                gameInfo.setText("Treasures=" + treasures)
        );
    }

    @FXML
    private void displayLostInfo(int treasures) {
        Platform.runLater(() ->
                gameInfo.setText("YOU LOSE! Most treasures taken by another player=" + treasures + ", your score was=" + playerEnvironmentData.getTreasuresPicked())
        );
    }

    @FXML
    private void displayWonInfo(int treasures) {
        Platform.runLater(() ->
                gameInfo.setText("YOU WIN! Your score is=" + treasures + " treasures!")
        );
    }

    public PlayerEnvironmentData getPlayerData() {
        return playerEnvironmentData;
    }

}
