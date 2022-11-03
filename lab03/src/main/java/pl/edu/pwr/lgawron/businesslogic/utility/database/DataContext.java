package pl.edu.pwr.lgawron.businesslogic.utility.database;

import pl.edu.pwr.lgawron.businesslogic.models.Customer;
import pl.edu.pwr.lgawron.businesslogic.models.Manufacturer;
import pl.edu.pwr.lgawron.businesslogic.models.Product;
import pl.edu.pwr.lgawron.businesslogic.models.Reclamation;
import pl.edu.pwr.lgawron.businesslogic.repositories.CustomerRepository;
import pl.edu.pwr.lgawron.businesslogic.repositories.ManufacturerRepository;
import pl.edu.pwr.lgawron.businesslogic.repositories.ProductRepository;
import pl.edu.pwr.lgawron.businesslogic.services.CustomerReclamationService;

public class DataContext {
    // przechowuje bazy wszystkich modeli
    // customer - produkty, baza customerow, baza complainow
    // employee - produkty, baza customerow, baza complainow,
    // baza producentow, baza odpowiedzi_producentow
    // producent - baza

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final CustomerReclamationService customerService;
    private final CustomerReclamationService manufacturerService;
    public final String productDatabaseUri = "product_database.json";
    public final String customerDatabaseUri = "customer_database.json";
    public final String manufacturerDatabaseUri = "manufacturer_database.json";
    public final String complaintDatabaseUri = "customer_complaint_database.json";
    public final String responseDatabaseUri = "manufacturer_response_database.json";

    public DataContext() {
        this.productRepository = new ProductRepository();
        this.customerRepository = new CustomerRepository();
        this.manufacturerRepository = new ManufacturerRepository();
        this.customerService = new CustomerReclamationService();
        this.manufacturerService = new CustomerReclamationService();

        this.refreshDatabase();
    }

    public void refreshDatabase() {
        productRepository
                .loadData(
                        JsonSerializeUtility
                                .serializeFromJson
                                        (DataFileUtility
                                                        .readFile
                                                                (productDatabaseUri),
                                                Product.class));
        customerRepository.loadData(JsonSerializeUtility.serializeFromJson(DataFileUtility.readFile(customerDatabaseUri), Customer.class));
        manufacturerRepository.loadData(JsonSerializeUtility.serializeFromJson(DataFileUtility.readFile(manufacturerDatabaseUri), Manufacturer.class));
        customerService.load(JsonSerializeUtility.serializeFromJson(DataFileUtility.readFile(complaintDatabaseUri), Reclamation.class));
        manufacturerService.load(JsonSerializeUtility.serializeFromJson(DataFileUtility.readFile(responseDatabaseUri), Reclamation.class));
    }

    public void saveCustomerServiceData() {
        DataFileUtility.writeJsonString(JsonSerializeUtility.serializeToJson(customerService.getReclamationList()), complaintDatabaseUri);
    }

    public void saveManufacturerServiceData() {
        DataFileUtility.writeJsonString(JsonSerializeUtility.serializeToJson(manufacturerService.getReclamationList()), responseDatabaseUri);
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public ManufacturerRepository getManufacturerRepository() {
        return manufacturerRepository;
    }

    public CustomerReclamationService getCustomerService() {
        return customerService;
    }

    public CustomerReclamationService getManufacturerService() {
        return manufacturerService;
    }
}
