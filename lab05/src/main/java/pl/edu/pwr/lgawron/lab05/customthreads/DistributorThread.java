package pl.edu.pwr.lgawron.lab05.customthreads;

public class DistributorThread implements Runnable {
    private boolean running;
    private final Variant variant;
    private final int id;
    private Thread t;
    int minSleepTime;

    public DistributorThread(Variant variant, int id, int minSleepTime) {
        this.variant = variant;
        this.id = id;
        this.minSleepTime = minSleepTime;

        t = new Thread(this, variant + "-" + id);
        running = true;
        t.start();
    }

    @Override
    public void run() {
        while (running) {
            try {

                Thread.sleep(minSleepTime + (int) (Math.random() * 100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public boolean isRunning() {
        return running;
    }

    public Variant getVariant() {
        return variant;
    }

    public int getId() {
        return id;
    }

    public Thread getT() {
        return t;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setT(Thread t) {
        this.t = t;
    }
}
