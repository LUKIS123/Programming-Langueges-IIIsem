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
}
