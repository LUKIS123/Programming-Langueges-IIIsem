package pl.edu.pwr.lgawron.lab06.administrator.queue;

import pl.edu.pwr.lgawron.lab06.administrator.adminsocket.models.PlayerRequest;

import java.util.LinkedList;

public class RequestQueue {
    private final LinkedList<PlayerRequest> queuedRequests;
    private static final Object lock = new Object();

    public RequestQueue() {
        this.queuedRequests = new LinkedList<>();
    }

    public void addElement(PlayerRequest request) {

        synchronized (lock) {
            if (queuedRequests.isEmpty()) {
                queuedRequests.add(request);
                lock.notifyAll();
            } else {
                queuedRequests.add(request);
            }
        }

    }

    public PlayerRequest popLatest() {

        synchronized (lock) {
            try {
                while (queuedRequests.isEmpty()) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return queuedRequests.pop();
        }

    }

    public LinkedList<PlayerRequest> getQueuedRequests() {
        synchronized (lock) {
            return queuedRequests;
        }
    }

}
