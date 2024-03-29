package pl.edu.pwr.lgawron.lab06.administrator.flow;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Pair;
import pl.edu.pwr.lgawron.lab06.administrator.models.PlayerRequest;
import pl.edu.pwr.lgawron.lab06.common.game.geometry.Point2D;
import pl.edu.pwr.lgawron.lab06.common.game.objects.GameInstance;
import pl.edu.pwr.lgawron.lab06.common.game.objects.PathInstance;
import pl.edu.pwr.lgawron.lab06.administrator.models.PlayerInstance;
import pl.edu.pwr.lgawron.lab06.administrator.queue.RequestQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PlayerService {
    private final List<PlayerInstance> playerList;
    private final List<List<GameInstance>> gameGrid;
    private final Pair<Integer, Integer> dimensions;
    private final Label playerInfo;
    private final MapRenderer mapRenderer;
    private final RequestQueue requestQueue;
    private int sequence;
    private int howManyTreasuresLeft;

    public PlayerService(MapRenderer mapRenderer, List<List<GameInstance>> gameGrid, Pair<Integer, Integer> dimensions, RequestQueue requestQueue, int howManyTreasuresLeft, Label playerInfo) {
        this.dimensions = dimensions;
        this.playerList = new ArrayList<>();
        this.gameGrid = gameGrid;
        this.mapRenderer = mapRenderer;
        this.howManyTreasuresLeft = howManyTreasuresLeft;
        this.playerInfo = playerInfo;

        this.requestQueue = requestQueue;
        this.sequence = 1;
    }

    public PlayerInstance addRegisteredPlayer(int playerServerPort, String proxyAddress) {
        PlayerInstance newPlayer = new PlayerInstance(sequence, playerServerPort, proxyAddress, requestQueue);
        newPlayer.setPosition(this.getRandomLocation());
        playerList.add(newPlayer);
        mapRenderer.renderPlayerSpawned(newPlayer);
        sequence++;
        this.updatePlayerInfoLabel();
        return newPlayer;
    }

    public PlayerInstance movePlayer(int playerId, int moveX, int moveY) {
        PlayerInstance playerById = this.getPlayerById(playerId);

        if (playerById.getId() == -1) {
            return playerById;
        }
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

    // player attempting to take treasure
    public PlayerInstance takeTreasureAttempt(int playerId, int treasureX, int treasureY) {
        PlayerInstance playerById = this.getPlayerById(playerId);

        if (playerById.getId() == -1) {
            return playerById;
        }

        Optional<GameInstance> environmentInstance = this.checkForTreasure(treasureX, treasureY);

        if (environmentInstance.isPresent()) {
            playerById.setCurrentWaitingTime(environmentInstance.get().getWaitingTime());
            gameGrid.get(treasureY).set(treasureX, new PathInstance(new Point2D(treasureX, treasureY)));
            playerById.setTakeAttempt(true);
            playerById.setHowManyTreasuresPicked(playerById.getHowManyTreasuresPicked() + 1);
            howManyTreasuresLeft--;

            // render a path instead of treasure
            mapRenderer.renderAfterTreasureTaken(treasureX, treasureY);
            if (howManyTreasuresLeft == 0) {
                requestQueue.addElement(new PlayerRequest("0", "logout"));
            }
        }
        return playerById;
    }

    public PlayerInstance getPlayerById(int playerId) {
        Optional<PlayerInstance> first = playerList.stream().filter(p -> p.getId() == playerId).findFirst();
        return first.orElseGet(() -> new PlayerInstance(-1, -1, "error", requestQueue));
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

    private String parseBack(GameInstance instance) {
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

    private boolean isValidPos(int i, int j, int n, int m) {
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

    // searching for treasure on a player spot
    public Optional<GameInstance> checkForTreasure(int x, int y) {
        GameInstance gameInstance = gameGrid.get(y).get(x);
        if (gameInstance.getType().equals("treasure")) {
            return Optional.of(gameInstance);
        } else {
            return Optional.empty();
        }
    }

    public Optional<PlayerInstance> getWhoWon() {
        Optional<PlayerInstance> found = playerList.stream().findFirst();
        if (found.isPresent()) {
            PlayerInstance first = found.get();
            for (PlayerInstance instance : playerList) {
                if (instance.getHowManyTreasuresPicked() > first.getHowManyTreasuresPicked()) {
                    first = instance;
                }
            }
            return Optional.of(first);
        } else {
            return found;
        }
    }

    public void kickPlayer(int playerId) {
        PlayerInstance playerById = getPlayerById(playerId);
        if (playerById.getId() == -1) {
            return;
        }

        mapRenderer.renderPlayerLeft(playerById.getPosition().getPositionX(), playerById.getPosition().getPositionY());
        playerById.getReceiverSocket().setExit(true);
        playerById.getReceiverSocket().kilThread();
        playerList.remove(playerById);
        this.updatePlayerInfoLabel();
    }

    @FXML
    private void updatePlayerInfoLabel() {
        Platform.runLater(() -> {
            playerInfo.setText("Players joined: " + playerList.size());
            playerInfo.setVisible(true);
        });
    }

    public List<PlayerInstance> getPlayerList() {
        return playerList;
    }

    public Pair<Integer, Integer> getDimensions() {
        return dimensions;
    }

}
