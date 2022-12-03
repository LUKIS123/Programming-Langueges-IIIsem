package pl.edu.pwr.lgawron.lab05.actors;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import pl.edu.pwr.lgawron.lab05.actorresources.Distributor;
import pl.edu.pwr.lgawron.lab05.flow.ApplicationFlow;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Assistant implements RunnableActor {
    private final AtomicInteger food = new AtomicInteger(50);
    private final Object lock;
    private boolean exit;
    private final int id;
    private final int minSleepTime;
    private final AtomicInteger direction;
    private final List<Assistant> assistantList;
    private final List<Feeder> feederList;
    private final List<Organism> organismList;
    private final List<Label> labelList;
    private Thread t;
    private Label currentLabel;
    private final AtomicInteger position;
    private final Distributor distributor;
    private final ApplicationFlow flow;

    public Assistant(int id, int startingPosition, int minSleepTime, ApplicationFlow flow, Distributor distributor) {
        this.id = id;
        this.position = new AtomicInteger(startingPosition);
        this.minSleepTime = minSleepTime;
        this.flow = flow;
        this.distributor = distributor;
        this.direction = new AtomicInteger(1);
        this.lock = new Object();

        // lists
        this.labelList = flow.getAssistantLabels();
        this.assistantList = flow.getAssistantNewList();
        this.feederList = flow.getFeeders();
        this.organismList = flow.getOrganisms();

        // labels
        this.currentLabel = labelList.get(position.get());
        this.refreshLabel();


        t = new Thread(this, "Assistant-" + id);
        exit = false;
        t.start();
    }

    @Override
    public void run() {
        while (!exit) {
            try {
                Thread.sleep(minSleepTime + (int) (Math.random() * 100));
                //t.wait(minSleepTime + (int) (Math.random() * 100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (food.get() == 0) {
                this.takeFoodSupply();
            }

            this.nourish();
            this.move();
            this.refreshLabel();
        }
    }

    public synchronized void nourish() {
        Feeder feeder = feederList.get(position.get());

        if (feeder.getNourishment().get() > 5) {
            return;
        }

        if (organismList.get(position.get()).getStamina().get() == 0) {
            return;
        }

        if (food.get() != 0) {

            int nourishment = feeder.getNourishment().get();
            int quantity = 10 - nourishment;

            if (food.get() >= quantity) {
                feeder.refill(nourishment + quantity);
                food.set(food.get() - quantity);
            } else {
                feeder.refill(nourishment + food.get());
                food.set(0);
            }
        }
    }

    public synchronized void move() {
        int nextPosition = position.get() + direction.get();
        if (nextPosition == -1 || nextPosition >= assistantList.size()) {
            this.changeDirection();
            return;
        }

        if (assistantList.get(nextPosition) == null) {
            Collections.swap(assistantList, position.get(), nextPosition);

            int oldPosition = position.get();

            position.set(nextPosition);

            // updating labels
            currentLabel = labelList.get(nextPosition);
            this.clearLabel(oldPosition);
            this.refreshLabel();

        } else {
            this.changeDirection();
        }
    }

    private void clearLabel(int oldPosition) {
        Platform.runLater(
                () -> labelList.get(oldPosition).setText("|_______|")
        );
    }

    private void changeDirection() {
        if (direction.get() == 1) {
            direction.set(-1);
        } else {
            direction.set(1);
        }
    }

    public synchronized void takeFoodSupply() {
        try {
            distributor.stockUpResource(this.id);
            food.set(distributor.getResource());
            distributor.finishProcess(this.id);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.tryToSleep();
    }

    private void refreshLabel() {
        Platform.runLater(
                () -> currentLabel.setText("|   " + food.get() + "   |")
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

    public void tryToWait(ActionEvent actionEvent) {

        while (!actionEvent.isConsumed()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //lock.notify();
    }

    public void tryToNotify() {
        synchronized (lock) {
            lock.notify();
        }
    }

    public int getId() {
        return id;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
