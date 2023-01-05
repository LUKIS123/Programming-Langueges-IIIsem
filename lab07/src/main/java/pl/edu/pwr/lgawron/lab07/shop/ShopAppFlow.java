package pl.edu.pwr.lgawron.lab07.shop;

import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;
import pl.edu.pwr.lgawron.lab07.shop.flow.ShopController;

public class ShopAppFlow {
    private final ValuesHolder values;
    private final ShopController shopController;

    public ShopAppFlow(ValuesHolder values) {
        this.values = values;
        shopController = new ShopController(values);
    }

    public void init() {
        shopController.start();
    }

    public void killApp() {

    }

}
