package pl.edu.pwr.lgawron.lab07.common;

import model.*;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ClientOrdersRepository;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IOrderService extends Remote {
    ClientOrdersRepository getOrderRepository() throws RemoteException;

    OrderLine createNewSingleOrderLine(ItemType type, int quantity, String advert) throws RemoteException;

    Order createNewOrder(int clientId) throws RemoteException;

    Order addLineToOrder(Order order, OrderLine orderLine) throws RemoteException;

    Order removeLineFromOrder(Order order, OrderLine orderLine) throws RemoteException;

    Order addLinesToOrder(Order order, List<OrderLine> orderLines) throws RemoteException;

    SubmittedOrder creteNewSubmittedOrderAndAddToRepo(Order order) throws RemoteException;

    SubmittedOrder getSubmittedById(int id) throws RemoteException;

    List<SubmittedOrder> getSubmittedByClientId(int clientId) throws RemoteException;

    List<SubmittedOrder> getSubmittedByStatus(Status status) throws RemoteException;

    SubmittedOrder changeSubmittedStatus(int id, Status newStatus) throws RemoteException;

}
