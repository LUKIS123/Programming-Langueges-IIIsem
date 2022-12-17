package pl.edu.pwr.lgawron.lab06.mainlogic.flow.game;

import javafx.util.Pair;
import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.AdminReceiverSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.MapRenderer;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry.Point2D;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.EnvironmentInstance;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.PlayerInstance;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.queue.RequestQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PlayerService {
    private final List<PlayerInstance> playerList;
    private final List<List<EnvironmentInstance>> gameGrid;
    private final Pair<Integer, Integer> dimensions;
    private final MapRenderer mapRenderer;
    private final int adminServerPort;
    private final RequestQueue requestQueue;
    private int sequence;

    public PlayerService(int adminServerPort, MapRenderer mapRenderer, List<List<EnvironmentInstance>> gameGrid, Pair<Integer, Integer> dimensions, RequestQueue requestQueue) {
        this.dimensions = dimensions;
        this.playerList = new ArrayList<>();
        this.gameGrid = gameGrid;
        this.mapRenderer = mapRenderer;

        this.adminServerPort = adminServerPort;
        this.requestQueue = requestQueue;
        this.sequence = 1;
    }

    public PlayerInstance addRegisteredPlayer(int playerServerPort) {
        // do wywalenia
        // AdminReceiverSocket adminReceiverSocket = new AdminReceiverSocket(0, requestQueue);

        PlayerInstance newPlayer = new PlayerInstance(sequence, playerServerPort, requestQueue);
        newPlayer.setPosition(this.getRandomLocation());
        // printowanie i komenda zwracajaca? -> gracz musi dostac gdzie jest
        playerList.add(newPlayer);
        mapRenderer.renderPlayerSpawned(newPlayer);
        sequence++;
        return newPlayer;
    }

    public PlayerInstance getPlayerById(int playerId) {
        // do poprawy pozniej moze na optionalach
        return playerList.stream().filter(p -> p.getId() == playerId).findFirst().get();
    }

    public PlayerInstance movePlayer(int playerId, int moveX, int moveY) {
        PlayerInstance playerById = this.getPlayerById(playerId);

        if (!this.checkIfMovePossible(playerById, moveX, moveY)) {
            return playerById;
        }

        // render
        mapRenderer.renderMove(
                playerById.getPosition().getPositionX(),
                playerById.getPosition().getPositionY(),
                playerById.getPosition().getPositionX() + moveX,
                playerById.getPosition().getPositionY() + moveY,
                playerById
        );

        // saving data
        playerById.setX(playerById.getPosition().getPositionX() + moveX);
        playerById.setY(playerById.getPosition().getPositionY() + moveY);

        return playerById;
    }

    private boolean checkIfMovePossible(PlayerInstance player, int moveX, int moveY) {
        int x = player.getPosition().getPositionX() + moveX;
        int y = player.getPosition().getPositionY() + moveY;

        if (this.checkIfPlayerPresent(x, y)) {
            return false;
        }

        try {
            if (gameGrid.get(y).get(x).getType().equals("obstacle")) {
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return true;
    }

    private String parseBack(EnvironmentInstance instance) {
        if (instance.getType().equals("path")) {
            return "*";
        }
        if (instance.getType().equals("obstacle")) {
            return "#";
        }
        if (instance.getType().equals("treasure")) {
            return "T";
        }
        return null;
    }

    public boolean checkIfPlayerPresent(int x, int y) {
        Optional<PlayerInstance> first = playerList.stream().filter(p -> p.getPosition().getPositionX() == x && p.getPosition().getPositionY() == y).findFirst();
        return first.isPresent();
    }

    private Point2D getRandomLocation() {
        Point2D point2D;

        while (true) {

            int x = new Random().nextInt(dimensions.getKey());
            int y = new Random().nextInt(dimensions.getValue());

            if (gameGrid.get(y).get(x).getType().equals("path")) {
                if (this.checkIfPlayerPresent(x, y)) {
                    continue;
                }
                point2D = new Point2D(x, y);
                break;
            }
        }

        return point2D;
    }

    static boolean isValidPos(int i, int j, int n, int m) {
        return i >= 0 && j >= 0 && i <= n - 1 && j <= m - 1;
    }

    public List<List<String>> getAdjacent(int i, int j) {
        int height = dimensions.getValue();
        int length = dimensions.getKey();

        List<String> upp = new ArrayList<>();
        List<String> cur = new ArrayList<>();
        List<String> dwn = new ArrayList<>();

        for (int y = -1; y < 2; y++) {
            for (int x = -1; x < 2; x++) {
                List<String> stringList = upp;
                if (y == -1) {
                    stringList = upp;
                }
                if (y == 0) {
                    stringList = cur;
                }
                if (y == 1) {
                    stringList = dwn;
                }

                if (x == 0 && y == 0) {
                    if (isValidPos(i, j, height, length)) {
                        stringList.add(parseBack(gameGrid.get(i).get(j)));
                    } else {
                        stringList.add("-");
                    }
                } else {
                    if (isValidPos(i + y, j + x, height, length)) {
                        if (this.checkIfPlayerPresent(j + x, i + y)) {
                            stringList.add("P");
                        } else {
                            stringList.add(parseBack(gameGrid.get(i + y).get(j + x)));
                        }
                    } else {
                        stringList.add("-");
                    }
                }

            }
        }
        return List.of(upp, cur, dwn);
    }

    public List<String> getAdjacentParsed(PlayerInstance playerInstance) {
        List<List<String>> adjacent = this.getAdjacent(playerInstance.getPosition().getPositionY(), playerInstance.getPosition().getPositionX());
        String upp = String.join(",", adjacent.get(0));
        String cur = String.join(",", adjacent.get(1));
        String dwn = String.join(",", adjacent.get(2));
        return List.of(upp, cur, dwn);
    }

    public List<PlayerInstance> getPlayerList() {
        return playerList;
    }

    public Pair<Integer, Integer> getDimensions() {
        return dimensions;
    }
}
