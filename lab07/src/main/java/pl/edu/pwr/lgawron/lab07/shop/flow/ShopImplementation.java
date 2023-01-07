package pl.edu.pwr.lgawron.lab07.shop.flow;

import interfaces.IShop;
import interfaces.IStatusListener;
import model.*;
import pl.edu.pwr.lgawron.lab07.common.IClientListenerHolder;
import pl.edu.pwr.lgawron.lab07.common.IOrderService;
import pl.edu.pwr.lgawron.lab07.common.IRepository;
import pl.edu.pwr.lgawron.lab07.common.modelsextended.ClientExtended;
import pl.edu.pwr.lgawron.lab07.common.modelsextended.ItemTypeExtended;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ShopImplementation implements IShop, Serializable {
    private final IRepository<ClientExtended> clientRepository;
    private final IRepository<ItemTypeExtended> itemTypeRepository;
    private final IOrderService orderService;
    private final IClientListenerHolder clientListenerHolder;
    private final ShopAppRenderer shopRenderer;
    private int clientSequence;
    private int itemSequence;

    public ShopImplementation(IRepository<ClientExtended> clientRepository, IRepository<ItemTypeExtended> itemTypeRepository, IOrderService orderService, IClientListenerHolder clientListenerHolder, ShopAppRenderer shopRenderer) {
        this.clientSequence = 1;
        this.itemSequence = 1;
        this.clientRepository = clientRepository;
        this.itemTypeRepository = itemTypeRepository;
        this.orderService = orderService;
        this.clientListenerHolder = clientListenerHolder;
        this.shopRenderer = shopRenderer;
    }

    @Override
    public int register(Client client) throws RemoteException {
        ClientExtended clientExtended = new ClientExtended(clientSequence, client);
        clientRepository.addInstance(clientExtended);
        clientSequence++;

        // todo: moze uzyc do tego eventqueue -> inny watek bedzie renderowal?
        shopRenderer.renderClientRegistered(clientExtended.getId());
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

        shopRenderer.renderOrderAdded(submittedOrder.getId());
        return submittedOrder.getId();
    }

    @Override
    public List<SubmittedOrder> getSubmittedOrders() throws RemoteException {
        return orderService.getOrderRepository().getRepo();
    }

    @Override
    public boolean setStatus(int orderId, Status status) throws RemoteException {
        if (orderService.getSubmittedById(orderId) == null) {
            return false;
        } else {
            SubmittedOrder submittedById = orderService.getSubmittedById(orderId);
            submittedById.setStatus(status);
            return true;
        }
    }

    @Override
    public Status getStatus(int orderId) throws RemoteException {
        if (orderService.getSubmittedById(orderId) == null) {
            return null;
        } else {
            return orderService.getSubmittedById(orderId).getStatus();
        }
    }

    @Override
    public boolean subscribe(IStatusListener iStatusListener, int clientId) throws RemoteException {
        return clientListenerHolder.addListener(iStatusListener, clientId);
    }

    @Override
    public boolean unsubscribe(int clientId) throws RemoteException {
        return clientListenerHolder.removeByClientId(clientId);
    }

    // utils
    public int addItemType(ItemType itemType) throws RemoteException {
        ItemTypeExtended itemTypeExtended = new ItemTypeExtended(itemSequence, itemType.getName(), itemType.getPrice(), itemType.getCategory());
        itemTypeRepository.addInstance(itemTypeExtended);
        itemSequence++;
        return itemTypeExtended.getId();
    }

}
