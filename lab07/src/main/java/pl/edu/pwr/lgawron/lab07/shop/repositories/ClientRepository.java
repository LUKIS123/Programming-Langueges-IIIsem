package pl.edu.pwr.lgawron.lab07.shop.repositories;

import pl.edu.pwr.lgawron.lab07.common.modelsextended.ClientExtended;
import pl.edu.pwr.lgawron.lab07.common.IRepository;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements IRepository<ClientExtended> {
    private final List<ClientExtended> clientList;

    public ClientRepository() {
        this.clientList = new ArrayList<>();
    }

    @Override
    public List<ClientExtended> getRepo() throws RemoteException {
        return clientList;
    }

    @Override
    public void addInstance(ClientExtended clientExtended) throws RemoteException {
        clientList.add(clientExtended);
    }

    @Override
    public ClientExtended getById(int id) throws RemoteException {
        Optional<ClientExtended> first = clientList
                .stream()
                .filter(clientExtended -> clientExtended.getId() == id)
                .findFirst();
        // id 0 -> a client does not exist
        return first.orElseGet(() -> new ClientExtended(-1));
    }

}
