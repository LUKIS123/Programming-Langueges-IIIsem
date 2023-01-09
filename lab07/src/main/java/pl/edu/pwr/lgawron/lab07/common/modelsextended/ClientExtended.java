package pl.edu.pwr.lgawron.lab07.common.modelsextended;

import model.Client;

import java.io.Serializable;

public class ClientExtended extends Client implements Serializable {
    private final int id;
    private String name;

    public ClientExtended(int id) {
        super();
        this.id = id;
    }

    public ClientExtended(int id, Client client) {
        this.id = id;
        this.name = client.getName();
    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return " Client" + id + " {" +
                "id=" + id +
                ", name=" + name +
                "} ";
    }

}
