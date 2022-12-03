package pl.edu.pwr.lgawron.lab05.actorresources;

import javafx.scene.control.Label;
import pl.edu.pwr.lgawron.lab05.frameutility.render.LabelTextRenderer;

import java.util.concurrent.atomic.AtomicInteger;

public class Distributor implements ResourceHolder {
    private final int minSleepTime;
    private final Label label;
    private final AtomicInteger value;

    public Distributor(int minSleepTime, Label label) {
        this.minSleepTime = minSleepTime;
        this.label = label;
        this.value = new AtomicInteger(0);
        this.refreshLabel(0, false);
    }

    @Override
    public void stockUpResource(int id) throws InterruptedException {
        synchronized (this) {
            this.refreshLabel(id, true);

            while (value.get() == 50) {
                wait();
            }

            Thread.sleep(minSleepTime + (int) (Math.random() * 120));
            value.set(50);
        }
    }

    @Override
    public void finishProcess(int id) throws InterruptedException {
        synchronized (this) {
            while (value.get() == 0) {
                wait();
            }

            value.set(0);
            this.refreshLabel(id, false);
            notifyAll();
        }
    }

    @Override
    public synchronized int getResource() {
        return value.get();
    }

    @Override
    public void stockUpResource(int id, int amount) throws InterruptedException {
    }

    private void refreshLabel(int id, boolean isUsed) {
        LabelTextRenderer.renderDistributor(label, id, isUsed);
    }
}
