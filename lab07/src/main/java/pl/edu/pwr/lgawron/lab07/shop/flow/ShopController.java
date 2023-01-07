package pl.edu.pwr.lgawron.lab07.shop.flow;

import interfaces.IShop;
import pl.edu.pwr.lgawron.lab07.common.IClientListenerHolder;
import pl.edu.pwr.lgawron.lab07.common.IOrderRepository;
import pl.edu.pwr.lgawron.lab07.common.IOrderService;
import pl.edu.pwr.lgawron.lab07.common.IRepository;
import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;
import pl.edu.pwr.lgawron.lab07.common.modelsextended.ClientExtended;
import pl.edu.pwr.lgawron.lab07.common.modelsextended.ItemTypeExtended;
import pl.edu.pwr.lgawron.lab07.common.listener.ListenerHolder;
import pl.edu.pwr.lgawron.lab07.shop.services.OrderService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

// import java.awt.EventQueue;
public class ShopController {
    private final ValuesHolder valuesHolder;
    private final IRepository<ClientExtended> clientRepository;
    private final IRepository<ItemTypeExtended> itemTypeRepository;
    private final IOrderRepository clientOrdersRepository;
    private final IOrderService orderService;
    private final IClientListenerHolder clientListenerHolder;
    private final ShopAppRenderer shopRenderer;
    private final IShop shop;
    private IShop shopRemote;

    public ShopController(ValuesHolder values, IRepository<ClientExtended> clientRepository, IRepository<ItemTypeExtended> itemTypeRepository, IOrderRepository clientOrdersRepository, ShopAppRenderer renderer) {
        this.valuesHolder = values;
        this.clientRepository = clientRepository;
        this.itemTypeRepository = itemTypeRepository;
        this.clientOrdersRepository = clientOrdersRepository;
        this.clientListenerHolder = new ListenerHolder();
        this.orderService = new OrderService(clientOrdersRepository);
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
            //this.test();
        } catch (RemoteException e) {
            System.out.println("SHOP_ERROR:" + e);
            e.printStackTrace();
        }
    }

    public void test() {
        Thread t = new Thread(() -> {
            try {
                while (true) {
                    if (clientRepository.getRepo().isEmpty()) {
                        System.out.println("Empty");
                    }
                    clientRepository.getRepo().forEach(cl -> System.out.println(cl.getName()));

                    System.out.println(itemTypeRepository.getRepo().size());

                    Thread.sleep(4000);
                }
            } catch (InterruptedException | RemoteException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
    }

}

// test
// zmienic w kontrolerze, nie potrzebuje proxy! lab06 admin tez?
//        try {
//            Registry registry = LocateRegistry.createRegistry(8085);
//
//
//            ShopImplementation shop = new ShopImplementation();
//            ItemType itemType = new ItemType();
//            itemType.setName("test123");
//            itemType.setCategory(1);
//            itemType.setPrice(2.0F);
//            shop.addToList(itemType);
//
//            IShop s = (IShop) UnicastRemoteObject.exportObject(shop, 0);
//            registry.rebind("t123", s);
//            System.out.println("Success!");
//        } catch (RemoteException e) {
//            System.out.println("SHOP ERROR: " + e);
//            e.printStackTrace();
//        }