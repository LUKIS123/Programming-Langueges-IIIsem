package pl.edu.pwr.lgawron.lab07.common.listener;

import interfaces.IStatusListener;
import model.Status;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.function.BiConsumer;

public class StatusListenerImplementation implements IStatusListener, Serializable {
    private final BiConsumer<Integer, Status> consumer;

    public StatusListenerImplementation(BiConsumer<Integer, Status> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void statusChanged(int orderId, Status status) throws RemoteException {
        consumer.accept(orderId, status);
    }

}
