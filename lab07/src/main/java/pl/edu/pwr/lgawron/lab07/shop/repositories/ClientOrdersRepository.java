package pl.edu.pwr.lgawron.lab07.shop.repositories;

import model.Status;
import model.SubmittedOrder;
import pl.edu.pwr.lgawron.lab07.common.IOrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientOrdersRepository implements IOrderRepository {
    // todo:
    //  zmienic na repa zwykle -> serwisy i może tą klase
    //  zrobic jeden jedyny serwis do orderow
    // dodac metody typu builder zwracajace orderline/order w OrderService
    private final List<SubmittedOrder> submittedOrderList;

    public ClientOrdersRepository() {
        this.submittedOrderList = new ArrayList<>();
    }

    @Override
    public List<SubmittedOrder> getRepo() {
        return submittedOrderList;
    }

    @Override
    public void addInstance(SubmittedOrder submitted) {
        submittedOrderList.add(submitted);
    }

    @Override
    public SubmittedOrder getById(int id) {
        Optional<SubmittedOrder> first = submittedOrderList.stream().filter(submittedOrder -> submittedOrder.getId() == id).findFirst();
        return first.orElse(null);
    }

    public List<SubmittedOrder> getByClientId(int clientId) {
        return submittedOrderList.stream().filter(submittedOrder -> submittedOrder.getOrder().getClientID() == clientId).toList();
    }

    public List<SubmittedOrder> getByStatus(Status status) {
        return submittedOrderList.stream().filter(submittedOrder -> submittedOrder.getStatus() == status).toList();
    }

}
