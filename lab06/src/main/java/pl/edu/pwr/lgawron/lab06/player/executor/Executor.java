package pl.edu.pwr.lgawron.lab06.player.executor;

public interface Executor {
    void start();

    void makeAction(String[] split);

    void tryToSleep(int time);

    void setExit(boolean exit);
}
