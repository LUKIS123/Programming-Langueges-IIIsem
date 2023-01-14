package pl.edu.pwr.lgawron.lab07.shop.repositories;

import model.Status;
import model.SubmittedOrder;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientOrdersRepository implements IOrderRepository {
    private final List<SubmittedOrder> submittedOrderList;

    public ClientOrdersRepository() {
        this.submittedOrderList = new ArrayList<>();
    }

    @Override
    public List<SubmittedOrder> getRepo() throws RemoteException {
        return submittedOrderList;
    }

    @Override
    public void addInstance(SubmittedOrder submitted) throws RemoteException {
        submittedOrderList.add(submitted);
    }

    @Override
    public SubmittedOrder getById(int id) throws RemoteException {
        Optional<SubmittedOrder> first = submittedOrderList.stream().filter(submittedOrder -> submittedOrder.getId() == id).findFirst();
        return first.orElse(null);
    }

    @Override
    public List<SubmittedOrder> getByClientId(int clientId) throws RemoteException {
        return submittedOrderList.stream().filter(submittedOrder -> submittedOrder.getOrder().getClientID() == clientId).collect(Collectors.toList());
    }

    @Override
    public List<SubmittedOrder> getByStatus(Status status) throws RemoteException {
        return submittedOrderList.stream().filter(submittedOrder -> submittedOrder.getStatus() == status).collect(Collectors.toList());
    }

}
