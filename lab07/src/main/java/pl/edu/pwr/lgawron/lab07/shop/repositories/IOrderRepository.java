package pl.edu.pwr.lgawron.lab07.shop.repositories;

import model.Status;
import model.SubmittedOrder;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IOrderRepository extends Remote {
    List<SubmittedOrder> getRepo() throws RemoteException;

    void addInstance(SubmittedOrder submitted) throws RemoteException;

    SubmittedOrder getById(int id) throws RemoteException;

    List<SubmittedOrder> getByClientId(int clientId) throws RemoteException;

    List<SubmittedOrder> getByStatus(Status status) throws RemoteException;

}
