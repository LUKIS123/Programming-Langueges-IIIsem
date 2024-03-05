package pl.edu.pwr.lgawron.lab06.common.game.objects;

import pl.edu.pwr.lgawron.lab06.common.game.geometry.Point2D;

public class TreasureInstance implements GameInstance {
    private final Point2D position;
    private final int waitingTime;

    public TreasureInstance(Point2D position) {
        this.position = position;
        this.waitingTime = 1000 + (int) (Math.random() * 4000 + (int) (Math.random() * 2000));
    }

    @Override
    public Point2D position() {
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
