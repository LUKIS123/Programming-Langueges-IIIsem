package pl.edu.pwr.lgawron.lab07.client;

import interfaces.IShop;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.*;
import pl.edu.pwr.lgawron.lab07.client.flow.ClientAppRenderer;
import pl.edu.pwr.lgawron.lab07.client.flow.ShoppingCartService;
import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
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
    private final List<SubmittedOrder> submittedOrders;
    private final ShoppingCartService shoppingCartService;

    public AppFlow(ValuesHolder values, Label notificationLabel, Button notificationButton, Label cartLabel, Button cartButton, VBox infoBox, VBox itemBox, VBox orderBox) {
        this.values = values;
        this.notificationLabel = notificationLabel;
        this.notificationButton = notificationButton;
        this.cartLabel = cartLabel;
        this.cartButton = cartButton;
        this.infoBox = infoBox;
        this.itemBox = itemBox;
        this.orderBox = orderBox;

        this.clientId = -1;
        this.submittedOrders = new ArrayList<>();
        this.clientAppRenderer = new ClientAppRenderer(this, notificationLabel, notificationButton, cartLabel, cartButton, infoBox, itemBox, orderBox);
        this.shoppingCartService = new ShoppingCartService(this, clientAppRenderer);
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
            throw new RuntimeException(e);
        }
    }

    public void register(String name) throws RemoteException {
        Client me = new Client();
        me.setName(name);
        clientId = shop.register(me);
        clientAppRenderer.renderAfterRegistration(clientId);
        this.downloadSubmittedOrders();
    }

    public void addToCart(ItemType itemType, int quantity, String text) {
        shoppingCartService.addOrderLine(itemType, quantity, clientId, text);
        clientAppRenderer.renderShoppingCartPopUp(shoppingCartService.getOrderBuilder());
    }

    public void submitOrderToShop() throws RemoteException {
        Order order = shoppingCartService.buildOrder();
        int orderId = shop.placeOrder(order);
        shoppingCartService.putToHistory(orderId, order);
        shoppingCartService.getOrderBuilder().getOrderLineList().clear();
        clientAppRenderer.renderShoppingCartPopUp(shoppingCartService.getOrderBuilder());
        clientAppRenderer.renderCartTotalCost(0f);
        this.downloadSubmittedOrders();
    }

    public void downloadSubmittedOrders() throws RemoteException {
        List<SubmittedOrder> submitted = shop
                .getSubmittedOrders()
                .stream()
                .filter(submittedOrder -> submittedOrder.getOrder().getClientID() == clientId).
                toList();
        if (submitted.isEmpty()) {
            return;
        }
        submittedOrders.clear();
        submittedOrders.addAll(submitted);
        clientAppRenderer.renderOrders(submittedOrders);
    }

    public boolean enrollDelivery(int orderId) throws RemoteException {
        return shop.setStatus(orderId, Status.DELIVERED);
    }

    public ShoppingCartService getShoppingCartService() {
        return shoppingCartService;
    }

    public int getClientId() {
        return clientId;
    }

    public List<SubmittedOrder> getSubmittedOrders() {
        return submittedOrders;
    }

    public void killApp() {
        System.exit(0);
    }

}
