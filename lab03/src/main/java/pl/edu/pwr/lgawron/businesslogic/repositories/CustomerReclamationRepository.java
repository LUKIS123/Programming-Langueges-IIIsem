package pl.edu.pwr.lgawron.businesslogic.repositories;

import pl.edu.pwr.lgawron.businesslogic.models.Reclamation;
import pl.edu.pwr.lgawron.businesslogic.utility.database.DataFileUtility;
import pl.edu.pwr.lgawron.businesslogic.utility.database.JsonSerializeUtility;
import pl.edu.pwr.lgawron.businesslogic.utility.exceptions.DatabaseSaveException;

import java.util.List;

public class CustomerReclamationRepository implements ModelRepository<Reclamation> {
    public final String complaintDatabaseUri = "customer_complaint_database.json";

    @Override
    public List<Reclamation> loadData() {
        return JsonSerializeUtility
                .serializeFromJson(DataFileUtility.readFile(complaintDatabaseUri), Reclamation.class);
    }

    @Override
    public void saveData(List<Reclamation> listToSave) throws DatabaseSaveException {
        DataFileUtility
                .writeJsonString(JsonSerializeUtility.serializeToJson(listToSave), complaintDatabaseUri);
    }
}
