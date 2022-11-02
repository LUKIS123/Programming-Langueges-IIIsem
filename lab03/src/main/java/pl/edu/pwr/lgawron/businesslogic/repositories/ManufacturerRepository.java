package pl.edu.pwr.lgawron.businesslogic.repositories;

import pl.edu.pwr.lgawron.businesslogic.models.Manufacturer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManufacturerRepository implements ModelRepository<Manufacturer> {
    private final List<Manufacturer> manufacturerList;

    public ManufacturerRepository() {
        this.manufacturerList = new ArrayList<>();
    }

    @Override
    public void loadData(List<Manufacturer> loadedList) {
        if (manufacturerList.isEmpty()) {
            manufacturerList.addAll(loadedList);
        } else {
            for (Manufacturer manufacturer : loadedList) {
                Manufacturer byId = this.findById(manufacturer.getId());
                if (byId == null) {
                    manufacturerList.add(manufacturer);
                }
            }
        }
    }

    @Override
    public List<Manufacturer> getDataList() {
        return manufacturerList;
    }

    @Override
    public Manufacturer findById(int id) {
        Optional<Manufacturer> first = manufacturerList.stream().filter(manufacturer -> manufacturer.getId() == id).findFirst();
        return first.orElse(null);
    }
}
