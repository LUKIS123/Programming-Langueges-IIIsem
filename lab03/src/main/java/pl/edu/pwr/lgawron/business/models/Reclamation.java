package pl.edu.pwr.lgawron.business.models;

import java.util.Date;

public class Reclamation {
    private final int id;
    private final int productId;
    private final int customerId;
    private ReclamationStatus status;
    private String description;
    private Date submittedToEmployee;
    private Date submittedToManufacturer;
    private Date manufacturerReply; // 2 weeks
    private Date returnToCustomer;
    private Date resulted;

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

    public ReclamationStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Date getSubmittedToEmployee() {
        return submittedToEmployee;
    }

    public Date getSubmittedToManufacturer() {
        return submittedToManufacturer;
    }

    public Date getManufacturerReply() {
        return manufacturerReply;
    }

    public Date getReturnToCustomer() {
        return returnToCustomer;
    }

    public Date getResulted() {
        return resulted;
    }

    public void setStatus(ReclamationStatus status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubmittedToEmployee(Date submittedToEmployee) {
        this.submittedToEmployee = submittedToEmployee;
    }

    public void setSubmittedToManufacturer(Date submittedToManufacturer) {
        this.submittedToManufacturer = submittedToManufacturer;
    }

    public void setManufacturerReply(Date manufacturerReply) {
        this.manufacturerReply = manufacturerReply;
    }

    public void setReturnToCustomer(Date returnToCustomer) {
        this.returnToCustomer = returnToCustomer;
    }

    public void setResulted(Date resulted) {
        this.resulted = resulted;
    }
}
