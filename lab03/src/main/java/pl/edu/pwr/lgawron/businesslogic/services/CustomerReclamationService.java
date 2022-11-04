package pl.edu.pwr.lgawron.businesslogic.services;

import pl.edu.pwr.lgawron.businesslogic.models.Reclamation;
import pl.edu.pwr.lgawron.businesslogic.models.ReclamationStatus;
import pl.edu.pwr.lgawron.businesslogic.repositories.CustomerReclamationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerReclamationService implements ModelService<Reclamation> {
    private final CustomerReclamationRepository repository;
    private final List<Reclamation> customerReclamations;

    public CustomerReclamationService() {
        this.repository = new CustomerReclamationRepository();
        this.customerReclamations = new ArrayList<>();
        this.refreshDataList();
    }

    @Override
    public List<Reclamation> refreshDataList() {
        List<Reclamation> loadedData = repository.loadData();
        if (loadedData == null) {
            return customerReclamations;
        }
        if (customerReclamations.isEmpty()) {
            customerReclamations.addAll(loadedData);
        } else {
            for (Reclamation reclamation : loadedData) {
                Reclamation byId = this.findById(reclamation.getId());
                if (byId == null) {
                    customerReclamations.add(reclamation);
                } else {
                    customerReclamations.set(customerReclamations.indexOf(byId), reclamation);
                }
            }
        }
        return customerReclamations;
    }

    @Override
    public List<Reclamation> getDataList() {
         return this.refreshDataList();
    }

    @Override
    public Reclamation findById(int id) {
        Optional<Reclamation> first = customerReclamations.stream().filter(reclamation -> reclamation.getCustomerId() == id).findFirst();
        return first.orElse(null);
    }

    @Override
    public void addToDatabase(Reclamation reclamation) {
        this.customerReclamations.add(reclamation);
        this.repository.saveData(customerReclamations);
    }

    @Override
    public void deleteFromDatabase(int id) {
        Reclamation byId = this.findById(id);
        if (byId != null && byId.status == ReclamationStatus.REPORTED) {
            customerReclamations.remove(byId);
            this.repository.saveData(customerReclamations);
        }
    }
}
