package pl.edu.pwr.lgawron.businesslogic.repositories;

import java.util.List;

public interface ModelRepository<T> {
    public void loadData(List<T> loadedList);

    public List<T> getDataList();

    public T findById(int id);
}
