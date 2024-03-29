package pl.edu.pwr.lgawron.lab07.shop.flow;

import interfaces.IStatusListener;
import pl.edu.pwr.lgawron.lab07.shop.repositories.IClientListenerRepository;

import java.util.HashMap;
import java.util.Map;

public class ListenerRepository implements IClientListenerRepository {
    private final Map<Integer, IStatusListener> statusListenerMap;

    public ListenerRepository() {
        this.statusListenerMap = new HashMap<>();
    }

    @Override
    public boolean addListener(IStatusListener statusListener, int clientId) {
        if (statusListenerMap.containsKey(clientId)) {
            return false;
        }
        statusListenerMap.put(clientId, statusListener);
        return true;
    }

    @Override
    public IStatusListener getListenerByClientId(int clientId) {
        return statusListenerMap.get(clientId);
    }

    @Override
    public boolean removeByClientId(int clientId) {
        if (!statusListenerMap.containsKey(clientId)) {
            return false;
        }
        statusListenerMap.remove(clientId);
        return true;
    }

}
