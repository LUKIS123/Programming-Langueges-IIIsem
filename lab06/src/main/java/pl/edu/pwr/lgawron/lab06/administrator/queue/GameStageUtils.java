package pl.edu.pwr.lgawron.lab06.administrator.queue;

import pl.edu.pwr.lgawron.lab06.administrator.flow.PlayerService;
import pl.edu.pwr.lgawron.lab06.administrator.models.PlayerInstance;
import pl.edu.pwr.lgawron.lab06.administrator.models.PlayerRequest;
import pl.edu.pwr.lgawron.lab06.administrator.utils.AdminResponseCreator;
import pl.edu.pwr.lgawron.lab06.common.game.objects.GameInstance;

import java.util.List;
import java.util.Optional;

public class GameStageUtils implements StageActionUtility {
    private final PlayerService playerService;

    public GameStageUtils(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public int executeAction(PlayerRequest playerRequest) {
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

            case EXIT -> {
                return -1;
            }

            default -> {
            }

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
