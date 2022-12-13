package pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.queue;

import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.models.PlayerRequest;

import java.util.LinkedList;

public class RequestQueue {
    private final LinkedList<PlayerRequest> queuedRequests;

    public RequestQueue() {
        this.queuedRequests = new LinkedList<>();
    }

    public synchronized void addElement(PlayerRequest request) {
        if (queuedRequests.isEmpty()) {
            queuedRequests.add(request);
            notifyAll();
        }
        queuedRequests.add(request);

        // notify
        //notifyAll();
    }

    public synchronized PlayerRequest popLatest() throws InterruptedException {
        while (queuedRequests.isEmpty()) {
            wait();
        }

        return queuedRequests.pop();
    }

    public LinkedList<PlayerRequest> getQueuedRequests() {
        return queuedRequests;
    }
}
