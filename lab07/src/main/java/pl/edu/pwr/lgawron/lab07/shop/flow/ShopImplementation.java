package pl.edu.pwr.lgawron.lab07.shop.flow;

import interfaces.IShop;
import interfaces.IStatusListener;
import model.*;
import pl.edu.pwr.lgawron.lab07.shop.repositories.IClientListenerRepository;
import pl.edu.pwr.lgawron.lab07.shop.services.IOrderService;
import pl.edu.pwr.lgawron.lab07.shop.repositories.IRepository;
import pl.edu.pwr.lgawron.lab07.shop.modelsextended.ClientExtended;
import pl.edu.pwr.lgawron.lab07.shop.modelsextended.ItemTypeExtended;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ShopImplementation implements IShop, Serializable {
    private final IRepository<ClientExtended> clientRepository;
    private final IRepository<ItemTypeExtended> itemTypeRepository;
    private final IOrderService orderService;
    private final IClientListenerRepository clientListenerHolder;
    private final ShopAppRenderer shopRenderer;
    private int clientSequence;

    public ShopImplementation(IRepository<ClientExtended> clientRepository, IRepository<ItemTypeExtended> itemTypeRepository,
                              IOrderService orderService, IClientListenerRepository clientListenerHolder,
                              ShopAppRenderer shopRenderer) {

        this.clientSequence = 1;
        this.clientRepository = clientRepository;
        this.itemTypeRepository = itemTypeRepository;
        this.orderService = orderService;
        this.clientListenerHolder = clientListenerHolder;
        this.shopRenderer = shopRenderer;
    }

    @Override
    public int register(Client client) throws RemoteException {
        Optional<ClientExtended> first = clientRepository.getRepositoryData().stream().filter(clientExtended -> Objects.equals(clientExtended.getName(), client.getName())).findFirst();
        if (first.isEmpty()) {
            ClientExtended clientExtended = new ClientExtended(clientSequence, client);
            clientRepository.addInstance(clientExtended);
            clientSequence++;
            shopRenderer.renderClientRegistered(clientExtended.getId());
            return clientExtended.getId();
        }
        return first.get().getId();
    }

    @Override
    public List<ItemType> getItemList() throws RemoteException {
        List<ItemTypeExtended> repo = itemTypeRepository.getRepositoryData();

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
        if (order.getOll().isEmpty()) {
            return -1;
        }
        SubmittedOrder submittedOrder = orderService.creteNewSubmittedOrderAndAddToRepo(order);
        shopRenderer.renderOrderAdded(submittedOrder.getId());
        return submittedOrder.getId();
    }

    @Override
    public List<SubmittedOrder> getSubmittedOrders() throws RemoteException {
        return orderService.getOrderRepository().getRepositoryData();
    }

    @Override
    public boolean setStatus(int orderId, Status status) throws RemoteException {
        if (orderService.getSubmittedById(orderId) == null) {
            return false;
        }
        SubmittedOrder submittedById = orderService.getSubmittedById(orderId);
        submittedById.setStatus(status);
        shopRenderer.renderOrderStatusChanged(orderId);

        IStatusListener listenerByClientId = clientListenerHolder.getListenerByClientId(submittedById.getOrder().getClientID());
        if (listenerByClientId != null) {
            listenerByClientId.statusChanged(orderId, status);
        }

        return true;
    }

    @Override
    public Status getStatus(int orderId) throws RemoteException {
        if (orderService.getSubmittedById(orderId) == null) {
            return null;
        }
        return orderService.getSubmittedById(orderId).getStatus();
    }

    @Override
    public boolean subscribe(IStatusListener iStatusListener, int clientId) throws RemoteException {
        if (clientRepository.getById(clientId).getId() == -1) {
            return false;
        }
        return clientListenerHolder.addListener(iStatusListener, clientId);
    }

    @Override
    public boolean unsubscribe(int clientId) throws RemoteException {
        return clientListenerHolder.removeByClientId(clientId);
    }

}
