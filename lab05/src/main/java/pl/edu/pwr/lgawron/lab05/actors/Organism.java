package pl.edu.pwr.lgawron.lab05.actors;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Organism implements RunnableActor {
    private final AtomicBoolean isAlive = new AtomicBoolean(true);
    private final AtomicInteger stamina = new AtomicInteger(5);
    private final Feeder feeder;
    private final int minSleepTime;
    private final int id;
    private final Label label;
    private final Thread t;

    public Organism(Feeder feeder, int minSleepTime, int id, Label label) {
        this.feeder = feeder;
        this.minSleepTime = minSleepTime;
        this.id = id;
        this.label = label;

        this.refreshLabel();

        t = new Thread(this, "Organism-" + id);
        t.start();
    }

    @Override
    public void run() {
        while (isAlive.get()) {
            try {
                Thread.sleep(minSleepTime + (int) (Math.random() * 100));
                // t.wait(minSleepTime + (int) (Math.random() * 100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (!this.eat()) {
                this.decay();
            } else {
                if (stamina.get() < 5) {
                    stamina.set(stamina.get() + 1);
                }
            }

            // else stamina +1 -> tylko jesli mniejsza od 5
            this.refreshLabel();
        }
    }

    private void refreshLabel() {
        Platform.runLater(
                () -> label.setText("|   " + stamina.get() + "   |")
        );
    }

    public synchronized boolean eat() {
        return feeder.consume();
    }

    public void decay() {
        if (stamina.get() == 1) {
            stamina.set(0);
            isAlive.set(false);
        } else {
            stamina.set(stamina.get() - 1);
        }
    }

    public AtomicInteger getStamina() {
        return stamina;
    }

    public int getId() {
        return id;
    }

    public AtomicBoolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean value) {
        this.isAlive.set(value);
    }
}
