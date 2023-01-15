package pl.edu.pwr.lgawron.lab07.shop.repositories;

import model.Status;
import model.SubmittedOrder;

import java.util.List;

public interface IOrderRepository {
    List<SubmittedOrder> getRepo();

    void addInstance(SubmittedOrder submitted);

    SubmittedOrder getById(int id);

    List<SubmittedOrder> getByClientId(int clientId);

    List<SubmittedOrder> getByStatus(Status status);

}
