package pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances;

import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry.Point2D;

public class PathInstance implements EnvironmentInstance {
    private final Point2D position;

    public PathInstance(Point2D position) {
        this.position = position;
    }

    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getWaitingTime() {
        return 0;
    }

    @Override
    public String getType() {
        return "path";
    }
}
