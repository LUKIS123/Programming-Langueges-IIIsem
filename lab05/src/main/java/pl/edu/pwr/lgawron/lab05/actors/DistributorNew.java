package pl.edu.pwr.lgawron.lab05.actors;

import javafx.scene.control.Label;

import java.util.concurrent.atomic.AtomicInteger;

public class DistributorNew implements Resource {
    private final int minSleepTime;
    private final Label label;
    private final AtomicInteger value;

    public DistributorNew(int minSleepTime, Label label) {
        // private final Object lock;
        this.minSleepTime = minSleepTime;
        this.label = label;
        //this.lock = new Object();
        this.value = new AtomicInteger(0);
    }

    public void dispense() throws InterruptedException {
        synchronized (this) {
            while (value.get() == 50) {
                wait();
            }
            value.set(50);
        }
    }

    public void finish() throws InterruptedException {
        synchronized (this) {
            while (value.get() == 0) {
                wait();
            }
            value.set(0);
            notifyAll();
        }
        Thread.sleep(minSleepTime + (int) (Math.random() * 100));
    }

    public Label getLabel() {
        return label;
    }

    public synchronized int getValue() {
        return value.get();
    }
}
