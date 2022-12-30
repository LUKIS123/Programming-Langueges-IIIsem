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
            // todo: dodac listenera po obu stronach receiverSocket

            // registration stage
            this.registrationStage();

            // -> game stage
            while (!exit) {
                PlayerRequest playerRequest = queue.popLatest();
                switch (playerRequest.getType()) {
                    case SEE -> {
                        PlayerInstance playerInstance = playerService.getPlayerById(playerRequest.getPlayerId());

                        if (playerInstance.getId() == -1) {
                            break;
                        }

                        playerInstance.getSenderSocket().sendMessage(
                                playerInstance.getClientServerPort(),
                                playerInstance.getProxy(),
                                AdminResponseCreator.createSeeMessage(playerInstance.getId(),
                                        playerService.getAdjacentParsed(playerInstance))
                        );
                    }

                    case MOVE -> {
                        PlayerInstance playerInstance = playerService.movePlayer(playerRequest.getPlayerId(), playerRequest.getMoveX(), playerRequest.getMoveY());
                        if (playerInstance.getId() == -1) {
                            break;
                        }

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

                    case TAKE -> {
                        PlayerInstance playerInstance = playerService.takeTreasureAttempt(playerRequest.getPlayerId(), playerRequest.getTreasureX(), playerRequest.getTreasureY());
                        if (playerInstance.getId() == -1) {
                            break;
                        }

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

                    case GAME_OVER -> {
                        List<PlayerInstance> playerList = playerService.getPlayerList();
                        Optional<PlayerInstance> found = playerService.getWhoWon();
                        if (found.isPresent()) {
                            PlayerInstance whoWon = found.get();
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
                        } else {
                            for (PlayerInstance instance : playerList) {
                                instance.getSenderSocket().sendMessage(
                                        instance.getClientServerPort(),
                                        instance.getProxy(),
                                        AdminResponseCreator.createGameOverMessage(
                                                instance.getId(),
                                                instance.getHowManyTreasuresPicked())
                                );
                            }
                        }
                    }

                    case LEAVE -> playerService.kickPlayer(playerRequest.getPlayerId());

                    case EXIT -> this.setExit(true);

                    default -> {
                    }

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
