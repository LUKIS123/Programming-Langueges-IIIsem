package pl.edu.pwr.lgawron.lab06.common.game.objects;

import pl.edu.pwr.lgawron.lab06.common.game.geometry.Point2D;

public interface GameInstance {
    Point2D position();

    int getWaitingTime();

    String getType();
}
