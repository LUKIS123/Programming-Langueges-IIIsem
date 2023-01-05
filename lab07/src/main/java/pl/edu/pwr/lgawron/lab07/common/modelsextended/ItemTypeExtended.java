package pl.edu.pwr.lgawron.lab07.common.modelsextended;

import model.ItemType;

import java.io.Serializable;

public class ItemTypeExtended extends ItemType implements Serializable {
    private final int id;
    private String name;
    private float price;
    private int category;

    public ItemTypeExtended(int id) {
        super();
        this.id = id;
    }

    public ItemTypeExtended(int id, String name, float price, int category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

}
