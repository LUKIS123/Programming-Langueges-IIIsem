package pl.edu.pwr.lgawron.lab06.administrator.queue;

import pl.edu.pwr.lgawron.lab06.administrator.utils.AdminResponseCreator;
import pl.edu.pwr.lgawron.lab06.administrator.adminsocket.models.PlayerRequest;
import pl.edu.pwr.lgawron.lab06.administrator.adminsocket.models.RequestType;
import pl.edu.pwr.lgawron.lab06.administrator.adminsocket.flow.PlayerService;
import pl.edu.pwr.lgawron.lab06.common.game.objects.GameInstance;
import pl.edu.pwr.lgawron.lab06.administrator.adminsocket.models.PlayerInstance;

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
            // todo: zrobic dodatkowa petle na rejestracje, zawierajaca tylko 2 typy: register i exit (exit aby wyjsc z petli register)
            //  jesli przycisk end registration -> przejscie do nastepnej petli
            // dodac listenera po obu stronach
            // zmienic sendersocket na tylko jeden
            // dodac eventy logout aby usuwalo graczy po opuszczeniu
            // obsluzyc gety w playerService po stronie serwera

            while (!exit) {
                PlayerRequest playerRequest = queue.popLatest();

                // register
                if (playerRequest.getType().equals(RequestType.REGISTER)) {
                    PlayerInstance playerInstance = playerService.addRegisteredPlayer(playerRequest.getClientServerPort(), playerRequest.getProxyAddress());
                    if (!playerInstance.isBound()) {
                        this.tryToSleep();
                    }
                    playerInstance.getSenderSocket().sendResponse(
                            playerRequest.getClientServerPort(),
                            playerInstance.getProxy(),
                            AdminResponseCreator.createRegisterMessage(
                                    playerInstance.getId(),
                                    playerInstance.getReceiverPort(),
                                    playerService.getDimensions().getKey(),
                                    playerService.getDimensions().getValue(),
                                    playerInstance.getPosition())
                    );
                }

                // see
                if (playerRequest.getType().equals(RequestType.SEE)) {
                    PlayerInstance playerInstance = playerService.getPlayerById(playerRequest.getPlayerId());

                    playerInstance.getSenderSocket().sendResponse(
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

                    playerInstance.getSenderSocket().sendResponse(
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
                    playerInstance.getSenderSocket().sendResponse(playerInstance.getClientServerPort(),
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
                            instance.getSenderSocket().sendResponse(
                                    instance.getClientServerPort(),
                                    instance.getProxy(),
                                    AdminResponseCreator.createGameOverMessage(
                                            instance.getId(),
                                            whoWon.getHowManyTreasuresPicked())
                            );
                        }
                    }
                    whoWon.getSenderSocket().sendResponse(
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
