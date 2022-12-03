package pl.edu.pwr.lgawron.lab05.customthreads;

import javafx.scene.control.Label;
import pl.edu.pwr.lgawron.lab05.actors.DistributorNew;

public class DistributorThreadNew implements Runnable {
    private final DistributorNew distributor;
    private final int id;
    private final int minSleepTime;
    private final Label label;
    private Thread t;
    private boolean exit;

    public DistributorThreadNew(int id, int minSleepTime, Label label) {
        this.id = id;
        this.minSleepTime = minSleepTime;
        this.label = label;

        this.distributor = new DistributorNew(minSleepTime, label);
        t = new Thread(this, "Distributor-" + id);
        exit = false;
        t.start();
    }

    @Override
    public void run() {

    }
}
