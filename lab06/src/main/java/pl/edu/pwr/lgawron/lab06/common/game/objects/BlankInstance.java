package pl.edu.pwr.lgawron.lab06.common.game.objects;

import pl.edu.pwr.lgawron.lab06.common.game.geometry.Point2D;

public record BlankInstance(Point2D position) implements GameInstance {
    @Override
    public int getWaitingTime() {
        return 0;
    }

    @Override
    public String getType() {
        return "blank";
    }
}
