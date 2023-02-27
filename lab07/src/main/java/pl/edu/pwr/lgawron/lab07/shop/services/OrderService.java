package pl.edu.pwr.lgawron.lab07.shop.services;

import model.Order;
import model.SubmittedOrder;
import pl.edu.pwr.lgawron.lab07.shop.repositories.ClientOrdersRepository;
import pl.edu.pwr.lgawron.lab07.shop.repositories.IRepository;

public class OrderService implements IOrderService {
    private final IRepository<SubmittedOrder> ordersRepository;

    public OrderService(IRepository<SubmittedOrder> ordersRepository) {
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
