package pl.edu.pwr.lgawron.lab07.client;

import interfaces.IShop;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.edu.pwr.lgawron.lab07.client.flow.ClientAppRenderer;
import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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

    public AppFlow(ValuesHolder values, Label notificationLabel, Button notificationButton, Label cartLabel, Button cartButton, VBox infoBox, VBox itemBox, VBox orderBox) {
        this.values = values;
        this.notificationLabel = notificationLabel;
        this.notificationButton = notificationButton;
        this.cartLabel = cartLabel;
        this.cartButton = cartButton;
        this.infoBox = infoBox;
        this.itemBox = itemBox;
        this.orderBox = orderBox;

        this.clientAppRenderer = new ClientAppRenderer(notificationLabel, notificationButton, cartLabel, cartButton, infoBox, itemBox, orderBox);
    }

    public void initialize() throws RemoteException, NotBoundException {
        Registry localhost = LocateRegistry.getRegistry(values.getServer(), values.getPort());
        shop = (IShop) localhost.lookup("shopRemote");
        clientAppRenderer.renderInfoAfterConnection(values.getServer(), values.getPort());
    }

}
