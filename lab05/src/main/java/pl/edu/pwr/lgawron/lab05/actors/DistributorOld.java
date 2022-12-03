package pl.edu.pwr.lgawron.lab05.actors;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.concurrent.atomic.AtomicBoolean;

public class DistributorOld implements RunnableActor {
    private final AtomicBoolean freeToUse = new AtomicBoolean(true);
    private boolean exit;
    private final int id;
    private final int minSleepTime;
    private final Label label;
    private Thread t;

    public DistributorOld(int id, int minSleepTime, Label label) {
        this.id = id;
        this.minSleepTime = minSleepTime;
        this.label = label;

        this.refreshLabel();

        t = new Thread(this, "Distributor-" + id);
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
                    //t.wait(minSleepTime + (int) (Math.random() * 100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.reset();
            }
            this.refreshLabel();
        }
    }

    private void refreshLabel() {
        Platform.runLater(
                () -> {
                    if (freeToUse.get()) {
                        label.setText("|   free   |");
                    } else {
                        label.setText("|   used   |");
                    }
                });
    }

    private void tryToSleep() {
        try {
            Thread.sleep(minSleepTime + (int) (Math.random() * 100));
            // t.wait(minSleepTime + (int) (Math.random() * 100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void reset() {
        this.setFreeToUse(true);
        // notifyAll();
    }

    public synchronized boolean isFreeToUse() {
        return freeToUse.get();
    }

    public boolean isExit() {
        return exit;
    }

    public int getId() {
        return id;
    }

    public int getMinSleepTime() {
        return minSleepTime;
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

    public synchronized void setFreeToUse(boolean freeToUse) {
        this.freeToUse.set(freeToUse);
        this.refreshLabel();
    }
}
