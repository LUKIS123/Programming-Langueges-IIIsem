package pl.edu.pwr.lgawron.lab07.shop.services;

import model.Order;
import model.SubmittedOrder;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ClientOrdersRepository;

public interface IOrderService {
    ClientOrdersRepository getOrderRepository();

    SubmittedOrder creteNewSubmittedOrderAndAddToRepo(Order order);

    SubmittedOrder getSubmittedById(int id);

}
