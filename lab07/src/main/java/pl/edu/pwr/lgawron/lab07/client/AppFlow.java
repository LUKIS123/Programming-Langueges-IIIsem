package pl.edu.pwr.lgawron.lab07.client;

import interfaces.IShop;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Client;
import model.ItemType;
import pl.edu.pwr.lgawron.lab07.client.flow.ClientAppRenderer;
import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class AppFlow {
    private final Label notificationLabel;
    private final Button notificationButton;
    private final Label cartLabel;
    private final Button cartButton;
    private final VBox infoBox;
    private final VBox itemBox;
    private final VBox orderBox;
    private final ValuesHolder values;
    private IShop shop;
    private final ClientAppRenderer clientAppRenderer;
    private int clientId;
    private List<ItemType> itemList;

    public AppFlow(ValuesHolder values, Label notificationLabel, Button notificationButton, Label cartLabel, Button cartButton, VBox infoBox, VBox itemBox, VBox orderBox) {
        this.values = values;
        this.notificationLabel = notificationLabel;
        this.notificationButton = notificationButton;
        this.cartLabel = cartLabel;
        this.cartButton = cartButton;
        this.infoBox = infoBox;
        this.itemBox = itemBox;
        this.orderBox = orderBox;

        this.clientAppRenderer = new ClientAppRenderer(this, notificationLabel, notificationButton, cartLabel, cartButton, infoBox, itemBox, orderBox);
    }

    public void initialize() throws RemoteException, NotBoundException {
        Registry localhost = LocateRegistry.getRegistry(values.getServer(), values.getPort());
        shop = (IShop) localhost.lookup("shopRemote");
        clientAppRenderer.renderInfoAfterConnection(values.getServer(), values.getPort());
        this.downloadItemOfferAndRender();
    }

    public void downloadItemOfferAndRender() {
        try {
            itemList = shop.getItemList();
            clientAppRenderer.renderItems(itemList);
        } catch (RemoteException e) {
            // todo obsluga wyjatku
            throw new RuntimeException(e);
        }
    }

    public void register(String name) throws RemoteException {
        Client me = new Client();
        me.setName(name);
        clientId = shop.register(me);
        clientAppRenderer.renderAfterRegistration(clientId);
    }

}
