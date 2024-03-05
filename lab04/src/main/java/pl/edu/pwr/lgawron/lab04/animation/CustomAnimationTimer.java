package pl.edu.pwr.lgawron.lab04.animation;

import javafx.animation.AnimationTimer;

public abstract class CustomAnimationTimer extends AnimationTimer {
    private volatile boolean running;
    private long sleepNs = 0;
    long prevTime = 0;

    public CustomAnimationTimer(long sleepMs) {
        this.sleepNs = sleepMs * 1_000_000;
    }

    @Override
    public void handle(long now) {

        // some delay
        if ((now - prevTime) < sleepNs) {
            return;
        }
        prevTime = now;

        handle();
    }

    public abstract void handle();

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
