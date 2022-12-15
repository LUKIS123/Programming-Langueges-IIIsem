package pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances;

import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry.Point2D;

public class PlayerInstance {
    private final int id;
    private final int serverPort;
    private Point2D position;

    public PlayerInstance(int id, int serverPort) {
        this.id = id;
        this.serverPort = serverPort;
    }

    public Point2D getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }
}
