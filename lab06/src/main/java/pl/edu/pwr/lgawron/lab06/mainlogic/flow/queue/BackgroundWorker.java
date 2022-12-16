package pl.edu.pwr.lgawron.lab06.mainlogic.flow.queue;

import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.AdminSenderSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.models.PlayerRequest;
import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.models.RequestType;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.PlayerService;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.PlayerInstance;
import pl.edu.pwr.lgawron.lab06.administrator.utils.MessageParser;

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

                    playerInstance.getSenderSocket().sendResponse(
                            playerRequest.getClientServerPort(),
                            "localhost",
                            MessageParser.createRegisterMessage(
                                    playerInstance.getId(),
                                    playerInstance.getReceiverPort(), // cos nie dziala -> null pointer
                                    playerInstance.getPosition())
                    );
                }

                // see
                if (playerRequest.getType().equals(RequestType.SEE)) {
                    PlayerInstance playerInstance = playerService.getPlayerById(playerRequest.getPlayerId());
                    playerInstance.getSenderSocket().sendResponse(
                            playerInstance.getClientServerPort(),
                            "localhost",
                            MessageParser.createSeeMessage(playerInstance.getId(),
                                    playerService.getAdjacentParsed(playerInstance))
                    );
                }

                // move
                if (playerRequest.getType().equals(RequestType.MOVE)) {
                    // PlayerInstance playerInstance = playerService.getPlayerById(playerRequest.getPlayerId());
                    PlayerInstance playerInstance = playerService.movePlayer(playerRequest.getPlayerId(), playerRequest.getMoveX(), playerRequest.getMoveY());
                    playerInstance.getSenderSocket().sendResponse(
                            playerInstance.getClientServerPort(),
                            proxy,
                            MessageParser.createMoveMessage(playerRequest.getPlayerId())
                    );


                }


            }

        });
        thread.start();
    }


    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
