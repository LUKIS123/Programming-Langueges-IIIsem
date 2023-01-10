package pl.edu.pwr.lgawron.lab07.shop;

import javafx.scene.layout.VBox;
import pl.edu.pwr.lgawron.lab07.shop.repositories.IOrderRepository;
import pl.edu.pwr.lgawron.lab07.shop.repositories.IRepository;
import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;
import pl.edu.pwr.lgawron.lab07.shop.modelsextended.ClientExtended;
import pl.edu.pwr.lgawron.lab07.shop.modelsextended.ItemTypeExtended;
import pl.edu.pwr.lgawron.lab07.common.utils.DataFileUtility;
import pl.edu.pwr.lgawron.lab07.common.utils.SerializeUtility;
import pl.edu.pwr.lgawron.lab07.shop.flow.ShopController;
import pl.edu.pwr.lgawron.lab07.shop.flow.ShopAppRenderer;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ClientOrdersRepository;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ClientRepository;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ItemTypeRepository;

import java.util.List;

public class AppFlow {
    private final ValuesHolder values;
    private final IRepository<ClientExtended> clientRepository;
    private final IRepository<ItemTypeExtended> itemTypeRepository;
    private final IOrderRepository clientOrdersRepository;
    private final ShopAppRenderer renderer;
    private final ShopController shopController;

    public AppFlow(ValuesHolder values, VBox itemBox, VBox clientBox, VBox orderBox, VBox infoBox) {
        this.values = values;
        this.clientRepository = new ClientRepository();
        this.itemTypeRepository = new ItemTypeRepository(this.readItemDataFile());
        this.clientOrdersRepository = new ClientOrdersRepository();
        this.renderer = new ShopAppRenderer(clientRepository, itemTypeRepository, clientOrdersRepository, itemBox, clientBox, orderBox, infoBox);
        this.shopController = new ShopController(values, clientRepository, itemTypeRepository, clientOrdersRepository, renderer);
    }

    private List<ItemTypeExtended> readItemDataFile() {
        String s = DataFileUtility.readFile("Items.txt");
        return SerializeUtility.deserializeItems(s);
    }

    public void initialize() {
        shopController.createRegistryAndExportIShop();
    }

    public void killApp() {
        shopController.removeShopFromRegistry();
        System.exit(0);
    }

}
