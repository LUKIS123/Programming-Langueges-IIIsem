package pl.edu.pwr.lgawron.lab05.actorresources;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FeederNew implements ResourceHolder {
    private final AtomicInteger nourishment;
    private final AtomicBoolean freeToUse;
    private final int minSleepTime;
    private final Label label;
    private final int id;

    public FeederNew(int minSleepTime, Label label, int id) {
        this.minSleepTime = minSleepTime;
        this.label = label;
        this.id = id;
        this.freeToUse = new AtomicBoolean(true);

        this.nourishment = new AtomicInteger(5);
        this.refreshLabel();
    }

    public boolean consumeResource() throws InterruptedException {
        synchronized (this) {
            while (!freeToUse.get()) {
                wait();
            }

            if (nourishment.get() != 0) {
                freeToUse.set(false);
                nourishment.set(nourishment.get() - 1);

                this.refreshLabel();
                return true;
            } else {
                this.refreshLabel();
                return false;
            }
        }
    }

    @Override
    public void stockUpResource(int id) throws InterruptedException {
    }

    @Override
    public void stockUpResource(int id, int amount) throws InterruptedException {
        synchronized (this) {
            this.refreshLabel();
            while (!freeToUse.get()) {
                wait();
            }
            freeToUse.set(false);

            Thread.sleep(minSleepTime + (int) (Math.random() * 100));
            nourishment.set(amount);
        }
    }

    @Override
    public void finishProcess(int id) throws InterruptedException {
        while (freeToUse.get()) {
            wait();
        }
        freeToUse.set(true);

        this.refreshLabel();
        notifyAll();
    }

    @Override
    public int getResource() {
        return nourishment.get();
    }

    public synchronized AtomicInteger getNourishment() {
        return nourishment;
    }

    public synchronized boolean getFreeToUse() {
        return freeToUse.get();
    }

    public synchronized void setFreeToUse(boolean freeToUse) {
        this.freeToUse.set(freeToUse);
    }

    private void refreshLabel() {
        Platform.runLater(
                () -> label.setText("|   " + nourishment.get() + "   |")
        );
    }
}
