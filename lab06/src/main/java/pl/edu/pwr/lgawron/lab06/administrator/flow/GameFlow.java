package pl.edu.pwr.lgawron.lab06.administrator.flow;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import pl.edu.pwr.lgawron.lab06.administrator.models.PlayerRequest;
import pl.edu.pwr.lgawron.lab06.administrator.adminsocket.registration.RegistrationReceiverSocket;
import pl.edu.pwr.lgawron.lab06.common.game.geometry.Point2D;
import pl.edu.pwr.lgawron.lab06.common.game.objects.GameInstance;
import pl.edu.pwr.lgawron.lab06.common.game.objects.ObstacleInstance;
import pl.edu.pwr.lgawron.lab06.common.game.objects.PathInstance;
import pl.edu.pwr.lgawron.lab06.common.game.objects.TreasureInstance;
import pl.edu.pwr.lgawron.lab06.administrator.queue.BackgroundWorker;
import pl.edu.pwr.lgawron.lab06.administrator.queue.RequestQueue;
import pl.edu.pwr.lgawron.lab06.administrator.utils.DataFileUtility;
import pl.edu.pwr.lgawron.lab06.common.input.ValuesHolder;

import java.util.ArrayList;
import java.util.List;

public class GameFlow {
    private final ValuesHolder valuesHolder;
    private final RequestQueue requestQueue;
    private final RegistrationReceiverSocket registrationReceiverSocket;
    private final BackgroundWorker backgroundWorker;
    private final PlayerService playerService;
    private final List<List<GameInstance>> gameGrid;
    private Pair<Integer, Integer> dimensions;
    private final MapRenderer mapRenderer;
    private final GridPane mapPane;
    private final GridPane playerPane;
    private int howManyTreasuresLeft;

    public GameFlow(ValuesHolder valuesHolder, GridPane mapPane, GridPane playerPane, Label playerInfo) {
        this.valuesHolder = valuesHolder;
        this.mapPane = mapPane;
        this.playerPane = playerPane;
        this.howManyTreasuresLeft = 0;

        this.requestQueue = new RequestQueue();
        this.registrationReceiverSocket = new RegistrationReceiverSocket(valuesHolder.getPort(), requestQueue);

        // game
        this.gameGrid = new ArrayList<>();

        // init
        this.initMapGrid();
        this.mapRenderer = new MapRenderer(mapPane, playerPane, gameGrid, dimensions);

        // logic
        this.playerService = new PlayerService(mapRenderer, gameGrid, dimensions, requestQueue, howManyTreasuresLeft, playerInfo);
        this.backgroundWorker = new BackgroundWorker(requestQueue, playerService);
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
            List<GameInstance> row = new ArrayList<>();
            for (Character character : characters) {
                if (character.equals('*')) {
                    row.add(new PathInstance(new Point2D(x, y)));
                }
                if (character.equals('#')) {
                    row.add(new ObstacleInstance(new Point2D(x, y)));
                }
                if (character.equals('T')) {
                    row.add(new TreasureInstance(new Point2D(x, y)));
                    howManyTreasuresLeft++;
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
        requestQueue.addElement(new PlayerRequest("0", "finish_registration"));
    }

    public void killApp() {
        registrationReceiverSocket.setExit(true);
        registrationReceiverSocket.kilThread();

        requestQueue.addElement(new PlayerRequest("0", "exit"));

        playerService.getPlayerList().forEach(playerInstance -> {
            playerInstance.getReceiverSocket().setExit(true);
            playerInstance.getReceiverSocket().kilThread();
        });
    }
}
