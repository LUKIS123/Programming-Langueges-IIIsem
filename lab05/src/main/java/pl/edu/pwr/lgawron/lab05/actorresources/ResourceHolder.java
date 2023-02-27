package pl.edu.pwr.lgawron.lab05.actorresources;

public interface ResourceHolder {
    void stockUpResource(int id) throws InterruptedException;

    void stockUpResource(int id, int amount) throws InterruptedException;

    void finishProcess(int id) throws InterruptedException;

    int getResource();
}
