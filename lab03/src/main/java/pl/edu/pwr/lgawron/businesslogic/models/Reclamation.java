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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Reclamation{").append("id=").append(id);
        builder.append(", productId=").append(productId);
        builder.append(", customerId=").append(customerId);
        builder.append(", status=").append(status);
        builder.append(", description=").append(description);
        builder.append(", submittedToEmployee=").append(submittedToEmployee);

        if (submittedToManufacturer != null)
            builder.append(", submittedToManufacturer=").append(submittedToManufacturer);
        if (manufacturerReply != null)
            builder.append(", manufacturerReply=").append(manufacturerReply);
        if (returnToCustomer != null)
            builder.append(", returnToCustomer=").append(returnToCustomer);
        if (resulted != null)
            builder.append(", resulted=").append(resulted);

        builder.append("}");
        return builder.toString();
    }
}
