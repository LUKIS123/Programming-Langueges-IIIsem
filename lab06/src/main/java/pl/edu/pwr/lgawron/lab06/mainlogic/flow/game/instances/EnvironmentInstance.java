package pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances;

import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry.Point2D;

public interface EnvironmentInstance {
    Point2D getPosition();

    int getWaitingTime();
    String getType();
}
