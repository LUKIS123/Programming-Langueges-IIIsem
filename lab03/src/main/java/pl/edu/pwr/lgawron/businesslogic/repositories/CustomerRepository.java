package pl.edu.pwr.lgawron.businesslogic.repositories;

import pl.edu.pwr.lgawron.businesslogic.models.Customer;
import pl.edu.pwr.lgawron.businesslogic.utility.database.DataFileUtility;
import pl.edu.pwr.lgawron.businesslogic.utility.database.JsonSerializeUtility;

import java.util.List;

public class CustomerRepository implements ModelRepository<Customer> {
    public final String customerDatabaseUri = "customer_database.json";

    @Override
    public List<Customer> loadData() {
        return JsonSerializeUtility
                .serializeFromJson(DataFileUtility.readFile(customerDatabaseUri), Customer.class);
    }

    @Override
    public void saveData(List<Customer> listToSave) {
        DataFileUtility
                .writeJsonString(JsonSerializeUtility.serializeToJson(listToSave), customerDatabaseUri);
    }
}
