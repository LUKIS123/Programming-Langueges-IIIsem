package pl.edu.pwr.lgawron.lab07.shop.repositories;

import java.util.List;

//https://turreta.com/2017/06/26/java-3-ways-to-implement-a-generic-interface
public interface IRepository<T> {
    List<T> getRepo();

    void addInstance(T t);

    T getById(int id);
}
