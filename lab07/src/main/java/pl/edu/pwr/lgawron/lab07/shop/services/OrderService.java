package pl.edu.pwr.lgawron.lab07.shop.services;

import model.*;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ClientOrdersRepository;
import pl.edu.pwr.lgawron.lab07.shop.repositories.IOrderRepository;

public class OrderService implements IOrderService {
    private final IOrderRepository ordersRepository;

    public OrderService(IOrderRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Override
    public ClientOrdersRepository getOrderRepository() {
        return (ClientOrdersRepository) ordersRepository;
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

}
