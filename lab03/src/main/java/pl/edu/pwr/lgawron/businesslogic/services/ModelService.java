package pl.edu.pwr.lgawron.businesslogic.services;

import java.util.List;

public interface ModelService<T> {
    public List<T> refreshDataList();

    public List<T> getDataList();

    public T findById(int id);

    public void addToDatabase(T t);

    public void deleteFromDatabase(int id);
}
