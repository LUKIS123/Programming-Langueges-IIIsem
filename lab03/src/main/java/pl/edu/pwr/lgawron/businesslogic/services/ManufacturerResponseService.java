package pl.edu.pwr.lgawron.businesslogic.services;

import pl.edu.pwr.lgawron.businesslogic.models.Reclamation;

import java.util.ArrayList;
import java.util.List;

// zaimlementowac mape, inaczej nie ma sensu (2x CustomerReclamationService)

public class ManufacturerResponseService {
    private final List<Reclamation> reclamationList;

    public ManufacturerResponseService() {
        this.reclamationList = new ArrayList<>();
    }

    public void load(List<Reclamation> loadedList){
        if(reclamationList.isEmpty()){
            reclamationList.addAll(loadedList);
        }
    }





}
