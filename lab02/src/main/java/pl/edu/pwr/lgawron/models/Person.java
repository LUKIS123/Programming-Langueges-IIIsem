package pl.edu.pwr.lgawron.models;

import java.util.List;

public class Person {
    private final int id;
    private final List<Integer> preferredFlavourIds;

    public Person(int id, List<Integer> preferredFlavourIds) {
        this.id = id;
        this.preferredFlavourIds = preferredFlavourIds;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getPreferredFlavourIds() {
        return preferredFlavourIds;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", preferredFlavourIds=" + preferredFlavourIds +
                '}';
    }
}
