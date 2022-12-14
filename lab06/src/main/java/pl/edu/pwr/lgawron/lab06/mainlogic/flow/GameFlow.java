package pl.edu.pwr.lgawron.lab06.mainlogic.flow;

import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.AdminReceiverSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.AdminSenderSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.PlayerService;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.queue.BackgroundWorker;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.queue.RequestQueue;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.ValuesHolder;

public class GameFlow {
    private final ValuesHolder valuesHolder;
    private final RequestQueue requestQueue;
    private final AdminReceiverSocket receiverSocket;
    private final AdminSenderSocket senderSocket;
    private final BackgroundWorker backgroundWorker;
    private final PlayerService playerService;

    public GameFlow(ValuesHolder valuesHolder) {
        this.valuesHolder = valuesHolder;

        this.requestQueue = new RequestQueue();
        this.senderSocket = new AdminSenderSocket();
        this.receiverSocket = new AdminReceiverSocket(valuesHolder.getPort(), requestQueue);
        // w tej klasie beda wykonywane komendy
        this.playerService = new PlayerService(valuesHolder.getPort());
        this.backgroundWorker = new BackgroundWorker(requestQueue, senderSocket, playerService);
    }

    public void init() {
        receiverSocket.start();
        backgroundWorker.start();
    }
}
