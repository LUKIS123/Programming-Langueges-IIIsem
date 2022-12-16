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
        // add grid to playersGrid, parsed or not?
        playerData.setSurroundingGrid(stringGrid);

        // render

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
