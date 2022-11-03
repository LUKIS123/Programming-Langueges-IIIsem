package pl.edu.pwr.lgawron.customer.flow;

import pl.edu.pwr.lgawron.businesslogic.models.Product;
import pl.edu.pwr.lgawron.businesslogic.models.Reclamation;
import pl.edu.pwr.lgawron.businesslogic.models.ReclamationStatus;
import pl.edu.pwr.lgawron.businesslogic.repositories.CustomerRepository;
import pl.edu.pwr.lgawron.businesslogic.repositories.ProductRepository;
import pl.edu.pwr.lgawron.businesslogic.services.CustomerReclamationService;
import pl.edu.pwr.lgawron.businesslogic.utility.database.DataContext;
import pl.edu.pwr.lgawron.businesslogic.utility.date.DateReader;
import pl.edu.pwr.lgawron.businesslogic.utility.parse.ConsoleInputDataParser;

import java.time.LocalDate;
import java.util.List;

public class CustomerAppFlow {
    private final int customerId;
    private int sequence;
    public LocalDate now;
    private final DataContext dataContext;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CustomerReclamationService reclamationService;

    public CustomerAppFlow(String[] consoleInput) {
        this.dataContext = new DataContext();
        dataContext.refreshDatabase();
        DateReader dateReader = new DateReader("date.txt");
        dateReader.refreshCurrentDate();

        this.customerRepository = dataContext.getCustomerRepository();
        this.productRepository = dataContext.getProductRepository();
        this.reclamationService = dataContext.getCustomerService();

        // customer ID
        this.customerId = ConsoleInputDataParser.getId(consoleInput);
        // next complaint id
        this.sequence = reclamationService.findByCustomer(customerId).size() + 1;
        // date
        this.now = dateReader.getCurrentDate();

    }

    public List<Product> listProducts() {
        return productRepository.getDataList();
    }

    public void makeComplaint(int productId, String description) {
        // adding new complaint to List in reclamationService
        var newReclamation = new Reclamation(sequence, productId, customerId);
        newReclamation.description = description;
        newReclamation.status = ReclamationStatus.REPORTED;
        newReclamation.submittedToEmployee = now;
        reclamationService.addReclamation(newReclamation);

        // updating sequence
        sequence += 1;

        // saving changes to file
        dataContext.saveCustomerServiceData();
    }

    public void deleteComplaint(int reclamationId) {
        reclamationService.deleteReclamation(reclamationId);
        sequence -= 1;
    }

    public void updateReclamation(int reclamationId, String description) {
        reclamationService.updateDescription(reclamationId, description);
    }

    public void updateReclamation(int reclamationId, int productId) {
        reclamationService.updateProductId(reclamationId, productId);
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public CustomerReclamationService getReclamationService() {
        return reclamationService;
    }
}
