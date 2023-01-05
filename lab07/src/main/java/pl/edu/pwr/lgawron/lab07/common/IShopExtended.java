package pl.edu.pwr.lgawron.lab07.common;

import interfaces.IShop;
import model.ItemType;

import java.rmi.RemoteException;

public interface IShopExtended extends IShop {
    int addItemType(ItemType itemType) throws RemoteException;

}
