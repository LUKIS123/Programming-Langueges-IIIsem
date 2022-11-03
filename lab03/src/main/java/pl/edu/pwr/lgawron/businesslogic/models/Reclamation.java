package pl.edu.pwr.lgawron.businesslogic.models;

import java.time.LocalDate;

public class Reclamation {
    private final int id;
    public int productId;
    private final int customerId;
    public ReclamationStatus status;
    public String description;
    public LocalDate submittedToEmployee;
    public LocalDate submittedToManufacturer;
    public LocalDate manufacturerReply; // 2 weeks
    public LocalDate returnToCustomer;
    public LocalDate resulted;

    public Reclamation(int id, int productId, int customerId) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getCustomerId() {
        return customerId;
    }
}
