package pl.edu.pwr.lgawron.businesslogic.services;

import pl.edu.pwr.lgawron.businesslogic.models.Manufacturer;
import pl.edu.pwr.lgawron.businesslogic.repositories.ManufacturerRepository;
import pl.edu.pwr.lgawron.businesslogic.utility.exceptions.DatabaseSaveException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManufacturerService implements ModelService<Manufacturer> {
    private final ManufacturerRepository repository;
    private final List<Manufacturer> manufacturers;

    public ManufacturerService() {
        this.repository = new ManufacturerRepository();
        this.manufacturers = new ArrayList<>();
        this.refreshDataList();
    }

    @Override
    public List<Manufacturer> refreshDataList() {
        List<Manufacturer> loadedData = repository.loadData();
        if (loadedData == null) {
            return manufacturers;
        }
        if (manufacturers.isEmpty()) {
            manufacturers.addAll(loadedData);
        } else {
            for (Manufacturer manufacturer : loadedData) {
                Manufacturer byId = this.findById(manufacturer.getId());
                if (byId == null) {
                    manufacturers.add(manufacturer);
                } else {
                    manufacturers.set(manufacturers.indexOf(byId), manufacturer);
                }
            }
        }
        return manufacturers;
    }

    @Override
    public List<Manufacturer> getDataList() {
        return this.refreshDataList();
    }

    @Override
    public Manufacturer findById(int id) {
        Optional<Manufacturer> first = manufacturers.stream().filter(manufacturer -> manufacturer.getId() == id).findFirst();
        return first.orElse(null);
    }

    @Override
    public void addToDatabase(Manufacturer manufacturer) throws DatabaseSaveException {
        this.manufacturers.add(manufacturer);
        this.repository.saveData(manufacturers);
    }

    @Override
    public void deleteFromDatabase(int id) throws DatabaseSaveException {
        Manufacturer byId = this.findById(id);
        if (byId != null) {
            manufacturers.remove(byId);
            this.repository.saveData(manufacturers);
        }
    }
}
