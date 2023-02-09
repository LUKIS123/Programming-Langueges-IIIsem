package pl.edu.pwr.lgawron.lab07.shop.repositories;

import model.SubmittedOrder;

import java.util.List;

public interface IOrderRepository {
    List<SubmittedOrder> getRepoData();

    void addInstance(SubmittedOrder submitted);

    SubmittedOrder getById(int id);

}
