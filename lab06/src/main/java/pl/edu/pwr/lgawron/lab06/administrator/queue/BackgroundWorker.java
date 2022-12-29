package pl.edu.pwr.lgawron.lab06.administrator.queue;

import pl.edu.pwr.lgawron.lab06.administrator.utils.AdminResponseCreator;
import pl.edu.pwr.lgawron.lab06.administrator.models.PlayerRequest;
import pl.edu.pwr.lgawron.lab06.administrator.models.RequestType;
import pl.edu.pwr.lgawron.lab06.administrator.flow.PlayerService;
import pl.edu.pwr.lgawron.lab06.common.game.objects.GameInstance;
import pl.edu.pwr.lgawron.lab06.administrator.models.PlayerInstance;

import java.util.List;
import java.util.Optional;

public class BackgroundWorker {
    private boolean exit;
    private Thread thread;
    private final RequestQueue queue;
    private final PlayerService playerService;

    public BackgroundWorker(RequestQueue requestQueue, PlayerService playerService) {
        this.queue = requestQueue;
        this.playerService = playerService;
        this.exit = false;
    }

    public void start() {
        thread = new Thread(() -> {
            // todo: dodac listenera po obu stronach
            // dodac eventy logout aby usuwalo graczy po opuszczeniu
            // obsluzyc gety w playerService po stronie serwera

            // registration stage
            this.registrationStage();

            // game stage
            while (!exit) {
                PlayerRequest playerRequest = queue.popLatest();

                // see
                if (playerRequest.getType().equals(RequestType.SEE)) {
                    PlayerInstance playerInstance = playerService.getPlayerById(playerRequest.getPlayerId());

                    playerInstance.getSenderSocket().sendMessage(
                            playerInstance.getClientServerPort(),
                            playerInstance.getProxy(),
                            AdminResponseCreator.createSeeMessage(playerInstance.getId(),
                                    playerService.getAdjacentParsed(playerInstance))
                    );
                }

                // move
                if (playerRequest.getType().equals(RequestType.MOVE)) {
                    PlayerInstance playerInstance = playerService.movePlayer(playerRequest.getPlayerId(), playerRequest.getMoveX(), playerRequest.getMoveY());
                    Optional<GameInstance> instanceOnSpot = playerService.checkForTreasure(playerInstance.getPosition().getPositionX(), playerInstance.getPosition().getPositionY());

                    playerInstance.getSenderSocket().sendMessage(
                            playerInstance.getClientServerPort(),
                            playerInstance.getProxy(),
                            AdminResponseCreator.createMoveMessage(
                                    playerInstance.getId(),
                                    playerInstance.getPosition().getPositionX(),
                                    playerInstance.getPosition().getPositionY(),
                                    instanceOnSpot)
                    );
                }

                // take
                if (playerRequest.getType().equals(RequestType.TAKE)) {
                    PlayerInstance playerInstance = playerService.takeTreasureAttempt(playerRequest.getPlayerId(), playerRequest.getTreasureX(), playerRequest.getTreasureY());
                    // response
                    playerInstance.getSenderSocket().sendMessage(playerInstance.getClientServerPort(),
                            playerInstance.getProxy(),
                            AdminResponseCreator.createTakeMessage(
                                    playerInstance.getId(),
                                    playerInstance.isTakeAttempt(),
                                    playerInstance.getCurrentWaitingTime(),
                                    playerInstance.getHowManyTreasuresPicked()
                            )
                    );
                    playerInstance.setTakeAttempt(false);
                    playerInstance.setCurrentWaitingTime(0);
                }

                // game over
                if (playerRequest.getType().equals(RequestType.GAME_OVER)) {
                    List<PlayerInstance> playerList = playerService.getPlayerList();
                    PlayerInstance whoWon = playerService.getWhoWon();
                    for (PlayerInstance instance : playerList) {
                        if (instance != whoWon) {
                            instance.getSenderSocket().sendMessage(
                                    instance.getClientServerPort(),
                                    instance.getProxy(),
                                    AdminResponseCreator.createGameOverMessage(
                                            instance.getId(),
                                            whoWon.getHowManyTreasuresPicked())
                            );
                        }
                    }
                    whoWon.getSenderSocket().sendMessage(
                            whoWon.getClientServerPort(),
                            whoWon.getProxy(),
                            AdminResponseCreator.createYouWonMessage(
                                    whoWon.getId(),
                                    whoWon.getHowManyTreasuresPicked()
                            )
                    );
                }

                // exit Background Worker Thread
                if (playerRequest.getType().equals(RequestType.EXIT)) {
                    this.setExit(true);
                }

            }

        });
        thread.start();
    }

    private void registrationStage() {
        while (true) {
            PlayerRequest playerRequest = queue.popLatest();

            if (playerRequest.getType().equals(RequestType.REGISTER)) {

                PlayerInstance playerInstance = playerService.addRegisteredPlayer(playerRequest.getClientServerPort(), playerRequest.getProxyAddress());
                while (!playerInstance.isBound()) {
                    this.tryToSleep();
                }
                playerInstance.getSenderSocket().sendMessage(
                        playerRequest.getClientServerPort(),
                        playerInstance.getProxy(),
                        AdminResponseCreator.createRegisterMessage(
                                playerInstance.getId(),
                                playerInstance.getReceiverPort(),
                                playerService.getDimensions().getKey(),
                                playerService.getDimensions().getValue(),
                                playerInstance.getPosition())
                );

            } else if (playerRequest.getType().equals(RequestType.FINISH_REGISTRATION)) {
                System.out.println("Exiting registration stage");
                break;
            }
        }
    }

    private void tryToSleep() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setExit(boolean exit) {
        this.exit = exit;
        this.thread.interrupt();
        System.out.println("BackGroundWorker killed");
    }

}
