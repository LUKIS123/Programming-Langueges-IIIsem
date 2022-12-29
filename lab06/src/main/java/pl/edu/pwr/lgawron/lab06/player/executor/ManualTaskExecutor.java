package pl.edu.pwr.lgawron.lab06.player.executor;

import pl.edu.pwr.lgawron.lab06.player.flow.PlayerClientService;
import pl.edu.pwr.lgawron.lab06.player.playersocket.PlayerReceiverSocket;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerData;

public class ManualTaskExecutor implements Executor {
    private boolean exit;
    private final PlayerClientService worker;
    private final Thread thread;
    private final PlayerReceiverSocket receiverSocket;
    private final PlayerTasks playerTasks;
    private PlayerData playerData;

    public ManualTaskExecutor(PlayerClientService worker, PlayerReceiverSocket receiverSocket, PlayerTasks playerTasks) {
        this.worker = worker;
        this.receiverSocket = receiverSocket;
        this.playerTasks = playerTasks;

        this.thread = new Thread(() -> {
            while (worker.getPlayerData() == null) {
                this.tryToSleep(100);
            }
            playerData = worker.getPlayerData();

            while (!exit) {
                String[] task = playerTasks.popTask();
                this.makeAction(task);
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

        if (type.equals("see")) {
            worker.handleSeeResponse(split);
        }
        if (type.equals("move")) {
            worker.handleMoveResponse(split);
        }
        if (type.equals("take")) {
            worker.handleTakeResponse(split);
            playerData.setPossibleCurrentSpotTreasure(false);
            this.tryToSleep(playerData.getTreasurePickedWaitTime());
            playerData.setTreasurePickedWaitTime(0);
        }
        if (type.equals("register")) {
            worker.handleRegistrationResponse(Integer.parseInt(split[2]), Integer.parseInt(split[0]), split[3], split[4], receiverSocket.getPort());
        }
        if (type.equals("exit")) {
            thread.interrupt();
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
        playerTasks.addTask(new String[]{"0", "exit"});
    }
}
