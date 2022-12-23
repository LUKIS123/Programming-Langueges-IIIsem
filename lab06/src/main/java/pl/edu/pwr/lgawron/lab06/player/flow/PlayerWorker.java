package pl.edu.pwr.lgawron.lab06.player.flow;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.ValuesHolder;
import pl.edu.pwr.lgawron.lab06.player.playersocket.PlayerSenderSocket;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerData;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerMapRenderer;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerRequestCreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlayerWorker {
    private PlayerData playerData;
    private final VBox controlBox;
    private PlayerSenderSocket senderSocket;
    private final ValuesHolder valuesHolder;
    private final Pair<Integer, Integer> dimensions;
    private int newReceiverPort;
    private final PlayerMapRenderer playerMapRenderer;
    private final PlayerAppFlow appFlow;
    private final Label gameInfo;

    public PlayerWorker(VBox controlBox, ValuesHolder valuesHolder, Pair<Integer, Integer> dimensions, PlayerMapRenderer playerMapRenderer, PlayerAppFlow appFlow) {
        this.appFlow = appFlow;
        this.valuesHolder = valuesHolder;
        this.dimensions = dimensions;

        this.controlBox = controlBox;
        this.playerMapRenderer = playerMapRenderer;
        this.gameInfo = new Label();
    }

    public void handleRegistrationResponse(int receiverPort, int id, String boardSize, String positions) {
        this.newReceiverPort = receiverPort;
        // getting info
        String[] sizes = boardSize.split(",");
        String[] coordinates = positions.split(",");

        this.playerData = new PlayerData(receiverPort, id, Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
        this.displayBasicInfo(receiverPort, id);

        // render
        appFlow.setDimensions(Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1]));
        playerData.fillGrid(Integer.parseInt(sizes[0]), Integer.parseInt(sizes[1]));
        playerMapRenderer.firstRender();
        playerMapRenderer.renderPlayerSpawned(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), playerData);
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
        int currentX = playerData.getPoint2D().getPositionX();
        int currentY = playerData.getPoint2D().getPositionY();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                // replacement
                if (stringGrid.get(1 + i).get(1 + j).equals("-")) {
                    continue;
                }
                playerData.replaceTile(currentX + j, currentY + i, stringGrid.get(1 + i).get(1 + j));
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
        int oldX = playerData.getPoint2D().getPositionX();
        int oldY = playerData.getPoint2D().getPositionY();
        int treasureWaitingTime = Integer.parseInt(splitData[3]);

        if (oldX == newX && oldY == newY) {
            return;
        }
        // render
        playerMapRenderer.renderMove(oldX, oldY, newX, newY, playerData);
        // save data
        playerData.setNewPosition(newX, newY);
        // possible treasure -> can be improved
        playerData.setPossibleCurrentSpotTreasure(treasureWaitingTime);
    }

    public void handleTakeResponse(String[] split) {
        boolean result = Boolean.parseBoolean(split[2]);
        if (result) {
            int waitTime = Integer.parseInt(split[3]);
            int treasures = Integer.parseInt(split[4]);

            playerData.setTreasuresPicked(treasures);
            playerData.setTreasurePickedWaitTime(waitTime);
            playerData.replaceTile(playerData.getPoint2D().getPositionX(), playerData.getPoint2D().getPositionY(), "*");
            this.displayTreasureInfo(treasures);
            // render treasure taken
            playerMapRenderer.renderAfterTreasurePicked(playerData.getPoint2D().getPositionX(), playerData.getPoint2D().getPositionY());
        }
    }

    // getting coordinates of treasure in field of view, if not -> random point
    public int[] getNextPointToMove() {
        for (int i = -1; i < 2; i++) {
            if (playerData.getPoint2D().getPositionY() + i < 0 || playerData.getPoint2D().getPositionY() + i >= dimensions.getValue()) {
                continue;
            }
            List<String> currentRow = playerData.getPlayerGrid().get(playerData.getPoint2D().getPositionY() + i);
            for (int j = -1; j < 2; j++) {
                if (playerData.getPoint2D().getPositionX() + j < 0 || playerData.getPoint2D().getPositionX() + j >= dimensions.getKey()) {
                    continue;
                }
                if (currentRow.get(playerData.getPoint2D().getPositionX() + j).equals("T")) {
                    return new int[]{j, i};
                }
            }
        }
        return this.getRandomAvailablePoint();
    }

    // no treasures nearby -> getting random point
    private int[] getRandomAvailablePoint() {
        Random random = new Random();
        int x;
        int y;
        do {
            x = random.nextInt(-1, 2);
            y = random.nextInt(-1, 2);
        } while (!this.checkPositionIfPossibleToMove(x, y));

        return new int[]{x, y};
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
        if (playerData == null) {
            return;
        }
        senderSocket.sendRequest(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.seeRequest(playerData.getId()));
    }

    public void sendArtificialMoveRequest(int x, int y) {
        senderSocket.sendRequest(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.artificialMoveRequest(playerData.getId(), x, y));
    }

    public boolean checkPositionIfPossibleToMove(int x, int y) {
        return playerData.checkIfPositionPossibleToMove(x, y);
    }

    // controls

    public void sendMoveUpRequest() {
        senderSocket.sendRequest(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.moveUpRequest(playerData.getId()));
    }

    public void sendMoveLeftRequest() {
        senderSocket.sendRequest(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.moveLeftRequest(playerData.getId()));
    }

    public void sendMoveRightRequest() {
        senderSocket.sendRequest(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.moveRightRequest(playerData.getId()));
    }

    public void sendMoveDownRequest() {
        senderSocket.sendRequest(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.moveDownRequest(playerData.getId()));
    }

    public void sendTakeRequest() {
        senderSocket.sendRequest(newReceiverPort, valuesHolder.getServer(), PlayerRequestCreator.takeTreasureRequest(playerData.getId(), playerData.getPoint2D()));
    }

    // controls

    public void setSenderSocket(PlayerSenderSocket senderSocket) {
        this.senderSocket = senderSocket;
    }

    @FXML
    private void displayBasicInfo(int receiverPort, int id) {
        Platform.runLater(() -> {
            controlBox.getChildren().add(new Label("Connected, id=" + id + ", listening on=" + receiverPort));
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
                gameInfo.setText("YOU LOSE! Most treasures taken by another player=" + treasures + ", your score was=" + playerData.getTreasuresPicked())
        );
    }

    @FXML
    private void displayWonInfo(int treasures) {
        Platform.runLater(() ->
                gameInfo.setText("YOU WIN! Your score is=" + treasures + " treasures!")
        );
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

}
