package pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances;

import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry.Point2D;

public class TreasureInstance implements EnvironmentInstance {
    private final Point2D position;
    private final int waitingTime;

    public TreasureInstance(Point2D position) {
        this.position = position;
        this.waitingTime = 1000 + (int) (Math.random() * 5000);
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getWaitingTime() {
        return waitingTime;
    }

    @Override
    public String getType() {
        return "treasure";
    }
}
