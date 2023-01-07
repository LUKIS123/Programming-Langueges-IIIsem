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
        runnable.run();
    }

}
