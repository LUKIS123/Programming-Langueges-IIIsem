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



                if (playerRequest.getType().equals(RequestType.REGISTER)) {
                    PlayerInstance playerInstance = playerService.addPlayer(playerRequest.getServerPort());
                    senderSocket.sendResponse(
                            playerRequest.getServerPort(),
                            "localhost",
                            MessageParser.createRegisterMessage(playerInstance.getId(), playerInstance.getServerPort())
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
