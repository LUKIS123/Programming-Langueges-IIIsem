package pl.edu.pwr.lgawron.lab07.shop.flow;

import interfaces.IStatusListener;
import model.Status;

import java.rmi.RemoteException;

public class StatusListenerImplementation implements IStatusListener {
    @Override
    public void statusChanged(int i, Status status) throws RemoteException {

    }
}
