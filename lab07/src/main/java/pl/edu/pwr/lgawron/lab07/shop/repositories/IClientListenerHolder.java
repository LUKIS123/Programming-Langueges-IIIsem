package pl.edu.pwr.lgawron.lab07.shop.repositories;

import interfaces.IStatusListener;

import java.util.Map;

public interface IClientListenerHolder {
    boolean addListener(IStatusListener statusListener, int clientId);

    IStatusListener getListenerByClientId(int clientId);

    public boolean removeByClientId(int clientId);

    Map<Integer, IStatusListener> getStatusListenerMap();

}
