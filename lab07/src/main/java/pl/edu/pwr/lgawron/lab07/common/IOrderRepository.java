package pl.edu.pwr.lgawron.lab07.common;

import model.Status;
import model.SubmittedOrder;

import java.rmi.Remote;
import java.util.List;

public interface IOrderRepository extends Remote {
    List<SubmittedOrder> getRepo();

    void addInstance(SubmittedOrder submitted);

    SubmittedOrder getById(int id);

    List<SubmittedOrder> getByClientId(int clientId);

    List<SubmittedOrder> getByStatus(Status status);

}
