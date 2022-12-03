package pl.edu.pwr.lgawron.lab05.actors;

import javafx.scene.control.Label;
import pl.edu.pwr.lgawron.lab05.actorresources.Feeder;
import pl.edu.pwr.lgawron.lab05.frameutility.render.LabelTextRenderer;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Organism implements RunnableActor {
    private final AtomicBoolean isAlive;
    private final AtomicInteger stamina;
    private final Feeder feeder;
    private final int minSleepTime;
    private final int id;
    private final Label label;

    public Organism(Feeder feeder, int minSleepTime, int id, Label label) {
        this.isAlive = new AtomicBoolean(true);
        this.stamina = new AtomicInteger(5);
        this.feeder = feeder;
        this.minSleepTime = minSleepTime;
        this.id = id;
        this.label = label;

        this.refreshLabel();

        Thread thread = new Thread(this, "Organism-" + id);
        thread.start();
    }

    @Override
    public void run() {
        while (isAlive.get()) {
            try {
                Thread.sleep(minSleepTime + (int) (Math.random() * 100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (!this.eat()) {
                this.decay();
            } else {
                if (stamina.get() < 5) {
                    stamina.set(stamina.get() + 1);
                }
                feeder.setFreeToUse(true);
            }

            this.refreshLabel();
        }
    }

    public synchronized boolean eat() {
        return feeder.consumeResource();
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

    @Override
    public void setExit(boolean exit) {
        this.isAlive.set(exit);
    }

    private void refreshLabel() {
        LabelTextRenderer.renderStamina(label, stamina.get());
    }
}