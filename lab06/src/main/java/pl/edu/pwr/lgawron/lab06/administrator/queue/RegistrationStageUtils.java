package pl.edu.pwr.lgawron.lab06.administrator.queue;

import pl.edu.pwr.lgawron.lab06.administrator.flow.PlayerService;
import pl.edu.pwr.lgawron.lab06.administrator.models.PlayerInstance;
import pl.edu.pwr.lgawron.lab06.administrator.models.PlayerRequest;
import pl.edu.pwr.lgawron.lab06.administrator.models.RequestType;
import pl.edu.pwr.lgawron.lab06.administrator.utils.AdminResponseCreator;

public class RegistrationStageUtils implements StageActionUtility {
    private final PlayerService playerService;

    public RegistrationStageUtils(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public int executeAction(PlayerRequest playerRequest) {
        if (playerRequest.getType().equals(RequestType.REGISTER)) {
            PlayerInstance playerInstance = playerService.addRegisteredPlayer(playerRequest.getClientServerPort(), playerRequest.getProxyAddress());
            while (!playerInstance.isBound()) {
                this.tryToSleep(5);
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
            return 1;
        } else if (playerRequest.getType().equals(RequestType.FINISH_REGISTRATION)) {
            System.out.println("Exiting registration stage");
            return 0;
        } else if (playerRequest.getType().equals(RequestType.EXIT)) {
            System.out.println("Exiting...");
            return -1;
        }
        return 1;
    }

    @Override
    public void tryToSleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
