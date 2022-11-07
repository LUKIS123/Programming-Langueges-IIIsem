package pl.edu.pwr.lgawron.employee.flow;

import pl.edu.pwr.lgawron.businesslogic.models.Reclamation;
import pl.edu.pwr.lgawron.businesslogic.models.ReclamationStatus;
import pl.edu.pwr.lgawron.businesslogic.services.*;
import pl.edu.pwr.lgawron.businesslogic.utility.date.DateReader;
import pl.edu.pwr.lgawron.businesslogic.utility.exceptions.DatabaseSaveException;
import pl.edu.pwr.lgawron.employee.view.ActionResult;
import pl.edu.pwr.lgawron.employee.view.EmployeeConsoleAppView;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class EmployeeAppController {
    private final CustomerService customerService;
    private final ManufacturerService manufacturerService;
    private final ProductService productService;
    private final CustomerReclamationService customerReclamationService;
    private final ManufacturerReclamationService manufacturerReclamationService;
    private final DateReader dateReader;
    public LocalDate today;
    public long deliveryTime;

    public EmployeeAppController() {
        this.customerService = new CustomerService();
        this.manufacturerService = new ManufacturerService();
        this.productService = new ProductService();
        this.customerReclamationService = new CustomerReclamationService();
        this.manufacturerReclamationService = new ManufacturerReclamationService();
        this.dateReader = new DateReader("date.txt");
        this.today = dateReader.getCurrentDate();
        this.deliveryTime = 2L;
    }

    public void index() {
        ActionResult actionResult = EmployeeConsoleAppView.login(today);
        while (actionResult != ActionResult.END) {
            switch (actionResult) {
                case MENU:
                    actionResult = EmployeeConsoleAppView.menu();
                    break;
                case LIST_PRODUCTS:
                    actionResult = EmployeeConsoleAppView.listProducts(productService.getDataList());
                    break;
                case LIST_COMPLAINTS:
                    actionResult = EmployeeConsoleAppView.listData(customerReclamationService.getDataList());
                    break;
                case LIST_RESPONSES:
                    actionResult = EmployeeConsoleAppView.listData(manufacturerReclamationService.getDataList());
                    break;
                case REFRESH_DATE:
                    actionResult = EmployeeConsoleAppView.refreshDate(this);
                    break;
                case FORWARD_TO_MANUFACTURER:
                    actionResult = EmployeeConsoleAppView.forwardToManufacturer(this);
                    break;
                case FORWARD_TO_CUSTOMER:
                    actionResult = EmployeeConsoleAppView.forwardToCustomer(this);
                    break;
                case FORWARD_ALL:
                    actionResult = EmployeeConsoleAppView.forwardAll(this);
                    break;
            }
        }
    }

    public void refreshDate() {
        today = dateReader.getCurrentDate();
    }

    public boolean sendReclamationToManufacturer(int reclamationId) {
        this.refreshDate();
        Reclamation byId = customerReclamationService.findById(reclamationId);
        if (byId != null && byId.status == ReclamationStatus.REPORTED) {
            byId.status = ReclamationStatus.SENT;
            byId.submittedToManufacturer = today;
            try {
                customerReclamationService.saveDataList();
                manufacturerReclamationService.addToDatabase(byId);
                manufacturerReclamationService.saveDataList();
            } catch (DatabaseSaveException e) {
                System.out.println(e.description);
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean sendBackToCustomer(int reclamationId) {
        this.refreshDate();
        Reclamation byId = manufacturerReclamationService.findById(reclamationId);
        if (byId != null && byId.status == ReclamationStatus.SENT_BACK
                && DAYS.between(byId.manufacturerReply, today) >= deliveryTime) {
            byId.status = ReclamationStatus.READY_TO_PICKUP;
            byId.returnToCustomer = today;
            try {
                manufacturerReclamationService.saveDataList();
                customerReclamationService.replaceReclamation(byId);
            } catch (DatabaseSaveException e) {
                System.out.println(e.description);
                return false;
            }
            return true;
        }
        return false;
    }

    public void tryForwardAllReclamation() {
        this.refreshDate();
        for (Reclamation cReclamation : customerReclamationService.getDataList()) {
            this.sendReclamationToManufacturer(cReclamation.getId());
        }
        for (Reclamation mReclamation : manufacturerReclamationService.getDataList()) {
            this.sendBackToCustomer(mReclamation.getId());
        }
    }

}
