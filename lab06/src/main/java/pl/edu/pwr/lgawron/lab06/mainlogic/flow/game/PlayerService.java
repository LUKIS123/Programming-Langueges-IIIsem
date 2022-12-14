package pl.edu.pwr.lgawron.lab06.mainlogic.flow.game;

import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances.PlayerInstance;

import java.util.ArrayList;
import java.util.List;

public class PlayerService {
    private final List<PlayerInstance> playerList;
    private final int adminServerPort;
    private int sequence;

    public PlayerService(int adminServerPort) {
        this.playerList = new ArrayList<>();
        this.adminServerPort = adminServerPort;
        this.sequence = 1;
    }

//    public PlayerInstance createNewPlayer() {
//        return new PlayerInstance(sequence, adminServerPort + sequence);
//    }

    public PlayerInstance addPlayer(int playerServerPort) {
        PlayerInstance newPlayer = new PlayerInstance(sequence, playerServerPort);
        playerList.add(newPlayer);
        sequence++;
        return newPlayer;
    }

    public List<PlayerInstance> getPlayerList() {
        return playerList;
    }
}
