package pl.edu.pwr.lgawron.lab05.actors;

import javafx.application.Platform;
import javafx.scene.control.Label;
import pl.edu.pwr.lgawron.lab05.flow.ApplicationFlow;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Assistant implements RunnableActor {
    private final AtomicInteger food = new AtomicInteger(50);
    private boolean exit;
    private final int id;
    private final int minSleepTime;
    private AtomicInteger direction;
    private List<Assistant> assistantList;
    private List<Feeder> feederList;
    private List<Label> labelList;
    private Thread t;
    private Label currentLabel;
    private AtomicInteger position;
    private final Distributor distributor;
    private final ApplicationFlow flow;

    public Assistant(int id, int startingPosition, int minSleepTime, ApplicationFlow flow, Distributor distributor) {
        this.id = id;
        this.position = new AtomicInteger(startingPosition);
        this.minSleepTime = minSleepTime;
        this.flow = flow;
        this.distributor = distributor;
        this.direction = new AtomicInteger(1);

        // listy
        this.labelList = flow.getAssistantLabels();
        this.assistantList = flow.getAssistants();
        this.feederList = flow.getFeeders();

        // lebelka
        this.currentLabel = labelList.get(position.get());
        this.refreshLabel();


        t = new Thread(this, "Distributor-" + id);
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

            nourish();
            // UWAGA w metodzie move zmiana labelki pozycji !!!!
            move();
            this.refreshLabel();
        }
    }

    public synchronized void nourish() {
        Feeder feeder = feederList.get(position.get());
        if (feeder.getNourishment().get() > 5) {
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

    // doczytac czy organizmom ma sie odnawiac zycie po zjedzeniu
    public synchronized void move() {
        // do tej pory jest git
        // uzyc direction do zmiany pozycji w liscie

        int nextPosition = position.get() + direction.get();
        if (nextPosition == -1 || nextPosition >= assistantList.size()) {
            this.changeDirection();
            return;
        }

        if (assistantList.get(nextPosition) == null) {
            Collections.swap(assistantList, position.get(), nextPosition);

            int oldPosition = position.get();

            position.set(nextPosition);

            // labelki
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
        if (distributor.isFreeToUse()) {
            distributor.setFreeToUse(false);
            food.set(50);
        }
        this.tryToSleep();
    }

    private void refreshLabel() {
        Platform.runLater(
                () -> currentLabel.setText("|   " + food.get() + "   |")
        );
    }

    // do znajdywania

    // -> indexof nie dziala
    // cala funckja troche bez sensu
    private int getCurrentPosition() {
        // return assistantList.indexOf(this);
        Optional<Assistant> first = assistantList.stream().filter(assistant -> assistant.getId() == this.id).findFirst();
        return first.map(Assistant::getCurrentPosition).orElseGet(() -> position.get());
    }

    private void tryToSleep() {
        try {
            Thread.sleep(minSleepTime + (int) (Math.random() * 100));
            // t.wait(minSleepTime + (int) (Math.random() * 100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public AtomicInteger getFood() {
        return food;
    }

    public void setFood(int value) {
        this.food.set(value);
    }

    public int getId() {
        return id;
    }

    public boolean isExit() {
        return exit;
    }

    public int getMinSleepTime() {
        return minSleepTime;
    }

    public Thread getT() {
        return t;
    }

    public int getPosition() {
        return position.get();
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public void setT(Thread t) {
        this.t = t;
    }

    public void setPosition(int newPosition) {
        position.set(newPosition);
    }
}
