package pl.edu.pwr.lgawron.lab06.player.executor;

import pl.edu.pwr.lgawron.lab06.player.flow.PlayerClientService;
import pl.edu.pwr.lgawron.lab06.player.playersocket.PlayerReceiverSocket;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerEnvironmentData;

public class ManualTaskExecutor implements Executor {
    private boolean exit;
    private final PlayerClientService worker;
    private final Thread thread;
    private final PlayerReceiverSocket receiverSocket;
    private final ServerResponseQueue serverResponseQueue;
    private PlayerEnvironmentData playerEnvironmentData;

    public ManualTaskExecutor(PlayerClientService worker, PlayerReceiverSocket receiverSocket, ServerResponseQueue serverResponseQueue) {
        this.worker = worker;
        this.receiverSocket = receiverSocket;
        this.serverResponseQueue = serverResponseQueue;

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
                String[] task = serverResponseQueue.popTask();
                this.makeAction(task);
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
            case "see" -> worker.handleSeeResponse(split);
            case "move" -> worker.handleMoveResponse(split);
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
