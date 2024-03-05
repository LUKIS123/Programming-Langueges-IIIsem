package pl.edu.pwr.lgawron.businesslogic.models;

public class Manufacturer {
    private int id;
    public String name;

    public Manufacturer() {
    }

    public Manufacturer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
