package pl.edu.pwr.lgawron.lab06.player.flow;

import javafx.scene.control.Label;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.ValuesHolder;
import pl.edu.pwr.lgawron.lab06.mainlogic.playersocket.PlayerReceiverSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.playersocket.PlayerSenderSocket;
import pl.edu.pwr.lgawron.lab06.player.utils.GameData;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerData;

public class PlayerAppFlow {
    private PlayerReceiverSocket receiverSocket;
    private PlayerSenderSocket senderSocket;
    private ValuesHolder valuesHolder;
    private PlayerData playerData;
    private GameData gameData;
    private final PlayerWorker playerWorker;

    public PlayerAppFlow(Label label) {
        this.playerWorker = new PlayerWorker(label);
    }

    public void startRegistration(ValuesHolder valuesHolder) {
        this.valuesHolder = valuesHolder;
        senderSocket = new PlayerSenderSocket();
        receiverSocket = new PlayerReceiverSocket(valuesHolder.getPort(), valuesHolder.getServer(), senderSocket, playerWorker);
        receiverSocket.start();

        // worker
        playerWorker.setSenderSocket(senderSocket);
    }

}
