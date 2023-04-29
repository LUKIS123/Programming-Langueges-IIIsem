package pl.edu.pwr.lgawron.lab06.player.executor;

import pl.edu.pwr.lgawron.lab06.player.flow.PlayerClientService;
import pl.edu.pwr.lgawron.lab06.player.playersocket.PlayerReceiverSocket;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerEnvironmentData;

public class PlayerAIAlgorithm implements Executor {
    private boolean exit;
    private final PlayerClientService worker;
    private final Thread thread;
    private final ServerResponseQueue serverResponseQueue;
    private final PlayerReceiverSocket receiverSocket;
    private PlayerEnvironmentData playerEnvironmentData;

    public PlayerAIAlgorithm(PlayerClientService worker, ServerResponseQueue serverResponseQueue, PlayerReceiverSocket receiverSocket) {
        this.worker = worker;
        this.serverResponseQueue = serverResponseQueue;
        this.receiverSocket = receiverSocket;
        this.exit = false;

        this.thread = new Thread(() -> {

            while (worker.getPlayerData() == null) {
                if (serverResponseQueue.getTasks().size() != 0) {
                    // executing registration response
                    this.makeAction(serverResponseQueue.popTask());
                }
                this.tryToSleep(100);
            }
            playerEnvironmentData = worker.getPlayerData();

            while (!exit) {
                this.makeSeeRequest();
                String[] first = serverResponseQueue.popTask();
                this.makeAction(first);

                if (exit) {
                    break;
                }

                this.makeMoveRequestWithinFiledOfView();
                String[] second = serverResponseQueue.popTask();
                this.makeAction(second);

                if (playerEnvironmentData.isPossibleCurrentSpotTreasure()) {
                    this.makeTakeRequest();
                    String[] third = serverResponseQueue.popTask();
                    this.makeAction(third);
                }
            }
        });
    }

    @Override
    public void start() {
        if (serverResponseQueue.getTasks().size() != 0) {
            // executing registration response
            this.makeAction(serverResponseQueue.popTask());
        }
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
                playerEnvironmentData.setPossibleCurrentSpotTreasure(false);
                this.tryToSleep(playerEnvironmentData.getTreasurePickedWaitTime());
                playerEnvironmentData.setTreasurePickedWaitTime(0);
            }
            case "register" ->
                    worker.handleRegistrationResponse(Integer.parseInt(split[2]), Integer.parseInt(split[0]), split[3], split[4], receiverSocket.getPort());
            case "over" -> worker.handleGameOverResponse(split);
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
        serverResponseQueue.addTask(new String[]{"0", "exit"});
    }

}
