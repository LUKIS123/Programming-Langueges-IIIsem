package pl.edu.pwr.lgawron.lab06.player.ai;

import pl.edu.pwr.lgawron.lab06.player.flow.PlayerWorker;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerData;

import java.util.Random;

public class PlayerAlgorithm {
    private boolean exit;
    private final int minSleepTime;
    private final PlayerWorker worker;
    private final Thread thread;
    private final TaskRepository taskRepository;
    private PlayerData playerData;

    public PlayerAlgorithm(PlayerWorker worker, TaskRepository taskRepository) {
        this.worker = worker;
        this.taskRepository = taskRepository;
        this.exit = false;
        this.minSleepTime = 1000;

        this.thread = new Thread(() -> {
            while (worker.getPlayerData() == null) {
                tryToSleep(100);
            }
            playerData = worker.getPlayerData();

            while (!exit) {
                makeSeeRequest();
                String[] first = taskRepository.popTask();
                makeAction(first);

                makeMoveRequest();
                String[] second = taskRepository.popTask();
                makeAction(second);

                if (playerData.isPossibleCurrentSpotTreasure()) {
                    makeTakeRequest();
                    String[] third = taskRepository.popTask();
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
            this.tryToSleep(700);
        }
        if (type.equals("see")) {
            worker.handleSeeResponse(split);
            this.tryToSleep(500);
        }
        if (type.equals("move")) {
            worker.handleMoveResponse(split);
            this.tryToSleep(700);
        }
        if (type.equals("take")) {
            worker.handleTakeResponse(split);
            playerData.setPossibleCurrentSpotTreasure(false);
            this.tryToSleep(playerData.getTreasurePickedWaitTime());
            playerData.setTreasurePickedWaitTime(0);
        }

    }

    private void makeSeeRequest() {
        worker.sendSeeRequest();
    }

    private void makeMoveRequest() {
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
