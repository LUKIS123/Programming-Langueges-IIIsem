package pl.edu.pwr.lgawron.customer.flow;

import pl.edu.pwr.lgawron.businesslogic.models.Product;
import pl.edu.pwr.lgawron.businesslogic.repositories.CustomerRepository;
import pl.edu.pwr.lgawron.businesslogic.repositories.ProductRepository;
import pl.edu.pwr.lgawron.businesslogic.services.CustomerReclamationService;
import pl.edu.pwr.lgawron.businesslogic.utility.parse.ConsoleInputDataParser;

import java.util.List;

public class CustomerAppFlow {
    private final int id;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CustomerReclamationService reclamationService;

    public CustomerAppFlow(String[] consoleInput) {
        // customer ID
        this.id = ConsoleInputDataParser.getId(consoleInput);

        this.customerRepository = new CustomerRepository();
        this.productRepository = new ProductRepository();
        this.reclamationService = new CustomerReclamationService();
    }

    public List<Product> listProducts() {
        return productRepository.getDataList();
    }

}
