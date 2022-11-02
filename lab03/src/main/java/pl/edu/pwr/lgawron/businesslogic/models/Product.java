package pl.edu.pwr.lgawron.businesslogic.models;

public class Product {
    private final int id;
    private final int manufacturerId;
    public final String name;

    public Product(int id, int manufacturerId, String name) {
        this.id = id;
        this.manufacturerId = manufacturerId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", manufacturerId=" + manufacturerId +
                ", name='" + name + '\'' +
                '}';
    }
}
