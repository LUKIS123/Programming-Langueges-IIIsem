package pl.edu.pwr.lgawron.lab06.player.flow;

import javafx.scene.control.Label;
import pl.edu.pwr.lgawron.lab06.mainlogic.playersocket.PlayerSenderSocket;
import pl.edu.pwr.lgawron.lab06.player.utils.GameData;
import pl.edu.pwr.lgawron.lab06.player.utils.PlayerData;

public class PlayerWorker {
    private GameData gameData;
    private PlayerData playerData;
    private final Label info;
    private PlayerSenderSocket senderSocket;

    public PlayerWorker(Label label) {
        this.info = label;
    }

    public void init(int receiverPort, int id) {
        this.playerData = new PlayerData(receiverPort, id);
        this.gameData = new GameData();

        System.out.println("Success" + id + ";" + receiverPort);
        info.setText("Connected, id=" + id + ", listening on=" + receiverPort);
        info.setVisible(true);
    }

    public void setSenderSocket(PlayerSenderSocket senderSocket) {
        this.senderSocket = senderSocket;
    }

    public GameData getGameData() {
        return gameData;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }
}
