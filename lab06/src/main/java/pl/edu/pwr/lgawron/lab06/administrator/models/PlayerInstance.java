package pl.edu.pwr.lgawron.lab06.administrator.models;

import pl.edu.pwr.lgawron.lab06.administrator.adminsocket.AdminReceiverSocket;
import pl.edu.pwr.lgawron.lab06.common.sockets.SenderSocket;
import pl.edu.pwr.lgawron.lab06.common.game.geometry.Point2D;
import pl.edu.pwr.lgawron.lab06.administrator.queue.RequestQueue;

public class PlayerInstance {
    private final int id;
    private final int clientServerPort;
    private final String proxy;
    private Point2D position;
    private final AdminReceiverSocket receiverSocket;
    private final SenderSocket senderSocket;
    private int howManyTreasuresPicked;
    private boolean takeAttempt;
    private int currentWaitingTime;

    public PlayerInstance(int id, int clientServerPort, String proxy, RequestQueue requestQueue) {
        this.id = id;
        this.clientServerPort = clientServerPort;
        this.proxy = proxy;
        this.receiverSocket = new AdminReceiverSocket(0, requestQueue);
        this.senderSocket = new SenderSocket();
        this.howManyTreasuresPicked = 0;
        this.takeAttempt = false;
        this.currentWaitingTime = 0;
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

    public boolean isBound() {
        return receiverSocket.checkIfConnected();
    }

    public String getProxy() {
        return proxy;
    }

    public SenderSocket getSenderSocket() {
        return senderSocket;
    }

    public int getHowManyTreasuresPicked() {
        return howManyTreasuresPicked;
    }

    public void setHowManyTreasuresPicked(int howManyTreasuresPicked) {
        this.howManyTreasuresPicked = howManyTreasuresPicked;
    }

    public boolean isTakeAttempt() {
        return takeAttempt;
    }

    public void setTakeAttempt(boolean takeAttempt) {
        this.takeAttempt = takeAttempt;
    }

    public int getCurrentWaitingTime() {
        return currentWaitingTime;
    }

    public void setCurrentWaitingTime(int currentWaitingTime) {
        this.currentWaitingTime = currentWaitingTime;
    }
}
