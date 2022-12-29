package pl.edu.pwr.lgawron.lab06.player.flow;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import pl.edu.pwr.lgawron.lab06.common.sockets.SenderSocket;
import pl.edu.pwr.lgawron.lab06.common.game.geometry.Point2D;
import pl.edu.pwr.lgawron.lab06.common.game.objects.BlankInstance;
import pl.edu.pwr.lgawron.lab06.common.game.objects.GameInstance;
import pl.edu.pwr.lgawron.lab06.common.input.ValuesHolder;
import pl.edu.pwr.lgawron.lab06.player.executor.Executor;
import pl.edu.pwr.lgawron.lab06.player.executor.ManualTaskExecutor;
import pl.edu.pwr.lgawron.lab06.player.playersocket.PlayerReceiverSocket;
import pl.edu.pwr.lgawron.lab06.player.executor.PlayerAIAlgorithm;
import pl.edu.pwr.lgawron.lab06.player.executor.PlayerTasks;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerMapRenderer;

import java.util.ArrayList;
import java.util.List;

public class PlayerAppFlow {
    private PlayerReceiverSocket receiverSocket;
    private SenderSocket senderSocket;
    private ValuesHolder valuesHolder;
    private PlayerClientService playerClientService;
    private final List<List<GameInstance>> gameGrid;
    private Pair<Integer, Integer> dimensions;
    private GridPane mapPane;
    private GridPane playerPane;
    private PlayerMapRenderer mapRenderer;
    private Executor executor;
    private final PlayerTasks playerTasks;

    public PlayerAppFlow() {
        this.gameGrid = new ArrayList<>();
        this.playerTasks = new PlayerTasks();
    }

    public void startRegistration(ValuesHolder valuesHolder, VBox controls, GridPane mapPane, GridPane playerPane) {
        this.valuesHolder = valuesHolder;

        this.mapPane = mapPane;
        this.playerPane = playerPane;

        // main player logic
        this.playerClientService = new PlayerClientService(controls, valuesHolder, this);
        this.senderSocket = new SenderSocket();
        this.receiverSocket = new PlayerReceiverSocket(valuesHolder.getPort(), valuesHolder.getServer(), senderSocket, playerClientService, playerTasks);
        this.receiverSocket.start();

        // worker
        this.playerClientService.setSenderSocket(senderSocket);
    }

    public Pair<Integer, Integer> setDimensionsAndInitGrid(int sizeX, int sizeY) {
        // setting board dimensions
        this.dimensions = new Pair<>(sizeX, sizeY);
        // init player grid
        this.initPlayerGrid();

        return dimensions;
    }

    public PlayerMapRenderer initMapRenderer() {
        this.mapRenderer = new PlayerMapRenderer(mapPane, playerPane, gameGrid, dimensions);
        return mapRenderer;
    }

    private void initPlayerGrid() {
        int x = 0;
        int y = 0;

        for (int i = 0; i < dimensions.getValue(); i++) {
            List<GameInstance> row = new ArrayList<>();
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
        if (valuesHolder == null || executor != null) {
            return;
        }
        playerPane.setVisible(true);
        executor = new PlayerAIAlgorithm(playerClientService, playerTasks, receiverSocket);
        executor.start();
    }

    public void startManualExecutor() {
        if (valuesHolder == null || executor != null) {
            return;
        }
        playerPane.setVisible(true);
        executor = new ManualTaskExecutor(playerClientService, receiverSocket, playerTasks);
        executor.start();
    }

    public void see() {
        if (this.checkForTreasureWaitTime()) {
            return;
        }
        playerClientService.sendSeeRequest();
    }

    public void moveUp(int direction) {
        if (this.checkForTreasureWaitTime()) {
            return;
        }
        playerClientService.sendMoveUpRequest(direction);
    }

    public void moveLeft() {
        if (this.checkForTreasureWaitTime()) {
            return;
        }
        playerClientService.sendMoveLeftRequest();
    }

    public void moveRight() {
        if (this.checkForTreasureWaitTime()) {
            return;
        }
        playerClientService.sendMoveRightRequest();
    }

    public void moveDown(int direction) {
        if (this.checkForTreasureWaitTime()) {
            return;
        }
        playerClientService.sendMoveDownRequest(direction);
    }

    public void take() {
        if (this.checkForTreasureWaitTime()) {
            return;
        }
        playerClientService.sendTakeRequest();
    }

    private boolean checkForTreasureWaitTime() {
        if (playerClientService.getPlayerData() == null) {
            return true;
        }
        return playerClientService.getPlayerData().getTreasurePickedWaitTime() != 0;
    }

    public void killApp() {
        if (executor != null) {
            executor.setExit(true);
        }
        if (receiverSocket != null) {
            receiverSocket.exit(true);
        }
    }

}
