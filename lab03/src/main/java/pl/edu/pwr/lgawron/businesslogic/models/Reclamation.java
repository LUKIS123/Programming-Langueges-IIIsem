package pl.edu.pwr.lgawron.businesslogic.models;

import java.util.Date;

public class Reclamation {
    private final int id;
    private final int productId;
    private final int customerId;
    public ReclamationStatus status;
    public String description;
    public Date submittedToEmployee;
    public Date submittedToManufacturer;
    public Date manufacturerReply; // 2 weeks
    public Date returnToCustomer;
    public Date resulted;

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
