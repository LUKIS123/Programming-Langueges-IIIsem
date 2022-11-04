package pl.edu.pwr.lgawron.businesslogic.repositories;

import pl.edu.pwr.lgawron.businesslogic.models.Product;
import pl.edu.pwr.lgawron.businesslogic.utility.database.DataFileUtility;
import pl.edu.pwr.lgawron.businesslogic.utility.database.JsonSerializeUtility;

import java.util.List;

public class ProductRepository implements ModelRepository<Product> {
    public final String productDatabaseUri = "product_database.json";

    @Override
    public List<Product> loadData() {
        return JsonSerializeUtility
                .serializeFromJson(DataFileUtility.readFile(productDatabaseUri), Product.class);
    }

    @Override
    public void saveData(List<Product> listToSave) {
        DataFileUtility.writeJsonString(JsonSerializeUtility.serializeToJson(listToSave), productDatabaseUri);
    }
}
