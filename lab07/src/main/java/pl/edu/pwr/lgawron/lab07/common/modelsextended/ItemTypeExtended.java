package pl.edu.pwr.lgawron.lab07.common.modelsextended;

import model.ItemType;

import java.io.Serializable;

public class ItemTypeExtended extends ItemType implements Serializable {
    private final int id;

    public ItemTypeExtended(int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
