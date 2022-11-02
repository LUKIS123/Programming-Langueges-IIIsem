package pl.edu.pwr.lgawron.businesslogic.models;

public class Manufacturer {
    private final int id;
    public final String name;

    public Manufacturer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
