package pl.edu.pwr.lgawron.lab06.player.ai;

import pl.edu.pwr.lgawron.lab06.player.flow.PlayerWorker;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerData;

import java.util.Random;

public class PlayerAlgorithm {
    private boolean exit;
    private final int minSleepTime;
    private final PlayerWorker worker;
    private Thread thread;
    private final TaskQueue taskQueue;
    private PlayerData playerData;

    public PlayerAlgorithm(PlayerWorker worker, TaskQueue taskQueue) {
        this.worker = worker;
        this.taskQueue = taskQueue;
        this.exit = false;
        this.minSleepTime = 1000;

        this.thread = new Thread(() -> {
            if (worker.getPlayerData() == null) {
                tryToSleep(100);
            }
            playerData = worker.getPlayerData();

            while (!exit) {
                makeSeeRequest();
                String[] first = taskQueue.popTask();
                makeAction(first);

                makeMoveRequest();
                String[] second = taskQueue.popTask();
                makeAction(second);

                if (playerData.isPossibleCurrentSpotTreasure()) {
                    makeTakeRequest();
                    String[] third = taskQueue.popTask();
                    makeAction(third);
                }
            }
        });
    }

    public void start() {
        thread.start();
    }

    private void makeAction(String[] split) {
        String type = split[1];
        if (type.equals("register")) {
            worker.handleRegistrationResponse(Integer.parseInt(split[2]), Integer.parseInt(split[0]), split[3], split[4]);
            this.tryToSleep(1000);
        }
        if (type.equals("see")) {
            worker.handleSeeResponse(split);
            this.tryToSleep(1000);
        }
        if (type.equals("move")) {
            worker.handleMoveResponse(split);
            this.tryToSleep(1000);
        }
        if (type.equals("take")) {
            worker.handleTakeResponse(split);
            playerData.setPossibleCurrentSpotTreasure(false);
            this.tryToSleep(1000);
        }

    }

    private void makeSeeRequest() {
        worker.sendSeeRequest();
    }

    private void makeMoveRequest() {
        // sprawdzanie czy w zasiegu wzroku skarb
        Random random = new Random();
        int x;
        int y;
        do {
            x = random.nextInt(-1, 2);
            y = random.nextInt(-1, 2);
        } while (!worker.checkPositionIfPossibleToMove(x, y));
        worker.sendArtificialMoveRequest(x, y);
    }

    private void makeTakeRequest() {
        worker.sendTakeRequest();
    }

    private void tryToSleep(int time) {
        try {
            Thread.sleep(time + (int) (Math.random() * 100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
