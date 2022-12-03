package pl.edu.pwr.lgawron.lab05.actorresources;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.concurrent.atomic.AtomicInteger;

public class Distributor implements ResourceHolder {
    private final int minSleepTime;
    private final Label label;
    private final AtomicInteger value;

    public Distributor(int minSleepTime, Label label) {
        this.minSleepTime = minSleepTime;
        this.label = label;
        this.value = new AtomicInteger(0);
        this.refreshLabel();
    }

    @Override
    public void stockUpResource(int id) throws InterruptedException {
        synchronized (this) {
            this.refreshLabel(false, id);

            while (value.get() == 50) {
                wait();
            }

            Thread.sleep(minSleepTime + (int) (Math.random() * 100));
            value.set(50);
        }
    }

    @Override
    public void stockUpResource(int id, int amount) throws InterruptedException {
    }

    @Override
    public void finishProcess(int id) throws InterruptedException {
        synchronized (this) {
            while (value.get() == 0) {
                wait();
            }

            value.set(0);
            this.refreshLabel(true, id);
            notifyAll();
        }
    }

    @Override
    public synchronized int getResource() {
        return value.get();
    }

    private void refreshLabel(boolean variant, int id) {
        Platform.runLater(
                () -> {
                    if (variant) {
                        label.setText("|____free____|");
                    } else {
                        label.setText("|used_by_as-" + String.valueOf(Character.toChars(id + 65)) + "|");
                    }
                });
    }

    private void refreshLabel() {
        Platform.runLater(
                () -> {
                    label.setText("|____free____|");
                });
    }
}
