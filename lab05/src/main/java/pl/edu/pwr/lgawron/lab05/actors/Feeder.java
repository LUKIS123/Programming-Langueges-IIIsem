package pl.edu.pwr.lgawron.lab05.actors;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Feeder implements RunnableActor {
    private final AtomicBoolean freeToUse = new AtomicBoolean(true);
    private boolean exit;
    private final AtomicInteger nourishment = new AtomicInteger(5);
    private final int minSleepTime;
    private final Label label;
    private final int id;
    private Thread t;

    public Feeder(int minSleepTime, int id, Label label) {
        this.minSleepTime = minSleepTime;
        this.id = id;
        this.label = label;

        this.refreshLabel();

        t = new Thread(this, "Feeder-" + id);
        exit = false;
        t.start();
    }

    @Override
    public void run() {
        while (!exit) {
            this.tryToSleep();
            if (!isFreeToUse()) {
                try {
                    Thread.sleep(minSleepTime + (int) (Math.random() * 100));
                    // t.wait(minSleepTime + (int) (Math.random() * 100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.setFreeToUse(true);
            }

            // notifyAll();
            // nourishment.set(nourishment.get() - 1);
            this.refreshLabel();
        }
    }

    private void refreshLabel() {
        Platform.runLater(
                () -> label.setText("|   " + nourishment.get() + "   |")
        );
    }

    private void tryToSleep() {
        try {
            Thread.sleep(minSleepTime + (int) (Math.random() * 100));
            // t.wait(minSleepTime + (int) (Math.random() * 100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void refill(int amount) {
        nourishment.set(amount);

        this.refreshLabel();
        this.setFreeToUse(false);
    }

    public synchronized boolean consume() {
        if (nourishment.get() != 0) {
            this.setFreeToUse(false);
            nourishment.set(nourishment.get() - 1);

            this.refreshLabel();
            return true;
        } else {
            this.refreshLabel();
            return false;
        }
    }

    public synchronized AtomicInteger getNourishment() {
        return nourishment;
    }

    public int getId() {
        return id;
    }

    public synchronized boolean isFreeToUse() {
        return freeToUse.get();
    }

    public boolean isExit() {
        return exit;
    }

    public Thread getT() {
        return t;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public void setT(Thread t) {
        this.t = t;
    }

    public synchronized void setNourishment(int value) {
        this.nourishment.set(value);
    }

    public synchronized void setFreeToUse(boolean freeToUse) {
        this.freeToUse.set(freeToUse);
    }
}
