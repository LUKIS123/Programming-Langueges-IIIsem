package pl.edu.pwr.lgawron.lab07.shop;

import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;
import pl.edu.pwr.lgawron.lab07.common.modelsextended.ItemTypeExtended;
import pl.edu.pwr.lgawron.lab07.common.utils.DataFileUtility;
import pl.edu.pwr.lgawron.lab07.common.utils.SerializeUtility;
import pl.edu.pwr.lgawron.lab07.shop.flow.ShopController;

import java.util.List;

public class ShopAppFlow {
    private final ValuesHolder values;
    private final ShopController shopController;

    public ShopAppFlow(ValuesHolder values) {
        this.values = values;
        shopController = new ShopController(values, this.readItemDataFile());
    }

    public List<ItemTypeExtended> readItemDataFile() {
        String s = DataFileUtility.readFile("Items.txt");
        return SerializeUtility.deserializeItems(s);
    }

    public void init() {
        shopController.start();
    }

    public void killApp() {

    }

}
