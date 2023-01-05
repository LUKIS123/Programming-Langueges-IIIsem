package pl.edu.pwr.lgawron.lab07.shop.flow;

import interfaces.IShop;
import interfaces.IStatusListener;
import model.*;
import pl.edu.pwr.lgawron.lab07.common.IOrderService;
import pl.edu.pwr.lgawron.lab07.common.IRepository;
import pl.edu.pwr.lgawron.lab07.common.modelsextended.ClientExtended;
import pl.edu.pwr.lgawron.lab07.common.modelsextended.ItemTypeExtended;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ShopImplementation implements IShop {
    // private final List<ItemType> items;
    private final IRepository<ClientExtended> clientRepository;
    private final IRepository<ItemTypeExtended> itemTypeRepository;
    private final IOrderService orderService;
    private int clientSequence;
    private int itemSequence;

    //    public ShopImplementation() {
    //        //this.items = new ArrayList<>();
    //    }
    public ShopImplementation(IRepository<ClientExtended> clientRepository, IRepository<ItemTypeExtended> itemTypeRepository, IOrderService orderService) {
        this.clientSequence = 1;
        this.itemSequence = 1;
        this.clientRepository = clientRepository;
        this.itemTypeRepository = itemTypeRepository;
        this.orderService = orderService;
    }

    @Override
    public int register(Client client) throws RemoteException {
        ClientExtended clientExtended = new ClientExtended(clientSequence, client);
        clientRepository.addInstance(clientExtended);
        clientSequence++;
        return clientExtended.getId();
    }

    @Override
    public List<ItemType> getItemList() throws RemoteException {
        List<ItemTypeExtended> repo = itemTypeRepository.getRepo();

        List<ItemType> itemList = new ArrayList<>();
        for (ItemTypeExtended itemTypeExtended : repo) {
            ItemType type = new ItemType();

            type.setPrice(itemTypeExtended.getPrice());
            type.setCategory(itemTypeExtended.getCategory());
            type.setName(itemTypeExtended.getName());
            itemList.add(type);
        }
        return itemList;
    }

    @Override
    public int placeOrder(Order order) throws RemoteException {
        SubmittedOrder submittedOrder = orderService.creteNewSubmittedOrderAndAddToRepo(order);
        return submittedOrder.getId();
    }

    @Override
    public List<SubmittedOrder> getSubmittedOrders() throws RemoteException {
        return orderService.getOrderRepository().getRepo();
    }

    @Override
    public boolean setStatus(int i, Status status) throws RemoteException {
        if (orderService.getSubmittedById(i) == null) {
            return false;
        } else {
            SubmittedOrder submittedById = orderService.getSubmittedById(i);
            submittedById.setStatus(status);
            return true;
        }
    }

    @Override
    public Status getStatus(int i) throws RemoteException {
        if (orderService.getSubmittedById(i) == null) {
            return null;
        } else {
            return orderService.getSubmittedById(i).getStatus();
        }
    }

    @Override
    public boolean subscribe(IStatusListener iStatusListener, int i) throws RemoteException {

        return false;
    }

    @Override
    public boolean unsubscribe(int i) throws RemoteException {
        return false;
    }

    // shop extended
    public int addItemType(ItemType itemType) throws RemoteException {
        ItemTypeExtended itemTypeExtended = new ItemTypeExtended(itemSequence, itemType.getName(), itemType.getPrice(), itemType.getCategory());
        itemTypeRepository.addInstance(itemTypeExtended);
        itemSequence++;
        return itemTypeExtended.getId();
    }

    public void addToList(ItemType itemType) {
        // items.add(itemType);
    }

}
