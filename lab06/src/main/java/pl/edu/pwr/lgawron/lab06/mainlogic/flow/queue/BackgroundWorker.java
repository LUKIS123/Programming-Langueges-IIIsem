package pl.edu.pwr.lgawron.lab06.mainlogic.flow.queue;

import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.AdminSenderSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.models.PlayerRequest;
import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.models.RequestType;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.PlayerService;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.EnvironmentInstance;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.PlayerInstance;
import pl.edu.pwr.lgawron.lab06.administrator.utils.AdminResponseParser;

import java.util.Optional;

public class BackgroundWorker {
    private boolean exit;
    private Thread thread;
    private final RequestQueue queue;
    private final AdminSenderSocket senderSocket;
    private final PlayerService playerService;
    private final String proxy = "localhost";

    public BackgroundWorker(RequestQueue requestQueue, AdminSenderSocket senderSocket, PlayerService playerService) {
        this.queue = requestQueue;
        this.senderSocket = senderSocket;
        this.playerService = playerService;
        this.exit = false;
    }

    public void start() {
        thread = new Thread(() -> {

            while (!exit) {
                PlayerRequest playerRequest = queue.popLatest();

                // register
                if (playerRequest.getType().equals(RequestType.REGISTER)) {
                    PlayerInstance playerInstance = playerService.addRegisteredPlayer(playerRequest.getClientServerPort());
                    if (!playerInstance.isBound()) {
                        this.tryToSleep();
                    }
                    playerInstance.getSenderSocket().sendResponse(
                            playerRequest.getClientServerPort(),
                            proxy,
                            AdminResponseParser.createRegisterMessage(
                                    playerInstance.getId(),
                                    playerInstance.getReceiverPort(),
                                    playerService.getDimensions().getKey(),
                                    playerService.getDimensions().getValue(),
                                    playerInstance.getPosition())
                    );
                }

                // game over


                // see
                if (playerRequest.getType().equals(RequestType.SEE)) {
                    PlayerInstance playerInstance = playerService.getPlayerById(playerRequest.getPlayerId());

                    playerInstance.getSenderSocket().sendResponse(
                            playerInstance.getClientServerPort(),
                            proxy,
                            AdminResponseParser.createSeeMessage(playerInstance.getId(),
                                    playerService.getAdjacentParsed(playerInstance))
                    );
                }

                // move
                if (playerRequest.getType().equals(RequestType.MOVE)) {
                    PlayerInstance playerInstance = playerService.movePlayer(playerRequest.getPlayerId(), playerRequest.getMoveX(), playerRequest.getMoveY());
                    Optional<EnvironmentInstance> instanceOnSpot = playerService.checkForTreasure(playerInstance.getPosition().getPositionX(), playerInstance.getPosition().getPositionY());

                    playerInstance.getSenderSocket().sendResponse(
                            playerInstance.getClientServerPort(),
                            proxy,
                            AdminResponseParser.createMoveMessage(
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
                            proxy,
                            AdminResponseParser.createTakeMessage(
                                    playerInstance.getId(),
                                    playerInstance.isTakeAttempt(),
                                    playerInstance.getCurrentWaitingTime(),
                                    playerInstance.getHowManyTreasuresPicked()
                            )
                    );
                    playerInstance.setTakeAttempt(false);
                    playerInstance.setCurrentWaitingTime(0);
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
    }
}
