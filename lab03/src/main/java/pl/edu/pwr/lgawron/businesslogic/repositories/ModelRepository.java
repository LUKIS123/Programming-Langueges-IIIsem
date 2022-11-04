package pl.edu.pwr.lgawron.businesslogic.repositories;

import java.util.List;

public interface ModelRepository<T> {
    public List<T> loadData();

    public void saveData(List<T> listToSave);
}
