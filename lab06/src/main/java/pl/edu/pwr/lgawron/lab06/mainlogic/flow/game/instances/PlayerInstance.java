package pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.instances;

import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.AdminReceiverSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.adminsocket.AdminSenderSocket;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.game.geometry.Point2D;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.queue.RequestQueue;

public class PlayerInstance {
    private final int id;
    private final int clientServerPort;
    private Point2D position;
    private final RequestQueue requestQueue;
    private final AdminReceiverSocket receiverSocket;
    private final AdminSenderSocket senderSocket;

    public PlayerInstance(int id, int clientServerPort, RequestQueue requestQueue, AdminReceiverSocket adminReceiverSocket) {
        this.id = id;
        this.clientServerPort = clientServerPort;
        this.requestQueue = requestQueue;
        this.receiverSocket = adminReceiverSocket;
//        this.receiverSocket = new AdminReceiverSocket(0, requestQueue);
//        this.receiverSocket.start();
        this.senderSocket = new AdminSenderSocket();
    }

    public Point2D getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public int getClientServerPort() {
        return clientServerPort;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setX(int x) {
        this.position.setPositionX(x);
    }

    public void setY(int y) {
        this.position.setPositionY(y);
    }

    public AdminReceiverSocket getReceiverSocket() {
        return receiverSocket;
    }

    public int getReceiverPort() {
        return receiverSocket.getServerPort();
    }

    public AdminSenderSocket getSenderSocket() {
        return senderSocket;
    }
}
