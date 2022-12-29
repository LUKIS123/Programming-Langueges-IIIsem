package pl.edu.pwr.lgawron.lab06.player.executor;

import pl.edu.pwr.lgawron.lab06.player.flow.PlayerClientService;
import pl.edu.pwr.lgawron.lab06.player.playersocket.PlayerReceiverSocket;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerData;

public class PlayerAIAlgorithm implements Executor {
    private boolean exit;
    private final PlayerClientService worker;
    private final Thread thread;
    private final PlayerTasks playerTasks;
    private final PlayerReceiverSocket receiverSocket;
    private PlayerData playerData;

    public PlayerAIAlgorithm(PlayerClientService worker, PlayerTasks playerTasks, PlayerReceiverSocket receiverSocket) {
        this.worker = worker;
        this.playerTasks = playerTasks;
        this.receiverSocket = receiverSocket;
        this.exit = false;

        this.thread = new Thread(() -> {
            while (worker.getPlayerData() == null) {
                this.tryToSleep(100);
            }
            playerData = worker.getPlayerData();

            while (!exit) {
                this.makeSeeRequest();
                String[] first = playerTasks.popTask();
                this.makeAction(first);

                this.makeMoveRequestWithinFiledOfView();
                String[] second = playerTasks.popTask();
                this.makeAction(second);

                if (playerData.isPossibleCurrentSpotTreasure()) {
                    this.makeTakeRequest();
                    String[] third = playerTasks.popTask();
                    this.makeAction(third);
                }
            }
        });
    }

    @Override
    public void start() {
        thread.start();
    }

    @Override
    public void makeAction(String[] split) {
        String type = split[1];

        switch (type) {
            case "see" -> {
                worker.handleSeeResponse(split);
                this.tryToSleep(600);
            }
            case "move" -> {
                worker.handleMoveResponse(split);
                this.tryToSleep(200);
            }
            case "take" -> {
                worker.handleTakeResponse(split);
                playerData.setPossibleCurrentSpotTreasure(false);
                this.tryToSleep(playerData.getTreasurePickedWaitTime());
                playerData.setTreasurePickedWaitTime(0);
            }
            case "register" ->
                    worker.handleRegistrationResponse(Integer.parseInt(split[2]), Integer.parseInt(split[0]), split[3], split[4], receiverSocket.getPort());
            case "exit" -> thread.interrupt();
            default -> {
            }
        }
    }

    private void makeSeeRequest() {
        worker.sendSeeRequest();
    }

    private void makeMoveRequestWithinFiledOfView() {
        int[] nextPointToMove = worker.getNextPointToMove();
        worker.sendArtificialMoveRequest(nextPointToMove[0], nextPointToMove[1]);
    }

    private void makeTakeRequest() {
        worker.sendTakeRequest();
    }

    @Override
    public void tryToSleep(int time) {
        try {
            Thread.sleep(time + (int) (Math.random() * 100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setExit(boolean exit) {
        this.exit = exit;
        playerTasks.addTask(new String[]{"0", "exit"});
    }

}
