package pl.edu.pwr.lgawron.lab06.mainlogic.flow;

import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.AdminSenderSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.registration.RegisterSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.PlayerService;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry.Point2D;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.EnvironmentInstance;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.ObstacleInstance;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.PathInstance;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.TreasureInstance;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.queue.BackgroundWorker;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.queue.RequestQueue;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.utils.DataFileUtility;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.ValuesHolder;

import java.util.ArrayList;
import java.util.List;

public class GameFlow {
    private final ValuesHolder valuesHolder;
    private final RequestQueue requestQueue;
    private final RegisterSocket registrationReceiverSocket;
    private final AdminSenderSocket registrationSenderSocket;
    private final BackgroundWorker backgroundWorker;
    private final PlayerService playerService;
    //private final List<List<Character>> grid;
    private final List<List<EnvironmentInstance>> gameGrid;
    private Pair<Integer, Integer> dimensions;
    private final MapRenderer mapRenderer;
    private final GridPane mapPane;
    private final GridPane playerPane;

    public GameFlow(ValuesHolder valuesHolder, GridPane mapPane, GridPane playerPane) {
        this.valuesHolder = valuesHolder;
        this.mapPane = mapPane;
        this.playerPane = playerPane;

        this.requestQueue = new RequestQueue();
        this.registrationSenderSocket = new AdminSenderSocket();
        // this.registrationReceiverSocket = new AdminReceiverSocket(valuesHolder.getPort(), requestQueue);
        this.registrationReceiverSocket = new RegisterSocket(valuesHolder.getPort(), requestQueue);

        // gra
        //this.grid = new ArrayList<>();
        this.gameGrid = new ArrayList<>();

        // init
        this.initMapGrid();
        this.mapRenderer = new MapRenderer(mapPane, playerPane, gameGrid, dimensions);

        // w tej klasie beda wykonywane komendy
        this.playerService = new PlayerService(valuesHolder.getPort(), mapRenderer, gameGrid, dimensions, requestQueue);
        this.backgroundWorker = new BackgroundWorker(requestQueue, registrationSenderSocket, playerService);
    }

    public void initServer() {
        registrationReceiverSocket.start();
        backgroundWorker.start();
    }

    private void initMapGrid() {
        List<String> lines = DataFileUtility.readFileLines("game_map.txt");

        int x = 0;
        int y = 0;

        for (String line : lines) {
            List<Character> characters = line.chars().mapToObj(c -> (char) c).toList();
            List<EnvironmentInstance> row = new ArrayList<>();
            for (Character character : characters) {
                if (character.equals('*')) {
                    row.add(new PathInstance(new Point2D(x, y)));
                }
                if (character.equals('#')) {
                    row.add(new ObstacleInstance(new Point2D(x, y)));
                }
                if (character.equals('T')) {
                    row.add(new TreasureInstance(new Point2D(x, y)));
                }
                x++;
            }
            gameGrid.add(row);
            x = 0;
            y++;
        }
        dimensions = new Pair<>(gameGrid.get(0).size(), gameGrid.size());
    }

    public void firstRender() {
        mapRenderer.renderMap();
    }

    public void finishRegistration() {
        registrationReceiverSocket.setExit(true);
    }
}
