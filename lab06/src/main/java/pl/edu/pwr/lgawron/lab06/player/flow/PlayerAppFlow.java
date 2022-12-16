package pl.edu.pwr.lgawron.lab06.player.flow;

import javafx.scene.layout.VBox;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.ValuesHolder;
import pl.edu.pwr.lgawron.lab06.mainlogic.playersocket.PlayerReceiverSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.playersocket.PlayerSenderSocket;
import pl.edu.pwr.lgawron.lab06.player.utils.GameData;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerData;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerRequestParser;

public class PlayerAppFlow {
    private PlayerReceiverSocket receiverSocket;
    private PlayerSenderSocket senderSocket;
    private ValuesHolder valuesHolder;
    private PlayerData playerData;
    private GameData gameData;
    private PlayerWorker playerWorker;

    public PlayerAppFlow() {
        //this.playerWorker = new PlayerWorker(controls);
    }

    public void startRegistration(ValuesHolder valuesHolder, VBox controls) {
        this.valuesHolder = valuesHolder;
        this.playerWorker = new PlayerWorker(controls, valuesHolder);
        senderSocket = new PlayerSenderSocket();
        receiverSocket = new PlayerReceiverSocket(valuesHolder.getPort(), valuesHolder.getServer(), senderSocket, playerWorker);
        receiverSocket.start();

        // worker
        playerWorker.setSenderSocket(senderSocket);
    }


    public void see() {
        //senderSocket.sendRequest(valuesHolder.getPort(), valuesHolder.getServer(), PlayerRequestParser.seeRequest(playerData.getId()));
        playerWorker.sendSeeRequest();
    }

    public void moveUp() {
        playerWorker.sendMoveUpRequest();
    }
}
