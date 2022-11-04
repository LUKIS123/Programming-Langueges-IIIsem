package pl.edu.pwr.lgawron.businesslogic.repositories;

import pl.edu.pwr.lgawron.businesslogic.models.Reclamation;
import pl.edu.pwr.lgawron.businesslogic.utility.database.DataFileUtility;
import pl.edu.pwr.lgawron.businesslogic.utility.database.JsonSerializeUtility;

import java.util.List;

public class ManufacturerReclamationRepository implements ModelRepository<Reclamation> {
    public final String responseDatabaseUri = "manufacturer_response_database.json";

    @Override
    public List<Reclamation> loadData() {
        return JsonSerializeUtility
                .serializeFromJson(DataFileUtility.readFile(responseDatabaseUri), Reclamation.class);
    }

    @Override
    public void saveData(List<Reclamation> listToSave) {
        DataFileUtility
                .writeJsonString(JsonSerializeUtility.serializeToJson(listToSave), responseDatabaseUri);
    }
}
