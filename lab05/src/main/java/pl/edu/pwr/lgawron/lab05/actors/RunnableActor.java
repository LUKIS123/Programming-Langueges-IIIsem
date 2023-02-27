package pl.edu.pwr.lgawron.lab05.actors;

public interface RunnableActor extends Runnable {
    void run();

    void setExit(boolean exit);
}
