package pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.queue;

import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.models.PlayerRequest;

public class QueueWorkerService {
    private boolean exit;
    private Thread thread;
    private final RequestQueue queue;

    public QueueWorkerService(RequestQueue requestQueue) {
        this.queue = requestQueue;
        this.exit = false;
    }

    public void handle() {
        thread = new Thread(() -> {


            while (!exit) {
//                if (queue.getQueuedRequests().isEmpty()) {
//                    try {
//                        wait();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }

                try {
                    PlayerRequest playerRequest = queue.popLatest();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                // tutaj jakies polaczenie z serwerem klienckim (senderem)
            }


        });
        thread.start();
    }


    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
