package pl.edu.pwr.lgawron.lab07.shop.services;

import model.*;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ClientOrdersRepository;

import java.util.List;

public interface IOrderService {
    ClientOrdersRepository getOrderRepository();

    OrderLine createNewSingleOrderLine(ItemType type, int quantity, String advert);

    Order createNewOrder(int clientId);

    Order addLineToOrder(Order order, OrderLine orderLine);

    Order removeLineFromOrder(Order order, OrderLine orderLine);

    Order addLinesToOrder(Order order, List<OrderLine> orderLines);

    SubmittedOrder creteNewSubmittedOrderAndAddToRepo(Order order);

    SubmittedOrder getSubmittedById(int id);

    List<SubmittedOrder> getSubmittedByClientId(int clientId);

    List<SubmittedOrder> getSubmittedByStatus(Status status);

    SubmittedOrder changeSubmittedStatus(int id, Status newStatus);

}
