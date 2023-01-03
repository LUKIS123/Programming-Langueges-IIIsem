package pl.edu.pwr.lgawron.lab07.common.modelsextended;

import model.Client;

import java.io.Serializable;

public class ClientExtended extends Client implements Serializable {
    private final int id;

    public ClientExtended(int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
