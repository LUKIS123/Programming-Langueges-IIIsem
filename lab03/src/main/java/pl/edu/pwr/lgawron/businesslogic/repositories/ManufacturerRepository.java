package pl.edu.pwr.lgawron.businesslogic.repositories;

import pl.edu.pwr.lgawron.businesslogic.models.Manufacturer;
import pl.edu.pwr.lgawron.businesslogic.utility.database.DataFileUtility;
import pl.edu.pwr.lgawron.businesslogic.utility.database.JsonSerializeUtility;
import pl.edu.pwr.lgawron.businesslogic.utility.exceptions.DatabaseSaveException;

import java.util.List;

public class ManufacturerRepository implements ModelRepository<Manufacturer> {
    public final String manufacturerDatabaseUri = "manufacturer_database.json";

    @Override
    public List<Manufacturer> loadData() {
        return JsonSerializeUtility
                .serializeFromJson(DataFileUtility.readFile(manufacturerDatabaseUri), Manufacturer.class);
    }

    @Override
    public void saveData(List<Manufacturer> listToSave) throws DatabaseSaveException {
        DataFileUtility
                .writeJsonString(JsonSerializeUtility.serializeToJson(listToSave), manufacturerDatabaseUri);
    }
}
