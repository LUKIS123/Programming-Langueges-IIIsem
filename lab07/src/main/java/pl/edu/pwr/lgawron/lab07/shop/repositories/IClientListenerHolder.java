package pl.edu.pwr.lgawron.lab07.shop.repositories;

import interfaces.IStatusListener;

public interface IClientListenerHolder {
    boolean addListener(IStatusListener statusListener, int clientId);

    IStatusListener getListenerByClientId(int clientId);

    boolean removeByClientId(int clientId);

}
