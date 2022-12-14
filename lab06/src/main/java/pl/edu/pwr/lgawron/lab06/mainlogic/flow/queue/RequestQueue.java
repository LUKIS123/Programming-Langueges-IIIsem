package pl.edu.pwr.lgawron.lab06.mainlogic.flow.queue;

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
        } else {
            queuedRequests.add(request);
        }
        // notify
        //notifyAll();
    }

    public synchronized PlayerRequest popLatest() {
        try {
            while (queuedRequests.isEmpty()) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return queuedRequests.pop();
    }

    public LinkedList<PlayerRequest> getQueuedRequests() {
        return queuedRequests;
    }
}
