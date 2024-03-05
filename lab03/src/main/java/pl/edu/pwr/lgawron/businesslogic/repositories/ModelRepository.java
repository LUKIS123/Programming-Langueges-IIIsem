package pl.edu.pwr.lgawron.businesslogic.repositories;

import pl.edu.pwr.lgawron.businesslogic.utility.exceptions.DatabaseSaveException;

import java.util.List;

public interface ModelRepository<T> {
    public List<T> loadData();

    public void saveData(List<T> listToSave) throws DatabaseSaveException;
}
