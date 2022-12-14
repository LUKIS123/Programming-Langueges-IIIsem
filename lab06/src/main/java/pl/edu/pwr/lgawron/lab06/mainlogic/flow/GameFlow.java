package pl.edu.pwr.lgawron.lab06.mainlogic.flow;

import javafx.util.Pair;
import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.AdminReceiverSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.AdminSenderSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.PlayerService;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.queue.BackgroundWorker;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.queue.RequestQueue;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.utils.DataFileUtility;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.ValuesHolder;

import java.util.Arrays;
import java.util.List;

public class GameFlow {
    private final ValuesHolder valuesHolder;
    private final RequestQueue requestQueue;
    private final AdminReceiverSocket receiverSocket;
    private final AdminSenderSocket senderSocket;
    private final BackgroundWorker backgroundWorker;
    private final PlayerService playerService;
    private List<List<Character>> grid;
    private Pair<Integer, Integer> dimensions;

    public GameFlow(ValuesHolder valuesHolder) {
        this.valuesHolder = valuesHolder;

        this.requestQueue = new RequestQueue();
        this.senderSocket = new AdminSenderSocket();
        this.receiverSocket = new AdminReceiverSocket(valuesHolder.getPort(), requestQueue);
        // w tej klasie beda wykonywane komendy
        this.playerService = new PlayerService(valuesHolder.getPort());
        this.backgroundWorker = new BackgroundWorker(requestQueue, senderSocket, playerService);

        this.initMap();
    }

    public void initServer() {
        receiverSocket.start();
        backgroundWorker.start();
    }

    private void initMap() {
        List<String> lines = DataFileUtility.readFileLines("game_map.txt");

        for (String line : lines) {
            List<Character> charObjectArray =
                    line.chars().mapToObj(c -> (char) c).toList();
            grid.add(charObjectArray);
        }
//        Character[] charObjectArray =
//                lines.get(0).chars().mapToObj(c -> (char) c).toArray(Character[]::new);

    }
}
