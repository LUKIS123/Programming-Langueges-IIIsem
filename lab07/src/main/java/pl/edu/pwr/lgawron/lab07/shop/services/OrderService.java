package pl.edu.pwr.lgawron.lab07.shop.services;

import model.*;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ClientOrdersRepository;
import pl.edu.pwr.lgawron.lab07.shop.repositories.IOrderRepository;

import java.util.List;

public class OrderService implements IOrderService {
    private final IOrderRepository ordersRepository;

    public OrderService(IOrderRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public ClientOrdersRepository getOrderRepository() {
        return (ClientOrdersRepository) ordersRepository;
    }

    // single order line utils
    @Override
    public OrderLine createNewSingleOrderLine(ItemType type, int quantity, String advert) {
        return new OrderLine(type, quantity, advert);
    }

    // order utils
    @Override
    public Order createNewOrder(int clientId) {
        return new Order(clientId);
    }

    @Override
    public Order addLineToOrder(Order order, OrderLine orderLine) {
        order.addOrderLine(orderLine);
        return order;
    }

    @Override
    public Order removeLineFromOrder(Order order, OrderLine orderLine) {
        order.removeOrderLine(orderLine);
        return order;
    }

    @Override
    public Order addLinesToOrder(Order order, List<OrderLine> orderLines) {
        orderLines.forEach(order::addOrderLine);
        return order;
    }

    // submitted order utils
    @Override
    public SubmittedOrder creteNewSubmittedOrderAndAddToRepo(Order order) {
        SubmittedOrder submitted = new SubmittedOrder(order);
        ordersRepository.addInstance(submitted);
        return submitted;
    }

    @Override
    public SubmittedOrder getSubmittedById(int id) {
        return ordersRepository.getById(id);
    }

    @Override
    public List<SubmittedOrder> getSubmittedByClientId(int clientId) {
        return ordersRepository.getByClientId(clientId);
    }

    @Override
    public List<SubmittedOrder> getSubmittedByStatus(Status status) {
        return ordersRepository.getByStatus(status);
    }

    @Override
    public SubmittedOrder changeSubmittedStatus(int id, Status newStatus) {
        SubmittedOrder submittedOrderById = this.getSubmittedById(id);
        submittedOrderById.setStatus(newStatus);
        return submittedOrderById;
    }

}
