package pl.edu.pwr.lgawron.lab07.client;

import interfaces.IShop;
import interfaces.IStatusListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.*;
import pl.edu.pwr.lgawron.lab07.client.flow.ClientAppRenderer;
import pl.edu.pwr.lgawron.lab07.client.flow.ShoppingCartService;
import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;
import pl.edu.pwr.lgawron.lab07.common.listener.StatusListenerImplementation;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

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
    private Map<Integer, Status> integerStatusMap;
    private final List<SubmittedOrder> submittedOrders;
    private final ShoppingCartService shoppingCartService;
    private StatusListenerImplementation statusListenerImplementation;
    private IStatusListener listenerRemote;

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
        this.integerStatusMap = new HashMap<>();
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
        this.downloadSubmittedOrdersAndRefresh();
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
        this.downloadSubmittedOrdersAndRefresh();
    }

    public void downloadSubmittedOrdersAndRefresh() throws RemoteException {
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

    public boolean subscribe() throws RemoteException {
        statusListenerImplementation = new StatusListenerImplementation((orderId, status) -> {
            try {
                Optional<SubmittedOrder> first = shop
                        .getSubmittedOrders()
                        .stream()
                        .filter(submittedOrder -> submittedOrder.getId() == orderId)
                        .findFirst();
                if (first.isPresent()) {
                    // for (SubmittedOrder submittedOrder : submittedOrders) {
                    ListIterator<SubmittedOrder> listIterator = submittedOrders.listIterator();
                    while (listIterator.hasNext()) {
                        SubmittedOrder next = listIterator.next();
                        if (next.getId() == first.get().getId()) {
                            listIterator.set(first.get());
                        }
                    }
                    // if (submittedOrder.getId() == first.get().getId()) {
                    // submittedOrders.remove(submittedOrder);
                    //submittedOrders.add(first.get());
                    //}
                    // }
                    clientAppRenderer.renderOrders(submittedOrders);
                    integerStatusMap.put(orderId, status);
                    clientAppRenderer.renderAfterNotification();
                }
            } catch (RemoteException e) {
                notificationLabel.setText("ERROR: Could not refresh order!");
            }
        });
        listenerRemote = (IStatusListener) UnicastRemoteObject.exportObject(statusListenerImplementation, 0);
        return shop.subscribe(listenerRemote, clientId);
    }

    public boolean unsubscribe() {
        if (shop == null) {
            return false;
        }
        try {
            return shop.unsubscribe(clientId);
        } catch (RemoteException ignored) {
            return false;
        }
    }

    public Map<Integer, Status> getIntegerStatusMap() {
        return integerStatusMap;
    }

    public int getClientId() {
        return clientId;
    }

    public List<SubmittedOrder> getSubmittedOrders() {
        return submittedOrders;
    }

    public void unExportListener() throws NoSuchObjectException {
        UnicastRemoteObject.unexportObject(listenerRemote, false);
    }

    public void killApp() {
        System.exit(0);
    }

}
