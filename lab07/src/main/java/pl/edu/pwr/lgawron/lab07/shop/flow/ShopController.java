package pl.edu.pwr.lgawron.lab07.shop.flow;

import interfaces.IShop;
import pl.edu.pwr.lgawron.lab07.common.IOrderRepository;
import pl.edu.pwr.lgawron.lab07.common.IOrderService;
import pl.edu.pwr.lgawron.lab07.common.IRepository;
import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;
import pl.edu.pwr.lgawron.lab07.common.modelsextended.ClientExtended;
import pl.edu.pwr.lgawron.lab07.common.modelsextended.ItemTypeExtended;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ClientOrdersRepository;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ClientRepository;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ItemTypeRepository;
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
    private final IShop shop;

    public ShopController(ValuesHolder valuesHolder) {
        this.valuesHolder = valuesHolder;

        this.clientRepository = new ClientRepository();
        this.itemTypeRepository = new ItemTypeRepository();
        this.clientOrdersRepository = new ClientOrdersRepository();
        this.orderService = new OrderService(clientOrdersRepository);
        this.shop = new ShopImplementation(clientRepository, itemTypeRepository, orderService);
    }

    public void start() {
        try {
            Registry registry = LocateRegistry.createRegistry(valuesHolder.getPort());
            IShop shopRemote = (IShop) UnicastRemoteObject.exportObject(shop, 0);
            registry.rebind("shopRemote", shopRemote);

            this.test();
        } catch (RemoteException e) {
            System.out.println("SHOP_ERROR:" + e);
            e.printStackTrace();
        }
    }

    public void test() {
        Thread t = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(4000);
                    if (clientRepository.getRepo().isEmpty()) {
                        System.out.println("Empty");
                    }
                    clientRepository.getRepo().forEach(cl -> System.out.println(cl.getName()));

                    // System.out.println(itemTypeRepository.getRepo().isEmpty());
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