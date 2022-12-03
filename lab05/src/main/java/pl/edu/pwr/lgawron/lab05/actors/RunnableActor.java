package pl.edu.pwr.lgawron.lab05.actors;

public interface RunnableActor extends Runnable {
    public void run();

    public void setExit(boolean exit);
}
