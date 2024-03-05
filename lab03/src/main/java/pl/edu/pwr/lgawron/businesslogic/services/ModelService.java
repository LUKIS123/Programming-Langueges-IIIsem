package pl.edu.pwr.lgawron.businesslogic.services;

import pl.edu.pwr.lgawron.businesslogic.utility.exceptions.DatabaseSaveException;

import java.util.List;

public interface ModelService<T> {
    public List<T> refreshDataList();

    public List<T> getDataList();

    public T findById(int id);

    public void addToDatabase(T t) throws DatabaseSaveException;

    public void deleteFromDatabase(int id) throws DatabaseSaveException;
}
