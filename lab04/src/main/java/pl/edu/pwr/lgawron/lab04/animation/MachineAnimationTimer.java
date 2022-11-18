package pl.edu.pwr.lgawron.lab04.animation;

import javafx.animation.AnimationTimer;

public class MachineAnimationTimer extends AnimationTimer {
    private volatile boolean running;

    @Override
    public void handle(long now) {
        super.start();
        running = true;
    }

    @Override
    public void start() {
        super.start();
        running = true;
    }

    @Override
    public void stop() {
        super.stop();
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
