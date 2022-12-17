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
    // do poprawy -> aby serwer przesyłał wymiary przy rejestracji
    private GridPane mapPane;
    private GridPane playerPane;
    private PlayerMapRenderer mapRenderer;

    public PlayerAppFlow() {
        //this.playerWorker = new PlayerWorker(controls);
        this.gameGrid = new ArrayList<>();
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
        senderSocket = new PlayerSenderSocket();
        receiverSocket = new PlayerReceiverSocket(valuesHolder.getPort(), valuesHolder.getServer(), senderSocket, playerWorker);
        receiverSocket.start();

        // worker
        playerWorker.setSenderSocket(senderSocket);
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


    public void see() {
        //senderSocket.sendRequest(valuesHolder.getPort(), valuesHolder.getServer(), PlayerRequestParser.seeRequest(playerData.getId()));
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

}
