package pl.edu.pwr.lgawron.lab07.shop.flow;

import interfaces.IShop;
import interfaces.IStatusListener;
import model.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ShopImplementation implements IShop {
    private final List<ItemType> items;

    public ShopImplementation() {
        this.items = new ArrayList<>();
    }

    @Override
    public int register(Client client) throws RemoteException {
        return 0;
    }

    @Override
    public List<ItemType> getItemList() throws RemoteException {
        return items;
    }

    @Override
    public int placeOrder(Order order) throws RemoteException {
        return 0;
    }

    @Override
    public List<SubmittedOrder> getSubmittedOrders() throws RemoteException {
        return null;
    }

    @Override
    public boolean setStatus(int i, Status status) throws RemoteException {
        return false;
    }

    @Override
    public Status getStatus(int i) throws RemoteException {
        return null;
    }

    @Override
    public boolean subscribe(IStatusListener iStatusListener, int i) throws RemoteException {
        return false;
    }

    @Override
    public boolean unsubscribe(int i) throws RemoteException {
        return false;
    }

    // added
    public void addToList(ItemType itemType) {
        items.add(itemType);
    }

}
