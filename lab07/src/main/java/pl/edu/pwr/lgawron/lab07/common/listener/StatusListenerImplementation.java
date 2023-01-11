package pl.edu.pwr.lgawron.lab07.common.listener;

import interfaces.IStatusListener;
import model.Status;

import java.io.Serializable;
import java.rmi.RemoteException;

public class StatusListenerImplementation implements IStatusListener, Serializable {
    private final Runnable runnable;

    public StatusListenerImplementation(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void statusChanged(int orderId, Status status) throws RemoteException {
        // todo: wymyslic cos aby odswiezalo po jednym orderId
        // moze zrobic Runnable ale przekazywac tam myRunnable i wtecy uzyc if(instanceof myRunnable) jesli nie to zwykly
        runnable.run();
    }

}
