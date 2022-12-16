package pl.edu.pwr.lgawron.lab06.player.flow;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.ValuesHolder;
import pl.edu.pwr.lgawron.lab06.mainlogic.playersocket.PlayerSenderSocket;
import pl.edu.pwr.lgawron.lab06.player.utils.GameData;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerData;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerRequestParser;

public class PlayerWorker {
    private GameData gameData;
    private PlayerData playerData;
    private VBox controlBox;
    private PlayerSenderSocket senderSocket;
    private ValuesHolder valuesHolder;
    private int newReceiverPort;

    public PlayerWorker(VBox controlBox, ValuesHolder valuesHolder) {
        this.valuesHolder = valuesHolder;
        this.controlBox = controlBox;
    }

    public void init(int receiverPort, int id) {
        this.newReceiverPort = receiverPort;
        this.playerData = new PlayerData(receiverPort, id);
        this.gameData = new GameData();

        this.displayBasicInfo(receiverPort, id);
    }

    @FXML
    public void displayBasicInfo(int receiverPort, int id) {
        System.out.println("Success;" + id + ";" + receiverPort);
        Platform.runLater(() ->
                controlBox.getChildren().add(new Label("Connected, id=" + id + ", listening on=" + receiverPort)));
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

    public void setSenderSocket(PlayerSenderSocket senderSocket) {
        this.senderSocket = senderSocket;
    }

    public GameData getGameData() {
        return gameData;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }


}
