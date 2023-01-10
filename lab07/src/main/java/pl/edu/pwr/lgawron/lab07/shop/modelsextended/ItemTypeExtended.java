package pl.edu.pwr.lgawron.lab07.shop.modelsextended;

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public int getCategory() {
        return category;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return " Item" + id + " {" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                ", category=" + category +
                "} ";
    }

}
