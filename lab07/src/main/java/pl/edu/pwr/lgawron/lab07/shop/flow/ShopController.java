package pl.edu.pwr.lgawron.lab07.shop.flow;

import interfaces.IShop;
import pl.edu.pwr.lgawron.lab07.shop.repositories.IClientListenerHolder;
import pl.edu.pwr.lgawron.lab07.shop.repositories.IOrderRepository;
import pl.edu.pwr.lgawron.lab07.shop.services.IOrderService;
import pl.edu.pwr.lgawron.lab07.shop.repositories.IRepository;
import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;
import pl.edu.pwr.lgawron.lab07.shop.modelsextended.ClientExtended;
import pl.edu.pwr.lgawron.lab07.shop.modelsextended.ItemTypeExtended;
import pl.edu.pwr.lgawron.lab07.shop.services.OrderService;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ShopController {
    private final ValuesHolder valuesHolder;
    private final ShopAppRenderer shopRenderer;
    private final IShop shop;
    private IShop shopRemote;

    public ShopController(ValuesHolder values, IRepository<ClientExtended> clientRepository, IRepository<ItemTypeExtended> itemTypeRepository, IOrderRepository clientOrdersRepository, ShopAppRenderer renderer) {
        this.valuesHolder = values;
        IClientListenerHolder clientListenerHolder = new ListenerHolder();
        IOrderService orderService = new OrderService(clientOrdersRepository);
        this.shopRenderer = renderer;
        this.shop = new ShopImplementation(clientRepository, itemTypeRepository, orderService, clientListenerHolder, renderer);
        shopRenderer.renderAllItems();
    }

    public void createRegistryAndExportIShop() {
        try {
            Registry registry = LocateRegistry.createRegistry(valuesHolder.getPort());
            shopRemote = (IShop) UnicastRemoteObject.exportObject(shop, 0);
            registry.rebind("shopRemote", shopRemote);
            shopRenderer.renderBasicInfo(valuesHolder);
        } catch (RemoteException e) {
            System.out.println("SHOP_ERROR:" + e);
            e.printStackTrace();
        }
    }

    public void removeShopFromRegistry() {
        try {
            UnicastRemoteObject.unexportObject(shopRemote, false);
        } catch (NoSuchObjectException ignored) {
        }
    }

}