package pl.edu.pwr.lgawron.lab07.seller;

import interfaces.IShop;
import javafx.scene.layout.VBox;
import model.Status;
import model.SubmittedOrder;
import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;
import pl.edu.pwr.lgawron.lab07.seller.flow.SellerAppRenderer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class SellerAppFlow {
    private final ValuesHolder values;
    private IShop shop;
    private final SellerAppRenderer sellerAppRenderer;
    private final List<SubmittedOrder> submittedOrderList;

    public SellerAppFlow(ValuesHolder values, VBox presentOrderBox, VBox orderHistoryBox) {
        this.values = values;

        this.submittedOrderList = new ArrayList<>();
        this.sellerAppRenderer = new SellerAppRenderer(this, presentOrderBox, orderHistoryBox);
    }

    public void initialize() throws RemoteException, NotBoundException {
        Registry localhost = LocateRegistry.getRegistry(values.getServer(), values.getPort());
        shop = (IShop) localhost.lookup("shopRemote");
        this.downloadOrdersAndRefresh();
    }

    public void downloadOrdersAndRefresh() throws RemoteException {
        submittedOrderList.clear();
        submittedOrderList.addAll(shop.getSubmittedOrders());
        sellerAppRenderer.renderOrders(submittedOrderList);
    }

    public boolean setOrderProcessing(int orderId) throws RemoteException {
        return shop.setStatus(orderId, Status.PROCESSING);
    }

    public boolean setOrderReady(int orderId) throws RemoteException {
        return shop.setStatus(orderId, Status.READY);
    }

    public void killApp() {
        System.exit(0);
    }

}
