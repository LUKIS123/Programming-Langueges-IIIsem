package pl.edu.pwr.lgawron.lab07.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

//https://turreta.com/2017/06/26/java-3-ways-to-implement-a-generic-interface
public interface IRepository<T> extends Remote {
    List<T> getRepo() throws RemoteException;

    void addInstance(T t) throws RemoteException;

    T getById(int id) throws RemoteException;
}
