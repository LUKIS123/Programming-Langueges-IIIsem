package pl.edu.pwr.lgawron.lab05.actorresources;

import javafx.scene.control.Label;
import pl.edu.pwr.lgawron.lab05.frameutility.render.LabelTextRenderer;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Feeder implements ResourceHolder {
    private final AtomicInteger nourishment;
    private final AtomicBoolean freeToUse;
    private final int minSleepTime;
    private final Label label;
    private final int id;
    private final Object lock;

    public Feeder(int minSleepTime, Label label, int id) {
        this.minSleepTime = minSleepTime;
        this.label = label;
        this.id = id;
        this.freeToUse = new AtomicBoolean(true);
        this.lock = new Object();

        this.nourishment = new AtomicInteger(5);
        this.refreshLabel(false);
    }

    public synchronized boolean consumeResource() {
        if (!freeToUse.get()) {
            return false;
        }

        if (nourishment.get() != 0) {
            freeToUse.set(false);

            nourishment.set(nourishment.get() - 1);
            this.refreshLabel(false);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void stockUpResource(int id, int amount) throws InterruptedException {
        synchronized (lock) {
            this.refreshLabel(true);
            while (!freeToUse.get()) {
                wait();
            }
            freeToUse.set(false);

            Thread.sleep(minSleepTime + (int) (Math.random() * 100));
            nourishment.set(amount);
            this.refreshLabel(true);
        }
    }

    @Override
    public void finishProcess(int id) throws InterruptedException {
        synchronized (lock) {
            while (freeToUse.get()) {
                wait();
            }
            freeToUse.set(true);
            this.refreshLabel(false);
        }
    }

    @Override
    public int getResource() {
        return nourishment.get();
    }

    @Override
    public void stockUpResource(int id) {
    }

    public synchronized AtomicInteger getNourishment() {
        return nourishment;
    }

    public synchronized void setFreeToUse(boolean freeToUse) {
        this.freeToUse.set(freeToUse);
    }

    public int getId() {
        return id;
    }

    private void refreshLabel(boolean isRefilled) {
        LabelTextRenderer.renderNourishment(label, nourishment.get(), isRefilled);
    }
}
