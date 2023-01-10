package pl.edu.pwr.lgawron.lab07.shop.repositories;

import interfaces.IStatusListener;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface IClientListenerHolder extends Remote {
    boolean addListener(IStatusListener statusListener, int clientId) throws RemoteException;

    IStatusListener getListenerByClientId(int clientId) throws RemoteException;

    public boolean removeByClientId(int clientId) throws RemoteException;

    Map<Integer, IStatusListener> getStatusListenerMap() throws RemoteException;

}
