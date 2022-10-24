package pl.edu.pwr.lgawron.business.models;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final int id;
    private final List<Reclamation> reclamationList;

    public Customer(int id) {
        this.id = id;
        this.reclamationList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Reclamation> getReclamationList() {
        return reclamationList;
    }

    public void addReclamation(Reclamation reclamation) {
        reclamationList.add(reclamation);
    }
}
