package pl.edu.pwr.lgawron.manufacturer.controller;

import pl.edu.pwr.lgawron.businesslogic.models.Manufacturer;
import pl.edu.pwr.lgawron.businesslogic.models.Product;
import pl.edu.pwr.lgawron.businesslogic.models.Reclamation;
import pl.edu.pwr.lgawron.businesslogic.models.ReclamationStatus;
import pl.edu.pwr.lgawron.businesslogic.services.ManufacturerReclamationService;
import pl.edu.pwr.lgawron.businesslogic.services.ManufacturerService;
import pl.edu.pwr.lgawron.businesslogic.services.ProductService;
import pl.edu.pwr.lgawron.businesslogic.utility.date.DateReader;
import pl.edu.pwr.lgawron.businesslogic.utility.exceptions.DatabaseSaveException;
import pl.edu.pwr.lgawron.manufacturer.view.ActionResult;
import pl.edu.pwr.lgawron.manufacturer.view.ManufacturerConsoleAppView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class ManufacturerAppController {
    private final ManufacturerService manufacturerService;
    private final ProductService productService;
    private final ManufacturerReclamationService reclamationService;
    private List<Product> productList;
    private int userId;
    private final DateReader dateReader;
    public LocalDate today;
    public long deliveryTime;

    public ManufacturerAppController() {
        this.manufacturerService = new ManufacturerService();
        this.productService = new ProductService();
        this.reclamationService = new ManufacturerReclamationService();
        this.dateReader = new DateReader("date.txt");
        this.today = dateReader.getCurrentDate();
        this.deliveryTime = 2L;
        this.productList = List.of();
    }

    public void index() {
        ActionResult actionResult = ManufacturerConsoleAppView.login(this);
        while (actionResult != ActionResult.END) {
            switch (actionResult) {
                case MENU:
                    actionResult = ManufacturerConsoleAppView.menu();
                    break;
                case LIST_PRODUCTS:
                    actionResult = ManufacturerConsoleAppView.listProducts(productList);
                    break;
                case LIST_COMPLAINTS:
                    actionResult = ManufacturerConsoleAppView.listComplains(this.findReclamationsById());
                    break;
                case PICK_UP_COMPLAINS:
                    actionResult = ManufacturerConsoleAppView.pickUpComplaints(this);
                    break;
                case ANSWER_COMPLAINT:
                    actionResult = ManufacturerConsoleAppView.answerComplaint(this);
                    break;
                case REFRESH_DATE:
                    actionResult = ManufacturerConsoleAppView.refreshDate(this);
                    break;
            }
        }
    }

    public boolean loginToDatabase(int manufacturerId) {
        Manufacturer byId = manufacturerService.findById(manufacturerId);
        if (byId != null) {
            userId = manufacturerId;
            return true;
        }
        return false;
    }

    public List<Product> getProductListById() {
        return productService.getDataList()
                .stream()
                .filter(product -> product.getManufacturerId() == userId)
                .toList();
    }

    public String getUserName() {
        return manufacturerService.findById(userId).name;
    }

    public void refreshDate() {
        today = dateReader.getCurrentDate();
    }

    public List<Reclamation> findReclamationsById() {
        List<Reclamation> found = new ArrayList<>();
        for (Product product : productList) {
            List<Reclamation> dataList = reclamationService.getDataList();
            for (Reclamation reclamation : dataList) {
                if (reclamation.getProductId() == product.getId()) {
                    found.add(reclamation);
                }
                if (reclamation.status == ReclamationStatus.SENT
                        && DAYS.between(reclamation.submittedToManufacturer, today) >= deliveryTime) {
                    reclamation.status = ReclamationStatus.PENDING;
                }
            }
        }
        return found;
    }

    public int pickUpComplaints() {
        this.refreshDate();
        List<Reclamation> reclamationsById = this.findReclamationsById();
        int count = 0;
        for (Reclamation reclamation : reclamationsById) {
            if (reclamation.status == ReclamationStatus.SENT
                    && DAYS.between(reclamation.submittedToManufacturer, today) >= deliveryTime) {
                reclamation.status = ReclamationStatus.PENDING;
                count++;
            }
            if (reclamation.status == ReclamationStatus.PENDING) {
                count++;
            }
        }
        try {
            reclamationService.saveDataList();
        } catch (DatabaseSaveException e) {
            System.out.println(e.description);
            return 0;
        }
        return count;
    }

    public boolean answerComplaint(int reclamationId, int answer) {
        this.refreshDate();
        Reclamation byId = reclamationService.findById(reclamationId);

        if (byId != null && byId.status == ReclamationStatus.PENDING) {
            if (answer != -1) {
                byId.response = ReclamationStatus.ACCEPTED;
            } else {
                byId.response = ReclamationStatus.REJECTED;
            }
            byId.status = ReclamationStatus.SENT_BACK;
            byId.manufacturerReply = today;
            try {
                reclamationService.saveDataList();
            } catch (DatabaseSaveException e) {
                System.out.println(e.description);
                return false;
            }
            return true;
        }
        return false;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
