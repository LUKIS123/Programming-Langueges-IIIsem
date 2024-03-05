package pl.edu.pwr.lgawron.businesslogic.models;

public class Product {
    private int id;
    private int manufacturerId;
    public String name;

    public Product() {
    }

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
