package pl.edu.pwr.lgawron.customer.controller;

import pl.edu.pwr.lgawron.businesslogic.models.Customer;
import pl.edu.pwr.lgawron.businesslogic.models.Reclamation;
import pl.edu.pwr.lgawron.businesslogic.models.ReclamationStatus;
import pl.edu.pwr.lgawron.businesslogic.services.CustomerReclamationService;
import pl.edu.pwr.lgawron.businesslogic.services.CustomerService;
import pl.edu.pwr.lgawron.businesslogic.services.ProductService;
import pl.edu.pwr.lgawron.businesslogic.utility.date.DateReader;
import pl.edu.pwr.lgawron.customer.view.ActionResult;
import pl.edu.pwr.lgawron.customer.view.CustomerConsoleAppView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerAppController {
    private final CustomerService customerService;
    private final ProductService productService;
    private final CustomerReclamationService reclamationService;
    private int userId;
    private final DateReader dateReader;
    private int databaseSequence;
    public LocalDate today;

    public CustomerAppController() {
        this.customerService = new CustomerService();
        this.productService = new ProductService();
        this.reclamationService = new CustomerReclamationService();
        this.dateReader = new DateReader("date.txt");
        this.today = dateReader.getCurrentDate();
        this.databaseSequence = reclamationService.getSequence();
    }

    public void index() {
        ActionResult actionResult = CustomerConsoleAppView.login(this);
        while (actionResult != ActionResult.END) {
            switch (actionResult) {
                case MENU:
                    actionResult = CustomerConsoleAppView.menu();
                    break;
                case LIST_PRODUCTS:
                    actionResult = CustomerConsoleAppView.listProducts(productService.getDataList());
                    break;
                case LIST_COMPLAINTS:
                    actionResult = CustomerConsoleAppView.listComplaints(this.findByCustomerId());
                    break;
                case ADD_COMPLAINT:
                    actionResult = CustomerConsoleAppView.addComplaint(this);
                    break;
                case DELETE_COMPLAINT:
                    actionResult = CustomerConsoleAppView.deleteComplaintIfPossible(this);
                    break;
                case REFRESH_DATE:
                    actionResult = CustomerConsoleAppView.refreshDate(this);
                    break;
                case PICK_UP:
                    actionResult = CustomerConsoleAppView.pickUp(this);
                    break;
            }
        }

    }

    public boolean loginToDatabase(int customerId) {
        Customer byId = customerService.findById(customerId);
        if (byId != null) {
            userId = customerId;
            return true;
        }
        return false;
    }

    public String getUserName() {
        return customerService.findById(userId).username;
    }

    public List<Reclamation> findByCustomerId() {
        return reclamationService.getDataList()
                .stream()
                .filter(reclamation -> reclamation.getCustomerId() == userId).toList();
    }

    public void addCustomerReclamation(int productId, String complaintDescription) {
        this.refreshDate();
        Reclamation newReclamation = new Reclamation(databaseSequence, productId, userId);
        newReclamation.description = complaintDescription;
        newReclamation.status = ReclamationStatus.REPORTED;
        newReclamation.submittedToEmployee = today;
        reclamationService.addToDatabase(newReclamation);

        today = dateReader.getCurrentDate();
        databaseSequence = reclamationService.getSequence();
    }

    public boolean deleteReclamation(int reclamationId) {
        reclamationService.deleteFromDatabase(reclamationId);
        return reclamationService.findById(reclamationId) == null;
    }

    public void refreshDate() {
        today = dateReader.getCurrentDate();
    }

    public List<Integer> pickUp() {
        this.refreshDate();
        List<Integer> pickedUp = new ArrayList<>();
        List<Reclamation> byCustomerId = this.findByCustomerId();
        for (Reclamation reclamation : byCustomerId) {
            if (reclamation.status == ReclamationStatus.READY_TO_PICKUP) {
                reclamation.status = ReclamationStatus.FINISHED;
                reclamation.resulted = today;
                pickedUp.add(reclamation.getId());
            }
        }
        return pickedUp;
    }

}
