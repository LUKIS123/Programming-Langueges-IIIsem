package pl.edu.pwr.lgawron.businesslogic.services;

import pl.edu.pwr.lgawron.businesslogic.models.Reclamation;
import pl.edu.pwr.lgawron.businesslogic.models.ReclamationStatus;
import pl.edu.pwr.lgawron.businesslogic.repositories.ManufacturerReclamationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManufacturerReclamationService implements ModelService<Reclamation> {
    private final ManufacturerReclamationRepository repository;
    private final List<Reclamation> manufacturerReclamations;

    public ManufacturerReclamationService() {
        this.repository = new ManufacturerReclamationRepository();
        this.manufacturerReclamations = new ArrayList<>();
        this.refreshDataList();
    }

    @Override
    public List<Reclamation> refreshDataList() {
        List<Reclamation> loadedData = repository.loadData();
        if (loadedData == null) {
            return manufacturerReclamations;
        }
        if (manufacturerReclamations.isEmpty()) {
            manufacturerReclamations.addAll(loadedData);
        } else {
            for (Reclamation reclamation : loadedData) {
                Reclamation byId = this.findById(reclamation.getId());
                if (byId == null) {
                    manufacturerReclamations.add(reclamation);
                } else {
                    manufacturerReclamations.set(manufacturerReclamations.indexOf(byId), reclamation);
                }
            }
        }
        return manufacturerReclamations;
    }

    @Override
    public List<Reclamation> getDataList() {
        return this.refreshDataList();
    }

    @Override
    public Reclamation findById(int id) {
        Optional<Reclamation> first = manufacturerReclamations.stream().filter(reclamation -> reclamation.getId() == id).findFirst();
        return first.orElse(null);
    }

    @Override
    public void addToDatabase(Reclamation reclamation) {
        this.refreshDataList();
        manufacturerReclamations.add(reclamation);
        repository.saveData(manufacturerReclamations);
    }

    @Override
    public void deleteFromDatabase(int id) {
        this.refreshDataList();
        Reclamation byId = this.findById(id);
        if (byId != null && byId.status == ReclamationStatus.REPORTED) {
            manufacturerReclamations.remove(byId);
            repository.saveData(manufacturerReclamations);
        }
    }

    public void saveDataList() {
        repository.saveData(manufacturerReclamations);
    }

    public void replaceReclamation(Reclamation reclamation) {
        this.refreshDataList();
        Reclamation byId = this.findById(reclamation.getId());
        if (byId != null) {
            manufacturerReclamations.set(manufacturerReclamations.indexOf(byId), reclamation);
            repository.saveData(manufacturerReclamations);
        }
    }

    public int getSequence() {
        int sequence = 1;
        while (true) {
            Reclamation byId = this.findById(sequence);
            if (byId == null) {
                return sequence;
            } else sequence++;
        }
    }

}
