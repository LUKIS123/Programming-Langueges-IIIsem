package pl.edu.pwr.lgawron.lab07.shop.services;

import model.*;
import pl.edu.pwr.lgawron.lab07.shop.repositories.IOrderRepository;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ClientOrdersRepository;

import java.rmi.RemoteException;
import java.util.List;

public class OrderService implements IOrderService {
    // todo:
    //  zrobic jeden jedyny serwis do orderow
    // dodac metody typu builder (nie do konca takie sa)
    // zwracajace orderline/order w OrderService
    private final IOrderRepository ordersRepository;

    public OrderService(IOrderRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public void addSubmittedOrder(SubmittedOrder submittedOrder) throws RemoteException {
        ordersRepository.addInstance(submittedOrder);
    }

    @Override
    public ClientOrdersRepository getOrderRepository() throws RemoteException {
        return (ClientOrdersRepository) ordersRepository;
    }

    // single order line utils
    @Override
    public OrderLine createNewSingleOrderLine(ItemType type, int quantity, String advert) throws RemoteException {
        return new OrderLine(type, quantity, advert);
    }

    // order utils
    @Override
    public Order createNewOrder(int clientId) throws RemoteException {
        return new Order(clientId);
    }

    @Override
    public Order addLineToOrder(Order order, OrderLine orderLine) throws RemoteException {
        order.addOrderLine(orderLine);
        return order;
    }

    @Override
    public Order removeLineFromOrder(Order order, OrderLine orderLine) throws RemoteException {
        order.removeOrderLine(orderLine);
        return order;
    }

    @Override
    public Order addLinesToOrder(Order order, List<OrderLine> orderLines) throws RemoteException {
        orderLines.forEach(order::addOrderLine);
        return order;
    }

    // submitted order utils
    @Override
    public SubmittedOrder creteNewSubmittedOrderAndAddToRepo(Order order) throws RemoteException {
        SubmittedOrder submitted = new SubmittedOrder(order);
        ordersRepository.addInstance(submitted);
        return submitted;
    }

    @Override
    public SubmittedOrder getSubmittedById(int id) throws RemoteException {
        return ordersRepository.getById(id);
    }

    @Override
    public List<SubmittedOrder> getSubmittedByClientId(int clientId) throws RemoteException {
        return ordersRepository.getByClientId(clientId);
    }

    @Override
    public List<SubmittedOrder> getSubmittedByStatus(Status status) throws RemoteException {
        return ordersRepository.getByStatus(status);
    }

    @Override
    public SubmittedOrder changeSubmittedStatus(int id, Status newStatus) throws RemoteException {
        SubmittedOrder submittedOrderById = this.getSubmittedById(id);
        submittedOrderById.setStatus(newStatus);
        return submittedOrderById;
    }

}
