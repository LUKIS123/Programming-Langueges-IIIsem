package pl.edu.pwr.lgawron.lab05.actors;

import javafx.scene.control.Label;
import pl.edu.pwr.lgawron.lab05.actorresources.Distributor;
import pl.edu.pwr.lgawron.lab05.actorresources.Feeder;
import pl.edu.pwr.lgawron.lab05.frameutility.render.LabelTextRenderer;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Assistant implements RunnableActor {
    private final AtomicInteger food;
    private boolean exit;
    private final int id;
    private final int minSleepTime;
    private final AtomicInteger direction;
    private final List<Assistant> assistantList;
    private final List<Feeder> feederList;
    private final List<Organism> organismList;
    private final List<Label> labelList;
    private Label currentLabel;
    private final AtomicInteger position;
    private final Distributor distributor;

    public Assistant(int id, int startingPosition, int minSleepTime,
                     Distributor distributor, List<Label> labelList, List<Assistant> assistantList,
                     List<Feeder> feederList, List<Organism> organismList) {

        this.food = new AtomicInteger(50);
        this.id = id;
        this.position = new AtomicInteger(startingPosition);
        this.minSleepTime = minSleepTime;
        this.distributor = distributor;
        this.direction = new AtomicInteger(1);

        // lists
        this.labelList = labelList;
        this.assistantList = assistantList;
        this.feederList = feederList;
        this.organismList = organismList;

        // labels
        this.currentLabel = labelList.get(position.get());
        this.refreshLabel();


        Thread thread = new Thread(this, "Assistant-" + id);
        this.exit = false;
        thread.start();
    }

    @Override
    public void run() {
        while (!exit) {
            try {
                Thread.sleep(minSleepTime + (int) (Math.random() * 100));
                this.nourish();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (food.get() == 0) {
                this.takeFoodSupply();
            }

            this.move();
            this.refreshLabel();
        }
    }

    public synchronized void nourish() throws InterruptedException {
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
                feeder.stockUpResource(id, nourishment + quantity);
                food.set(food.get() - quantity);
            } else {
                feeder.stockUpResource(id, nourishment + food.get());
                food.set(0);
            }
            this.refreshLabel();
            feeder.finishProcess(id);
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
            this.refreshLabel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.tryToSleep();
    }

    private void tryToSleep() {
        try {
            Thread.sleep(minSleepTime + (int) (Math.random() * 100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setExit(boolean exit) {
        this.exit = exit;
    }

    private void refreshLabel() {
        LabelTextRenderer.renderAssistants(currentLabel, food.get(), id);
    }

    private void clearLabel(int oldPosition) {
        LabelTextRenderer.renderEmptyAssistant(labelList.get(oldPosition));
    }
}
