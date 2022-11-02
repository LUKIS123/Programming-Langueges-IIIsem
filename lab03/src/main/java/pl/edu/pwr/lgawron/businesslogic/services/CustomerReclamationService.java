package pl.edu.pwr.lgawron.businesslogic.services;

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
        if (reclamationList.isEmpty()) {
            reclamationList.addAll(loadedList);
        } else {
            for (Reclamation reclamation : loadedList) {
                Reclamation byId = this.findById(reclamation.getId());
                if (byId == null) {
                    reclamationList.add(reclamation);
                } else {
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

    public void addReclamation(Reclamation reclamation) {
        reclamationList.add(reclamation);
    }
}
