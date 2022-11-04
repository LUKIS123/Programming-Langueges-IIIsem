package pl.edu.pwr.lgawron.businesslogic.service_test;

import pl.edu.pwr.lgawron.businesslogic.models.Reclamation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerReclamationService {
    private final List<Reclamation> reclamationList;

    public CustomerReclamationService() {
        this.reclamationList = new ArrayList<>();
    }

    public void load(List<Reclamation> loadedList) {
        if (loadedList == null) {
            // moze rzucac exception idk
            return;
        }

        if (reclamationList.isEmpty()) {
            reclamationList.addAll(loadedList);
        } else {
            for (Reclamation reclamation : loadedList) {
                Reclamation byId = this.findById(reclamation.getId());
                if (byId == null) {
                    reclamationList.add(reclamation);
                } else {
                    // pewnie trzeba nadpisac equals
                    reclamationList.set(reclamationList.indexOf(byId), reclamation);
                }
            }
        }
    }

    public List<Reclamation> getReclamationList() {
        return reclamationList;
    }

    public Reclamation findById(int id) {
        Optional<Reclamation> first = reclamationList.stream().filter(reclamation -> reclamation.getId() == id).findFirst();
        return first.orElse(null);
    }

    public List<Reclamation> findByCustomer(int customerId) {
        return reclamationList.stream().filter(reclamation -> reclamation.getCustomerId() == customerId).toList();
    }

    public void addReclamation(Reclamation reclamation) {
        reclamationList.add(reclamation);
    }

    public void deleteReclamation(int id) {
        Reclamation byId = this.findById(id);
        if (byId != null) {
            reclamationList.remove(byId);
        }
    }

    public void updateDescription(int id, String description) {
        Reclamation byId = this.findById(id);
        if (byId != null) {
            byId.description = description;
        }
    }

    public void updateProductId(int id, int productId) {
        Reclamation byId = this.findById(id);
        if (byId != null) {
            byId.productId = productId;
        }
    }
}
