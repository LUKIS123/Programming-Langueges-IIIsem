package pl.edu.pwr.lgawron.lab07.shop.repositories;

import model.SubmittedOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientOrdersRepository implements IRepository<SubmittedOrder> {
    private final List<SubmittedOrder> submittedOrderList;

    public ClientOrdersRepository() {
        this.submittedOrderList = new ArrayList<>();
    }

    @Override
    public List<SubmittedOrder> getRepositoryData() {
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

}
