package pl.edu.pwr.lgawron.lab06.mainlogic.flow.game;

import javafx.util.Pair;
import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.models.RequestType;
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
    // private static final int[][] directions = new int[][]{{-1,-1}, {-1,0}, {-1,1},  {0,1}, {1,1},  {1,0},  {1,-1},  {0, -1}};

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
        PlayerInstance newPlayer = new PlayerInstance(sequence, playerServerPort, requestQueue);
        newPlayer.setPosition(this.getRandomLocation());
        // printowanie i komenda zwracajaca? -> gracz musi dostac gdzie jest
        playerList.add(newPlayer);
        mapRenderer.renderPlayers(newPlayer);
        sequence++;
        return newPlayer;
    }

    public PlayerInstance getPlayerById(int playerId) {
        // do poprawy pozniej moze na optionalach
        return playerList.stream().filter(p -> p.getId() == playerId).findFirst().get();
    }

    public void movePlayer(int playerId, int moveX, int moveY) {

    }

    // do poprawy
    public String[] getNearestEnvironmentParsed(PlayerInstance playerInstance) {
        Point2D position = playerInstance.getPosition();
        int x = position.getPositionX();
        int y = position.getPositionY();

        String[][] env = new String[3][3];

        if (x > 0 && y > 0 && x < dimensions.getKey() && y < dimensions.getValue()) {
            List<EnvironmentInstance> upper = gameGrid.get(y - 1);
            List<EnvironmentInstance> actual = gameGrid.get(y);
            List<EnvironmentInstance> lower = gameGrid.get(y + 1);
        }

        if (y > 0 && y < dimensions.getValue()) {
            if (x > 0 && x < dimensions.getKey()) {

            }

        }
        if (y == 0) {

        }

        if (y == dimensions.getValue()) {

        }

        // zaimplementowac logike -> sprawdzanie krawedzi
        String up = String.join(",", env[0]);
        String cu = String.join(",", env[0]);
        String dw = String.join(",", env[0]);
        return new String[]{up, cu, dw};
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
        int n = dimensions.getValue();
        int m = dimensions.getKey();

        List<String> upp = new ArrayList<>();
        List<String> cur = new ArrayList<>();
        List<String> dwn = new ArrayList<>();

        if (isValidPos(i - 1, j - 1, n, m)) {
            if (this.checkIfPlayerPresent(j - 1, i - 1)) {
                upp.add("P");
            } else {
                upp.add(parseBack(gameGrid.get(i - 1).get(j - 1)));
            }
        } else {
            upp.add("-");
        }

        if (isValidPos(i - 1, j, n, m)) {
            if (this.checkIfPlayerPresent(j, i - 1)) {
                upp.add("P");
            } else {
                upp.add(parseBack(gameGrid.get(i - 1).get(j)));
            }
        } else {
            upp.add("-");
        }

        if (isValidPos(i - 1, j + 1, n, m)) {
            if (this.checkIfPlayerPresent(j + 1, i - 1)) {
                upp.add("P");
            } else {
                upp.add(parseBack(gameGrid.get(i - 1).get(j + 1)));
            }
        } else {
            upp.add("-");
        }

        if (isValidPos(i, j - 1, n, m)) {
            if (this.checkIfPlayerPresent(j - 1, i)) {
                cur.add("P");
            } else {
                cur.add(parseBack(gameGrid.get(i).get(j - 1)));
            }
        } else {
            cur.add("-");
        }

        if (isValidPos(i, j, n, m)) {
            if (this.checkIfPlayerPresent(j, i)) {
                cur.add("P");
            } else {
                cur.add(parseBack(gameGrid.get(i).get(j)));
            }
        } else {
            cur.add("-");
        }

        if (isValidPos(i, j + 1, n, m)) {
            if (this.checkIfPlayerPresent(j + 1, i)) {
                cur.add("P");
            } else {
                cur.add(parseBack(gameGrid.get(i).get(j + 1)));
            }
        } else {
            cur.add("-");
        }

        if (isValidPos(i + 1, j - 1, n, m)) {
            if (this.checkIfPlayerPresent(j - 1, i + 1)) {
                dwn.add("P");
            } else {
                dwn.add(parseBack(gameGrid.get(i + 1).get(j - 1)));
            }
        } else {
            dwn.add("-");
        }

        if (isValidPos(i + 1, j, n, m)) {
            if (this.checkIfPlayerPresent(j, i + 1)) {
                dwn.add("P");
            } else {
                dwn.add(parseBack(gameGrid.get(i + 1).get(j)));
            }
        } else {
            dwn.add("-");
        }

        if (isValidPos(i + 1, j + 1, n, m)) {
            if (this.checkIfPlayerPresent(j + 1, i + 1)) {
                dwn.add("P");
            } else {
                dwn.add(parseBack(gameGrid.get(i + 1).get(j + 1)));
            }
        } else {
            dwn.add("-");
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
}
