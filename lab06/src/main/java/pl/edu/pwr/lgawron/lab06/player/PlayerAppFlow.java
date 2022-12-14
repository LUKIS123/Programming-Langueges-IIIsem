package pl.edu.pwr.lgawron.lab06.player;

import pl.edu.pwr.lgawron.lab06.mainlogic.parse.ValuesHolder;
import pl.edu.pwr.lgawron.lab06.mainlogic.playersocket.PlayerReceiverSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.playersocket.PlayerSenderSocket;

public class PlayerAppFlow {
    PlayerReceiverSocket receiverSocket;
    PlayerSenderSocket senderSocket;
    ValuesHolder valuesHolder;

    public PlayerAppFlow() {
    }

    public void startRegistration(ValuesHolder valuesHolder) {
        this.valuesHolder = valuesHolder;
        senderSocket = new PlayerSenderSocket();
        receiverSocket = new PlayerReceiverSocket(valuesHolder.getPort(), valuesHolder.getServer(), senderSocket);
        receiverSocket.start();
    }
}
