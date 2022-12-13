package pl.edu.pwr.lgawron.lab06.mainlogic.flow;

import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.AdminReceiverSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.AdminSenderSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.PlayerInstance;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.queue.QueueWorkerService;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.queue.RequestQueue;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.ValuesHolder;

import java.util.ArrayList;
import java.util.List;

public class GameFlow {
    private final ValuesHolder valuesHolder;
    private final RequestQueue requestQueue;
    private final AdminReceiverSocket receiverSocket;
    private final AdminSenderSocket senderSocket;
    private final QueueWorkerService queueWorkerService;
    List<PlayerInstance> players = new ArrayList<>();

    public GameFlow(ValuesHolder valuesHolder) {
        this.valuesHolder = valuesHolder;

        this.requestQueue = new RequestQueue();
        this.senderSocket = new AdminSenderSocket();
        this.receiverSocket = new AdminReceiverSocket(valuesHolder.getPort(), requestQueue);
        // w tej klasie beda wykonywane komendy
        this.queueWorkerService = new QueueWorkerService(requestQueue);
    }

    public void init() {
        receiverSocket.start();
    }
}
