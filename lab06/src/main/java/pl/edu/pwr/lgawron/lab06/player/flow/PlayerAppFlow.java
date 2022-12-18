package pl.edu.pwr.lgawron.lab06.player.flow;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry.Point2D;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.BlankInstance;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.EnvironmentInstance;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.ValuesHolder;
import pl.edu.pwr.lgawron.lab06.mainlogic.playersocket.PlayerReceiverSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.playersocket.PlayerSenderSocket;
import pl.edu.pwr.lgawron.lab06.player.ai.PlayerAlgorithm;
import pl.edu.pwr.lgawron.lab06.player.ai.TaskQueue;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerMapRenderer;

import java.util.ArrayList;
import java.util.List;

public class PlayerAppFlow {
    private PlayerReceiverSocket receiverSocket;
    private PlayerSenderSocket senderSocket;
    private ValuesHolder valuesHolder;
    private PlayerWorker playerWorker;
    private final List<List<EnvironmentInstance>> gameGrid;
    private Pair<Integer, Integer> dimensions = new Pair<>(20, 15);
    private GridPane mapPane;
    private GridPane playerPane;
    private PlayerMapRenderer mapRenderer;
    private PlayerAlgorithm algorithm;
    private TaskQueue taskQueue;

    public PlayerAppFlow() {
        this.gameGrid = new ArrayList<>();
        this.taskQueue = new TaskQueue();
    }

    public void startRegistration(ValuesHolder valuesHolder, VBox controls, GridPane mapPane, GridPane playerPane) {
        this.valuesHolder = valuesHolder;

        this.mapPane = mapPane;
        this.playerPane = playerPane;

        // map render
        this.initPlayerGrid();
        this.mapRenderer = new PlayerMapRenderer(mapPane, playerPane, gameGrid, dimensions);

        // main player logic
        this.playerWorker = new PlayerWorker(controls, valuesHolder, mapRenderer, this);
        this.senderSocket = new PlayerSenderSocket();
        this.receiverSocket = new PlayerReceiverSocket(valuesHolder.getPort(), valuesHolder.getServer(), senderSocket, playerWorker, taskQueue);
        this.receiverSocket.start();

        // worker
        this.playerWorker.setSenderSocket(senderSocket);

        // algo
        this.algorithm = new PlayerAlgorithm(playerWorker, taskQueue);
    }

    private void initPlayerGrid() {
        int x = 0;
        int y = 0;

        for (int i = 0; i < dimensions.getValue(); i++) {
            List<EnvironmentInstance> row = new ArrayList<>();
            for (int j = 0; j < dimensions.getKey(); j++) {
                row.add(new BlankInstance(new Point2D(x, y)));
                x++;
            }
            gameGrid.add(row);
            x = 0;
            y++;
        }
    }

    public void startAlgo() {
        if (algorithm == null) {
            return;
        }
        algorithm.start();
    }

    public void see() {
        playerWorker.sendSeeRequest();
    }

    public void moveUp() {
        playerWorker.sendMoveUpRequest();
    }

    public void moveLeft() {
        playerWorker.sendMoveLeftRequest();
    }

    public void moveRight() {
        playerWorker.sendMoveRightRequest();
    }

    public void moveDown() {
        playerWorker.sendMoveDownRequest();
    }

    public void take() {
        playerWorker.sendTakeRequest();
    }

    public Pair<Integer, Integer> setDimensions(int sizeX, int sizeY) {
        this.dimensions = new Pair<>(sizeX, sizeY);
        return dimensions;
    }

    public void killApp() {
        if (algorithm != null) {
            algorithm.setExit(true);
        }
        if (receiverSocket != null) {
            receiverSocket.setExit(true);
        }
    }

}
