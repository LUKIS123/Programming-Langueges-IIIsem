package pl.edu.pwr.lgawron.lab07.shop;

import javafx.scene.layout.VBox;
import pl.edu.pwr.lgawron.lab07.shop.repositories.IOrderRepository;
import pl.edu.pwr.lgawron.lab07.shop.repositories.IRepository;
import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;
import pl.edu.pwr.lgawron.lab07.shop.modelsextended.ClientExtended;
import pl.edu.pwr.lgawron.lab07.shop.modelsextended.ItemTypeExtended;
import pl.edu.pwr.lgawron.lab07.common.utils.DataFileUtility;
import pl.edu.pwr.lgawron.lab07.common.utils.SerializeUtility;
import pl.edu.pwr.lgawron.lab07.shop.flow.ShopRemoteService;
import pl.edu.pwr.lgawron.lab07.shop.flow.ShopAppRenderer;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ClientOrdersRepository;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ClientRepository;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ItemTypeRepository;

import java.util.List;

public class ShopAppFlow {
    private final ShopRemoteService shopRemoteService;

    public ShopAppFlow(ValuesHolder values, VBox itemBox, VBox clientBox, VBox orderBox, VBox infoBox) {
        IRepository<ClientExtended> clientRepository = new ClientRepository();
        IRepository<ItemTypeExtended> itemTypeRepository = new ItemTypeRepository(this.readItemDataFile());
        IOrderRepository clientOrdersRepository = new ClientOrdersRepository();
        ShopAppRenderer renderer = new ShopAppRenderer(clientRepository, itemTypeRepository, clientOrdersRepository, itemBox, clientBox, orderBox, infoBox);
        this.shopRemoteService = new ShopRemoteService(values, clientRepository, itemTypeRepository, clientOrdersRepository, renderer);
    }

    private List<ItemTypeExtended> readItemDataFile() {
        String s = DataFileUtility.readFile("Items.txt");
        return SerializeUtility.deserializeItems(s);
    }

    public void initialize() {
        shopRemoteService.createRegistryAndExportIShop();
    }

    public void killApp() {
        shopRemoteService.removeShopFromRegistry();
        System.exit(0);
    }

}
