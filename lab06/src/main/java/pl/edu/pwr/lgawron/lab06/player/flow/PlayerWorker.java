package pl.edu.pwr.lgawron.lab06.player.flow;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.ValuesHolder;
import pl.edu.pwr.lgawron.lab06.mainlogic.playersocket.PlayerSenderSocket;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerData;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerMapRenderer;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerRequestParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerWorker {
    //private GameData gameData;
    private PlayerData playerData;
    private VBox controlBox;
    private PlayerSenderSocket senderSocket;
    private ValuesHolder valuesHolder;
    private int newReceiverPort;
    private final PlayerMapRenderer playerMapRenderer;
    private final PlayerAppFlow appFlow;

    public PlayerWorker(VBox controlBox, ValuesHolder valuesHolder, PlayerMapRenderer playerMapRenderer, PlayerAppFlow appFlow) {
        this.appFlow = appFlow;
        this.valuesHolder = valuesHolder;
        this.controlBox = controlBox;
        this.playerMapRenderer = playerMapRenderer;
    }

    public void handleRegistrationResponse(int receiverPort, int id, String boardSize, String positions) {
        this.newReceiverPort = receiverPort;
        // getting info
        String[] sizes = boardSize.split(",");
        String[] coordinates = positions.split(",");

        this.playerData = new PlayerData(receiverPort, id, Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
        // this.gameData = new GameData();
        this.displayBasicInfo(receiverPort, id);

        // render
        // mozna przeniesc do innej metody, zeby wyrenderowac po starcie gry
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
        // jesli bedzie sparb to kolejny element arrayu bedzie przechowywal czas w ms podniesienia !!!
        String[] coordinates = newPosition.split(",");
        int newX = Integer.parseInt(coordinates[0]);
        int newY = Integer.parseInt(coordinates[1]);
        int oldX = playerData.getPoint2D().getPositionX();
        int oldY = playerData.getPoint2D().getPositionY();

        if (oldX == newX && oldY == newY) {
            return;
        }
        // render
        playerMapRenderer.renderMove(oldX, oldY, newX, newY, playerData);
        // save data
        playerData.setNewPosition(newX, newY);
    }

    public void sendSeeRequest() {
        if (playerData == null) {
            return;
        }
        senderSocket.sendRequest(newReceiverPort, valuesHolder.getServer(), PlayerRequestParser.seeRequest(playerData.getId()));
    }

    public void sendMoveUpRequest() {
        senderSocket.sendRequest(newReceiverPort, valuesHolder.getServer(), PlayerRequestParser.moveUpRequest(playerData.getId()));
    }

    public void sendMoveLeftRequest() {
        senderSocket.sendRequest(newReceiverPort, valuesHolder.getServer(), PlayerRequestParser.moveLeftRequest(playerData.getId()));
    }

    public void sendMoveRightRequest() {
        senderSocket.sendRequest(newReceiverPort, valuesHolder.getServer(), PlayerRequestParser.moveRightRequest(playerData.getId()));
    }

    public void sendMoveDownRequest() {
        senderSocket.sendRequest(newReceiverPort, valuesHolder.getServer(), PlayerRequestParser.moveDownRequest(playerData.getId()));
    }

    public void sendTakeRequest() {
        // todo
    }

    public void setSenderSocket(PlayerSenderSocket senderSocket) {
        this.senderSocket = senderSocket;
    }

    @FXML
    private void displayBasicInfo(int receiverPort, int id) {
        System.out.println("Success;" + id + ";" + receiverPort);
        Platform.runLater(() ->
                controlBox.getChildren().add(new Label("Connected, id=" + id + ", listening on=" + receiverPort)));
    }

    public PlayerData getPlayerData() {
        return playerData;
    }
}
