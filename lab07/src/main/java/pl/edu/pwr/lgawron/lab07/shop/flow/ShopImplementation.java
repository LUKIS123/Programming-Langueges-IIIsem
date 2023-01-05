package pl.edu.pwr.lgawron.lab07.shop.flow;

import interfaces.IShop;
import interfaces.IStatusListener;
import model.*;
import pl.edu.pwr.lgawron.lab07.common.IOrderService;
import pl.edu.pwr.lgawron.lab07.common.IRepository;
import pl.edu.pwr.lgawron.lab07.common.modelsextended.ClientExtended;
import pl.edu.pwr.lgawron.lab07.common.modelsextended.ItemTypeExtended;

import java.rmi.RemoteException;
import java.util.List;

public class ShopImplementation implements IShop {
    // private final List<ItemType> items;
    private final IRepository<ClientExtended> clientRepository;
    private final IRepository<ItemTypeExtended> itemTypeRepository;
    private final IOrderService orderService;
    private final int sequence;

//    public ShopImplementation() {
//        //this.items = new ArrayList<>();
//    }

    public ShopImplementation(IRepository<ClientExtended> clientRepository, IRepository<ItemTypeExtended> itemTypeRepository, IOrderService orderService) {
        this.sequence = 1;
        this.clientRepository = clientRepository;
        this.itemTypeRepository = itemTypeRepository;
        this.orderService = orderService;
    }

    @Override
    public int register(Client client) throws RemoteException {
        ClientExtended clientExtended = new ClientExtended(sequence, client);
        clientRepository.addInstance(clientExtended);
        return clientExtended.getId();
    }

    @Override
    public List<ItemType> getItemList() throws RemoteException {
        //return items;
        return null;
    }

    @Override
    public int placeOrder(Order order) throws RemoteException {
        return 0;
    }

    @Override
    public List<SubmittedOrder> getSubmittedOrders() throws RemoteException {
        return null;
    }

    @Override
    public boolean setStatus(int i, Status status) throws RemoteException {
        return false;
    }

    @Override
    public Status getStatus(int i) throws RemoteException {
        return null;
    }

    @Override
    public boolean subscribe(IStatusListener iStatusListener, int i) throws RemoteException {
        return false;
    }

    @Override
    public boolean unsubscribe(int i) throws RemoteException {
        return false;
    }

    // added
    public void addToList(ItemType itemType) {
        // items.add(itemType);
    }

}
