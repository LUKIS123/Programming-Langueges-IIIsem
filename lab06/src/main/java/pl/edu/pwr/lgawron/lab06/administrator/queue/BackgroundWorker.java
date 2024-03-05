package pl.edu.pwr.lgawron.lab06.administrator.queue;

import pl.edu.pwr.lgawron.lab06.administrator.flow.PlayerService;

public class BackgroundWorker {
    private boolean exit;
    private Thread thread;
    private final RequestQueue queue;
    private final PlayerService playerService;
    private StageActionUtility stageActionUtility;

    public BackgroundWorker(RequestQueue requestQueue, PlayerService playerService) {
        this.queue = requestQueue;
        this.playerService = playerService;
        this.stageActionUtility = new RegistrationStageUtils(playerService);
        this.exit = false;
    }

    public void start() {
        thread = new Thread(() -> {
            // registration stage
            int registrationStageStatus;
            do {
                registrationStageStatus = stageActionUtility.executeAction(queue.popLatest());
            } while (registrationStageStatus != 0 && registrationStageStatus != -1);

            // end of registration stage
            if (registrationStageStatus == -1) {
                exit = true;
            } else {
                stageActionUtility = new GameStageUtils(playerService);
            }

            // game stage
            int gameStageStatus;
            while (!exit) {
                gameStageStatus = stageActionUtility.executeAction(queue.popLatest());
                if (gameStageStatus == -1) {
                    this.setExit(true);
                }
            }

        });
        thread.start();
    }

    public void setExit(boolean exit) {
        this.exit = exit;
        this.thread.interrupt();
        System.out.println("BackGroundWorker killed");
    }

}
